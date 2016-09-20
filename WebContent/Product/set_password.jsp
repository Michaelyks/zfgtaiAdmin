<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
	<meta charset="utf-8" />
	<link href="css/common.css" rel="stylesheet" type="text/css">
	<link href="css/login.css" rel="stylesheet" type="text/css">
	<link href="css/registerpwd.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
	<!--[if IE 7]>
	<link href="css/IE.css" rel="stylesheet" type="text/css">
	<![endif]-->
	<title>中泰理财 - 找回密码</title>
</head>
<body>
	<jsp:include page="header.jsp" />
		<div class="login">
		<!--  -->
			<div class="main white-bg">
				<div class="reg-icon">
					<span class="iconAll icon-num1"></span>
					<span class="iconAll icon-line">
						<i class="progress-bar"></i>
					</span>
					<span class="iconAll icon-num2"></span>
				</div>
				<div class="reg-icon-word">
					<span class="fl color-blue">验证账户信息</span>
					<span class="fr color-ddd">设置新密码</span>
				</div>
				<div class="user_reg">
					<form id="form_reset_password" method="post">
						<ul class="left left_f find-pwd">
					 		<li class="err_tip" id="message-show">错误提示</li>
							<li class="phone">
								<em><span class="iconAll icon-phone"></span></em>
								<input type="password" id="password" name="users.password" placeholder="请设置登录密码" />
							</li>
							<li class="phone">
								<em><span class="iconAll icon-phone"></span></em>
								<input type="password" value="" id="password2" name="password2" placeholder="请确认登录密码" />
							</li>
							<li><a href="javascript:void(0)" id="reset_password_go" class="btn-a blue-bg reg_now reg_now2">完成</a></li>
							<input value="${modifyId}" type="hidden" name="modifyId"  />
							<input value="${smsValidateCode}" type="hidden" name="smsValidateCode"  />
						
						</ul>
					</form>
					<div class="clea"></div>
					<div class="safe-reg">
						<span class="iconAll icon-safe"></span>
						您的信息已使用SSL加密技术，数据传输安全
					</div>
				</div>
			</div>
		</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">

function errTip(obj,errorMsg){
	//obj.siblings(".err_tip").html(errorMsg).css({"display":"inline-block","background-position":"0 -79px"});
	$("#message-show").html(errorMsg).show();
}
function cleanErrTip(obj){
	//obj.siblings(".err_tip").html("").css({"display":"inline-block","background-position":"0 -110px"});
	$("#message-show").html("").hide();
}
//检查输入是否为空
$(function(){
	
	//检查用户名
	$("#password").blur(function(){
		if(!isPassword($(this).val())){
			errTip($(this),"由6-16个字母、数字或下划线组成");
		}
		else{
			cleanErrTip($(this));
		}
	});
	$("#password2").blur(function(){
		if(!isPassword($(this).val())){
			errTip($(this),"由6-16个字母、数字或下划线组成");
		}else if($("#password").val()!=$("#password2").val()){
			errTip($(this),"两次密码不一致");
		}
		else{
			cleanErrTip($(this));
		}
	});
	
});
var isGoIn = true;
$("#reset_password_go").click(function(){
	if(!registCheck())
		return false;
	if(!isGoIn)
		return false;
	isGoIn = false;
	var url = "${pageContext.request.contextPath}/Product/registerUser!resetPassword.action";
	 $.post(url,$("#form_reset_password").serialize(),function(data){
		 //alert(data.status);
		 if("ok"==data.status){
			 //找回密码成功,跳转到登录页面;
			  jingao(data.json,"提示",function(){
				  window.location.href="${pageContext.request.contextPath}/Product/login.jsp";
			 },0);
		 }else{
			 jingao(data.json,"提示","",0);
			 isGoIn = true;
		 }
	 }); 
});

function registCheck(){
	var password = $("#password").val();
	var password2=$("#password2").val();
    var isGo = true;
	 if(!isPassword(password)){
		errTip($("#password"), "由6-16个字母、数字或下划线组成");
		isGo = false;
	}if(!isPassword(password2)){
		errTip($("#password2"), "由6-16个字母、数字或下划线组成");
		isGo = false;
	}if(password!=password2){
		errTip($("#password2"),"两次密码不一致");
		isGo = false;
	}
	
	return isGo;
}

//进度条
var j=0;
function ak(j){
	$(".progress-bar").css({"width":j+"%","display":"block"});
}

function dothis(){
	if(j<=100){
		setTimeout("dothis()",1)
		ak(j);
		j++;
	}else{
		$(".icon-num2").addClass("active");
		$(".reg-icon-word .fr").removeClass("color-ddd").addClass("color-blue");
		return;
	}
};
dothis();
</script>
</html>
