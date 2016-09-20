<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<title>中泰理财 - 理财产品</title>
<link href="css/common.css" rel="stylesheet" type="text/css">
<link href="css/search2.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/jquery.superslide.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<style type="text/css">
.selectedPage {
	background-color: #1480CC;
	color: white;
}
</style>

</head>

<body>
<jsp:include page="top.jsp" />
<script type="text/javascript">choosePage("2");</script>
<div class="content wrap cf">
    <div class="sear_left fl">
    	<div class="filter_cate">
        	<h4><span>筛选项目</span><!--  <a style='cursor:pointer;' >单选</a> --></h4>
            <dl>
            	<dt>产品状态</dt>
                <dd>
                	<a href="javascript:void(0);" class="product_status on" data-value="all">全部</a>
                    <a href="javascript:void(0);" class="product_status" data-value="0">营销中</a>
                    <a href="javascript:void(0);" class="product_status" data-value="1">已售馨</a>
                    <a href="javascript:void(0);" class="product_status" data-value="2">结算中</a>
                    <a href="javascript:void(0);" class="product_status" data-value="3">已还款</a>
                </dd>
            </dl>
            <dl>
            	<dt>产品类型</dt>
                <dd>
                	<a style='cursor:pointer;' class="invest_type on" data-value="all">全部</a>
                    <a style='cursor:pointer;' class="invest_type" data-value="0">车无忧</a>
                    <a style='cursor:pointer;' class="invest_type" data-value="1">贸易通</a>
                    <a style='cursor:pointer;' class="invest_type" data-value="2">牛市通</a>
                    <a style='cursor:pointer;' class="invest_type" data-value="3">房盈宝</a>
                   <!--  <a style='cursor:pointer;'>一个月</a>
                    <a style='cursor:pointer;'>三个月</a>
                    <a style='cursor:pointer;'>六个月</a> -->
                </dd>
            </dl>
            <!-- <dl>
            	<dt>按预期收益</dt>
                <dd>
                	<a style='cursor:pointer;' class="all on">全部</a>
                    <a style='cursor:pointer;'>10%以内</a>
                    <a style='cursor:pointer;'>10%-20%</a>
                    <a style='cursor:pointer;'>20%-30%</a>
                    <a style='cursor:pointer;'>30%-50%</a>
                    <a style='cursor:pointer;'>50%以上</a>
                </dd>
            </dl> -->
        </div>
        <div class="search2_list">
        <c:forEach items="${productList}" var="product">
        <c:choose>
        	<c:when test="${product.productStatus eq 1}">
        		<div class="item" style="background-color: #D2D2D2;">
        	</c:when>
        	<c:otherwise>
        		 <c:choose>
		        	<c:when test="${product.productType eq 1 && product.productStatus eq 0}">
		        		<div class="item" style="background-color: #FFFAC5;">
		        	</c:when>
		        	<c:otherwise>
		        		<div class="item">
		        	</c:otherwise>
		        </c:choose>
        	</c:otherwise>
        </c:choose>
                <div class="title">
                	<!-- <span class="time">募集剩余：<i>05天12小时15分</i></span> -->
                	 <c:choose>
			        	<c:when test="${product.productType eq 1}">
			        		<h4><a href="${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?productId=${product.id}" target="_blank">${product.title}</a></h4>
			        	</c:when>
			        	<c:otherwise>
			        		<h4>
			        			<a href="${pageContext.request.contextPath}/Product/index!showProductDetails.action?productId=${product.id}" target="_blank">${product.title}</a>
								<c:if test="${product.isRecommend eq 1}">
									<img src="images/recommend.png"/>
								</c:if>
							</h4>
			        	</c:otherwise>
		       		 </c:choose>
                </div>
				<div class="data">
                	<ul class="cf">
                    	<li>
                   			<h4>投资期限</h4>
                        	<c:choose>
                        	<c:when test="${product.productStatus == 1}">
                          	  <p><span class="color_d24740" style="font-size: 20px;">${product.cpzt}</span></p>
                            </c:when>
                             <c:when test="${product.productStatus== 2}">
                          	  <p><span class="color_d24740" style="font-size: 20px;">${product.cpzt}</span></p>
                            </c:when>
                              <c:when test="${product.productStatus== 3}">
                          	  <p><span class="color_d24740" style="font-size: 20px;">${product.cpzt}</span></p>
                            </c:when>
                            <c:otherwise>
                            	<c:choose>
                            		<c:when test="${product.productType == 1}">
                            			<p><span class="color_d24740" >${product.lcqx}</span>天</p>
                            		</c:when>
                            		<c:otherwise>
                            			<p><span class="color_d24740" >${product.tzqx}</span>天</p>
                            		</c:otherwise>
                            	</c:choose>
                            </c:otherwise>
                            </c:choose>
                        </li>
						<li>
                        	<h4>项目总额</h4>
                            <p><span><fmt:formatNumber value="${product.financingAmount * 0.01}" pattern="#,##0.#"/></span></p>
                        </li>
                        <li>
                        	<h4>年化收益</h4>
                            <p><span class="color_d24740"><fmt:formatNumber value="${product.annualEarnings}" pattern="#.#"/></span>%</p>
                        </li>
                        <li>
                        	<h4>起投金额<!-- ：<span>如1000元</span> --></h4>
                            <p><span><fmt:formatNumber value="${product.atleastMoney * 0.01}" pattern="#,##0.#"/></span></p>
                        </li>
                        <c:if test="${product.productType eq 1}">
	                        <li><img src="images/gold.png"/></li>
                        </c:if>
                    </ul>
                </div>
                <div class="bot">
                <c:if test="${product.productStatus == 0}">
                <c:choose>
                	<c:when test="${product.productType == 1}">
                		<a style='cursor:pointer;' href="${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?productId=${product.id}" class="btn" target="_blank">立即投资</a>
                	</c:when>
                	<c:otherwise>
                		<a style='cursor:pointer;' href="${pageContext.request.contextPath}/Product/index!showProductDetails.action?productId=${product.id}" class="btn" target="_blank">立即投资</a>
                	</c:otherwise>
                </c:choose>
                </c:if>
                <c:if test="${product.productType != 1}">
                    <ul>
                        <li class="li_3">
                        	<label>项目到期：</label>
                            <p><fmt:formatDate value="${product.finishTime}" pattern="yyyy-MM-dd"/></p>
                            <!-- <i></i>
                            <div class="tips">
                                <s></s>
                                借入钱购买的产品，需要支付一定的利息需要支付一定的利息需要支付一定的利息
                            </div> -->
                        </li>
                        <c:if test="${product.productStatus == 2 || product.productStatus == 3}">
                        <li class="li_4">
                        	<label>当前进度：</label>
                            <p class="pross"><em style="width: <fmt:formatNumber value="100" pattern="#.#"/>%;"></em></p>
                            <b><fmt:formatNumber value="100" pattern="#.#"/>%</b>
                        </li>
                        </c:if>
                         <c:if test="${product.productStatus == 0 || product.productStatus == 1}">
                         <li class="li_4">
                        	<label>当前进度：</label>
                            <p class="pross"><em style="width: <fmt:formatNumber value="${product.wcjd * 100}" pattern="#.#"/>%;"></em></p>
                            <b><fmt:formatNumber value="${product.wcjd * 100}" pattern="#.#"/>%</b>
                        </li>
                         </c:if>
                    </ul>
                    </c:if>
                </div>
                <c:choose>
                	<c:when test="${product.productStatus == 1}">
						<div class="invest_z" style="background:url(${pageContext.request.contextPath}/Product/images/yishouqing.png);"></div>
						<img class="maiwang" src="images/shouq.png"/>
					</c:when>
					<c:when test="${product.productStatus == 2}">
						<div class="invest_z" style="background:url(${pageContext.request.contextPath}/Product/images/jiesuanzhong.png);"></div>
					</c:when>
					<c:when test="${product.productStatus == 3}">
					<div class="invest_z" style="background:url(${pageContext.request.contextPath}/Product/images/yihuankuan.png);"></div>
					</c:when>
					<c:otherwise>
						<div class="invest_z"></div>
					</c:otherwise>
				</c:choose>
            </div>
           </c:forEach>
           <c:choose>
            <c:when test="${productList eq '[]' || productList eq ''}">
            	<div style="text-align: center;margin-top: 15px;">
            	<img src="images/no_record.png" />
            	</div>
            </c:when>
            <c:otherwise>
            <div class="page" style="display: block;">
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
            </c:otherwise>
           </c:choose>
        </div>
    </div>
<!--     <div class="sear_right fr">
        <div class="weixin"><img src="images/weixin.jpg" width="160" /></div>
        <div class="noun_explain" style="display: none;">
            <h2>名词解释</h2>
            <ul class="cf">
                <li><a style='cursor:pointer;'>领投人</a></li>
                <li><a style='cursor:pointer;'>投资额度</a></li>
                <li><a style='cursor:pointer;'>预期收益</a></li>
                <li><a style='cursor:pointer;'>运作期限</a></li>
                <li><a style='cursor:pointer;'>资金投向</a></li>
                <li><a style='cursor:pointer;'>止损控制</a></li>
                <li><a style='cursor:pointer;'>当前价值</a></li>
                <li><a style='cursor:pointer;'>购买价格</a></li>
            </ul>
            <p><a style='cursor:pointer;'>点击查看详情</a></p>
        </div>
    </div> -->
</div>
<jsp:include page="footer.jsp"></jsp:include>
<form id="myForm" method="post">
<input id="product_status" type="hidden" value="" name="product.productStatus">
<input id="invest_type" type="hidden" value="" name="product.investType">
<input id="currentPage" type="hidden" value="1" name="currentPage">
</form>
<!-- 
<form id="myForm2" method="post" action="www.baidu.com">
<input id="product_status" type="hidden" value="" name="product.productStatus">
<input id="invest_type" type="hidden" value="" name="product.investType">
<input id="currentPage" type="hidden" value="1" name="currentPage">
</form> -->
</body>
<script type="text/javascript">
$('.filter_cate dd a').click(function(e){
	e.preventDefault();
	$(this).addClass('on').siblings().removeClass('on');
	selectCondition();
	$("#currentPage").val(1);
	$("#myForm").attr("action","${pageContext.request.contextPath}/Product/productCategory!getProductByCondition.action");
	$("#myForm").submit();
	
});
//选择选中的值;
function selectCondition(){
	var investType = "";
	//遍历产品投资类型;
	$(".invest_type").each(function(){
		var status_class = $(this).attr("class");
		if(status_class.search("on") != -1){
			//alert($(this).attr("data-value"));
			investType = $(this).attr("data-value");
			$("#invest_type").val(investType);
		}
	});
	
	var productStatus = "";
	//遍历产品投资类型;
	$(".product_status").each(function(){
		var status_class = $(this).attr("class");
		if(status_class.search("on") != -1){
			//alert($(this).attr("data-value"));
			productStatus = $(this).attr("data-value");
			$("#product_status").val(productStatus);
		}
	});
}
//alert("${product.productStatus}");
function initChoose(){
	var productStatus = "${product.productStatus}";
	$(".product_status").each(function(){
		if(productStatus == $(this).attr("data-value")){
			$(this).addClass('on').siblings().removeClass('on');
		}
	});
	var investType = "${product.investType}";
	//遍历产品投资类型;
	$(".invest_type").each(function(){
		if(investType == $(this).attr("data-value")){
			$(this).addClass('on').siblings().removeClass('on');
		}
	});
}
initChoose();

var pageCount = parseInt("${pageCount}");
function initPage(){
	
	var homePage = "<li class='first'><a style='cursor:pointer;' >首页</a></li>";
	var prevPage = "<li class='prev'><a style='cursor:pointer;'>上一页</a></li>";
    
    
    $("#pageUl").append(homePage);
    $("#pageUl").append(prevPage);
	for(var i = 1; i <= pageCount; i++ ){
		 var pageLi = "<li ><a style='cursor:pointer;' onclick='getProductByPage("+i+")' >"+i+"</a></li>";
		$("#pageUl").append(pageLi);
	}
	var nextPage = "<li class='next'><a style='cursor:pointer;'>下一页</a></li>";
	var lastPage = "<li class='last'><a style='cursor:pointer;' >末页</a></li>";
	 $("#pageUl").append(nextPage);
	 $("#<li ><a style='cursor:pointer;' onclick='getProductByPage("+i+")' >"+i+"</a></li>").append(lastPage);
}


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
//initPage();
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
		 $(this).children().attr("style"," background-color: #1480CC;color: white;");
	 }
 });
</script>


</html>