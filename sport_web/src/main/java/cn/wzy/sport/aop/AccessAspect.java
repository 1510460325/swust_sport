package cn.wzy.sport.aop;

import cn.wzy.sport.dao.Operation_LogDao;
import cn.wzy.sport.dao.Role_AuthDao;
import cn.wzy.sport.dao.User_AuthDao;
import cn.wzy.sport.entity.Operation_Log;
import cn.wzy.sport.entity.Role_Auth;
import cn.wzy.sport.entity.User_Auth;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static cn.wzy.sport.constant.CodeConstant.ILLEGAL_ACCESS_ERROR;
import static cn.wzy.sport.service.constant.RoleConstant.VISITOR;

/**
 * Created by Wzy
 * on 2018/5/8
 */
@Log4j
public class AccessAspect {
    private static final String WEBAPP_CONTEXT = "/api";

    @Autowired
    private Role_AuthDao role_authDao;

    @Autowired
    private User_AuthDao user_authDao;

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
        String api = request.getRequestURI().replaceAll(WEBAPP_CONTEXT, "");
        // 获取请求方法
        String methodName = request.getMethod();
        if ("OPTIONS".equals(methodName)) {
            // 预请求，直接放过
            return joinPoint.proceed();
        }
        // 获取查询url
        String search_url = methodName + ":" + api;

        //日志记录
        Operation_Log record = new Operation_Log();
        record.setOpUserid(userId);
        record.setOpDate(new Date());
        record.setOpContent("roleId:" + roleId + "& userId:" + userId + "访问接口" + search_url);
        operation_logDao.insert(record);
        log.info("roleId:" + roleId + "& userId:" + userId + "访问接口" + search_url);



        BaseQuery<User_Auth> urlQuery = new BaseQuery<>(User_Auth.class);
        urlQuery.getQuery().setUsUrl(search_url);
        List<User_Auth> list = user_authDao.selectByCondition(urlQuery);
        if (list == null || list.size() == 0) {
            return new ResultModel().builder().code(ILLEGAL_ACCESS_ERROR).build();
        }

        BaseQuery<Role_Auth> req_Query = new BaseQuery<>(Role_Auth.class);
        req_Query.getQuery().setRoUrlid(list.get(0).getId()).setRoAllowrole(roleId);
        Integer total = role_authDao.selectCountByCondition(req_Query);
        if (total == null || total == 0)
            return new ResultModel().builder().code(ILLEGAL_ACCESS_ERROR).build();
        else
            return joinPoint.proceed();
    }
}
