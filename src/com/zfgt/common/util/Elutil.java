
package com.zfgt.common.util;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author 曾礼强
 *@date 2015-4-22
 * 
 */
public class Elutil {
	public Elutil()
	{
	}

	public static String getDdName(Integer str)
	{
		String data_name = ServiceUtils.getConstantNameByDdId(str);
		return data_name;
	}

	public static String getSubString(String str, String length)
		throws UnsupportedEncodingException
	{
		String subString = "";
		int l = Integer.parseInt(length);
		int sbl = str.getBytes().length;
		if (str != null && !str.equals(""))
		{
			str = str.replaceAll("<img.*/>", "");
			if (sbl > l)
			{
				char c[] = str.toCharArray();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < c.length; i++)
				{
					sb = sb.append(c[i]);
					if (sb.toString().getBytes().length > l)
						break;
					subString = sb.toString();
				}

				subString = (new StringBuilder(String.valueOf(subString))).append("...").toString();
			} else
			{
				StringBuffer sb = new StringBuffer(str);
				subString = sb.toString();
			}
		}
		return subString;
	}

	public static String getFormatDate(Date date)
	{
		return DateUtil.formatDate(date);
	}
	
	public static Date getFormatDate(String date) throws Exception
	{
		return QwyUtil.fmyyyyMMdd.parse(date);
	}

	public static void main(String args[])
		throws UnsupportedEncodingException
	{
		String str1 = "a我=爱你中国bbbbbbbb";
		System.out.println(getSubString(str1, "8"));
	}
}
