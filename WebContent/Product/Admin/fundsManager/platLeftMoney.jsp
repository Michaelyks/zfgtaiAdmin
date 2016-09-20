<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平台可用余额</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
</head>
<body>
<%-- <jsp:include page="top.jsp" /> --%>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
		<h3>用户可用金额</h3>
	</div>
	<div class="main" align="center">
		<label>今日最大可用余额：</label><label><fmt:formatNumber value="${leftMoney * 0.01}" pattern="#,##0.##"/></label>
	</div>
	<div class="main" align="center">
		<label>零钱包流水总额：</label><label><fmt:formatNumber value="${shiftMoney * 0.01}" pattern="#,##0.##"/></label>
	</div>
	<div class="main" align="center">
		<label>零钱包现存总额：</label><label><fmt:formatNumber value="${inShiftMoney * 0.01}" pattern="#,##0.##"/></label>
	</div>
	<div class="main" align="center">
		<label>零钱包转出总额：</label><label><fmt:formatNumber value="${outShiftMoney * 0.01}" pattern="#,##0.##"/></label>
	</div>
</div>
</body>
</html>