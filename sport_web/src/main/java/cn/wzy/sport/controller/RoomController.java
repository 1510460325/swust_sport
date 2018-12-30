package cn.wzy.sport.controller;

import cn.wzy.sport.service.VO.RoomVO;
import cn.wzy.sport.entity.Room;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.RoomService;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * Create by Wzy
 * on 2018/7/21 15:09
 * 不短不长八字刚好
 */
@Controller
@RequestMapping("/room")
public class RoomController extends BaseController {

	@Autowired
	private RoomService roomService;

	/**
	 * query rooms by condition.
	 *
	 * @param room record
	 * @param query page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rooms.do", method = RequestMethod.GET)
	public ResultModel query(Room room, BaseQuery<Room> query) {
		List<Room> result = roomService.queryByCondition(query.setQuery(room));
		return ResultModel.builder()
			.total(result == null ? 0 : result.size())
			.data(result)
			.code(SUCCESS)
			.build();
	}

	/**
	 * query total by condition.
	 *
	 * @param room record
	 * @param query page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/total.do", method = RequestMethod.GET)
	public ResultModel total(Room room, BaseQuery<Room> query) {
		return ResultModel.builder()
			.data(roomService.total(query.setQuery(room)))
			.code(SUCCESS)
			.build();
	}

	/**
	 * changeNum a sport's room.
	 *
	 * @param record userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/enter.do", method = RequestMethod.PUT)
	public ResultModel enterRoom(@RequestBody User_Info record) {
		record.setId((Integer) ValueOfClaims("userId"));
		return ResultModel.builder()
			.data(roomService.enterNewRoom(record.getId(), record.getUsRoomid()))
			.code(SUCCESS)
			.build();
	}

	/**
	 * create a new sport's room.
	 *
	 * @param record record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create.do", method = RequestMethod.POST)
	public ResultModel create(@RequestBody RoomVO record) {
		record.setRoOwnerid((Integer) ValueOfClaims("userId"));
		return ResultModel.builder()
			.data(roomService.create(record))
			.code(SUCCESS)
			.build();
	}

}
