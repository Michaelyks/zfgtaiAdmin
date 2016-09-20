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
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<title>后台管理 - 发送群体加息券</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h1 style="text-align: center;">发送群体加息券</h1>
			<form id="sendCouponGroupForm" method="post">
				<table>
					<tr>
						<td>人群</td>
						<td><select name="paramMap.isBindBank">
							<option value="">所有</option>
							<option value="1">绑定人数</option>
							<option value="0">未绑定人数</option>
						</select></td>
					</tr>
					<tr>
						<td>发放加息率:</td>
						<td><input id="interestRate" type="text" name="paramMap.interestRate" maxlength="3">% （0.1%~3%）</td>
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
		var interestRate=$("#interestRate").val();
		var note=$("#note").val();
		var num = $("#num").val();
		if(isEmpty(interestRate)){
			alert("加息券不能为空");
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
		
		return true;
	}
	
	
	var text = "<img src='${pageContext.request.contextPath}/Product/images/progress.gif' width='24' height='24'> <span style='font-family:楷体;font-size:22px;'>正在发送,请稍等...</span>";
	
	var chuangkou;
	$(document).ready(function(){
		$("#btnSendCouponGroup").click(function(){
			if(!checkCoupon()){
				return false;
			}
			chuangkou = chuangkou(text,"温馨提示","",0,true);
			$.post("${pageContext.request.contextPath}/Product/Admin/sendInterestCoupon!sendInterestCouponUnbind.action", $("#sendCouponGroupForm").serialize(), function(data){
				closeAlert(chuangkou);
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
