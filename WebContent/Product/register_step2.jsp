<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
	<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<meta charset="utf-8" />
		<link href="css/common.css" rel="stylesheet" type="text/css">
		<link href="css/login.css" rel="stylesheet" type="text/css">
		<link href="css/registerNew.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<title>中泰理财 - 注册</title>
	</head>
	<body>
		<jsp:include page="header.jsp" />
			<div class="main main2">
				<ul class="step">
					<li>填写注册信息</li>
					<li>领取新人专享</li>
					<div style="clear:both"></div>
				</ul>
				<div class="user_reg">
					<p><i class="xs_hb"></i>恭喜你成为中泰理财的一员</p>
				</div>
			</div>
		<jsp:include page="footer.jsp"></jsp:include>
		<div id="goToIndex" class="ping_n">
			<div class="ping_bg">	</div>
			<div class="p_center">
				<!-- <div>
					<p style="visibility: hidden;">截止日期：<span>2015/6/30</span></p>
					<a href="javascript:void(0);" id="goToIndex">知道了</a>
				</div>	 -->	
			</div>
		</div>
	</body>
<script type="text/javascript">
$("#goToIndex").click(function(){
	if("${param.productId}"==""){
		window.location.href="${pageContext.request.contextPath}/Product/index!loadProduct.action";
	}else{
		window.location.href="${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?productId=${param.productId}";
	}
});
</script>
</html>
