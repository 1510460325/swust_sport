package cn.wzy.sport.aop;

import cn.wzy.sport.dao.Operation_LogDao;
import cn.wzy.sport.dao.Role_AuthDao;
import cn.wzy.sport.entity.Operation_Log;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import static cn.wzy.sport.constant.CodeConstant.ILLEGAL_ACCESS_ERROR;
import static cn.wzy.sport.service.constant.RoleConstant.VISITOR;

/**
 * Created by Wzy
 * on 2018/5/8
 */
@Log4j
public class AccessAspect {

	public static volatile boolean open = false;

	@Autowired
	private Role_AuthDao role_authDao;

	@Autowired
	private Operation_LogDao operation_logDao;


	public Object checkAccess(ProceedingJoinPoint joinPoint) throws Throwable {
		BaseController controller = (BaseController) joinPoint.getTarget();
		HttpServletRequest request = controller.getRequest();

		//从jwt中获取请求者的roleId
		Integer roleId = (Integer) controller.ValueOfClaims("roleId");
		roleId = roleId == null ? VISITOR : roleId;
		Integer userId = (Integer) controller.ValueOfClaims("userId");
		userId = userId == null ? -1 : userId;
		String api = request.getRequestURI().replaceAll(request.getContextPath(), "");
		String methodName = request.getMethod();
		if ("OPTIONS".equals(methodName)) {
			return joinPoint.proceed();
		}
		String search_url = methodName + ":" + api;

		//日志记录
		Operation_Log record = new Operation_Log();
		record.setOpUserid(userId);
		record.setOpDate(new Date());
		record.setOpContent("roleId:" + roleId + "& userId:" + userId + "访问接口" + search_url);
		operation_logDao.insert(record);
		log.info("roleId:" + roleId + " & userId:" + userId + "访问接口" + search_url);
		if (open)
			log.info("他/她位于：" + queryAdress(request));

		int total = role_authDao.records(roleId, search_url);
		if (total == 0) {
			return new ResultModel().builder().code(ILLEGAL_ACCESS_ERROR).build();
		} else {
			return joinPoint.proceed();
		}
	}

	protected String queryAdress(HttpServletRequest request) throws IOException {
		String command = "java -classpath "+ PropertiesUtil.StringValue("IpListener") + " " + request.getRemoteAddr();
		BufferedReader br;
		Process p = Runtime.getRuntime().exec(command);
		br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
}
