<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<title>投资总金额排行</title>
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
<!-- <script type="text/javascript">
function queryProduct(){
	var insertTime=$("#insertTime").val();
	var url = "${pageContext.request.contextPath}/Product/Admin/userStat!loadUsersStat.action?insertTime="+insertTime;
	window.location.href=url;
}
</script> -->
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>投资总金额排行</h3>
	<!--<a class="sereach" href="${pageContext.request.contextPath}/Product/Admin/ranking!showInvestorsRank.action?">投资总金额排行</a>  -->
	<a class="sereach" href="${pageContext.request.contextPath}/Product/Admin/ranking!showCZRecordRank.action?">充值总金额排行</a>
	<table border="0.5" cellspacing="0" cellpadding="0">
		<tbody>
				<tr>
					<td width="50px" style="text-align: center;">序号</td>
					<td width="200px" style="text-align: center;">用户id</td>
					<td width="200px" style="text-align: center;">用户名</td>
					<td width="200px" style="text-align: center;">姓名</td>
					<td width="100px" style="text-align: center;">投资总金额（元）</td>
				</tr>
			<c:forEach items="${list}"  var="item" varStatus="i">
				<tr>
					<td style="text-align: center;">${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
					<td style="text-align: center;">${item.usersId}</td>
					<td style="text-align: center;">
					      <a class="a"  href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item.usersname)}">${myel:jieMiUsername(item.usersname)}</a></td>
					<td style="text-align: center;">${item.realname}</td>
					<td style="text-align: center;"><fmt:formatNumber value="${item.inmoney * 0.01}" pattern="#,##0.##"/></td>
				</tr>
			</c:forEach>

		</tbody>
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
</body>
</html>