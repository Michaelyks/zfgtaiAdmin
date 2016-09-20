<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 已还款</title>
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/repayment_tip.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<div class="main">
			<div class="layout money_back">
				<div class="tis_content">	
					<p>尊敬的用户您好，该项目已还款，为保护当事人隐私并应借款人要求，该项目相关</p>
					<p>证据资料已归还当事人并在本网站上不再显示，请选择其它项目投资，谢谢！</p>
				</div>
				<a href="${pageContext.request.contextPath}/Product/productCategory!loadProduct.action" class="to_touzi">投资其他项目</a>
			</div>
		</div>	
		<jsp:include page="footer.jsp"/>

	</body>
</html>