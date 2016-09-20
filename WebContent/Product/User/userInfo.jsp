<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="${pageContext.request.contextPath}/Product/page_500.jsp"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 基本资料</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/account.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/userInfo.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
	</head>
	<body>
		<jsp:include page="../header.jsp" />
		<!-- 主内容 -->
		<div class="main">
			<div class="layout my_account">
				<jsp:include page="leftMenu.jsp"/>
				<!-- 内容页 -->
				<div class="fr my_info">
					<div class="userinfo_title">
						<p>基本资料</p>			
					</div>
					<div class="info_list info_1">
						<div class="list_title">
							<p>登录密码</p>
							<span>登录中泰理财账户时需要输入的密码</span>
							<a class="info_change">修改</a>
						</div>
						<form id="form_modify_password" >
						<ul class="hide_list">
							<li><label>原登录密码</label><input type="password" id="oldPassword" name="oldPassword" class="input" maxlength="16"  /><span class="hide_tips">原有的登录密码</span></li>
							<li><label>新登录密码</label><input type="password" id="newPassword" name="newPassword" class="input" maxlength="16" /><span class="hide_tips">6~16位数字字母下划线组成</span></li>
							<li><label>新登录密码确认</label><input type="password" id="newPassword2" name="newPassword2" class="input" maxlength="16" /><span class="hide_tips">请重新输入一遍新密码</span></li>
							<li><a class="xiugai" id="modify_password" style="cursor: pointer;">修改密码</a></li>
						</ul>			
						</form>			
					</div>
					<c:if test="${isPwd eq 'ok' }">
					  <div class="info_list info_2">
						<div class="list_title">
							<p>交易密码</p>
							<span>设置修改交易密码</span>
							<a class="info_change">修改</a>
						</div>
						<form id="form_modify_payPassword" >
						<ul class="hide_list">
							<!-- <li><label>原支付密码</label><input type="password" id="oldPayPassword" name="oldPayPassword" class="input"  maxlength="16" /><span class="hide_tips">原有的支付密码</span></li> -->
							<li><label>手机号码</label><input type="text" id="telPhone" name="users.username" class="input"  maxlength="11" /><span class="hide_tips">绑定的手机号码</span></li>
							<li><label>手机验证码</label><input type="text" id="code" name="code" class="input" maxlength="6" /><span class="hide_tips"></span><a id="sendCode" style="color:#f00;" href="javascript:void(0);">发送验证码</a></li>
							<li><label>新支付密码</label><input type="password" id="newPayPassword" name="newPayPassword" class="input" maxlength="6"/><span class="hide_tips">6位纯数字组成</span></li>
							<li><label>新支付密码确认</label><input type="password" id="newPayPassword2" name="newPayPassword2" class="input" maxlength="16" /><span class="hide_tips">请重新输入一遍新支付密码</span></li>
							<!-- <li><label>请输入手机验证码</label><input type="text" id="code" name="code" class="input" maxlength="6" /><span class="hide_tips"></span><a id="sendCode" href="javascript:void(0);">发送验证码</a></li> -->
							<li><a class="xiugai" id="modify_payPassword">修改支付密码</a><%-- <a class="forget_zf" href="${pageContext.request.contextPath}/Product/find_pay_password.jsp">忘记支付密码？</a> --%></li>
						</ul>		
						</form>				
					 </div>
					</c:if>

					<div class="info_list info_3">
						<div class="list_title2">
							<p>注册时间</p>
							<span>您的注册时间为：<a><fmt:formatDate value="${usersInfo.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a></span>
						</div>					
					</div>
					
				</div>
				<!-- 内容页 end-->
				<div class="clea"></div>
			</div>
			
		
		</div>
		<!-- 主内容 end-->
		
		<jsp:include page="../footer.jsp"/>

		<script>
		var issend=false;
			$(function(){
				$(".list_title").click(function(){	
					var fa = $(this).parents(".info_list");
					$(".info_list").find(".sho").hide();			
					if(fa.find(".hide_list").hasClass("sho")){
						fa.find(".hide_list").hide();
						fa.find(".hide_list").removeClass("sho");				
					}
					else{
						$(".my_info").find(".sho").removeClass("sho");
						fa.find(".hide_list").slideToggle("show");
						fa.find(".hide_list").addClass("sho");					
					}
					
				});
				//滑动展出结束
				
				//发送修改密码的验证码
				$("#sendCode").click(function(){
					if(issend){
						return;
					}
					var tel=$("#telPhone").val();
					if(!isPhoneNumber(tel)){
						jingao("手机号码有误！","提示","",0);	
						return false;
					}
					issend=true;
					$(this).text('验证码发送中，请稍候...');
					var username="users.username="+tel;
					var url = "${pageContext.request.contextPath}/Product/registerUser!sendSMSMessageFindPayPassword.action";
					if(!$(this).hasClass("false")){
						$.ajax({ 		//发送验证码
							url:url,
							data:username,
							success:function(data){
								if('ok'==data.status){
									countTime("#sendCode");
								}else {
									$("#sendCode").text('发送验证码');
									issend=false;
									jingao(data.json,"提示","",0);
								}
								
							},
							error:function(){
								$(this).text('发送验证码');
								issend=false;
								jingao("网络错误！","提示","",0);	
							}
						});
					} 
				});
			
			});
			function countTime(obj){
				$(obj).addClass("false");
				var time=120;
				var t=setInterval(function(){
					$(obj).html(time+"s");
					time--;
					if(time==0){
						clearInterval(t);
						issend=false;
						$(obj).removeClass("false").html("发送验证码");
					}
				},1000);
			}
			function resetTime(){
				clearInterval(t);
				issend=false;
				$(obj).removeClass("false").html("发送验证码");
			}
			
		</script>
		
		<script type="text/javascript">
		var allow_modify_password = true;
		$("#modify_password").click(function(){
			var newPassword = $("#newPassword").val();
			var newPassword2 = $("#newPassword2").val();
			if(!isPassword(newPassword)){
				jingao("新密码格式错误","提示","",0);
				return false;
			}
			if(newPassword!=newPassword2){
				jingao("两次新密码不一致","提示","",0);
				return false;
			}
			if(!allow_modify_password)
				return false;
			allow_modify_password = false;
			 var url = "${pageContext.request.contextPath}/Product/User/userInfo!modifyPassword.action";
			 $.post(url,$("#form_modify_password").serialize(),function(data){
				 if("noLogin"==data.status){
					 jingao("登录超时,请重新登录","提示",function(){
						window.location.href="${pageContext.request.contextPath}/Product/login.jsp"; 
					 },0);
				 }else if("ok"==data.status){
					 jingao(data.json,"提示",function(){
							window.location.href="${pageContext.request.contextPath}/Product/login.jsp"; 
						 },0);
				 }else{
					 jingao(data.json,"提示","",0);
					 allow_modify_password = true;
				 }
			 }); 
			
		});
		var allow_modify_pay_password = true;
		$("#modify_payPassword").click(function(){
			var newPayPassword = $("#newPayPassword").val();
			var newPayPassword2 = $("#newPayPassword2").val();
			var newPaycode=$("#code").val();		//验证码
			var tel=$("#telPhone").val();
			if(!isPhoneNumber(tel)){
				jingao("手机号码有误！","提示","",0);	
				return false;
			}
			if(!isPassword(newPayPassword)){
				jingao("新支付密码格式错误","提示","",0);
				return false;
			}
			if(newPayPassword!=newPayPassword2){
				jingao("两次新支付密码不一致","提示","",0);
				return false;
			}
			if(newPaycode==''){
				jingao("验证码不能为空","提示","",0);
				return false;
			}
			if(!allow_modify_pay_password)
				{return false;}	
			allow_modify_pay_password = false;
			 var url = "${pageContext.request.contextPath}/Product/User/userInfo!modifyPayPassword.action";
			 $.post(url,$("#form_modify_payPassword").serialize(),function(data){
				 if("noLogin"==data.status){
					 jingao("登录超时,请重新登录","提示",function(){
						window.location.href="${pageContext.request.contextPath}/Product/login.jsp"; 
					 },0);
				 }else{
					 if("ok"==data.status){
						 jingao(data.json,"提示",function(){
							 allow_modify_pay_password=true;
							 location.replace(location);	//刷新页面
						 },0); 
					 }else{
						 allow_modify_pay_password = true;
						 jingao(data.json,"提示",null,0);
					 }
					 
				 }
				
			 }); 
			
		});
		</script>
		
	</body>
</html>