<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 首页</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/index.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/register.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
		
		<style type="text/css">
		.smsCode {
			cursor: pointer;
		}
		#nav a{background:#fff;color:black;}
		#top_regist{color:#3598db;}
				
	</style>
	</head>
	<body>
		<jsp:include page="header.jsp" />
		
<!-- 主内容 -->
<div class="main">
	<!-- 注册 -->
	<div class="content">
		<!-- 
		<ul class="reg_step">
			<li>1.填写注册信息</li>
			<li>领取新人专享</li>
		</ul>
		 -->
		<div class="reg">
			<div>
				<div class="fl">
					<p class="title">欢迎注册中泰理财<span class="fr">已有账号？<a style="color:#3598DB;" href="${pageContext.request.contextPath}/Product/login.jsp">立即登陆</a></span></p>
					<form id="registerForm"  method="post">
						<ul>
							<li>
								<span class="border"><label class="phone phone1"><img src="images/reg_phone.jpg"/></label><input id="username" maxlength="11" type="text" name="users.username" class="inp1" value=""/><span class="inp_tis in_t">请输入手机号码</span></span>
							</li>
							<!-- 验证码 -->
						 	<li class="ver" style="display:none;"><input name="validateCode" id="new_validateCode"  maxlength="4" class="inp5" type="text" /><img src="image.jsp" id="CodeImg" align="absmiddle" width="60" height="21" style="cursor: pointer;"><span class="inp_tis in_t2">请输入验证码</span></li>
							<!-- 验证码 end -->
							<li>
								<div class="tis"></div>
							</li>
							<li>
								<span class="border border2"><input id="smsValidateCode"  name="smsValidateCode" maxlength="6" type="text" class="inp2" /><label class="smsCode" id="getValidateCode" style="color:white;background-color:#3898DB;;">获取短信验证码</label>
								<span class="inp_tis in_t2" >请输入短信验证码</span>
								</span>
							</li>
							<li>
								<div class="tis" id="sms_tip"></div>
							</li>
							<li class="mima"><label class="phone"><img src="images/reg_lock.jpg"/></label><input id="password" class="inp3" maxlength="16" type="password" name="users.password" /><span class="inp_tis in_t">请设置登陆密码</span></li>
							<li>
								<div class="tis"></div>
							</li>
							<li class="mima"><label class="phone"><img src="images/reg_lock.jpg"/></label><input id="password2" class="inp4" maxlength="16" type="password" name="password2" /><span class="inp_tis in_t">请确认登陆密码</span></li>
							<li>
								<div class="tis"></div>
							</li>
							<li>
								<p class="agree"><span id="agree_check" class="check checked"></span>我已阅读，并同意签订<a href="${pageContext.request.contextPath}/Product/protocol_reg.jsp">《中泰理财注册协议》</a></p>
								<script>
									$(function(){
										$("#agree_check").click(function(){
											if($(this).hasClass("checked")){
												$(this).removeClass("checked");
											}else{
												$(this).addClass("checked");
											}	
										});
									});
								</script>
								<a  id="regist" href="javascript:void(0)"  class="reg_now">立即注册</a>
							</li>
							<li><p class="ssl">您的信息已使用SSL加密技术，数据传输安全</p></li>
						</ul>
						
					</form>	
					<img src="images/regist.jpg"/>						
				</div>
				<!-- 
				<div class="fr">
					<p>已经有 <span class="red"><fmt:formatNumber value="${registerCount}" pattern="#,##0" /> </span>位理财达人加入中泰理财</p>
					<p>累计投资 <span class="red"><fmt:formatNumber value="${collectMoney * 0.01}" pattern="#,##0" /></span> 元</p>
					<img src="images/xszx.png" width="100%" />
				</div>
				 -->
				<div class="clea"></div>
			</div>						
		</div>
		<!-- 
		<div class="border-b"><img src="images/tengxunyun.jpg" /><span>由腾讯云提供云安全保障</span></div>
		 -->
	</div>
	<!-- 注册 end-->

</div>
<!-- 主内容 end-->
		
<div id="pop" style="display:none;">
	<div class="pop_bg"></div>
	<ul class="pop_tis">
		<li class="ver"><input name="validateCode" id="validateCode"  maxlength="4" class="inp5" type="text" value=""/><img src="image.jsp" id="CodeImg" align="absmiddle" width="60" height="21" style="cursor: pointer;"><span class="inp_tis in_t2">请输入右边的验证码</span></li>
		<li>
			<div class="tis" id="yzCode_tip"></div>
		</li>
		<li>
			<a class="post_code" id="yzCode">提交验证码</a><a class="close">X</a>
		</li>
	</ul>
</div>		
<jsp:include page="footer.jsp" />

<script>
$(function(){
	$(".inp_tis").click(function(){
		$(this).siblings("input").focus();
	});
	ck("#username");
	ck("#smsValidateCode");
	ck("#password");
	ck("#password2");
	ck("#validateCode");
	$("#pop .close").click(function(){
		$("#pop").css("display","none");
	})
	function ck(obj){
		var name = $(obj);
		$(name).bind("input propertychange",function(){e($(this));}).blur(function(){
			if($(this).val() == ""){
				e($(this),1);
			}						
		});	
	}
	function e(obj,tip){			
		if(tip){
			obj.siblings(".inp_tis").css("display","block");
		}
		else{
			obj.siblings(".inp_tis").css("display","none");
		}
	
	}

});
</script>


<script type="text/javascript">
$(function(){
	  $("#CodeImg").click(function(){
			var img = document.getElementById("CodeImg");
    	if(img){
    		//random+=1;
    		img.src="/wgtz/Product/image.jsp?time="+ new Date();
    	}
		});
	 
});

//检查输入是否为空
$(function(){
	//检查用户名
	$("#registerForm li input").eq(0).blur(function(){
		isPhoneRight = false;
		$("#getValidateCode").css("background-color","#9AACB6");
		var phone = $(this).val();
		//alert(phone);
		var test = new RegExp(/^[1][345789][0-9]{9}$/);
		if(phone==""){
			display($(this),"请输入您的手机号码",1);
		}else if(!isPhoneNumber(phone)){
			display($(this),"手机号码格式有误",1);
		}
		else{
			var url = "${pageContext.request.contextPath}/Product/registerUser!isUserExist.action";
			 $.post(url,"users.username="+phone,function(data){
				 if("ok"!=data.status){
					 display($("#username"),data.json,1);
				 }else{
					 display($("#username"),"",2);
					 isPhoneRight = true;
					 $("#getValidateCode").css("background-color","#0085d4");
				 }
			 }); 
		}
	});
	$("#registerForm li input").eq(1).blur(function(){
		var test = new RegExp(/^[0-9]{6}$/);
		var smsCode = $(this).val();
		if(smsCode==""){
			display($(this),"请输入短信验证码",1);
		}else if(!test.test(smsCode)){
			display($(this),"短信验证码格式有误",1);
		}
		else{
			display($(this),"",2);
		}
	});
	$("#registerForm li input").eq(2).blur(function(){
		var test = new RegExp(/^\w{6,16}$/);
		var password = $(this).val();
		if(password=="" || !test.test(password)){
			display($(this),"密码由6-16个字母、数字或下划线组成",1);
		}
		else{
			display($(this),"",2);
		}
	});
	$("#registerForm li input").eq(3).blur(function(){
		var password =$("#password").val();
		var password2 = $(this).val();
		if(password2=="" || password2!=password){
			display($(this),"请输入与上面一致的密码",1);
		}
		else{
			display($(this),"",2);
		}
	});
	/* $("#registerForm li input").eq(4).blur(function(){
		var test = new RegExp(/^[0-9]{4}$/);
		var code = $(this).val();
		//alert(3);
		if(code==""){
			display($(this),"请输入验证码",1);
		}else if(!test.test(code)){
			display($(this),"验证码错误",1);
		}
		else{
			display($(this),"",2);
			var url = "${pageContext.request.contextPath}/Product/registerUser!yzCode.action";
			 $.post(url,"validateCode="+phone,function(data){
				 if("ok"!=data.status){
					 display($("#username"),data.json,1);
				 }else{
					 display($("#username"),"",2);
				 }
			 }); 
			
		}
	}); */
	
	$("#yzCode").click(function(){
		var test = new RegExp(/^[0-9]{4}$/);
		var code = $("#validateCode").val();
		//alert(3);
		if(code==""){
			$("#yzCode_tip").text("请输入右边的验证码");
		}else if(!test.test(code)){
			$("#yzCode_tip").text("验证码错误");
		}
		else{
			$("#yzCode_tip").text("");
			var url = "${pageContext.request.contextPath}/Product/registerUser!yzCode.action";
			 $.post(url,"validateCode="+code,function(data){
				 if("ok"!=data.status){
					 $("#yzCode_tip").text(data.json);
					 $("#new_validateCode").val("");
				 }else{
					 $("#yzCode_tip").text("");
					 $("#new_validateCode").val(code);
					 $("#pop").attr("style","display:none;");
					 sendYzCode();
				 }
			 }); 
			
		}
	});
	
});




var isRegister = true;
$("#regist").click(function(){
	if(!isRegister)
		return false;
	var text = "<img src='${pageContext.request.contextPath}/Product/images/progress.gif' width='24' height='24'> <span style='font-family:楷体;font-size:22px;'>正在注册,请稍候...</span>";
	//alert("ok");
	var url = "${pageContext.request.contextPath}/Product/registerUser!phoneRegister.action";
	//alert("注册"+$("#registerForm").serialize());
	//url += "&attention.userId=" + 123456;
	//alert(url);
	//alert("jinruyanzheng");
	if(!registCheck()){
		return false;
	}
	//alert("zhuce");
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
		//errTip($("#validateCode"), "验证码格式有误");
		jingao("验证码格式有误","提示","",0);
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
var isYzCode = false;
var isPhoneRight = false;
$("#getValidateCode").click(function(){
	if(isPhoneRight)
		$("#pop").css("display","block");
});

function sendYzCode (){
	 $("#sms_tip").text("");
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
			/*  jingao(data.json,"提示",function(){
				 isGoIn = true;
			 },0); */
			 $("#sms_tip").text(data.json);
			 isGoIn = true;
		 }
		/* if(data.status!="ok"){
			 jingao(data.json,"提示","",0);
			 return false;
		} */
	 }); 
}

function daojishi(){
	 isGoIn = false;
	--i;
	$("#getValidateCode").css("background-color","#9AACB6");
	$("#getValidateCode").text("再次获取("+i+")s");
	if(parseInt(i)<=0){
		clearInterval(intervalId);
		$("#getValidateCode").css("background-color","#0085d4");
		$("#getValidateCode").text("获取短信验证码");
		 isGoIn = true;
	}
}

function display(o,t,c){
	//alert(o);
	//alert(d);
	if(c==1){
		o.parents("li").next("li").find(".tis").html(t);
		
		//o.siblings(".tis").html(t).css({"display":"inline-block","background-position":"0 -79px"});
	}
	else if(c==2){
		//o.siblings(".tis").html("").css({"display":"inline-block","background-position":"0 -110px"});
		o.parents("li").next("li").find(".tis").html("");
	}
}
function cleanDaojishi(intervalId){
	if(""==intervalId)
		return;
	clearInterval(intervalId);
	$("#getValidateCode").css("background-color","#0085d4");
	$("#getValidateCode").text("获取短信验证码");
	 $("#getValidateCode").attr("disabled",true);
}
function errTip(obj,errorMsg){
	display($(obj),errorMsg,1);
}
</script>
	</body>
</html>