package cn.wzy.service;

import cn.wzy.sport.entity.User_Info;
import cn.wzy.sport.service.User_InfoService;
import cn.wzy.sport.service.User_MessageService;
import cn.wzy.sport.service.model.LoginResult;
import cn.wzy.util.BaseDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by Wzy
 * on 2018/7/14 12:36
 * 不短不长八字刚好
 */
public class User_InfoServiceTest extends BaseDaoTest {

	@Autowired
	private User_InfoService user_infoService;

	@Autowired
	private User_MessageService userMessageService;

	@Test
	public void register() {

	}

	@Test
	public void login() {
		User_Info user_info = new User_Info()
			.setUsName("5120162154")
			.setUsPassword("asdf");
		LoginResult result = user_infoService.login(user_info, "asdf", "1234");
		System.out.println(result);

	}
	@Test
	public void test() {
		System.out.println(userMessageService.queryMessByRoom(1));
	}

}
