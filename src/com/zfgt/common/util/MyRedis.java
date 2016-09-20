package com.zfgt.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;

/**
 * 
 * @author 作者: jbb E-mail:jbbdx9900@126.com
 * 
 * @version 创建时间：2012-4-24 上午10:08:18
 * 
 *          类说明: Redis的访问类
 * 
 */
public class MyRedis {
	static Logger log = Logger.getLogger(MyRedis.class);
	private static ShardedJedisPool pool;

	/**
	 * getJedis的链接池对象
	 * 
	 * @param key
	 *            要判断的key
	 * @return
	 */
	private ShardedJedis getJedis() {
		System.out.println("0000");
		try {
			if (pool == null) {
				List<JedisShardInfo> shards = Arrays.asList(new JedisShardInfo(PropertiesUtil.getProperties("redis_ip") + "", 6379)
				// new JedisShardInfo("192.168.0.109",6379)
				/* new JedisShardInfo("115.159.82.101",6379) */
				);
				JedisPoolConfig config = new JedisPoolConfig();
				// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
				// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
				config.setMaxActive(300);
				// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
				config.setMaxIdle(100);
				// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
				config.setMaxWait(1000 * 5);
				// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
				config.setTestOnBorrow(false);

				pool = new ShardedJedisPool(config, shards);
			}

			ShardedJedis jedis = pool.getResource();

			return jedis;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return null;
		}

	}

	/**
	 * 储存某个Key和值并设置超时时间
	 * 
	 * @param key
	 *            存储的key
	 * @param time
	 *            超时时间(秒)
	 * @param value
	 *            存储的值
	 * @return
	 */
	public boolean setex(String Key, int time, String value) {

		if (value == null)
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();

			jedis.setex(Key, time, value);
			// jedis.disconnect();
			return true;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return false;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 储存摸个Key和值
	 * 
	 * @param key
	 *            存储的key
	 * @param time
	 *            超时时间(秒)
	 * @param value
	 *            存储的值
	 * @return
	 */
	public boolean set(String Key, String value) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(Key, value);
			// jedis.disconnect();
			return true;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return false;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 储存摸个Key和map 到一个hash中
	 * 
	 * @param key
	 *            存储的key
	 * @param Map
	 *            存储的值
	 * @return
	 */
	public boolean setHash(String key, Map<String, String> map) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hmset(key, map);
			// jedis.disconnect();
			return true;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return false;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 储存一个对象到Sorted-Sets
	 * 
	 * @param key
	 *            存储的key
	 * @param Map
	 *            存储的值
	 * @return
	 */
	public boolean zadd(String key, double score, String member) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			Long end = jedis.zadd(key, score, member);
			System.out.println("end zadd:" + end);
			// jedis.disconnect();
			return true;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return false;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 排序取出Sorted-Sets 小到大
	 * 
	 * @param key
	 *            存储的key
	 * @param Map
	 *            存储的值
	 * @return
	 */
	public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();

			Set<Tuple> ss = jedis.zrevrangeWithScores(key, start, end);
			// jedis.disconnect();
			return ss;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return null;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 排序取出Sorted-Sets 大到小
	 * 
	 * @param key
	 *            存储的key
	 * @param Map
	 *            存储的值
	 * @return
	 */
	public Set<Tuple> zrangeWithScores(String key, int start, int end) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();

			Set<Tuple> ss = jedis.zrangeWithScores(key, start, end);
			// jedis.disconnect();
			return ss;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return null;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 读取某个key的value
	 * 
	 * @param key
	 *            存储的key
	 * @return
	 */
	public String get(String Key) {
		String end = null;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			end = jedis.get(Key);
			// jedis.disconnect();
			return end;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return null;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 读取某个hmget key的value
	 * 
	 * @param key
	 *            存储的key
	 * @return
	 */
	public String hget(String Key, String Keyin) {
		String end = null;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();

			end = jedis.hget(Key, Keyin);
			// jedis.disconnect();
			return end;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return null;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 从Sorted-Sets 中删除某个值
	 * 
	 * @param key
	 *            Sorted-Sets的key
	 * @param Keyin
	 *            要删除的对象
	 * @return
	 */
	public String zrem(String Key, String Keyin) {

		ShardedJedis jedis = null;
		try {
			jedis = getJedis();

			System.out.println(jedis.zrem(Key, Keyin));
			// jedis.disconnect();
			return "0";
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return "-1";
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 删除某个key
	 * 
	 * @param key
	 *            要判断的key
	 * @return
	 */
	public boolean del(String Key) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(Key);
			// jedis.disconnect();
			return true;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return false;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 判断某个key是否存在
	 * 
	 * @param key
	 *            要判断的key
	 * @return
	 */
	public boolean exists(String key) {
		boolean boo = false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();

			boo = jedis.exists(key);
			// jedis.disconnect();
			return boo;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return false;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 判断某个key的过去时间（秒）
	 * 
	 * @param key
	 *            要判断的key
	 * @return
	 */
	public Long existsttl(String key) {

		ShardedJedis jedis = null;
		try {
			jedis = getJedis();

			Long i = jedis.ttl(key);
			// jedis.disconnect();
			return i;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return -1l;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 储存某个Key和值 以List的形式 并删除游标num以后的元素
	 * 
	 * @param Key
	 *            存储的key
	 * @param value
	 *            存储的值
	 * @param num
	 *            游标值
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean setList(String Key, String value, long num) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();

			jedis.lpush(Key, value);
			// jedis.disconnect();
			// 删除游标num以后的元素
			List end = null;
			end = jedis.lrange(Key, 0, -1);
			if (end.size() > num) {
				jedis.ltrim(Key, 0, num);
				// jedis.disconnect();
			}
			return true;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return false;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 读取某个key的value 以List的形式
	 * 
	 * @param key
	 *            存储的key
	 * @return
	 */
	public List<String> getList(String Key) {
		List<String> end = null;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();

			end = jedis.lrange(Key, 0, -1);
			// jedis.disconnect();
			return end;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return end;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 读取某个key的value 以List的形式
	 * 
	 * @param key
	 *            存储的key
	 * @return
	 */
	public List<String> getList(String Key, int s1, int e1) {
		List<String> end = null;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();

			end = jedis.lrange(Key, s1, e1);
			// jedis.disconnect();
			return end;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return end;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 根据key 删除缓存中某个value值(list)
	 * 
	 * @param Key
	 * @param value
	 * @return
	 */
	public String deleteListValue(String Key, String value) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();

			jedis.lrem(Key, 1, value);
			return null;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return null;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 如果该成员存在，则返回它的位置索引值(从低到高)。否则返回nil。
	 * 
	 * @author 包智名
	 */
	public long getIndexByKeyLowtoHigh(String key, String member) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.zrank(key, member);
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return 0;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 如果该成员存在，则返回它的位置索引值(从高到低)。否则返回nil。
	 * 
	 * @author 包智名
	 */
	public long getIndexByKeyHightoLow(String key, String member) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.zrevrank(key, member);
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return 0;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 返回Sorted-Sets中的成员数量，如果该Key不存在，返回0。
	 * 
	 * @author 包智名
	 */
	public long getSortedSetsCount(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.zcard(key);
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return 0;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	/**
	 * 获取缓存中的map 包智名
	 * 
	 * @param args
	 */
	public Map<String, String> hgetAll(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			Map<String, String> map = jedis.hgetAll(key);

			return map;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			return null;
		} finally {
			if (jedis != null)
				if (pool != null)
					pool.returnResource(jedis);
		}
	}

	public static void main(String[] args) {
		MyRedis yibu = new MyRedis();
		/*
		 * yibu.del("Phone_065848F75DFA28EE5FDDF36DF74F4B0F");
		 * 
		 * //yibu.del("402881114a38fa00014a391c6d6f000b_content_mobile");
		 * HashMap<String,String> map =new HashMap<String,String>();
		 * map.put("name", "test1"); map.put("sex", "女"); map.put("order", "1");
		 * yibu.setHash("test1", map);
		 * 
		 * HashMap<String,String> map2 =new HashMap<String,String>();
		 * map2.put("name", "test2"); map2.put("sex", "女"); map2.put("order",
		 * "2"); yibu.setHash("test2", map2);
		 * 
		 * 
		 * HashMap<String,String> map4 =new HashMap<String,String>();
		 * map4.put("name", "test4"); map4.put("sex", "男"); map4.put("order",
		 * "4"); yibu.setHash("test4", map4);
		 * 
		 * 
		 * HashMap<String,String> map3 =new HashMap<String,String>();
		 * map3.put("name", "test3"); map3.put("sex", "女"); map3.put("order",
		 * "3"); yibu.setHash("test3", map3);
		 * 
		 * 
		 * HashMap<String,String> map5 =new HashMap<String,String>();
		 * map5.put("name", "test5"); map5.put("sex", "男"); map5.put("order",
		 * "5"); yibu.setHash("test5", map5);
		 * 
		 * 
		 * yibu.zadd("testSet", 1, "test1"); yibu.zadd("testSet", 2, "test2");
		 * yibu.zadd("testSet", 3, "test3"); yibu.zadd("testSet", 4, "test4");
		 * yibu.zadd("testSet", 5, "test5"); System.out.println(
		 * "===================插入完成==================================================="
		 * ); System.out.println(
		 * "===================从大到小循环==================================================="
		 * ); Set<Tuple> elements = yibu.zrevrangeWithScores("testSet", 0, -1);
		 * for(Tuple tuple: elements){ System.out.println(tuple.getElement() +
		 * "-" + tuple.getScore()); }
		 * 
		 * yibu.zadd("testSet", 1, "test5"); System.out.println(
		 * "===================从大到小循环完成==================================================="
		 * ); System.out.println(
		 * "===================从小到大循环==================================================="
		 * );
		 * 
		 * Set<Tuple> elements1 = yibu.zrangeWithScores("testSet", 0, -1);
		 * for(Tuple tuple: elements1){ System.out.println(tuple.getElement() +
		 * "-" + tuple.getScore()); System.out.println("性别：" +
		 * yibu.hget(tuple.getElement(), "sex")); } System.out.println(
		 * "===================从小到大循环完成==================================================="
		 * ); System.out.println(
		 * "===================删除test3==================================================="
		 * ); yibu.zrem("testSet","test3"); System.out.println(
		 * "===================删除test3完==================================================="
		 * ); System.out.println(
		 * "===================从小到大循环==================================================="
		 * );
		 * 
		 * Set<Tuple> elements2 = yibu.zrangeWithScores("testSet", 0, -1);
		 * for(Tuple tuple: elements2){ System.out.println(tuple.getElement() +
		 * "-" + tuple.getScore()); System.out.println("性别：" +
		 * yibu.hget(tuple.getElement(), "sex")); } System.out.println(
		 * "===================从小到大循环完成==================================================="
		 * ); yibu.set("qwy", "qwy123456");
		 */
		// yibu.del("qwy");
		yibu.setex("qwy", 120, "1");
		System.out.println(yibu.get("qwy"));
		yibu.setex("qwy", 120, "");
		System.out.println(yibu.get("qwy") + "99");
	}

}
