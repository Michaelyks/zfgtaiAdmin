<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
     <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财理财产品-贸易通,牛市通,车无忧,房盈宝,等优选理财产品</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/common_product.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/productCategory_new.css" rel="stylesheet" type="text/css"/>
		<link href="css/product.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		
	</head>
	<body>
		<jsp:include page="header.jsp" />
		
		<!-- 主内容 -->
		<div class="main">
			<!-- 筛选产品 -->
			<div class="layout filter_cate">
				<!-- <h4>理财产品</h4> -->
				<dl  style="border-bottom:1px dashed #CCC;position:relative;">
					<dt>产品状态</dt>
					<dd id="filter_status">
						<a href="javascript:void(0);" class="product_status on3" data-value="all">全部</a>
						<a href="javascript:void(0);" class="product_status" data-value="0">热卖中</a>
						<a href="javascript:void(0);" class="product_status" data-value="1">已售馨</a>
						<!-- <a href="javascript:void(0);" class="product_status" data-value="2">结算中</a> -->
						<a href="javascript:void(0);" class="product_status" data-value="3">已还款</a>
					</dd>
					<a href="/zfgtai/Product/index!showProductDetails.action?productId=4028805e54eb7d680154eb7e5d320001" style="display:inline-block;width:200px;height:50px;line-height:50px;border-radius:5px;text-align:center;font-size:20px;position:absolute;top:50px;;right:200px;color:#FFF;background:#FD8026;">进入详情页</a>
				</dl>
				<dl>
					<dt>产品类型</dt>
					<dd id="filter_type">
						<a href="javascript:void(0);" class="invest_type on3" data-value="all">全部</a>
						<a href="javascript:void(0);" class="invest_type" data-value="0">贸易通</a>
						<a href="javascript:void(0);" class="invest_type" data-value="1">资产宝</a>
						
						<!-- 
						<a href="javascript:void(0);" class="invest_type" data-value="0">车无忧</a>
						<a href="javascript:void(0);" class="invest_type" data-value="1">贸易通</a>
						<a href="javascript:void(0);" class="invest_type" data-value="2">牛市通</a>
						<a href="javascript:void(0);" class="invest_type" data-value="3">房盈宝</a>
						 -->
					</dd>
				</dl>
			
			</div>
			<!-- 筛选产品 end-->
			
			<!-- 产品列表 -->
			<div class="layout">
				<!-- 有记录时返回的页面 -->
				<div class="product" style="display:block;">
					<!-- 每1页6个产品-->
					<ul>
						<!-- 最左侧产品样式 （每3个一排，共2排）-->
						<li class="fl product_show product_show1">
							<!-- 新手 -->
							<div  class="new">
								<p class="title">贸易通产品名NO.001</p>
								<ul>
									<li class="rate fl rate1"><span class="red  text_bt"><span class="s48">15.00</span>%</span><br><span class="text_bt text_bt1">年化收益率</span></li>
									<li class="rate fl"><span class="black"><span class="s36 l_black">100</span>天</span><br><span class="text_bt  text_bt2">理财期限</span></li>
								</ul>
								<p class="jine"><span class="lf">起购金额</span><span class="rt">1,000</span>元</p>
								<p class="jine" ><span  class="lf">剩余金额</span><span class="rt" >1,000,000</span>元</p>
								<!-- 购买标签样式 -->
								<p><a class="choose" href="">立即购买</a></p>
								<p class="zhenxuan">多重安全保障&nbsp;&nbsp;严格甄选产品</p>
							</div>	
						</li>
						<li class="fl product_show">
							<!-- 已售罄 -->
							<div class="saleout">
								<p class="title">贸易通产品名NO.001</p>
								<ul>
									<li class="rate fl rate1"><span class="red  text_bt"><span class="s48">15.00</span>%</span><br><span class="text_bt text_bt1">年化收益率</span></li>
									<li class="rate fl"><span class="black"><span class="s36 l_black">100</span>天</span><br><span class="text_bt  text_bt2">理财期限</span></li>
								</ul>
								<p class="jine"><span class="lf">起购金额</span><span class="rt">1,000</span>元</p>
								<p class="jine" ><span  class="lf">剩余金额</span><span class="rt" >1,000,000</span>元</p>
								<!-- 已售罄标签样式 -->
								<p><a class="choose  choose1" href="">已售罄</a></p>
								<p class="zhenxuan">多重安全保障&nbsp;&nbsp;严格甄选产品</p>
							</div>	
						</li>
						<li class="fl product_show">
							<!-- 已还款-->
							<div class="paid">
								<p class="title">贸易通产品名NO.001</p>
								<ul>
									<li class="rate fl rate1"><span class="red  text_bt"><span class="s48">15.00</span>%</span><br><span class="text_bt text_bt1">年化收益率</span></li>
									<li class="rate fl"><span class="black"><span class="s36 l_black">100</span>天</span><br><span class="text_bt  text_bt2">理财期限</span></li>
								</ul>
								<p class="jine"><span class="lf">起购金额</span><span class="rt">1,000</span>元</p>
								<p class="jine" ><span  class="lf">剩余金额</span><span class="rt" >1,000,000</span>元</p>
								<!-- 已还款标签样式（同已售罄） -->
								<p><a class="choose choose1 product_paid">已还款</a></p>
								<p class="zhenxuan">多重安全保障&nbsp;&nbsp;严格甄选产品</p>
							</div>
						</li>
						<li class="fl product_show product_show1">
							<!-- 普通产品无特殊标签-->
							<div>
								<p class="title">贸易通产品名NO.001</p>
								<ul>
									<li class="rate fl rate1"><span class="red  text_bt"><span class="s48">15.00</span>%</span><br><span class="text_bt text_bt1">年化收益率</span></li>
									<li class="rate fl"><span class="black"><span class="s36 l_black">100</span>天</span><br><span class="text_bt  text_bt2">理财期限</span></li>
								</ul>
								<p class="jine"><span class="lf">起购金额</span><span class="rt">1,000</span>元</p>
								<p class="jine" ><span  class="lf">剩余金额</span><span class="rt" >1,000,000</span>元</p>
								<p><a class="choose" href="">立即购买</a></p>
								<p class="zhenxuan">多重安全保障&nbsp;&nbsp;严格甄选产品</p>
							</div>	
						</li>
						<li class="fl product_show">
							<div>
								<p class="title">贸易通产品名NO.001</p>
								<ul>
									<li class="rate fl rate1"><span class="red  text_bt"><span class="s48">15.00</span>%</span><br><span class="text_bt text_bt1">年化收益率</span></li>
									<li class="rate fl"><span class="black"><span class="s36 l_black">100</span>天</span><br><span class="text_bt  text_bt2">理财期限</span></li>
								</ul>
								<p class="jine"><span class="lf">起购金额</span><span class="rt">1,000</span>元</p>
								<p class="jine" ><span  class="lf">剩余金额</span><span class="rt" >1,000,000</span>元</p>
								<p><a class="choose" href="">立即购买</a></p>
								<p class="zhenxuan">多重安全保障&nbsp;&nbsp;严格甄选产品</p>
							</div>	
						</li>
						<li class="fl product_show">
							<div class="paid">
								<p class="title">贸易通产品名NO.001</p>
								<ul>
									<li class="rate fl rate1"><span class="red  text_bt"><span class="s48">15.00</span>%</span><br><span class="text_bt text_bt1">年化收益率</span></li>
									<li class="rate fl"><span class="black"><span class="s36 l_black">100</span>天</span><br><span class="text_bt  text_bt2">理财期限</span></li>
								</ul>
								<p class="jine"><span class="lf">起购金额</span><span class="rt">1,000</span>元</p>
								<p class="jine" ><span  class="lf">剩余金额</span><span class="rt" >1,000,000</span>元</p>
								<p><a class="choose choose1 product_paid">已还款</a></p>
								<p class="zhenxuan">多重安全保障&nbsp;&nbsp;严格甄选产品</p>
							</div>
						</li>
					</ul>
					<div id="pageUI-box;" class="layout" style="background:#fff;text-align:center;">
						<ul id="pageUl">
							<li class='btn first'><a style='cursor:pointer;' >首页</a></li>
							<li class="prev"><a style='cursor:pointer;'>上一页</a></li>
							<li><a style='cursor:pointer;'>1</a></li>
							<li><a style='cursor:pointer;'>2</a></li>
							<li><a style='cursor:pointer;'>3</a></li>
							<li><a style='cursor:pointer;'>4</a></li>
							<li><a style='cursor:pointer;'>5</a></li>
							<li class="next"><a style='cursor:pointer;'>下一页</a></li>
							<li class="last"><a style='cursor:pointer;'>末页</a></li>
						</ul>
				</div>
				</div>
				
				<!-- 无记录时返回的空页面 -->
				<div class="product empty" style="display:none">
					<div></div>
				</div>
				
			</div>
				
		</div>
		<div id="warn_paid" class="mask" style="display:none;">
			<div class="warnning_box">
				<p class="tishi">温馨提示<span class="fr close"></span></p>
				<div class="warn_text">
					<p>尊敬的用户您好！该项目已还款，为保护当事人隐私并应借款人要求，该项目相关证据资料已归还当事人并在本网站上不再显示，请选择其他项目投资，谢谢！</p>
				</div>
				<!-- 点击以下标签时回到当前页面选中"热卖中"状态 -->
				<a href="" class="tzqt">投资其他项目</a>
			</div>
		</div>
		<script>
			$(function(){
				$(".product_paid").click(function(){
					$("#warn_paid").show();
				});
				$("#warn_paid .close").click(function(){
					$("#warn_paid").hide();
				});
			});
		</script>
		
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
				//console.log(currentPage);
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
					 $(this).children("a").attr("style","color: white;");
				 }
			 });
		</script>
	</body>
</html>