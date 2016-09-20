<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<title>中泰理财 - 在线支付</title>
<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/Product/css/public.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/Product/css/account.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/Product/css/account_pay.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/Product/css/cz_bang.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/js/account_pay.js"></script>
<script src="${pageContext.request.contextPath}/Product/js/cz_bang.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<link href="${pageContext.request.contextPath}/Product/css/pay_online.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<!-- 顶部 -->
	<jsp:include page="../header.jsp" />
		<!-- 顶部 end-->
		
		
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
				<p>项目名称:
					<c:choose>
						<c:when test="${product.productType eq '1'}">
						<a href="${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?productId=${product.id}" target="_blank">${myel:getSubString(product.title,30)}</a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath}/Product/index!showProductDetails.action?productId=${product.id}" target="_blank">${myel:getSubString(product.title,38)}</a>
						</c:otherwise>
					</c:choose>
				投资金:<a><fmt:formatNumber value="${inv.copies}" pattern="#,###.##"/> 元</a></p>
				<div><span class="pay_jiner"><label>使用账户金额：</label><input maxlength="10" id="txt_useAccountMoney" type="text" value="<fmt:formatNumber value="${useAccountMoney * 0.01}" pattern="#.##"/>" />元 </span>可用余额：<a class="red"><fmt:formatNumber value="${leftMoney * 0.01}" pattern="0.##"/></a><a href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action" target="_blank" class="cz_now">立即充值</a></div>
			</div>
			<!-- 账户余额 end-->
			
			<div class="height18"></div>
			<c:if test="${product.productType ne '1'}">
			<!-- 账户红包 -->
				<div class="pay_hongbao">
					<p class="touzijuan">可使用投资券</p>
					<div class="hongbao_c">
					<c:if test="${product.productType eq '0'}">
						<div class="hb">
							<p class="fl"><a>投资券总额</a></p>
							<div class="fr">
								<p><span class="red_big"><fmt:formatNumber value="${useCoupon * 0.01}" pattern="#.##"/> </span>元</p>
								<span>适用范围：用于投资抵用</span>
							</div>
							<img class="on_red" src="${pageContext.request.contextPath}/Product/images/choose.png" />
						</div>
					</c:if>
					<c:if test="${product.productType eq '1'}">
						<div class="hb">
							<p class="fl"><a>新手投资券总额</a></p>
							<div class="fr">
								<p><span class="red_big">900</span>元</p>
								<span>适用范围：用于新手产品投资抵用</span>
							</div>
							<img class="on_red" src="${pageContext.request.contextPath}/Product/images/choose.png" />
						</div>
					</c:if>
					<div class="clea"></div>
					</div>
				</div>
			</c:if>
			<!-- 账户红包 end -->
			
			<div class="height18"></div>
			
			<!-- 支付 -->
			<div class="pay_hongbao bank"id="div_other_pay"style="display: none;">
				<p   class="touzijuan">还需支付<a><span id="span_needPay"><fmt:formatNumber value="${needPay * 0.01}" pattern="0.##"/></span></a></p>
				<ul class="payway">
					<li class="onway">快捷支付</li>
					<div class="clea"></div>
				</ul>
				<div class="hongbao_cc">
					<c:choose>
						<c:when test="${listBankCard ne null}">
						<p style="color:red;"></p>
						<c:forEach items="${listBankCard}" var="list" varStatus="index">
							<div class="add_bank mybank on_bank">
								<p>${list.bankName}</p>
								<p>**** **** **** ${list.cardLast}</p>
							</div>
						</c:forEach>
						</c:when>
						<c:otherwise>
							<p>
								您的账户尚未绑定任何银行卡&nbsp;&nbsp;&nbsp;&nbsp;<a id="goToBind" class="red" href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action#bind_card">立即绑定</a>
							</p>
							<div class="add_bank" id="div_bind_card">
								<span>添加银行卡</span>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="clea"></div>
				</div>
				<%-- <ul class="hongbao_cc">
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
				</ul> --%>
			</div>
			<a id="payConfirm" class="pay_now">确定支付</a>
			<!-- 支付 end -->
		</div>
		<!-- 主内容 end-->
		
		
		<!-- 底部 -->
			<jsp:include page="../footer.jsp" />
		<!-- 底部 end-->
	<form id="payForm" method="post">
		<input type="hidden" name="inv.id" value="${inv.id}" />
		<input type="hidden" name="productId" value="${product.id}" />
		<input type="hidden" name="inv.copies" value="${inv.copies}" />
		<input type="hidden" id="hidInMoney" name="inv.inMoney" value="<fmt:formatNumber value="${useAccountMoney}" pattern="#.##"/>" />
		<input type="hidden" id="hidCoupon" name="inv.coupon" value="0" />
		<input type="hidden" id="hidBankPay" name="bankMoney" value="<fmt:formatNumber value="${needPay<0?0:needPay * 0.01}" pattern="0.##"/>" />
	</form>
	<script>
		if(parseFloat("${needPay}")>0){
			$("#div_other_pay").attr("style","display:block;");
		}
		//是否使用红包
		var use_hb = false;
		$(function(){
			$(".mybank").click(function(){
				$(".hongbao_cc").find(".chooseimg").remove();
				$(".bank").find(".on_bank").removeClass("on_bank");
				$(this).addClass("on_bank");
			});


			$(".hb").click(function(){
				var type='${product.productType}';
				if(type == '1'){
					return false;
				}
				if(use_hb){
					$(".on_red").css("display","block");
				}
				else{
					$(".on_red").css("display","none");
				}
				var coupon = 0;
				var accountMoney = $("#txt_useAccountMoney").val();
				var copies = parseFloat("${inv.copies}");
				var leftMoney = parseFloat("${leftMoney * 0.01}");
				if(use_hb){
					coupon = parseFloat("${useCoupon * 0.01}");
					if(accountMoney>=copies){
						var temp = parseFloat(copies)-parseFloat(coupon);
						$("#txt_useAccountMoney").val(temp.toFixed(2));
					}
					if(coupon>=copies){
						$("#txt_useAccountMoney").val(0);
					}
				}else{
					if(leftMoney>=copies){
						$("#txt_useAccountMoney").val(copies);
					}else{
						$("#txt_useAccountMoney").val(leftMoney.toFixed(2));
					}
				}
				accountMoney = $("#txt_useAccountMoney").val();
				//自己支付的金额+投资券;
				var totalMoney = parseFloat(accountMoney)+parseFloat(coupon);
				if(totalMoney<copies){
					//显示在线支付
					$("#span_needPay").text((parseFloat(copies)-parseFloat(totalMoney)).toFixed(2));
					$("#div_other_pay").attr("style","display:block;");
				}else{
					$("#span_needPay").text(0);
					$("#div_other_pay").attr("style","display:none;");
					
				}
				$("#hidBankPay").val($("#span_needPay").text());
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
		});
		var isPay = true;
		$("#payConfirm").click(function(){
			var coupon = 0;
			if(!use_hb){
				coupon = parseFloat("${useCoupon}");
			}
			$("#hidInMoney").val($("#txt_useAccountMoney").val()*100);
			$("#hidCoupon").val(coupon);
			var url = "${pageContext.request.contextPath}/Product/User/confirmInvest!confirmPay.action";
			var su="";
			if(isPay)
				isPay = false;
			var text = "<img src='${pageContext.request.contextPath}/Product/images/progress.gif' width='24' height='24'> <span style='font-family:楷体;font-size:22px;'>正在支付,请稍等...</span>";
			var id = chuangkou(text,"正在支付...","",0,true);
			$.ajax({
				type:"post",
				url:url,
				async:true,
				data:$("#payForm").serialize(),
				beforeSend:function(XMLHttpRequest){
					
				},
				success:function(data){
					su = data;
					//alert(data.json);
				},
				complete:function(XMLHttpRequest,textStatus){
					closeAlert(id);
					jingao(su.json,"提示",function(){
						if(su.status=="ok"){
							//跳转到我的帐号;
							window.location.href="${pageContext.request.contextPath}/Product/User/myAccount!showMyAccount.action";
						}
					},0);
					isPay = true;
				}
			});
		});

		$("#txt_useAccountMoney").blur(function(){
			var accountMoney = parseFloat($(this).val());
			//alert("离开钱"+accountMoney);
			if(isNaN(accountMoney)){
				$(this).val(0);	
				accountMoney = 0;
			}else{
				accountMoney = accountMoney.toFixed(2);
				//alert("离开后"+accountMoney);
				$(this).val(parseFloat(accountMoney));
			}
			var leftMoney = parseFloat("${leftMoney * 0.01}");
			//alert("leftMoney"+leftMoney);
			var copies = parseFloat("${inv.copies}");
			if(accountMoney>leftMoney){
				$(this).val(parseFloat(leftMoney));	
				if(leftMoney>copies){
					$(this).val(parseFloat(copies));	
					//alert("leftMoney:true");
				}else{
					$(this).val(parseFloat(leftMoney).toFixed(2));
					//alert("leftMoney:false");
				}
			}
			//重新获取一遍金额;
			accountMoney = parseFloat($(this).val());
			var coupon = 0;
			if(!use_hb){
				coupon = parseFloat("${useCoupon * 0.01}");
				if(accountMoney>=copies){
					var temp = parseFloat(copies)-parseFloat(coupon);
					temp = temp<0?0:temp;
					$("#txt_useAccountMoney").val(temp.toFixed(2));
				}
			}else{
				if(accountMoney>=copies){
					$("#txt_useAccountMoney").val(copies);
				}
			}
			//自己支付的金额+投资券;
			var totalMoney = parseFloat(accountMoney)+parseFloat(coupon);
			//alert(totalMoney);
			if(totalMoney<copies){
				//显示在线支付
				$("#span_needPay").text((parseFloat(copies)-parseFloat(totalMoney)).toFixed(2));
				$("#div_other_pay").attr("style","display:block;");
			}else{
				$("#span_needPay").text(0);
				$("#div_other_pay").attr("style","display:none;");
			}
			$("#hidBankPay").val($("#span_needPay").text());
		});
		
		$("#goToBind").click(function(){
			alert("绑定成功后,回来刷新页面即可");
		});
	</script>
</body>
</html>