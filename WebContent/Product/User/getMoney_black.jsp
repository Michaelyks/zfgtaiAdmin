<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>      
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<title>中泰理财 - 我的账户 - 提现</title>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<link href="../css/account.css" rel="stylesheet" type="text/css">
<link href="../css/recharge.css?"+Math.random(); rel="stylesheet" type="text/css">
<link href="../css/cz_bang.css" rel="stylesheet" type="text/css">
<link href="../css/acount_history.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../js/cz_bang.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
</head>

<body>
<jsp:include page="../top.jsp" />
<div class="lujin"><div class="crumbs"><a href="#"><img src="../images/home_ico.png" /></a> &gt; <a href="#">我的账户</a> &gt; <a href="#">帐户管理</a> &gt; <em>提现</em></div></div>
<div class="maincnt wrap cf">
	<jsp:include page="leftMenu.jsp" />
    <div class="mainright fr">
    	
        <div class="recharge_box">
        	<div class="asset"><label>账户总资产：</label><span><fmt:formatNumber value="${(users.usersInfo.totalMoney + coupons) * 0.01}" pattern="0.00"/></span> 元</div>
            <div class="cntbox">
            <form id="recharge_form">
            	<div class="cf">
                    <p>填写提现金额</p>
                    <p><label style="  font-size: 14px;">可提现金额：</label><strong><fmt:formatNumber value="${users.usersInfo.leftMoney * 0.01}" pattern="0.00"/></strong><span>元</span></p>
                    <p><em>*</em><label style="  font-size: 14px;">提现金额：</label><input maxlength="9" id="txt_rechargeMoney" name="rechargeMoney" type="text" class="txt"><span>元</span></p>
                    <p style="font-size:14px;color:red;">提示：提现的金额只能1元起</p>
                    <div class="way">
						<%-- <c:choose>
							<c:when test="${cardLast ne null}">
							<span style="font-size: 14px;">支付方式：</span> 
							<select id="pay_type" name="payType" style="border: 1px solid gray; width: 120px; margin: 20px 10px; padding: 2px 5px;">
								<option value="0" style="">易宝支付</option>
							</select><br>
						<p>已绑定的银行卡</p>
						<p><label style="  font-size: 16px;"><input type="radio" checked="checked">${bankName} **** **** **** ${cardLast}</label><br></p>
						
						<p><a id="goToWithdraw" style="margin-top: 10px;width: 90px; text-align: center;  background: #da2c36; color: white; border-radius: 5px; cursor: pointer;  display: inline-block;">立即提现</a>
										</p>
							</c:when>
							<c:otherwise>
							<p style="font-size:20px;margin: 20px 10px; padding: 2px 5px;">没有已绑定的银行卡&nbsp;&nbsp;&nbsp;&nbsp; <a style="color:red;" href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action#bind_card" >立即绑定</a></p>
							</c:otherwise>
						</c:choose> --%>
						
				<c:choose>
					<c:when test="${listBankCard ne null}">
					<p style="font-size: 20px;margin-top: 10px;">请选择到账的银行卡：</p>
					<c:forEach items="${listBankCard}" var="list" varStatus="index">
						<p style="font-size: 16px;margin-top: 10px;"><label ><input name="pay_card" value="${list.type}" type="radio" ${index.index == 0? 'checked=\'checked\'' : ''}>${list.bankName} **** **** **** ${list.cardLast}</label><br></p>
					</c:forEach>
					
					<p>
					<a id="goToWithdraw" style="margin-top: 10px;width: 90px; text-align: center;  background: #da2c36; color: white; border-radius: 5px; cursor: pointer;  display: inline-block;">立即提现</a>
					</p>
					</c:when>
					<c:otherwise>
					<p style="font-size:20px;margin: 20px 10px; padding: 2px 5px;">没有已绑定的银行卡&nbsp;&nbsp;&nbsp;&nbsp; <a style="color:red;" id="goToBind" href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action#bind_card" >立即绑定</a></p>
					</c:otherwise>
				</c:choose>
					</div>
                </div>
                </form>
                <div style="margin-top: 20px;  margin-left: -10px;">
                <p style="font-size:20px;margin-top: 20px;margin-bottom:20px;">提现流水记录</p>
	              <table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
					<tbody>
					<tr class="head_list">
						<td width="55">流水号</td>
						<td width="75">提现金额(元)</td>
						<td width="50">提现机构</td>
						<td width="50">提现方式</td>	
						<td width="90">提交时间</td>	
						<td width="90">审核时间</td>			
						<td width="50">提现状态</td>
					</tr>
					</tbody>
					<tbody>
					<c:forEach items="${listTxRecord}" var="list" varStatus="s">
						<tr>
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
				<jsp:include page="../page.jsp" />
                </div>          
            </div>
        </div>
    </div>
</div>
<jsp:include page="../footer.jsp" />
</body>
<script type="text/javascript">
choosePage("3");
chooseMenu("1");

$("#goToWithdraw").click(function(){
	var url = "${pageContext.request.contextPath}/Product/User/userRecharge!withdraw.action";
	var su = "";
	//alert($("#recharge_form").serialize());
	if(!isPrice($("#txt_rechargeMoney").val()) || parseFloat($("#txt_rechargeMoney").val())<1){
		jingao("金额格式错误","提示","",0);
		return false;
	}
	$.ajax({
		url:url,
		type:"post",
		data:$("#recharge_form").serialize(),
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
</html>
