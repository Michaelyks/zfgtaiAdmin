// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DateUtil.java

package com.zfgt.common.util;

import java.io.PrintStream;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

public class DateUtil
{

	private static Logger log = Logger.getLogger("com/bedp/util/DateUtil");
	private static String defaultDatePattern = null;
	private static String timePattern = "HH:mm";
	public static final String TS_FORMAT = (new StringBuilder(String.valueOf(getDatePattern()))).append(" HH:mm:ss.S").toString();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy��MM��dd��");
	private static final long MILLISECOND_PER_SECOND = 1000L;
	private static final long MILLISECOND_PER_MINUTIE = 60000L;
	private static final long MILLISECOND_PER_HOUR = 0x36ee80L;
	private static final long MILLISECOND_PER_DAY = 0x5265c00L;

	public DateUtil()
	{
	}

	public static Date toSqlDate(java.util.Date date)
	{
		if (date != null)
			return new Date(date.getTime());
		else
			return null;
	}

	public static java.util.Date parseDate(String date)
	{
		java.util.Date ret = null;
		try
		{
			ret = sdf.parse(date);
		}
		catch (ParseException e)
		{
			ret = null;
		}
		return ret;
	}

	public static String formatDate(java.util.Date date)
	{
		String ret = "";
		try
		{
			ret = sdf.format(date);
		}
		catch (Exception e)
		{
			ret = "";
		}
		return ret;
	}

	public static String formatDateCN(java.util.Date date)
	{
		String ret = "";
		try
		{
			ret = sdf5.format(date);
		}
		catch (Exception e)
		{
			ret = "";
		}
		return ret;
	}

	public static String getDateTime()
	{
		return sdf2.format(Calendar.getInstance().getTime());
	
	}

	public static String getDateTime1()
	{
		return sdf4.format(Calendar.getInstance().getTime());
		
	}
	public static String getDateyyyyMMdd()
	{
		return sdf3.format(Calendar.getInstance().getTime());
		
	}

	public static String getDate()
	{
		return sdf.format(Calendar.getInstance().getTime());
	
	}

	public static String getTime()
	{
		String temp = " ";
		temp = (new StringBuilder(String.valueOf(temp))).append(sdf1.format(Calendar.getInstance().getTime())).toString();
		return temp;
		
	}

	public static String getStartDate()
	{
		return (new StringBuilder(String.valueOf(getYear()))).append("-01-01").toString();
		
	}

	public static String getEndDate()
	{
		return getDate();
		
	}

	public static String getYear()
	{
		return String.valueOf(Calendar.getInstance().get(1));
		
		
	}

	public static String getOldYear()
	{
		return String.valueOf(Calendar.getInstance().get(1) - 1);
		
		
	}

	public static String getMonth()
	{
		DecimalFormat df;
		df = new DecimalFormat();
		df.applyPattern("00;00");
		return df.format(Calendar.getInstance().get(2) + 1);
		
		
	}

	public static String getDay()
	{
		return String.valueOf(Calendar.getInstance().get(5));
		
		
	}

	public static long getSubMinuteMargin(java.util.Date date1, java.util.Date date2)
	{
		long date1Millis = date1.getTime();
		long date2Millis = date2.getTime();
		long l = date1Millis - date2Millis;
		long margin = l / 60000L;
		return margin;
	}


	public static int getMargin(String date1, String date2)
	{
		int margin;
		ParsePosition pos = new ParsePosition(0);
		ParsePosition pos1 = new ParsePosition(0);
		java.util.Date dt1 = sdf.parse(date1, pos);
		java.util.Date dt2 = sdf.parse(date2, pos1);
		long l = dt1.getTime() - dt2.getTime();
		margin = (int)(l / 0x5265c00L);
		return margin;
	
	}

	public static long getQuot(String time1, String time2)
	{
		long quot = 0L;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			java.util.Date date1 = ft.parse(time1);
			java.util.Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000L / 60L / 60L / 24L;
		}
		catch (ParseException e)
		{
			log.error("操作异常: ",e);
		}
		return quot;
	}

	public static int getMonthMargin(String date1, String date2)
	{
		int margin;
		margin = (Integer.parseInt(date2.substring(0, 4)) - Integer.parseInt(date1.substring(0, 4))) * 12;
		margin += Integer.parseInt(date2.substring(4, 7).replaceAll("-0", "-")) - Integer.parseInt(date1.substring(4, 7).replaceAll("-0", "-"));
		return margin;
		
	}

	public static int getYearMargin(String date1, String date2)
	{
		int margin;
		margin = getMonthMargin(date1, date2) / 12;
		if (getMonthMargin(date1, date2) % 12 != 0)
			margin++;
		return margin;
		
	}

	public static String addDay(String date, int i)
	{
		GregorianCalendar gCal;
		gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));
		gCal.add(5, i);
		return sdf.format(gCal.getTime());
		
		
	}

	public static String addMonth(String date, int i)
	{
		GregorianCalendar gCal;
		gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));
		gCal.add(2, i);
		return sdf.format(gCal.getTime());
	
	
	}

	public static String addYear(String date, int i)
	{
		GregorianCalendar gCal;
		gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));
		gCal.add(1, i);
		return sdf.format(gCal.getTime());
		
	}

	public static int getMaxDay(String year, String month)
	{
		int day = 0;
		int iyear = Integer.parseInt(year);
		int imonth = Integer.parseInt(month);
		if (imonth == 1 || imonth == 3 || imonth == 5 || imonth == 7 || imonth == 8 || imonth == 10 || imonth == 12)
			day = 31;
		else
		if (imonth == 4 || imonth == 6 || imonth == 9 || imonth == 11)
			day = 30;
		else
		if (iyear % 4 == 0 && iyear % 100 != 0 || iyear % 400 == 0)
			day = 29;
		else
			day = 28;
		return day;
		
	}

	public String rollDate(String orgDate, int Type, int Span)
	{
		String temp;
		char seperater;
		temp = "";
		int iPos = 0;
		seperater = '-';
		if (orgDate == null || orgDate.length() < 6)
			return "";
		iPos = orgDate.indexOf(seperater);
		int iyear;
		if (iPos > 0)
		{
			iyear = Integer.parseInt(orgDate.substring(0, iPos));
			temp = orgDate.substring(iPos + 1);
		} else
		{
			iyear = Integer.parseInt(orgDate.substring(0, 4));
			temp = orgDate.substring(4);
		}
		iPos = temp.indexOf(seperater);
		int imonth;
		if (iPos > 0)
		{
			imonth = Integer.parseInt(temp.substring(0, iPos));
			temp = temp.substring(iPos + 1);
		} else
		{
			imonth = Integer.parseInt(temp.substring(0, 2));
			temp = temp.substring(2);
		}
		if (--imonth < 0 || imonth > 11)
			imonth = 0;
		int iday = Integer.parseInt(temp);
		if (iday < 1 || iday > 31)
			iday = 1;
		Calendar orgcale = Calendar.getInstance();
		orgcale.set(iyear, imonth, iday);
		temp = rollDate(orgcale, Type, Span);
		return temp;
	
	}

	public static String rollDate(Calendar cal, int Type, int Span)
	{
		String temp;
		temp = "";
		Calendar rolcale = cal;
		rolcale.add(Type, Span);
		temp = sdf.format(rolcale.getTime());
		return temp;
	
	}

	public static synchronized String getDatePattern()
	{
		defaultDatePattern = "yyyy-MM-dd";
		return defaultDatePattern;
	}

	public static final String getDate(java.util.Date aDate)
	{
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null)
		{
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}
		return returnValue;
	}

	public static String getTimeNow(java.util.Date theTime)
	{
		return getDateTime(timePattern, theTime);
	}

	public static Calendar getToday()
		throws ParseException
	{
		java.util.Date today = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	public static final String getDateTime(String aMask, java.util.Date aDate)
	{
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate == null)
		{
			log.error("aDate is null!");
		} else
		{
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		return returnValue;
	}

	public static final String convertDateToString(java.util.Date aDate)
	{
		return getDateTime(getDatePattern(), aDate);
	}

	public static final String convertDateToFullString(java.util.Date aDate)
	{
		return getDateTime("yyyy-MM-dd HH:mm:ss", aDate);
	}

	public static final java.util.Date convertStringToDate(String aMask, String strDate)
		throws ParseException
	{
		SimpleDateFormat df = null;
		java.util.Date date = null;
		df = new SimpleDateFormat(aMask);
		if (log.isDebugEnabled())
			log.debug((new StringBuilder("converting '")).append(strDate).append("' to date with mask '").append(aMask).append("'").toString());
		try
		{
			date = df.parse(strDate);
		}
		catch (ParseException pe)
		{
			log.error((new StringBuilder("ParseException: ")).append(pe).toString());
			throw pe;
		}
		return date;
	}

	public static java.util.Date convertStringToDate(String strDate)
		throws ParseException
	{
		java.util.Date aDate = null;
		try
		{
			if (log.isDebugEnabled())
				log.debug((new StringBuilder("converting date with pattern: ")).append(getDatePattern()).toString());
			aDate = convertStringToDate(getDatePattern(), strDate);
		}
		catch (ParseException pe)
		{
			log.error((new StringBuilder("Could not convert '")).append(strDate).append("' to a date, throwing exception").toString());
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return aDate;
	}

	public static String getSimpleDateFormat()
	{
		SimpleDateFormat formatter = new SimpleDateFormat();
		String NDateTime = formatter.format(new java.util.Date());
		return NDateTime;
	}

	public static java.util.Date addStartTime(java.util.Date param)
	{
		java.util.Date date = param;
		date.setDate(date.getDate() - 1);
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		return date;
		
	}

	public static java.util.Date addEndTime(java.util.Date param)
	{
		java.util.Date date = param;
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(0);
		return date;
		
	}

	public static String getMonthLastDay(int month)
	{
		java.util.Date date = new java.util.Date();
		int day[][] = {
			{
				0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 
				31, 30, 31
			}, {
				0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 
				31, 30, 31
			}
		};
		int year = date.getYear() + 1900;
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
			return (new StringBuilder(String.valueOf(day[1][month]))).toString();
		else
			return (new StringBuilder(String.valueOf(day[0][month]))).toString();
	}

	public static String getMonthLastDay(int year, int month)
	{
		int day[][] = {
			{
				0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 
				31, 30, 31
			}, {
				0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 
				31, 30, 31
			}
		};
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
			return (new StringBuilder(String.valueOf(day[1][month]))).toString();
		else
			return (new StringBuilder(String.valueOf(day[0][month]))).toString();
	}

	public static String getTimestamp()
	{
		java.util.Date date = new java.util.Date();
		String timestamp = (new StringBuilder()).append(date.getYear() + 1900).append(date.getMonth()).append(date.getDate()).append(date.getMinutes()).append(date.getSeconds()).append(date.getTime()).toString();
		return timestamp;
	}

	public static String getTimestamp(java.util.Date date)
	{
		String timestamp = (new StringBuilder()).append(date.getYear() + 1900).append(date.getMonth()).append(date.getDate()).append(date.getMinutes()).append(date.getSeconds()).append(date.getTime()).toString();
		return timestamp;
	}

	public static java.util.Date getDateTimes()
	{
		return Calendar.getInstance().getTime();
		
	}

	public static void main(String args[])
	{
		System.out.println(getDateTime());
	}

}
