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
<title>中泰理财 - 重设支付密码</title>
	</head>
	<body>
	<jsp:include page="header.jsp" />
		<div class="main">
			<ul class="step step_s">
				<li>找回支付密码</li>
				<li>设置支付密码</li>
				<div style="clear:both"></div>
			</ul>
			<div class="user_reg">
				
				<ul class="left left_f">
				 	<form id="form_reset_password"  method="post">
						<li>
							<label><a class="red">*</a>新密码</label>
							<input type="password" id="password" name="users.password"/>
							<i class="rg_p"></i>
							<span class="err_tip"></span>
						</li>
						<div class="clea"></div>
						<li>
							<label><a class="red">*</a>重复新密码</label>
							<input type="password" value="" id="password2" name="password2" />
							<i class="rg_p"></i>
							<span class="err_tip"></span>
						</li>
						<div class="clea"></div>
						<div class="clea"></div>
						<li><a href="javascript:void(0)" id="reset_password_go" class="reg_now reg_now2">确定修改</a></li>
						<input value="${modifyId}" type="hidden" name="modifyId"  />
						<input value="${smsValidateCode}" type="hidden" name="smsValidateCode"  />
					</form>
				</ul>
				
				<div style="clear:both"></div>
			</div>
		</div>
	<jsp:include page="footer.jsp"></jsp:include>
	</body>
<script type="text/javascript">

function errTip(obj,errorMsg){
	obj.siblings(".err_tip").html(errorMsg).css({"display":"inline-block","background-position":"0 -79px"});
}
function cleanErrTip(obj){
	obj.siblings(".err_tip").html("").css({"display":"inline-block","background-position":"0 -110px"});
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
	var url = "${pageContext.request.contextPath}/Product/registerUser!resetPayPassword.action";
	 $.post(url,$("#form_reset_password").serialize(),function(data){
		 //alert(data.status);
		 if("ok"==data.status){
			 //找回密码成功,跳转到登录页面;
			  jingao(data.json,"提示",function(){
				  window.location.href="${pageContext.request.contextPath}/Product/User/userInfo!queryUsersInfoByid.action";
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
</script>
</html>
