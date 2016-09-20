package com.zfgt.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class ObjectUtil {
	private static DecimalFormat df = new DecimalFormat("0.00");

	public static Map<String, Object> declaredObjectToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Field[] fs = obj.getClass().getDeclaredFields();
			for (Field f : fs) {
				boolean b = f.isAccessible();
				f.setAccessible(true);
				map.put(f.getName(), f.get(obj));
				f.setAccessible(b);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, Object> objectToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Method[] m = obj.getClass().getMethods();

			for (int i = 0; i < m.length; i++) {

				String method = m[i].getName();
				if (method.startsWith("get")) {
					try {
						Object value = m[i].invoke(obj);
						if (value != null) {
							String key = method.substring(3);
							if (!key.equals("class")) {
								key = key.substring(0, 1).toLowerCase() + key.substring(1);
								map.put(key, value + "");
							}
						}
					} catch (Exception e) {
						System.out.println("error:" + method);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static List<Map<String, Object>> objectToList(Object obj) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Method[] m = obj.getClass().getMethods();

			for (int i = 0; i < m.length; i++) {

				String method = m[i].getName();
				if (method.startsWith("get")) {
					try {
						Object value = m[i].invoke(obj);
						if (value != null) {
							String key = method.substring(3);
							key = key.substring(0, 1).toLowerCase() + key.substring(1);
							Map<String, Object> map = new HashMap<String, Object>();
							map.put(key, value);
							map.put("key", key);
							map.put("value", value);
							list.add(map);
						}
					} catch (Exception e) {
						System.out.println("error:" + method);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Object mapToObject(Map<String, Object> map, Object obj)
			throws IllegalAccessException, InvocationTargetException {
		BeanUtils.populate(obj, map);
		return obj;
	}

	public static void accumulationObject(Object obj1, Object obj2)
			throws IllegalArgumentException, IllegalAccessException {
		if (obj1.getClass() != obj2.getClass()) {
			return;
		}
		Field[] fs = obj1.getClass().getDeclaredFields();

		for (Field f : fs) {
			if (f.getType() == int.class || f.getType() == Integer.class) {
				Boolean b = f.isAccessible();
				f.setAccessible(true);
				f.set(obj1, f.getInt(obj1) + f.getInt(obj2));
				f.setAccessible(b);
			} else if (f.getType() == double.class || f.getType() == Double.class) {
				Boolean b = f.isAccessible();
				f.setAccessible(true);
				f.set(obj1, new BigDecimal(f.getDouble(obj1)).add(new BigDecimal(f.getDouble(obj2))).doubleValue());
				f.setAccessible(b);
			} else {
				// f.set(obj1, f.get(obj1));
			}
		}
	}

	public static void addMapStringWithDouble(Map<String, Object> map) {
		if (map == null) {
			return;
		}
		for (String key : map.keySet()) {
			if (map.get(key).getClass() == double.class || map.get(key).getClass() == Double.class) {
				map.put(key + "s", df.format(new BigDecimal((double) map.get(key))));
			}
		}
	}

	public static void changeMapDoubleToString(Map<String, Object> map) {
		if (map == null) {
			return;
		}
		for (String key : map.keySet()) {
			if (map.get(key) == null) {
				continue;
			}
			if (map.get(key).getClass() == double.class || map.get(key).getClass() == Double.class) {
				map.put(key, df.format(new BigDecimal((double) map.get(key))));
			}
		}
	}

	public static void changeMapToString(Map<String, Object> map) {
		if (map == null) {
			return;
		}
		for (String key : map.keySet()) {
			if (map.get(key) == null) {
				continue;
			}
			map.put(key, map.get(key) + "");
		}

	}

	private static String createMethodName(String inStr) {
		StringBuffer sb = new StringBuffer();
		sb.append("set");
		sb.append(inStr.substring(0, 1).toUpperCase());
		sb.append(inStr.substring(1, inStr.length()));
		return sb.toString();
	}

	public static void testPrintAllKeyValue(Map<String, Object> map) {
		for (String key : map.keySet()) {
			System.out.println("key:" + key + "value:" + map.get(key));
		}
	}
}
