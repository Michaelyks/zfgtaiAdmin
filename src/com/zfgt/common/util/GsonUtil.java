package com.zfgt.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

	public static String objectToJson(Object obj) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(obj);
	}

	public static Object jsonToObject(String jsonStr, Object obj) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.fromJson(jsonStr, obj.getClass());
	}
}
