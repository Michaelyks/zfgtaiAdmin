<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>解绑银行卡</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css"/>
<style type="text/css">
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
<script type="text/javascript">
	function unbind(username){
		if(confirm("是否解绑银行卡")){
			$.ajax({ 
				url: "${pageContext.request.contextPath}/Product/Admin/bindCardRecord!unbind.action?name="+username,
				success: function(data){
		       		alert(data.json);
		       		window.location.href="${pageContext.request.contextPath}/Product/Admin/bankCard/unbindBankCard2.jsp";
		      }});
		}
	}
	function queryInfo(){
		window.location.href="${pageContext.request.contextPath}/Product/Admin/bindCardRecord!findAccountByUserName.action?name="+$("#name").val();
	}
</script> 	
</head>
<body>
<div class="center">		
<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h3>解绑银行卡</h3>
		<label>用户名:<input type="text" name="name" id="name" value="${name}">
		<input type="button" value="search" id="frm" onclick="queryInfo()"></label>&nbsp;&nbsp;
		<c:if test="${account != null}">
			<table>
				<tr>
					<td>用户名</td>
					<td>${myel:jieMiUsername(account.users.username)}</td>
				</tr>
				<tr>
					<td>尾号（4位）</td>
					<td>${account.cardLast}</td>
				</tr>
				<tr>
					<td>真实姓名</td>
					<td>${account.bankAccountName}</td>
				</tr>
				<tr>
					<td>银行名称</td>
					<td>${account.bankName}</td>
				</tr>
				<tr id="jb" style="display: block;">
					<td colspan="2"><a href="javascript:unbind('${myel:jieMiUsername(account.users.username)}');" class="a">解绑银行卡</a></td>
				</tr>
			</table>
		</c:if>
	</div>
</div>
<script type="text/javascript">
	var message='${message}';
	if(message!=''&&message!=null){
		alert(message);
	}
</script>
</body>
</html>