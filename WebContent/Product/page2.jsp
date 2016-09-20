<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<script type="text/javascript">
	
</script>
<c:if test="${pageUtil.list ne null && pageUtil.list ne '' &&  pageUtil.list ne '[]'}">
<c:if test="${fn:contains(pageUtil.pageUrl,'?') }">
<div class="pageindex2">
		<a href="${pageUtil.pageUrl}&currentPage=${pageUtil.currentPage}&pageSize=${pageUtil.pageSize}${anchor}">${pageUtil.currentPage}</a>/
		<a href="${pageUtil.pageUrl}&currentPage=${pageUtil.pageCount }&pageSize=${pageUtil.pageSize}${anchor}">${pageUtil.pageCount }</a>页 
		<a href="${pageUtil.pageUrl}&currentPage=1&pageSize=${pageUtil.pageSize}${anchor}">首页</a>
		<c:if test="${pageUtil.currentPage<=1}">
				<a style="  color: gray;">上一页</a>
		</c:if>
		<c:if test="${pageUtil.currentPage>1}">
		<a href="${pageUtil.pageUrl}&currentPage=${pageUtil.currentPage-1}&pageSize=${pageUtil.pageSize}${anchor}">上一页</a>
		</c:if>
		
		<c:if test="${pageUtil.currentPage>=pageUtil.pageCount}">
				<a style="  color: gray;">下一页</a>
		</c:if>
		<c:if test="${pageUtil.currentPage<pageUtil.pageCount}">
		<a href="${pageUtil.pageUrl}&currentPage=${pageUtil.currentPage+1}&pageSize=${pageUtil.pageSize}${anchor}">下一页</a>
		</c:if>
		<a href="${pageUtil.pageUrl}&currentPage=${pageUtil.pageCount}&pageSize=${pageUtil.pageSize}${anchor}">末页</a>
</div>
</c:if>

<c:if test="${!fn:contains(pageUtil.pageUrl,'?') }">
<div class="pageindex2">
		<a href="${pageUtil.pageUrl}?currentPage=${pageUtil.currentPage}&pageSize=${pageUtil.pageSize}${anchor}">${pageUtil.currentPage}</a>/
		<a href="${pageUtil.pageUrl}?currentPage=${pageUtil.pageCount }&pageSize=${pageUtil.pageSize}${anchor}">${pageUtil.pageCount }</a>页 
		<a href="${pageUtil.pageUrl}?currentPage=1&pageSize=${pageUtil.pageSize}${anchor}">首页</a>
		
		<c:if test="${pageUtil.currentPage<=1}">
				<a style="  color: gray;">上一页</a>
		</c:if>
		<c:if test="${pageUtil.currentPage>1}">
		<a href="${pageUtil.pageUrl}?currentPage=${pageUtil.currentPage-1}&pageSize=${pageUtil.pageSize}${anchor}">上一页</a>
		</c:if>
		
		<c:if test="${pageUtil.currentPage>=pageUtil.pageCount}">
				<a style="  color: gray;">下一页</a>
		</c:if>
		<c:if test="${pageUtil.currentPage<pageUtil.pageCount}">
		<a href="${pageUtil.pageUrl}?currentPage=${pageUtil.currentPage+1}&pageSize=${pageUtil.pageSize}${anchor}">下一页</a>
		</c:if>
		<a href="${pageUtil.pageUrl}?currentPage=${pageUtil.pageCount}&pageSize=${pageUtil.pageSize}${anchor}">末页</a>
</div>
</c:if>

</c:if>