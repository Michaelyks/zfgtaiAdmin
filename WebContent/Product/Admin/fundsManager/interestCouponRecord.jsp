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
<title>加息券记录</title>
<style type="text/css">
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
</head>
<body>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	
	<div class="main" align="center">
		<h1 style="text-align: center;">加息券记录</h1>
		<form action="${pageContext.request.contextPath}/Product/Admin/sendInterestCoupon!interestCouponRecord.action">
		<label>用户名:<input type="text" name="username" id="username" value="${username}" maxlength="11">
		<span>发送加息券时间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
		<span>使用加息券时间:</span> <input id="useTime" name="useTime" type="text" value="${useTime}"><br>
		状态：<select id="status" name="status">
			<option value="">所有</option>			
			<option value="0">未使用</option>
			<option value="2">已用完</option>
			<option value="3">已过期</option>
		</select>
		<input type="submit" value="search"></label>&nbsp;&nbsp;
		</form>
		<table style="width:100%;margin-top: 20px;text-align: center;">
		<tr>
			<td>序号</td>
			<td>用户名</td>
			<td>加息率</td>
			<td>状态</td>
			<td>发送时间</td>
			<td>到期时间</td>
			<td>使用时间</td>
			<td>备注</td>
		</tr>
		<c:forEach items="${pageUtil.list}" var="item" varStatus="i">
			<tr>
				<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
				<td><a class="a"  href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item.users.username)}">${myel:jieMiUsername(item.users.username)}</a></td> 
				<td><fmt:formatNumber value="${item.interestRate}" pattern="0.00"/>%</td>
				<td>${item.couponStatusChina}</td>
				<td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.overTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.useTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${item.note}</td>
			</tr>
		</c:forEach>
	</table>

	<c:choose>
			<c:when test="${pageUtil.list ne '[]' &&  pageUtil.list ne '' && pageUtil.list ne null}">
			<jsp:include page="/Product/page.jsp" /></c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
			  		<img src="../images/no_record.png" />
			  	</div>
			</c:otherwise>
		</c:choose>
				</div>
</div>
</body>
<script type="text/javascript">
	var k4 = new Kalendae.Input("insertTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
	var k5 = new Kalendae.Input("useTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
	 $(function(){
		$("#status option[value='${status}']").attr("selected",true);
	 });
</script>
</html>