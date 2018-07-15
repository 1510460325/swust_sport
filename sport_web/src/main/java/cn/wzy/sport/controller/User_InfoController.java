package cn.wzy.sport.controller;

import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.User_InfoService;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    public ResultModel register(User_Info user_info) {
        return new ResultModel().builder()
                .data(user_infoService.register(user_info))
                .code(SUCCESS)
                .build();
    }
}
