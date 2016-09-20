<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 认证资料</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/account.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/userInfo.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
		<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
	</head>
	<body>
			<jsp:include page="../header.jsp"/>
		
		<!-- 主内容 -->
		<div class="main">
			<div class="layout my_account">
					<jsp:include page="leftMenu.jsp"/>
				<!-- 内容页 -->
				<div class="fr my_info">
					<div class="userinfo_title">
						<p>认证资料</p>			
					</div>
					<c:choose>
						<c:when test="${phone eq null}">
						<%-- 未绑定手机的时候 --%>
							<div class="info_list info_1">
							<div class="list_title">
								<p>手机绑定<span class="phone_num"></span></p> 
								<span>绑定手机，账户资金变动更安全</span>
								<a class="info_change">绑定</a>
							</div>
							<ul class="hide_list">
								<li><label>手机号</label><input type="text" /><a class="phone_ver">获取短信验证</a><span class="hide_tips"></span></li>
								<li><label>短信验证码</label><input type="text" /><a class="phone_ver">获取短信验证</a><span class="hide_tips"></span></li>
								<li><a class="xiugai">更换手机号码</a></li>
							</ul>						
							</div>
						</c:when>
						<c:otherwise>
						<%-- 绑定手机的时候 --%>
						<div class="info_list info_1">
							<div class="list_title">
								<p>手机绑定<span class="phone_num">${phone}</span></p> 
								<span>绑定手机，账户资金变动更安全</span>
								<a class="info_change">修改</a>
							</div>
							<ul class="hide_list succ">
								手机号码修改，将牵扯到您的账户资金安全如确认修改，<br>请联系客服 (客服时间：周一至周五 9:00-18:00，双休日 10:00-17:00)<br/>客服热线：400-806-5993 
							</ul>						
							</div>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
		 				<c:when test="${idcard eq null}">
		 				<%-- 未绑定银行卡的时候 --%>
 	 				<div class="info_list info_2">
						<div class="list_title">
							<p>实名认证-银行卡绑定</p>
							<span>绑定银行卡，轻松理财</span>
							<a class="info_change" href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action">认证绑定</a>
						</div>
					</div>
	<!--						<form id = "validate_form">
						<ul class="hide_list" id="certification">
							<li><label>真实姓名</label><input type="text"  maxlength="15" placeholder="请输入真实姓名" id="realName" name="account.bankAccountName" class="input vali" value="" /><span class="hide_tips"></span></li>
							<li><label>身份证号</label><input tupy="text" maxlength="18" placeholder="请输入证件号" id="idcard" name="account.idcard" class="input vali" /><span class="hide_tips"></span></li>
							<li><label>银行卡号</label><input tupy="text" maxlength="20" placeholder="请输入银行卡号" id="cardNo" name="account.bankAccount" class="input vali" />
								<span class="suppor_bank">查看支持银行列表
								<ul class="bang_list">
									<li><img src="../images/BANK_ICBC.png">工商银行</li>
									<li><img src="../images/BANK_BOC.png">中国银行</li>
									<li><img src="../images/BANK_CCB.png">建设银行</li>
									<li><img src="../images/BANK_POST.png">邮政储蓄</li>
									<li><img src="../images/BANK_ECITIC.png">中信银行</li>
									<li><img src="../images/BANK_CEB.png">光大银行</li>
									<li><img src="../images/BANK_HXB.png">华夏银行</li>
									<li><img src="../images/BANK_CMBCHINA.png">招商银行</li>
									<li><img src="../images/BANK_CIB.png">兴业银行</li>
									<li><img src="../images/BANK_SPDB.png">浦发银行</li>
									<li><img src="../images/BANK_PINGAN.png">平安银行</li>
									<li><img src="../images/BANK_GDB.png">广发银行</li>
									<li><img src="../images/BANK_CMBC.png">民生银行</li>
									<li><img src="../images/BANK_ABC.png">农业银行</li>
									<li><img src="../images/BANK_BOCO.png">交通银行</li>
									<div class="clea"></div>
								</ul>
								</span><span class="hide_tips"></span>
							</li>
							<li><label>银行预留手机号码</label><input  type="text" maxlength="11" id="prePhone"  class="input vali"  name="account.phone" placeholder="请输入手机号码"/>
							<a class="bank_ver" id="getValidateCode">获取验证码</a><span class="hide_tips"></span></li>
							<li><label>验证码</label><input type="text" maxlength="6" class="input" id="validata_code" name="validateCode" placeholder="请输入验证码"/><span class="hide_tips"></span></li>
							<li><a id="confirmBindCard" class="xiugai">立即认证</a></li>
						</ul>	
						<input type="hidden" id="requestId"  class="input" name="account.requestId" />
						<input type="hidden" id="bankName"  class="input" name="account.bankName" />	
						</form>				
						</div> -->
		 				</c:when>
		 				<c:otherwise>
		 				<%-- 绑定银行卡的时候 --%>
		 				<div class="info_list info_2">
						<div class="list_title">
							<p>实名认证-银行卡绑定<span class="phone_num">${relName}</span></p>
							<span>绑定银行卡，轻松理财</span>
							<a class="info_change">解绑</a>
						</div>
						<ul class="hide_list succ">
							<span class="succ_tip">
								真实姓名：${relName}<br/>
								身份证号：${idcard}<br/>
								银行卡号：**** **** **** ${cardLast}<br/>
								银行名称：${bankName}<br/>
								<span style="color:red;">注:银行卡解绑，涉及到用户的资金安全问题，如需解绑，<br>请联系客服 (客服时间：周一至周五 9:00-21:00，双休日 10:00-18:00)<br>客服热线：400-806-5993   </span>
							</span>
						</ul>						
						</div>		
		 				</c:otherwise>
	 				</c:choose>
				</div>
				<!-- 内容页 end-->
				<div class="clea"></div>
			</div>
			
		
		</div>
		<!-- 主内容 end-->
		
		
		<jsp:include page="../footer.jsp"/>

		<script>
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
				$(".suppor_bank").mouseover(function(){
					$(".bang_list").css("display","block");
				}).mouseout(function(){
					$(".bang_list").css("display","none");
				});
			});
			
			
			$("#certification input:eq(0)").blur(function(){
				if($(this).val()==""){
					errTip($(this),"请填写真实姓名");
					isGetCode = false;
				}
				else{
					errTip($(this),"");
					isGetCode = true;
				}
				
			});
			
			$("#certification input:eq(1)").blur(function(){
				
				if($(this).val()==""){
					errTip($(this),"请输入身份证号");
					isGetCode = false;
				}
				else{
					var ver = /^\d{17}(\d|X)$/i;
					if(ver.test($(this).val())){
						errTip($(this),"");
						isGetCode = true;
					}
					else{
						errTip($(this),"号码为18位数字或末尾字母X");
						isGetCode = false;
					}
					
					
				}
				
			});
			
			$("#certification input:eq(2)").blur(function(){
				var cardno = $(this).val();
				if(cardno==""){
					errTip($(this),"请输入卡号");
					isGetCode = false;
					return false;
				}
				var test = new RegExp(/^[0-9]{16,20}$/);
				if(!test.test(cardno)){
					errTip($(this),"卡号格式不正确");
					isGetCode = false;
					return false;
				}
				errTip($(this),"");
				var url = "${pageContext.request.contextPath}/Product/User/validate!checkBankCard.action";
				 $.post(url,"account.bankAccount="+cardno,function(data){
					 //alert(isGetCode);
					 var status = data.status;
					// alert(data);//结果!
					 if("ok"==status){
						errTip($(this),"");
						$("#bankName").val(data.json);
						isGetCode = true;
						//alert(isGetCode);
 					}else{
 						errTip($(this),data.json);
 						isGetCode = false;
 						//alert(isGetCode);
 						//return false;
 					}
				 }); 
			});
		$("#certification input:eq(3)").blur(function(){
				if($(this).val()==""){
					errTip($(this),"请输入手机号码");
					isGetCode = false;
				}    					
				else{
					//alert(test.test($(this).val()));
					if(isPhoneNumber($(this).val())){
						errTip($(this),"");
						isGetCode = true;
					}
					else{
						errTip($(this),"手机号码格式有误");
						isGetCode = false;
					}
					
				}
				
			});
			$("#certification input:eq(4)").blur(function(){
				var test = new RegExp(/^[0-9]{4,6}$/);
				if($(this).val()==""){
					errTip($(this),"请输入验证码");
				}    					
				else{    						
					if(test.test($(this).val())){
						errTip($(this),"");
						$(".check_:eq(4)").html("");
						isGetCode = true;
					}
					else{
						errTip($(this),"验证码格式有误");
						isGetCode = false;
					}							    						
				}
				
			});
		</script>
		
		<script type="text/javascript">
		
		$(document).ready(function(){
			
			$("#getValidateCode").click(function(){
				 if(!isGoIn)
					 return false;
				if(!isValidata()){
					return false;
				}
				if(!isGetCode){
					jingao("请检查绑定资料是否正确","提示","",0);
					return false;
				}
				//alert("tijiao: "+isGetCode);
				//alert($("#validate_form").serialize());
				var url = "${pageContext.request.contextPath}/Product/User/validate!bindCard.action";
				var su = "";
				isGoIn = false;
				lockInput(true);
				$.ajax({
					url:url,
					type:"post",
					data:$("#validate_form").serialize(),
					beforeSend:function(XMLHttpRequest){
						$("#psi_tip").text("正在获取验证码...");
						var dialog = art.dialog({id: 'dialog_getValidateCode',title: false,lock:true,fixed:true,content:document.getElementById("psi_load")});
					},
					success:function(data){
						su=data;
					},
					complete:function(XMLHttpRequest,textStatus){
						art.dialog({id: 'dialog_getValidateCode'}).close();
						if("noLogin"==su.status){
							if(confirm("登录已失效,请重新登录")){
								//跳转到登录界面;
								window.location.href="${pageContext.request.contextPath}/Product/login.jsp";
							}
							return false;
						}else if("ok"==su.status){
							var dialog = art.dialog({id: 'dialog_getValidateCode2',title: false,time:4,lock:true,fixed:true,content:"验证码已发送,请注意查收"});
							$("#requestId").val(su.json);
							 cleanDaojishi(intervalId);
							 i=120;
							 intervalId = setInterval("daojishi()",1000);  
						}else if("ok"!=su.status){
							jingao(su.json,"提示","",0);
							isGoIn = true;
							lockInput(false);
							return false;
						}
					}
				});
			});
			
			
			$("#confirmBindCard").click(function(){
				if(!isValidata()){
					return false;
				}
				if(!isGetCode){
					jingao("请检查绑定资料是否正确","提示","",0);
					return false;
				}
				//alert("tijiao: "+isGetCode);
				//alert($("#validate_form").serialize());
				var url = "${pageContext.request.contextPath}/Product/User/validate!confirmBindCard.action";
				var su = "";
				$.ajax({
					url:url,
					type:"post",
					data:$("#validate_form").serialize(),
					beforeSend:function(XMLHttpRequest){
						$("#psi_tip").text("正在绑定银行卡...");
						var dialog = art.dialog({id: 'dialog_confirmBindCard',title: false,lock:true,fixed:true,content:document.getElementById("psi_load")});
					},
					success:function(data){
						su=data;
					},
					complete:function(XMLHttpRequest,textStatus){
						art.dialog({id: 'dialog_confirmBindCard'}).close();
						if("noLogin"==su.status){
							if(confirm("登录已失效,请重新登录")){
								//跳转到登录界面;
								window.location.href="${pageContext.request.contextPath}/Product/login.jsp";
							}
							return false;
						}else if("ok"==su.status){
							jingao("绑定成功","提示",function(){
								window.location.href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action";
							},0);
						}else if("ok"!=su.status){
							jingao(su.json,"提示","",0);
							return false;
						}
					}
				});
			});
			
		});//JQuery结束符号

		var isGetCode = false;
		function isValidata(){
			//isGetCode = false;
			var realName = $("#realName").val();
			var idcard = $("#idcard").val();
			var cardNo = $("#cardNo").val();
			var prePhone = $("#prePhone").val();
			
			if(isEmpty(realName)){
				jingao("真实姓名不能为空","提示","",0);
				return false;
			}
			if(isEmpty(idcard)){
				jingao("身份证号码不能为空","提示","",0);
				return false;
			}
			if(isEmpty(cardNo)){
				jingao("银行卡号不能为空","提示","",0);
				return false;
			}
			if(isEmpty(prePhone)){
				jingao("银行预留手机号不能为空","提示","",0);
				return false;
			}
			//isGetCode = true;
			return true;
			
		}


		var i = 120;
		var intervalId = "";
		var isGoIn = true;
		function daojishi(){
			 isGoIn = false;
			--i;
			$("#getValidateCode").attr("style","background-color:#9AACB6");
			$("#getValidateCode").text("获取("+i+")s");
			if(parseInt(i)<=0){
				clearInterval(intervalId);
				$("#getValidateCode").removeAttr("style");
				$("#getValidateCode").text("获取验证码");
				 isGoIn = true;
			}
		}

		function cleanDaojishi(intervalId){
			if(""==intervalId)
				return;
			clearInterval(intervalId);
			$("#getValidateCode").removeAttr("style");
			$("#getValidateCode").text("获取验证码");
			 $("#getValidateCode").attr("disabled",true);
		}
		function lockInput(isLock){
			if(isLock){
				//锁住;
				$("#validate_form input.vali").each(function(){
					$(this).attr("style","background-color: #D3D2D2;color: #777777;");
					$(this).attr("readonly","readonly");
				});
			}else{
				$("#validate_form input.vali").each(function(){
					$(this).removeAttr("style");
					$(this).removeAttr("readonly");
				});
			}
		}
		
		function errTip(obj,message){
			obj.siblings("span.hide_tips").text(message);
		}
		</script>
		
	</body>
</html>