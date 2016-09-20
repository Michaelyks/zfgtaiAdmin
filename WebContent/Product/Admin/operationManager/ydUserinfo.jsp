<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>移动用户信息</title>
</head>
<body>
	<table border="1">
		<c:forEach items="${list}" var="item">
			<tr>
				<td>${myel:jieMiUsername(item.username)}</td>
			</tr>
		</c:forEach>
	</table>
	<jsp:include page="/Product/page.jsp" />
</body>
</html>