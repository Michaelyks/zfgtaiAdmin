<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
    <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>中泰理财 - 项目详情</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/wapNotice/css/reset.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/wapNotice/css/layout.css">
	<link href="${pageContext.request.contextPath}/wapNotice/css/jquery.circliful.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="finPro_con_listwarp">
		<div class="box box1">
			<p>${product.description}</p>
			<c:if test="${product.description eq '' || product.description eq ' ' || product.description eq null}">
							<div class="product_img" style="font-size: 28px;">暂无描述</div>
						</c:if>
		</div>
	</div>
<!-- 	<div class="finPro_con_btn">
		<div class="left"><a href="#" class="tc">点击立即投资</a></div>
		<div class="count"></div>
	</div> -->



	<!-- 计算收益 -->
	<div class="comp_benefits">
		<div class="conbox">
			<h2 class="tit">计算收益</h2>
			<form action="">
				<ul class="list">
					<li>
						<img class="ico" src="${pageContext.request.contextPath}/wapNotice/images/comp_ben_ico01.png" alt="">
						<div class="right">
							<input type="text" placeholder="请输入理财金额">
						</div>
					</li>
					<li>
						<img class="ico" src="${pageContext.request.contextPath}/wapNotice/images/comp_ben_ico02.png" alt="">
						<div class="right">
							<input type="text" placeholder="请选择日期">
						</div>
					</li>
				</ul>
			</form>
			<div class="sy">
				<span class="r">12.00</span>
				<span class="l">产品预期收益(元)</span>
			</div>
			<a href="javascript:;" class="btn">计 算</a>
		</div>
	</div>


	
</body>
</html>
<script src="${pageContext.request.contextPath}/wapNotice/js/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/wapNotice/js/rem.js"></script>

<script>
$(function(){
	var tag = $('.coupons_tag li');
	var ul = $('.finPro_con_listwarp .box');

	//计算收益
	$('.finPro_con_btn .count').click(function(){
		$('.comp_benefits').fadeIn(200);
	})
	$('.comp_benefits').click(function(){
		$(this).fadeOut(200);
	})
	$('.comp_benefits .conbox').click(function(event){
		event.stopPropagation();
	})
	
})
</script>