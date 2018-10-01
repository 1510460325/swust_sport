package cn.wzy.sport.timer;

import cn.wzy.sport.dao.RoomDao;
import cn.wzy.sport.service.RoomService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * Create by Wzy
 * on 2018/7/26 13:49
 * 不短不长八字刚好
 */
@Component
@Log4j
public class RefreshSports extends TimerTask {

    @Autowired
    private RoomService roomService;

    @Override
    public void run() {
        roomService.refreshStatus();
    }
}
