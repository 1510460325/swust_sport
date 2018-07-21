package cn.wzy.sport.controller;

import cn.wzy.sport.entity.Room;
import cn.wzy.sport.service.RoomService;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
     * 查询所有的房间
     * @param room
     * @param query
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rooms",method = RequestMethod.GET)
    public ResultModel query(Room room, BaseQuery<Room> query) {
        query.setQuery(room);
        List<Room> result = roomService.queryByCondition(query);
        return new ResultModel()
                .builder().total(result == null ? 0 : result.size())
                .data(result)
                .code(SUCCESS)
                .build();
    }
}
