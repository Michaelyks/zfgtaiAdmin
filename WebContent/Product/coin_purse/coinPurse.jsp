<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>零钱包	</title>
</head>
<body>
	<span>收益率</span><fmt:formatNumber value="${coinPurse.earnings}" pattern="0.##"/><span>%</span><br>
	<span>钱包金额</span><fmt:formatNumber value="${coinPurse.inMoney*0.01}" pattern="0.##"/><span>元</span><br>
	<span><a href="${pageContext.request.contextPath}/Product/User/shiftTo!toShiftTo.action">转入                                            ></a></span><br>
	<span><a href="${pageContext.request.contextPath}/Product/User/rollOut!toRollOut.action">转出                                           ></a></span><br>
	<span><a href="${pageContext.request.contextPath}/Product/User/autoShiftTo!toAutoShiftTo.action">自动转入                                           ></a></span><br>
</body>
</html>