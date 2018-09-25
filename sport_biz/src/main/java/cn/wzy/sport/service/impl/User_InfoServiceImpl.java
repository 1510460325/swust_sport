package cn.wzy.sport.service.impl;

import cn.wzy.sport.dao.User_InfoDao;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static cn.wzy.sport.service.constant.RoleConstant.ORDINARY;
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

	private static BASE64Encoder encoder = new BASE64Encoder();

	@Override
	public int register(User_Info user_info) {
		//查找是否已经存在
		BaseQuery<User_Info> baseQuery = new BaseQuery<>(User_Info.class);
		baseQuery.getQuery().setUsName(user_info.getUsName());
		List<User_Info> list = userInfoDao.selectByCondition(baseQuery);
		//用户不存在
		if (list == null || list.size() == 0) {
			user_info.setUsRole(ORDINARY);
			user_info.setUsRoomid(-1);
			user_info.setUsStatus(ACTIVE);
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
			return new LoginResult().setStatus(VERIFI_ERROR);
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
		if (user.getUsStatus() == LOCK)
			return new LoginResult().setStatus(USER_LOCK);
		LoginResult result = new LoginResult();
		BeanUtils.copyProperties(user, result);
		result.setStatus(SUCCESS);
		result.setUsPassword(null);
		return result;
	}

	@Override
	public User_Info queryUser(Integer userId) {
		return userInfoDao.selectByPrimaryKey(userId).setUsPassword(null);
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
		for (User_Info user : list) {
			user.setUsPassword(null);
		}
		return list;
	}

	@Override
	public int update(User_Info user_info) {
		return this.userInfoDao.updateByPrimaryKeySelective(user_info);
	}

	@Override
	public boolean setAvatar(User_Info record, String avatar) {
		String relativePath = PropertiesUtil.StringValue("avatar");
		if (avatar != null) {
			String fileName = System.currentTimeMillis() + "user.jpg";
			if (StreamsUtil.download(relativePath, fileName, avatar))
				record.setUsImg("/person/" + fileName);
		}
		this.userInfoDao.updateByPrimaryKeySelective(record);
		return true;
	}
}
