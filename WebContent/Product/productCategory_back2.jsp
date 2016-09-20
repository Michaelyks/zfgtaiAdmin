<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
     <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 所有产品</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/product.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<style type="text/css">
			#pageUl li{
				margin: 0px 2px;
			}
		</style>
	</head>
	<body>
		<jsp:include page="header.jsp" />
		
		<!-- 主内容 -->
		<div class="main">
			<!-- 筛选产品 -->
			<div class="layout filter_cate">
				<h4>理财产品</h4>
				<dl>
					<dt>产品状态</dt>
					<dd id="filter_status">
						<a href="javascript:void(0);" class="product_status on3" data-value="all">全部</a>
						<a href="javascript:void(0);" class="product_status" data-value="0">营销中</a>
						<a href="javascript:void(0);" class="product_status" data-value="1">已售馨</a>
						<!-- <a href="javascript:void(0);" class="product_status" data-value="2">结算中</a> -->
						<a href="javascript:void(0);" class="product_status" data-value="3">已还款</a>
					</dd>
				</dl>
				<dl>
					<dt>产品状态</dt>
					<dd id="filter_type">
						<a href="javascript:void(0);" class="invest_type on3" data-value="all">全部</a>
						<a href="javascript:void(0);" class="invest_type" data-value="0">车无忧</a>
						<a href="javascript:void(0);" class="invest_type" data-value="1">贸易通</a>
						<a href="javascript:void(0);" class="invest_type" data-value="2">牛市通</a>
						<a href="javascript:void(0);" class="invest_type" data-value="3">房盈宝</a>
					</dd>
				</dl>
			
			</div>
			<!-- 筛选产品 end-->
			<div class="height18"></div>
			<!-- 产品列表 -->
			<div class="layout">
				<div class="product">
				<c:forEach items="${productList}" var="product">
					<%--理财产品,除了已售罄  --%>
					<div class="product_list <c:if test='${product.productStatus eq 1}'>shouqing</c:if>">
						<div class="fl">
							<p class="list_title"><a href="${pageContext.request.contextPath}/Product/index!showProductDetails.action?productId=${product.id}">${myel:getSubString(product.title,22)}</a><c:if test="${product.isRecommend eq '1' }"> <a class="recommend">推荐</a></c:if></p>
							<ul>
								<li>年化收益<span class="red big"><fmt:formatNumber value="${product.annualEarnings}" pattern="#.#"/></span><a class="red">%</a></li>
								<li>起投金额<span><fmt:formatNumber value="${product.atleastMoney * 0.01}" pattern="#,##0.#"/></span>元</li>
								<li style="width:20%;">投资期限<span>${product.tzqx}</span>天</li>
								<li style="width:30%;">项目总额<span><fmt:formatNumber value="${product.financingAmount * 0.01}" pattern="#,##0.#"/></span>元</li>
								<div class="clea"></div>
							</ul>
						</div>
						<ul class="fr">
							<li class="progress"><label>当前进度：</label><p class="jindutiao"><b class="qwy_progress" data-value="${product.wcjd}" style="width:0px;"></b></p><span><fmt:formatNumber value="${product.wcjd * 100}" pattern="#.#"/>%</span></li>
							
							<c:if test="${product.productStatus eq '0' }">
								<li><a href="#" >立即投资</a></li>
							</c:if>
							<c:if test="${product.productStatus eq '1' }">
								<li><a href="#" >已售罄</a></li>
							</c:if>
							<c:if test="${product.productStatus eq '3' }">
								<li><a href="#" style="background: #6BE21A;">已还款</a></li>
							</c:if>
						</ul>
						<div class="clea"></div>
						<c:if test="${product.wcjd gt 0.1 && product.productStatus eq 0}"><img class="product_hot" src="${pageContext.request.contextPath}/Product/images/product_hot.png"/></c:if>
						<c:if test="${product.productStatus eq '1' }"><img class="product_yishouqing" src="images/yishouqing.png"/></c:if>
					</div>
				</c:forEach>
				<div class="layout" style="background:#fff;padding:20px 0;text-align:center">
					<ul id="pageUl">
	                	<!-- <li class='btn first'><a style='cursor:pointer;' >首页</a></li>
	                    <li class="prev"><a style='cursor:pointer;'>上一页</a></li>
	                    <li><a style='cursor:pointer;'>1</a></li>
	                    <li><a style='cursor:pointer;'>2</a></li>
	                    <li><a style='cursor:pointer;'>3</a></li>
	                    <li><a style='cursor:pointer;'>4</a></li>
	                    <li><a style='cursor:pointer;'>5</a></li>
	                    <li class="next"><a style='cursor:pointer;'>下一页</a></li>
	                    <li class="last"><a style='cursor:pointer;'>末页</a></li> -->
               		</ul>
				</div>
				
			</div>
			<!-- 筛选列表 end-->
		
		</div>
		<!-- 主内容 end-->
<jsp:include page="footer.jsp" />
<form id="myForm" method="post">
<input id="product_status" type="hidden" value="" name="product.productStatus">
<input id="invest_type" type="hidden" value="" name="product.investType">
<input id="currentPage" type="hidden" value="1" name="currentPage">
</form>
		<script>
			$(function(){
				$('.filter_cate dd a').click(function(e){
					e.preventDefault();
					$(this).addClass('on3').siblings().removeClass('on3');
					selectCondition();
					$("#currentPage").val(1);
					$("#myForm").attr("action","${pageContext.request.contextPath}/Product/productCategory!getProductByCondition.action");
					$("#myForm").submit(); 
				});
				
			});
			//选择选中的值;
			function selectCondition(){
				var investType = "";
				//遍历产品投资类型;
				$(".invest_type").each(function(){
					var status_class = $(this).attr("class");
					if(status_class.search("on3") != -1){
						//alert($(this).attr("data-value"));
						investType = $(this).attr("data-value");
						$("#invest_type").val(investType);
					}
				});
				
				var productStatus = "";
				//遍历产品投资类型;
				$(".product_status").each(function(){
					var status_class = $(this).attr("class");
					if(status_class.search("on3") != -1){
						//alert($(this).attr("data-value"));
						productStatus = $(this).attr("data-value");
						$("#product_status").val(productStatus);
					}
				});
			}
			
			function initChoose(){
				var productStatus = "${product.productStatus}";
				$(".product_status").each(function(){
					if(productStatus == $(this).attr("data-value")){
						$(this).addClass('on3').siblings().removeClass('on3');
					}
				});
				var investType = "${product.investType}";
				//遍历产品投资类型;
				$(".invest_type").each(function(){
					if(investType == $(this).attr("data-value")){
						$(this).addClass('on3').siblings().removeClass('on3');
					}
				});
			}
			initChoose();
			
			function initProgress(){
				$(".qwy_progress").each(function(){
					//alert(1);
					var val = $(this).attr("data-value");
					val = parseFloat(val)*100;
					$(this).animate({width:val+"%"},1000);
				});
			};
			initProgress();
			var pageCount = parseInt("${pageCount}");

			function getProductByPage(currentPage){
				//alert(currentPage);
				console.log(currentPage);
				//alert("");
				$("#currentPage").val(currentPage);
				selectCondition();
				$("#myForm").attr("action","${pageContext.request.contextPath}/Product/productCategory!getProductByCondition.action");
				$("#myForm").submit();
			}
			appendDivA(parseInt("${currentPage}"), parseInt("${pageCount}"));
			/**
			 *在div下追加a标签;
			 *@param currentPage : 当前页数;
			 *@param pageCount : 总页数;
			 *@author qwy
			 */
			function appendDivA(currentPage,pageCount){
				  var id = "#pageUl";
					$(id).html("");
					var home = "<li class='first'><a style='cursor:pointer;' onclick='getProductByPage(1)'>首页</a></li>";
					var end = "<li class='last'><a style='cursor:pointer;' onclick='getProductByPage(${pageCount})'>末页</a></li>";
					var tempPrev = "  ";
					var tempNext = "  ";
					//如果总页数大于5页;
					if(pageCount<=5){
						for(var i=1;i<=pageCount;i++){
							//alert(i);
							tempNext +="<li ><a style='cursor:pointer;' onclick='getProductByPage("+i+")' >"+i+"</a></li>";
						}
					}else{
						if(currentPage>=4){
							var tempPage = currentPage+2>=pageCount?pageCount:currentPage+2;
							//alert(tempPage);
							if(tempPage==pageCount){
								for(var i=pageCount-4;i<=pageCount;i++){
									//alert(i);
									tempNext +="<li ><a style='cursor:pointer;' onclick='getProductByPage("+i+")' >"+i+"</a></li>";
								}
							}else{
								for(var i=currentPage-2;i<currentPage;i++){
									tempPrev +="<li ><a style='cursor:pointer;' onclick='getProductByPage("+i+")' >"+i+"</a></li>";
								}
								for(var i=currentPage;i<=tempPage;i++){
									tempNext +="<li ><a style='cursor:pointer;' onclick='getProductByPage("+i+")' >"+i+"</a></li>";
								}
							}
						}else{
							for(var i=1;i<3;i++){
								tempPrev +="<li ><a style='cursor:pointer;' onclick='getProductByPage("+i+")' >"+i+"</a></li>";
							}
							for(var i=3;i<=5;i++){
								tempNext +="<li ><a style='cursor:pointer;' onclick='getProductByPage("+i+")' >"+i+"</a></li>";
							}
						}
					}
					var result = home+tempPrev+tempNext+end;
					$(id).html(result);
			}
			 
			 $("#pageUl").children().each(function(){
				 if("${currentPage}"==$(this).text()){
					 $(this).attr("style"," background-color: #1480CC;color: white;");
				 }
			 });
		</script>
	</body>
</html>