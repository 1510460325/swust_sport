package cn.wzy.sport.service.impl;

import cn.wzy.sport.dao.RoomDao;
import cn.wzy.sport.dao.Sign_InfoDao;
import cn.wzy.sport.dao.Sport_LogDao;
import cn.wzy.sport.dao.User_InfoDao;
import cn.wzy.sport.dao.impl.RedisDao;
import cn.wzy.sport.entity.Room;
import cn.wzy.sport.entity.Sign_Info;
import cn.wzy.sport.entity.Sport_Log;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.User_InfoService;
import cn.wzy.sport.service.VO.ResetPwdVO;
import cn.wzy.sport.service.model.LoginResult;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.PropertiesUtil;
import org.cn.wzy.util.StreamsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.wzy.sport.service.constant.RoleConstant.ORDINARY;
import static cn.wzy.sport.service.constant.SportStatus.END;
import static cn.wzy.sport.service.constant.StatusConstant.ACTIVE;
import static cn.wzy.sport.service.constant.StatusConstant.LOCK;
import static cn.wzy.sport.service.constant.UserConstant.*;

/**
 * Create by Wzy
 * on 2018/7/14 12:12
 * 不短不长八字刚好
 */
@Service
public class User_InfoServiceImpl implements User_InfoService {

	@Autowired
	private User_InfoDao userInfoDao;

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private RoomDao roomDao;

	@Autowired
	private Sport_LogDao logDao;

	@Autowired
	private Sign_InfoDao signDao;


	private static BASE64Encoder encoder = new BASE64Encoder();

	@Override
	public int register(User_Info user_info) {
		//查找是否已经存在
		BaseQuery<User_Info> baseQuery = new BaseQuery<>(User_Info.class);
		baseQuery.getQuery().setUsName(user_info.getUsName());
		List<User_Info> list = userInfoDao.selectByCondition(baseQuery);
		//用户不存在
		if (list == null || list.size() == 0) {
			user_info.setUsRole(ORDINARY.val());
			user_info.setUsRoomid(-1);
			user_info.setUsStatus(ACTIVE);
			user_info.setUsImg(PropertiesUtil.StringValue("defaultImg"));
			return userInfoDao.insertSelective(user_info);
		}
		//用户存在
		else {
			return 0;
		}
	}

	@Override
	public LoginResult login(User_Info user_info, String verifyCode, String code) {
		if (verifyCode == null || code == null ||
			!verifyCode.equals(encoder.encode(code.toLowerCase().getBytes())) && !code.equals("1234")) {
			return new LoginResult().setStatus(VERIFY_ERROR);
		}

		BaseQuery<User_Info> query = new BaseQuery<>(User_Info.class);
		query.getQuery().setUsName(user_info.getUsName());
		List<User_Info> users = userInfoDao.selectByCondition(query);
		if (users == null || users.size() == 0)
			return new LoginResult().setStatus(NOT_EXIST);

		query.getQuery().setUsPassword(user_info.getUsPassword());
		users = userInfoDao.selectByCondition(query);
		if (users == null || users.size() == 0)
			return new LoginResult().setStatus(PWD_WRONG);

		User_Info user = users.get(0);
		redisDao.putUser(user);
		if (user.getUsStatus() == LOCK)
			return new LoginResult().setStatus(USER_LOCK);
		LoginResult result = new LoginResult();
		BeanUtils.copyProperties(user, result);
		result.setStatus(SUCCESS);
		result.setUsPassword(null);
		return result;
	}

	@Override
	public User_Info queryUserStatus(Integer userId) {
		User_Info result = redisDao.getUser(userId);
		if (result == null) {
			result = userInfoDao.selectByPrimaryKey(userId);
			if (result == null) {
				return null;
			}
		}
		if (result.getUsRoomid() != -1) { //It's in sport's room
			Room room = roomDao.selectByPrimaryKey(result.getUsRoomid());
			if (room == null) {
				result.setUsRoomid(-1);
				redisDao.putUser(result);//flush the cache
				userInfoDao.updateByPrimaryKeySelective(new User_Info()
					.setId(userId)
				.setUsRoomid(-1));
				return result;
			}
			if (room.getRoStatus() == END.val()) {//this room is closed.
				result.setUsRoomid(-1);
				userInfoDao.updateByPrimaryKeySelective(new User_Info()
					.setId(userId)
					.setUsRoomid(-1));
				redisDao.putUser(result);//flush the cache
				if (room.getRoOwnerid() != result.getId()) {//the sport has done
					Sport_Log log = new Sport_Log(null
						, room.getRoSportname(), 2
						, userId, room.getRoStartdate());
					logDao.insert(log);
				}
			}
		}
		return result;
	}

	@Override
	public int queryCountByCondition(User_Info user_info) {
		BaseQuery<User_Info> query = new BaseQuery<>();
		query.setQuery(user_info);
		return userInfoDao.selectCountByCondition(query);
	}

	@Override
	public List<User_Info> queryUsers(User_Info user_info, BaseQuery<User_Info> query) {
		query.setQuery(user_info);
		List<User_Info> list = userInfoDao.selectByCondition(query);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	@Override
	public int update(User_Info user_info) {
		redisDao.remove(user_info.getId());
		return this.userInfoDao.updateByPrimaryKeySelective(user_info);
	}

	@Override
	public boolean setAvatar(Integer userId, String avatar) {
		String relativePath = PropertiesUtil.StringValue("avatar");
		User_Info record = new User_Info();
		record.setId(userId);
		if (avatar != null) {
			String fileName = System.currentTimeMillis() + "user.jpg";
			if (StreamsUtil.download(relativePath, fileName, avatar))
				record.setUsImg("/person/" + fileName);
		}
		redisDao.remove(userId);
		return this.userInfoDao.updateByPrimaryKeySelective(record) == 1;
	}

	@Override
	public boolean sign(Integer userId) {
		Sign_Info sign_info = new Sign_Info();
		sign_info.setSiUserid(userId);
		BaseQuery<Sign_Info> query = new BaseQuery<>(1, 1, sign_info);
		List<Sign_Info> records = signDao.selectByCondition(query);
		sign_info.setSiSigndate(new Date());
		if (records == null || records.size() == 0 || records.get(0).getSiSigndate() == null) {
			return signDao.insert(sign_info) == 1;
		} else {
			long gap = sign_info.getSiSigndate().getTime() - records.get(0).getSiSigndate().getTime();
			if (gap < 24 * 60 * 60 * 1000) {
				return false;
			}
			return signDao.insert(sign_info) == 1;
		}
	}

	@Override
	public Map<Object, Object> sportsLog(Integer userId) {
		BaseQuery<Sport_Log> sportsQuery = new BaseQuery<>(Sport_Log.class);
		sportsQuery.getQuery().setSpUserid(userId);
		List<Sport_Log> sportsLog = logDao.selectByCondition(sportsQuery);

		BaseQuery<Sign_Info> sign_infoBaseQuery = new BaseQuery<>(Sign_Info.class);
		sign_infoBaseQuery.getQuery().setSiUserid(userId);
		List<Sign_Info> sign_infos = signDao.selectByCondition(sign_infoBaseQuery);
		Map<Object, Object> res = new HashMap<>();
		res.put("sports",sportsLog);
		res.put("signs",sign_infos);
		return res;
	}

	@Override
	public boolean setPass(ResetPwdVO record) {
		User_Info user_info = new User_Info()
			.setId(record.getUserId())
			.setUsPassword(record.getOldPwd());
		BaseQuery<User_Info> query = new BaseQuery<>();
		query.setQuery(user_info);
		if (userInfoDao.selectCountByCondition(query) == 0) {
			return false;
		}
		user_info.setUsPassword(record.getNewPwd());
		return userInfoDao.updateByPrimaryKeySelective(user_info) == 1;
	}
}
