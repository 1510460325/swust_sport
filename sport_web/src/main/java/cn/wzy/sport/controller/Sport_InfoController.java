package cn.wzy.sport.controller;

import cn.wzy.sport.entity.Sport_Info;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.Sport_InfoService;
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
 * on 2018/7/20 15:40
 * 不短不长八字刚好
 */
@Controller
@RequestMapping("/sports")
public class Sport_InfoController extends BaseController {

    @Autowired
    private Sport_InfoService sport_infoService;

    /**
     * 获取所有的运动模块
     * @param sport_info
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sports.do",method = RequestMethod.GET)
    public ResultModel sports(Sport_Info sport_info) {
        List<Sport_Info> reslut = sport_infoService.querySports(sport_info);
        return new ResultModel().builder()
                .code(SUCCESS)
                .data(reslut)
                .total(reslut == null ? 0:reslut.size())
                .build();
    }

    /**
     * 更新模块信息
     * @param sport_info
     * @param file1
     * @param file2
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update.do",method = RequestMethod.PUT)
    public ResultModel update(Sport_Info sport_info,String file1, String file2) {
        return new ResultModel().builder()
                .code(SUCCESS)
                .data(sport_infoService.update(getRequest(),sport_info,file1,file2))
                .build();
    }

    /**
     * 删除运动模块
     * @param sport_info
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
    public ResultModel deleteSport(Sport_Info sport_info) {
        return new ResultModel().builder()
                .code(SUCCESS)
                .data(sport_infoService.deleteSport(sport_info.getId()))
                .build();
    }

    /**
     * 新添模块
     * @param sport_info
     * @param file1
     * @param file2
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert.do",method = RequestMethod.POST)
    public ResultModel insertOne(Sport_Info sport_info, String file1, String file2) {
        return new ResultModel().builder()
                .code(SUCCESS)
                .data(sport_infoService.insert(getRequest(),sport_info,file1,file2))
                .build();
    }
}
