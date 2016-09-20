<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>      
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<title>中泰理财 - 提现</title>
	<meta http-equiv="x-ua-compatible" content="IE=edge" > 
	<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/Product/css/account.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/Product/css/recharge.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/Product/css/public.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/Product/js/AutoComplete.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
</head>
<body>
	<jsp:include page="../header.jsp" />
<!-- 主内容 -->
<div class="main">
	<div class="layout my_account">
		<jsp:include page="leftMenu.jsp" />
	<!-- 内容页 -->
	<div class="fr my_info">
		<!--<div class="recharge_zzc">
			<p>可用余额(元)</p>
			<span style="color:red;"><fmt:formatNumber value="${users.usersInfo.leftMoney * 0.01}" pattern="0.00"/></span>
			<div>中泰理财为您提供银行级别的帐户和资金安全保障</div>
		</div>-->
		<ul class="list_title" id="list_title">
			<li class="on">提现</li>
			<li>提现记录</li>
			<div class="clea"></div>
		</ul>
		<div class="line1"></div>
		<form id="getMoney_form" autocomplete="off" class="box-chan">
			
			<div class="recharge_money mt40">
				<ul>
					<li>
						<span class="recharge_inp">
							<label>账户余额：</label>
							<span class="orange-color f24"> 0.00 </span>元
						</span>
					</li>
					<li>
						<span class="recharge_inp">
							<label>提现银行：</label>
							<span class="banks"></span>
						</span>
					</li>
					<li>
						<span class="recharge_inp">
							<label>提现金额：</label>
							<input maxlength="9" id="txt_rechargeMoney" autocomplete="off" name="rechargeMoney" type="text" class="txt" value="">
							
						</span>
						<span class="orange-color">*充值金额必须≥1元</span>
					</li>
					<li>
						<span class="recharge_inp">
							<label>交易密码：<a class="red">*</a></label>
							<input autocomplete="off" maxlength="17" id="txt_pwd" name="payPassword" type="password" class="txt">
						</span>
						<span>忘记交易密码？</span>
						<c:if test="${isPwd eq 'no'}">
							<span class="input_tips" >*您未设置交易密码,<a id="firstsetpwd" style="color:#f00;cursor: pointer;">点击设置交易密码</a></span>
						</c:if>
					</li>
				</ul>
			</div>
		
			<div class="recharge_money">
			<ul>
			<%-- <c:if test="${isFirstCZ eq '1'}"> --%>
				<li>
					<div class="recharge_inp"><label>开户地址<a class="red">*</a></label>
					<!-- <select name="province" id="province" class="form-select">
						<option>请选择</option>
					</select> -->
					<div class="select">
						<input type="text" id="province" name="province" data-value='' class="input province setselect contain" />			
						<div class="auto_hidden" id="provinceAuto">
							<!--自动填充-->
							</div>
					</div>
					</div>
						<div class="select">
							<input type="text" id="city" name="city" data-value='' value="" disabled="disabled" class="input province setselect contain" />
							<div class="auto_hidden" id="cityAuto">
								<!--自动填充-->
								</div>
						</div>
						
				</li>
				<li><div class="recharge_inp"><label>开户行<a class="red">*</a></label>
					<div class="select">
						<input type="text" id="zh" name="braBankName" data-value='' value="" disabled="disabled" class="input zh setselect contain" />
						<div class="auto_hidden" id="zhAuto">
							<!--自动填充-->
							</div>
					</div>
					</div> 
				</li>
			<%-- </c:if>  --%>
			 
			</ul>
			
			<a id="goToWithdraw" class="to_cz pay-tx">确认提现</a>
		</div>

		
		<div class="recharge_bank">
			<c:choose>
			<c:when test="${listBankCard ne null}">
			<c:forEach items="${listBankCard}" var="list" varStatus="index">
				<div class="add_bank mybank on_bank">
					<p>${list.bankName}</p>
					<p>**** **** **** ${list.cardLast}</p>
				</div>
			</c:forEach>
			</c:when>
			<c:otherwise>
				<p>
					为了您的账户安全，首次提现请完善开户行信息&nbsp;&nbsp;&nbsp;&nbsp;
				</p>
				 
			</c:otherwise>
		</c:choose>
			<div class="clea"></div>
			<p class="red" style="font-size:16px;">温馨提示：1、提现、充值零手续费；</p>
			<p class="red" style="font-size:16px;text-indent:55PX;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、为防止恶意提现，每天只能提现一次；</p>
			<p class="red" style="font-size:16px;text-indent:55PX;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、资金将于下一个工作日到账（节假日顺延），具体到卡时间以银行处理时间为准。</p>
			
		</div>
			<input type="hidden" name="province" id="province_" value="" />
			<input type="hidden" name="city" id="city_" value="" />
			<input type="hidden" name="braBankName" id="braBankName_" value="" />
		</form>
		<div class="recharge_jilu box-chan" style="display:none;">
			<table>
				<tbody>
					<tr class="list_title">
						<td>流水号</td>
						<td>提现金额(元)</td>
						<td>提现机构</td>
						<td>提现方式</td>	
						<td>提交时间</td>	
						<td>审核时间</td>			
						<td>提现状态</td>
					</tr>
					<c:forEach items="${listTxRecord}" var="list" varStatus="s">
						<tr ${s.count % 2 == 0 ? 'style=\"background-color: #FCFCFC;\"':''}>
							<td class="td0" title="${list.recordNumber}">${list.recordNumber}</td>
							<td class="td2" ><fmt:formatNumber value="${list.money*0.01}" pattern="#,##0.##" type="number" /></td>
							<td class="td3" >${list.account.bankName}</td>
							<td class="td4" >
								<c:if test="${list.account.type eq '0'}">易宝支付</c:if>
								<c:if test="${list.account.type eq '1'}">连连支付</c:if>
							</td>
							<td class="td5" title="<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td class="td1"><fmt:formatDate value="${list.checkTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td class="td6" >${list.txzt}</td>
						</tr>
					</c:forEach>									
				</tbody>
			</table>
			
			<c:if test="${listTxRecord eq '' || listTxRecord eq '[]' || listTxRecord eq null}">
				<div class="nothing-img"><img src="../images/nothing-img.jpg"/></div>
			</c:if>
			<div class="mt40" id="page">
				<jsp:include page="../page.jsp" />
			</div>
		</div>
	</div>
	<!-- 内容页 end-->
		<div class="clea"></div>
	</div>
</div>
<!-- 主内容 end-->
<jsp:include page="../footer.jsp" />
<script type="text/javascript">
$(function(){
var isFirstCZ='${isFirstCZ}';
var isPwd='${isPwd}';

var setPwd='';
var setDbPwd='';
$.ajaxSetup({ cache: false });
function firstSetPwd(){
	 setDoubleInput('password','password','设置交易密码',function(){
			$.ajax({
				url:'${pageContext.request.contextPath}/Product/User/userInfo!setPayPassword.action',
				data:{'newPayPassword':setPwd,'newPayPassword2':setDbPwd},
				success:function(data){
					if("ok"==data.status){
						isPwd='ok';
					}else{
						jingao(data.json,"提示",null,0);
						return false;
					}					
					jingao("密码设置成功，立即提现","提示",function(){location=location;},0);
				},
				error:function(){
					jingao("密码设置失败","提示",null,0);
				}
			});
		},1);
}

$(".list_title li").click(function(){
	$(this).addClass("on").siblings().removeClass("on");
	$(".box-chan:eq("+$(this).index()+")").show().siblings(".box-chan").hide();
})
 
$("#firstsetpwd").click(function(){firstSetPwd();});
$("#div_bind_card").click(function(){
	window.location.href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action";
	
});
$("#goToWithdraw").click(function(){
	 if(isPwd=="no"){  //如果没有交易密码
		jingao("尚未设置交易密码，请先设置交易密码","提示",function(){firstSetPwd()},0);
		return false;
	} 
	
	if(!formPass()){
		jingao("开户地址有误，请重新填写","提示",null,0);
		return false;
	}
	
	$("#province_").val($("#province").attr("data-value"));
	$("#city_").val($("#city").attr("data-value"));
	$("#braBankName_").val($("#zh").val());
	
	var url = "${pageContext.request.contextPath}/Product/User/userRecharge!llWithdrawCash.action";
	var su = "";
	//alert($("#getMoney_form").serialize()); || parseFloat($("#txt_rechargeMoney").val())<1
	if(!isPrice($("#txt_rechargeMoney").val())){
		jingao("【提现金额】格式错误","提示","",0);
		return false;
	}

		if(parseFloat($("#txt_rechargeMoney").val())<1){
		jingao("【提现金额】必须1元起","提示","",0);
		return false;
	}
	if(hasDot(parseFloat($("#txt_rechargeMoney").val()))){
		if(parseFloat($("#txt_rechargeMoney").val()).toString().split(".")[1].length >2){
			jingao("【提现金额】不能超过2位小数","提示","",0);
			return false;
		}
	}
	if($("#txt_pwd").val()==""||!isPassword($("#txt_pwd").val()) ){
		jingao("支付密码错误","提示","",0);
		return false;
	}
	if(isFirstCZ=="1"){
		/* if($("#province").val()=="请选择" || $("#city").val()=="请选择" ){ 
			jingao("开户地址不能为空","提示","",0);
			return false;
		}
		if($("#zh").val()=="请选择" ){ 
			jingao("开户行不能为空","提示","",0);
			return false;
		} */
	}
	$.ajax({	
		url:url,
		type:"post",
		data:$("#getMoney_form").serialize(),
		beforeSend:function(XMLHttpRequest){
			$("#psi_tip").text("正在提交,请稍等...");
			var dialog = art.dialog({id: 'dialog_goToWithdraw',title: false,lock:true,fixed:true,content:document.getElementById("psi_load")});
		},
		success:function(data){
			su=data;
		},
		complete:function(XMLHttpRequest,textStatus){
			art.dialog({id: 'dialog_goToWithdraw'}).close();
			if("noLogin"==su.status){
				if(confirm("登录已失效,请重新登录")){
					//跳转到登录界面;
					window.location.href="${pageContext.request.contextPath}/Product/login.jsp";
				}
				return false;
			}else if("ok"==su.status){
				jingao(su.json,"提示",function(){
					window.location.href="${pageContext.request.contextPath}/Product/User/myAccount!showMyAccount.action";
				},0);
				//var dialog = art.dialog({id: 'dialog_getValidateCode2',title: false,lock:true,fixed:true,content:"提现成功"});
			}else{
				jingao(su.json,"提示",function(){
					window.location.href="${pageContext.request.contextPath}/Product/User/userRecharge!getMoney.action";
				},0);
				return false;
			};
		}
	});
});
var urlEncode = function (param, key, encode) {
	  if(param==null) return '';
	  var paramStr = '';
	  var t = typeof (param);
	  if (t == 'string' || t == 'number' || t == 'boolean') {
	    paramStr +=  ((encode==null||encode) ? encodeURIComponent(param) : param);
	  } else {
	    for (var i in param) {
	      var k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);
	      paramStr += urlEncode(param[i], k, encode);
	    }
	  }
	  return paramStr;
	};
function hasDot(num) {
    if (!isNaN(num)) {
        return ((num + '').indexOf('.') != -1) ? true : false;
    }
}


	var province="",city='',zh='';	//  province省份   city市县   
	var proviceValue='',cityValue='',zhValue='';	// 
var autoCompleteProvince='',autoCompleteCity='',autoCompleteZh='';

	inputbind();		//触发绑定事件
	getProvince();
	
function formPass(){
	var isFirstCZ = '${isFirstCZ}';
	if(isFirstCZ=="1"){
		if(proviceValue!= '' && cityValue!='' && zhValue!=''){
			return true;
		}else {
			return false;		
		}
	}
	return true;
	
}	

	function inputbind(){		//给所有输入框添加绑定事件，并获取其值，包含一部分逻辑判断
		$("#province").blur(function(){
			province=$(this).val();
			proviceValue=$(this).attr("data-value");
			if(proviceValue!=''){
				getCity();
			}
		})
		$("#province").focus(function(){
			//重置其他参数
			city='',zh='',cityValue='',zhValue='';
			$("#city,#zh").val("").click(null).keyup(null);
			$("#cityAuto,#zhAuto").html("").removeClass("auto_show").addClass("auto_hidden");
		});
		$("#city").blur(function(){
			city=$(this).val();
			cityValue=$(this).attr("data-value");
			if(cityValue!='') {
				getZh();
			}
		})
		$("#city").focus(function(){
			if(province=="") {
				$("#province").focus();
				return;
			}
			//重置其他参数
			zh='',zhValue='';
			$("#zh").val("").click(null).keyup(null);
			$("#provinceAuto").removeClass("auto_show").addClass("auto_hidden");
			$("#zhAuto").html("").removeClass("auto_show").addClass("auto_hidden");
		});
		$("#zh").blur(function(){
			zh=$(this).val();
			zhValue=$(this).attr("data-value");
		})
		$("#zh").focus(function(){
			if(province==''){
				$("#province").focus();
			}else if(city==''){
				$("#city").focus();
			}
			$("#provinceAuto,#cityAuto").removeClass("auto_show").addClass("auto_hidden");
		})
	}
	function getProvince(){
		$.ajax({		//请求省份
				type:'post',
				url:'${pageContext.request.contextPath}/Product/User/userRecharge!queryProvince.action',		//访问的地址
				dataType: "json",  
				success:function(data){
					//var data=JSON.parse(data);			//解析json
					var data=eval(data);
					console.log(data);
					var city=new Array();
					var cityValue=new Array();
					for(var i=0;i<data.length;i++){		//循环遍历json,获取省份
						city.push(data[i].province);	//把所有城市保存在数组里
						cityValue.push(data[i].city_code);
					}
					autoCompleteProvince=new AutoComplete('province','provinceAuto',city,cityValue);	//初始化下拉 province：input输入框，provinceAuto,下拉狂  ,province：城市数组
					$("#province").click(function(){autoCompleteProvince.showAll();});
					$("#province").keyup(function(){autoCompleteProvince.start(event);});
				},error:function(){
					jingao("网络有问题，请刷新后重试","温馨提示",null,1);
				}
			});
	}
	function getCity(){
		console.log(province);
		$("#city").attr("placeholder","数据加载中...");
		$.ajax({		//请求市县
				type:'post',
				contentType:'application/x-www-form-urlencoded; charset=utf-8',
				url:'${pageContext.request.contextPath}/Product/User/userRecharge!queryCity.action?province='+urlEncode(province),		//访问的地址
	            dataType: "json",  
	            contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success:function(datas){
					$("#city").removeAttr("placeholder").removeAttr("disabled");
					var datas=eval(datas);		//解析json
					console.log(datas);
					var city=new Array();		
					var cityValue=new Array();		
					for(var i=0;i<datas.length;i++){		//循环遍历json,获取省份
						city.push(datas[i].city);		//把所有城市保存在数组里
						cityValue.push(datas[i].city_code);
					}
					autoCompleteCity=new AutoComplete('city','cityAuto',city,cityValue);		//初始化下拉 city：input输入框，cityAuto,下拉狂  ,city：城市数组
					$("#city").click(function(){autoCompleteCity.showAll();});
					$("#city").keyup(function(){autoCompleteCity.start(event);});	
				},error:function(error){
					console.log('cityerr:'+error);
					jingao("网络有问题，请刷新后重试","温馨提示",null,1);
				}
			});
	};
	function getZh(){
		$("#zh").attr("placeholder","数据加载中...");
		var su = "";
		$.ajax({		//请求支行
				type:'post',
				url:'${pageContext.request.contextPath}/Product/User/userRecharge!querySubbranch.action?city='+cityValue,		//访问的地址
				success:function(data){
					su=data;
					console.log('result:'+su)
				},
				complete : function(XMLHttpRequest, textStatus) {
					console.log('sucess:'+su)
					if(su.status=="error"){
						$("#zh").attr("placeholder","");
						jingao(su.json,null,1);
					}else{
						 $("#zh").removeAttr("placeholder").removeAttr("disabled");
						console.log(su);
						var zh=new Array();
						var zhValue=new Array();	
						for(var i=0;i<su.length;i++){		
							zh.push(su[i].brabank_name);		//把所有城市保存在数组里
							zhValue.push(su[i].prcptcd);
						}
						autoCompleteZh=new AutoComplete('zh','zhAuto',zh,zhValue);//初始化下拉 zh：input输入框，zhAuto,下拉狂  ,zh：城市数组
						$("#zh").click(function(){autoCompleteZh.showAll();});
						$("#zh").keyup(function(){autoCompleteZh.start(event);});
					}
					
				},error:function(error){
					console.log("error:"+error)
				}
			});
	};
});
	(function(){if(navigator.userAgent.toLowerCase().indexOf("chrome")!=-1){var selectors=document.getElementsByTagName("input");for(var i=0;i<selectors.length;i++){if((selectors[i].type!=="submit")&&(selectors[i].type!=="password")){selectors[i].value=" "}}setTimeout(function(){for(var i=0;i<selectors.length;i++){if(selectors[i].type!=="submit"){selectors[i].value=""}}},100)}})()
</script>
	</body>
</html>