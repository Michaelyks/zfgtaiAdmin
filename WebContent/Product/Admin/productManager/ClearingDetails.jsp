
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>
	<meta charset="utf-8" />
	<title>结算详单</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<%--<script src="${pageContext.request.contextPath}/Product/Admin/product/plugins\My97DatePicker\WdatePicker.js"></script>--%>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css"> 
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
			<div class="main">
			<h3 align="center">结算详单</h3>
				<div class="table">
					<table cellspacing="0" cellpadding="0"  style="  width: 100%;">
						<tbody>
							<tr>
								<td>序号</td>
								<td>用户名</td>
								<td>姓名</td>
								<td>投资本金</td>
								<td>本次结算</td>
								<td>结算类型</td>
								<td>产品名称</td>
								<td>年化收益</td>
								<td>投资劵金额</td>
								<td>加息劵年化收益</td>
								<td>结算日期</td>
							</tr>
						<c:forEach items="${pageUtil.list}"  var="item" varStatus="i">
							<tr>
								<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
								<td><a class="a"  href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item.users.username)}">${myel:jieMiUsername(item.users.username)}</a></td>
								<td>${item.users.usersInfo.realName}
								</td>
								<td>${item.inMoney*0.01}</td>
								<td><fmt:formatNumber value="${(item.payInterest+item.payMoney) * 0.01}" pattern="#,##0.##"/></td>
								<td><c:if test="${item.payMoney <= 0}">付息</c:if>
									<c:if test="${item.payMoney > 0}">还本且付息</c:if>
								</td>
								<td>
									${item.product.title}
								</td>
								<td>${item.product.annualEarnings}</td>
								<td>${item.investors.coupon }</td>
								<td>${item.investors.couponAnnualRate }</td>
								<td>${item.product.clearingTime}</td>
							</tr>
						</c:forEach>
						</tbody>
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
		</div>	
	</body>
</html>