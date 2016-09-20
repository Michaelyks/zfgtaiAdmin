<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 流水记录</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/account.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/record.css" rel="stylesheet" type="text/css"/>
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
					<p class="record_title">流水记录</p>
					<div class="touzijilu">
						<table cellspacing="0" cellpadding="0" border="0">
							<tbody>
								<tr class="list_head">
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
											<a href="${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?productId=${list.product.id}" target="_blank">${myel:getSubString(list.product.title,18)}</a>
										</c:when>
										<c:otherwise>
											<a href="${pageContext.request.contextPath}/Product/index!showProductDetails.action?productId=${list.product.id}" target="_blank">${myel:getSubString(list.product.title,18)}</a>
										</c:otherwise>
									</c:choose>
									</td>
									<td title="投入本金<fmt:formatNumber value="${list.inMoney*0.01}" pattern="#,##0.##" type="number" />元,投资券<fmt:formatNumber value="${list.coupon*0.01}" pattern="#,##0.##" type="number" />元">
									<fmt:formatNumber value="${list.copies}" pattern="#,##0.##" type="number" />
									</td>
									<td title="<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd"/></td>
									<td class="red" title="${list.product.annualEarnings}"><fmt:formatNumber value="${list.product.annualEarnings}" pattern="0.##" type="number" />%</td>
									<td title="<fmt:formatNumber value="${list.expectEarnings*0.01}" pattern="0.00" type="number" />"><fmt:formatNumber value="${list.expectEarnings*0.01}" pattern="0.00" type="number" /></td>
									<td title="<fmt:formatDate value="${list.ydqsj}" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${list.ydqsj}" pattern="yyyy-MM-dd"/></td>
									<td title="<fmt:formatDate value="${list.clearTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${list.clearTime}" pattern="yyyy-MM-dd"/></td>
									<td title="支付失败">支付失败</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div align="right" style="margin: 15px;">
		            	<c:if test="${investorsList ne null}"><jsp:include page="../page.jsp" /></c:if>
	            	</div>
				</div>
				<div class="clea"></div>
			</div>
		</div>
		<!-- 主内容 end-->		
		<div class="clea"></div>
		
		<jsp:include page="../footer.jsp" />

	</body>
</html>