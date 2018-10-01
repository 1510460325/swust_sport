package cn.wzy.sport.dao;

import cn.wzy.sport.entity.User_Info;
import org.cn.wzy.dao.BaseDao;

public interface User_InfoDao extends BaseDao<User_Info> {
	int updateAndReturnOld(Integer userId, Integer newId);
}