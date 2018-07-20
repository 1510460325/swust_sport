package cn.wzy.sport.service;

import cn.wzy.sport.entity.Sport_Info;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by Wzy
 * on 2018/7/20 15:41
 * 不短不长八字刚好
 */
public interface Sport_InfoService {
    /**
     * 查询运动模块
     * @param sport_info
     * @return
     */
    List<Sport_Info> querySports(Sport_Info sport_info);

    /**
     * 更新运动模块
     * @param request
     * @param sport_info
     * @param file
     * @return
     */
    Integer update(HttpServletRequest request, Sport_Info sport_info, String file);

    /**
     * 删除sport
     * @param id
     * @return
     */
    Integer deleteSport(Integer id);

    Integer insert(HttpServletRequest request, Sport_Info sport_info, String file);
}
