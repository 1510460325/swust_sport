package cn.wzy.sport.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.util.VerifyCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * Create by Wzy
 * on 2018/7/15 15:56
 * 不短不长八字刚好
 */
@Controller
@RequestMapping("/verify")
public class VerifyController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "/getVerify.do",method = RequestMethod.GET)
    public ResultModel verify() throws IOException {
        return new ResultModel().builder()
                .data(VerifyCodeUtils.VerifyCode(80,30,4))
                .code(SUCCESS).build();
    }
}
