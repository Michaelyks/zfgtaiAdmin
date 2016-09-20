<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon"/>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/wecome.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<s:action name="platfromInversors!getPlatformInfo" namespace="/Product/Admin" />
<title>欢迎</title>
</head>
<body>
	<div class="center">
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center" >
			<div class="wecome">			       
					<p>你好,${myel:jieMiUsername(usersAdmin.username)} </p>
					<div>平台发布总额(元)：<span><fmt:formatNumber value="${myPlatform.totalMoney * 0.01}" pattern="#,##0" /></span></div>
					<div>平台融资总额(元)：<span><fmt:formatNumber value="${myPlatform.collectMoney * 0.01}" pattern="#,##0" /></span></div>
					<div>平台收益总额(元)：<span><fmt:formatNumber value="${myPlatform.totalProfit * 0.01}" pattern="#,##0.##" /></span></div>
					<div>赠送的投资券总金额(元)：<span><fmt:formatNumber value="${myPlatform.totalCoupon * 0.01}" pattern="#,##0.##" /></span></div>
					<div>有效使用投资券的总金额(元)：<span><fmt:formatNumber value="${myPlatform.useCoupon * 0.01}" pattern="#,##0.##" /></span></div>
					<div>新手投资券(元)：<span><fmt:formatNumber value="${myPlatform.freshmanCoupon  * 0.01}" pattern="#,##0.##" /></span></div>
					<div>平台注册人数：<span><fmt:formatNumber value="${myPlatform.registerCount}" pattern="#,##0.##" /></span></div>
					<div>平台虚拟投资金额：<span><fmt:formatNumber value="${myPlatform.virtualMoney*0.01}" pattern="#,##0.##" /></span></div>
					<!-- <span>欢迎登录后台管理系统！</span> -->
				</div>	
	</div>
	</div>
</body>
</html>