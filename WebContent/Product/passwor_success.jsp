<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<meta charset="utf-8" />
		<link href="css/common.css" rel="stylesheet" type="text/css">
		<link href="css/login.css" rel="stylesheet" type="text/css">
		<link href="css/registerNew.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<title>中泰理财 - 修改密码成功</title>
	</head>
	<body>
	<jsp:include page="top.jsp" />
		<div class="main">
			<ul class="step step_s">
				<li>手机确定账户</li>
				<li>修改密码</li>
				<div style="clear:both"></div>
			</ul>
			<div class="succ">
				<p><i class="yes"></i>密码设置成功！请妥善记忆</p>
				<div><a href="#">前往我的账户</a></div>
			</div>
		</div>
	<jsp:include page="footer.jsp"></jsp:include>
	</body>
<script type="text/javascript">
	//检查输入是否为空
	$(function(){
		//检查用户名
		$("#registerForm li input").eq(0).blur(function(){
			if($(this).val()==""){
				$(this).siblings(".err_tip").html("请输入您的新密码").css({"display":"inline-block","background-position":"0 -79px"});
			}
			else{
				$(this).siblings(".err_tip").html("").css({"display":"inline-block","background-position":"0 -110px"});
			}
		});
		$("#registerForm li input").eq(1).blur(function(){
			if($(this).val()==""){
				$(this).siblings(".err_tip").html("请输入您的新密码").css({"display":"inline-block","background-position":"0 -79px"});
			}
			else{
				$(this).siblings(".err_tip").html("").css({"display":"inline-block","background-position":"0 -110px"});
			}
		});
		
	})


</script>
</html>
