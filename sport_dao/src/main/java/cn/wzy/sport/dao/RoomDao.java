package cn.wzy.sport.dao;

import cn.wzy.sport.entity.Room;
import org.cn.wzy.dao.BaseDao;

import java.util.List;

public interface RoomDao extends BaseDao<Room> {

    Integer refresh();
}