<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 在线支付</title>
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<%-- <link href="${pageContext.request.contextPath}/Product/css/account.css" rel="stylesheet" type="text/css"/> --%>
		<%-- <link href="${pageContext.request.contextPath}/Product/css/recharge.css" rel="stylesheet" type="text/css"/> --%>
		<link href="${pageContext.request.contextPath}/Product/css/recharge_wycz.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		
	</head>
	<body>
		
		<!-- 主内容 -->
		<div class="layout">
		
			<!-- 支付 -->
			<div class="pay_hongbao bank">
				
				<ul class="payway">
					<li class="onway">快捷支付</li>
					<li >网银支付</li>
					<div class="clea"></div>
				</ul>
				<div class="hongbao_cc">
					<div class="add_bank mybank on_bank">
						<p>农业银行</p>
						<p>**** **** **** 8888</p>
					</div>
					<div class="add_bank mybank">
						<p>建设银行</p>
						<p>**** **** **** 6666</p>	
					</div>
					<div class="add_bank">
							<span>添加银行卡</span>
					</div>
					<div class="clea"></div>
				</div>
				<ul class="hongbao_cc">
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_icbc.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_cmbchina.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_abc.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_ccb.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_ceb.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_boc.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_pingan.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_spdb.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_boco.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_bjrcb.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_bccb.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_ecitic.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_post.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_ccb.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_cmbc.jpg"/></li>
					<div class="clea"></div>
				</ul>
			</div>
			<a class="pay_now">确定支付</a>
			<!-- 支付 end -->
		</div>
		<!-- 主内容 end-->
		
		
		

		<script>
			$(function(){
				$(".mybank").click(function(){
					$(".hongbao_cc").find(".chooseimg").remove();
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
				$(".pay_hongbao .hongbao_cc").eq(0).css("display","block");
				$(".pay_hongbao .payway li").click(function(){
					$(".payway").find(".onway").removeClass("onway");
					$(this).addClass("onway");
					var ind = $(this).index();
					$(".pay_hongbao .hongbao_cc").css("display","none");
					$(".pay_hongbao .hongbao_cc").eq(ind).css("display","block");
				});
				$(".pay_hongbao .hongbao_cc li img").click(function(){
					$(".hongbao_cc").find(".on_bank").removeClass("on_bank");
					$(this).addClass("on_bank");
					var dom = '<img src="${pageContext.request.contextPath}/Product/images/bank2_yes.jpg" class="chooseimg"/>';
					$(".hongbao_cc").find(".chooseimg").remove();
					$(dom).insertAfter(this);
				})
			})
		</script>
		
	</body>
</html>