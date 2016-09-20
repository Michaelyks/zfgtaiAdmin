<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<title>后台管理 - 用户充值</title>
</head>	
<body>
<%-- <jsp:include page="top.jsp" /> --%>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
	<h1 style="text-align: center;">用户充值</h1>
		<form id="rechargeForm" method="post">
			<table>
				<tr>
					<td>用户名:</td>
					<td><input type="text" name="username" id="username" /></td>
				</tr>
				<tr>
						<td colspan="2" align="center"><span id="realName"></span></td>
					</tr>
				<tr>
					<td>充值金额:</td>
					<td><input type="text" name="rechargeMoney" id="rechargeMoney">(元)</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr>
				<tr>
				<!-- <tr>
					<td>红包到期时间:</td>
					<td><input type="text" name="overTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"/></td>
				</tr>
				<tr>
					<td>红包类型</td>
					<td>
					<select name="con.type">
					<option value="新手红包" selected="selected">新手红包</option>
					</select>
					</td>
				</tr>
				-->
				<tr>
					<td>备注</td>
					<td><input type="text" name="cause" id="cause"> </td>
				</tr> 
				<tr>
					<td colspan="2" align="center"><span id="cause_msg"></span></td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="2"><input type="button" id="btnRecharge" value="充值"> </td>
				</tr>
			</table>
		</form>
	</div>
</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#btnRecharge").click(function(){
			var username=$("#username").val();
			var rechargeMoney=$("#rechargeMoney").val();
			var cause=$("#cause").val();
			if(isEmpty(username)){
				alert("请填写用户名");
				return false;
			}else if(isEmpty(rechargeMoney)){
				alert("请填写金额");
				return false;
			}else if(!isPrice(rechargeMoney)){
				alert("金额格式错误");
		 		return false;
			}else if(isEmpty(cause)){
				alert("备注不能为空");
				return false;
			}
			var url="${pageContext.request.contextPath}/Product/Admin/recharge!rechargeMoney.action";
			$.ajax({
				type:"post",
				url:url,
				data:$("#rechargeForm").serialize(),
				success:function(data){
					alert(data.json);
					if(data.status=="ok"){
						window.location.reload();
					}
				}
			});
			
		});
	});
	
	$("#username").blur(function(){
		var username=$("#username").val();
		if(""==username){
			$("#username").select();
			alert("用户名不能为空");
			$("#realName").text("");
			return false;
		}
		var url="${pageContext.request.contextPath}/Product/Admin/sendCoupon!getRealNameByUsername.action";
		$.ajax({
			type:"post",
			url:url,
			data:"username="+username,
			success:function(data){
				//alert(data.json);
				if(data.status=="ok"){
					$("#realName").text("真实姓名: "+data.json);
				}else{
					$("#realName").text("提示: "+data.json);
				}
			}
		});
	});
	$("#rechargeMoney").blur(function(){
		var rechargeMoney=$("#rechargeMoney").val();
		if(isEmpty(rechargeMoney)){
			$("#rechargeMoney_msg").text("提示：请填写金额");
			return false;
		}else if(!isPrice(rechargeMoney)){
			$("#rechargeMoney_msg").text("提示：金额格式错误");
	 		return false;
		}else {
			$("#rechargeMoney_msg").text("");
		}
	});
	
	$("#cause").blur(function(){
		var cause=$("#cause").val();
		if(isEmpty(cause)){
			$("#cause_msg").text("提示：备注不能为空");
			return false;
		}else {
			$("#cause_msg").text("");
		}
	});
	</script>
</body>
</html>
