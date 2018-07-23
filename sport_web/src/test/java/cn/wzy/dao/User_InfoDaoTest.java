package cn.wzy.dao;

import cn.wzy.sport.dao.Complain_InfoDao;
import cn.wzy.sport.dao.RoomDao;
import cn.wzy.sport.dao.User_InfoDao;
import cn.wzy.sport.dao.User_MessageDao;
import cn.wzy.sport.entity.Complain_Info;
import cn.wzy.sport.entity.Room;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.entity.User_Message;
import cn.wzy.util.BaseDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private Complain_InfoDao complain_infoDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private User_MessageDao user_messageDao;

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
            record.setUsAge(18 + n % 3);
            record.setUsInstitution("计科");
            record.setUsMajor(names[n % 3 + 4]);
            record.setUsClass(names[n % 3 + 4] + n % 7 + "班");
            record.setUsNickname(names[n % 4]);
            record.setUsRole(n % 2 + 1);
            record.setUsRoomid(-1);
            record.setUsStatus(ACTIVE);
            list.add(record);
        }
        System.out.println(userInfoDao.insertList(list));
    }

    @Test
    public void deleteList() {
        List<Integer> list = new ArrayList<>(200);
        for (int i = 5; i < 200; i++) {
            list.add(i);
        }
        userInfoDao.deleteByIdsList(list);
    }

    @Test
    public void insertComplains() {
        String[] strings = new String[5];
        strings[0] = "这个东西真的好用！";
        strings[1] = "我这么说...";
        strings[2] = "应该没人反对,对吧?";
        strings[3] = "如果有！";
        strings[4] = "给你一个字 \'哼\'";
        List<Complain_Info> list = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            Complain_Info complain_info = new Complain_Info();
            complain_info.setCoUserid(i + 2)
                    .setCoContent(strings[i % 5])
                    .setCoCreatdate(new Date());
            list.add(complain_info);
        }
        complain_infoDao.insertList(list);
    }

    @Test
    public void insertRooms() {
        List<Room> list = new ArrayList<>(15);
        String[] location = new String[7];
        location[0] = "新区";
        location[1] = "中区";
        location[2] = "老区";
        location[3] = "篮球";
        location[4] = "足球";
        location[5] = "乒乓球";
        location[6] = "橄榄球";
        for (int i = 0; i < 15; i++) {
            Room room = new Room();
            room.setRoEnddate(new Date())
                    .setRoStartdate(new Date())
                    .setRoSportname(location[i % 4 + 3])
                    .setRoLocation(location[i % 3])
                    .setRoNum(15)
                    .setRoOrinum(20)
                    .setRoOwnerid(1 + i * i % 3)
                    .setRoStatus(1);
            list.add(room);
        }
        roomDao.insertList(list);
    }

    @Test
    public void insertMessage() throws InterruptedException {
        List<User_Message> list = new ArrayList<>(500);
        String[] strs = new String[10];
        strs[0] = "很多人喜欢旅游分享经验，看着他们把钱花光出去走一趟，回来后生活还是没有任何改变，我就放心了。";
        strs[1] = "失败是成功之母，可惜成功六亲不认。";
        strs[2] = "上帝为你关上一道防盗门，同时还给你上了一把钛合金锁。";
        strs[3] = "有什么好悲伤的？人生不就是起起落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落落的吗？";
        strs[4] = "从前书信很慢，车马很远，一生只能爱一个人，但可以纳很多妾。";
        strs[5] = "只要坚持下去，你就发现幸运一定会发生在别人身上。";
        strs[6] = "如果你还在坚持，说明你还不够绝望。";
        strs[7] = "你全力做到的最好，可能还不如别人随便搞搞。";
        strs[8] = "物以类聚人以穷分。有钱人终成眷属。";
        strs[9] = "别跟往事干杯了，往事都快吐了。";
        for (int i = 0; i < 500;i++) {
            User_Message record = new User_Message();
            record.setUsUserid(i % 20 + 1);
            record.setUsRoomid(i % 14 + 1);
            record.setUsMessage(strs[i % 10]);
            record.setUsSendtime(new Date());
            list.add(record);
            Thread.sleep(1000);
            System.out.println(i);
        }
        user_messageDao.insertList(list);
    }


}
