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
<title>后台管理 - 发送投资券(群体)</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h1 style="text-align: center;">发送投资券(群体)</h1>
			<form id="sendCouponGroupForm" method="post">
				<table>
					<tr>
						<td colspan="1" >用户名:</td>
						<td><textarea cols="13" rows="20" id="username" name="username"></textarea>*用户名之间必须以英文","隔开</td>
					</tr>
					<tr>
						<td>发放金额:</td>
						<td><input id="money" type="text" name="con.money" maxlength="4">(元)</td>
					</tr>
					<tr>
						<td>投资券到期时间:</td>
						<td><input type="text" id="overTime"  name="overTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/></td>
					</tr>
					<tr>
						<td>投资券类型</td>
						<td>
						<select id="type" name="con.type">
						<option value="0" selected="selected">活动投资券</option>
						</select>
						</td>
					</tr>
					<tr>
						<td>备注</td>
						<td><input id="note" type="text" name="con.note"> </td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="2"><input type="button" id="btnSendCouponGroup" value="群发投资券" > </td>
					</tr>
				</table>
			</form>
	</div>
</div>
	<script type="text/javascript">
	function checkCoupon(){
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
		}else{
			if(isEmpty(money)){
				alert("金额格式错误");
				return false;
			}else
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


	$(document).ready(function(){
		$("#btnSendCouponGroup").click(function(){
			if(!checkCoupon()){
				return false;
			}
			var username=$("#username").val();
			var overTime=$("#overTime").val();
			var money=$("#money").val();
			var type=$("#type").val();
			var note=$("#note").val();
			var url="${pageContext.request.contextPath}/Product/Admin/sendCoupon!sendCouponGroup.action?username="+username+"&overTime="+overTime+"&con.money="+money+"&con.note="+note+"&con.type="+type;

/* 			$.ajax({
				type:"post",
				url:url,
				data:$("#sendCouponGroupForm").serialize(),
				success:function(data){
					alert(data.json);
					if(data.status=="ok"){
						window.location.reload();
					}
				}
			}); */

/* 			var formData="username="+username;
			     formData+="&overTime="+overTime;
			     formData+="&con.money="+money;
			     formData+="&con.note="+note; */
			$.post(url,null,function(data){
				alert(data.json);
				if(data.status=="ok"){
					window.location.reload();
				}
			 }); 
			
		});
	});

	
	</script>
</body>
</html>
