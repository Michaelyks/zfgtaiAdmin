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

<title>实际提现率</title>
</head>
<body>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
			<h3 style="text-align: center;">实际提现率</h3><br>
		<div id="div_condition" style="text-align: center;" >
	
		<span>时间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
		<input type="button" value="search" id="frm" onclick="Byname()">&nbsp;&nbsp;
		</div>
		<table style="width:1200px;margin-top: 20px;width: 100%;" >
		<tr>
		<td>序号</td>
		<td>日期</td>
		<td>充值金额(元)</td>
		<td>提现金额(元)</td>
		<td>提现率(百分比)</td>
		</tr>		
		<c:if test="${pageUtil.list ne '[]' &&  pageUtil.list ne '' && pageUtil.list ne null}">
		<tr>
		<td></td>
		<td>合计</td>
		<td><fmt:formatNumber value="${czTotalAmount* 0.01}" pattern="#,##0.##"/></td>
		<td><fmt:formatNumber value="${txTotalAmount* 0.01}" pattern="#,##0.##"/></td>
		<td>
				   
		   
		    <c:choose> 
		   <c:when test="${czTotalAmount eq '0'}">100%</c:when>
		 <c:otherwise>
<fmt:formatNumber value="${txTotalAmount/czTotalAmount*100}" pattern="#,##0.##"/>%
</c:otherwise>
</c:choose>
		
		</td>
		</tr>
		</c:if>
		<c:forEach items="${pageUtil.list}" var="item" varStatus="i">
		<tr>
		<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
		
	    <td>${item.date}</td>
		 <td><fmt:formatNumber value="${item.czMoney* 0.01}" pattern="#,##0.##"/></td>
		  <td><fmt:formatNumber value="${item.txMoney* 0.01}" pattern="#,##0.##"/></td>
		 
		   <td>
		   
		   
		    <c:choose> 
		   <c:when test="${item.czMoney eq '0'}">100%</c:when>
		 <c:otherwise>
<fmt:formatNumber value="${item.txMoney / item.czMoney *100}" pattern="#,##0.##"/> %
</c:otherwise>
</c:choose>
		   </td>
		 
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
	
		var url="${pageContext.request.contextPath}/Product/Admin/tXProcedures!loadExtractionrate.action?insertTime="+$('#insertTime').val();

		window.location.href=url;
	}
</script>
<script type="text/javascript">
	var k4 = new Kalendae.Input("insertTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
</script>
</body>
</html>	