<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品统计</title>
		<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
		<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
</head>
<body>
<div class="center">
<jsp:include page="/Product/Admin/common/head.jsp"/>
			<div class="main">
			<h3 align="center">产品统计</h3>
				<div class="top">
					<dl>
						<dt>已发布产品数量：${productCount}个</dt>
						<dd>其中新手产品：${xsproductCount}个</dd>
						<dd>其中常规产品：${ptproductCount}个</dd>
						<dd>已售罄产品：${ysqproductCount}个</dd>
					</dl>
					<dl>
						<dt>已发布产品金额：￥<fmt:formatNumber value="${totalMoney}" pattern="#,##0.##"/></dt>
						<dd>其中新手产品：￥<fmt:formatNumber value="${xsproductAllMoney}" pattern="#,##0.##"/></dd>
						<dd>其中常规产品：￥<fmt:formatNumber value="${ptproductAllMoney}" pattern="#,##0.##"/></dd>
						<dd>其中未售金额：￥<fmt:formatNumber value="${wsje}" pattern="#,##0.##"/></dd>
					</dl>
					<div class="clea"></div>
				</div>
				<div class="middle">
					<div>
						<span>已提交未审批产品：</span>
						<ul>
						<c:forEach items="${list}"  var="item" varStatus="i">
							<li>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}.${item.title}/<fmt:formatDate value="${item.insertTime}" type="both" pattern="yyyy.MM.dd"/></li>
						</c:forEach>
						</ul>
						<div class="clea"></div>						
					</div>					
				</div>
				<div class="bottom">
					<a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!sendProduct.action">发布常规产品</a><a href="${pageContext.request.contextPath}/Product/Admin/releaseFreshmanProduct!sendProduct.action">发布新手产品</a>
				</div>
			</div>
		</div>	
</body>
</html>