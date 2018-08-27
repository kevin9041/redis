package com.movitech;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class TestAPI {
	public static void main(String[] args) 
	{
		Jedis jedis = new Jedis("127.0.0.1",6379);
		//Jedis jedis = new Jedis("192.168.56.102",6379);

		jedis.set("k1","v1");
		jedis.set("k2","v2");
		jedis.set("k3","v3");

		System.out.println(jedis.get("k3"));
		
		Set<String> sets = jedis.keys("*");
		//System.out.println(sets.size());

        sets.stream().forEach(System.out::println);

        //后续请参考脑图，家庭作业，敲一遍......
	}
}
