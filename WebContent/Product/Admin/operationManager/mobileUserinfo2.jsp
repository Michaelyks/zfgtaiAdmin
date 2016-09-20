<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<form method="post" action="${pageContext.request.contextPath}/Product/Admin/usersConvert!allUserInfo.action" onsubmit="return isSubmit();">
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
			<tr>
				<td>姓名</td>
				<td>性别</td>
				<td>年龄</td>
				<td>手机号码</td>
				<td>地区</td>
				<td>投资金额</td>
			</tr>
		<c:forEach items="${list}" var="item">
			<tr>
				<td>${item[0]}</td>
				<td>${item[2]}</td>
				<td>${item[3]}</td>
				<td>${myel:jieMiUsername(item[1])}</td>
				<td><c:choose>
					<c:when test="${item[4] eq item[5] }">${item[4]}
					</c:when>
					<c:otherwise>
						${item[4]}${item[5]}
					</c:otherwise>
				</c:choose></td>
				<td>
				<c:choose>
					<c:when test="${item[6]>0}">
						<fmt:formatNumber value="${item[6]*0.01}" pattern="#,##0.##"/>
					</c:when>
					<c:otherwise>
						0
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</c:forEach>
	</table>
	</div>
</div>
</body>
</html>