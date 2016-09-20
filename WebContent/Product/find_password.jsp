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
				<span class="iconAll icon-line"></span>
				<span class="iconAll icon-num2"></span>
			</div>
			<div class="reg-icon-word">
				<span class="fl color-blue">验证账户信息</span>
				<span class="fr color-ddd">设置新密码</span>
			</div>
			<div class="user_reg">
				<form id="form_find_password" method="post">
					<ul class="left left_f find-pwd">
				 	
						<li class="err_tip" id="message-show">错误提示</li>
						<li class="phone">
							<em><span class="iconAll icon-phone"></span></em>
							<input placeholder="请输入手机号码" maxlength="11" type="text" id="username" name="users.username"/>
						</li>
						<li class="ver">
							<input type="text" maxlength="6" name="smsValidateCode" id="smsValidateCode" placeholder="请输入验证码" />
							<div class="sjm sjm2" id="getValidateCode_div">
								<a id="getValidateCode" class="btn-a blue-bg">获取验证码</a>
							</div>
							<div class="clea"></div>
						</li>
						<div class="clea"></div>
						<li><a id="find_password" href="javascript:void(0)" class="btn-a blue-bg reg_now reg_now2">下一步</a></li>
						
					
					</ul>
				</form>
				<div class="clea"></div>
				<div class="safe-reg">
					<span class="iconAll icon-safe"></span>
					您的信息已使用SSL加密技术，数据传输安全
				</div>
			</div>
		</div>
		<!--  -->
	</div>
		
	<jsp:include page="footer.jsp"></jsp:include>
	</body>
<script type="text/javascript">
	//检查输入是否为空
	$(function(){
		//检查用户名
		$("#username").blur(function(){
			if(!isPhoneNumber($(this).val())){
				errTip($(this), "手机号码格式错误");
			}
			else{
				//$(this).siblings(".err_tip").html("").css({"display":"inline-block","background-position":"0 -110px"});
				var url = "${pageContext.request.contextPath}/Product/registerUser!isUserExist.action";
				 $.post(url,"users.username="+$(this).val(),function(data){
					 //alert(data.status);
					 if("exists"==data.status){
						 //用户名存在,可以发短信;
						 cleanErrTip($("#username"));
					 }else if("ok"==data.status){
						 errTip($("#username"), "用户名不存在");
					 }else{
						 errTip($("#username"), data.json);
					 }
				 }); 
			}
		});
		$("#smsValidateCode").blur(function(){
			 var reg2 = /^[0-9]{6}$/;
			if(!reg2.test($(this).val())){
				//$(this).siblings(".err_tip").html("请输入手机验证码").css({"display":"inline-block","background-position":"0 -79px"});
				errTip($(this), "手机验证码格式错误");
			}
			else{
				//$(this).siblings(".err_tip").html("").css({"display":"inline-block","background-position":"0 -110px"});
				cleanErrTip($(this));
			}
		});
		
	});

$("#find_password").click(function(){
	var url = "${pageContext.request.contextPath}/Product/registerUser!findPassword.action";
	//alert("注册"+$("#form_find_password").serialize());
	//url += "&attention.userId=" + 123456;
	//alert(url);
	if(!registCheck()){
		return false;
	}
	var su = "";
	$.ajax({
		type : "post",
		url : url,
		data:$("#form_find_password").serialize(),
		success : function(data, textStatus) {
			su = data;

		},
		complete : function(XMLHttpRequest, textStatus) {
			//alert(su.json);
			if ("ok" == su.status) {
				$("#hid_modifyId").val(su.json);
				$("#hid_smsValidateCode").val($("#smsValidateCode").val());
				$("#form_goTo_reset").submit();
			} else if ("err" == su.status) {
				jingao(su.json,"提示","",0);
			}

		}
	});
});
function errTip(obj,errorMsg){
	//$(obj).siblings(".err_tip").html(errorMsg).css({"display":"inline-block","background-position":"0 -79px"});
	$("#message-show").html(errorMsg).show();
}
function cleanErrTip(obj){
	//$(obj).siblings(".err_tip").html("").css({"display":"inline-block","background-position":"0 -110px"});
	$("#message-show").html("").hide();
}
function registCheck(){
	var username = $("#username").val(); //手机号
	var smsValidateCode=$("#smsValidateCode").val();
    var reg2 = /^[0-9]{6}$/;
    var isGo = true;
	 if(isEmpty(username)){
		errTip($("#username"), "手机号码不能为空");
		isGo = false;
	}if(!isPhoneNumber(username)){
		errTip($("#username"), "手机号码格式错误");
		isGo = false;
	}if(!reg2.test(smsValidateCode)){
		errTip($("#smsValidateCode"), "手机验证码格式错误");
		isGo = false;
	}
	
	return isGo;
}


var i = 120;
var intervalId = "";
var isGoIn = true;
$("#getValidateCode").click(function(){
	if(!isGoIn)
		return false;
	var username = $("#username").val(); //手机号
	if(!isPhoneNumber(username)){
		errTip($("#username"), "手机号码格式错误");
		return false;
	}
	 var url = "${pageContext.request.contextPath}/Product/registerUser!sendSMSMessageFindPassword.action";
	 isGoIn = false;
	 $.post(url,"users.username="+username,function(data){
		 if("exists"==data.status){
			 errTip($("#username"), "用户名不存在");
			// $("#username").siblings(".err_tip").html("用户名不存在").css({"display":"inline-block","background-position":"0 -79px"});
			 isGoIn = true;
		 }else if("ok"==data.status){
			 cleanDaojishi(intervalId);
			 i=120;
			 intervalId = setInterval("daojishi()",1000);  
			 jingao(data.json,"提示",function(){
			 },0);
		 }else{
			 jingao(data.json,"提示",function(){
				 isGoIn = true;
			 },0);
		 }
		/* if(data.status!="ok"){
			 jingao(data.json,"提示","",0);
			 return false;
		} */
	 }); 
});

function daojishi(){
	 isGoIn = false;
	--i;
	$("#getValidateCode_div").attr("style","background-color:#9AACB6");
	$("#getValidateCode").text("再次获取("+i+")s");
	if(parseInt(i)<=0){
		clearInterval(intervalId);
		$("#getValidateCode_div").removeAttr("style");
		$("#getValidateCode").text("获取短信验证码");
		 isGoIn = true;
	}
}

function cleanDaojishi(intervalId){
	if(""==intervalId)
		return;
	clearInterval(intervalId);
	$("#getValidateCode_div").removeAttr("style");
	$("#getValidateCode").text("获取短信验证码");
	 $("#getValidateCode").attr("disabled",true);
}
</script>
<form action="${pageContext.request.contextPath}/Product/registerUser!goToResetPassword.action" method="post" id="form_goTo_reset">
<input value="" type="hidden" name="modifyId"  id="hid_modifyId"/>
<input value="" type="hidden" name="smsValidateCode" id="hid_smsValidateCode"  />
</form>
</html>
