package cn.wzy.dao;

import cn.wzy.sport.dao.User_InfoDao;
import cn.wzy.util.BaseDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by Wzy
 * on 2018/7/14 11:13
 * 不短不长八字刚好
 */
public class User_InfoDaoTest extends BaseDaoTest {

    @Autowired
    private User_InfoDao userInfoDao;

    @Test
    public void test() {
        System.out.println(userInfoDao.selectByPrimaryKey(1));
    }
}
