package cn.wzy.sport.service;

import cn.wzy.sport.entity.Room;
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
     * @param query
     * @return
     */
    List<Room> queryByCondition(BaseQuery<Room> query);

    /**
     * 更新所有room的状态
     */
    Integer refreshStatus();
}
