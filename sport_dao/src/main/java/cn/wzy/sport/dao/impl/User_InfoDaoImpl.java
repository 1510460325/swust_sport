package cn.wzy.sport.dao.impl;

import cn.wzy.sport.dao.User_InfoDao;
import cn.wzy.sport.entity.User_Info;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by Wzy
 * on 2018/7/14 11:11
 * 不短不长八字刚好
 */
@Repository
public class User_InfoDaoImpl extends BaseDaoImpl<User_Info> implements User_InfoDao {
	@Override
	public String getNameSpace() {
		return "cn.wzy.sport.dao.User_InfoDao";
	}

	@Override
	public int updateAndReturnOld(Integer userId, Integer newId) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("newId", newId);
		return this.getSqlSession().selectOne(getNameSpace() + ".updateAndReturnOld", param);
	}
}
