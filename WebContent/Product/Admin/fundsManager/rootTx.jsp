<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
<title>管理员提现</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	function mySubmit(){
		var username=$("#username").val();
		var money=$("#money").val();
		var note=$("#note").val();
		if(username==""||money==""||note==""){
			alert("请输入用户账号或提现金额或备注");
			return false;
		}
		if(!isPhoneNumber(username)){
			alert("手机号码格式有误");
			return false;
		}
		return true;
	}
	function rootTx(){
		if(mySubmit()){
			var url="${pageContext.request.contextPath}/Product/Admin/recharge!rootWithdraw.action";
			$.post(url,$("#frm").serialize(),function(data){
				var su=data
				alert(su.json);
				if(su.status=='ok'){
					windows.location.href="${pageContext.request.contextPath}/Product/Admin/recharge!rootTxRecord.action";
				}
	    	});
		}
	}
</script>
</head>
<body>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
		<h1 style="text-align: center;">管理员提现</h1>
		<form id="frm" action="${pageContext.request.contextPath}/Product/Admin/recharge!rootWithdraw.action" onsubmit="return mySubmit();">
				用户账号:<input type="text" name="username" id="username" maxlength="11">
				提现金额:<input type="text" name="money" id="money">
				备注:<input type="text" name="note" id="note">
				<input type="button" onclick="rootTx()" value="提现">
		</form>
	</div>
</div>
</body>
</html>	