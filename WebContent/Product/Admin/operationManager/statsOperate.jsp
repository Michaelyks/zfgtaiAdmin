<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户转换数据报表</title>
<link
	href="${pageContext.request.contextPath}/Product/Admin/css/public.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css"
	rel="stylesheet" type="text/css" />
<script
	src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css"
	type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script
	src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
<style type="text/css">
.sereach {
	width: 200px;
	height: 32px;
	line-height: 32px;
	text-align: center;
	border: 1px solid #009DDA;
	border-radius: 5px;
}

.select1 {
	border-radius: 5px;
	border-color: #009DDA;
	margin-right: 5px;
}

.a {
	color: blue;
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="center">
		<jsp:include page="/Product/Admin/common/head.jsp" />
		<div class="main" align="center">
			<h3>数据统计</h3>
			<form id="frm"
				action="${pageContext.request.contextPath}/Product/Admin/usersConvert!loadUsersConvert.action">
			</form>
			<table border="1" cellspacing="0" cellpadding="0" width="70%"
				style="text-align: center;">
				<tr>
					<td width="30%">用户投资的待结算的余额</td>
					<td><fmt:formatNumber value="${map.stillBalanceUserCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">产品3日内到期项目总额</td>
					<td><fmt:formatNumber value="${map.dueThreeDayProductCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">用户投资3日内到期项目总额</td>
					<td><fmt:formatNumber value="${map.dueThreeDayUserCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">产品待结算的余额</td>
					<td><fmt:formatNumber value="${map.stillBalanceProductCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">已到期未结算的金额</td>
					<td><fmt:formatNumber value="${map.dueNoBalanceCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				
				<%--
				<tr>
					<td width="200px;">注册人数</td>
					<td><fmt:formatNumber value="${map.regUserSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">绑卡人数</td>
					<td><fmt:formatNumber value="${map.bindBankUserSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">投资次数</td>
					<td><fmt:formatNumber value="${map.investCount / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">首投次数</td>
					<td><fmt:formatNumber value="${map.firstInvestCount / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">首投人数</td>
					<td><fmt:formatNumber value="${map.firstInvestUserSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">首投金额</td>
					<td><fmt:formatNumber value="${map.firstInvestCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">首投率</td>
					<td><fmt:formatNumber value="${0}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">复投次数</td>
					<td><fmt:formatNumber value="${map.reInvestCount / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">复投金额</td>
					<td><fmt:formatNumber value="${map.reInvestCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">复投人数</td>
					<td><fmt:formatNumber value="${map.reInvestUserSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">复投率</td>
					<td><fmt:formatNumber value="${0}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">新增二次投资次数</td>
					<td><fmt:formatNumber value="${map.newTwoInvestCount / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">新增二次投资人数</td>
					<td><fmt:formatNumber value="${map.newTwoInvestUserSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">新增二次投资金额</td>
					<td><fmt:formatNumber value="${map.newTwoInvestCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">新增二次投资率</td>
					<td><fmt:formatNumber value="${0}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">产品金额</td>
					<td><fmt:formatNumber value="${0 / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">投资金额</td>
					<td><fmt:formatNumber value="${map.investCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">投资劵金额</td>
					<td><fmt:formatNumber value="${map.couponCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">已还本金</td>
					<td><fmt:formatNumber value="${map.repayPrincipalCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">已还利息</td>
					<td><fmt:formatNumber value="${map.repayInterestCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">充值次数</td>
					<td><fmt:formatNumber value="${map.rechargeCount / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">充值人数</td>
					<td><fmt:formatNumber value="${map.rechargeUserSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">充值金额</td>
					<td><fmt:formatNumber value="${map.rechargeCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">提现次数</td>
					<td><fmt:formatNumber value="${map.withdrawCount / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">提现人数</td>
					<td><fmt:formatNumber value="${map.withdrawUserSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">提现金额</td>
					<td><fmt:formatNumber value="${map.withdrawCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">成功提现次数</td>
					<td><fmt:formatNumber value="${map.successWithdrawCount / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">成功提现人数</td>
					<td><fmt:formatNumber value="${map.successWithdrawUserSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr>
				<tr>
					<td width="200px;">成功提现金额</td>
					<td><fmt:formatNumber value="${map.successWithdrawCentSum / 100}" pattern="###,###,##0.00"></fmt:formatNumber> </td>
				</tr> --%>
			</table>
		</div>
	</div>
</body>
</html>