package cn.wzy.sport.service.impl;

import cn.wzy.sport.dao.RoomDao;
import cn.wzy.sport.dao.Sport_LogDao;
import cn.wzy.sport.dao.User_InfoDao;
import cn.wzy.sport.dao.impl.RedisDao;
import cn.wzy.sport.entity.Room;
import cn.wzy.sport.entity.Sport_Log;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.RoomService;
import cn.wzy.sport.service.User_InfoService;
import cn.wzy.sport.service.VO.RoomVO;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static cn.wzy.sport.service.constant.SportStatus.READY;
import static cn.wzy.sport.service.constant.StatusConstant.LOCK;

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

	@Autowired
	private Sport_LogDao logDao;

	@Autowired
	private User_InfoService userInfoService;

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
		User_Info user = userInfoService.queryUserStatus(userId);
		if (user.getUsStatus() == LOCK) {//user is active
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

	@Override
	public boolean create(RoomVO room) {
		//insert the room information
		Room record = new Room();
		BeanUtils.copyProperties(room, record);
		record.setRoStartdate(new Date(room.getRoStart()))
			.setRoEnddate(new Date(room.getRoEnd()))
			.setRoNum(1)
			.setRoStatus(READY.val());
		roomDao.insertSelective(record);
		//update the user status
		User_Info user = new User_Info();
		user.setId(record.getRoOwnerid())
			.setUsRoomid(record.getId());
		userDao.updateByPrimaryKeySelective(user);
		redisDao.remove(user.getId());
		//insert the sport log (leader)
		Sport_Log log = new Sport_Log();
		log.setSpName(record.getRoSportname())
			.setSpUserid(record.getRoOwnerid())
			.setSpType(1)
			.setSpSportdate(new Date());
		return logDao.insert(log) == 1;
	}
}
