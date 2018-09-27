package cn.wzy.dao;

import cn.wzy.sport.dao.Role_AuthDao;
import cn.wzy.util.BaseDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/27 13:31
 */
public class Role_authDaoTest extends BaseDaoTest {

	@Autowired
	private Role_AuthDao dao;

	@Test
	public void test() {
		System.out.println(dao.records(9,"GET:/verify/getVerify.do"));
	}
}
