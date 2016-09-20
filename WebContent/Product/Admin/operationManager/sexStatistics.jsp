<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>性别统计</title>
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
<script type="text/javascript">
function queryProduct(){
	var insertTime=$("#insertTime").val();
	var registPlatform=$("#registPlatform option:selected").val();
	var url = "${pageContext.request.contextPath}/Product/Admin/activity!loadQdtj.action?insertTime="+insertTime;
	window.location.href=url;
}
</script>
<script type="text/javascript">
$(function(){
	$("#registPlatform option[value='${registPlatform}']").attr("selected",true);
 });
</script> 
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>性别统计</h3>
	<form id="frm" action="">
	</form>
	<table border="1" cellspacing="0" cellpadding="0" style="text-align: center;">
		<tr>
			<td width="200px;">性别</td>
			<td width="200px;">人数</td>
			<td width="200px;">人数比例</td>
			<td width="200px;">投资次数</td>
			<td width="200px;">投资总额</td>
		</tr>
		<c:forEach items="${list}" var="item">
		<tr>
		     <td>${item.sexChina}</td>
		     <td>${item.rsCount}</td>
		     <td><fmt:formatNumber value="${item.rate*100}" pattern="#.##" />%</td>
		     <td>${item.csCount}</td>
		     <td><fmt:formatNumber value="${item.jeCount*0.01}" pattern="#.##" /></td>
		</tr>
		</c:forEach>
	</table>
	</div>
</div>
<div id="psi_load" style="display:none;">
<img width="18" src="${pageContext.request.contextPath}/Product/images/progress.gif"><span  id="psi_tip">正在导出,请稍等...</span>
</div>	
<script type="text/javascript">
	var k4 = new Kalendae.Input("insertTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
	 $(function(){
		$("#isbindbank option[value='${isbindbank}']").attr("selected",true);
	 });
</script>
</body>
</html>