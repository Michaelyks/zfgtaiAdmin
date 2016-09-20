<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<title>中泰理财 - 我的账户 - 认证资料</title>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<link href="../css/account.css" rel="stylesheet" type="text/css">
<link href="../css/user_info.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
</head>

<body>
<jsp:include page="../top.jsp" />
		<div class="lujin"><div class="crumbs"><a href="#"><img src="../images/home_ico.png" /></a> &gt; <a href="#">我的账户</a> &gt; <a href="#">用户信息</a> &gt; <em>基本资料</em></div></div>
<div class="maincnt wrap cf">
	<jsp:include page="leftMenu.jsp" />
    <div class="mainright fr">
      <div class="wdzh"> 
			<div class="wdzh_zl">
			   <h2>认证资料</h2>
			   <ul class="info">
			   
			 <%-- <li class="info_li">
					<div class="click">
					<c:choose>
						<c:when test="${email eq null}">
						<%//未绑定的时候 %>
							<span class="wdzh_zl_left">
								<i class="yx_n"></i>
								<font><p class="i_top">邮箱绑定 <a class="user_wbd">未绑定</a></p><p class="i_bot user_gray">绑定邮箱，轻松梳理理财信息</p></font>
							</span>
							<span>绑定</span>
						</c:when>
						<c:otherwise>
						<%//已绑定的时候 %>
							<span class="wdzh_zl_left">
								<i class="yx_y"></i>
								<font><p class="i_top">邮箱绑定 <a class="user_bd">${email}</a></p><p class="i_bot user_gray">绑定邮箱，轻松梳理理财信息</p></font>						
							</span>
							<span>修改</span>
						</c:otherwise>
					</c:choose>
					</div>	
				  	<div class="hide">
						<span>
							<p><a class="inp_cum">邮箱地址：</a><input type="text" id="email" name="${users.username}" class="input" value="${users.username}" /><a onclick="confirmEmail()" style="cursor: pointer;">邮箱认证</a></p>									
						</span>
					</div>
				</li> --%>
						
		  		<li class="info_li">	
					<c:choose>
						<c:when test="${phone eq null}">
						<%//未绑定的时候 %>
							<div class="click">
							<span class="wdzh_zl_left">
								<i class="sj_n"></i>
								<font><p class="i_top">手机绑定 <a class="user_wbd">未绑定</a></p><p class="i_bot user_gray">绑定手机，账户资金变动更安全</p></font>	
							</span>
							<span>绑定</span>
							</div>
							<div class="hide">	
							<span>
								<p><a class="inp_cum">手机号：</a><input type="text"  class="input"/><a>获取验证码</a></p>
								<p><a class="inp_cum">验证码：</a><input type="text" class="input" /><a id="get_checkCode" onclick="get_checkCode()" class="renz">绑定</a></p>							
							</span>
							</div>
						</c:when>
						<c:otherwise>
						<%//已绑定的时候 %>
						<div class="click">
							<span class="wdzh_zl_left">
								<i class="sj_y"></i>
								<font><p class="i_top">手机绑定 <a class="user_bd">${phone}</a></p><p class="i_bot user_gray">绑定手机，账户资金变动更安全</p></font>	
							</span>
							<span>修改</span>
							</div>
							<div class="hide" style="margin: 2px;background-color: #DDF0FE;">	
							<span class="succ_tip">
								手机号码修改，将牵扯到您的账户资金安全如确认修改，<br>请联系客服 (客服时间：周一至周五 9:00-21:00，双休日 10:00-18:00)<br/>客服热线：400-806-5993   
							</span>
							</div>
						</c:otherwise>
					</c:choose>				
					
				</li>
				
	 	<li class="info_li">
	 			<c:choose>
	 				<c:when test="${idcard eq null}">
	 				<%//未绑定的时候 %>
	 					<div class="click">
						<span class="wdzh_zl_left">
							<i class="sm_n"></i>
							<font><p class="i_top">实名认证&银行卡绑定 <a class="user_wbd">未认证绑定</a></p><p class="i_bot user_gray">绑定银行卡，轻松理财</p></font>							
						</span>
						<span>认证绑定</span>
						</div>
					<form id = "validate_form">
					<div class="hide" id="bind_card">
						<span id="certification">
							<p><a class="inp_cum">真实姓名：</a><input type="text"  maxlength="6" placeholder="请输入真实姓名" id="realName" name="account.bankAccountName" class="input vali" value="" /></p>
							<span class="check_"></span>
							<p><a class="inp_cum">身份证号：</a><input tupy="text" maxlength="18" placeholder="请输入证件号" id="idcard" name="account.idcard" class="input vali" /></p>
							<span class="check_"></span>
							<p><a class="inp_cum">银行卡号：</a><input tupy="text" maxlength="20" placeholder="请输入卡号" id="cardNo" name="account.bankAccount" class="input vali" style='background:url("") no-repeat 145px center white'/><a class="bank_list">查看支持银行列表</a></p>
							<div class="b_list">
								<ul> 
									<li><img src="../images/BANK_ICBC.png"/>工商银行</li>
									<li><img src="../images/BANK_BOC.png"/>中国银行</li>
									<li><img src="../images/BANK_CCB.png"/>建设银行</li>
									<li><img src="../images/BANK_POST.png"/>邮政储蓄</li>
									<li><img src="../images/BANK_ECITIC.png"/>中信银行</li>
									<li><img src="../images/BANK_CEB.png"/>光大银行</li>
									<li><img src="../images/BANK_HXB.png"/>华夏银行</li>
									<li><img src="../images/BANK_CMBCHINA.png"/>招商银行</li>
									<li><img src="../images/BANK_CIB.png"/>兴业银行</li>
									<li><img src="../images/BANK_SPDB.png"/>浦发银行</li>
									<li><img src="../images/BANK_PINGAN.png"/>平安银行</li>
									<li><img src="../images/BANK_GDB.png"/>广发银行</li>
									<li><img src="../images/BANK_CMBC.png"/>民生银行</li>
									<li><img src="../images/BANK_ABC.png"/>农业银行</li>
									<li><img src="../images/BANK_BOCO.png"/>交通银行</li>
									<div class="clea"></div>
								</ul>
							</div>
							<span class="check_"></span>
							<p><a class="inp_cum">银行预留手机号：</a><input  type="text" maxlength="11" id="prePhone"  class="input vali"  name="account.phone" placeholder="请输入手机号码"/><a id="getValidateCode">获取验证码</a></p>
							<span class="check_"></span>
							<p><a class="inp_cum">验证码：</a><input type="text" maxlength="6" class="input" id="validata_code" name="validateCode" placeholder="请输入验证码"/><a id="confirmBindCard" class="renz">立即认证</a></p>
							<span class="check_"></span>						
						</span>
					</div>
					<input type="hidden" id="requestId"  class="input" name="account.requestId" />
					<input type="hidden" id="bankName"  class="input" name="account.bankName" />		
					</form>
	 				</c:when>
	 				<c:otherwise>
	 				<%//已绑定的时候 %>
	 					<div class="click">
						<span class="wdzh_zl_left">
							<i class="sm_y"></i>
							<font><p class="i_top">实名认证&银行卡绑定 <a class="user_bd">${relName}</a></p><p class="i_bot user_gray">绑定银行卡，轻松理财</p></font>	
						</span>
						<span>解绑</span>
						</div>
						<div class="hide" style="margin: 2px;background-color: #DDF0FE;">	
							<span class="succ_tip">
								真实姓名：${relName}<br/>
								身份证号：${idcard}<br/>
								银行卡号：**** **** **** ${cardLast}<br/>
								银行名称：${bankName}<br/>
								<span style="color:red;">注:银行卡解绑，涉及到用户的资金安全问题，如需解绑，<br>请联系客服 (客服时间：周一至周五 9:00-21:00，双休日 10:00-18:00)<br>客服热线：400-806-5993   </span>
							</span>
							</div>
	 				</c:otherwise>
	 			</c:choose>
				</li>
				<script>
					$(function(){
						
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
						
						
						$("#certification input").click(function(){
	    					
	    					if($(this).val()=="请输入真实姓名"||$(this).val()=="请输入证件号"||$(this).val()=="请输入卡号"||$(this).val()=="请输入手机号码"||$(this).val()=="请输入验证码"){
	    						$(this).val("");
	    					}
	    				})
	    				$("#certification input:eq(0)").blur(function(){
	    					if($(this).val()==""){
	    						$(".check_:eq(0)").css("display","block");
	    						$(".check_:eq(0)").html("请填写真实姓名");
	    					}
	    					else{
	    						$(".check_:eq(0)").css("display","none");
	    						$(".check_:eq(0)").html("");
	    					}
	    					
	    				});
	    				
	    				$("#certification input:eq(1)").blur(function(){
	    					if($(this).val()==""){
	    						$(".check_:eq(1)").css("display","block");
	    						$(".check_:eq(1)").html("请输入身份证号");
	    						isGetCode = false;
	    					}
	    					else{
	    						var ver = /^\d{17}(\d|X)$/i;
	    						if(ver.test($(this).val())){
	    							$(".check_:eq(1)").css("display","none");
	        						$(".check_:eq(1)").html("");
	        						isGetCode = true;
	    						}
	    						else{
	    							$(".check_:eq(1)").css("display","block");
	        						$(".check_:eq(1)").html("号码为18位数字或末尾字母X");
	        						isGetCode = false;
	    						}
	    						
	    						
	    					}
	    					
	    				});
	    				
	    				$("#certification input:eq(2)").blur(function(){
	    					var cardno = $(this).val();
	    					if(cardno==""){
	    						$(".check_:eq(2)").css("display","block");
	    						$(".check_:eq(2)").html("请输入卡号");
	    						isGetCode = false;
	    						return false;
	    					}
	    					var test = new RegExp(/^[0-9]{16,20}$/);
	    					if(!test.test(cardno)){
	    						$(".check_:eq(2)").css("display","block");
	    						$(".check_:eq(2)").html("卡号格式不正确");
	    						isGetCode = false;
	    						return false;
	    					}
	    					$(".check_:eq(2)").css("display","none");
    						$(".check_:eq(2)").html("");
	    					var url = "${pageContext.request.contextPath}/Product/User/validate!checkBankCard.action";
	    					 $.post(url,"account.bankAccount="+cardno,function(data){
	    						 //alert(isGetCode);
	    						 var status = data.status;
	    						// alert(data);//结果!
	    						 if("ok"==status){
    								$(".check_:eq(2)").css("display","none");
    	    						$(".check_:eq(2)").html("");
    	    						$("#bankName").val(data.json);
    	    						isGetCode = true;
    	    						//alert(isGetCode);
	 	    					}else{
	 	    						$(".check_:eq(2)").css("display","block");
	 	    						$(".check_:eq(2)").html(data.json);
	 	    						isGetCode = false;
	 	    						//alert(isGetCode);
	 	    						//return false;
	 	    					}
    						 }); 
	    				});
	    			$("#certification input:eq(3)").blur(function(){
	    					if($(this).val()==""){
	    						$(".check_:eq(3)").css("display","block");
	    						$(".check_:eq(3)").html("请输入手机号码");
	    						isGetCode = false;
	    					}    					
	    					else{
	    						//alert(test.test($(this).val()));
	    						if(isPhoneNumber($(this).val())){
	    							$(".check_:eq(3)").css("display","none");
		    						$(".check_:eq(3)").html("");
		    						isGetCode = true;
	    						}
	    						else{
	    							$(".check_:eq(3)").css("display","block");
		    						$(".check_:eq(3)").html("手机号码格式有误");
		    						isGetCode = false;
	    						}
	    						
	    					}
	    					
	    				});
	    				$("#certification input:eq(4)").blur(function(){
	    					var test = new RegExp(/^[0-9]{4,6}$/);
	    					if($(this).val()==""){
	    						$(".check_:eq(4)").css("display","block");
	    						$(".check_:eq(4)").html("请输入验证码");
	    					}    					
	    					else{    						
	    						if(test.test($(this).val())){
	    							$(".check_:eq(4)").css("display","none");
		    						$(".check_:eq(4)").html("");
		    						isGetCode = true;
	    						}
	    						else{
	    							$(".check_:eq(4)").css("display","block");
		    						$(".check_:eq(4)").html("验证码格式有误");
		    						isGetCode = false;
	    						}							    						
	    					}
	    					
	    				});
	    					    				
					});
					$(function(){
						$(".bank_list").mouseover(function(){
							$(".b_list").css("display","block");
						}).mouseout(function(){
							$(".b_list").css("display","none");
						})
						
					})
				</script>
			<!--  <li class="info_li"><span class="wdzh_zl_left">真实姓名：</span><span ><input type="text" id="realName" name="users.usersInfo.realName" class="input" value="${users.usersInfo.realName}" /><a id="modify_realName" onclick="modify_realName()" style="cursor: pointer;">身份认证</a></span></li>-->
			<!--  <li><span class="wdzh_zl_left">我的邀请码：</span><span>${users.id}</span></li>-->
				
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
</script>

</html>
