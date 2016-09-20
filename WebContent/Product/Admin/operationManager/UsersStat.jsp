<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<title>注册统计</title>
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
</style>
<script type="text/javascript">
$(function(){
	$("#insertTime").keydown(function(event){
		if(event.keyCode==13){
			queryProduct();
		}
	});
});




function queryProduct(){
	var insertTime=$("#insertTime").val();
	var url = "${pageContext.request.contextPath}/Product/Admin/userStat!loadUsersStat.action?insertTime="+insertTime;
	window.location.href=url;
}
</script>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>注册统计</h3>
	<span>注册时间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
	<a class="sereach" href="javascript:queryProduct();" id="sereach">查询</a>
	<table border="0.5" cellspacing="0" cellpadding="0">
		<tbody>
				<tr>
					<td width="50px" style="text-align: center;">序号</td>
					<td width="200px" style="text-align: center;">注册平台</td>
					<td width="100px" style="text-align: center;">注册人数</td>
				</tr>
			<c:forEach items="${list}"  var="item" varStatus="i">
				<tr>
					<td style="text-align: center;">${i.index+1}</td>
					<td style="text-align: center;">
						<c:if test="${item.registPlatorm == 0}">web端注册</c:if>
						<c:if test="${item.registPlatorm == 1}">Android移动端</c:if>
						<c:if test="${item.registPlatorm == 2}">IOS移动端</c:if>
						<c:if test="${item.registPlatorm == 3}">微信注册</c:if>
					</td>
					<td style="text-align: center;">${item.registPlatormCount}</td>
				</tr>
			</c:forEach>
			<tr>
					<td style="text-align: center;" colspan="2">
						总计
					</td>
					<td style="text-align: center;">${registAllCount}</td>
				</tr>
		</tbody>
	</table>
</div>
</div>
</body>
<script type="text/javascript">
	var k4 = new Kalendae.Input("insertTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
</script>
</html>