package cn.wzy.sport.dao.impl;

import cn.wzy.sport.dao.RoomDao;
import cn.wzy.sport.entity.Room;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Create by Wzy
 * on 2018/7/14 11:07
 * 不短不长八字刚好
 */
@Repository
public class RoomDaoImpl extends BaseDaoImpl<Room> implements RoomDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.sport.dao.RoomDao";
    }
}
