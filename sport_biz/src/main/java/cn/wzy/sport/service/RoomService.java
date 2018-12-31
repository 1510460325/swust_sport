package cn.wzy.sport.service;

import cn.wzy.sport.entity.Room;
import cn.wzy.sport.service.VO.RoomVO;
import org.cn.wzy.query.BaseQuery;

import java.util.List;

/**
 * Create by Wzy
 * on 2018/7/21 15:05
 * 不短不长八字刚好
 */
public interface RoomService {
	/**
	 * 查询所有的房间
	 *
	 * @param query
	 * @return
	 */
	List<Room> queryByCondition(BaseQuery<Room> query);

	/**
	 * 更新所有room的状态
	 */
	Integer refreshStatus();

	/**
	 * query total.
	 * @param query query
	 * @return total
	 */
	int total(BaseQuery<Room> query);

	/**
	 * changeNum a new room
	 * @param userId userId
	 * @param roomId roomId
	 * @return
	 */
	boolean enterNewRoom(Integer userId, Integer roomId);

	/**
	 * create a new room
	 * @param room record
	 * @return result
	 */
	boolean create(RoomVO room);

	/**
	 * end the room
	 * @param room
	 * @return
	 */
	boolean end(RoomVO room);
}
