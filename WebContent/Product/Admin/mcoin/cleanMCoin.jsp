<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
<style type="text/css">
.a {
  color: blue;
  text-decoration: underline;
}
</style>

<title>用户信息</title>
</head>
<body>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
	<h3 style="text-align: center;">用户积分信息</h3><br>
	<div id="div_condition" style="text-align: center;" >
	<label>用户名:<input type="text" name="username" id="username" value="${username}" maxlength="11">
	<input type="button" value="search" id="frm" onclick="Byname()">
	</label>
		<input type="button" value="设置喵币清空开关" id="setCleanMcoin">&nbsp;&nbsp;
		<input type="button" value="清空"  disabled="disabled" id="cleanMcoin">
		</div>
		<table style="width:1200px;margin-top: 20px;width: 100%;" >
		<tr>
		<td>序号</td>	
		<td>用户名</td>
		<td>用户id</td>	
		<td>姓名</td>
		<td>剩余积分</td>
		
		<td>扣除积分总和</td>
		<td>获取积分总和</td>
		<td>积分使用率(%)</td>
		</tr>
		<c:forEach items="${pageUtil.list}" var="item" varStatus="i">
		<tr>
		<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
		 <td><a class="a"  target="_blank" href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item.username)}">${myel:jieMiUsername( item.username)}</a></td>	
		 <td>${item.usersId}</td>	
		 <td>${item.real_name}</td>
		 <td>${item.total_point}</td>
		 
		  <td>${item.coin}</td>
		   <td>${item.addMCoin}</td>
		    <td><fmt:formatNumber value="${item.usage_rate}" pattern="#,##0.##"/></td>
		</tr>
		</c:forEach>
		</table>
	<c:choose>
			<c:when test="${pageUtil.list ne '[]' &&  pageUtil.list ne '' && pageUtil.list ne null}"><jsp:include page="/Product/page.jsp" /></c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
			  		<img src="../images/no_record.png" />
			  	</div>
			</c:otherwise>
		</c:choose>
	</div>
</div> 

<script type="text/javascript">
function Byname(){
	var url="${pageContext.request.contextPath}/Product/Admin/meowIncome!loadUserInfo.action?username="+$("#username").val();
	window.location.href=url;
}

$(function(){
	$("#setCleanMcoin").click(function(){
		if($("#cleanMcoin").attr('disabled'))
		$("#cleanMcoin").attr('disabled',false);
		else{
			$("#cleanMcoin").attr('disabled',true);
			
		}
		
	});
	
	
	
	$("#cleanMcoin").click(function(){
		if(confirm("确定清空用户积分？")){
			var url="${pageContext.request.contextPath}/Product/Admin/cleanMCoin!cleanMCoinThread.action";
	        $.post(url,function(data){
	        	alert(data.json);//结果!
			 });
		}
	
	});
});
</script>
</body>
</html>	