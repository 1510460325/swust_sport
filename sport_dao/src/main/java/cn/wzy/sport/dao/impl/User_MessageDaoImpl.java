package cn.wzy.sport.dao.impl;

import cn.wzy.sport.dao.User_MessageDao;
import cn.wzy.sport.entity.User_Message;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Create by Wzy
 * on 2018/7/14 11:12
 * 不短不长八字刚好
 */
@Repository
public class User_MessageDaoImpl extends BaseDaoImpl<User_Message> implements User_MessageDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.sport.dao.User_MessageDao";
    }
}
