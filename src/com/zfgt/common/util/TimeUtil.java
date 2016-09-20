package com.zfgt.common.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**

 * @author 作者 qwy

 * @version 创建时间：2015-04-22 16:52:04

 * 类说明
 * 一些常用的时间方法


 */
public class TimeUtil {
	
	
	/**
	 * 方法说明：
	 * 是nowTime增加add小时 add可以是负数
	 * @param nowTime 需要操作的时间
	 * @param add 增减的小时
	 * @return 操作后的时间
	 */
	public static Date getTimeAddDay(Date nowTime,Long add)
	{
		//if(add>0)
		//{
		Calendar gg=Calendar.getInstance();
		gg.setTime(nowTime);
		gg.add(Calendar.DAY_OF_MONTH, add.intValue());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		System.out.println(format.format(gg.getTime()));
		return gg.getTime();
		//}
		
		//return nowTime;
	}
	
	
	/**
	 * 方法说明：
	 * 是nowTime增加add小时 add可以是负数
	 * @param nowTime 需要操作的时间
	 * @param add 增减的小时
	 * @return 操作后的时间
	 */
	public static Date getTimeAdd(Date nowTime,Long add)
	{
		//if(add>0)
		//{
		Calendar gg=Calendar.getInstance();
		gg.setTime(nowTime);
		gg.add(Calendar.HOUR, add.intValue());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		System.out.println(format.format(gg.getTime()));
		return gg.getTime();
		//}
		
		//return nowTime;
	}
	
	
	/**
	 * 方法说明：
	 * 是nowTime增加addf分钟add可以是负数
	 * @param nowTime 需要操作的时间
	 * @param add 增减的小时
	 * @return 操作后的时间
	 */
	public static Date getTimeAddM(Date nowTime,Long add)
	{
		//if(add>0)
		//{
		Calendar gg=Calendar.getInstance();
		gg.setTime(nowTime);
		gg.add(Calendar.MINUTE, add.intValue());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		System.out.println(format.format(gg.getTime()));
		return gg.getTime();
		//}
		
		//return nowTime;
	}
	
	
	
	
	
	/**
	 * 方法说明：
	 * 取得明天的0点0分0秒
	 * @param nowTime 需要操作的时间
	 * @return 操作后的时间
	 */
	public  static Date getTomorrow(Date nowTime)
	{
		
		Calendar gg=Calendar.getInstance();
		gg.setTime(nowTime);
		gg.add(Calendar.DAY_OF_MONTH, 1);
		gg.set(Calendar.HOUR_OF_DAY, 0);
		gg.set(Calendar.MINUTE, 0);
		gg.set(Calendar.SECOND, 0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		System.out.println(format.format(gg.getTime()));
		return gg.getTime();	
	}
	
	public static void main(String[] args) {
		TimeUtil.getTomorrow(new Date());
	}

	

}
