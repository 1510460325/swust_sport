package cn.wzy.sport.service;

import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.model.LoginResult;

/**
 * Create by Wzy
 * on 2018/7/14 11:50
 * 不短不长八字刚好
 */
public interface User_InfoService {
    /**
     * 注册
     * @param user_info
     * @return
     */
    int register(User_Info user_info);

    /**
     * 登录
     * @param user_info
     * @param verifyCode
     * @param code
     * @return
     */
    LoginResult login(User_Info user_info, String verifyCode, String code);

    /**
     * 查看用户信息
     * @param userId
     * @return
     */
    User_Info queryUser(Integer userId);
}
