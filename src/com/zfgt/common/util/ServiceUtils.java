
package com.zfgt.common.util;

/**
 * @author 曾礼强
 *@date 2015-4-22
 * 
 */

import java.util.*;

public class ServiceUtils
{

	private static List supportOrderFieldList = new ArrayList();
	private static Map supportSearchFieldMap = new HashMap();
	private static Map contantMap = new HashMap();
	private static Map contantsMap = new HashMap();

	public ServiceUtils()
	{
	}

	public static List generatePagination(Pagination pagination, List idList)
	{
		Integer count = pagination.getNumPerPage();
		Integer pageNo = pagination.getPageNo();
		if (pageNo == Pagination.QUERY_ALL)
			return idList;
		Integer min = Integer.valueOf(count.intValue() * (pageNo.intValue() - 1));
		Integer max = Integer.valueOf(count.intValue() * pageNo.intValue());
		if (min.intValue() < 0 || max.intValue() < 0)
			return null;
		if (min.intValue() >= idList.size())
			return null;
		if (max.intValue() > idList.size())
			max = Integer.valueOf(idList.size());
		List subIdList = idList.subList(min.intValue(), max.intValue());
		if (subIdList.size() == 0)
			return null;
		else
			return subIdList;
	}

	private static Map convertSupportedSearchKey(Map supportedSearchMap)
	{
		Map map = new HashMap();
		if (supportedSearchMap != null)
		{
			Set keySet = supportedSearchMap.keySet();
			if (keySet != null)
			{
				String key;
				for (Iterator iterator = keySet.iterator(); iterator.hasNext(); map.put(key.toLowerCase(), (Class)supportedSearchMap.get(key)))
					key = (String)iterator.next();

			}
		}
		return map;
	}

	private static List convertSupportedOrderList(List supportedOrderList)
	{
		List orderList = new ArrayList();
		if (supportedOrderList != null)
		{
			String orderKey;
			for (Iterator iterator = supportedOrderList.iterator(); iterator.hasNext(); orderList.add(orderKey.toLowerCase()))
				orderKey = (String)iterator.next();

		}
		return orderList;
	}

	public static List getSupportOrderFieldList()
	{
		return supportOrderFieldList;
	}

	public static void setSupportOrderFieldList(List supportOrderFieldList)
	{
		supportOrderFieldList = supportOrderFieldList;
	}

	public static Map getSupportSearchFieldMap()
	{
		return supportSearchFieldMap;
	}

	public static void setSupportSearchFieldMap(Map supportSearchFieldMap)
	{
		supportSearchFieldMap = supportSearchFieldMap;
	}

	public static void setContantMap(Map contantMap)
	{
		contantMap = contantMap;
	}

	public static void setContantsMap(Map contantsMap)
	{
		contantsMap = contantsMap;
	}

	public static List getConstantListByParentDdId(Integer parentDdId)
	{
		List contantList = (List)contantMap.get(parentDdId);
		return contantList;
	}

	public static String getConstantNameById(Integer parentDdId, Integer ddId)
	{
		List contantList = (List)contantMap.get(parentDdId);
		if (contantList != null)
		{
			for (Iterator iterator = contantList.iterator(); iterator.hasNext();)
			{
				Map contant = (Map)iterator.next();
				if (contant.containsKey(ddId))
					return (String)contant.get(ddId);
			}

		}
		return "";
	}

	public static String getConstantNameByDdId(Integer ddId)
	{
		if (contantsMap.containsKey(ddId))
			return (String)contantsMap.get(ddId);
		else
			return "";
	}

}
