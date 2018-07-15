package cn.wzy.sport.service;

import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.model.LoginResult;

/**
 * Create by Wzy
 * on 2018/7/14 11:50
 * 不短不长八字刚好
 */
public interface User_InfoService {
    int register(User_Info user_info);
    LoginResult login(User_Info user_info, String verifyCode, String code);
}
