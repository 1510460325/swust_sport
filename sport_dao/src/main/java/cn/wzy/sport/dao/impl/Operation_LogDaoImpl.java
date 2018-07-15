package cn.wzy.sport.dao.impl;

import cn.wzy.sport.dao.Operation_LogDao;
import cn.wzy.sport.entity.Operation_Log;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Create by Wzy
 * on 2018/7/14 11:03
 * 不短不长八字刚好
 */
@Repository
public class Operation_LogDaoImpl extends BaseDaoImpl<Operation_Log> implements Operation_LogDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.sport.dao.Operation_LogDao";
    }
}
