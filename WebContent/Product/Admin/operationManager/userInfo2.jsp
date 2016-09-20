<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息表</title>
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
		var insertTime=$("#insertTime").val();
		var username=$("#username").val();
		var isbindbank=$("#isbindbank option:selected").val();
		var level=$("#level option:selected").val();
		var inMoney1=$("#inMoney1").val();
		var inMoney2=$("#inMoney2").val();
		var channel=$("#channel").val();
		
		var url = "${pageContext.request.contextPath}/Product/Admin/userStat!getRegister.action?insertTime="+insertTime+"&username="+username+"&isbindbank="+isbindbank+"&channel="+channel+"&level="+level+"&inMoney1="+inMoney1+"&inMoney2="+inMoney2;
		window.location.href=url;
	}
	
	function goqueryProduct(){
		var insertTime=$("#insertTime").val();
		var username=$("#username").val();
		var isbindbank=$("#isbindbank option:selected").val();
		var level=$("#level option:selected").val();
		var inMoney1=$("#inMoney1").val();
		var inMoney2=$("#inMoney2").val();
		var channel=$("#channel").val();
		var goPage=$("#goPage").val();
		if(isNaN(goPage)){
			alert("跳转页格式错误");
			return false;
		}
		if(isNaN(inMoney1)){
			alert("金额格式错误");
			return false;
		}
		if(isNaN(inMoney1)){
			alert("金额格式错误");
			return false;
		}
		var url = "${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?insertTime="+insertTime+"&username="+username+"&isbindbank="+isbindbank+"&channel="+channel+"&level="+level+"&inMoney1="+inMoney1+"&inMoney2="+inMoney2+"&goPage="+goPage;
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
		var url = "${pageContext.request.contextPath}/Product/Admin/sendCoupon!couponRecord.action?username="+name+"&useTime="+""+"";
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
	function profitDiagram(id)
	{
		//var url = "${pageContext.request.contextPath}/Product/Admin/operationManager/profitDiagrams.jsp?usersId="+id+"";
		var url = "${pageContext.request.contextPath}/Product/statistics!queryAllUserProfit.action?usersId="+id+"";
		window.open(url);
	}
	function queryShiftTo(name){
		var url = "${pageContext.request.contextPath}/Product/Admin/usersPurse!loadUsersShiftTo.action?username="+name+"";
		window.open(url);
	}
	function queryCoinPurse(name){
		var url = "${pageContext.request.contextPath}/Product/Admin/usersPurse!loadUsersCoinPurse.action?username="+name+"";
		window.open(url);
	}
	function queryRollOut(name){
		var url = "${pageContext.request.contextPath}/Product/Admin/usersPurse!loadUsersRollOut.action?username="+name+"";
		window.open(url);
	}
	function queryMcoin(name){
		var url = "${pageContext.request.contextPath}/Product/Admin/meowIncome!findUsersMcoin.action?username="+name+"";
		window.open(url);
	}
	
</script>
<script type="text/javascript">
	$(function(){
		$("#registPlatform option[value='${registPlatform}']").attr("selected",true);
		$("#username,#channel,#insertTime,#isbindbank").keydown(function(event){
			if(event.keyCode==13){
				queryProduct();
			}
		});
		
		$("#inMoney1").blur(function(){
			var inMoney1=$("#inMoney1").val();
			var inMoney2=$("#inMoney2").val();
			if(inMoney1!=""&&inMoney2!="" && inMoney1>inMoney2){
				$("#inMoney1").val(inMoney2);
				$("#inMoney2").val(inMoney1);
			}
		});
		
		$("#inMoney2").blur(function(){
			
			var inMoney1=$("#inMoney1").val();
			var inMoney2=$("#inMoney2").val();
			
			if(inMoney1!=""&&inMoney2!="" && inMoney1>inMoney2){
				$("#inMoney1").val(inMoney2);
				$("#inMoney2").val(inMoney1);
			}
		});
		
		
	 });
	
</script>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>用户信息表2</h3>
	<%-- <span>关键字:</span> <input id="username" name="username" type="text" value="${username}" maxlength="11" placeholder="用户ID/用户名/姓名">
	<span>渠道:</span> <input id="channel" name="channel" type="text" value="${channel}"> --%>
	<span>注册时间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
	
	<%-- <span>是否绑卡</span>
	<select id="isbindbank">
		<option value="">所有</option>
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
	<span>等级</span>
	<select id="level">
		<option value="">所有</option>
		<option value="0">0</option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
		<option value="6">6</option>
	</select>
		<span>投资总额范围:</span> <input id="inMoney1" name="inMoney1" type="text" value="${inMoney1}" style="width:50px"> -<input id="inMoney2" name="inMoney2" type="text" value="${inMoney2}" style="width:50px"> （万）
	 --%><a class="sereach" href="javascript:queryProduct();" id="sereach">查询</a>
	<table border="0.5" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td width="100px;">编号</td>
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
			<td width="100px;">投资总金额</td>
			<td width="100px;">VIP等级</td>
			
			<!-- <td width="200px;">操作</td> -->
		</tr>
		<c:forEach items="${listUsers}" var="item" varStatus="i">
			<tr>
				<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
				<td>${item.id}</td>
				<td><a class="a"  href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item.username)}">${myel:jieMiUsername(item.username)}</a></td>
				<td>${item.usersInfo.realName}</td>
				<td>${item.usersInfo.sex}</td>
				<td>${item.usersInfo.age}</td>
				<td>${item.usersInfo.birthday}</td>
				
				<td>${item.province}</td>
				<td>${item.city}</td>
				<td>${item.cardType}</td>
				<td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
				<td><%-- <fmt:formatNumber value="${item.inMoney}" pattern="#,##0.##"/> --%></td>
				<td><%-- ${item.level} --%></td>
				<%-- <td style="margin: 10px;">
					<a class="a" href="javascript:queryIns('${myel:jieMiUsername(item.username)}','${item.isBindBank}')">查看投资记录</a>
					<a class="a" href="javascript:queryCzRecord('${myel:jieMiUsername(item.username)}')">查看充值记录</a><br>
					<a class="a" href="javascript:queryTxRecord('${myel:jieMiUsername(item.username)}')">查看提现记录</a>
					<a class="a" href="javascript:queryCoupon('${myel:jieMiUsername(item.username)}')">查看投资券</a><br>
					<a class="a" href="javascript:queryZiJin('${myel:jieMiUsername(item.username)}')">用户资金流水</a>
					
					<a class="a" target="_blank" href="javascript:queryInsterest('${myel:jieMiUsername(item.username)}')">付息明细</a><br>
					<a class="a" target="_blank" href="${pageContext.request.contextPath}/Product/Admin/account!showMyAccount.action?username=${myel:jieMiUsername(item.username)}">查看钱包</a>
					<c:if test="${item.isBindBank == '1'}">
					   <a class="a" target="_blank" href="${pageContext.request.contextPath}/Product/Admin/bindCardRecord!findAccount.action?name=${myel:jieMiUsername(item.username)}">查看绑卡人信息</a>
					</c:if> 
					<br>  <a class="a" target="_blank" href="javascript:profitDiagram('${item.id}')">收益曲线图</a>
					<a class="a" href="javascript:queryMcoin('${myel:jieMiUsername(item.username)}')">查看喵币</a><br>
					
					<a class="a" href="javascript:queryShiftTo('${myel:jieMiUsername(item.username)}')">零钱包转入记录</a><br>
					<a class="a" href="javascript:queryRollOut('${myel:jieMiUsername(item.username)}')">零钱包转出记录</a><br>
					<a class="a" href="javascript:queryCoinPurse('${myel:jieMiUsername(item.username)}')">零钱包余额</a><br>
					<p>&nbsp;</p>
				</td> --%>
			</tr>
		</c:forEach>
	</table>
		<c:choose>
			<c:when test="${listUsers ne '[]' &&  listUsers ne '' && listUsers ne null}"><jsp:include page="/Product/page.jsp" />
			<%-- 跳转页面:<input id="goPage" name="goPage" type="text" value="${goPage}" style="width:50px">
				<a class="sereach" href="javascript:goqueryProduct();">跳转</a> --%>
			</c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
			  		<img src="../images/no_record.png" />
			  	</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<script type="text/javascript">
	/* var k4 = new Kalendae.Input("insertTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	}); */
	 $(function(){
		$("#isbindbank option[value='${isbindbank}']").attr("selected",true);
		$("#level option[value='${level}']").attr("selected",true);
	 });
</script>
</body>
</html>