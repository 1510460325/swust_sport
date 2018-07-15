package cn.wzy.sport.dao.impl;

import cn.wzy.sport.dao.RoleDao;
import cn.wzy.sport.entity.Role;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Create by Wzy
 * on 2018/7/14 11:06
 * 不短不长八字刚好
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.sport.dao.RoleDao";
    }
}
