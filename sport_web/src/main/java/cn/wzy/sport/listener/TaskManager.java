package cn.wzy.sport.listener;

import cn.wzy.sport.timer.RefreshSports;
import lombok.extern.log4j.Log4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;

/**
 * Create by Wzy
 * on 2018/7/26 14:42
 * 不短不长八字刚好
 */
@Log4j
public class TaskManager implements ServletContextListener {

    private Timer timer;

    private RefreshSports refreshTask;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        timer = new Timer("refresh rooms", true);
        log.info("refresh room-task start.");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        refreshTask = (RefreshSports) ctx.getBean("refreshSports");
        timer.schedule(refreshTask, 500, 60000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("refresh room-task shutdown.");
        timer.cancel();
    }
}
