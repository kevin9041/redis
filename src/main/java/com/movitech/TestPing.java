package com.movitech;

import redis.clients.jedis.Jedis;

public class TestPing {
	public static void main(String[] args) 
	{
		Jedis jedis = new Jedis("127.0.0.1",6379);
		//Jedis jedis = new Jedis("192.168.56.102",6379);
		System.out.println(jedis.ping());
	}
}
