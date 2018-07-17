package cn.wzy.sport.controller;

import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.User_InfoService;
import cn.wzy.sport.service.constant.StatusConstant;
import cn.wzy.sport.service.constant.UserConstant;
import cn.wzy.sport.service.model.LoginResult;
import io.jsonwebtoken.Claims;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * Create by Wzy
 * on 2018/7/14 13:30
 * 不短不长八字刚好
 */
@Controller
@RequestMapping("/user")
public class User_InfoController extends BaseController {

    @Autowired
    private User_InfoService user_infoService;

    /**
     * 注册用户
     * @param user_info
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    public ResultModel register(User_Info user_info) {
        return new ResultModel().builder()
                .data(user_infoService.register(user_info))
                .code(SUCCESS)
                .build();
    }

    /**
     * 登录
     * @param user_info
     * @param verifyCode
     * @param code
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login.do",method = RequestMethod.GET)
    public ResultModel login(User_Info user_info, String verifyCode, String code) {
        LoginResult result = user_infoService.login(user_info,verifyCode,code);
        if (result.getStatus() == UserConstant.SUCCESS) {
            Map<String,Object> claims = new HashMap<>(2);
            claims.put("roleId",result.getUsRole());
            claims.put("userId",result.getId());
            return new ResultModel().builder()
                    .data(result)
                    .code(SUCCESS)
                    .token(TokenUtil.tokens(claims))
                    .build();
        }
        return new ResultModel().builder()
                .data(result)
                .code(SUCCESS)
                .build();
    }

    /**
     * 用id查询用户
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user.do",method = RequestMethod.GET)
    public ResultModel user_info(Integer userId) {
        return new ResultModel().builder()
                .code(SUCCESS)
                .data(user_infoService.queryUser(userId))
                .build();
    }
}
