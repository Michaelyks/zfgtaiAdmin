<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.js"></script>
<title>转入</title>
<script type="text/javascript">
function shiftTo(){
	var url="${pageContext.request.contextPath}/Product/User/shiftTo!saveShiftTo.action";
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
		转入金额 ：<input type="text" name="inMoney">
		<input type="button" onclick="shiftTo()" value="确认转入">
	</form>
</body>
</html>