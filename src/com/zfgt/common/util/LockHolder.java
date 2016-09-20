package com.zfgt.common.util;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**类说明:用户金额操作的同步锁
 * @author qwy
 *
 * @createTime 2015-04-27 10:46:16
 */
public class LockHolder {
	
	private static Map<Integer,	String> locks = new ConcurrentHashMap<Integer, String>(); 
	
	private static Map<Long,	String> locksLong = new ConcurrentHashMap<Long, String>(); 
	
	private static Map<String,	String> locksString = new ConcurrentHashMap<String, String>(); 

	public static Object getLock(int index) 
	{ 
	String lock = locks .get(index); 
	return lock == null?addLock(index):lock; 
	} 
	
	static Object addLock(int index) 
	{ 
	locks .put(index, index+""); 
	return locks .get(index); 
	} 
	
	/**
	 * @param index
	 * @author qwy
	 * @return
	 */
	public static Object getLock(String index) 
	{ 
		String lock = locksString.get(index); 
		return lock == null?addLock(index):lock; 
	} 
	
	/**
	 * @param index 标识符
	 * @return
	 * @author qwy
	 */
	static Object addLock(String index) 
	{ 
		locksString.put(index, index); 
		return locksString.get(index); 
	}
	
	
	/**
	 * @param index 标识符
	 * @author qwy
	 * @return
	 */
	public static Object getLock(long index) 
	{ 
		String lock = locksLong.get(index); 
		return lock == null?addLock(index):lock; 
	} 
	
	/**
	 * @param index 标识符
	 * @return
	 * @author qwy
	 */
	static Object addLock(long index) 
	{ 
		locksLong.put(index, index+""); 
		return locksLong.get(index); 
	} 

}
