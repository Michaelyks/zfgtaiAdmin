<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 平台公告</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/repayment_tip.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
		<jsp:include page="header.jsp" />
		<jsp:include page="about_nav.jsp" />
		<div class="main">
			<div class="layout gonggao">
				<jsp:include page="notice_left_memu.jsp" />
				<div class="fr g_content">
					<div class="meiti_top">
						<p>${notice.title}</p>
						<span><fmt:formatDate value="${notice.insertTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
					</div>
					<div class="meiti_bottom">
							${notice.content}
					</div>
				</div>
				
				<div class="clea"></div>
			</div>
		</div>	
		<jsp:include page="footer.jsp" />
	</body>
	<script>
		ab_left_memu(1);
	</script>
</html>