package com.zfgt.common.util;

import java.util.List;

/**分页工具类;
 * @author qwy
 *
 * @createTime 2015-4-14下午4:03:21
 */
public class PageUtil<T> {
	public PageUtil(){}
	public PageUtil(int pageSize,int currentPage)
	{
		this.pageSize=pageSize;
		if(currentPage==0)
			currentPage=1;
		this.currentPage=currentPage;
	}
	
	private int pageSize;
	private int currentPage;
	private int pageCount;
	private boolean isLastPage=false;
	private int count;
	private List<T>list;
	private List listCount;
	private String pageUrl;
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public List getListCount() {
		return listCount;
	}
	public void setListCount(List listCount) {
		this.listCount = listCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		if(currentPage==0)
			currentPage=1;
		this.currentPage = currentPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;

        pageCount = count / pageSize + (count%pageSize==0 ? 0 : 1);
        if(pageCount==0)
        	pageCount=1;

        if(count==0) {
 
            if(currentPage!=1)
            	currentPage=1;
             //   throw new IndexOutOfBoundsException("Page index out of range.");

        }

        //else {

            //if(currentPage>pageCount)

//               throw new IndexOutOfBoundsException("Page index out of range.");
            	//currentPage = pageCount;
       // }
	}
	public boolean isLastPage() {
		return isLastPage;
	}
	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
	

}
