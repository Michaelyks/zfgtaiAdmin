<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<title>中泰理财 - 注册</title>
	</head>
	<body>
	
	<jsp:include page="header.jsp" />
		<div class="main">
			<ul class="step">
				<li>填写注册信息</li>
				<li>领取新人专享</li>
				<div style="clear:both"></div>
			</ul>
			<div class="user_reg">
				
				<ul class="left">
				 	<form id="registerForm"  method="post">
						<li>
							<label><a class="red">*</a>手机号码</label>
							<input value="" maxlength="11" type="text" id="username" name="users.username" style="font-size: 16px;"  value=""/>
							<i class="rg_u">+86</i>
							<span class="err_tip"></span>
						</li>
						<li class="ver ver2">
							<label><a class="red">*</a>验证码</label>
							<input type="text" maxlength="4" name="validateCode" id="validateCode" style="font-size: 16px;"  value=""/>
							<img src="image.jsp" id="CodeImg" align="absmiddle" width="60" height="21">
							<a href="javascript:void(0)" id="getcode"  class="kbq">看不清，换一张</a>
							<span class="err_tip"></span>
							<div class="clea"></div>
						</li>
						<div class="clea"></div>
						<li class="ver">
							<label><a class="red">*</a>手机验证码</label>
							<input type="text" maxlength="6" name="smsValidateCode" id="smsValidateCode" style="font-size: 16px;"  value=""/>
							<div class="sjm" id="getValidateCode_div">
								<a id="getValidateCode" style="cursor: pointer;">获取短信验证码</a>
							</div>
							<span class="err_tip"></span>
							<div class="clea"></div>
						</li>
						<div class="clea"></div>
						<li>
							<label><a class="red">*</a>设置密码</label>
							<input type="password" maxlength="16" id="password" name="users.password" value=""/>
							<i class="rg_p"></i>
							<span class="err_tip"></span>
						</li>
						<li>
							<label><a class="red">*</a>确认密码</label>
							<input type="password" maxlength="16" id="password2" name="password2"  value=""/>
							<i class="rg_p"></i>
							<span class="err_tip"></span>
						</li>
						<li class="xiey"><input type="checkbox" id="emailagree" name="agree" value="on" checked="checked"/>同意<span><a style="color:#2AB350;" class="blue1" href="${pageContext.request.contextPath}/Product/protocol_reg.jsp" target="_blank" looyu_bound="1">《中泰理财注册协议》</a></span><%--  和 <a class="blue1" href="${pageContext.request.contextPath}/Product/protocol.jsp" target="_blank" looyu_bound="1">《服务协议》</a> --%></li>
						<li><a id="regist" href="javascript:void(0)" class="reg_now">完成注册</a></li>
					</form>
				</ul>
				
				<div class="right">
					<div class="acount_man">
						<p>已经有<span class="yellow_n"><fmt:formatNumber value="${registerCount}" pattern="#,##0" /></span><a class="yellow_f">位</a>理财达人加入中泰理财</p>
						<p>累计投资<span class="yellow_n"><fmt:formatNumber value="${collectMoney * 0.01}" pattern="#,##0" /></span><a class="yellow_f">元</a></p>
					</div>
					<a><img src="images/reg_adTemp.jpg"/></a>
				</div>
				<div style="clear:both"></div>
			</div>
		</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<input type="hidden" id="hid_regist" value="false">
	</body>
<script type="text/javascript">
	//检查输入是否为空
	$(function(){
		//检查用户名
		$("#registerForm li input").eq(0).blur(function(){
			$("#hid_regist").val("false");
			var phone = $(this).val();
			var test = new RegExp(/^[1][35789][0-9]{9}$/);
			if(phone==""){
				display($(this),"请输入您的手机号码",1);
			}else if(!test.test(phone)){
				display($(this),"手机号码格式有误",1);
			}
			else{
				var url = "${pageContext.request.contextPath}/Product/registerUser!isUserExist.action";
				 $.post(url,"users.username="+phone,function(data){
					 if("ok"!=data.status){
						 display($("#username"),data.json,1);
						 $("#hid_regist").val("false");
					 }else{
						 display($("#username"),"",2);
						 $("#hid_regist").val("true");
					 }
				 }); 
			}
		});
		$("#registerForm li input").eq(1).blur(function(){
			$("#hid_regist").val("false");
			var test = new RegExp(/^[0-9]{4}$/);
			var code = $(this).val();
			if(code==""){
				display($(this),"请输入验证码",1);
			}else if(!test.test(code)){
				display($(this),"验证码错误",1);
			}
			else{
				display($(this),"",2);
				$("#hid_regist").val("true");
			}
		});
		$("#registerForm li input").eq(2).blur(function(){
			$("#hid_regist").val("false");
			var test = new RegExp(/^[0-9]{6}$/);
			var smsCode = $(this).val();
			if(smsCode==""){
				display($(this),"请输入手机验证码",1);
			}else if(!test.test(smsCode)){
				display($(this),"手机验证码格式有误",1);
			}
			else{
				display($(this),"",2);
				$("#hid_regist").val("true");
			}
		});
		$("#registerForm li input").eq(3).blur(function(){
			$("#hid_regist").val("false");
			var test = new RegExp(/^\w{6,16}$/);
			var password = $(this).val();
			if(password=="" || !test.test(password)){
				display($(this),"由6-16个字母、数字或下划线组成",1);
			}
			else{
				display($(this),"",2);
				$("#hid_regist").val("true");
			}
		});
		$("#registerForm li input").eq(4).blur(function(){
			$("#hid_regist").val("false");
			var password =$("#password").val();
			var password2 = $(this).val();
			if(password2=="" || password2!=password){
				display($(this),"请输入与上面一致的密码",1);
			}
			else{
				display($(this),"",2);
				$("#hid_regist").val("true");
			}
		});
		
	})
	
function errTip(obj,errorMsg){
		display($(obj),errorMsg,1);
}
	
$(function(){
	  $("#getcode").click(function(){
			var img = document.getElementById("CodeImg");
    	if(img){
    		//random+=1;
    		img.src="/wgtz/Product/image.jsp?time="+ new Date();
    	}
		});
	 
});
var isRegister = true;
$("#regist").click(function(){
	if(!isRegister)
		return false;
	var text = "<img src='${pageContext.request.contextPath}/Product/images/progress.gif' width='24' height='24'> <span style='font-family:楷体;font-size:22px;'>正在注册,请稍候...</span>";
	if($("#hid_regist").val()=="false"){
		return false;
	}
	//alert("ok");
	var url = "${pageContext.request.contextPath}/Product/registerUser!phoneRegister.action";
	//alert("注册"+$("#registerForm").serialize());
	//url += "&attention.userId=" + 123456;
	//alert(url);
	if(!registCheck()){
		return false;
	}
	$(this).attr("disabled",false);
	var id = chuangkou(text,"正在注册...","",0,true);
	isRegister = false;
	var su = "";
	$.ajax({
		type : "post",
		url : url,
		data:$("#registerForm").serialize(),
		success : function(data, textStatus) {
			su = data;

		},
		complete : function(XMLHttpRequest, textStatus) {
			//alert(su.json);
			closeAlert(id);
			if ("ok" == su.status) {
			/* 	var suOk = "注册成功";
				if(su.json!=""){
					suOk ="";
					suOk +="<div align='left'><p style='font-size: 20px; margin: 10px;'>注册成功</p><p style='color: rgb(63, 148, 139); font-size: 16px; margin: 10px;'>立即领取新手专享，享受高额收益</p>";
					suOk +="<p><a style='color: red; margin: 10px; font-size: 14px;' href='${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?productId="+su.json+"'>点击领取</a></p></div>";
				}
				jingao(suOk,"提示",function(){
					window.location.href="${pageContext.request.contextPath}/Product/index!loadProduct.action";
				},0); */
				var param = "";
				if(su.json!=""){
					param="?productId="+su.json;
				}
				window.location.href="${pageContext.request.contextPath}/Product/register_step2.jsp"+param;
			} else if ("err" == su.status) {
				jingao(su.json,"提示","",0);
			} else{
				jingao(su.json,"提示","",0);
			}
			$(this).attr("disabled",true);
			isRegister = true;
		}
	});
});
	//o为对象，t为提示语，c显示样式，1为错误，2为正确
function display(o,t,c){
		if(c==1){
			o.siblings(".err_tip").html(t).css({"display":"inline-block","background-position":"0 -79px"});
		}
		else if(c==2){
			o.siblings(".err_tip").html("").css({"display":"inline-block","background-position":"0 -110px"});
		}
}
function registCheck(){
	var username = $("#username").val(); //手机号
	var password =$("#password").val(); //密码
	var password1=$("#password2").val(); //确认密码
	var validateCode=$("#validateCode").val();
	var smsValidateCode=$("#smsValidateCode").val();
	
    var reg2 = /^[0-9]{4}$/;
    var reg3 = /^[0-9]{6}$/;
    //alert(password);
    var isGo = true;
	 if(!isPhoneNumber(username)){
		//jingao("手机号码格式错误","提示","",0);
		errTip($("#username"), "手机号码格式错误");
		isGo = false;
	} if(password.length<6){
		errTip($("#password"), "密码长度必须大于六位");
		//jingao("密码长度必须大于六位","提示","",0);
		isGo =  false;
	} if(""==password1){
		errTip($("#password2"), "密码不能为空");
		//jingao("密码不能为空","提示","",0);
		isGo =  false;
	} if(password!=password1){
		errTip($("#password2"), "两次密码不一样");
		//jingao("两次密码不一样","提示","",0);
		isGo =  false;
	} if(!reg2.test(validateCode)){
		errTip($("#validateCode"), "验证码格式有误");
		//jingao("验证码格式有误","提示","",0);
		isGo =  false;
	} if(!reg3.test(smsValidateCode)){
		errTip($("#smsValidateCode"), "手机验证码格式有误");
		//jingao("手机验证码格式有误","提示","",0);
		isGo =  false;
	} if(!document.getElementById("emailagree").checked){

		  jingao("请同意协议","提示","",0);
		  isGo =  false;
	}
	//alert(isGo);
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
		display($("#username"),"手机号码格式错误",1);
		return false;
	}
	 var url = "${pageContext.request.contextPath}/Product/registerUser!sendSMSMessage.action";
	 isGoIn = false;
	 $.post(url,"users.username="+username,function(data){
		 if("exists"==data.status){
			 display($("#username"),"该手机已被注册",1);
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
</html>
