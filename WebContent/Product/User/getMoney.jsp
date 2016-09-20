<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>      
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<title>中泰理财 - 提现</title>
	<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/Product/css/account.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/Product/css/recharge.css" rel="stylesheet" type="text/css"/>
	<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
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
		<div class="recharge_zzc">
			<p>可用余额(元)</p>
			<span style="color:red;"><fmt:formatNumber value="${users.usersInfo.leftMoney * 0.01}" pattern="0.00"/></span>
			<div>中泰理财为您提供银行级别的帐户和资金安全保障</div>
		</div>
		<div class="recharge_money">
			<form id="getMoney_form">
				<ul>
					<li><span class="recharge_inp"><label>提现金额<a class="red">*</a></label><input maxlength="9" id="txt_rechargeMoney" name="rechargeMoney" type="text" class="txt"><span>元</span></span><span class="input_tips">提现的金额只能1元起</span></li>
					<li><span class="recharge_inp"><label>交易密码<a class="red">*</a></label><input  id="txt_pwd" name="password" type="password" class="txt"><span></span></span><span class="input_tips"></span></li>
					<c:if test="${listBankCard ne null}">
					<li><a id="goToWithdraw" class="to_cz" style="cursor: pointer;">确认提现</a></li>
					</c:if>
				</ul>
			</form>
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
					您的账户尚未绑定任何银行卡&nbsp;&nbsp;&nbsp;&nbsp;<a class="red" href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action">立即绑定</a>
				</p>
				<div class="add_bank" id="div_bind_card">
					<span>添加银行卡</span>
				</div>
			</c:otherwise>
		</c:choose>
			<div class="clea"></div>
		</div>
		<div class="recharge_jilu">
			<p>提现流水记录</p>
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
			<div style="  text-align: right;margin: 30px;">
					<jsp:include page="../page.jsp" />
					</div>
					<c:if test="${listTxRecord eq '' || listTxRecord eq '[]' || listTxRecord eq null}">
					<div class="wujilu">
						<span>暂无记录</span>						
					</div>
					</c:if>
		</div>
	</div>
	<!-- 内容页 end-->
		<div class="clea"></div>
	</div>
</div>
<!-- 主内容 end-->
<jsp:include page="../footer.jsp" />
<script type="text/javascript">
$("#div_bind_card").click(function(){
	window.location.href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action";
	
});
$("#goToWithdraw").click(function(){
	var url = "${pageContext.request.contextPath}/Product/User/userRecharge!withdraw.action";
	var su = "";
	//alert($("#getMoney_form").serialize());
	if(!isPrice($("#txt_rechargeMoney").val()) || parseFloat($("#txt_rechargeMoney").val())<1){
		jingao("金额格式错误","提示","",0);
		return false;
	}
	if(($("#txt_pwd").val()=="") ){
		jingao("交易密码不能为空","提示","",0);
		return false;
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
					window.location.href="${pageContext.request.contextPath}/Product/User/userRecharge!getMoney.action";
				},0);
				//var dialog = art.dialog({id: 'dialog_getValidateCode2',title: false,lock:true,fixed:true,content:"提现成功"});
			}else if("ok"!=su.status){
				jingao(su.json,"提示",function(){
					window.location.href="${pageContext.request.contextPath}/Product/User/userRecharge!getMoney.action";
				},0);
				return false;
			}
		}
	});
});
</script>
	</body>
</html>