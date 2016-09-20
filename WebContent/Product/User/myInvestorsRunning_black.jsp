<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>      
 <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset=UTF-8">
<title>中泰理财 - 流水记录</title>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<link href="../css/account.css" rel="stylesheet" type="text/css">
<link href="../css/acount_history.css" rel="stylesheet" type="text/css">

</head>
<body>
<jsp:include page="../top.jsp" />
		<div class="lujin"><div class="crumbs"><a href="#"><img src="../images/home_ico.png" /></a> &gt; <a href="#">投资中心</a> &gt; <em>流水记录</em></div></div>		
<div class="maincnt wrap cf">
	<jsp:include page="leftMenu.jsp" />
	<div class="mainright fr">
    <ul class="tow_F">
			<!--  <li>我投资的产品</li>
			<li class="mingx"><a href="#">点击此处进入到产品明细</a></li>-->
			<li><p class="f14 f_red1" >流水记录</p></li>
		</ul>
        <div id="myInvestors" style="display:block;">
         <c:choose>
			<c:when test="${investorsList ne '[]' && investorsList ne '' && investorsList ne null}">
				<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
					<tbody>
					<tr class="head_list">
						<td width="30">序号</td>
						<td width="75">产品名称</td>
						<td width="75">项目总额(元)</td>
						<td width="80">投入金额(元)</td>
						<td width="70">年化收益</td>	
						<td width="70">投资时间</td>				
						<td width="50">状态</td>
					</tr>
					</tbody>
					<tbody>
					<c:forEach items="${investorsList}" var="list" varStatus="s">
						<tr>
							<td class="td0" title="">${s.count + (currentPage-1)*pageSize}</td>
							<td class="td1" title="${list.product.title}">
							<c:choose>
								<c:when test="${list.product.productType eq '1'}">
								<a href="${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?productId=${list.product.id}" target="_blank">${myel:getSubString(list.product.title,18)}</a>
								</c:when>
								<c:otherwise>
									<a href="${pageContext.request.contextPath}/Product/index!showProductDetails.action?productId=${list.product.id}" target="_blank">${myel:getSubString(list.product.title,18)}</a>
								</c:otherwise>
							</c:choose>
							</td>
							<td class="td2" title="<fmt:formatNumber value="${list.product.financingAmount*0.01}" pattern="#,##0.##" type="number" />"><fmt:formatNumber value="${list.product.financingAmount*0.01}" pattern="#,##0.##" type="number" /></td>
							<td class="td3" title="<fmt:formatNumber value="${list.inMoney*0.01}" pattern="#,##0.##" type="number" />"><fmt:formatNumber value="${list.inMoney*0.01}" pattern="#,##0.##" type="number" /></td>
							<td class="td4" title="<fmt:formatNumber value="${list.product.annualEarnings}" pattern="0.##" type="number" />%"><fmt:formatNumber value="${list.product.annualEarnings}" pattern="0.##" type="number" />%</td>
							<td class="td5" title="<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd"/></td>
							<td class="td6" title="支付失败">支付失败</td>
						</tr>
					</c:forEach>					
					</tbody>
				</table>
				<jsp:include page="../page.jsp" />
			</c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;">
            	<img src="../images/no_record.png" />
            	</div>
			</c:otherwise>
			</c:choose>
		</div>
      </div>
    </div>
<jsp:include page="../footer.jsp" />
</body>
<script type="text/javascript">
choosePage("3");
chooseMenu("2");
</script>
</html>