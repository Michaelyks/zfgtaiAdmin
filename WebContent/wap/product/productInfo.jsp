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
	<title>中泰理财 - 信息披露</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/wapNotice/css/reset.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/wapNotice/css/layout.css">
	<link href="${pageContext.request.contextPath}/wapNotice/css/jquery.circliful.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="finPro_con_listwarp">
		<div class="box box1">
		<c:forEach items="${infoList}" var="list">
		     <p>&nbsp;</p>
			<img src="${list}" alt="信息披露图片" style="width:100%;"/>
		</c:forEach>
		<c:if test="${infoList eq '' || infoList eq '[]' || infoList eq null}">
				<div class="product_img" style="font-size: 28px;">暂无图片</div>
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
							<input type="text" id="tzje" placeholder="请输入理财金额">
						</div>
					</li>
					<li>
						<img class="ico" src="${pageContext.request.contextPath}/wapNotice/images/comp_ben_ico02.png" alt="">
						<div class="right">
							<input type="text" id="tzqx" placeholder="投资期限(天)">
						</div>
						<input type="hidden" id="nhsy" value="${product.annualEarnings}">
					</li>
				</ul>
			</form>
			<div class="sy">
				<span class="r"><a id="interest">0</a></span>
				<span class="l">产品预期收益(元)</span>
			</div>
			<a id="calc_result" class="btn">计 算</a>
		</div>
	</div>


<script src="${pageContext.request.contextPath}/wapNotice/js/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/wapNotice/js/rem.js"></script>
<script src="${pageContext.request.contextPath}/wapNotice/js/jquery.circliful.min.js"></script>
<script src="${pageContext.request.contextPath}/wapNotice/js/jquery.SuperSlide.2.1.1.js"></script>
<script>
$( document ).ready(function() {
		$('.finPro_con').circliful();
    });
$(function(){
	var tag = $('.coupons_tag li');
	var ul = $('.finPro_con_listwarp .box');

	//计算收益
	$('.finPro_con_btn .count').click(function(){
		$('.comp_benefits').fadeIn(200);
		$("#tzje").val("");
		$("#tzqx").val("");
		$("#interest").html("0");
	}); 
	$('.comp_benefits').click(function(){
		$(this).fadeOut(200);
	});
	$('.comp_benefits .conbox').click(function(event){
		event.stopPropagation();
	});
	$("#calc_result").click(function(){		
		var check =  /^\d+$/;
		var tzje = $("#tzje").val();
		var tzqx = $("#tzqx").val();
		var nhsy = $("#nhsy").val();
		var isOk = true;
		if(!check.test(tzje)){
			//$("#tzje").siblings(".r").html("输入的数必须为正整数");
			$("#interest").html("请输入正整数");
			isOk = false;
		}
		if(!check.test(tzqx)){
			//$("#tzqx").siblings(".r").html("输入的数必须为正整数");
			$("#interest").html("请输入正整数");
			isOk = false;
		}
		var ckeck2 =  /^[0-9]+.?[0-9]*$/;
		if(!ckeck2.test(nhsy)){
			//$("#nhsy").siblings(".r").html("输入的数必须为数字格式");
			$("#interest").html("输入的数必须为正整数");
			isOk = false;
		}
		if(!isOk){
			return false;
		}
		tzje = parseFloat(tzje);
		tzqx = parseFloat(tzqx);
		nhsy = parseFloat(nhsy);
		$("#tzje").val(tzje);
		$("#tzqx").val(tzqx);
		$("#nhsy").val(nhsy);
		var interest = tzje*nhsy*0.01/365*tzqx;
		interest = Math.floor(interest*100)/100;
		$("#interest").text(interest);
		//$("#benXi").text(parseFloat(tzje+interest));
	});
});
</script>		
</body>
</html>
