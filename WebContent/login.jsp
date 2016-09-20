<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录结果</title>
</head>
<body>
<form action="http://192.168.0.100:8088/baiYiMaoMobile/Product/login" method="post">
<h1>1.用户登录接口</h1>
<span>接收参数:</span>
<span>{"username":"test@163.com","password":"123456"}</span><br>
	<input type="text" name="parameters" value='{"username":"test@163.com","password":"123456"}' style="width:500px;" />
	 <input type="submit" value="登录"/>
</form>
<br>
<form action="http://192.168.0.100:8088/baiYiMaoMobile/Product/loadProduct" method="post">
<h1>2.获取理财产列表接口</h1>
<span>接收参数:</span>
<span>{"currentPage":"1"} <span style="color:red;">ps:currentPage是当前页数;可变的页数</span></span><br>
	<input type="text" name="parameters" value='{"currentPage":"1"}' style="width:500px;" />
	 <input type="submit" value="获取"/>
</form>
<br>
<form action="http://192.168.0.100:8088/baiYiMaoMobile/Product/getProductById" method="post">
<h1>3.获取理财产品详情</h1>
<span>接收参数:</span>
<span>{"productId":"402880e44ce5b4de014ce937f34e0004"}</span><br>
	<input type="text" name="parameters" value='{"productId":"402880e44ce5b4de014ce937f34e0004"}' style="width:500px;" />
	 <input type="submit" value="获取"/>
</form>
<br>
</body>
</html>