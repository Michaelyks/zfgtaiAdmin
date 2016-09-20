<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<title>中泰理财 - 登录</title>
<link href="css/common.css" rel="stylesheet" type="text/css">
<link href="css/login.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/jquery.superslide.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">

</head>

<body>
<jsp:include page="top.jsp" />
<script type="text/javascript">choosePage("1");</script>
   <!--  <div class="main">
			<div class="left">
				<img src="images/loginImg.jpg"/>
			</div>
			<div class="right">
				<p>用户登录</p>
				<form id="loginForm">
					<ul class="lg">
						<li><label><i class="log_u"></i>账&nbsp;&nbsp;&nbsp;&nbsp;号</label><input type="text" id="username" name="users.username" maxlength="11" value="" placeholder="请输入用户名"/></li>
						<li><label><i class="log_p"></i>密&nbsp;&nbsp;&nbsp;&nbsp;码</label><input type="password" id="password" name="users.password" maxlength="16" value="" placeholder="请输入密码"/></li>
						<li class="wjma"><a href="find_password.jsp">忘记登录密码？</a></li>
						<li class="lo"><a id="btnLogin" onclick="goIn()">立即登录</a></li>
						<li><div class="n_user"><span>还没有账号？</span><a href="${pageContext.request.contextPath}/Product/registerUser!regist.action">免费注册》</a></div></li>
					</ul>
				
				</form>
			
			</div>
			<div style="clear:both"></div>
		</div>-->
		<div class="log">
			<div class="log_c">
				<div class="xins">
					<div>
						<p>新手注册</p>
						<p>送<span>1000</span><a>元</a>投资红包哦！</p>
					</div>
				</div>
				<div class="logi">
					<p>用户登录</p>
					<form id="loginForm">
						<ul>
							<li><span class="l_u">账号<i></i></span><input type="text" id="username" name="users.username" maxlength="11" value="" placeholder="请输入用户名" /></li>
							<li><span class="l_p">密码<i></i></span><input type="password" id="password" name="users.password" maxlength="16" value="" placeholder="请输入密码"/></li>
							<li><a class="l_fg" href="find_password.jsp">忘记密码？</a></li>
							<li><a class="l_ld" id="btnLogin" onclick="goIn()">立即登录</a></li>
							<li><span class="l_zh">还没有账号?</span><a href="${pageContext.request.contextPath}/Product/registerUser!regist.action">免费注册>></a></li>
						</ul>
					</form>
				</div>
				<div class="clea"></div>
			</div>
			
		</div>
 
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
//登录
	$(function(){
		$(".footer").css("padding-top","0px");
		
	})
function goIn(){
	var url = "${pageContext.request.contextPath}/Product/usersLogin!usersLogin.action";
	//alert("登录"+$("#loginForm").serialize());
	//url += "&attention.userId=" + 123456;
	//alert(url);
	var su = "";
	$.ajax({
		type : "post",
		url : url,
		data:$("#loginForm").serialize(),
		success : function(data, textStatus) {
			su = data;
		},
		complete : function(XMLHttpRequest, textStatus) {
			if ("ok" == su.status) {
				jingao(su.json,"提示",function(){
					window.location.href="${pageContext.request.contextPath}/Product/index!loadProduct.action";
				},0);
			} else if ("err" == su.status) {
				jingao(su.json,"提示","",0);
			} else{
				jingao(su.json,"提示","",0);
			}

		}
	});
}

$("#username,#password").keydown(function(event){
	if(event.keyCode==13){
		goIn();
	}
});
</script>
</html>