<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
	<meta charset="utf-8"/>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
	<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
</head>

<!-- 顶部 -->
<div class="top">
	<div class="layout">
		<div class="fl">
			<i class="phone"></i>400-806-5993<a class="time">(工作时间：早上9：00-下午18:00)</a>
		</div>
		<c:choose>
			<c:when test="${usersLogin eq null}">
				<div class="fr login_status"><i class="mobile_phone"></i>欢迎来到中泰理财！|<a href="#">登录</a>|<a href="#">注册</a></div>
			</c:when>
			<c:otherwise>
				<div class="fr login_status"><i class="mobile_phone"></i>欢迎</div>
			</c:otherwise>
		</c:choose>
		<div class="clea"></div>
	</div>
</div>
<!-- 顶部 end-->
	
<!-- 头部 -->
<div class="header">
	<div class="layout">
		<div class="fl">
			<a href="#"><img src="${pageContext.request.contextPath}/Product/images/logo.png"/></a>
		</div>
		<ul class="fl nav">
			<li><a id="top_index" href="#" class="on">首页</a></li>
			<li><a id="top_category" href="#">理财产品</a></li>
			<li><a id="top_account" href="#">我的账户</a></li>
			<li><a id="top_aboutUs" href="#">关于我们</a></li>
		</ul>
		<div class="clea"></div>
	</div>
</div>
<!-- 头部 end-->