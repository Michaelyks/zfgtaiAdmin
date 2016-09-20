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
<title>后台管理 - 发送投资券</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h1 style="text-align: center;">发送投资券</h1>
			<form id="sendHongBaoForm" method="post">
				<table>
					<tr>
						<td>用户名:</td>
						<td><input id="username" type="text" name="username" maxlength="11"/></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><span id="realName"></span></td>
					</tr>
					<tr>
						<td>发放金额:</td>
						<td><input id="money" type="text" name="con.money" maxlength="4">(元)</td>
					</tr>
					<tr>
						<td>红包到期时间:</td>
						<td><input type="text" name="overTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/></td>
					</tr>
					<tr>
						<td>红包类型</td>
						<td>
						<select name="con.type">
						<!-- <option value="1" >新手投资券</option> -->
						<option value="0" selected="selected">活动投资券</option>
						</select>
						</td>
					</tr>
					<tr>
						<td>备注</td>
						<td><input id="note" type="text" name="con.note"> </td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="2"><input type="button" id="btnSendHongBao" value="发放红包" > </td>
					</tr>
				</table>
			</form>
	</div>
</div>
	<script type="text/javascript">
	function checkHongbao(){
		if(!confirm("确定发送投资券?")){
			return false;
		}
		var username=$("#username").val();
		var money=$("#money").val();
		var note=$("#note").val();
		//alert(username);
		if(isEmpty(username)){
			alert("请填写用户名");
			return false;
		}else
			if(isEmpty(money)){
				alert("金额格式错误");
				return false;
			}else{
				if(isEmpty(note)){
					alert("备注不能为空");
					return false;
				}else
					if(isNaN(money)){
						alert("金额格式错误");
						return false;
					}else
						if(!(/(^[1-9]\d*$)/.test(money))){
							alert("金额格式错误");
					 		return false;
						}else{
							return true;
						}
			}
	}
	
	$("#username").blur(function(){
		var username=$("#username").val();
		if(""==username){
			$("#username").select();
			alert("用户名不能为空");
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
	
	
	$(document).ready(function(){
		$("#btnSendHongBao").click(function(){
			if(!checkHongbao()){
				return false;
			}
			var url="${pageContext.request.contextPath}/Product/Admin/sendCoupon!sendHongBao.action";
			$.ajax({
				type:"post",
				url:url,
				data:$("#sendHongBaoForm").serialize(),
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
