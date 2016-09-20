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
<style type="text/css">
.a {
  color: blue;
  text-decoration: underline;
}
</style>

<title>零钱包自动转入记录</title>
</head>
<body>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
		<h3 style="text-align: center;">零钱包自动转入记录</h3><br>
		<div id="div_condition" style="text-align: center;" >
		<label>用户名:<input type="text" name="username" id="username" value="${username}" maxlength="11">
		<span>用户帐号:</span> <input id="bankAccount" name="bankAccount" type="text" value="${bankAccount}">
		<span>用户编号:</span> <input id="usersId" name="usersId" type="text" value="${usersId}">
		<input type="button" value="search" id="frm" onclick="Byname()"></label>&nbsp;&nbsp;
		</div>
		
		<table style="width:1200px;margin-top: 20px;width: 100%;" >
		<tr>
		<td>序号</td>
		<td>用户名</td>
		<td>保留金额(元)</td>
		<td>插入时间</td>
		<td>更新时间</td>

		</tr>
		<c:forEach items="${pageUtil.list}" var="item" varStatus="i">
		<tr>
		<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
		<td><a class="a"  target="_blank" href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item.username)}">${myel:jieMiUsername(item.username)}</a></td> 
		   <td><fmt:formatNumber value="${item.leftMoney * 0.01}" pattern="#,##0.##"/></td>
	     <td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	       <td>${item.updateTime}</td>
	       <!--   <td> ${item.bankAccount}</td>-->
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
$(function(){
	$("body").keydown(function(e){
		var ev = document.all ? window.event : e;
		 if(ev.keyCode==13) 
		 {
			 $("#frm").click();
		 }

	    });
	});

	function Byname(){
	
		var url="${pageContext.request.contextPath}/Product/Admin/zeroPurse!showZeroPurse.action?username="+$("#username").val()+"&bankAccount="+$('#bankAccount').val()+"&usersId="+$("#usersId").val();

		window.location.href=url;
	}
</script>
</body>
</html>	