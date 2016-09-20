<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统维护中</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/Product/User/userRegisters!emailRegister.action" method="post">

	用户:<input name="userName"><br>
	邮箱:<input name="email"><br>
	密码:<input name="passWord1"><br>
	确认密码:<input name="passWord2"><br>
	验证码：<input name="checkCode"><br>
	 <input type="submit" value="登录"/>
</form>
注册结果为：
${message}
</body>
</html>