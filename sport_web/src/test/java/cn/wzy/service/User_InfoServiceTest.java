package cn.wzy.service;

import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.User_InfoService;
import cn.wzy.sport.service.model.LoginResult;
import cn.wzy.util.BaseDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static cn.wzy.sport.service.constant.RoleConstant.ORDINARY;
import static cn.wzy.sport.service.constant.StatusConstant.ACTIVE;

/**
 * Create by Wzy
 * on 2018/7/14 12:36
 * 不短不长八字刚好
 */
public class User_InfoServiceTest extends BaseDaoTest {

    @Autowired
    private User_InfoService user_infoService;

    @Test
    public void register() {

    }

    @Test
    public void login() {
        User_Info user_info = new User_Info()
                .setUsName("5120162154")
                .setUsPassword("asdf");
        LoginResult result = user_infoService.login(user_info,"asdf","1234");
        System.out.println(result);

    }

}
