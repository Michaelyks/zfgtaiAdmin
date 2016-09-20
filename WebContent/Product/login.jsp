<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 登录</title>
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/login.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
		
		<style>
			#nav a{background:#fff;color:black;}
			#top_login{color:#3598db;}
			html,body{height:100%;}
			.footer{width:100%;position:absolute;bottom:0;}
			.top{top:690px;}
		</style>

	</head>
	<body>
		<jsp:include page="header.jsp" />
		<div class="login">
			<div class="login_c">
				<img src="./images/login_bg.jpg"/>
				<div class="login_info">
					<p>欢迎登录中泰理财</p>
					<form id="loginForm">
						<ul>
							<li><span class="inp_border"><label></label><input  type="text" id="username" name="users.username" maxlength="11" value="" /><span class="inp_tip"/>请输入手机号码</span></span></li>
							<li><span class="inp_border"><label></label><input type="password" id="password" name="users.password" maxlength="16" value="" /><span class="inp_tip"/>请输入登录密码</span></span></li>
							<li class="forget"><span><span id="IDremember" class="check"></span>记住账号</span><a href="find_password.jsp">忘记密码?</a></li>
							<li><a class="logo_now" id="btnLogin" onclick="goIn()" style="cursor:pointer;">立即登录</a></li>
							<li class="to_reg"><span style="font-size:18px;">还没账号？</span><a href="${pageContext.request.contextPath}/Product/registerUser!regist.action">立即注册</a></li>
						</ul>
					</form>
					<div class="login_border"><i class="txyun"></i><span>您的信息已使用SSL加密技术，数据传输安全</span></div>
					<div class="login_bg"></div>
				</div>
			</div>		
		</div>
		<jsp:include page="footer.jsp" />
		
		<!-- 页面自适应高度 -->
		<script>
			$(function(){
				$(window).resize(function(){
					var H=$(window).height();
					var T=$(".footer").offset().top;
					console.log(H);
					console.log(T);
					if(H<900){
						$(".footer").addClass("top");
					}else{
						$(".footer").removeClass("top");
					}
				});
				
			})
		</script>
		
		
		
		<script>
			$(function(){
				$("#IDremember").click(function(){
					if($(this).hasClass("checked")){
						$(this).removeClass("checked");
					}else{
						$(this).addClass("checked");
					}
					
				});
				$(".inp_tip").click(function(){
					$(this).siblings("input").focus();
				});
				$("#username").bind("input propertychange",function(){e($(this));}).blur(function(){
					if($(this).val() == ""){
						e($(this),1);
					}else{
						e($(this),0);
					}						
				});
				
				$("#password").bind("input propertychange",function(){e($(this));}).blur(function(){
					if($(this).val() == ""){
						e($(this),1);
					}else{
						e($(this),0);
					}						
				});

			});
			function e(obj,tip){			
				if(tip){
					obj.siblings(".inp_tip").css("display","block");
				}
				else{
					obj.siblings(".inp_tip").css("display","none");
				}
			
			}
			
			function goIn(){
				var url = "${pageContext.request.contextPath}/Product/usersLogin!usersLogin.action";
				//alert("登录"+$("#loginForm").serialize());
				//url += "&attention.userId=" + 123456;
				//alert(url);
				var su = "";
				$.ajax({
					type : "post",
					url : url,
					data:$("#loginForm").serialize(),
					success : function(data, textStatus) {
						su = data;
					},
					complete : function(XMLHttpRequest, textStatus) {
						if ("ok" == su.status) {
							jingao(su.json,"提示",function(){
								if("ok" == su.status){
									url="${pageContext.request.contextPath}/Product/login.jsp";
									if(su.url!=null&&su.url!=url){
										window.location.href=""+su.url;	
									}else{
									window.location.href="${pageContext.request.contextPath}/Product/index!loadProduct.action";	
									}
									
								}else{
									window.location.href="${pageContext.request.contextPath}/Product/index!loadProduct.action";
								}
							},0);
						} else if ("err" == su.status) {
							jingao(su.json,"提示","",0);
						} else{
							jingao(su.json,"提示","",0);
						}

					}
				});
			}

			$("#username,#password").keydown(function(event){
				if(event.keyCode==13){
					goIn();
				}
			});
			/* setTimeout("clear()",1000);
			function clear(){
				//alert(3);
				var myUsername = $("#username").val();
				var myPassword = $("#password").val();
				//alert(myPassword);
				if(myUsername!=""){
					e($("#username"),0);
				}
				if(myPassword!=""){
					
					e($("#password"),0);
				}
			} */
		</script>
		
	</body>
</html>