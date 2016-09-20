<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/login.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/js/login.js"></script>
<title>百亿猫后台登陆</title>
</head>
<body>
	<div class="logo">
			<img src="${pageContext.request.contextPath}/Product/Admin/img/logo.png"/><span>深圳中泰理财金融服务有限公司</span>
		</div>
		<div class="nam">
			<p>后台管理系统</p>
			<div><span></span></div>
		</div>
		<div class="login" style="text-align: center;">
			<form action="${pageContext.request.contextPath}/Product/loginBackground!login.action" method="post">
				<p>登陆</p>
				<ul class="user">
					<li><span>请输入登陆账号</span><input type="text" name="users.username"  id="username"/></li>
					<li><span>请输入登陆密码</span><input type="password"  name="users.password" id="password"/></li>
				</ul>
				<!-- <div><a href="#">忘记密码?</a></div> -->
				<input type="submit" value="登录" style="width:200px;height:30px;"/>
			</form>			
		</div>
</body>
<script type="text/javascript">

if("${status}"=="not"){
	alert("帐号或密码不正确!");
}else if("${status}"=="err"){
	alert("登录失败!");
}

</script>
</html>