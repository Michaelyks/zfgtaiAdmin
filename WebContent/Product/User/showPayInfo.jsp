<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>

	<head> 
		<meta charset="utf-8" />
		<title>中泰理财 - 利息表</title>
	<!--  	<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/interest.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	--></head>
	</head>
	<body>
		<!-- 弹出层 -->
		<div class="pop">
			<div class="pop_bg filte"></div>
			<div class="pop_c" id="pop_c">
				<div class="pop_title">每笔资金受特别保护，安全可靠，请放心操作<a id="psi_close" class="close">X</a></div>
				<div class="pop_centent">
					<p class="centent_title">信息确定</p>
					<div class="list_head"><img width="31" height="31" src="${pageContext.request.contextPath}/Product/images/login_temp2.png" />${product.title}</div>
					<div class="list_detail">
						<ul class="first_ul">
							<li>投资金额：<span><fmt:formatNumber value="${inv.inMoney * 0.01}" pattern="#.#"/> 元</span></li>
							<li>年化收益：<span><fmt:formatNumber value="${inv.annualEarnings}" pattern="#.#"/> %</span></li>
							<li>总收益：<span><fmt:formatNumber value="${inv.expectEarnings * 0.01}" pattern="#.##"/> 元</span></li>
							<div class="clea"></div>
						</ul>
						<ul>
							<li>投资期限：<span>${product.tzqx}</span> 天</li>
							<li>产品类型：${product.cplx}</li>
							<li>起息日：${product.jxfs}</li>
							<li>还款方式：${product.fxfs}</li>
							<div class="clea"></div>
						</ul>
					</div>
					<p class="centent_title">还付信息表</p>
					<ul class="list_head2">
						<li>你的投资本金：<fmt:formatNumber value="${inv.inMoney * 0.01}" pattern="#.#"/> 元</li>
						<li>年化收益：<fmt:formatNumber value="${inv.annualEarnings}" pattern="#.#"/> %</li>
						<li>起息日：<fmt:formatDate value="${inv.startTime}" pattern="yyyy-MM-dd"/></li>
						<li>项目结束：<fmt:formatDate value="${product.finishTime}" pattern="yyyy-MM-dd"/></li>
					</ul>
					<div class="list_detail list_detail2">					
						<ul>
							<li><i class="clock"></i>回报时间</li>
							<li>归还本金(元)</li>
							<li>投资天数(天)</li>
							<li>获得利息(元)</li>
						</ul>
						<c:forEach items="${listInterest}" var="list">
							<ul>
								<li><a class="red"><fmt:formatDate value="${list.returnTime}" pattern="yyyy-MM-dd"/></a></li>
								<li><a class="red"><fmt:formatNumber value="${list.payMoney * 0.01}" pattern="#.##"/></a></li>
								<li><a class="red">${list.payDay}</a></li>
								<li><a class="red"><fmt:formatNumber value="${list.payInterest * 0.01}" pattern="#.##"/></a></li>
							</ul>
						</c:forEach>
					</div>
					<a class="touz_now" id="confirmInvest">立即投资</a>
					
				</div>
			</div>
		</div>
		<input type="hidden" id="requestMethod" value="${requestMethod}">
		<form method="post" id="confirmForm">
		<input type="hidden" name="inv.copies" value="${inv.copies}">
		<input type="hidden" name="productId" value="${product.id}">
		</form>
		<!-- 弹出层 end-->
		<script>
			$(function(){
				$("#pop_c .close").click(function(){
					$(".pop").css("display","none");
				})
			});
			$("#psi_close").click(function(){
				var list = art.dialog.list;
				for (var i in list) {
					//alert(list[i]);
				    list[i].close();
				};
			});
			$("#confirmInvest").click(function(){
				var href="${pageContext.request.contextPath}/Product/User/confirmInvest!"+$("#requestMethod").val()+".action";
				$("#confirmForm").attr("action",href);
				$("#confirmForm").submit();
			});
		
		</script>
	</body>
</html>