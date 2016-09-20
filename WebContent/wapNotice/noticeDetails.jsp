
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	<title>最新动态 - 详情</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title></title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/wapNotice/css/reset.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/wapNotice/css/layout.css">
	<style type="text/css">
	.newsDetail p span{
	    display: initial;
    	margin: 1rem 0 0 0;
	}
	</style>
</head>
<body style="background: #fff;" >
<c:if test="type != '1' ">
	<header><a href="javascript:history.go(-1);" class="header_l"><img src="${pageContext.request.contextPath}/wapNotice/images/return_icon.png"/></a>最新动态详情
	</header>
</c:if>
<section class="newsDetail">
    <h1>${notice.title}</h1>
    <h2><fmt:formatDate value="${notice.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></h2>
     <div style="width:100%;">${notice.content}</div>
</section>
</body>
<script src="${pageContext.request.contextPath}/wapNotice/js/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/wapNotice/js/rem.js"></script>
</html>
