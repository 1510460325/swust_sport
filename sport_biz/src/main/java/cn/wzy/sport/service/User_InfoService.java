package cn.wzy.sport.service;

import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.model.LoginResult;
import org.cn.wzy.query.BaseQuery;
import org.omg.PortableInterceptor.Interceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 条件查询所有用户
     * @param user_info
     * @param query
     * @return
     */
    List<User_Info> queryUsers(User_Info user_info, BaseQuery<User_Info> query);

    /**
     * 条件查询个数
     * @param user_info
     * @return
     */
    int queryCountByCondition(User_Info user_info);

    /**
     * 更新个人信息
     * @param user_info
     * @return
     */
    int update(User_Info user_info);

    /**
     * 更新个人照片
     * @param userId
     * @param avatar
     * @return
     */
    boolean setAvatar(Integer userId, String avatar);
}
