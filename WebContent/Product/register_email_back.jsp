<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="UTF-8">
<title>中泰理财 - 注册</title>
<link href="css/common.css" rel="stylesheet" type="text/css">
<link href="css/login.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/jquery.superslide.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<style type="text/css">
/* input[type=text], input[type=password] {
        border-color: #bbb;
        height: 32px;
        font-size: 14px;
        border-radius: 2px;
        outline: 0;
        border: #ccc 1px solid;
        padding: 0 10px;
        width: 250px;
        -webkit-transition: box-shadow .5s;
        margin-bottom: 15px;
    } */
</style>
	<script type="text/javascript">
	function emailCheck(){
		var username = $("#username").val(); //邮箱
		var password =$("#passwords").val(); //密码
		var password1=$("#password2").val(); //确认密码
		var validateCode=$("#validateCode").val();
	    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		 if(isEmpty(username)){
			jingao("邮箱不能为空","提示","",0);
			return false;
		}else if(!reg.test(username)){

			jingao("邮箱格式错误","提示","",0);
			return false;
		}else if(password.length<6){

			jingao("密码长度必须大于六位","提示","",0);
			return false;
		}else if(password!=password1){

			jingao("两次密码不一样","提示","",0);
			return false;
		}else if(isEmpty(validateCode)){

			jingao("验证码不能为空","提示","",0);
			return false;
		}else
		if(!document.getElementById("emailagree").checked){

			  jingao("请同意协议","提示","",0);
			  return false;
			}
		return true;
	}
	
	</script>
</head>
<body>
<jsp:include page="top.jsp" />
<script type="text/javascript">
choosePage("1");
//生成验证码
$(function(){
	  $("#getcode").click(function(){
			var img = document.getElementById("CodeImg");
      	if(img){
      		//random+=1;
      		img.src="/wgtz/Product/image.jsp?time="+ new Date();
      	}
		});
	 
});
$(document).ready(function(){
	/**
	 *邮箱失焦验证;
	 */
	 $("#username").blur(function(){
		if(isEmpty($(this).val())){
			 jingao("邮箱不能为空","提示","",0);
			return false;
		}else{
			var url = "${pageContext.request.contextPath}/Product/registerUser!isUserExist.action?";
			url +="users.username="+$(this).val();
			$.post(url,function(data){
				if(data.status=="err"){
					jingao(data.json,"提示","",0);
					return false;
				}else{
				}
			});
		}
	}); 	 
});

</script>
<div class="home2">
	<div class="home2_body regist">

		<img src="images/login_temp2.png" alt="" class="temp1">
		<div class="registform nomargin">
			<ul class="registformtabs">
				<li ref=".tabsbody20140311 li" id="regEmail" class="tabs_4t_0_out tabs_4t_0" ><a class="loginformtabs1" looyu_bound="1">邮箱注册</a></li>
				<!-- <li ref=".tabsbody20140311 li" id="regPhone" class="tabs_4t_0_out tabs_4t_0" ><a class="loginformtabs0" looyu_bound="1">手机注册</a></li> -->

			</ul>
			<div class="tabsbody20140311">
			<div class="tabsbody20140311_header"></div>
				<div class="tabsbody20140311_body">

					<ul class="registformbody">

				<!-- <li id="loginFrom" class="hidden" style="display: list-item;">
				<form action="/Money/userRegisters!phoneregister.action" id="from1" method="post" onsubmit="return isnotnull()">
				<input type="hidden" name="symbol" value="phone"> 
					<table>
						<tr>
							<td class="formitem">昵称</td>
							<td class="inputfield"><input type="text" id="nickname" name="nickname" value="" class="input nickname"/><span class="tips0">请输入昵称</span></td>
						</tr>
						<tbody><tr>
							<td class="formitem"><span class="s1">*</span>&nbsp;手机号码<input type="hidden" id="nickname" name="nickname" value="" class="input nickname"></td>
							<td class="inputfield">
							<div style="position: relative;height: 40px;white-space: nowrap;">
							<input type="text" id="mobile" name="mobile" value="" class="input mobile"><span class="tips0">请输入手机号码</span>
							</div>
							</td>
						</tr>
						<tr>
							<td class="formitem"><span class="s1">*</span>&nbsp;验证码</td>
							<td class="inputfield">
							<div style="position: relative;height: 40px;white-space: nowrap;">
							<input type="text" id="smscode" name="smscode" value="" class="input smscode"><span class="tips0">请输入短信验证码</span><a href="javascript:verification();" class="getsmscode" looyu_bound="1">获取验证码</a>
							</div>
							</td>
						</tr>
						<tr>
							<td class="formitem"><span class="s1">*</span>&nbsp;密码</td>
							<td class="inputfield">
							<div style="position: relative;height: 40px;white-space: nowrap;">
							<input type="password" id="password" name="password" value="" class="input password"><span class="tips0">6-16位字母和数字结合的密码</span>
							</div>
							</td>
						</tr>
						<tr>
							<td class="formfield2"><span class="s1">*</span>&nbsp;确认密码</td>
							<td class="inputfield">
							<div style="position: relative;height: 40px;white-space: nowrap;">
							<input type="password" id="password1" name="password1" value="" class="input password"><span class="tips0">请重复密码</span>
							</div>
							</td>
						</tr>
						<tr>
							<td class="formitem">邀请码</td>
							<td class="inputfield">
							<div style="position: relative;height: 40px;white-space: nowrap;">
							
							
							
							<input type="text" id="remend2" name="recommend" value="" class="input nickname" onblur="check_phonerecommend()"><span class="tips0">可填可不填</span>
							</div>
							</td>
						</tr>
						<tr>
							<td></td><td class="regist20140311"><input type="checkbox" id="sjagree" name="agree" value="on" checked="checked">同意<a class="blue1" href="#" target="_blank" looyu_bound="1">中泰理财注册协议</a>和<a class="blue1" href="#" target="_blank" looyu_bound="1">服务协议？</a><br><br></td>
						</tr>
						<tr>
							<td></td><td class="inputfield"><input type="submit" value="注 册" class="btnlogin"><br><br></td>
						</tr>
						
					</tbody></table>
				</form>
				</li> -->
				<li id="loginPhone" style="display: list-item;">
				<form id="registerForm" action="" method="post"  >
					<table>
					 
					  <!-- <tr><td class="formitem">*为必填内容</td></tr> -->
						<!-- <tr>
							<td class="formitem">昵称</td>
							<td class="inputfield"><input type="text" id="nicknames" name="nickname" value="" class="input nickname"/><span class="tips0">请输入昵称</span></td>
						</tr> --> 
						<tbody><tr>
							<td class="formitem"><span class="s1">*</span>&nbsp;邮箱</td>
							<td class="inputfield">
							<div style="position: relative;height: 40px;white-space: nowrap;">
							<input type="text" id="username" name="users.username" value=""  class="input mobile" placeholder="请输入邮箱名称"><!-- <span class="tips0" style="display: none;">请输入邮箱名称</span> -->
							</div>
							</td>
						</tr>
						<tr>
							<td class="formitem"><span class="s1">*</span>&nbsp;密码</td>
							<td class="inputfield">
							<div style="position: relative;height: 40px;white-space: nowrap;">
							<input type="password" id="passwords" name="users.password" value="" class="input password" placeholder="6-16位字母和数字结合的密码"><!-- <span class="tips0">6-16位字母和数字结合的密码</span> -->
							</div>
							</td>
						</tr>
						<tr>
							<td class="formitem"><span class="s1">*</span>&nbsp;确认密码</td>
							<td class="inputfield">
							<div style="position: relative;height: 40px;white-space: nowrap;">
							<input type="password" id="password2" name="password2" value="" class="input password" placeholder="请重复密码"><!-- <span class="tips0">请重复密码</span> -->
							</div>
							</td>
						</tr>
						<!-- <tr>
							<td class="formitem">邀请码</td>
							<td class="inputfield"><input type="text" id="remend" name="use.id" value="" class="input nickname" onblur="check_recommend()"/><span class="tips0">请输入邀请码</span></td>
							<td class="inputfield">
							<div style="position: relative;height: 40px;white-space: nowrap;">
							
							
							
							<input type="text" id="remend" name="recommend" value="" class="input nickname" onblur="check_recommend()"><span class="tips0">可填可不填</span>
							
							</div>
							</td>
						</tr> -->
						<tr>
						 <td class="formitem"><span class="s1">*</span>&nbsp;验证码</td>
						<!-- <span class="label">验证码:</span> -->
						<td class="inputfield">
						<div style="position: relative;height: 40px;white-space: nowrap;">
						<input type="text" class="input" style="width:85px;" id="validateCode" name="validateCode" value="" placeholder="输入验证码"><!-- <span class="tips0">输入验证码</span>  -->
              		<img src="image.jsp" id="CodeImg" align="absmiddle" width="60" height="21">  <a href="javascript:void(0)" id="getcode" class="color_f73" style="vertical-align: bottom;text-decoration: underline" looyu_bound="1">看不清图片</a><font class="color_fb" id="msg_code"></font>
						</div>
						</td>
						</tr>
						<tr>
							<td></td><td class="regist20140311"><input type="checkbox" id="emailagree" name="agree" value="on" checked="checked">同意 <a class="blue1" href="${pageContext.request.contextPath}/Product/protocol_reg.jsp" target="_blank" looyu_bound="1">中泰理财注册协议</a> 和 <a class="blue1" href="${pageContext.request.contextPath}/Product/protocol.jsp" target="_blank" looyu_bound="1">服务协议</a> <br><br></td>
						</tr>
						<tr><!-- onclick="userregistration()" -->
							<td></td><td class="inputfield"><input type="button" id="register" value="注 册" class="btnlogin" style="cursor: pointer;"><br><br></td>
						</tr>

					</tbody></table>
				</form>
				</li>
			</ul>
			<table class="table0"></table>
			
			<!-- <table class="table0">
				<tr>
					<td class="formfield1"><input type="checkbox" id="agree" name="agree" value="on"/>同意<a class="blue1">中泰理财注册协议？</a><br/><br/></td>
				</tr>
				<tr>
					<td class="formfield1"><input type="submit" value="登 录" onclick="userregistration()" class="btnlogin"/><br/><br/></td>
				</tr>
			</table> -->
			</div>
				<div class="tabsbody20140311_footer"></div>
		</div>
		</div>
		<div class="tologin">已有中泰理财账户？<a class="blue1" href="login.jsp" looyu_bound="1">立即登录</a></div>
	</div>
</div>
<jsp:include page="footer.jsp" />
</body>
<script type="text/javascript">

$(document).ready(function(){
	$("#register").click(function(){
		var url = "${pageContext.request.contextPath}/Product/registerUser!registerUser.action";
		//alert("注册"+$("#registerForm").serialize());
		//url += "&attention.userId=" + 123456;
		//alert(url);
		if(!emailCheck()){
			return false;
		}
		var su = "";
		$.ajax({
			type : "post",
			url : url,
			data:$("#registerForm").serialize(),
			success : function(data, textStatus) {
				su = data;

			},
			complete : function(XMLHttpRequest, textStatus) {
				alert(su.json);
				if ("ok" == su.status) {
					jingao(su.json,"提示",function(){
						window.location.href="${pageContext.request.contextPath}/Product/index!loadProduct.action";
					},0);
				} else if ("err" == su.status) {
					jingao(su.json,"提示","",0);
				} else{
					jingao(su.json,"提示","",0);
				}

			}
		});
	});
	
});


</script> 
</html>