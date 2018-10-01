package cn.wzy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.cn.wzy.util.PropertiesUtil;
import org.cn.wzy.util.TokenUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by Wzy
 * on 2018/7/18 23:14
 * 不短不长八字刚好
 */

public class JwtTest {

	@Test
	public void test1() {
		Map<String, Object> map = new HashMap<>();
		String secretKey = PropertiesUtil.StringValue("secretKey");
		map.put("asdf", "adsf");
		String token = TokenUtil.tokens(map);
		System.out.println(token);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.replace("ey", "s")).getBody();
		} catch (ExpiredJwtException e) {
			System.out.println("超时.");
		} catch (MalformedJwtException e) {
			System.out.println("jwt 有误.");
		}
		System.out.println(claims);
	}

	@Test
	public void test2() {
		System.out.println(PropertiesUtil.StringValue("IpListener"));
	}
}
