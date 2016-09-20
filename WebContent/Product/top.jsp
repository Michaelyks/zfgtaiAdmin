<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
</head>
<!-- <html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="UTF-8">
</head> -->
<c:choose>
	<c:when test="${usersLogin ne null}">
		<div class="topbar">
			<div class="wrap">
				<div class="phone_num"><img src="${pageContext.request.contextPath}/Product/images/tel_ico.png"/>400-806-5993<span>(工作时间：早上9：00-下午18:00)</span></div>
				<ul>
					<li><a href="${pageContext.request.contextPath}/Product/User/myAccount!showMyAccount.action" class="ico1">进入钱包 <%-- <b><fmt:formatNumber value="${usersLogin.leftMoney * 0.01}" pattern="#.##"/> 元</b> --%></a></li>
					<li><a href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action" class="ico2">充值</a></li>
					<!--已有页面<li><a href="${pageContext.request.contextPath}/Product/User/buyCar!loadInvestors.action" class="ico3">购物车 <b>${productCount}</b></a></li>-->
					<li><a href="${pageContext.request.contextPath}/Product/index!exitLogin.action" class="ico4">退出</a></li>
				</ul>
				<p>
					欢迎您！<a href="${pageContext.request.contextPath}/Product/User/userInfo!queryUsersInfoByid.action">${usersLogin.username}</a>
				</p>
			</div>
		</div>
	</c:when>
	<c:otherwise>
	<div class="topbar">
			<div class="wrap">
				<div class="phone_num"><img src="${pageContext.request.contextPath}/Product/images/tel_ico.png" />400-806-5993<span>(工作时间：早上9：00-下午18:00)</span></div>
				<ul>
					<!-- <li><a href="#" class="ico5">客户端</a></li>
					<li><a href="#" class="ico6">站内信</a></li> -->
					<!--已有页面<li><a href="${pageContext.request.contextPath}/Product/login.jsp" class="ico3">购物车</a></li>-->
					<!-- <li><a href="#" class="ico7">帮助</a></li> -->
					<li><a href="${pageContext.request.contextPath}/Product/index!exitLogin.action" class="ico4">退出</a></li>
				</ul>
			<p>欢迎来到中泰理财！<a href="${pageContext.request.contextPath}/Product/login.jsp">登录</a> | <a href="${pageContext.request.contextPath}/Product/registerUser!regist.action">注册</a></p>
		</div>
		</div>
	</c:otherwise>
</c:choose>
<div class="header">
	<div class="wrap cf">
		<div class="logo">
			<a href="${pageContext.request.contextPath}/Product/index!loadProduct.action"><img
				src="${pageContext.request.contextPath}/Product/images/logo.png" /></a>
		</div>
		<ul class="nav" style="padding-top:8px">
			<li><a id="top_index"
				href="${pageContext.request.contextPath}/Product/index!loadProduct.action" class="on">首页</a></li>
			<li><a id="top_category"
				href="${pageContext.request.contextPath}/Product/productCategory!loadProduct.action">理财产品</a></li>
			<li><a id="top_account"
				href="${pageContext.request.contextPath}/Product/User/myAccount!showMyAccount.action">我的账户</a></li>
			<!--<li><a href="#">帮助中心</a></li>-->
			<li><a id="top_aboutUs" href="${pageContext.request.contextPath}/Product/aboutUs.jsp">关于我们</a></li>
		</ul>
	</div>
</div>

<script type="text/javascript">
	function choosePage(menu) {
		if ("1" == menu) {
			$("#top_index").addClass("on");
			$("#top_category").removeClass("on");
			$("#top_account").removeClass("on");
			$("#top_aboutUs").removeClass("on");
		} else if ("2" == menu) {
			$("#top_index").removeClass("on");
			$("#top_category").addClass("on");
			$("#top_account").removeClass("on");
			$("#top_aboutUs").removeClass("on");
		} else if ("3" == menu) {
			$("#top_index").removeClass("on");
			$("#top_category").removeClass("on");
			$("#top_account").addClass("on");
			$("#top_aboutUs").removeClass("on");
		}else if("4" == menu){
			$("#top_index").removeClass("on");
			$("#top_category").removeClass("on");
			$("#top_account").removeClass("on");
			$("#top_aboutUs").addClass("on");
		}
	}
</script>
<!-- </html> -->