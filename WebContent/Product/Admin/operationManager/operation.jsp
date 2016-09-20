<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>运营数据表</title>
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
function ireportDo(){
		var list='${table}';
		if(list!='1'){
			alert("无数据");
			return false;
		}
 		var url='${pageContext.request.contextPath}/Product/Admin/usersConvert!iportOperationTable.action?';	
    	var my= art.dialog({
    	    title: '提示',
    	    content:document.getElementById("psi_load"),
    	    height: 60,
    	    lock: true,
    	    cancel: false
    	});  
    	$.post(url,$("#frm").serialize(),function(data){			
    		my.close();
    			var ssss="导出成功&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+data+"' style='color:red;'>点击下载</a>";
           		art.dialog({
       		    title: '提示',
       		    content:ssss,
       		    height: 60,
       		    lock: true,
       		    ok:function(){
       		    	//mysss.close();
       		    }
       		});   
    	});

}
$(function(){
	$("#registPlatform option[value='${registPlatform}']").attr("selected",true);
	$("#insertTime").keydown(function(event){
		if(event.keyCode==13){
			$("#frm").submit(); 
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
 
 
</script> 
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>运营数据表</h3>
	<form id="frm" action="${pageContext.request.contextPath}/Product/Admin/usersConvert!loadOperation.action">
	<span>查询期间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
	<input type="submit" value="查询">
	<input type="button" value="导出报表" onclick="ireportDo()">
	<input type="button" value="更新当天数据" id="startCurrEveryDayStatsOperate">	
	</form>
	<table border="1" cellspacing="0" cellpadding="0" style="text-align: center;">
		<tr>
			<td width="200px;">日期</td>
			<td width="200px;">激活用户</td>
			<td width="200px;">投资次数</td>
			<td width="200px;">首投次数</td>
			<td width="200px;">复投次数</td>
			<td width="200px;">新增二次投资次数</td>
			<td width="200px;">投资金额(元)</td>
			<td width="200px;">还款金额</td>
			<td width="200px;">支付利息</td>
			<td width="100px;">投资人数</td>
			<td width="200px;">首投人数</td>
			<td width="200px;">复投人数</td>
			<td width="200px;">新增二次投资人数</td>
			<td width="200px;">人均投资金额(元)</td>
			<td width="200px;">首投总额(元)</td>
			<td width="200px;">新增二次投资率(%)</td>
			<td width="200px;">复投率(%)</td>
		</tr>
		<tr>
				<td>合计</td>
				<td>${totalAllSum.activateUserSum}</td>
				<td>${totalAllSum.investCount}</td>
				<td>${totalAllSum.firstInvestCount}</td>
				<td>${totalAllSum.reInvestCount}</td>
				<td>${totalAllSum.newTwoInvestCount}</td>
				<td><fmt:formatNumber value="${totalAllSum.investCentSum / 100}" pattern="#,###,##0.00"/></td>
				<td><fmt:formatNumber value="${totalAllSum.repayPrincipalCentSum / 100}" pattern="#,###,##0.00"/></td>
				<td><fmt:formatNumber value="${totalAllSum.repayInterestCentSum / 100}" pattern="#,###,##0.00"/></td>
				<td>${totalAllSum.investUserSum}</td>
				<td>${totalAllSum.firstInvestUserSum}</td>
				<td>${totalAllSum.reInvestUserSum}</td>
				<td>${totalAllSum.newTwoInvestUserSum}</td>
				<td><fmt:formatNumber value="${totalAllSum.avgInvestCentSum / 100}" pattern="#,###,##0.00"/></td>
				<td><fmt:formatNumber value="${totalAllSum.firstInvestCentSum / 100}" pattern="#,###,##0.00"/></td>
				<td><fmt:formatNumber value="${totalAllSum.newTwoInvestRate}" pattern="#,###,##0.00"/></td>
				<td><fmt:formatNumber value="${totalAllSum.reInvestRate}" pattern="#,###,##0.00"/></td>
		</tr>
		<c:forEach items="${pageUtil.list}" var="item">
			<tr>
				<td>${item.dates}</td>
				<td>${item.activateUserSum}</td>
				<td>${item.investCount}</td>
				<td>${item.firstInvestCount}</td>
				<td>${item.reInvestCount}</td>
				<td>${item.newTwoInvestCount}</td>
				<td><fmt:formatNumber value="${item.investCentSum / 100}" pattern="#,###,##0.00"/></td>
				<td><fmt:formatNumber value="${item.repayPrincipalCentSum / 100}" pattern="#,###,##0.00"/></td>
				<td><fmt:formatNumber value="${item.repayInterestCentSum / 100}" pattern="#,###,##0.00"/></td>
				<td>${item.investUserSum}</td>
				<td>${item.firstInvestUserSum}</td>
				<td>${item.reInvestUserSum}</td>
				<td>${item.newTwoInvestUserSum}</td>
				<td><fmt:formatNumber value="${item.avgInvestCentSum / 100}" pattern="#,###,##0.00"/></td>
				<td><fmt:formatNumber value="${item.firstInvestCentSum / 100}" pattern="#,###,##0.00"/></td>
				<td><fmt:formatNumber value="${item.newTwoInvestRate}" pattern="#,###,##0.00"/></td>
				<td><fmt:formatNumber value="${item.reInvestRate}" pattern="#,###,##0.00"/></td>
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