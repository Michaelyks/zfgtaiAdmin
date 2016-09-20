<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<meta charset="utf-8" />
		<link href="css/common.css" rel="stylesheet" type="text/css">
		<link href="css/login.css"  rel="stylesheet" type="text/css">
		<link href="css/registerNew.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<title>中泰理财 - 找回支付密码</title>
	</head>
	<body>
	<jsp:include page="header.jsp" />
		<div class="main">
			<ul class="step">
				<li>找回支付密码</li>
				<li>设置支付密码</li>
				<div style="clear:both"></div>
			</ul>
			<div class="user_reg">
				
				<ul class="left left_f">
				 	<form id="form_find_pay_password"  method="post">
						<li>
							<label><a class="red">*</a>手机号码</label>
							<input value="" maxlength="11" type="text" id="username" name="users.username"/>
							<i class="rg_u">+86</i>
							<span class="err_tip"></span>
						</li>
						<div class="clea"></div>
						<li class="ver">
							<label><a class="red">*</a>手机验证码</label>
							<input type="text" maxlength="6" name="smsValidateCode" id="smsValidateCode"/>
							<div class="sjm sjm2" id="getValidateCode_div">
								<a id="getValidateCode" style="cursor: pointer;">获取短信验证码</a>
							</div>
							<span class="err_tip"></span>
							<div class="clea"></div>
						</li>
						<div class="clea"></div>
						<li><a id="find_pay_password" href="javascript:void(0)" class="reg_now">下一步</a></li>
					</form>
				</ul>
				
				<div style="clear:both"></div>
			</div>
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

$("#find_pay_password").click(function(){
	var url = "${pageContext.request.contextPath}/Product/registerUser!findPayPassword.action";
	//alert("注册"+$("#form_find_pay_password").serialize());
	//url += "&attention.userId=" + 123456;
	//alert(url);
	if(!registCheck()){
		return false;
	}
	var su = "";
	$.ajax({
		type : "post",
		url : url,
		data:$("#form_find_pay_password").serialize(),
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
	$(obj).siblings(".err_tip").html(errorMsg).css({"display":"inline-block","background-position":"0 -79px"});
}
function cleanErrTip(obj){
	$(obj).siblings(".err_tip").html("").css({"display":"inline-block","background-position":"0 -110px"});
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
	 var url = "${pageContext.request.contextPath}/Product/registerUser!sendSMSMessageFindPayPassword.action";
	 isGoIn = false;
	 $.post(url,"users.username="+username,function(data){
		 //alert(data);
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
<form action="${pageContext.request.contextPath}/Product/registerUser!goToResetPayPassword.action" method="post" id="form_goTo_reset">
<input value="" type="hidden" name="modifyId"  id="hid_modifyId"/>
<input value="" type="hidden" name="smsValidateCode" id="hid_smsValidateCode"  />
</form>
</html>
