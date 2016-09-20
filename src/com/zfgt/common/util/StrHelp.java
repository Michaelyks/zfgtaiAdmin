package com.zfgt.common.util;

import java.util.List;

public class StrHelp {
	public static String schoolNum="0";

	public static boolean isNull(String name)

	{
		if(name!=null && !"".equals(name) && !"null".equals(name))
			return true;
		else
			return false;
	}
	
	public static String getExamNum(String stuNo)
	{
	
		if(isNull(stuNo) && "1".equals(schoolNum))
		{
			if(stuNo.length()>7)
			{
				return stuNo.substring(0,3)+stuNo.substring(7);
			}else
				return stuNo;
		}
		else
			return stuNo;
	}
	
	public static String getStuNo(String examNo)
	{
		if(isNull(examNo) && "1".equals(schoolNum))
		{
			if(examNo.length()>3)
			{
				return examNo.substring(0,3)+"6040"+examNo.substring(3);
			}else
				return examNo;
		}
		else
			return examNo;
	}
	
	public static void main(String args[])
	{
		//String stuNo="0806040120520";
		String stuNo="080120520";
		System.out.println(getStuNo(stuNo));
	}

	public static boolean isNull(List<String> classId) {
		if(classId!=null && classId.size()>0)
			return true;
		else
			return false;
	}
}
