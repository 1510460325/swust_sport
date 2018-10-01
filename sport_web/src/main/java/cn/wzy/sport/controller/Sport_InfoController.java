package cn.wzy.sport.controller;

import cn.wzy.sport.entity.Sport_Info;
import cn.wzy.sport.service.Sport_InfoService;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * Create by Wzy.
 * on 2018/7/20 15:40
 * 不短不长八字刚好
 */
@Controller
@RequestMapping("/sports")
public class Sport_InfoController extends BaseController {

  @Autowired
	private Sport_InfoService sport_infoService;

	/**
	 * query sports by condition.
	 *
	 * @param sport_info record
	 * @return result
	 */
	@ResponseBody
	@RequestMapping(value = "/sports.do", method = RequestMethod.GET)
	public ResultModel sports(BaseQuery<Sport_Info> query, Sport_Info sport_info) {
		List<Sport_Info> reslut = sport_infoService.querySports(query.setQuery(sport_info));
		return new ResultModel().builder()
			.code(SUCCESS)
			.data(reslut)
			.total(reslut == null ? 0 : reslut.size())
			.build();
	}

	/**
	 * query total by condition.
	 *
	 * @param sport_info sport info
	 * @return result
	 */
	@ResponseBody
	@RequestMapping(value = "/total.do", method = RequestMethod.GET)
	public ResultModel total(BaseQuery<Sport_Info> query, Sport_Info sport_info) {
		return new ResultModel().builder()
			.code(SUCCESS)
			.data(sport_infoService.total(query.setQuery(sport_info)))
			.build();
	}

	/**
	 * update the sport_info.
	 *
	 * @param sport_info sport_info
	 * @param file1      module img
	 * @param file2      room img
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.PUT)
	public ResultModel update(Sport_Info sport_info, String file1, String file2) {
		return new ResultModel().builder()
			.code(SUCCESS)
			.data(sport_infoService.update(sport_info, file1, file2))
			.build();
	}

	/**
	 * delete the sport.
	 *
	 * @param sport_info record
	 * @return result
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.do", method = RequestMethod.DELETE)
	public ResultModel deleteSport(Sport_Info sport_info) {
		return new ResultModel().builder()
			.code(SUCCESS)
			.data(sport_infoService.deleteSport(sport_info.getId()))
			.build();
	}

	/**
	 * add the new sport
	 *
	 * @param sport_info sport_info
	 * @param file1      module img
	 * @param file2      room img
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public ResultModel insertOne(Sport_Info sport_info, String file1, String file2) {
		return new ResultModel().builder()
			.code(SUCCESS)
			.data(sport_infoService.insert(sport_info, file1, file2))
			.build();
	}
}
