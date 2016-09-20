<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
	<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<meta charset="utf-8"/>
		<title>购物车</title>
		<link href="../css/common.css" rel="stylesheet" type="text/css">
		<link href="../css/account.css" rel="stylesheet" type="text/css">
		<link href="../css/public.css" type="text/css" rel="stylesheet" />
		<link href="../css/car.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
		<style type="text/css">
			.tr_bg{
			background-color: gainsboro;
			}
		</style>
	</head>
	<body>
	<jsp:include page="../top.jsp" />
	<div class="lujin"><div class="crumbs"><a href="#"><img src="../images/home_ico.png" /></a> &gt; <a href="#">投资中心</a> &gt; <em>购物车</em></div></div>	
	<div >
		<div class=" maincnt wrap cf">
	<jsp:include page="leftMenu.jsp" />
		<div class="center">
			<form>
				<div class="top">
						<ul class="t_1">
						<li>确认订单信息</li>
					</ul>
					<table id="order_table" style="width: 100%; text-align: center;">
							<tr class="t_2" > 
								<td width="6%"></td>
								<td width="17%">产品名称</td>
								<td width="10%">项目总额(元)</td>
								<td width="15%">投资期限(天)</td>
								<td width="12%" >年化收益</td>
								<td width="10%">价格(元)</td>
								<td width="24%">安全性</td>
								<td width="6%">操作</td>
							</tr>
							<c:forEach items="${investorsList}"  var="inv" varStatus="index">
							<tr id="tr_${inv.id}" class="qwy_tr " data-value="${inv.id}" style="text-align: center;" onclick="selectProduct(this,'${inv.id}')">
								<td><!-- onmouseover="addBg(this,'${inv.id}')" onmouseout="delBg(this,'${inv.id}')"  -->
								<img name="choose" id="radio_${inv.id}" src="../images/select_no.png">
								</td>
								<td>${inv.product.title}</td>
								<td><fmt:formatNumber value="${inv.product.financingAmount * 0.01}" pattern="#,#00.#"/></td>
								<td>${inv.product.tzqx}</td>
								<td><fmt:formatNumber value="${inv.product.annualEarnings}" pattern="#.#"/>%</td>
								<td><span id="price_${inv.id}"><fmt:formatNumber value="${inv.inMoney * 0.01}" pattern="#.#"/></span></td>
								<td>								 
									<div style="display: inline-block;"><img src="../images/bao.png" /><br/>已投保</div>
									<div style="display: inline-block;"><img src="../images/shenf.png" /><br/>身份确认</div>
									<!-- <div><img src="../images/shenf.jpg" /><br/>身份确认</div> -->
								</td>
								<td><a style="cursor: pointer;color: red;" onclick="deleteInvs('${inv.id}')" >删除</a></td>
							</tr>
							</c:forEach>
						</table>
				</div>
				<c:choose>
					<c:when test="${investorsList eq null || investorsList eq '' || investorsList eq '[]'}">
					<div align="center">
					 <img src="../images/buy_car.png"><span class="f_red1">购物车里什么都没有~  </span><a href="/wgtz/Product/productCategory!loadProduct.action" class="myInvestors">立即购买</a>
					</div>
					</c:when>
					<c:otherwise>
					<div class="bottom" id="div_order">
					<ul class="b_1">
						<li>单个支付：<span class="f-24" id="pay_total">￥0<%-- <fmt:formatNumber value="${productTotal * 0.01}" pattern="#.#"/> --%> </span></li>
					</ul>
					<%-- <ul class="heji">
						<li>支付金额：<span class="red">￥</span><span class="f-32"><fmt:formatNumber value="${productTotal * 0.01}" pattern="#.#"/></span></li>
					</ul> --%>
					<ul class="heji" style="margin-top: 30px;">
						<a href="#">确认提交</a>
						<div style="clear:both"></div>
					</ul>
				</div>
					</c:otherwise>
				</c:choose>
			</form>	
			</div>
		</div>
</div>
<jsp:include page="../footer.jsp" />
</body>
<script type="text/javascript">
choosePage("3");
chooseMenu("2");
if("${investorsList}"!="" && "${investorsList}"!="[]"){
	var first_tr = $("#order_table tr").first().next();
	$(first_tr).addClass('tr_bg');
	var total_id = $(first_tr).attr("data-value");
	$("#pay_total").text("￥"+$("#price_"+total_id).text());
	var first_img = $("img[name='choose']").first();
	$(first_img).attr("src","../images/select_yes.png");
}
//alert();
function deleteInvs(invId){
	//alert(3);
	if(!confirm("确定要从购物车移除该产品吗?")){
		return false;
	}
	//var invId = $("#del_inv").attr("data-value");
	window.location.href="${pageContext.request.contextPath}/Product/User/buyCar!deleteInvestors.action?inv.id="+invId;
};

function addBg(obj,invId){
	$(obj).addClass('tr_bg');//.siblings().removeClass('tr_bg');
/* 	if($("#radio_"+invId).attr("src")=="../images/select_yes.png"){
		alert(2);
		
		$(obj).addClass('tr_bg');
		return false;
	} */
	
	$("img[name='choose']").each(function(){
		//alert($(this).attr("id")+" : "+invId);
		//alert($(this).attr("src"));
		if($(this).attr("src")=="../images/select_yes.png"){
			//$(this).attr("src","../images/select_yes.png");
			$("#tr_"+invId).addClass('tr_bg');
		}
	});
}
function delBg(obj,invId){
	if($("#radio_"+invId).attr("src")=="../images/select_yes.png"){
		//alert(3);
		return false;
	}
	//alert(4);
	$("#tr_"+invId).removeClass('tr_bg');
	//$(this).removeClass('tr_bg');
	/* $("img[name='choose']").each(function(){
		//alert($(this).attr("id")+" : "+invId);
		//alert($(this).attr("src"));
		$(this).removeClass('tr_bg');
		if($(this).attr("src")=="../images/select_yes.png"){
			//$(this).attr("src","../images/select_yes.png");
			alert("家央视");
			$(this).addClass('tr_bg');
		}
	}); */
	
} 
function selectProduct(obj,invId){
	$(obj).addClass('tr_bg').siblings().removeClass('tr_bg');
	//$("#radio_"+invId).attr("src","../images/select_yes.png");
	//$(obj).removeClass('tr_bg');
	$("img[name='choose']").each(function(){
		//alert($(this).attr("id")+" : "+invId);
		//alert($(this).attr("src"));
		if($(this).attr("id")=="radio_"+invId){
			$(this).attr("src","../images/select_yes.png");
		}else{
			$(this).attr("src","../images/select_no.png");
		}
	}); 
	$("#pay_total").text("￥"+$("#price_"+invId).text());
}

</script>
</html>