<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  errorPage="page_404.jsp" %>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
     <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>登录-中泰理财账户,安全,便捷,高效的投资理财平台</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/account.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
		<jsp:include page="../header.jsp" />
		
		<!-- 主内容 -->
		<div class="main">
			<div class="layout my_account">
			<jsp:include page="leftMenu.jsp" />
			
				<!-- 内容页 -->
				<div class="fr my_info">
					<div class="baozhan">
						<div class="user_img">
							<img  src="${pageContext.request.contextPath}/Product/images/Account_user_img.png"/>
							<div class="account_wsd">
								<span>欢迎您，${usersLogin.username2}，上次登录时间为：</span>
								<ul class="renzhen">
									<li class="rz_l1 tubiao">
									<c:choose>
	                					<c:when test="${users.usersInfo.isVerifyPhone ==1}">
	                    					<p class="s_OK"></p>
	                    				</c:when>
	                    				<c:otherwise>
	                    					<p class="s_false"></p>		
	                    				</c:otherwise>
	                    			</c:choose>
	                    			</li>
	                    			<li class="yincang_box yincang_box1" style="display:none;">
	                    			<c:choose>
	                					<c:when test="${users.usersInfo.isVerifyPhone ==1}">
	                						<div class="yincang">手机号已绑定</div>
	                    				</c:when>
	                    				<c:otherwise>
	                    					<div class="yincang">手机号未绑定，&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action">立即绑定</a></div>
	                    				</c:otherwise>
	                    			</c:choose>
	                    				<b class="sanjiao"></b>
	                    			</li>
	                    			<li class="rz_l2 tubiao">
	                     			<c:choose>
                    					<c:when test="${users.usersInfo.isVerifyIdcard ==1}">
                    						<p class="s_OK"></p>
                    					</c:when>
                    					<c:otherwise>
                    						<p class="s_false"></p>
                    					</c:otherwise>
                   	 				</c:choose>
                   	 				</li> 
                   	 				<li class="yincang_box yincang_box2" style="display:none;">
                   	 				<c:choose>
                    					<c:when test="${users.usersInfo.isVerifyIdcard ==1}">
                    						<div class="yincang">已实名认证</div>
                    					</c:when>
                    					<c:otherwise>
                    						<div class="yincang">未实名认证，&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action">立即认证</a></div>
                    					</c:otherwise>
                   	 				</c:choose>
                   	 					<b class="sanjiao"></b>
	                    			</li>
                   	 				<li  class="rz_l3 tubiao">
                   	 				<c:choose>
                    					<c:when test="${users.usersInfo.isVerifyIdcard ==1}">
                    						<p class="s_OK"></p>
                    					</c:when>
                    					<c:otherwise>
                    						<p class="s_false"></p>
                    					</c:otherwise>
                   	 				</c:choose>
                   	 				</li>
                   	 				<li class="yincang_box yincang_box3" style="display:none;">
                   	 				<c:choose>
                    					<c:when test="${users.usersInfo.isVerifyIdcard ==1}">
                    						<div class="yincang">已设置交易密码</div>
                    					</c:when>
                    					<c:otherwise>
                    						<div class="yincang">未设置交易密码，&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action">立即设置</a></div>
                    					</c:otherwise>
                   	 				</c:choose> 
                   	 					<b class="sanjiao"></b>
	                    			</li>  
									<li>
										<p class="jindutiao"><b id="data_progress"></b></p>
										<label>安全等级：<span class="red" id="data_progress_text">低</span></label><!-- <span id="data_progress_span">未完善</span> -->
									</li>
									<li id="tisheng" class="tisheng" style="display:none;"><a href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action">立即提升</a></li>
									<div class="clea"></div>
								</ul>
								
								<!-- 安全等级模块操作 -->
								<script>
									$(function(){
										/*"当安全等级为高时，"立即提升"链接隐藏，其他状态时显示*/
										if($("#data_progress_text").html()!="高"){
											$("#tisheng").show();
										}else{
											$("#tisheng").hide();
										}
										
										/*鼠标在图标上悬浮和移动时显示隐藏说明文本框*/
										$(".renzhen .tubiao").mouseover(function(){
											$(this).next(".yincang_box").show();
										});
										/*鼠标下移时说明文本框不关闭（需等候链接操作）*/
										$(".renzhen .tubiao").mouseleave(function(e){
											var x=e.offsetX;
											var y=e.offsetY;
											//console.log(x);
											//console.log(y);
											if(x<0||x>19||y<0){
												$(this).next(".yincang_box").hide();
											}	
										});
										$(".renzhen .yincang_box").mouseleave(function(){
											$(this).hide();
										});
										
									});
								</script>
							</div>
							<div class="yhq_tishi">
								<p>优惠券<span class="wenhao"></span></p>
								<div class="yincang" style="display:none;">平台赠送的优惠券，用来抵购理财产品。<b class="sanjiao"></b></div>
								<a class="red"  href="${pageContext.request.contextPath}/Product/User/myCoupon!loadCoupon.action"><span>1张</span></a>
							</div>
						</div>
						
						<!-- 问号悬浮显示提示文本 -->
						<script>
							$(function(){
								$(".wenhao").hover(function(){
									$(this).parent().next(".yincang").show();
								});
								$(".wenhao").mouseleave(function(){
									$(this).parent().next(".yincang").hide();
								});
							})
						</script>
						
					</div>
					
					<div class="accunt_info">
						<ul class="zichan_box">
							<li>
								<p>资产总额<span class="wenhao"></span></p>
								<div class="yincang" style="display:none;">说明文本说明文本说明文本<b class="sanjiao"></b></div>
								<span><fmt:formatNumber value="${users.usersInfo.leftMoney * 0.01 + freezeMoney}" pattern="0.00"/></span>
							</li>
							<li class="dengyu"></li>
							<li>
								<p>可用余额<span class="wenhao"></span></p>
								<div class="yincang" style="display:none;">用来购买平台产品。<b class="sanjiao"></b></div>
								<span><fmt:formatNumber value="${users.usersInfo.leftMoney * 0.01}" pattern="0.00"/></span>
							</li>
							<li class="jiahao"></li>
							<li>
								<p>冻结资金<span class="wenhao"></span></p>
								<div class="yincang" style="display:none;">包括：已购买营销中产品总额+提现申请金额。<b class="sanjiao"></b></div>
								<span><fmt:formatNumber value="${freezeMoney}" pattern="0.00"/></span>
							</li>
							<li class="jiahao"></li>
							<li>
								<p>待收收益<span class="wenhao"></span></p>
								<div class="yincang" style="display:none;">已购买营销中的产品预计收益。<b class="sanjiao"></b></div>
								<span><fmt:formatNumber value="" pattern="0.00"/>0.00</span>
							</li>
							<div class="clea"></div>
						</ul>
						<div id="money" class="money">
							<canvas id="tongjitu" width="250px" height="250px"></canvas>
							<ul class="precent">
								<li class="kyye"><i></i>可用余额<span id="keyong">2500.00</span></li>
								<li class="djzj"><i></i>冻结资金<span id="dongjie">4200.00</span></li>
								<li class="dssy"><i></i>待收收益<span id="shouyi">3300.00</span></li>
							</ul>
							<!-- 圆环比例图（h5） -->
							<script>
								$(function(){
									var context = document.getElementById("tongjitu").getContext("2d");
									var ky=parseFloat($("#keyong").html());
									var dj=parseFloat($("#dongjie").html());
									var sy=parseFloat($("#shouyi").html());
									var total=ky+dj+sy;
									
									//将数据分解成扇形角度比例	
									var ky_deg=ky*360/total;
									var dj_deg=dj*360/total;
									var sy_deg=sy*360/total;
									
									//定义绘制扇形的方法
									CanvasRenderingContext2D.prototype.sector = function (x, y, radius, sDeg, eDeg) {
										  // 初始保存
										  this.save();
										  // 位移到目标点
										  this.translate(x, y);
										  this.beginPath();
										  // 画出圆弧
										  this.arc(0,0,radius,sDeg, eDeg);
										  // 再次保存以备旋转
										  this.save();
										  // 旋转至起始角度
										  this.rotate(eDeg);
										  // 移动到终点，准备连接终点与圆心
										  this.moveTo(radius,0);
										  // 连接到圆心
										  this.lineTo(0,0);
										  // 还原
										  this.restore();
										  // 旋转至起点角度
										  this.rotate(sDeg);
										  // 从圆心连接到起点
										  this.lineTo(radius,0);
										  this.closePath();
										  // 还原到最初保存的状态
										  this.restore();
										  return this;
										 }
									//绘制比例饼状图
									var deg = Math.PI/180;
									if(ky==0&&dj==0&&sy==0){
										//当数据均为0时，绘制三个等分扇形
										context.fillStyle="#FFA100";
										context.sector(125,125,125,270*deg,390*deg).fill();
										context.fillStyle="#FFC20E";
										context.sector(125,125,125,390*deg,510*deg).fill();
										context.fillStyle="#00D7A9";
										context.sector(125,125,125,510*deg,630*deg).fill();
									}else{
										context.fillStyle="#FFA100";
										context.sector(125,125,125,270*deg,(270+ky_deg)*deg).fill();
										context.fillStyle="#FFC20E";
										context.sector(125,125,125,(270+ky_deg)*deg,(270+ky_deg+dj_deg)*deg).fill();
										context.fillStyle="#00D7A9";
										context.sector(125,125,125,(270+ky_deg+dj_deg)*deg,(270+ky_deg+dj_deg+sy_deg)*deg).fill();
									}
									
									//遮盖白色实心圆形成圆环比例图
									context.fillStyle = "#fff";
									context.beginPath();
									context.arc(125,125,105,0,Math.PI*2);
									context.closePath();
									context.fill();
								});
							</script>
							<a id="chongzhi" class="account_cz" href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action">充值</a><a href="${pageContext.request.contextPath}/Product/User/userRecharge!getMoney.action">提现</a>
						</div>
						<script>
							$(function(){
								$("#money>a").mouseover(function(){
									$("#money>a").removeClass("account_cz");
									$(this).addClass("account_cz");
								}).mouseleave(function(){
									$("#money>a").removeClass("account_cz");
									$("#money>#chongzhi").addClass("account_cz");
								});
							});
						</script>
						<ul class="shouyi_show">
							<li>
								<p>待收收益<span class="wenhao"></span></p>
								<div class="yincang daishou" style="display:none;">已购买营销中的产品预计收益。<b class="sanjiao"></b></div>
								<span class="profit red"><fmt:formatNumber value="" pattern="0.00"/>0.00</span>
							</li>
							<li>
								<p>累计收益(元)<span class="wenhao"></span></p>
								<div class="yincang" style="display:none;">说明文本说明文本说明文本<b class="sanjiao"></b></div>
								<span class="profit red"><fmt:formatNumber value="${users.usersInfo.totalProfit * 0.01}" pattern="0.00"/></span>
							</li>
						</ul>
						
						<div class="recharge_jilu">
							<table class="tb_AcIndex">
								<tbody>
									<tr class="list_title">
										<td>序号</td>
										<td>投资时间</td>
										<td>项目名称</td>
										<td>投资金额(元)</td>
										<!-- <td>收益率</td> -->
										<td>预计收益(元)</td>
										<td>到期时间</td>
										<!-- <td>结算时间</td> -->
										<td>状态</td>
									</tr>		
									<c:forEach items="${investorsList}" var="list" varStatus="s">
									<tr ${s.count % 2 == 0 ? 'style=\"background-color: #FCFCFC;\"':''} >
									<td>${s.count + (currentPage-1)*pageSize}</td>
									<td title="<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd"/></td>
									<td title="${list.product.title}">
									<c:choose>
										<c:when test="${list.product.productType eq '1'}">
											<a href="${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?productId=${list.product.id}" target="_blank">${myel:getSubString(list.product.title,26)}</a>
										</c:when>
										<c:otherwise>
											<a href="${pageContext.request.contextPath}/Product/index!showProductDetails.action?productId=${list.product.id}" target="_blank">${myel:getSubString(list.product.title,26)}</a>
										</c:otherwise>
									</c:choose>
									</td>
									<td title="投入本金<fmt:formatNumber value="${list.inMoney*0.01}" pattern="#,##0.##" type="number" />元,投资券<fmt:formatNumber value="${list.coupon*0.01}" pattern="#,##0.##" type="number" />元">
									<fmt:formatNumber value="${list.copies}" pattern="#,##0.##" type="number" />
									</td>
									<!-- <td class="red" title="${list.annualEarnings}"><fmt:formatNumber value="${list.annualEarnings}" pattern="0.##" type="number" />%</td> -->
									<td title="<fmt:formatNumber value="${list.expectEarnings*0.01}" pattern="0.00" type="number" />"><fmt:formatNumber value="${list.expectEarnings*0.01}" pattern="0.00" type="number" /></td>
									<td title="<fmt:formatDate value="${list.finishTime}" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${list.finishTime}" pattern="yyyy-MM-dd"/></td>
									<!-- <td title="<fmt:formatDate value="${list.clearTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${list.clearTime}" pattern="yyyy-MM-dd"/></td> -->
									<td title="${list.tzzt}">${list.tzzt}</td>
								</tr>
								</c:forEach>					
								</tbody>
							</table>		
						</div>
						<c:choose>
							<c:when test="${investorsList eq null || investorsList eq '' || investorsList eq '[]' }">
								<div class="tzjl">
								<div class="touzi_tip">
									<a href="${pageContext.request.contextPath}/Product/productCategory!loadProduct.action">立即投资</a>
								</div>
								</div>
							</c:when>
							<c:otherwise>
							<div style="bottom: 55px;text-align: center;  margin-top: 20px;">
								<a  style="display: inline-block; width: 90px;  line-height: 38px;text-align: center;color: #fff;font-size: 16px;background: #ff776d;border-radius: 5px;" href="${pageContext.request.contextPath}/Product/User/investorsRecord!getInvestorsRecords.action">查看更多项目</a>
							</div>
							</c:otherwise>
						</c:choose>
						</div>
					</div>	
					<div class="clea"></div>
				</div>
				<!-- 内容页 end-->
				<div class="clea"></div>
			</div>
		</div>
		<!-- 主内容 end-->
		<jsp:include page="../footer.jsp" />
	</body>
	<script type="text/javascript">
		var val = "${safeLevel}";
		val = parseFloat(val)*3;
		if(val==90)
			val=100;
		$("#data_progress").animate({width:val+"%"},1000);
		if(val<60)
			$("#data_progress_text").text("低");
		else if(val>=60 && val<100)
			$("#data_progress_text").text("中");
		else{
			$("#data_progress_text").text("高");
			$("#data_progress_span").text("已完善");
		}
	</script>
</html>

