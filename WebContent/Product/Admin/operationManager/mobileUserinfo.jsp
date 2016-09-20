<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户手机号码</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<!-- <script type="text/javascript">
	function isSubmit(){
		var insertTime=$("#insertTime").val();
		if(insertTime=='' || insertTime==null ){
			alert("请选择时间");
			return false;
		}
	}
</script> -->
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<form method="post" action="${pageContext.request.contextPath}/Product/Admin/usersConvert!allMobile.action" onsubmit="return isSubmit();">
		<span>注册时间到现在为止的所有手机号码</span><br>
		<span>注册时间:</span><input id="insertTime" name="insertTime" type="text" value="${insertTime}"><span style="color: red;">*格式yyyy-MM-dd HH:mm:ss</span><br>
		<span>是否投资:</span><select name="type">
			<option value="" selected="selected">所有</option>
			<option value="1">投资用户</option>
			<option value="2">无投资</option>
		</select>
		<span>是否绑卡:</span><select name="bindBank">
			<option value="" selected="selected">所有</option>
			<option value="1">已绑卡</option>
			<option value="2">未绑卡</option>
		</select>
		<br>
		<input type="submit">
	</form>
	<table border="1">
		<c:forEach items="${list}" var="item">
			<tr>
				<td>${item}</td>
			</tr>
		</c:forEach>
	</table>
	</div>
</div>
</body>
</html>