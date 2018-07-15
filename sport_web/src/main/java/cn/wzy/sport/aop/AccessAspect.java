package cn.wzy.sport.aop;

import cn.wzy.sport.dao.Role_AuthDao;
import cn.wzy.sport.dao.User_AuthDao;
import cn.wzy.sport.entity.Role_Auth;
import cn.wzy.sport.entity.User_Auth;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static cn.wzy.sport.constant.CodeConstant.ILLEGAL_ACCESS_ERROR;
import static cn.wzy.sport.service.constant.RoleConstant.VISITOR;

/**
 * Created by Wzy
 * on 2018/5/8
 */
@Log4j
public class AccessAspect {
    private static final String WEBAPP_CONTEXT = "/sport";
    @Autowired
    private Role_AuthDao role_authDao;
    @Autowired
    private User_AuthDao user_authDao;

    public Object checkAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        BaseController controller = (BaseController) joinPoint.getTarget();
        HttpServletRequest request = controller.getRequest();

        //从jwt中获取请求者的roleId
        int roleId;
        Integer id = (Integer) TokenUtil.tokenValueOf(request.getHeader("Authorization"), "roleId");
        roleId = id == null ? VISITOR : id;
        Integer userId = (Integer) TokenUtil.tokenValueOf(request.getHeader("Authorization"), "userId");
        String api = request.getRequestURI().replaceAll(WEBAPP_CONTEXT, "");
        // 获取请求方法
        String methodName = request.getMethod();
        if ("OPTIONS".equals(methodName)) {
            // 预请求，直接放过
            return joinPoint.proceed();
        }
        // 获取查询url
        String search_url = methodName + ":" + api;
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
