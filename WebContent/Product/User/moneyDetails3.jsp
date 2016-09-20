<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 资金流水</title>
		<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/account.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/fundRecord.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body>
		<jsp:include page="../header.jsp" />
		
		<!-- 主内容 -->
		<div class="main">
			<div class="layout my_account">
				<jsp:include page="leftMenu.jsp" />
				<!-- 内容页 -->
				<div class="fr my_info">					
					<div class="mingxi">
					<form id="formed" method="post">
						<div class="mingxi_head">
							<ul class="fl" id="liushui">
								<li class="on2"><a id="quanb" data-value="all" style="cursor: pointer;">全部</a></li>
								<li><a id="quanb" data-value="cz" style="cursor: pointer;">充值</a></li>
								<li><a id="congz" data-value="tx" style="cursor: pointer;">提现</a></li>
								<li><a id="touzi" data-value="buy" style="cursor: pointer;">投资</a></li>
								<li><a id="shouyi" data-value="lx" style="cursor: pointer;">收益</a></li>
								<li><a id="fank" data-value="back" style="cursor: pointer;">返款</a></li>
								<div class="clea"></div>
							</ul>
							<div class="fr">
								<input type="text" id="startTime" size="10" maxlength="10" value="${startDate}" readonly="readonly" onclick="WdatePicker()" style="  background-color: white;"/>
								<span>至</span>
								<input type="text" id="endTime" size="10" maxlength="10" value="${endDate}" onclick="WdatePicker()" readonly="readonly" style="  background-color: white;"/>
								<input type="hidden" id="queryType" value="${type}">
								<a class="sereach" id="queryFundRecord" >查询</a>
							</div>
							<div class="clea"></div>
						</div>
					</form>
						<table style="text-align: center; width: 100%;  margin-top: 10px;">
						<tr>
							<th style="width: 5%;">序号</th>
							<th style="width: 18%;">时间</th>
							<th style="width: 10%;">交易类型</th>
							<th style="width: 14%;">交易金额（元）</th>
							<th style="width: 14%;">帐户余额（元）</th>
							<th style="width: 39%;">备注</th>
						</tr>
						<c:forEach items="${fundRecordList}" var="list" varStatus="index">
						<tr style="height:50px;">
							<td>${index.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
							<td><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td title="${list.note}">${list.jylx}</td>
							<td>
							<c:if test="${list.status eq 0}"><span style="color: red;">+<fmt:formatNumber value="${list.money *0.01}" pattern="#,##0.##"/></span></c:if>
							<c:if test="${list.status eq 1}"><span style="color: green;">-<fmt:formatNumber value="${list.money *0.01}" pattern="#,##0.##"/></span></c:if>
							</td>
							<td><fmt:formatNumber value="${list.usersCost *0.01}" pattern="#,##0.##"/></td>
							<td style="text-align: center;">${list.note}</td>
						</tr>
						</c:forEach>
						</table>
						<c:if test="${fundRecordList eq null}">
						<div class="wujilu">
							<span>暂无记录</span>
						</div>
		            	</c:if>
		            	<div align="right" style="margin: 15px;">
		            	<c:if test="${fundRecordList ne null}"><jsp:include page="../page.jsp" /></c:if>
		            	</div>
					</div>
				</div>
				<!-- 内容页 end-->
				<div class="clea"></div>
			</div>
			
		
		</div>
		<!-- 主内容 end-->
		<jsp:include page="../footer.jsp" />	
		
		<script>
			$(function(){
				$("#liushui li").click(function(){
					$("#liushui").find(".on2").removeClass("on2");
					$(this).addClass("on2");	
					$("#queryType").val($(this).find("a").attr("data-value"));
					window.location.href=getQueryUrl();
				});
				//资金流水列表点击效果
			});
		</script>
		<script type="text/javascript">
		$("#liushui li").each(function(){
			if($(this).find("a").attr("data-value") == "${type}"){
				$(this).addClass('on2').siblings().removeClass('on2');
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
	</body>
</html>