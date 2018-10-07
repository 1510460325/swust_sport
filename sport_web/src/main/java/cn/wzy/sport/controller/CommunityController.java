package cn.wzy.sport.controller;

import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.entity.User_Message;
import cn.wzy.sport.service.User_InfoService;
import cn.wzy.sport.service.User_MessageService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j;
import org.cn.wzy.util.TokenUtil;
import org.springframework.web.context.WebApplicationContext;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by Wzy
 * on 2018/8/6 13:24
 * 不短不长八字刚好
 */
@ServerEndpoint("/community/{roomId}/{token}")
@Log4j
public class CommunityController {

	private static User_InfoService userService;

	private static User_MessageService service;

	//消息保存队列线程池
	private static final ExecutorService executor = Executors.newFixedThreadPool(5);

	public static void init(WebApplicationContext ctx) {
		service = (User_MessageService) ctx.getBean("user_MessageServiceImpl");
		userService = (User_InfoService) ctx.getBean("user_InfoServiceImpl");
		log.info("**********CommunityController init.**********");
	}

	public static void shutdown() {
		executor.shutdown();
		log.info("**********CommunityController shutdown.**********");
	}

	@Override
	public int hashCode() {
		return (roomId + "-" + userId).hashCode();
	}

	private static final Map<Integer, CopyOnWriteArraySet<CommunityController>> rooms = new HashMap<>();

	private Session session;

	private Integer userId;

	private Integer roomId;

	private User_Info user;

	@OnOpen
	public void onOpen(@PathParam(value = "roomId") Integer roomId, @PathParam(value = "token") String token, Session session) {
		this.session = session;
		this.roomId = roomId;
		Claims claims;
		try {
			claims = TokenUtil.parse(token);
		} catch (ExpiredJwtException e) {
			session.getAsyncRemote().sendText("ExpiredJwtException");
			try {
				session.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			return;
		} catch (JwtException e) {
			session.getAsyncRemote().sendText("JwtException");
			try {
				session.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			return;
		}
		this.userId = claims.get("userId", Integer.class);
		User_Info user_info = userService.queryUserStatus(userId);
		if (user_info == null) {
			session.getAsyncRemote().sendText("UserException");
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if (user_info.getUsStatus() == -1) {
			session.getAsyncRemote().sendText("USER_LOCK");
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		user = user_info;
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

		String info = messageConvertion(message);
		System.out.println(info);
		CopyOnWriteArraySet<CommunityController> friends = rooms.get(roomId);
		if (friends != null) {
			for (CommunityController item : friends) {
				if (item == this) {
					continue;
				}
				item.session.getAsyncRemote().sendText(info);
			}
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		log.info("发生错误" + new Date());
		error.printStackTrace();
	}

	private String messageConvertion(String message) {
		String split = "!@#$%^$#";
		return user.getId() + split + user.getUsNickname() + split + user.getUsImg() + split + message;
	}
}
