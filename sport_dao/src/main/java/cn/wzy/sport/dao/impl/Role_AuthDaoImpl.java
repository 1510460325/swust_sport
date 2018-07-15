package cn.wzy.sport.dao.impl;

import cn.wzy.sport.dao.Role_AuthDao;
import cn.wzy.sport.entity.Role_Auth;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Create by Wzy
 * on 2018/7/14 11:04
 * 不短不长八字刚好
 */
@Repository
public class Role_AuthDaoImpl extends BaseDaoImpl<Role_Auth> implements Role_AuthDao {

    @Override
    public String getNameSpace() {
        return "cn.wzy.sport.dao.Role_AuthDao";
    }
}
