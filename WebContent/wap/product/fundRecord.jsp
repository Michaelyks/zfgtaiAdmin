<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		<meta name="apple-touch-fullscreen" content="yes">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		 <meta name="format-detection" content="telephone=no">
		<title>资金流水</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/wap/product/css/capital.css" />
	</head>
<body>
   
	<div class="wapper header2">
		<div class="head">
			<span class="time-month">日期</span>
			<p class="time-date" style="text-align: center;">查询资金流水记录</p>
		</div>
	</div>	
	<div class="space-top"></div>
	<div class="wapper">
		<div class="section">
			<c:choose>
			<c:when test="${fundRecordList ne '[]' &&  fundRecordList ne '' && fundRecordList ne null}">
				<div class="list-box">
				<div id="list">
				<ul class="bill-list" id="ul_list">
				<c:forEach items="${fundRecordList}" var="list">
				<c:choose>
					<c:when test="${list.jylx eq '充值' }">
						<li class="bill-cz">
					</c:when>
					<c:when test="${list.jylx eq '返款' }">
						<li class="bill-fk">
					</c:when>
					<c:when test="${list.jylx eq '收益' }">
						<li class="bill-sy">
					</c:when>
					<c:when test="${list.jylx eq '提现' }">
						<li class="bill-tx">
					</c:when>
					<c:when test="${list.jylx eq '投资' }">
						<li class="bill-tz">
					</c:when>
					<c:otherwise>
						<li class="bill-qt">
					</c:otherwise>
				</c:choose>
						<span class="time-day"><fmt:formatDate value="${list.insertTime}" pattern="M月"/><br/><fmt:formatDate value="${list.insertTime}" pattern="dd日"/></span>
						<div class="bill-detail">
							<p class="time-trading">交易时间：<fmt:formatDate value="${list.insertTime}" pattern="yyyy/MM/dd   HH:mm:ss"/></p>
							<div class="trading">
								<span class="bill-in"></span>	
								<div class="trading-box">
									<p class="line">交易类型：${list.jylx}</p>
									<p class="line">交易本金：<span class="color"><fmt:formatNumber value="${list.money * 0.01}" pattern="0.00"/></span></p>
									<c:if test="${list.jylx eq '投资'}">
									  <p class="line">投  资  券：<span class="color"><fmt:formatNumber value="${list.couponValue}" pattern="0.00"/></span></p>
									</c:if>	
									<p class="line">账户余额：<fmt:formatNumber value="${list.usersCost * 0.01}" pattern="0.00"/></p>
									<p class="line">交易说明：${list.note}</p>
								</div>
							</div>
						</div>
					</li>
				</c:forEach>
				</ul>
				<div class="onload">
				<a href="javascript:;" id="loadmore" class="onload-word">点击加载更多...</a>
					<p class="onload-img" onclick="loadMore()"><img src="${pageContext.request.contextPath}/wap/product/img/onload.gif"></p>
				</div>
				<%-- <div class="onloadd" style="display: none;">
				<a href="javascript:void(0);" id="loadmore" class="onload-word" onclick="loadMore()">往下拉加载更多...</a>
					<p class="onload-img"><img src="${pageContext.request.contextPath}/Product/img/onload.gif" /></p>
				</div> --%>
				</div>
				</div>
			</c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
			  		<span style="FILTER: glow(color=#B4BBCF,strength=5)"><FONT face=黑体 color=#8c96b5 size=6><B><I>亲，暂无记录哦</I></B></FONT></span>
			  	</div>
			</c:otherwise>
			</c:choose>
		</div>
 </div>
<script src="${pageContext.request.contextPath}/wap/product/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/wap/product/js/catital.js"></script>
<script type="text/javascript">
base = "${pageContext.request.contextPath}";
usersId = "${usersId}";
</script>
</body>
</html>
