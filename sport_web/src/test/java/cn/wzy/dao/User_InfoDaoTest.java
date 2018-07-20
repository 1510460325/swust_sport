package cn.wzy.dao;

import cn.wzy.sport.dao.User_InfoDao;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.util.BaseDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static cn.wzy.sport.service.constant.StatusConstant.ACTIVE;

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
    @Test
    public void insertList() {
        String[] names = new String[10];
        names[0] = "张飞";
        names[1] = "刘备";
        names[2] = "关羽";
        names[3] = "曹操";

        names[4] = "计科";
        names[5] = "信安";
        names[6] = "软件";
        int n = 200;
        List<User_Info> list = new ArrayList<>(200);
        while (n-- != 0) {
            User_Info record = new User_Info();
            record.setUsName(names[n % 4] + "Id" + n);
            record.setUsPassword("aasdf");
            record.setUsAge(18 + n%3);
            record.setUsInstitution("计科");
            record.setUsMajor(names[n % 3 + 4]);
            record.setUsClass(names[n % 3 + 4]+n%7 + "班");
            record.setUsNickname(names[n % 4]);
            record.setUsRole(n%2 + 1);
            record.setUsRoomid(-1);
            record.setUsStatus(ACTIVE);
            list.add(record);
        }
        System.out.println(userInfoDao.insertList(list));
    }

    @Test
    public void deleteList() {
        List<Integer> list = new ArrayList<>(200);
        for (int i =5; i < 200;i++) {
            list.add(i);
        }
        userInfoDao.deleteByIdsList(list);
    }
}
