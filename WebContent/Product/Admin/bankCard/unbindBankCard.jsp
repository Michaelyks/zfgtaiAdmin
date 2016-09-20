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
	function send(wh){
		$.ajax({ 
			url: "${pageContext.request.contextPath}/Product/Admin/bindCardRecord!sendYzm.action?name="+$("#name").val()+"&wh="+wh,
			success: function(data){
	       		if(data.status=='ok'){
	       			alert(data.json);
	       			$("#vcodetr").css('display','block');
	       			$("#vbtn").css('display','block');
	       		}else if(data.status=='investors'){
	       			alert(data.json);
	       			window.location.href="${pageContext.request.contextPath}/Product/Admin/investors!findInvertors.action?name="+$("#name").val();	
	       		}else{
	       			alert(data.json);	
	       		}
	      }});
	}
	function verifyCode(username){
		$.ajax({ 
			url: "${pageContext.request.contextPath}/Product/Admin/bindCardRecord!verifyYzmAndUnbind.action?name="+username+"&vcode="+$("#vcode").val(),
			success: function(data){
	       		if(data.status=='ok'){
	       			alert(data.json);
	       			$("#vcodetr").css('display','none');
	       			$("#vbtn").css('display','none');
	       			window.location.href="${pageContext.request.contextPath}/Product/Admin/bankCard/unbindBankCard.jsp";
	       		}else if(data.status=='investors'){
	       			alert(data.json);
	       			window.location.href="${pageContext.request.contextPath}/Product/Admin/investors!findInvertors.action?name="+$("#name").val();	
	       		}else{
	       			alert(data.json);	
	       		}
	      }});
	}
	function Byname(){
		window.location.href="${pageContext.request.contextPath}/Product/Admin/bindCardRecord!findAccountByName.action?name="+$("#name").val();
	}
</script> 	
</head>
<body>
<div class="center">		
<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h3>解绑银行卡</h3>
		<label>用户名:<input type="text" name="name" id="name" value="${name}">
		<input type="button" value="search" id="frm" onclick="Byname()"></label>&nbsp;&nbsp;
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
					<td colspan="2"><a href="javascript:send('${account.cardLast}');" class="a">解绑</a></td>
				</tr>
				<tr id="vcodetr" style="display:none;">
					<td>验证码</td>
					<td><input type="text" name="vcode" id="vcode"></td>
				</tr>
				<tr id="vbtn" style="display:none;">
					<td colspan="2"><a href="javascript:verifyCode('${myel:jieMiUsername(account.users.username)}');" class="a">立即认证</a></td>
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