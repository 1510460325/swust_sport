package cn.wzy.sport.dao.impl;

import cn.wzy.sport.entity.User_Info;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Wzy
 * on 2018/6/4
 */
@Log4j
public class RedisDao {

	private final JedisPool jedisPool;
	private final String pwd;

	public RedisDao(JedisPoolConfig config, String ip, int port, String password) {
		pwd = password;
		jedisPool = new JedisPool(config, ip, port);
	}

	private final RuntimeSchema<User_Info> schema = RuntimeSchema.createFrom(User_Info.class);

	public User_Info getUser(int userId) {
		try {
			Jedis jedis = jedisPool.getResource();
			jedis.auth(pwd);
			try {
				String key = "user:" + userId;
				byte[] bytes = jedis.get(key.getBytes());
				if (bytes != null) {
					User_Info user = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, user, schema);
					return user;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public String putUser(User_Info record) {
		try {
			Jedis jedis = jedisPool.getResource();
			jedis.auth(pwd);
			try {
				String key = "user:" + record.getId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(record, schema
					, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				String result = jedis.setex(key.getBytes(), 3600, bytes);
				return result;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean remove(Integer userId) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(pwd);
		return jedis.del(("user:" + userId).getBytes()) == 1;
	}
}
