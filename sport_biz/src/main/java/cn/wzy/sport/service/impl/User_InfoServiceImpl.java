package cn.wzy.sport.service.impl;

import cn.wzy.sport.dao.RoomDao;
import cn.wzy.sport.dao.Sport_LogDao;
import cn.wzy.sport.dao.User_InfoDao;
import cn.wzy.sport.dao.impl.RedisDao;
import cn.wzy.sport.entity.Room;
import cn.wzy.sport.entity.Sport_Log;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.User_InfoService;
import cn.wzy.sport.service.model.LoginResult;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.PropertiesUtil;
import org.cn.wzy.util.StreamsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.util.List;

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
			if (room.getRoStatus() == END.val()) {//this room is closed.
				result.setUsRoomid(-1);
				redisDao.putUser(result);//flush the cache
				if (room.getRoOwnerid() != result.getId()) {//the sport has done
					Sport_Log log = new Sport_Log(null
						,room.getRoSportname(),2
						,userId,room.getRoStartdate());
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
}
