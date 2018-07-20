package cn.wzy.sport.service;

import cn.wzy.sport.entity.Complain_Info;
import cn.wzy.sport.service.model.ComplainVO;

import java.util.List;

/**
 * Create by Wzy
 * on 2018/7/20 22:03
 * 不短不长八字刚好
 */
public interface Complain_InfoService {
    /**
     * 查看所有的反馈
     * @return
     */
    List<ComplainVO> queryAllComplains();

    /**
     * 删除反馈
     * @param id
     * @return
     */
    Integer deleComplain(Integer id);

}
