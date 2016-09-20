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
<title>后台管理 - 发送积分</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h1 style="text-align: center;">发送积分</h1>
			<form id="sendMcoinForm" method="post">
				<table>
					<tr>
						<td><span style="color: red;">*</span>用户名:</td>
						<td><textarea cols="13" rows="20" id="username" name="username"></textarea>*多个用户必须以英文","隔开</td>
					</tr>
					<tr>
						<td colspan="2" align="center"><span id="realName"></span></td>
					</tr>
					<tr>
						<td><span style="color: red;">*</span>发放数量:</td>
						<td><input id="mcoin" type="text" name="mcoin" maxlength="5">(积分)</td>
                    </tr>				
					<tr>
						<td><span style="color: red;">*</span>备注</td>
						<td><input id="note" type="text" name="note"> </td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="2"><input type="button" id="btnSendMcoin" value="赠送积分" > </td>
					</tr>
				</table>
			</form>
	</div>
</div>
	<script type="text/javascript">
	function checkHongbao(){
		if(!confirm("确定发送积分?")){
			return false;
		}
		var username=$("#username").val();
		var money=$("#mcoin").val();
		var note=$("#note").val();
		if(isEmpty(username)){
			alert("请填写用户名");
			return false;
		}
		else if(isEmpty(money)){
			alert("请填写发放数量");
			return false;
		}else if(isEmpty(note)){
			alert("请填写备注");
			return false;
		}
		
		return true;
		}

	$("#username").blur(function(){
		var username=$("#username").val();
/* 		if(""==username){
			$("#username").select();
			alert("用户名不能为空");
			return false;
		} */
		var url="${pageContext.request.contextPath}/Product/Admin/meowIncome!getRealNameByUsername.action";
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
		$("#btnSendMcoin").click(function(){
			if(!checkHongbao()){
				return false;
			}
			var data="username="+$("#username").val()+"&mcoin="+$("#mcoin").val()+"&note="+$("#note").val();
			var url="${pageContext.request.contextPath}/Product/Admin/meowIncome!sendMcoin.action";
			$.ajax({
				type:"post",
				url:url,
				//data:$("#sendMcoinForm").serialize(),
				data:data,
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
