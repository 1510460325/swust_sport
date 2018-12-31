package cn.wzy.sport.service.impl;

import cn.wzy.sport.VO.UserMessVO;
import cn.wzy.sport.dao.User_InfoDao;
import cn.wzy.sport.dao.User_MessageDao;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.entity.User_Message;
import cn.wzy.sport.service.User_MessageService;
import cn.wzy.sport.service.model.MessageVO;
import cn.wzy.sport.service.model.User_MessageVO;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Create by Wzy
 * on 2018/7/21 15:53
 * 不短不长八字刚好
 */
@Service
public class User_MessageServiceImpl implements User_MessageService {

    @Autowired
    private User_MessageDao user_messageDao;

    @Autowired
    private User_InfoDao userInfoDao;

    @Override
    public List<MessageVO> queryByUser(String username, Integer roomId) {
        Integer userId = null;
        if (username == null)
            userId = null;
        else {
            BaseQuery<User_Info> query = new BaseQuery<>(User_Info.class);
            query.getQuery().setUsName(username);
            List<User_Info> user = userInfoDao.selectByCondition(query);
            if (user != null && user.size() != 0)
                userId = user.get(0).getId();
            else
                return null;
        }
        BaseQuery<User_Message> messageQuery = new BaseQuery<>(User_Message.class);
        messageQuery.getQuery().setUsRoomid(roomId).setUsUserid(userId);
        List<User_Message> list = user_messageDao.selectByCondition(messageQuery);
        if (list == null || list.size() == 0)
            return null;
        Map<Integer,MessageVO> totalMap = new HashMap<>(list.size() / 4);
        for (User_Message message: list) {
            if (totalMap.containsKey(message.getUsRoomid())) {
                MessageVO old = totalMap.get(message.getUsRoomid());
                old.setTotal(old.getTotal() + 1);
            }
            else {
                MessageVO vo = new MessageVO();
                vo.setRoomId(message.getUsRoomid());
                vo.setTotal(1);
                vo.setSendTime(message.getUsSendtime());
                vo.setUsName(userId == null ? "ALL" : username);
                vo.setUserId(userId == null ? -1: message.getUsUserid());
                totalMap.put(vo.getRoomId(),vo);
            }
        }
        return new ArrayList<>(totalMap.values());
    }

    @Override
    public List<User_MessageVO> queryMessage(BaseQuery<User_Message> query) {

        List<User_Message> list = user_messageDao.selectByCondition(query);
        if (list == null || list.size() == 0)
            return null;
        List<Integer> userIds = new ArrayList<>(list.size());
        List<User_MessageVO> result = new ArrayList<>(list.size());
        for (User_Message message: list) {
            userIds.add(message.getUsUserid());
            User_MessageVO record = new User_MessageVO();
            BeanUtils.copyProperties(message,record);
            result.add(record);
        }
        List<User_Info> users = userInfoDao.selectByIds(userIds);
        Map<Integer, User_Info> map = new HashMap<>(users.size());
        for (User_Info userInfo:users) {
            map.put(userInfo.getId(),userInfo);
        }
        for (User_MessageVO record:result) {
            User_Info user = map.get(record.getUsUserid());
            record.setUsName(user.getUsName());
            record.setUsStatus(user.getUsStatus());
        }
        return result;
    }

    @Override
    public Integer save(User_Message record) {
        return user_messageDao.insert(record);
    }

    @Override
    public List<UserMessVO> queryMessByRoom(Integer id) {
        return user_messageDao.queryMessByRoom(id);
    }
}
