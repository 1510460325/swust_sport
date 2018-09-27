package cn.wzy.sport.service.impl;

import cn.wzy.sport.dao.RoomDao;
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

	@Override
	public Integer refreshStatus() {
		return roomDao.refresh();
	}

	@Override
	public List<Room> queryByCondition(BaseQuery<Room> query) {
		return roomDao.selectByCondition(query);
	}
}
