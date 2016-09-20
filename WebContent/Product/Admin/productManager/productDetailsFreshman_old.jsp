<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
    	 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 新手专享产品详情</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/product_detail.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
		<style type="text/css">
		#productDescription table{
			width:100%;
		}
		</style>
	</head>
	<body>
		<jsp:include page="../common/header.jsp" />
		<!-- 主内容 -->
		<div class="main">
			<!-- 产品投资头部 -->
			<div class="product_head">
			<form id="buyNowFrom" method="post">
			<input type="hidden" name="inv.productId" value="${product.id}">
				<div class="layout">
					<div class="fl">
						<div class="product_name">优车投No.001 <a>项目到期：<span>2015-06-30</span></a></div>
						<ul>
							<li>年化收益</li>
							<li>项目总额</li>
							<li>投资期限</li>
							<li>起投金额</li>
							<li class="jdt">当前进度</li>
							<div class="clea"></div>
						</ul>
						<ul>
							<li><span class="b_red"><fmt:formatNumber value="${product.annualEarnings}" pattern="#.#"/></span><a>%</a></li>
							<li><span><fmt:formatNumber value="${product.financingAmount * 0.01}" pattern="#,###.#"/></span>元</li>
							<li><span style="color: red; ">${product.lcqx}</span>天</li>
							<li><span><fmt:formatNumber value="${product.qtje * 0.01}" pattern="#,###.##"/></span>元</li>
							<%-- <li class="jdt"><p class="jindutiao"><b class="qwy_progress" data-value="${product.wcjd}" style="width:0px;"></b></p><span><fmt:formatNumber value="${product.wcjd * 100}" pattern="#.#"/>%</span></li> --%>
							<li class="jdt"><span style="color: red; font-size: 20px;">营销中</span></li>
							<div class="clea"></div>
						</ul>
						<div class="pay_modth">
							<p><span>起息日：</span>${product.jxfs}</p>
							<p><span>还款方式：</span>${product.fxfs}</p>
							<a>计算收益</a>
						</div>
					</div>
					<div class="fr">
						<p><a class="zw">剩余金额</a><span><fmt:formatNumber value="${product.leftCopies}" pattern="#,###.#"/></span><a>元</a></p>
						<p><a class="zw">投资金额</a><input id="input_copies" type="text" name="inv.copies" value="100" disabled="disabled"/><a>元</a></p>
						<div class="rookie_tip">新手福利：投100元抵1000元<div class="triangle"></div></div>
						<a class="touzi" style="cursor: pointer;" id="buyNow" >立即投资</a>
						<img class="zhuanxiang" src="images/product_xszx.png"/>
					</div>
					<div class="clea"></div>
				</div>
				</form>
			</div>
			<!-- 产品投资头部 end-->
			
			<!-- 专业安全的管理 -->
			<ul class="manage">
				<li class="mana_li1">
					<p>交易实时监控</p>
					<span>7*24全天交易监控</span>
				</li>
				<li class="mana_li2">
					<p>全实名认证交易</p>
					<span>所有投资者必须实名认证</span>
				</li>
				<li class="mana_li3">
					<p>资金认证提现</p>
					<span>资金只能提现到您名下的银行</span>
				</li>
				<li class="mana_li4">
					<p>信息安全保障</p>
					<span>腾讯云提供最安全技术</span>
				</li>
				<div class="clea"></div>
			</ul>
			<!-- 专业安全的管理 end-->
			
			<!-- 产品投资底部 -->
			<div class="product_bottom">
				<ul class="product_title" id="product_title">
					<li class="on">项目详情</li>
					<li>信息披露</li>
					<li>法律意见书</li>
					<li>投资记录</li>
					<div class="clea"></div>
				</ul>			
				<div class="product_Content">
					<div class="content_list">
						<div class="list_title" style="margin-bottom: 20px;border-bottom: 1px solid #e6e6e6;padding-bottom: 10px;">
							<h4>项目详情内容</h4>
						</div>
						<div id="productDescription">${product.description}</div>
					</div>
					<div class="content_list">
						<div class="list_title">
							<h4>信息披露</h4>
							<div class="title_tip">
								<p>项目相关图片</p>
								<span>100%律师监督下实物拍摄，盗图必究</span>
							</div>
						</div>
						<c:forEach items="${infoList}" var="list">
						<p>&nbsp;</p>
						<img src="${list}" alt="信息披露图片" style="width:100%;"/>
						</c:forEach>
						<!-- <div class="product_img">暂无图片</div> -->
					</div>
					<div class="content_list">
						<div class="list_title">
							<h4>法律意见书</h4>
							<div class="title_tip">
								<p>项目相关图片</p>
								<span>100%律师监督下实物拍摄，盗图必究</span>
							</div>
						</div>
						<c:forEach items="${lawList}" var="list">
						<p>&nbsp;</p>
						<img src="${list}" alt="法律意见书图片" style="width:100%;"/>
						</c:forEach>
						<!-- <div class="product_img">暂无图片</div> -->
					</div>
					<div class="content_list tab">
						<div class="list_title">
							<h4>投资记录</h4>
						</div>
						<div>
							<table class="tzjl">
								<tbody>
									<tr>
										<td>用户名</td>
										<td>投资金额(元)</td>
										<td>投资时间</td>
										<td>投资状态</td>
									</tr>
									<c:forEach items="${investorsList}" var="list">
			                			<tr>
			                				<td>${list.username}</td>
			                				<td><fmt:formatNumber value="${list.copies}" pattern="#,##0.##"/></td>
			                				<td><fmt:formatDate value="${list.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                				<td><i class="status_yes"></i>投资成功</td>
			                			</tr>
		                			</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
				</div>
			</div>
			<!-- 产品投资底部 end-->
		
		</div>
		<!-- 主内容 end-->
		
		<jsp:include page="../common/footer.jsp" />
		
		<script>
			
			$(function(){
				$(".content_list").eq(0).css("display","block");
				$("#product_title li").click(function(){
					$("#product_title").find(".on").removeClass("on");
					$(this).addClass("on");
					var ind = $(this).index();
					$(".content_list").css("display","none")
					$(".content_list").eq(ind).css("display","block");
					
				})
			});
			function initProgress(){
				$(".qwy_progress").each(function(){
					//alert(1);
					var val = $(this).attr("data-value");
					val = parseFloat(val)*100;
					$(this).animate({width:val+"%"},1000);
				});
			};
			initProgress();
		</script>
		
		
		
		
		<script type="text/javascript">
		function checkInput(){
			var leftCopies= "${product.leftCopies}";//剩余可投资
			var atleastMoney="${product.qtje}";//起投金额
			var status="${product.productStatus}";
			var num=document.getElementById("input_copies").value;
			var endTime="${product.endTime}";
			//var date1= new Date(Date.parse(strTime.replace(/-/g,   "/"))); 
			//var date=new Date();
			//alert(format(date));
			//alert(endTime);
			var strTime=$("#endTime").text();
			var date1= new Date(Date.parse(strTime.replace(/-/g,   "/"))); 
			var date=new Date();
			if("${usersLogin.isBindBank}"!="1"){
				jingao("必须先绑定银行卡才能投资!", "提示", function(){
					window.location.href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action";
				}, 0);
				return false;
			}
			if(isEmpty(num)){
				jingao("起投金额不能为空","提示","",0);
		 		return false;
			}else
			if(isNaN(num)){
				jingao("起投金额不能为非数字","提示","",0);
		 		return false;
			}else
			if(!(/(^[1-9]\d*$)/.test(num))){
				jingao("起投金额只能是正整数","提示","",0);
		 		return false;
			}else
			if(endTime<=format(new Date())){
				jingao("该投资已过截止期","提示","",0);
				return false;
			}else 
			if (date1.valueOf()<=date.valueOf()) {
				jingao("该投资已过期","提示","",0);
				return false;
			}else
			if(num*100<atleastMoney){
				jingao("投资金额必须大于起投金额","提示","",0);
				return false;
			}else
			if(num.length>9){
				jingao("请输入小于等于九位数的金额","提示","",0);
				return false;
			}if(status!=0){
				jingao("非营销中无法购买","提示","",0);
				return false;
			}else
			if(num*1>leftCopies){
					jingao("投资金额必须小于剩余金额","提示","",0);
					return false;
			}else 
			if(0<leftCopies<=atleastMoney*0.01*1.5){
				if(num*1!=leftCopies){
					jingao("当剩余份数小于起投金额的1.5倍时，购买量只能为起投金额","提示","",0);
					return false;
				}else{
					return true;
				}
			}else{
				return true;		
			}
			
		}
		
		
		
		</script>
	</body>
</html>