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
				<div><input maxlength="10" id="txt_useAccountMoney" type="hidden" value="<fmt:formatNumber value="${useAccountMoney * 0.01}" pattern="0.00"/>" readonly />可用余额：<a class="red"><fmt:formatNumber value="${leftMoney * 0.01}" pattern="0.##"/>元</a><a href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action" target="_blank" class="cz_now">立即充值</a></div>
			</div>
			<!-- 账户余额 end-->
			<div class="height18"></div>
			<c:if test="${product.productType eq '0'}">
			<!-- 账户红包 -->
				<c:if test="${listCoupon ne null && listCoupon ne '' && listCoupon ne '[]'}"> 
				<div class="pay_hongbao">
					<p class="touzijuan">可使用投资券</p>
					<div class="hongbao_c">
					<c:if test="${product.productType eq '0'}">
						<c:forEach items="${listCoupon}" var="list" varStatus="index">
							<div class="hb mr30">
								<p class="fl"><a>活动投资券</a></p>
								<div class="fr">
									<p class="hb_touziquan"><span data-ticket="${list.id }" class="red_big"><fmt:formatNumber value="${list.initMoney * 0.01}" pattern="#.##"/></span>元</p>
									<span>适用范围：用于投资抵用</span>
									<p class="hb_time" title="<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">获得时间：<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd"/></p>
									<p class="hb_time">到期时间：<c:if test="${list.overTime eq null}">永久有效</c:if><fmt:formatDate value="${list.overTime}" pattern="yyyy-MM-dd"/></p>
								</div>
								<img class="on_red" src="/wgtz/Product/images/choose.png">
							</div>
						</c:forEach>
					</c:if>
<%-- 					<c:if test="${product.productType eq '1'}"> 
						<div class="hb">
							<p class="fl"><a>新手投资券总额</a></p>
							<div class="fr">
								<p><span class="red_big">900</span>元</p>
								<span>适用范围：用于新手产品投资抵用</span>
							</div>
							<img class="on_red" src="${pageContext.request.contextPath}/Product/images/choose.png" />
						</div>
					</c:if> --%>
					<div class="clea"></div>
					</div>
				</div>
				</c:if>
			</c:if>
			<!-- 账户红包 end -->
 			
			<div class="height18"></div>
			
			<!-- 支付 -->
			<div class="pay_hongbao bank"id="div_other_pay" >
				<p   class="touzijuan">还需支付<a><span id="span_needPay"><fmt:formatNumber value="${inv.copies}" pattern="0.##"/></span>元</a></p>
			</div>	
<%--				<ul class="payway">
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
			</div> --%>
			<!-- 支付 end -->
			<div class="height18"></div>
			
			<!-- 支付密码  -->
			<c:choose>
			 <c:when test="${isPwd eq 'ok' }">
			   <div class="pay_yuer" id='pwdpay' style="border:1px solid #e6e6e6;">
				<p><a style=" font-size:14px;">为了您的资金安全，请输入支付密码以完成交易<a></p>
				<div>
				<span class="pay_jiner"><label>支付密码：</label>
				<input type="password" id="pay_pwd" /></span>
				</div>
			  </div>
			 </c:when>
			 <c:otherwise>
			 <input type="hidden" id="pay_pwd" />
			 </c:otherwise>
			</c:choose>

			<!-- 支付密码end -->
			<a id="payConfirm" class="pay_now" disabled="disabled">确定支付</a>
			
			
			
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
		<input type="hidden" id="hidCouponId" name="couponId" value="0" />
		<input type="hidden" id="hidBankPay" name="bankMoney" value="<fmt:formatNumber value="${needPay<0?0:needPay * 0.01}" pattern="0.##"/>" />
		<input type="hidden" id="payPwd" name="payPassword" value='' />
	</form>
	<script>
		if(parseFloat("${needPay}")>0){
			$("#div_other_pay").attr("style","display:block;");
		}
/*投资券*/
var ticketId="",									//投资券id
	tickNum=0,									//投资券金额
	copies='${inv.copies}',						//投资金额
	leftMoney = parseFloat("${leftMoney * 0.01}"),//账户余额
	payPwd='';									//支付密码
var isPwd='${isPwd}';

var setPwd='',setDbPwd='';	//第一次密码，第二次密码

function contSum(){
/*
 * 计算还需支付金额
 */
	var needMoney=parseFloat(copies-tickNum);	//需要支付的金额
	/* if(needMoney > leftMoney) {
		$("#span_needPay").text(parseFloat(needMoney - leftMoney));	
	}else {
		$("#span_needPay").text(0);
	} */
	if(needMoney < 0) {
		$("#span_needPay").text(0);
	}else {
		$("#span_needPay").text(needMoney);
	}
	//银行卡支付 差额
	$("#hidBankPay").val( leftMoney >= needMoney ? 0 : needMoney-leftMoney);
}
 
/*提交前判断余额是否充足*/
function neesPay(){
	var needMoney = parseFloat(copies-tickNum);		//需要支付的金额
	if(needMoney <= leftMoney) {		//当前投资金额 | 投资券+余额
		return true;
	}else {
		return false;
	}
}
		//是否使用红包
		var use_hb = true;
		var last_index;
		$(function(){
			if(isPwd!='ok'){
				$("#pwdpay").hide();

				$("#payConfirm").addClass("pay_true").removeAttr("disabled");
			}
			
			$(".mybank").click(function(){
				$(".hongbao_cc").find(".chooseimg").remove();
				$(".bank").find(".on_bank").removeClass("on_bank");
				$(this).addClass("on_bank");
			});
			$(".hb").find(".on_red").css("display","none");
			$(".hb").click(function(){
				var type='${product.productType}';
				var coupon=0;
				if(type == '1'){
					return false;
				}
					if($(this).find(".on_red").css("display")=="block"){
						$(".hb").find(".on_red").css("display","none");
						ticketId='';
						tickNum=0;
					}else {
						$(".hb").find(".on_red").css("display","none");
						$(this).find(".on_red").css("display","block");
						coupon =  parseFloat($(this).find(".fr .hb_touziquan .red_big").html());
						ticketId=$(this).find(".red_big").attr("data-ticket");
						tickNum=coupon; 
					}
					last_index=$(this).index();
				contSum();
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
			});
		});
		var isPay = true;
		$("#payConfirm").click(function(){
			if($("#payConfirm").hasClass('pay_true')){ 
			if(neesPay()){
			if(isPwd!='ok'){		//判断是否第一次，第一次则设置A
				setDoubleInput('password','password','设置交易密码',function(){
	    				$.ajax({
	    					url:'${pageContext.request.contextPath}/Product/User/userInfo!setPayPassword.action',
	    					data:{'newPayPassword':setPwd,'newPayPassword2':setDbPwd},
	    					success:function(data){	   
	    						if("ok"==data.status){
	    							payPwd=setPwd;
	    						}else{
	    							jingao(data.json,"提示",null,0);
	    							return false;
	    						}
	    						jingao("密码设置成功去投资","提示",function(){
	    							pay();}
	    						,0);

	    					},
	    					error:function(){
	    						jingao("密码设置失败","提示",null,0);
	    					}
	    				});
	    			},1);
            }else {		//否则直接提交
				pay();            	
            }
		}else {
			jingao("账户余额不足，请先去充值","提示",function(){
				//跳转到充值页面;
				window.location.href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action";
			},1);
		}
		}
		});
			
			
		 
		
		function pay(){
			if(!$("#payConfirm").hasClass('pay_true')){ 
				return;
			}else { 
				$("#payPwd").val(payPwd);
			}
			var coupon = 0;
			if(!use_hb){
				coupon = parseFloat("${useCoupon}");
			}
            
			
			$("#hidInMoney").val(($("#txt_useAccountMoney").val().replace(/[,]/g,""))*100);
			$("#hidCoupon").val(tickNum*100);
			$("#hidCouponId").val(ticketId);
			
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
		}
		
		
		$("#pay_pwd").keyup(function(){
			payPwd=$(this).val();
			if(payPwd.length > 5  && payPwd.length<18 ) { 
				$(".pay_now").addClass("pay_true");
				$(".pay_now").removeAttr("disabled");
			}else { 
				$(".pay_now").removeClass("pay_true");
				$(".pay_now").attr("disabled","disabled");
			}
		})
		
		
		$("#goToBind").click(function(){
			alert("绑定成功后,回来刷新页面即可");
		});
	</script>
</body>
</html>