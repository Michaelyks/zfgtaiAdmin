<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>零钱包 - 用户零钱包数据报表</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
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
</head>
<body>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
		<h3 style="text-align: center;">用户零钱包信息</h3><br/>
		<div id="div_condition" style="text-align: center;" >
		<label>用户名:<input type="text" name="usersname" id="usersname" value="${usersname}" maxlength="11">
		<input type="button" value="search" id="frm" onclick="Byname()"></label>&nbsp;&nbsp;
		</div>
		
		<table style="width:1200px;margin-top: 20px;width: 100%;" >
		<tr>
		<td>序号</td>
		<td>用户名</td>
		<td>插入时间</td>
		<td>剩余金额</td>
		
		
		</tr>
		<c:forEach items="${pageUtil.list}" var="item" varStatus="i">
		<tr>
		<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
		<td><a class="a"  target="_blank" href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item.usersname)}">${myel:jieMiUsername(item.usersname)}</a></td> 
	    <td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<td><fmt:formatNumber value="${item.leftMoney * 0.01}" pattern="#,##0.##"/></td>
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
	
		var url="${pageContext.request.contextPath}/Product/Admin/usersPurse!loadUsersCoinPurse.action?username="+$("#usersname").val();

		window.location.href=url;
	}
</script>
</body>
</html>