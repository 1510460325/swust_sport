package cn.wzy.sport.service.impl;

import cn.wzy.sport.dao.RoomDao;
import cn.wzy.sport.dao.User_InfoDao;
import cn.wzy.sport.dao.impl.RedisDao;
import cn.wzy.sport.entity.Room;
import cn.wzy.sport.service.RoomService;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by Wzy
 * on 2018/7/21 15:07
 * 不短不长八字刚好
 */
@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomDao roomDao;

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private User_InfoDao userDao;

	@Override
	public Integer refreshStatus() {
		return roomDao.refresh();
	}

	@Override
	public int total(BaseQuery<Room> query) {
		return roomDao.selectCountByCondition(query);
	}

	@Override
	public boolean enterNewRoom(Integer userId, Integer roomId) {
		if (userId == null || roomId == null) {
			return false;
		}
		int old = userDao.updateAndReturnOld(userId, roomId);
		redisDao.remove(userId);
		if (old == roomId) {
			return true;
		} else {
			return roomDao.changeNum(old, roomId);
		}
	}

	@Override
	public List<Room> queryByCondition(BaseQuery<Room> query) {
		return roomDao.selectByCondition(query);
	}
}
