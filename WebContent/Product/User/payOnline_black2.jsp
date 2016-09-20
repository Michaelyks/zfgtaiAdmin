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
	</head>

<body>
	<jsp:include page="../header.jsp" />
	<div class="center">
		<div class="top">每笔资金都受到保险公司的承保，安全可靠，请放心操作。</div>
		<div class="bottom">
			<ul class="one_f">
					<c:choose>
						<c:when test="${product.productType eq '1'}">
							<li style="width: 400px;">项目名字：<span class="green"><a style="color:rgb(13, 103, 242);" href="${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?productId=${product.id}" target="_blank">${myel:getSubString(product.title,30)}</a></span></li>
						</c:when>
						<c:otherwise>
							<li style="width: 400px;">项目名字：<span class="green"><a style="color:rgb(13, 103, 242);" href="${pageContext.request.contextPath}/Product/index!showProductDetails.action?productId=${product.id}" target="_blank">${myel:getSubString(product.title,38)}</a></span></li>
						</c:otherwise>
					</c:choose>
				<li>投资本金：<span class="chense"><fmt:formatNumber value="${inv.copies}" pattern="#,###.##"/> 元</span></li>
			</ul>
			<ul class="tow_f">
				<li>使用账户金额<input maxlength="10" id="txt_useAccountMoney" type="text" value="<fmt:formatNumber value="${useAccountMoney * 0.01}" pattern="#.##"/>" />元  可用余额：<span class="chense"><fmt:formatNumber value="${leftMoney * 0.01}" pattern="0.##"/> 元 
				<a style="margin-left: 20px;color:red;" href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action" target="_blank">立即充值</a></span></li>
				<li>
					<c:if test="${product.productType eq '0'}">
					<label><input id="chk_coupon" type="checkbox" checked="checked" style="width:13px;">使用投资券支付;&nbsp;&nbsp;&nbsp;&nbsp;可用余额 <span style="color: red;"><fmt:formatNumber value="${useCoupon * 0.01}" pattern="#.##"/> 元</span></label> 
					<%-- 可用投资券金额：<span class="chense"><fmt:formatNumber value="${useCoupon * 0.01}" pattern="#.##"/></span> --%>
					<div class="tis">
						<img src="../images/wen_01.png" />
						<div class="tis_c">
							<div class="tis_bg">
							</div>
							<span class="tis_wen">投资券用于购买理财产品,不折现</span>
							<div class="triangle"></div>
						</div>
					</div>
					</c:if>
					<c:if test="${product.productType eq '1'}">
					<label><input id="chk_coupon" type="checkbox" checked="checked" disabled="disabled" style="width:13px;">新手投资抵用券  <span style="color: red;"><fmt:formatNumber value="${useCoupon * 0.01}" pattern="#.##"/>元</span></label>
					</c:if>
				</li>
			</ul>
			<!-- <div class="four_f">
				<img src="../images/bank.png" />
			</div> -->
			<div id="div_other_pay" style="display: none;">
			<div class="three_f">还需要支付：<a class="chense"><span id="span_needPay"><fmt:formatNumber value="${needPay * 0.01}" pattern="0.##"/></span>元</a></div>
			<div class="cz_info" style="padding-left:30px;">
                <c:choose>
					<c:when test="${listBankCard ne null}">
					<p>请选择支付的银行卡：</p>
					<c:forEach items="${listBankCard}" var="list" varStatus="index">
						<p style="font-size: 16px;margin-top: 10px;"><label ><input name="pay_card" value="${list.type}" type="radio" ${index.index == 0? 'checked=\'checked\'' : ''}>${list.bankName} **** **** **** ${list.cardLast}</label><br></p>
					</c:forEach>
					</c:when>
					<c:otherwise>
					<p style="font-size:20px;margin: 20px 10px; padding: 2px 5px;">没有已绑定的银行卡&nbsp;&nbsp;&nbsp;&nbsp; <a style="color:red;" id="goToBind" target="_blank" href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action#bind_card" >立即绑定</a></p>
					</c:otherwise>
				</c:choose>
                </div>
             </div>  
		 <div class="five_f">
				<a id="payConfirm" style="cursor: pointer;" class="zhifu">确定支付</a><!-- <span>您的单张银行卡余额不足<a class="chense"><span >999.00</span>元</a> ?<a href="#">立即充值</a></span> -->
		 </div>
			<!-- <ul class="six_f">
				<li>足额担保</li>
				<li>法律保障</li>
			</ul> -->
			<div>
				<img alt="" src="../images/db.jpg">
			</div>
		</div>
	
	</div>
	<form id="payForm" method="post">
		<input type="hidden" name="inv.id" value="${inv.id}" />
		<input type="hidden" name="productId" value="${product.id}" />
		<input type="hidden" name="inv.copies" value="${inv.copies}" />
		<input type="hidden" id="hidInMoney" name="inv.inMoney" value="<fmt:formatNumber value="${useAccountMoney}" pattern="#.##"/>" />
		<input type="hidden" id="hidCoupon" name="inv.coupon" value="0" />
		<input type="hidden" id="hidBankPay" name="bankMoney" value="<fmt:formatNumber value="${needPay<0?0:needPay * 0.01}" pattern="0.##"/>" />
	</form>
<jsp:include page="../footer.jsp" />
<script type="text/javascript">
if(parseFloat("${needPay}")>0){
	$("#div_other_pay").attr("style","display:block;");
}

var isPay = true;
$("#payConfirm").click(function(){
	//alert("准备支付...");
	
	var isCheck = $("#chk_coupon").is(":checked");
	var coupon = 0;
	if(isCheck){
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
			$(this).val(parseFloat(leftMoney));
			//alert("leftMoney:false");
		}
	}
	//重新获取一遍金额;
	accountMoney = parseFloat($(this).val());
	var isCheck = $("#chk_coupon").is(":checked");
	var coupon = 0;
	if(isCheck){
		coupon = parseFloat("${useCoupon * 0.01}");
		if(accountMoney>=copies){
			var temp = parseFloat(copies)-parseFloat(coupon);
			temp = temp<0?0:temp;
			$("#txt_useAccountMoney").val(temp);
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

$("#chk_coupon").click(function(){
	var isCheck = $(this).is(":checked");
	var coupon = 0;
	var accountMoney = $("#txt_useAccountMoney").val();
	var copies = parseFloat("${inv.copies}");
	var leftMoney = parseFloat("${leftMoney * 0.01}");
	if(isCheck){
		coupon = parseFloat("${useCoupon * 0.01}");
		if(accountMoney>=copies){
			var temp = parseFloat(copies)-parseFloat(coupon);
			$("#txt_useAccountMoney").val(temp);
		}
		if(coupon>=copies){
			$("#txt_useAccountMoney").val(0);
		}
	}else{
		if(leftMoney>=copies){
			$("#txt_useAccountMoney").val(copies);
		}else{
			$("#txt_useAccountMoney").val(leftMoney);
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
});

$("#goToBind").click(function(){
	alert("绑定成功后,回来刷新页面即可");
});
</script>
</body>
</html>
