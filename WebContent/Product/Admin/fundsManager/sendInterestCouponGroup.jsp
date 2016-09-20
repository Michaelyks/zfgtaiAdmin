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
		<h1 style="text-align: center;">发送加息券(群体)</h1>
			<form id="sendCouponGroupForm" method="post">
				<table>
					<tr>
						<td colspan="1" >用户名:</td>
						<td><textarea cols="13" rows="20" id="username" name="paramMap.userName"></textarea>*用户名之间必须以英文","隔开</td>
					</tr>
					<tr>
						<td>发放加息率:</td>
						<td><input id="interestRate" type="text" name="paramMap.interestRate" maxlength="3">% （0.1%~3%）</td>
					</tr>
					<tr>
						<td>加息券数量:</td>
						<td><input id="num" type="text" name="paramMap.num" maxlength="2"></td>
					</tr>
					<tr>
						<td>加息券到期时间:</td>
						<td><input type="text" id="overTime"  name="paramMap.overTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/></td>
					</tr>
					<tr>
						<td>加息券类型</td>
						<td>
						<select id="type" name="paramMap.type">
						<option value="0" selected="selected">活动加息券</option>
						</select>
						</td>
					</tr>
					<tr>
						<td>备注</td>
						<td><input id="note" type="text" name="paramMap.note"> </td>
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
		if(!confirm("确定发送加息券?")){
			return false;
		}
		var username=$("#username").val();
		var interestRate=$("#interestRate").val();
		var note=$("#note").val();
		var num = $("#num").val();
		//alert(username);
		if(isEmpty(username)){
			alert("请填写用户名");
			return false;
		}
		if(isEmpty(interestRate)){
			alert("收益率格式错误");
			return false;
		}
		
		if(isEmpty(note)){
			alert("备注不能为空");
			return false;
		}
		
		if(isNaN(interestRate)){
			alert("加息券格式错误");
			return false;
		}

		var reg = /^[0-9](\.[0-9]{0,1}){0,1}$/;
		if (!reg.test(interestRate)) {
		     alert("利率格式错误");
			return false;
		}
		if(isNaN(num)){
			alert("数量格式错误");
			return false;
		}else if(!(/(^[1-9]\d*$)/.test(num))){
			alert("数量格式错误");
	 		return false;
		}
		
		return true;
		
	}

	$(document).ready(function(){
		$("#btnSendCouponGroup").click(function(){
			if(!checkCoupon()){
				return false;
			}
			
			$.post("${pageContext.request.contextPath}/Product/Admin/sendInterestCoupon!sendInterestCouponGroup.action", $("#sendCouponGroupForm").serialize(), function(data){
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
