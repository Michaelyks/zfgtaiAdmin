<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
    	 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 产品详情</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/product_detail.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/interest.css" rel="stylesheet" type="text/css"/>
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
		<jsp:include page="header.jsp" />
		
		<!-- 主内容 -->
		<div class="main">
			<!-- 产品投资头部 -->
			<div class="product_head">
			<form id="buyNowFrom" method="post">
			<input type="hidden" name="inv.productId" value="${product.id}">
				<div class="layout">
					<div class="fl">
						<div class="product_name">${product.title} <a>项目到期：<span><fmt:formatDate value="${product.finishTime}" pattern="yyyy-MM-dd"/></span></a></div>
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
							<c:choose>
								<c:when test="${product.productStatus eq 0}">
								<li><span style="color: red; ">${product.tzqx}</span>天</li>
								</c:when>
								<c:when test="${product.productStatus eq 1}">
								<li><span style="color: red; ">进行中</span></li>
								</c:when>
								<c:otherwise>
								<li><span style="color: #35c600; ">${product.cpzt}</span></li>
								</c:otherwise>
							</c:choose>
							
							
							<li><span><fmt:formatNumber value="${product.qtje * 0.01}" pattern="#,###.##"/></span>元</li>
							<li class="jdt"><p class="jindutiao"><b class="qwy_progress" data-value="${product.wcjd}" style="width:0px;"></b></p><span><fmt:formatNumber value="${product.wcjd * 100}" pattern="#.#"/>%</span></li>
							<div class="clea"></div>
						</ul>
						<div class="clea"></div>
						<div class="pay_modth">
							<p><span>起息日：</span>${product.jxfs}</p>
							<p><span>还款方式：</span>${product.fxfs}</p>
							<a href="calculator.jsp" target="_blank">计算收益</a>
						</div>
					</div>
					<div class="fr">
					<c:if test="${product.productStatus eq '0'}" >
						<p><a class="zw">剩余金额</a><span><fmt:formatNumber value="${product.leftCopies}" pattern="#,###.#"/></span><a>元</a></p>
						<p><a class="zw">投资金额</a><input id="input_copies" type="text" name="inv.copies" value="" /><a>元</a></p>
						<a class="touzi" style="cursor: pointer;" id="buyNow" >立即投资</a>
					</c:if>
					<c:if test="${product.productStatus ne '0'}" >
						<p><a class="zw">剩余金额</a>${product.cpzt}</p>
						<p><a class="zw">投资金额</a></p>
						<a class="touzi" style="background: #AAAAAB;" >${product.cpzt}</a>
					</c:if>
					</div>
					<div class="clea"></div>
				</div>
				<input type="text" style="display: none;">
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
			<div class="product_bottom"  id="details">
				<ul class="product_title" id="product_title">
					<li class="on">项目详情</li>
					<li>企业信息</li>
					<li>保障措施</li>
					<li>投资记录</li>
					<div class="clea"></div>
				</ul>			
				<div class="product_Content">
					<div class="content_list">
						<div class="list_title" style="margin-bottom: 20px;border-bottom: 1px solid #e6e6e6;padding-bottom: 10px;">
							<h4>项目详情内容</h4>
						</div>
						<div id="productDescription">${product.description}</div>
						<c:if test="${product.description eq '' || product.description eq ' ' || product.description eq null}">
							<div class="product_img" style="font-size: 28px;">暂无描述</div>
						</c:if>
					</div>
					<div class="content_list">
						<div class="list_title">
							<h4>企业信息</h4>
							<div class="title_tip">
								<p>项目相关图片</p>
								<span>100%律师监督下实物拍摄，盗图必究</span>
							</div>
						</div>
						<c:forEach items="${infoList}" var="list">
						<p>&nbsp;</p>
						<img src="${list}" alt="企业信息图片" style="width:100%;"/>
						</c:forEach>
						<c:if test="${infoList eq '' || infoList eq '[]' || infoList eq null}">
							<div class="product_img" style="font-size: 28px;">暂无图片</div>
						</c:if>
					</div>
					<div class="content_list">
						<div class="list_title">
							<h4>保障措施</h4>
							<div class="title_tip">
								<p>项目相关图片</p>
								<span>100%律师监督下实物拍摄，盗图必究</span>
							</div>
						</div>
						<c:forEach items="${lawList}" var="list">
						<p>&nbsp;</p>
						<img src="${list}" alt="保障措施图片" style="width:100%;"/>
						</c:forEach>
						<c:if test="${lawList eq '' || lawList eq '[]' || lawList eq null}">
							<div class="product_img" style="font-size: 28px;">暂无图片</div>
						</c:if>
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
							<c:choose>
					            <c:when test="${investorsList eq '[]' || investorsList eq '' || investorsList eq null}">
					            	<div style="text-align: center;margin-top: 15px;">
					            	<img src="${pageContext.request.contextPath}/Product/images/no_record.png" />
					            	</div>
					            </c:when>
					            <c:otherwise>
						            <%-- <jsp:include page="page.jsp" /> --%>
						            <div align="right" style="margin: 15px;">
						            	<a href="${pageContext.request.contextPath}${url}&currentPage=1">首页</a>
						            	<a id="last" href="${pageContext.request.contextPath}${url}&currentPage=${currentPage-1}">上一页</a>
						            	<a id="next" href="${pageContext.request.contextPath}${url}&currentPage=${currentPage+1}"">下一页</a>
						            	<a href="${pageContext.request.contextPath}${url}&currentPage=${pageCount}">末页</a>
						            </div>
					            </c:otherwise>
				           </c:choose>
						</div>
					</div>
					
				</div>
			</div>
			<!-- 产品投资底部 end-->
		
		</div>
		<!-- 主内容 end-->
		
		<jsp:include page="footer.jsp" />
		
		<script>
			$(function(){
				var currentPage='${currentPage}';
				var pageCount='${pageCount}';
				if(currentPage==1){
					$("#last").css("color","gray");
					$("#last").removeAttr("href");
				} 
				if(currentPage==pageCount){
					$("#next").css("color","gray");
					$("#next").removeAttr("href");
				}
			} );
			$(function(){
				$(".content_list").eq(0).css("display","block");
				$("#product_title").find(".on").removeClass("on");
				$("#product_title li").eq(3).addClass("on");
				$(".content_list").css("display","none")
				$(".content_list").eq(3).css("display","block");
				$("#product_title li").click(function(){
					$("#product_title").find(".on").removeClass("on");
					$(this).addClass("on");
					var ind = $(this).index();
					$(".content_list").css("display","none")
					$(".content_list").eq(ind).css("display","block");
					
				});
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
		
		
		
		$("#buyNow").click(function() {
		//alert($("#buyNowFrom").serialize());
		if ("${usersLogin}" == "") {
			if (confirm("需要登录才能购买是否立即登录?")) {
				window.location.href = "${pageContext.request.contextPath}/Product/usersLogin!saveUrl.action?url=${url}";
				return false;
			} else {
				return false;
			}
		} else {
			$.ajax({
				type : "post",
				async : false,
				url : "${pageContext.request.contextPath}/Product/productCategory!isLogin.action?product.id=${product.id}",
				success : function(data) {
					if (data.status == "ok") {
						if (checkInput()) {
							var dialog = art.dialog({
										id : 'dialog_showPayInfo',
										title : false,
										fixed : true,
										content : document.getElementById("psi_load")
									});
							$.ajax({
								type : "post",
								url : "${pageContext.request.contextPath}/Product/User/buyCar!showPayInfo.action",
								data : $("#buyNowFrom").serialize(),
								success : function(data) {
									dialog.content(data);
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
							window.location.href = "${pageContext.request.contextPath}/Product/usersLogin!saveUrl.action?url=${url}";
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
		});
		</script>
	</body>
</html>