<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 在线支付</title>
		.<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/pay_online.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
	</head>
	<body>
		<jsp:include page="../header.jsp"/>
		
		<!-- 标题 -->
		<div class="pay_title">
			<div class="layout">每笔资金受特别保护，安全可靠，请放心操作</div>
		</div>
		<!-- 标题 end-->
		<div class="height18"></div>
		<!-- 主内容 -->
		<div class="layout">
		
			<!-- 账户余额 -->
			<div class="pay_yuer">
				<p>项目名称:<a>房盈宝No.001</a>投资金:<a>100元</a></p>
				<div><span class="pay_jiner"><label>使用账户金额：</label><input type="text"/>元</span>可用余额：<a class="red">0元</a><a href="#" class="cz_now">立即充值</a></div>
			</div>
			<!-- 账户余额 end-->
			
			<div class="height18"></div>
			
			<!-- 账户红包 -->
			<div class="pay_hongbao">
				<p class="touzijuan">可使用投资券<a>2</a>张</p>
				<div class="hongbao_c">
					<div class="hb">
						<p class="fl"><a>投资券总额</a></p>
						<div class="fr">
							<p><span class="big">520</span>元</p>
							<span>适用范围：用于投资抵用</span>
						</div>
						<img class="on_red" src="${pageContext.request.contextPath}/Product/images/choose.png" />
					</div>
					<div class="clea"></div>
				</div>
			</div>
			<!-- 账户红包 end -->
			
			<div class="height18"></div>
			
			<!-- 支付 -->
			<div class="pay_hongbao bank">
				<p class="touzijuan">还需支付<a>80元</a></p>
				<div class="hongbao_c">
				 <c:choose>
					<c:when test="${listBankCard ne null}">
					<c:forEach items="${listBankCard}" var="list" varStatus="index">
						<div class="add_bank mybank on_bank">
						<p>${list.bankName}</p>
						<p>**** **** **** ${list.cardLast}</p>
						</div>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="add_bank" id="div_bind_card">
								<span>添加银行卡</span>
						</div>
					</c:otherwise>
				</c:choose>
					<div class="clea"></div>
				</div>
			</div>
			<a class="pay_now">确定支付</a>
			<!-- 支付 end -->
		</div>
		<!-- 主内容 end-->
		<jsp:include page="../footer.jsp"/>
		<script>
			$(function(){
				$(".mybank").click(function(){
					$(".bank").find(".on_bank").removeClass("on_bank");
					$(this).addClass("on_bank");
				});
				//选择银行结束
				var use_hb = false;
				$(".hb").click(function(){
					if(use_hb){
						$(".on_red").css("display","block");
					}
					else{
						$(".on_red").css("display","none");
					}
					use_hb = !use_hb; 
				});
			});
			$("#div_bind_card").click(function(){
				alert("绑定成功后,回来刷新页面即可");
				window.open("${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action");  
				
			});
		</script>
		
	</body>
</html>
