package com.movitech;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestKey {
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

	public static void main(String[] args) {
		//del key1[,ke2,...]:删除一个或者多个key，不存在的key可忽略,返回值代表操作成功的记录数
		Long del = jedis.del("testdel1","testdel2");//2

		//dump key:序列化一个key
		//todo

		//exists key:验证key是否存在
		Boolean isExists = jedis.exists("name");//true

		//EXPIRE key 30: 设置key过期时间为 30 秒，以秒为单位
		Long returnValue = jedis.expire("testexpire", 30);//1

		//PEXPIRE key milliseconds：和 EXPIRE 命令的作用类似，但是它以毫秒为单位设置 key 的生存时间
		//todo

		//EXPIREAT key 1355292000:设置UNIX时间戳格式的过期时间
		Long testexpireat = jedis.expireAt("testexpireat", 1565582060);//1

		//PEXPIREAT key milliseconds-timestamp:和 EXPIREAT 命令类似，但它以毫秒为单位设置 key 的过期 unix 时间戳
		//todo

		/**
		 * keys pattern:查找所有符合给定模式pattern的key
		 * KEYS * 匹配数据库中所有 key
		 KEYS h?llo 匹配 hello ， hallo 和 hxllo 等
		 KEYS h*llo 匹配 hllo 和 heeeeello 等
		 KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo
		 */
		Set<String> keys = jedis.keys("*");

		/**
		 *
		 * MIGRATE host port key destination-db timeout [COPY] [REPLACE]：
		 * timeout 参数以毫秒为格式
		 将 key 原子性地从当前实例传送到目标实例的指定数据库上，一旦传送成功， key 保证会出现在目标实例上，而当前实例上的 key 会被删除
		 这个命令是一个原子操作，它在执行的时候会阻塞进行迁移的两个实例，直到以下任意结果发生：迁移成功，迁移失败，等到超时
		 当IOERR发生时，有以下两种情况：
		 key可能存在两个实例中
		 key可能只存在于当前实例
		 即唯一不可能发生的情况是丢失key
		 可选项：
		 copy：不移除源实例上的key
		 replace：替换目标实例上已存在的key
		 */
		//todo

		//move key db：将当前数据库的 key 移动到给定的数据库 db 当中，当目标数据库存在相同的key则移动失败
		Long testmove = jedis.move("testmove", 1);//1

		/**
		 *
		 * OBJECT subcommand key：OBJECT 命令允许从内部察看给定 key 的 Redis 对象
		 REFCOUNT 和 IDLETIME 返回数字
		 ENCODING 返回相应的编码类型
		 */
		String test1Encoding = jedis.objectEncoding("test1");//embstr
		Long objectIdletime = jedis.objectIdletime("test1");//0
		Long objectRefcount = jedis.objectRefcount("test1");//1

		//PERSIST key：移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )
		Long persist = jedis.persist("test1");//0   《---》  redis 操作返回值 1

		//TTL key:以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)
		Long ttl = jedis.ttl("test1");//-1

		//PTTL key ：类似于 TTL 命令，但它以毫秒为单位返回 key 的剩余生存时间
		//todo

		//RANDOMKEY： 从当前数据库中随机返回(不删除)一个 key
		String randomKey = jedis.randomKey();//test9  ..

		/**
		 * RENAME key newkey：将 key 改名为 newkey
		 当 key 和 newkey 相同，或者 key 不存在时，返回一个错误
		 当 newkey 已经存在时， RENAME 命令将覆盖旧值
		 */
		String rename = jedis.rename("test1", "test11");//OK

		//RENAMENX key newkey:当且仅当 newkey 不存在时，将 key 改名为 newkey,当 key 不存在时，返回一个错误
		Long renamenx = jedis.renamenx("test2", "test22");//1

		/**
		 * SORT key [BY pattern] [LIMIT offset count] [GET pattern [GET pattern ...]] [ASC | DESC] [ALPHA] [STORE destination]:
		 保存排序结果(SORT numbers STORE sorted-numbers),可以通过将 SORT 命令的执行结果保存，并用 EXPIRE 为结果设置生存时间，以此来产生一个 SORT 操作的结果缓存。这样就可以避免对 SORT 操作的频繁调用：只有当结果集过期时，才需要再调用一次 SORT 操作,另外，为了正确实现这一用法，你可能需要加锁以避免多个客户端同时进行缓存重建(也就是多个客户端，同一时间进行 SORT 操作，并保存为结果集)，具体参见 SETNX 命令
		 */
		SortingParams sortingParams = new SortingParams();
		sortingParams = sortingParams.desc();
		List<String> list1 = jedis.sort("list1", sortingParams);//sortedlist1
		Long sort2 = jedis.sort("list1",sortingParams,"list2");//8

		//TYPE key:返回 key 所储存的值的类型(none (key不存在),string (字符串),list (列表),set (集合),zset (有序集),hash (哈希表))
		String type = jedis.type("list1");//list

		/**
		 *
		 * SCAN 命令返回的每个元素都是一个数据库键(scan 0 match *a* count 10)
		 SSCAN 命令返回的每个元素都是一个集合成员
		 HSCAN 命令返回的每个元素都是一个键值对，一个键值对由一个键和一个值组成
		 ZSCAN 命令返回的每个元素都是一个有序集合元素，一个有序集合元素由一个成员（member）和一个分值（score）组成
		 */
		//todo

	}


}
