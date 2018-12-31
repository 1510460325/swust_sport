package cn.wzy.sport.service;

import cn.wzy.sport.VO.UserMessVO;
import cn.wzy.sport.entity.User_Message;
import cn.wzy.sport.service.model.MessageVO;
import cn.wzy.sport.service.model.User_MessageVO;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.cn.wzy.query.BaseQuery;

import java.util.List;
import java.util.Map;

/**
 * Create by Wzy
 * on 2018/7/21 15:46
 * 不短不长八字刚好
 */
public interface User_MessageService {
    /**
     * 条件查询所有聊天信息
     * @param query
     * @return
     */
    List<User_MessageVO> queryMessage(BaseQuery<User_Message> query);

    /**
     * 关联user查询
     * @param username
     * @param roomId
     * @return
     */
    List<MessageVO> queryByUser(String username, Integer roomId);

    /**
     * 插入聊天
     * @param record
     * @return
     */
    Integer save(User_Message record);


    /**
     * 查询房间聊天记录
     * @param id
     * @return
     */
    List<UserMessVO> queryMessByRoom(Integer id);
}
