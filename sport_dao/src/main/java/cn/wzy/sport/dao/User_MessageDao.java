package cn.wzy.sport.dao;

import cn.wzy.sport.VO.UserMessVO;
import cn.wzy.sport.entity.User_Message;
import org.cn.wzy.dao.BaseDao;

import java.util.List;

public interface User_MessageDao extends BaseDao<User_Message> {
	List<UserMessVO> queryMessByRoom(Integer id);
}