package cn.wzy.sport.listener;

import cn.wzy.sport.controller.CommunityController;
import cn.wzy.sport.service.RoomService;
import lombok.extern.log4j.Log4j;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by Wzy
 * on 2018/7/26 14:42
 * 不短不长八字刚好
 */
@Log4j
public class InitialListener implements ServletContextListener {

	private ScheduledThreadPoolExecutor service;
	private RoomService roomService;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		service = ((ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1));
		log.info("**********refresh room-task start**********");
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
		roomService = ctx.getBean(RoomService.class);
		service.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				roomService.refreshStatus();
			}
		}, 0, 8, TimeUnit.SECONDS);
		CommunityController.init(ctx);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		service.shutdown();
		log.info("**********refresh room-task shutdown**********");
		CommunityController.shutdown();
	}
}
