package cn.wzy.sport.dao.impl;

import cn.wzy.sport.dao.Sign_InfoDao;
import cn.wzy.sport.entity.Sign_Info;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Create by Wzy
 * on 2018/7/14 11:08
 * 不短不长八字刚好
 */
@Repository
public class Sign_InfoDaoImpl extends BaseDaoImpl<Sign_Info> implements Sign_InfoDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.sport.dao.Sign_InfoDao";
    }
}
