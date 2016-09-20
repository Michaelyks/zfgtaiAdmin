<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.js"></script>
<title>自动转入</title>
<script type="text/javascript">
function autoShiftTo(){
	var url="${pageContext.request.contextPath}/Product/User/autoShiftTo!autoShiftTo.action";
	$.post(url,$("#frm").serialize(),function(data){
		 if("ok"==data.status){
			 /* jingao("转入零钱包成功!", "提示", null, 0); */
			 alert("转入零钱包成功!");
		 }else if("noLogin"==data.status){
			/*  jingao("登录已失效,请重新登录", "提示", function(){
					window.location.href="${pageContext.request.contextPath}/Product/login/login.jsp";}, 1); */
			 alert("登录已失效,请重新登录");
		 }else{
			/*  jingao(data.json, "提示", null, 0); */
			 alert(data.json);
		 }
	 }); 
}
</script>
</head>
<body>	
		<form id="frm">
			自动转入：<input type="radio" name="type" value="0">开启<input type="radio" name="type" value="1">关闭
			余额保留金额 ：<input type="text" name="leftMoney" value="${leftMoney}">
			<input type="button" onclick="autoShiftTo()" value="确认">
		</form>
</body>
<script type="text/javascript">
	$(function(){
		var type='${type}'
		$("input[name=type]").attr("checked",type);
	});
</script>
</html>