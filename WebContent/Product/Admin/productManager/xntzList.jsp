<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<style type="text/css">
.a {
  color: blue;
  text-decoration: underline;
}
</style>	

<title>虚拟投资列表</title>
</head>
<body>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
		<h1 style="text-align: center;">虚拟投资记录</h1><br>
		<a href="${pageContext.request.contextPath}/Product/Admin/virtualInsRecord!toVirtualIns.action">新增</a>
		<table style="width:1200px;margin-top: 20px;width: 100%;" >
		<tr>
			<td>序号</td>
			<td>虚拟产品名称</td>
			<td>虚拟投资金额(元)</td>
			<td>虚拟投资人数</td>
			<td>制单时间</td>
			<td>制单状态</td>
			<td>制单人</td>
		</tr>
		<tr align="left">
			<td colspan="2">统计</td>
			<td ><fmt:formatNumber value="${virtualIns.inMoney * 0.01}" pattern="#,##0.##"/></td>
			<td colspan="4">${virtualIns.userCount}</td>
		</tr>
		<c:forEach items="${list}" var="item" varStatus="i">
		<tr>
			<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
			<td>${item.product.title}</td>
			<td><fmt:formatNumber value="${item.inMoney * 0.01}" pattern="#,##0.##"/></td>
			<td>${item.userCount}</td>
			<td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${item.statusChina}</td>
			<td>
				${myel:jieMiUsername(item.admin.username)}
			</td>
		</tr>
		</c:forEach>
		
		</table>
		<c:choose>
			<c:when test="${list ne '[]' &&  list ne '' && list ne null}">
				<jsp:include page="/Product/page.jsp" />
			</c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
			  		<img src="../images/no_record.png" />
			  	</div>
			</c:otherwise>
		</c:choose>
	</div>
</div> 

</body>
</html>	