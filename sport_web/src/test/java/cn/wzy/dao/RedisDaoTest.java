package cn.wzy.dao;

import cn.wzy.sport.dao.impl.RedisDao;
import cn.wzy.sport.entity.User_Info;
import cn.wzy.util.BaseDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/10/2 9:43
 */
public class RedisDaoTest extends BaseDaoTest {

	@Autowired
	private RedisDao dao;

	@Test
	public void test() {
		System.out.println(dao.putUser(new User_Info().setId(13).setUsName("asdf")));
		System.out.println(dao.getUser(1));
	}
}
