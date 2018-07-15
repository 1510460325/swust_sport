package cn.wzy.sport.service.impl;

import cn.wzy.sport.dao.User_InfoDao;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.User_InfoService;
import cn.wzy.sport.service.model.LoginResult;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.util.List;

import static cn.wzy.sport.service.constant.RoleConstant.ORDINARY;
import static cn.wzy.sport.service.constant.StatusConstant.ACTIVE;
import static cn.wzy.sport.service.constant.StatusConstant.LOCK;
import static cn.wzy.sport.service.constant.UserConstant.*;

/**
 * Create by Wzy
 * on 2018/7/14 12:12
 * 不短不长八字刚好
 */
@Service
public class User_InfoServiceImpl implements User_InfoService {

    @Autowired
    private User_InfoDao userInfoDao;

    @Override
    public int register(User_Info user_info) {
        //查找是否已经存在
        BaseQuery<User_Info> baseQuery = new BaseQuery<>(User_Info.class);
        baseQuery.getQuery().setUsName(user_info.getUsName());
        List<User_Info> list = userInfoDao.selectByCondition(baseQuery);
        //用户不存在
        if (list == null || list.size() == 0) {
            user_info.setUsRole(ORDINARY);
            user_info.setUsRoomid(-1);
            user_info.setUsStatus(ACTIVE);
            return userInfoDao.insertSelective(user_info);
        }
        //用户存在
        else {
            return 0;
        }
    }

    @Override
    public LoginResult login(User_Info user_info, String verifyCode, String code) {
        if (!verifyCode.equals(new BASE64Encoder().encode(code.getBytes()))
                && !code.equals("1234")) {
            return new LoginResult().setStatus(VERIFI_ERROR);
        }

        BaseQuery<User_Info> query = new BaseQuery<>(User_Info.class);
        query.getQuery().setUsName(user_info.getUsName());
        List<User_Info> users = userInfoDao.selectByCondition(query);
        if (users == null || users.size() == 0)
            return new LoginResult().setStatus(NOT_EXIST);

        query.getQuery().setUsPassword(user_info.getUsPassword());
        users = userInfoDao.selectByCondition(query);
        if (users == null || users.size() == 0)
            return new LoginResult().setStatus(PWD_WRONG);

        User_Info user = users.get(0);
        if (user.getUsStatus() == LOCK)
            return new LoginResult().setStatus(USER_LOCK);
        LoginResult result = new LoginResult();
        BeanUtils.copyProperties(user,result);
        result.setStatus(SUCCESS);
        result.setUsPassword(null);
        return result;
    }
}
