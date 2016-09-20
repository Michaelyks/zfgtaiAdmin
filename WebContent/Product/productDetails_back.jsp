<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
    	 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!doctype html>
<html>

	<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<meta charset="utf-8" />
		<title>中泰理财 - 产品详情</title>
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/Product/css/public.css" type="text/css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/Product/css/Details_page.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/Product/js/detail.js"></script>
		<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/swfobject.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
		<style type="text/css">
		.flashDiv{
		  height: 200px;
  margin-top: 50px;
  margin-left: 100px;
		}
		</style>
	</head>
	
	<body style="background-color: white;">
	<jsp:include page="top.jsp" />
	<script type="text/javascript">choosePage("2");</script>
		<div class="center">
			<div class="top">
			<form id="buyNowFrom" method="post">
			<input type="hidden" name="inv.productId" value="${product.id}">
				<div class="left">	
					<ul>
						<li class="red">${product.title}</li>
					<!-- <li>香港恒生指数理财产品</li> -->
					<li class="gray">产品类型：${product.cplx}<span>产品状态：${product.cpzt}</span></li>
					
					<%-- <c:if test="${product.qtje*0.01*1.5 < product.leftCopies*1 || product.leftCopies*1 == 0}">
					<li>起投金额：<span class="red"><fmt:formatNumber value="${product.qtje * 0.01}" pattern="#,###.##"/> 元</span></li>
					</c:if>
					<c:if test="${product.qtje*0.01*1.5 >= product.leftCopies*1 && product.leftCopies*1>0}">
					<li>起投金额：<span class="red"><fmt:formatNumber value="${product.leftCopies}" pattern="#,###.##"/> 元</span></li>
					</c:if> --%>
					<li>起投金额：<span class="red"><fmt:formatNumber value="${product.qtje * 0.01}" pattern="#,###.##"/> 元</span></li>
					
					<li>起息日：<span class="gray">${product.jxfs}</span></li>
					<li>还款方式：<span class="gray">${product.fxfs}</span></li>
					<li>年化收益：<span class="red"><fmt:formatNumber value="${product.annualEarnings}" pattern="#.#"/>%</span></li>
					<c:choose>
						<c:when test="${product.productStatus == 1}">
							<li>投资期限：<span class="red">${product.cpzt}</span></li>
						</c:when>
						<c:when test="${product.productStatus == 2}">
							<li>投资期限：<span class="red">${product.cpzt}</span></li>
						</c:when>
						<c:when test="${product.productStatus == 3}">
							<li>投资期限：<span class="red">${product.cpzt}</span></li>
						</c:when>
						<c:otherwise>
						<li>投资期限：<span class="red">${product.tzqx} 天</span></li>
						</c:otherwise>
					</c:choose>
					<c:if test="${product.productStatus == 0}">
					<li class="money">投资金额：<span ><input maxlength="9" id="input_copies" type="text" name="inv.copies" value="" />元</span></li>
					</c:if>
					<c:if test="${product.productStatus != 0}">
					<li class="money" style="visibility: hidden;">投资金额：<span ><input id="input_copies" type="text" name="inv.copies" value="" />元</span></li>
					</c:if>
					</ul>	
					<c:if test="${product.productStatus == 0}">
					<a style="cursor: pointer;" id="buyNow" >立即投资</a>
					</c:if>
					<c:if test="${product.productStatus != 0}">
					<a style="background: #8EA8BA;">停止售卖</a>
					</c:if>
					
				</div>
				</form>
				<ul>
				<li>剩余金额：<a class="red"><fmt:formatNumber value="${product.leftCopies}" pattern="#,###.#"/> 元</a></li>
				<li><div class="flashDiv" id="mjjdt" ></div></li>
				<li><ul><li>项目总额：<span><fmt:formatNumber value="${product.financingAmount * 0.01}" pattern="#,###.#"/> 元</span><br><br></li>
				<li>项目到期：<span id="endTime"><fmt:formatDate value="${product.finishTime}" pattern="yyyy-MM-dd"/></span></li></ul></li>
				<li style="visibility: hidden;"><span id="endDate"><fmt:formatDate value="${product.endTime}" pattern="yyyy-MM-dd"/></span></li></ul></li>
			</ul>
				<div style="clear:both"></div>
			</div>
			
			<div class="bottom" id="details">
				<ul class="list_t">
					<li >项目详情</li>
					<li >信息披露</li>
					<li >法律意见书</li>
					<li class="change2" >投资记录</li>
					<div style="clear:both"></div>				
				</ul>
				<div class="list_bottom" > <div class="list_center" >${product.description}</div></div>
				<div class="list_bottom" >
					<div class="list_center list_tu">
						<c:forEach items="${infoList}" var="list">
						<p>&nbsp;</p>
						<img src="${list}" alt="信息披露图片" style="width:100%;"/>
						</c:forEach>
					</div>
				<!-- 以下注释的是点击图片切换效果 -->
					<!--
				 		<ul id="big_photo" class="big_photo">
							<li><img src="images/0.jpg"/></li>
							<li><img src="images/1.jpg"/></li>
							<li><img src="images/2.jpg"/></li>
							<li><img src="images/3.jpg"/></li>
							<li><img src="images/1.jpg"/></li>
							<div style="clear:both"></div>
						</ul>
						<div class="small_c">
							<ul id="small_photo" class="small_photo">
								<li><img src="images/0.jpg" class="on_t"/></li>
								<li><img src="images/1.jpg"/></li>
								<li><img src="images/2.jpg"/></li>
								<li><img src="images/3.jpg"/></li>
								<li><img src="images/1.jpg"/></li>
								<div style="clear:both"></div>
							</ul>
						</div>	
						<div id="left" class="left" onclick="move(this)"></div>
						<div id="right" class="right" onclick="move(this)"></div>	
					-->
				</div>
				<div class="list_bottom" >					
			  		<div class="list_center list_tu">
						<c:forEach items="${lawList}" var="list">
						<p>&nbsp;</p>
						<img src="${list}" alt="法律意见书图片" style="width:100%;"/>
						</c:forEach>
					</div>	
				</div>
				<div class="list_bottom" style="display:block">
				<c:choose>
					<c:when test="${investorsList eq null || investorsList eq '[]' && investorsList eq ''}">
					<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
            			<img src="${pageContext.request.contextPath}/Product/images/no_record.png" />
            		</div>
                	</c:when>
                	<c:otherwise>
                	<table>              		
                		<tbody>
                			<tr class="first_tb">         			
                				<td class="td1" width="124px">用户名</td>
                				<td width="160px">投资金额(元)</td>
                				<td width="160px">投资时间</td>
                				<td class="td1">投资状态</td>
                			</tr>
                			<c:forEach items="${investorsList}" var="list">
                			<tr>
                				<td class="td1">${list.username}</td>
                				<td><fmt:formatNumber value="${list.copies}" pattern="#,##0.##"/></td>
                				<td><fmt:formatDate value="${list.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                				<td class="td1"><img src="${pageContext.request.contextPath}/Product/images/status_yes_02.png"/></td>
                			</tr>
                			</c:forEach>
                		</tbody>
                	</table>
                	<DIV align="right" style="margin: 0px 20px 20px 20px;">
                	<jsp:include page="page.jsp" />
                	</DIV>
                	</c:otherwise>
                	</c:choose>
				</div>
			</div>
		
		</div>
		<%-- <jsp:include page="showPayInfo.jsp" /> --%>
		<jsp:include page="footer.jsp" />
	</body>
<script type="text/javascript">
var wcjd="${product.wcjd*100}";
//var wcjd = parseInt(wcjd); 暂时去除
wcjd = parseInt(wcjd);
/* //临时
 if(wcjd==0){
	wcjd==100;
} 
//临时 20140812
if("0"!="${product.productStatus}"){
	//alert(3);
	wcjd=100;
} */
//alert(wcjd);
var url = "flash/yuan.swf?percent="+wcjd;
__flash("mjjdt",url,158,158);

//判断输入金额格式数值是否符合要求 
function checkInput(){
	//return true;
		var leftCopies= "${product.leftCopies}";//剩余可投资
		var atleastMoney="${product.qtje}";//起投金额
		var status="${product.productStatus}";
		var num=document.getElementById("input_copies").value;
		//alert(num);
		// var endTime="${product.endTime}";
		//var date1= new Date(Date.parse(strTime.replace(/-/g,   "/"))); 
		//var date=new Date();
		//alert(endTime);
		var strTime=$("#endTime").text();
		var endDate=$("#endDate").text();
		var date1= new Date(Date.parse(strTime.replace(/-/g,   "/"))); 
		var date2= new Date(Date.parse(endDate.replace(/-/g,   "/"))); 
		var date=new Date();
		//alert(format(date));
		//alert(format(date2));
		//alert(format(date2)<format(date));
		
		if("${usersLogin.isBindBank}"!="1"){
			jingao("必须先绑定银行卡才能投资!", "提示", function(){
				window.location.href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action";
			}, 0);
			return false;
		}
	
		if (isEmpty(num)) {
			jingao("投资金额不能为空", "提示", "", 0);
			return false;
		} else if (isNaN(num)) {
			jingao("投资金额不能为非数字", "提示", "", 0);
			return false;
		} else if (!(/(^[1-9]\d*$)/.test(num))) {
			jingao("投资金额只能是正整数", "提示", "", 0);
			return false;
		} else if (format(date2) <= format(date)) {
			jingao("项目到期前3天不能购买", "提示", "", 0);
			return false;
		} else if (format(date1) <= format(date)) {
			jingao("该投资已过期", "提示", "", 0);
			return false;
		} else if (num * 100 < atleastMoney) {
			jingao("投资金额必须大于等于起投金额", "提示", "", 0);
			return false;
		} else if (num.length > 9) {
			jingao("请输入小于等于九位数的金额", "提示", "", 0);
			return false;
		}
		if (status != 0) {
			jingao("非营销中无法购买", "提示", "", 0);
			return false;
		} else if (num * 1 > leftCopies) {
			jingao("投资金额必须小于等于剩余金额", "提示", "", 0);
			return false;
		}else if(parseInt(num)%100!=0){
			jingao("投资金额必须为100的整数倍", "提示", "", 0);
			return false;
		}else{
			return true;
		}

	}

	$("#buyNow")
			.click(
					function() {
						//alert($("#buyNowFrom").serialize());
						if ("${usersLogin}" == "") {
							if (confirm("需要登录才能购买是否立即登录?")) {
								window.location.href = "${pageContext.request.contextPath}/Product/login.jsp";
								return false;
							} else {
								return false;
							}
						} else {
							$
									.ajax({
										type : "post",
										async : false,
										url : "${pageContext.request.contextPath}/Product/productCategory!isLogin.action?product.id=${product.id}",
										success : function(data) {
											if (data.status == "ok") {
												if (checkInput()) {
													var dialog = art
															.dialog({
																id : 'dialog_showPayInfo',
																title : false,
																fixed : true,
																content : document
																		.getElementById("psi_load")
															});
													$
															.ajax({
																type : "post",
																url : "${pageContext.request.contextPath}/Product/User/buyCar!showPayInfo.action",
																/* data:"inv.productId=${product.id}", */
																data : $(
																		"#buyNowFrom")
																		.serialize(),
																success : function(
																		data) {
																	dialog
																			.content(data);
																}
															});
												} else {
													return false;
												}
											} else if ("hasBuy" == data.status) {
												alert("每个用户只能购买一次新手产品");
												return false;
											} else if ("no" == data.status) {
												if (confirm("需要登录才能购买是否立即登录?")) {
													window.location.href = "${pageContext.request.contextPath}/Product/login.jsp";
												} else {
													return false;
												}
												return false;
											} else {
												alert(data.json);
												return false;
											}
										}
									});
						}

						//$("#buyNowFrom").attr("action","${pageContext.request.contextPath}/Product/User/buyCar!addNewInvestors.action");
						//$("#buyNowFrom").submit();
					});
</script>
</html>