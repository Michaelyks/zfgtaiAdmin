<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="../page_500.jsp"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<title>中泰理财 - 我的账户 - 充值</title>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<link href="../css/account.css" rel="stylesheet" type="text/css">
<link href="../css/user_info.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
</head>
<script type="text/javascript">
	$(function(){
		$(".click").click(function(){	
			var fa = $(this).parents(".info_li");
			$(".info").find(".sho").hide();			
			if(fa.find(".hide").hasClass("sho")){
				fa.find(".hide").hide();
				fa.find(".hide").removeClass("sho");				
			}
			else{
				$(".info").find(".sho").removeClass("sho");
				fa.find(".hide").fadeToggle("show");
				fa.find(".hide").addClass("sho");					
			}
			
		});
		$(".info_li").mouseover(function(){
			$(this).children(".click").find("span").eq(1).css("border","1px solid #1480CC");
		}).mouseout(function(){
			$(this).children(".click").find("span").eq(1).css("border","1px solid #ddd");
		});
	});
	/**
	 *修改昵称
	 */
	function modify_nickName(){
		var nickName=$("#nickName").val();
		var a=confirm("确定修改昵称吗？");
		if(isEmpty(nickName)){
			jingao("请输入昵称","提示","",0);
			return false;
		}
		if(!a){
			return ;
		}
		
		var url = "${pageContext.request.contextPath}/Product/User/userInfo!modifyNickName.action?";
		url +="users.usersInfo.nickName="+encodeURI(encodeURI(nickName,"utf-8"),"utf-8");
		$.ajax({
			type : "post",
			url : url,
			success : function(data, textStatus) {
				su = data;
			},
			complete : function(XMLHttpRequest, textStatus) {
				if ("success" == su.status) {
					jingao(su.json,"提示","",0);
				} else if ("err" == su.status) {
					jingao(su.json,"提示","",0);
				} 

			}
		});
		
	}
	/**
	 *修改真实姓名
	 */
	 function modify_realName(){
		var a=confirm("真实姓名修改后将不能修改，确定要修改吗？");
		var realName=$("#realName").val();
			if(isEmpty(realName)){
				jingao("请输入真实姓名","提示","",0);
				return false;
			}
			if(!a){
				return ;
			}
			var url = "${pageContext.request.contextPath}/Product/User/userInfo!modifyRealName.action?";
			url +="users.usersInfo.realName="+encodeURI(encodeURI(realName,"utf-8"),"utf-8");
			$.post(url,function(data){
				if(data.status=="success"){
					jingao(data.json,"提示","",0);
				}else{
					jingao(data.json,"提示","",0);
					return false;
				}
			});
			
			

	}
		/**
		 *绑定身份证
		 */
		 function modify_realName(){
			var a=confirm("真实姓名修改后将不能修改，确定要修改吗？");
			var realName=$("#realName").val();
				if(isEmpty(realName)){
					jingao("请输入真实姓名","提示","",0);
					return false;
				}
				if(!a){
					return ;
				}
				var url = "${pageContext.request.contextPath}/Product/User/userInfo!modifyRealName.action?";
				url +="users.usersInfo.realName="+encodeURI(encodeURI(realName,"utf-8"),"utf-8");
				$.post(url,function(data){
					if(data.status=="success"){
						jingao(data.json,"提示","",0);
					}else{
						jingao(data.json,"提示","",0);
						return false;
					}
				});
				
				

		}
		/**
		 *发送短信验证
		 */
		function get_checkCode(){
			//$("#phonecode").style.display="inline-block";
			//$("#modify_phone").style.display="inline-block";
			jingao("短信已发送,请查收","提示","",0);
			document.getElementById("phonecode").style.display="inline-block";
			document.getElementById("modify_phone").style.display="inline-block";

		} 
		/**
		*邮箱验证
		*/
	function confirmEmail(){
			var email=$("#email").val();
			 var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
			//验证邮箱格式
			 if(isEmpty(email)){
					jingao("邮箱不能为空","提示","",0);
					return false;
				}else if(!reg.test(email)){
					jingao("邮箱格式错误","提示","",0);
					return false;
				}
			//alert("已发送邮箱链接至您的邮箱，请登入邮箱进行验证");
			var url = "${pageContext.request.contextPath}/Product/User/userInfo!sendEmail.action?";
			url +="email="+encodeURI(encodeURI(email,"utf-8"),"utf-8");
			//var url = "${pageContext.request.contextPath}/Product/User/userInfo!sendEmail.action?";
			//url +="email="+encodeURI(encodeURI(email,"utf-8"),"utf-8");
			$.ajax({
				type : "post",
				url : url,
				success : function(data, textStatus) {
					su = data;
				},
				complete : function(XMLHttpRequest, textStatus) {
					if ("success" == su.status) {
						jingao(su.json,"提示","",0);
					} else if ("err" == su.status) {
						jingao(su.json,"提示","",0);
					} 

				}
			});
			//是否验证邮箱
			
		} 
	
	
	

</script>
<body>
<jsp:include page="../top.jsp" />
		<div class="lujin"><div class="crumbs"><a href="#"><img src="../images/home_ico.png" /></a> &gt; <a href="#">我的账户</a> &gt; <a href="#">用户信息</a> &gt; <em>基本资料</em></div></div>
<div class="maincnt wrap cf">
	<jsp:include page="leftMenu.jsp" />
    <div class="mainright fr">
      <div class="wdzh"> 
			<div class="wdzh_zl">
			   <h2>基本资料</h2>
			   <ul class="info">
				<%-- <li class="info_li">
					<div class="click">
						<span class="wdzh_zl_left">
								<i class="nc_n"></i>
								<font>修改昵称 </font>
						</span>
						<span>修改</span>
					</div>
					<div class="hide">
						<span>
							<p><a class="inp_cum">新昵称：</a><input type="text" id="nickName" name="users.usersInfo.nickName" class="input" value="${users.usersInfo.nickName}" /><a onclick="modify_nickName()" >修改昵称</a></p>					
						</span>
					</div>
				</li> --%>
				<li class="info_li">
					<div class="click">
						<span class="wdzh_zl_left">
							<i class="mm_n"></i>
							<font>修改登录密码 </font>
						</span>
						<span>修改</span>
					</div>
					<div class="hide">
					<form id="form_modify_password" >
						<span>
							<p><a class="inp_cum">旧密码：</a><input type="password" id="oldPassword" name="oldPassword" class="input" maxlength="16"  placeholder="原有的登录密码" style="  width: 252px;"/></p>
							<p><a class="inp_cum">新密码：</a><input type="password" id="newPassword" name="newPassword" class="input" maxlength="16"  placeholder="6~16位数字字母下划线组成" style="  width: 252px;"/></p>
							<p><a class="inp_cum">新密码确认：</a><input type="password" id="newPassword2" name="newPassword2" class="input" maxlength="16"  placeholder="请重新输入一遍新密码" style="  width: 252px;"/></p>
							<div>	
								<a id="modify_password" style="cursor: pointer;">修改密码</a>
							</div>
						</span>
					</form>
					</div>
				</li>
				<li class="info_li">
					<div class="click">
						<span class="wdzh_zl_left">
							<i class="mm_n"></i>
							<font>修改支付密码 </font>
						</span>
						<span>修改</span>
					</div>
					<div class="hide">
					<form id="form_modify_payPassword" >
						<span>
							<p style="margin-left: 115px;">注: 默认的支付密码为平台的登录密码</p>					
							<p><a class="inp_cum">旧支付密码：</a><input type="password" id="oldPayPassword" name="oldPayPassword" class="input"  maxlength="16"  placeholder="原有的支付密码" style="  width: 252px;"/></p>
							<p><a class="inp_cum">新支付密码：</a><input type="password" id="newPayPassword" name="newPayPassword" class="input" maxlength="16" placeholder="6~16位数字字母下划线组成" style="  width: 252px;"/></p>
							<p><a class="inp_cum">新支付密码确认：</a><input type="password" id="newPayPassword2" name="newPayPassword2" class="input" maxlength="16"  placeholder="请重新输入一遍新支付密码" style="  width: 252px;"/></p>
							<div>	
								<a id="modify_payPassword">修改支付密码</a>
								<a href="${pageContext.request.contextPath}/Product/find_pay_password.jsp">忘记支付密码？</a>
							</div>
						</span>
					</form>
					</div>
				</li>
					
				
			<!--  <li class="info_li"><span class="wdzh_zl_left">真实姓名：</span><span ><input type="text" id="realName" name="users.usersInfo.realName" class="input" value="${users.usersInfo.realName}" /><a id="modify_realName" onclick="modify_realName()" style="cursor: pointer;">身份认证</a></span></li>-->
			<!--  <li><span class="wdzh_zl_left">我的邀请码：</span><span>${users.id}</span></li>-->
				<li class="info_li">
					<div class="click">
						<span class="wdzh_zl_left">
							<i class="zc_n"></i>
							<font>查看注册时间 </font>
						</span>
						<span>查看</span>
					</div>
					<div class="hide">
						<span>注册时间：<fmt:formatDate value="${usersInfo.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </span>
					</div>
				</li>
			   </ul>
		   </div>
		</div>
    </div>
</div>
<jsp:include page="../footer.jsp" />
</body>
<script type="text/javascript">
choosePage("3");
chooseMenu("3");
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
	if(!isPassword(newPayPassword)){
		jingao("新支付密码格式错误","提示","",0);
		return false;
	}
	if(newPayPassword!=newPayPassword2){
		jingao("两次新支付密码不一致","提示","",0);
		return false;
	}
	if(!allow_modify_pay_password)
		return false;
	allow_modify_pay_password = false;
	 var url = "${pageContext.request.contextPath}/Product/User/userInfo!modifyPayPassword.action";
	 $.post(url,$("#form_modify_payPassword").serialize(),function(data){
		 if("noLogin"==data.status){
			 jingao("登录超时,请重新登录","提示",function(){
				window.location.href="${pageContext.request.contextPath}/Product/login.jsp"; 
			 },0);
		 }else{
			 jingao(data.json,"提示",function(){
				 location.reload();
			 },0);
		 }
		
	 }); 
	
});
	

</script>
</html>
