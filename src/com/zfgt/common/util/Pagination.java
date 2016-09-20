package com.zfgt.common.util;
import java.io.Serializable;
/**
 * @author 曾礼强
 *@date 2015-4-22
 * 
 */
public class Pagination
	implements Serializable
{

	private static final long serialVersionUID = 0xbb2fe1f19e40a20cL;
	/**
	 * @deprecated Field ORDER_METHOD_ASC is deprecated
	 */
	public static final String ORDER_METHOD_ASC = "ASC";
	/**
	 * @deprecated Field ORDER_METHOD_DESC is deprecated
	 */
	public static final String ORDER_METHOD_DESC = "DESC";
	public static final Integer DEFAULT_NUM_PERPAGE = Integer.valueOf(200);
	public static final Integer DEFAULT_PAGENO = Integer.valueOf(1);
	public static final Integer QUERY_ALL = Integer.valueOf(-1);
	private Integer numPerPage;
	private Integer pageNo;
	/**
	 * @deprecated Field orderField is deprecated
	 */
	private String orderField;
	/**
	 * @deprecated Field orderMethod is deprecated
	 */
	private String orderMethod;

	public Pagination()
	{
		numPerPage = DEFAULT_NUM_PERPAGE;
		pageNo = DEFAULT_PAGENO;
		orderMethod = "ASC";
	}

	public Integer getNumPerPage()
	{
		if (QUERY_ALL == pageNo)
			return Integer.valueOf(1024);
		else
			return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage)
	{
		this.numPerPage = numPerPage;
	}

	public Integer getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(Integer pageNo)
	{
		this.pageNo = pageNo;
	}

	/**
	 * @deprecated Method getOrderField is deprecated
	 */

	public String getOrderField()
	{
		return orderField;
	}

	/**
	 * @deprecated Method setOrderField is deprecated
	 */

	public void setOrderField(String orderField)
	{
		this.orderField = orderField;
	}

	/**
	 * @deprecated Method getOrderMethod is deprecated
	 */

	public String getOrderMethod()
	{
		return orderMethod;
	}

	/**
	 * @deprecated Method setOrderMethod is deprecated
	 */

	public void setOrderMethod(String orderMethod)
	{
		this.orderMethod = orderMethod;
	}

	public Integer getStart()
	{
		if (QUERY_ALL == pageNo)
			return Integer.valueOf(0);
		if (pageNo.intValue() <= 0)
			pageNo = DEFAULT_PAGENO;
		if (numPerPage.intValue() <= 0)
			numPerPage = DEFAULT_NUM_PERPAGE;
		return Integer.valueOf((pageNo.intValue() - 1) * numPerPage.intValue());
	}

	public String toString()
	{
		return (new StringBuilder("Pagination [numPerPage=")).append(numPerPage).append(", pageNo=").append(pageNo).append(", orderField=").append(orderField).append(", orderMethod=").append(orderMethod).append("]").toString();
	}

}
