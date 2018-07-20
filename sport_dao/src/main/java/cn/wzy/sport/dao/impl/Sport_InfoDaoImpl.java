package cn.wzy.sport.dao.impl;

import cn.wzy.sport.dao.Sport_InfoDao;
import cn.wzy.sport.entity.Sport_Info;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Create by Wzy
 * on 2018/7/14 11:09
 * 不短不长八字刚好
 */
@Repository
public class Sport_InfoDaoImpl extends BaseDaoImpl<Sport_Info> implements Sport_InfoDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.sport.dao.Sport_InfoDao";
    }
}


