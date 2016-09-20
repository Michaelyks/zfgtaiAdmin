<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%> 	
<!doctype html>
<html>
<head>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>中泰理财-后台登录</title>		
<link href="${pageContext.request.contextPath}/Product/css/public.css" rel="stylesheet" type="text/css" />		
<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
  function adminLogin(){
	  var url="${pageContext.request.contextPath}/Product/loginBackground!adminLogin.action?";
	  var username=$("#username").val();
	  var password=$("#password").val();
	  if(username==null||""==username){
		  alert("用户名不能为空");
		  return false;
	  }
	  if(password==null||""==password){
			 alert("密码不能为空");
			 return false;
		 }
	  var formData="users.username="+username;
	  formData+="&users.password="+password;
	  $.post(url,formData,function(data){
			 if("ok"==data.status){			 
				 window.location.href="${pageContext.request.contextPath}/Product/Admin/login/welcome.jsp";
			 }else{			 
				 	alert(data.json);
				 	//$("#username").val("");
				 	//$("#password").val("");
				 	return false;
			 }
		 });
  }
</script>
</head>
<body>
<div class="center">
	<div class="logo">
		<img src="${pageContext.request.contextPath}/Product/images/logo.png"/><span>杭州奥发金融服务外包有限公司</span>
	</div>
<div class="main" align="center" >
	<h1>后台管理员登录</h1>
	<form action="" method="post">
		<table border="1">
		<tr>
		<td>用户名：</td>
		<td><input type="text" id="username" name="users.username"/></td>
		</tr>
		<tr>
		<td>密码：</td>
		<td><input type="password" id="password" name="users.password" /></td>
		<tr>
		<td colspan="2" style="text-align: center;"><input type="button" value="登录" onclick="adminLogin();" style="width:200px;height:30px;"/></td>
		</tr>
	</table>
</form>
</div>
</div>
	
</body>
</html>