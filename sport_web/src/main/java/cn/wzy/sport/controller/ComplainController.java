package cn.wzy.sport.controller;

import cn.wzy.sport.service.Complain_InfoService;
import cn.wzy.sport.service.model.ComplainVO;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * Create by Wzy
 * on 2018/7/20 22:06
 * 不短不长八字刚好
 */
@Controller
@RequestMapping("/complain")
public class ComplainController extends BaseController {
    @Autowired
    private Complain_InfoService complain_infoService;

    /**
     * 查看所有的反馈
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/complains.do",method = RequestMethod.GET)
    public ResultModel complains() {
        List<ComplainVO> result = complain_infoService.queryAllComplains();
        return new ResultModel().builder()
                .code(SUCCESS)
                .data(result)
                .total(result == null ? 0 : result.size())
                .build();
    }

    /**
     * 删除反馈
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
    public ResultModel deleComplains(Integer id) {
        return new ResultModel().builder()
                .code(SUCCESS)
                .data(complain_infoService.deleComplain(id))
                .build();
    }
}
