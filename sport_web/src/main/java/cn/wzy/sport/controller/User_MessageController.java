package cn.wzy.sport.controller;

import cn.wzy.sport.entity.User_Message;
import cn.wzy.sport.service.User_MessageService;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * Create by Wzy
 * on 2018/7/21 16:02
 * 不短不长八字刚好
 */
@Controller
@RequestMapping("/message")
public class User_MessageController extends BaseController {

    @Autowired
    private User_MessageService user_messageService;

    /**
     * 条件查询记录
     * @param query
     * @param user_message
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/messages.do",method = RequestMethod.GET)
    public ResultModel queryMessages(BaseQuery<User_Message> query, User_Message user_message){
        query.setQuery(user_message);
        return ResultModel.builder()
                .code(SUCCESS)
                .data(user_messageService.queryMessage(query))
                .build();
    }

    @ResponseBody
    @RequestMapping(value = "/userMessages.do",method = RequestMethod.GET)
    public ResultModel userMessages(String username, Integer roomId){
        return ResultModel.builder()
                .code(SUCCESS)
                .data(user_messageService.queryByUser(username,roomId))
                .build();
    }

}
