<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
</style>
<script type="text/javascript">
function queryProduct(){
	var payTime=$("#payTime").val();
	var status=$("#status").val();
	var url = "${pageContext.request.contextPath}/Product/Admin/platfromInversors!loadPlatfromInversors.action?payTime="+payTime+"&status="+status;
	window.location.href=url;
}
$(function(){
	 var status='${status}';
		// alert(tj1);
		// alert(annualEarnings);
		 if(status!=''){
		 	$("#status").find("option[value='"+status+"']").attr("selected",true);
		 }
	$("#payTime,#status").keydown(function(event){
		if(event.keyCode==13){
			queryProduct(); 
		}
	});
});
</script>
<title>平台投资情况</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>平台投资情况</h3>
		<span class="select1" >投资时间:</span> <input id="payTime" name="payTime" type="text" value="${payTime}">
		投资状态：
		<select class="select1" id="status" name="status">
			<option value="">全部</option>
			<option value="1">已付款</option>
			<option value="3">已结算</option>
		</select>
		<a class="sereach" href="javascript:queryProduct();" id="sereach">查询</a>
	<table border="0.5">
				<tbody>
					<tr>
						<td width="50px" style="text-align: center;">序号</td>
						<td width="200px" style="text-align: center;">投资平台</td>
						<td width="100px" style="text-align: center;">投资金额</td>
					</tr>
				<c:forEach items="${list}"  var="item" varStatus="i">
					<tr>
						<td style="text-align: center;">${i.index+1}</td>
						<td style="text-align: center;">${item.registPlatform}</td>
						<td style="text-align: center;"><fmt:formatNumber value="${item.platformInvest}" pattern="#,##0.#" /> </td>
					</tr>
				</c:forEach>
				</tbody>
				<tr>
					<td colspan="2" style="text-align: left;">总计</td>
					<td ><fmt:formatNumber value="${collectMoney}" pattern="#,##0.#" /></td>
				</tr>
			</table>
	</div>
</div>
</body>
<script type="text/javascript">
	var k4 = new Kalendae.Input("payTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
</script>
</html>