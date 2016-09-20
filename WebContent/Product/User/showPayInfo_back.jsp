<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/tip.js"></script>
<link href="${pageContext.request.contextPath}/Product/css/tip.css" rel="stylesheet" type="text/css">

<title></title>
</head>
<body>
	<div class="ping" style="display:block;">
		<div class="ping_bg"></div>	
			<div class="page">
				<div class="header">每笔资金受特别保护，安全可靠，请放心操作<img id="psi_close" style="cursor: pointer;" src="images/close.jpg"/></div>
				<div class="center">
					<div class="list">
						<ul class="head_ul">
							<li class="change">信息确定</li>
							<li>还付信息表</li>
							<div style="clear:both"></div>
						</ul>
						<ul class="info">
							<li><span>投  资  金</span>：<a class="red_bor"><input readonly="readonly" type="text" value="<fmt:formatNumber value="${inv.inMoney * 0.01}" pattern="#.#"/>" id="psi_money"/>元</a></li>
							<li><span>项目名称</span>：<a class="blue"><em id="psi_title">${product.title}</em></a></li>
							<li><span>年化收益</span>：<a class="red" id="psi_nhsy"><fmt:formatNumber value="${product.annualEarnings}" pattern="#.#"/>%</a></li>
							<li><span>投资期限</span>：<span class="red" id="psi_realName">${product.tzqx} 天</span></li>
							<li><span>产品类型</span>：<span class="red" id="psi_idCard">${product.cplx}</span></li>
							<li><span>总  收  益</span>：<span  class="red" id="psi_allShouYi"><fmt:formatNumber value="${inv.expectEarnings * 0.01}" pattern="#.##"/>元</span></li>
							<li><span>起息日</span>：<span class="red" id="psi_phone" style="width:130px;">${product.jxfs}</span></li>
							<li><span>还款方式</span>：<em id="psi_payInfo"class="blue">${product.fxfs}</em></li>
							<div class="clea"></div>
						</ul>
						<ul class="tab">
							<table>
								<tbody>
									<tr class="head_list">
										<td>您的投资本金：<a class="red" id="psi_money2"><fmt:formatNumber value="${inv.inMoney * 0.01}" pattern="#.#"/>元</a></td>
										<td>年化收益：<a class="red" id="psi_nhsy2"><fmt:formatNumber value="${product.annualEarnings}" pattern="#.#"/>%</a></td>
										<td>起息日：<span class="red" ><fmt:formatDate value="${inv.startTime}" pattern="yyyy-MM-dd"/></span></td>
										<td>项目结束 ：<span class="red" ><fmt:formatDate value="${product.finishTime}" pattern="yyyy-MM-dd"/></span></td>
									</tr>
									<c:forEach items="${listInterest}" var="list">
										<tr>	
											<td><img src="images/click.jpg"/>回报时间：<a class="red"><fmt:formatDate value="${list.returnTime}" pattern="yyyy-MM-dd"/></a></td>
											<td>归还本金：<a class="red"><fmt:formatNumber value="${list.payMoney * 0.01}" pattern="#.##"/>元</a></td>
											<td>投资天数：<a class="red">${list.payDay}天</a></td>
											<td>获得利息：<a class="red"><fmt:formatNumber value="${list.payInterest * 0.01}" pattern="#.##"/>元</a></td>
										</tr> 
									</c:forEach>
									<!-- <tr>	
										<td><img src="images/click.jpg"/>回报时间：2015-05-20</td>
										<td>支付本金：<a class="red">0</a></td>
										<td>支付本金：<a class="red">0</a></td>
										<td>支付l利息：<a class="red">10.45元</a></td>
									</tr>
									<tr class="last_list">	
										<td><img src="images/click.jpg"/>回报时间：2015-05-20</td>
										<td>支付本金：<a class="red">0</a></td>
										<td>支付本金：<a class="red">0</a></td>
										<td>支付l利息：<a class="red">10.45元</a></td>
									</tr> -->
								</tbody>
							</table>
						</ul>
						<a style="cursor: pointer;" id="confirmInvest">确定投资</a>
					</div>
				</div>
			</div>
	</div>
	<input type="hidden" id="requestMethod" value="${requestMethod}">
	<form method="post" id="confirmForm">
	<input type="hidden" name="inv.copies" value="${inv.copies}">
	<input type="hidden" name="productId" value="${product.id}">
	</form>
	<script type="text/javascript">
	$("#psi_close").click(function(){
		var list = art.dialog.list;
		for (var i in list) {
		    list[i].close();
		};
	});
	$("#confirmInvest").click(function(){
		var href="${pageContext.request.contextPath}/Product/User/confirmInvest!"+$("#requestMethod").val()+".action";
		$("#confirmForm").attr("action",href);
		$("#confirmForm").submit();
	});
	</script>
</body>
</html>