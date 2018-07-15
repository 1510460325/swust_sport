package cn.wzy.sport.dao.impl;

import cn.wzy.sport.dao.Complain_InfoDao;
import cn.wzy.sport.entity.Complain_Info;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Create by Wzy
 * on 2018/7/14 11:00
 * 不短不长八字刚好
 */
@Repository
public class Complain_InfoDaoImpl extends BaseDaoImpl<Complain_Info> implements Complain_InfoDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.sport.dao.Complain_InfoDao";
    }
}
