package cn.wzy.sport.dao.impl;

import cn.wzy.sport.dao.Sport_LogDao;
import cn.wzy.sport.entity.Sport_Log;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Create by Wzy
 * on 2018/7/14 11:09
 * 不短不长八字刚好
 */
@Repository
public class Sport_LogDaoImpl extends BaseDaoImpl<Sport_Log> implements Sport_LogDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.sport.dao.Sport_LogDao";
    }
}
