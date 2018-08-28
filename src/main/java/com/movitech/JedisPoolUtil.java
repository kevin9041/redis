package com.movitech;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//單利模式--類内部成員級別
public class JedisPoolUtil {

	private static JedisPool jedisPool = null;
	
	private JedisPoolUtil(){}
	
	public static JedisPool getJedisPoolInstance() {
		if(null == jedisPool) {
			//懶漢模式的單利模式是綫程不安全的
			synchronized (JedisPoolUtil.class) {
				if(null == jedisPool) {
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					poolConfig.setMaxActive(1000);
					poolConfig.setMaxIdle(32);
					poolConfig.setMaxWait(100*1000);
					poolConfig.setTestOnBorrow(true);

					//jedisPool = new JedisPool(poolConfig,"127.0.0.1",6379);
					jedisPool = new JedisPool(poolConfig,"192.168.56.102",6379);
				}
			}
		}
		return jedisPool;
	}

	public static void release(JedisPool jedisPool,Jedis jedis) {
		if(null != jedis) {
			jedisPool.returnResourceObject(jedis);
		}
	}
	
}
