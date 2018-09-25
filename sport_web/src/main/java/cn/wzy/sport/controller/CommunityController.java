package cn.wzy.sport.controller;

import cn.wzy.sport.entity.User_Message;
import cn.wzy.sport.service.User_MessageService;
import lombok.extern.log4j.Log4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Create by Wzy
 * on 2018/8/6 13:24
 * 不短不长八字刚好
 */
@ServerEndpoint("/community/{ro_user}")
@Log4j
public class CommunityController {


	private static final User_MessageService service;

	private static final Executor executor = Executors.newFixedThreadPool(5);

	static {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = ((User_MessageService) ctx.getBean("user_MessageServiceImpl"));
	}

	@Override
	public int hashCode() {
		return new String(roomId + "-" + userId).hashCode();
	}

	private static final Map<Integer, CopyOnWriteArraySet<CommunityController>> rooms = new HashMap<>();

	private Session session;

	private Integer userId;

	private Integer roomId;

	@OnOpen
	public void onOpen(@PathParam(value = "ro_user") String ro_user, Session session) {
		this.session = session;
		String[] param = ro_user.split("-");
		this.roomId = Integer.parseInt(param[0]);
		this.userId = Integer.parseInt(param[1]);
		CopyOnWriteArraySet<CommunityController> friends = rooms.get(roomId);
		if (friends == null) {
			synchronized (rooms) {
				if (!rooms.containsKey(roomId)) {
					friends = new CopyOnWriteArraySet<>();
					rooms.put(roomId, friends);
				}
			}
		}
		friends.add(this);
	}

	@OnClose
	public void onClose() {
		CopyOnWriteArraySet<CommunityController> friends = rooms.get(roomId);
		if (friends != null) {
			friends.remove(this);
		}
	}

	@OnMessage
	public void onMessage(final String message, Session session) {
		//新建线程来保存用户聊天信息
		executor.execute(new Runnable() {
			@Override
			public void run() {
				service.save(new User_Message(0, userId, message, roomId, new Date()));
			}
		});

		String info = userId + ":" + message;
		CopyOnWriteArraySet<CommunityController> friends = rooms.get(roomId);
		if (friends != null) {
			for (CommunityController item : friends) {
				if (item == this) {
					continue;
				}
				item.session.getAsyncRemote().sendText(message);
			}
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		log.info("发生错误" + new Date());
		error.printStackTrace();
	}
}
