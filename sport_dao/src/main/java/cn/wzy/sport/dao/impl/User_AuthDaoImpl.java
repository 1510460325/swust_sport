package cn.wzy.sport.dao.impl;

import cn.wzy.sport.dao.User_AuthDao;
import cn.wzy.sport.entity.User_Auth;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Create by Wzy
 * on 2018/7/14 11:10
 * 不短不长八字刚好
 */
@Repository
public class User_AuthDaoImpl extends BaseDaoImpl<User_Auth> implements User_AuthDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.sport.dao.User_AuthDao";
    }
}
