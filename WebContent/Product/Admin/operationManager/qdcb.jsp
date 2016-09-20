<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Android渠道成本</title>
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
	var url = "${pageContext.request.contextPath}/Product/Admin/activity!loadQdcb.action?insertTime="+insertTime;
	window.location.href=url;
}
</script>
<script type="text/javascript">
function ireportDo(){
		var list='${table}';
		if(list!='1'){
			alert("无数据");
			return false;
		}
		var insertTime=$("#insertTime").val();
		var registPlatform=$("#registPlatform option:selected").val();
 		var url='${pageContext.request.contextPath}/Product/Admin/activity!iportQdcbTable.action';	
    	var my= art.dialog({
    	    title: '提示',
    	    content:document.getElementById("psi_load"),
    	    height: 60,
    	    lock: true,
    	    cancel: false
    	});  
    	$.post(url,$("#frm").serialize(),function(data){			
    		my.close();
    		data = '${pageContext.request.contextPath}'+data;
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
	<h3>Android渠道成本</h3>
	<form id="frm" action="${pageContext.request.contextPath}/Product/Admin/activity!loadQdcb.action">
	<span>查询期间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
	<input type="submit" value="查询">
	<input type="button" value="导出报表" onclick="ireportDo()">
	<input type="button" value="更新当天数据" id="startCurrEveryDayStatsOperate">	
	</form>
	<table border="1" cellspacing="0" cellpadding="0" style="text-align: center;">
		<tr>
			<td width="200px;">日期</td>
			<td width="200px;">注册人数</td>
			<td width="100px;">投资人数</td>
			<td width="200px;">投资金额</td>
			<td width="200px;">首投人数</td>
			<td width="200px;">首投金额</td>
		</tr>
			<tr>
				<td>合计</td>
				<td>${tj.regUsersCount}</td>
				<td>${tj.insUsersCount}</td>
				<td><fmt:formatNumber value="${tj.insMoney/100}" pattern="#,##0.##"/></td>
				<td>${tj.strs}</td>
				<td><fmt:formatNumber value="${tj.stje/100}" pattern="#,##0.##"/></td>
		</tr> 
 		<c:forEach items="${list}" var="item">
			<tr>
				<td><fmt:formatDate value="${item.date}" pattern="yyyy-MM-dd"/></td>
				<td>${item.regUserSum}</td>
				<td>${item.investUserSum}</td>
				<td><fmt:formatNumber value="${item.investCentSum/100}" pattern="#,##0.##"/></td>
				<td>${item.firstInvestUserSum}</td>
				<td><fmt:formatNumber value="${item.firstInvestCentSum/100}" pattern="#,##0.##"/></td>
			</tr>
		</c:forEach> 
	</table>
	
	<c:choose>
			<c:when test="${pageUtil.list ne '[]' &&  pageUtil.list ne '' && pageUtil.list ne null}">
			<jsp:include page="/Product/page.jsp" /> </c:when>
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