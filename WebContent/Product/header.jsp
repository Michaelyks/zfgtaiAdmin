<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<!-- 顶部 -->
<div class="top">
	<div class="layout">
		<div class="fl">
			<i class="phone"></i>400-806-5993<a class="time">(工作时间：早上9：00-下午18:00)</a>
		</div>
		<c:choose>
			<c:when test="${usersLogin eq null}">
				<div class="fr login_status"><i class="mobile_phone"></i>欢迎来到中泰理财！|<a href="${pageContext.request.contextPath}/Product/login.jsp" id="top_login">登录</a>|<a href="${pageContext.request.contextPath}/Product/registerUser!regist.action" id="top_regist">注册</a></div>
			</c:when>
			<c:otherwise>
				<div class="fr login_status"><i class="mobile_phone"></i>欢迎  <a style=" color:red; display: initial;" href="${pageContext.request.contextPath}/Product/User/userInfo!queryUsersInfoByid.action">${usersLogin.username2}</a> 来到中泰理财！|<a href="${pageContext.request.contextPath}/Product/index!exitLogin.action">退出</a></div>
			</c:otherwise>
		</c:choose>
		<div class="clea"></div>
	</div>
</div>
<!-- 顶部 end-->
	
<!-- 头部 -->
<div class="header">
	<div class="layout" style="z-index:100">
		<div class="fl">
			<a href="${pageContext.request.contextPath}/Product/index!loadProduct.action"><img src="${pageContext.request.contextPath}/Product/images/logo.png"/></a>
		</div>
		<ul class="fl nav" id="nav">
			<li><a id="top_index" href="${pageContext.request.contextPath}/Product/index!loadProduct.action">首页</a></li>
			<li><a id="top_category" href="${pageContext.request.contextPath}/Product/productCategory!loadProduct.action">理财产品</a></li>
			<li><a id="top_account" href="${pageContext.request.contextPath}/Product/User/myAccount!showMyAccount.action">我的账户</a></li>
			<li class="nav_li4">
			<a id="top_aboutUs" href="${pageContext.request.contextPath}/Product/about_us.jsp">关于我们</a>
				<%-- <ul class="index_hidelist">
				<li><a href="${pageContext.request.contextPath}/Product/aboutus_new.jsp#indexNotice">公司简介</a></li>
				<li><a href="${pageContext.request.contextPath}/Product/power.jsp#indexNotice">核心优势</a></li>
				<li><a  href="${pageContext.request.contextPath}/Product/contactus.jsp#indexNotice">联系我们</a></li>
				</ul> --%>
			</li>
		</ul>
		<div class="clea"></div>
	</div>
</div>
<!-- 头部 end-->
