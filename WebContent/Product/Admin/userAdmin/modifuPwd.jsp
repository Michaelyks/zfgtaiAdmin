<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/wecome.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<link href="${pageContext.request.contextPath}/Product/css/user_info.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<title>管理员修改密码</title>
<script type="text/javascript">
	function modifypassword() {
		var oldPassword = $("#oldPassword").val();
		var newPassword = $("#newPassword").val();
		var newPassword2 = $("#newPassword2").val();
		if(oldPassword == ''){
			jingao("旧密码为空","提示","",0);
			return false;
		} 
		if(newPassword == ''){
			jingao("新密码为空","提示","",0);
			return false;
		} 
		if(newPassword2 == ''){
			jingao("确认密码为空","提示","",0);
			return false;
		} 
		if(!isPassword(newPassword)){
			jingao("新密码格式错误","提示","",0);
			return false;
		}
		if(newPassword!=newPassword2){
			jingao("两次新密码不一致","提示","",0);
			return false;
		}
		 var url = "${pageContext.request.contextPath}/Product/loginBackground!mobifyPassword.action";
		 $.post(url,$("#form_modify_password").serialize(),function(data){
			 if("noLogin"==data.status){
				 jingao("登录超时,请重新登录","提示",function(){
					window.location.href="${pageContext.request.contextPath}/Product/loginBackground.jsp"; 
				 },0);
			 }else if("ok"==data.status){
				 jingao(data.json,"提示",function(){
					 window.location.href="${pageContext.request.contextPath}/Product/loginBackground.jsp"; 
					 },0);
			 }else{
				 jingao(data.json,"提示",function(){
					 location.reload();
				 },0);
			 }
			
		 }); 
		
	}
</script>
<style type="text/css">
	.mobify a {
  display: inline-block;
  padding: 2px 4px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background: #da2c36;
  color: white;
  margin: 5px 0 0 114px;
  cursor: pointer;
}
</style>
</head>
<body>
	<div class="center">
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
		<h3>修改密码</h3>
		<form id="form_modify_password" >
				<span>
					<p><a class="inp_cum">旧密码：</a><input type="password" id="oldPassword" name="oldPassword" class="input"  maxlength="16"  placeholder="原有的密码" style="  width: 252px;"/></p>
					<p><a class="inp_cum">新密码：</a><input type="password" id="newPassword" name="newPassword" class="input" maxlength="16" placeholder="6~16位数字字母下划线组成" style="  width: 252px;"/></p>
					<p><a class="inp_cum">新密码确认：</a><input type="password" id="newPassword2" name="newPassword2" class="input" maxlength="16"  placeholder="请重新输入一遍新密码" style="  width: 252px;"/></p>
					<div class="mobify">	
						<a id="modify_password" onclick="modifypassword()">修改密码</a>
					</div>
				</span>
			</form>
		</div>
	</div>
</body>

</html>