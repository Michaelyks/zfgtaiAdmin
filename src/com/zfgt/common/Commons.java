package com.zfgt.common;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;


/**
 * @author Administrator
 * Description:
 */
public class Commons {
	public static final int length=10;
	public static final int listLength=10;
	public static final int samplingListMax=200;
	public static final int samplingMax=5;
	public static final int maxKeyCount=99; 
	public static final String[] picList={"jpg","jpeg","gif","bmp","png"};
	public static final String[] flaList={"flv","wmv","rm","asf","mp3","wma","swf","rmvb","avi","mpg"};
	public static final String[] VoList={"rm","rmvb"};
	public static final int coujian=10;
	
	public static Map<String,Object> mapException(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("end", "error");
		map.put("message", "操作异常");
		return map;
	}
	
	public static Map<String,Object> mapNoData(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("end", "noData");
		map.put("message", "没有数据");
		return map;
	}
	
	public static Map<String,Object> mapParamError(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("end", "paramError");
		map.put("message", "参数错误");
		return map;
	}
	
	public static Map<String,Object> mapNoLogin(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("end", "noLogin");
		map.put("message", "请先登录");
		return map;
	}
	
	public static void main(String[] args) {
		JSONObject json = JSONObject.fromObject(mapException());
		System.out.println(json.toString());
	}
}

