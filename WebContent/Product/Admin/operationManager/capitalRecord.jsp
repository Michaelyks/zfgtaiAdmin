<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册投资充值提现</title>
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
	.fixedHead { 
position: relative; 
top:expression(this.offsetParent.scrollTop-2); 
} 
</style>	
<script type="text/javascript">
$(function(){
	$("#insertTime").keydown(function(event){
		if(event.keyCode==13){
			queryProduct();
		}
	});
	
	$("#startCurrEveryDayStatsOperate").click(function(){
		var url="${pageContext.request.contextPath}/Product/Admin/startThread!startCurrEveryDayStatsOperate.action";
		$.post(url,null,function(data){
			alert(data.json);//结果!
			window.location.reload()
		}); 
	});
});

function queryProduct(){
	var insertTime=$("#insertTime").val();
	var url = "${pageContext.request.contextPath}/Product/Admin/recharge!CapitalRecord.action?insertTime="+insertTime;
	window.location.href=url;
}
</script>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>注册投资充值提现</h3>
	<span>查询期间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
	<a class="sereach" href="javascript:queryProduct();" id="sereach">查询</a>
	<input type="button" value="更新当天数据" id="startCurrEveryDayStatsOperate">	
	<table border="1" cellspacing="0" cellpadding="0" style="text-align: center;">
		<tr class="fixedHead">
			<td width="200px;">日期</td>
			<td width="200px;">注册人数</td>
			<td width="200px;">投资人数</td>
			<td width="200px;">成交金额</td>
			<td width="200px;">充值金额</td>
			<td width="100px;">提现金额</td>
			<td width="100px;">成功提现金额</td>
		</tr>
		<tr>
				<td>合计</td>
				<td>
					<fmt:formatNumber value="${tj.regCount}" pattern="#,##0.##"/>
				</td>
				<td>
					<fmt:formatNumber value="${tj.ivsCount }" pattern="#,##0.##"/>
				</td>
				<td>
					<fmt:formatNumber value="${tj.ivsMoney * 0.01}" pattern="#,##0.##"/>
				</td>
				
				<td>
					<fmt:formatNumber value="${tj.czMoney * 0.01}" pattern="#,##0.##"/>
				</td>
				
				<td>
					<fmt:formatNumber value="${tj.txMoney * 0.01}" pattern="#,##0.##"/>
				</td>
				<td>
					<fmt:formatNumber value="${tj.ctxMoney * 0.01}" pattern="#,##0.##"/>
				</td>
		</tr>
		<c:forEach items="${pageUtil.list}" var="item">
			<tr>
				<td><fmt:formatDate value="${item.date}" pattern="yyyy-MM-dd"/></td>
				<td>
					<fmt:formatNumber value="${item.regCount}" pattern="#,##0.##"/>
				</td>
				<td>
					<fmt:formatNumber value="${item.ivsCount }" pattern="#,##0.##"/>
				</td>
				<td>
					<fmt:formatNumber value="${item.ivsMoney *0.01}" pattern="#,##0.##"/>
				</td>
				
				<td>
					<fmt:formatNumber value="${item.czMoney * 0.01}" pattern="#,##0.##"/>
				</td>
				
				<td>
					<fmt:formatNumber value="${item.txMoney * 0.01}" pattern="#,##0.##"/>
				</td>
				<td>
					<fmt:formatNumber value="${item.ctxMoney * 0.01}" pattern="#,##0.##"/>
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