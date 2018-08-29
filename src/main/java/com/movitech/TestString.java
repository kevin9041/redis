package com.movitech;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class TestString {

	//Jedis jedis = new Jedis("127.0.0.1",6379);
	static Jedis jedis = new Jedis("192.168.56.102",6379);
	static {
		String s = jedis.flushAll();
		//System.out.println(s);//OK

		jedis.set("test1","value1");
		jedis.set("test2","value2");
		jedis.set("test3","value3");
		jedis.set("test4","value4");
		jedis.set("test5","value5");
		jedis.set("test6","value6");
		jedis.set("test7","value7");
		jedis.set("test8","value8");
		jedis.set("test9","value9");

		jedis.set("testdel1","valuedel1");
		jedis.set("testdel2","valuedel2");

		jedis.set("testexpire","valueexpire");
		jedis.set("testexpireat","valueexpireat");

		jedis.set("testmove","valuemove");

		jedis.lpush("list1","22","56","1","59","80","23","66","100");

		Map<String,String> setContent = new HashMap<>();
		setContent.put("name","weihuanbo");
		setContent.put("age","28");
		setContent.put("salary","------");
		jedis.hmset("set1",setContent);
	}

	public static void main(String[] args) 
	{
		//APPEND key value：如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾，如果 key 不存在， APPEND
		Long append = jedis.append("test1", "weihuanbo");//15

		//strlen key:获取key的长度，可以不存在返回0
		Long test1Len = jedis.strlen("test1");//15

		//SETRANGE key start value：可以用于覆盖或修改字符串，对于不存在的key当作空串处理
		Long aLong = jedis.setrange("test1", 6, "bbbbbbbbbbbbbb");//20

		//
	}
}
