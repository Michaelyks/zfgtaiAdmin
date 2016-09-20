<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询用户信息</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<style type="text/css">
	.sereach {
  width: 200px;
  height: 32px;
  line-height: 32px;
  text-align: center;
  border: 1px solid #009DDA;
  border-radius: 5px;
}
 .select1 {
  border-radius: 5px;
  border-color: #009DDA;
  margin-right: 5px;
}
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
<script type="text/javascript">
	function queryIns(name,isbindbank){
		if(isbindbank == 1){
			window.open("${pageContext.request.contextPath}/Product/Admin/investors!findInvertors.action?status=all&name="+name);
		}else{
			alert("没有绑定银行卡");
		}
	}
	function queryProduct(){
		var username=$("#username").val();
		var url = "${pageContext.request.contextPath}/Product/Admin/userStat!searchUserInfo.action?username="+username;
		window.location.href=url;
	}
	function ins(username){
		var url = "${pageContext.request.contextPath}/Product/Admin/account!showMyAccount.action?username="+username;
		window.open(url);
	}
	function queryCzRecord(name){
		var url = "${pageContext.request.contextPath}/Product/Admin/recharge!rechargeMoneyRecord.action?status=all&name="+name+"";
		window.open(url);
	}
	function queryTxRecord(name){
		var url = "${pageContext.request.contextPath}/Product/Admin/checkTxsq!loadTxsq.action?status=all&name="+name+"";
		window.open(url);
	}
	function queryCoupon(name){
		var url = "${pageContext.request.contextPath}/Product/Admin/sendCoupon!couponRecord.action?username="+name+"";
		window.open(url);
	}
	function queryZiJin(name){
		var url = "${pageContext.request.contextPath}/Product/Admin/fundRecord!loadFund.action?name="+name+"";
		window.open(url);
	}
	function queryInsterest(name){
		var url = "${pageContext.request.contextPath}/Product/Admin/interestDetails!findInvertorsByProduct.action?username="+name+"";
		window.open(url);
	}
</script>
<script type="text/javascript">
	$(function(){
		$("#username").keydown(function(event){
			if(event.keyCode==13){
				queryProduct();
			}
		});
	 });
	
</script>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>用户信息表</h3>
	<span>关键字:</span> <input id="username" name="username" type="text" value="${username}" maxlength="11" placeholder="用户名">
	<a class="sereach" href="javascript:queryProduct();" id="sereach">查询</a>
	<table border="0.5" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td width="100px;">用户ID</td>
			<td width="100px;">用户名</td>
			<td width="100px;">姓名</td>
			<td width="100px;">性别</td>
			<td width="100px;">年龄</td>
			<td width="100px;">生日</td>
			<td width="100px;">所属省份</td>
			<td width="100px;">所属城市</td>
			<td width="100px;">电话类型</td>
			<td width="200px;">注册时间</td>
			<td width="100px;">注册平台</td>
			<td width="100px;">是否绑定银行卡</td>
			<td width="100px;">操作</td>
		</tr>
		<c:forEach items="${list}" var="item" varStatus="i">
			<tr>				
				<td>${item.id}</td>
				<td>${myel:jieMiUsername(item.username)}</td>
				<td>${item.usersInfo.realName}</td>
				<td>${item.usersInfo.sex}</td>
				<td>${item.usersInfo.age}</td>
				<td><fmt:formatDate value="${item.usersInfo.birthday}" pattern="MM-dd"/></td>
				<td>${item.province}</td>
				<td>${item.city}</td>
				<td>${item.cardType}</td>
				<td>${item.insertTime}</td>
				<td>
					<c:if test="${item.registPlatform == 0}">web端注册</c:if>
					<c:if test="${item.registPlatform == 1}">Android移动端</c:if>
					<c:if test="${item.registPlatform == 2}">IOS移动端</c:if>
					<c:if test="${item.registPlatform == 3}">微信注册</c:if>
				
				</td>
				<td>
					<c:choose>
						<c:when test="${item.usersInfo.isBindBank == '1'}">是</c:when>
						<c:otherwise>否</c:otherwise>
					</c:choose>
				</td>
				<td style="margin: 10px;">
					<a class="a" href="javascript:queryIns('${myel:jieMiUsername(item.username)}','${item.usersInfo.isBindBank}')">查看投资记录</a>
					<a class="a" href="javascript:queryCzRecord('${myel:jieMiUsername(item.username)}')">查看充值记录</a><br>
					<a class="a" href="javascript:queryTxRecord('${myel:jieMiUsername(item.username)}')">查看提现记录</a>
					<a class="a" href="javascript:queryCoupon('${myel:jieMiUsername(item.username)}')">查看投资券</a><br>
					<a class="a" href="javascript:queryZiJin('${myel:jieMiUsername(item.username)}')">用户资金流水</a>
					<a class="a" target="_blank" href="javascript:queryInsterest('${myel:jieMiUsername(item.username)}')">付息明细</a><br>
					<a class="a" target="_blank" href="${pageContext.request.contextPath}/Product/Admin/account!showMyAccount.action?username=${myel:jieMiUsername(item.username)}">查看钱包</a>
					<c:if test="${item.usersInfo.isBindBank == '1'}">
					   <a class="a" target="_blank" href="${pageContext.request.contextPath}/Product/Admin/bindCardRecord!findAccount.action?name=${myel:jieMiUsername(item.username)}">查看绑卡人信息</a>
					</c:if>   
					<p>&nbsp;</p>
				</td>
			</tr>
		</c:forEach>
	</table>
	</div>
</div>
</body>
</html>