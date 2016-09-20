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
		<jsp:include page="../common/header.jsp" />
		
		<!-- 主内容 -->
		<div class="main">
			<div class="layout my_account">
			<jsp:include page="leftMenu.jsp" />
				<!-- 内容页 -->
				<div class="fr my_info">
					<div class="title">我的账户 </div>
					<c:if test="${users ne null && users.username ne '' && users.username ne null}"><div  class="title">${myel:jieMiUsername(users.username)}</div></c:if>
					<div class="baozhan">
						<div class="account_wsd">
							<label>资料完善度：</label><p class="jindutiao"><b id="data_progress"></b></p><span class="red" id="data_progress_text">低</span><span id="data_progress_span">未完善</span>
						</div>
						<div class="account_bz">中泰理财为您提供银行级别的帐户和资金安全保障</div>
					</div>
					<ul class="renzhen">
						<c:choose>
	                		<c:when test="${users.usersInfo.isVerifyPhone ==1}">
	                    		<li class="rz_l1">手机已绑定</li>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<li class="rz_l1">手机未绑定</li>
	                    	</c:otherwise>
	                    </c:choose>
	                     <c:choose>
                    	<c:when test="${users.usersInfo.isVerifyIdcard ==1}">
                    		<li class="rz_l2">实名已认证</li>
                    	</c:when>
                    	<c:otherwise>
                    		<li class="rz_l2 status_fal">未实名认证<a>立即认证</a></li>
                    	</c:otherwise>
                   	 	</c:choose>    
						<li class="rz_l3">密码已修改</li>
						<div class="clea"></div>
					</ul>
					<div class="accunt_info">
						<p class="account_zzc">账户总资产(元)</p>
						<div class="money">
							<span style="color: red;"><fmt:formatNumber value="${users.usersInfo.leftMoney * 0.01 + freezeMoney + coupon}" pattern="0.00"/></span><a class="account_cz" >充值</a><a>提现</a>
						</div>
						<ul class="jiner">
							<li>可用余额<i><div class="hide_tip">用来购买平台产品<div class="triangle"></div></div></i></li>
							<li>冻结金额<i><div class="hide_tip hid_t2">包括 已购买营销中的产品总额 和 提现申请 的金额<div class="triangle"></div></div></i></li>
							<li>投资券金额<i><div class="hide_tip hid_t2">平台赠送的投资券，用来抵购理财产品<div class="triangle"></div></div></i></li>
							<div class="clea"></div>
						</ul>
						<ul class="jiner jiner2">
							<li><span style="color: red;"><fmt:formatNumber value="${users.usersInfo.leftMoney * 0.01}" pattern="0.00"/></span></li>
							<li><span style="color: red;"><fmt:formatNumber value="${freezeMoney}" pattern="0.00"/></span></li>
							<li><span style="color: red;"><fmt:formatNumber value="${coupon}" pattern="0.00"/></span><a>去使用</a></li>
							<div class="clea"></div>
						</ul>
						<div class="height3"></div>
						<p class="account_zsy">产品总收益(元)</p>
						<span class="profit" style="color: red;"><fmt:formatNumber value="${users.usersInfo.totalProfit * 0.01}" pattern="0.00"/></span>
						<div class="recharge_jilu">
							<p>投资记录</p>
							<table>
								<tbody>
									<tr class="list_title">
										<td>序号</td>
										<td>项目名称</td>
										<td>投资金额(元)</td>
										<td>投资时间</td>
										<td>收益率</td>
										<td>预计收益(元)</td>
										<td>到期时间</td>
										<td>结算时间</td>
										<td>状态</td>
									</tr>		
									<c:forEach items="${investorsList}" var="list" varStatus="s">
									<tr ${s.count % 2 == 0 ? 'style=\"background-color: #FCFCFC;\"':''} >
									<td>${s.count + (currentPage-1)*pageSize}</td>
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
									<td title="<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd"/></td>
									<td class="red" title="${list.annualEarnings}"><fmt:formatNumber value="${list.annualEarnings}" pattern="0.##" type="number" />%</td>
									<td title="<fmt:formatNumber value="${list.expectEarnings*0.01}" pattern="0.00" type="number" />"><fmt:formatNumber value="${list.expectEarnings*0.01}" pattern="0.00" type="number" /></td>
									<td title="<fmt:formatDate value="${list.finishTime}" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${list.finishTime}" pattern="yyyy-MM-dd"/></td>
									<td title="<fmt:formatDate value="${list.clearTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${list.clearTime}" pattern="yyyy-MM-dd"/></td>
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
									<p>您还没有投资哦，马上开始轻松稳定的中泰理财投资之旅!</p>
									<a >马上投资</a>
								</div>
								</div>
							</c:when>
							<c:otherwise>
							<div style="bottom: 55px;text-align: center;  margin-top: 20px;">
								<a  style="display: inline-block; width: 90px;  line-height: 38px;text-align: center;color: #fff;font-size: 16px;background: #ff776d;border-radius: 5px;" >查看更多</a>
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
		<jsp:include page="../common/footer.jsp" />
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

