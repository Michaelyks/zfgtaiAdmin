<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<title></title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h1 style="text-align: center;"></h1>
			<form id="txRecordForm" method="post">
				<table>
					<tr>
						<td><span style="color: red;">*</span>账号:</td>
						<td><input id="username" type="text" name="reflect.username" maxlength="11"/></td>
					</tr>

					<tr>
						<td><span style="color: red;">*</span>原订单号:</td>
						<td><input id="requestId" type="text" name="reflect.requestId" ></td>
					</tr>
				
					<tr>
						<td><span style="color: red;">*</span>商业订单号</td>
						<td><input id="orderId" type="text" name="reflect.orderId"> </td>
					</tr>
					<tr>
						<td><span style="color: red;">*</span>银通订单号</td>
						<td><input id="accounId" type="text" name="reflect.accounId"> </td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="2"><input type="button" id="btnTxRecord" value="提交" > </td>
					</tr>
				</table>
			</form>
	</div>
</div>
<script  type="text/javascript">
function check(){
var username=$("#username").val();
var requestId=$("#requestId").val();
var orderId=$("#orderId").val();
var accounId=$("#accounId").val();
if(username==""){
	alert("请输入用户账号");
	return false;
}
else if(!isPhoneNumber(username)){
	alert("手机号码格式有误");
	return false;
}
else if(requestId==""){
	alert("请输入原订单号");
	return false;
}
else if(orderId==""){
	alert("请输入商业订单号");
	return false;
}
else if(accounId==""){
	alert("请输入银通订单号");
	return false;
}
return true;
}


$(document).ready(function(){
	$("#btnTxRecord").click(function(){
		if(!check()){
			return false;
		}
		var url="${pageContext.request.contextPath}/Product/Admin/reflect!loadTxRecord.action";
		$.ajax({
			type:"post",
			url:url,
			data:$("#txRecordForm").serialize(),
			success:function(data){
				alert(data.json);
				if(data.status=="ok"){
					window.location.reload();
				}
			}
		});
		
	});
});
</script>
</body>
</html>