package cn.wzy.sport.controller;

import cn.wzy.sport.service.VO.AvatarVo;
import cn.wzy.sport.aop.AccessAspect;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.User_InfoService;
import cn.wzy.sport.service.constant.UserConstant;
import cn.wzy.sport.service.model.LoginResult;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.wzy.sport.service.constant.RoleConstant.ADMIN;
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
	 *
	 * @param user_info
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public ResultModel register(@RequestBody User_Info user_info) {
		return new ResultModel().builder()
			.data(user_infoService.register(user_info))
			.code(SUCCESS)
			.build();
	}

	/**
	 * 登录
	 *
	 * @param user_info
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public ResultModel login(User_Info user_info, String code) {
		LoginResult result = user_infoService.login(user_info, (String) getSessionValue("verifyCode"), code);
		if (result.getStatus() == UserConstant.SUCCESS) {
			Map<String, Object> claims = new HashMap<>(2);
			claims.put("roleId", result.getUsRole());
			claims.put("userId", result.getId());
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
	 *
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user.do", method = RequestMethod.GET)
	public ResultModel user_info(Integer userId) {
		return new ResultModel().builder()
			.code(SUCCESS)
			.data(user_infoService.queryUserStatus(userId))
			.build();
	}

	/**
	 * 条件查询所有用户
	 *
	 * @param user_info
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/users.do", method = RequestMethod.GET)
	public ResultModel Usersquery(User_Info user_info, BaseQuery<User_Info> query) {
		List<User_Info> result = user_infoService.queryUsers(user_info, query);
		return new ResultModel().builder()
			.code(SUCCESS)
			.data(result)
			.total(result == null ? 0 : result.size())
			.build();
	}

	/**
	 * 查询所有的个数
	 *
	 * @param user_info
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userCount.do", method = RequestMethod.GET)
	public ResultModel queryCount(User_Info user_info) {
		return new ResultModel().builder()
			.code(SUCCESS)
			.data(user_infoService.queryCountByCondition(user_info))
			.build();
	}

	/**
	 * 更新个人信息
	 *
	 * @param user_info
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.PUT)
	public ResultModel update(@RequestBody User_Info user_info) {
		checkAccess(user_info);
		return new ResultModel().builder()
			.code(SUCCESS)
			.data(user_infoService.update(user_info))
			.build();
	}


	@ResponseBody
	@RequestMapping(value = "/setAvatar.do", method = RequestMethod.PUT)
	public ResultModel setAvatar(@RequestBody AvatarVo  record) {
		return new ResultModel().builder()
			.code(SUCCESS)
			.data(user_infoService.setAvatar(record.getUserId(), record.getAvatar()))
			.build();
	}

	private void checkAccess(User_Info user_info) {
		Integer roleId = (Integer) ValueOfClaims("roleId");
		Integer userId = (Integer) ValueOfClaims("userId");
		if (roleId != ADMIN) {
			user_info.setId(userId);
			user_info.setUsRole(null);
			user_info.setUsStatus(null);
		}
	}

	/**
	 * 开启ip查询
	 *
	 * @param open
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipSearch.do", method = RequestMethod.PUT)
	public ResultModel IpSearch(Boolean open) {
		if (open != null)
			AccessAspect.open = open;
		if (AccessAspect.open)
			return new ResultModel().builder()
				.data("开启ip查询")
				.build();
		return new ResultModel().builder()
			.data("关闭ip查询")
			.build();
	}

	/**
	 * 查询是否开启ipsearch
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isOpen.do", method = RequestMethod.GET)
	public ResultModel isOPen() {
		return new ResultModel().builder()
			.data(AccessAspect.open)
			.build();
	}
}
