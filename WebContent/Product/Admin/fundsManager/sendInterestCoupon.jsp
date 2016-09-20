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
<title>后台管理 - 发送加息券</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h1 style="text-align: center;">发送加息券</h1>
			<form id="sendHongBaoForm" method="post">
			<table>
				<tr>
					<td>用户名:</td>
					<td><input id="username" type="text" name="paramMap.userName" maxlength="11"/></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><span id="realName"></span></td>
				</tr>
				<tr>
					<td>发放加息率:</td>
					<td><input id="interestRate" type="text" name="paramMap.interestRate" maxlength="3">% （0.1%~3%）</td>
				</tr>
				<tr>
					<td>加息券到期时间:</td>
					<td><input type="text" name="paramMap.overTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/></td>
				</tr>
				<tr>
					<td>红包类型</td>
					<td>
					<select name="paramMap.type">
					<option value="0" selected="selected">活动加息券</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td><input id="note" type="text" name="paramMap.note"> </td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="2"><input type="button" id="btnSendHongBao" value="发放加息券" > </td>
				</tr>
			</table>
		</form>
	</div>
</div>
	<script type="text/javascript">
	function checkHongbao(){
		if(!confirm("确定发送加息券?")){
			return false;
		}
		var username=$("#username").val();
		var interestRate=$("#interestRate").val();
		var note=$("#note").val();
		//alert(username);
		if(isEmpty(username)){
			alert("请填写用户名");
			return false;
		}
		
		if(isEmpty(interestRate)){
			alert("加息券格式错误");
			return false;
		}
		
		if(isEmpty(note)){
			alert("备注不能为空");
			return false;
		}
		if(isNaN(interestRate)){
			alert("利率格式错误");
			return false;
		}

		var reg = /^[0-9](\.[0-9]{0,1}){0,1}$/;
		if (!reg.test(interestRate)) {
		     alert("利率格式错误");
			return false;
		}
		return true;	
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
			var url="${pageContext.request.contextPath}/Product/Admin/sendInterestCoupon!sendInterestCoupon.action";
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
