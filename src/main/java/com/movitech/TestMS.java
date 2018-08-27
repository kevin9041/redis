package com.movitech;

import redis.clients.jedis.Jedis;

public class TestMS {
	public static void main(String[] args) {
		Jedis jedis_M = new Jedis("192.168.56.102",6379);
		Jedis jedis_S = new Jedis("192.168.56.102",6380);
		
		jedis_S.slaveof("192.168.56.102",6379);
		
		jedis_M.set("name","weihuanbo");
		
		String result = jedis_S.get("name");
		System.out.println(result);
	}
}
