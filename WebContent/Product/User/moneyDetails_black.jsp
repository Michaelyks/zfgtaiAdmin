<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<title>中泰理财 - 我的账户 - 资金流水</title>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<link href="../css/account.css" rel="stylesheet" type="text/css">
<link href="../css/recharge.css?" +Math.random(); rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery.superslide.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
	<jsp:include page="../top.jsp" />
	<div class="lujin">
		<div class="crumbs">
			<a href="#"><img src="../images/home_ico.png" /></a> &gt; <a
				href="#">我的账户</a> &gt; <a href="#">帐户管理</a> &gt; <em>资金流水</em>
		</div>
	</div>
	<div class="maincnt wrap cf">
		<jsp:include page="leftMenu.jsp" />
		<div class="mainright fr">
			<div class="wdzh">
				<form id="formed" method="post">
					<table class="zjmxsearch">
						<caption>资金明细</caption>
						<tbody>
							<tr>
								<td class="td0">交易类型</td>
								<td class="td10">
								<a id="quanb" class="focus" data-value="all" style="cursor: pointer;">全部</a> 
								<a id="congz" data-value="cz" style="cursor: pointer;">充值</a> 
								<a id="tix" data-value="tx" style="cursor: pointer;">提现</a> 
								<a id="touzi" data-value="buy" style="cursor: pointer;">投资</a>
								<a id="shouyi" data-value="lx" style="cursor: pointer;">收益</a>  
								<a id="fank" data-value="back" style="cursor: pointer;">返款</a></td>
							</tr>
							<tr>
								<td class="td0">起止日期</td>
								<td class="td11">从
								<input class="inputdate" type="text" id="startTime" size="10" maxlength="10" value="${startDate}" readonly="readonly" onclick="WdatePicker()"/> 到 
								<input class="inputdate" type="text" id="endTime" size="10" maxlength="10" value="${endDate}" onclick="WdatePicker()" readonly="readonly"/> 
								<input type="hidden" id="queryType" value="${type}">
								<a class="cx" id="queryFundRecord" style="cursor: pointer;">查询</a>
								</td>
							</tr>
							<tr>
								<td colspan="2"><span id="date_msg" class="s1"></span></td>
							</tr>
						</tbody>
					</table>
				</form>
				<table class="repeatlist" cellpadding="0" cellspacing="0" style="text-align: center;">
					<thead>
						<tr>
							<th>序号</th>
							<th>时间</th>
							<th>交易类型</th>
							<th style="width: 20%;">交易金额（元）</th>
							<th>帐户余额（元）</th>
						</tr>
						<c:forEach items="${fundRecordList}" var="list" varStatus="index">
						<tr>
							<td>${index.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
							<td><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td title="${list.note}">${list.jylx}</td>
							<td>
							<c:if test="${list.status eq 0}"><span style="color: red;">+<fmt:formatNumber value="${list.money *0.01}" pattern="#,##0.##"/></span></c:if>
							<c:if test="${list.status eq 1}"><span style="color: green;">-<fmt:formatNumber value="${list.money *0.01}" pattern="#,##0.##"/></span></c:if>
							</td>
							<td><fmt:formatNumber value="${list.usersCost *0.01}" pattern="#,##0.##"/></td>
						</tr>
						</c:forEach>
					</thead>
					<tbody>
					</tbody>
				</table>
				<!-- <div class="pageindex2">
					<div class="pageindex2">
						<a href="#">1</a>/ <a href="#">1</a>页 <a href="#">首页</a> <a
							href="#">上一页</a> <a href="#">下一页</a> <a href="#">末页</a>
					</div>
				</div> -->
				<c:if test="${fundRecordList eq null}">
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
            	<img src="../images/no_record.png" />
            	</div>
            	</c:if>
				<c:if test="${fundRecordList ne null}"><jsp:include page="../page.jsp" /></c:if>
			</div>
		</div>
	</div>
	<jsp:include page="../footer.jsp" />
</body>
<script type="text/javascript">
	choosePage("3");
	chooseMenu("1");
	$(".td10 a").click(function(e){
		e.preventDefault();
		$(this).addClass('focus').siblings().removeClass('focus');
		$("#queryType").val($(this).attr("data-value"));
		window.location.href=getQueryUrl();
	});
	
	
	$(".td10 a").each(function(){
		if($(this).attr("data-value") == "${type}"){
			$(this).addClass('focus').siblings().removeClass('focus');
		}
	});
	
	$("#queryFundRecord").click(function(){
		window.location.href=getQueryUrl();
	});
	function getQueryUrl(){
		var href="${pageContext.request.contextPath}/Product/User/fundRecord!loadFundRecord.action?";
		href+="type="+$("#queryType").val();
		href+="&startDate="+$("#startTime").val();
		href+="&endDate="+$("#endTime").val();
		return href;
	}
</script>
</html>
