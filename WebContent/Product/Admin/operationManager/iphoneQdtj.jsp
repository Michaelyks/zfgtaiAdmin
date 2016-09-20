<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IOS渠道统计汇总表</title>
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
	.main {position:relative;}
.fixtop { position:fixed;width:1500px; line-height: 30px;height:30px;z0-index:99;background:#c1c1c1;display:none;left:50%;margin-left:-750px;top:0px; overflow: hidden;}
.ct-table{border:1px solid #ccc;border-collapse:collapse;border-spacing:0;}
.ct-table thead { background: #c1c1c1;}
.ct-table thead.fix {position:fixed;left:50%;top:0px;margin-left:-750px;width:1500px;}
.ct-table tbody {margin-top: 30px}
.ct-table tbody.mt { margin-top:30px; }
.ct-table tr td { line-height: 30px; }
.ct-table tbody>tr>td:nth-child(even) { background: #e2e2e2; }
.ct-table tbody>tr>td:nth-child(odd) { background: #f9f9f9; }
</style>	
<script type="text/javascript">
/* function queryProduct(){
	var insertTime=$("#insertTime").val();
	var registPlatform=$("#registPlatform option:selected").val();
	var url = "${pageContext.request.contextPath}/Product/Admin/activity!loadQdtj.action?insertTime="+insertTime;
	window.location.href=url;
}
$(function(){
 	var hTop=$("#tHead").offset().top-30; 	
 	$("#fixtop").append($("#tHead").clone());
 	$(window).scroll(function(){
 		var scrollTop=$(window).scrollTop();
 		 if(scrollTop > hTop){
 		 	$("#fixtop").show();
 		 }else {
 		 	$("#fixtop").hide();
 		 }
 	})
 }) */
</script>
<script type="text/javascript">
/* function ireportDo(){
		var list='${table}';
		if(list!='1'){
			alert("无数据");
			return false;
		}
		var insertTime=$("#insertTime").val();
		var registPlatform=$("#registPlatform option:selected").val();
 		var url='${pageContext.request.contextPath}/Product/Admin/activity!iportQdtjTable.action';	
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

} */
/* $(function(){
	$("#registPlatform option[value='${registPlatform}']").attr("selected",true);
	$("#insertTime").keydown(function(event){
		if(event.keyCode==13){
			$("#frm").submit();
		}
	});
 }); */
</script> 
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>IOS渠道统计汇总表</h3>
<!--	<form id="frm" action="${pageContext.request.contextPath}/Product/Admin/activity!loadQdtj.action">
	<span>查询期间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
	<input type="submit" value="查询">
 	<input type="button" value="导出报表" onclick="ireportDo()">
	</form> -->
	<div id="fixtop" class="fixtop"></div>
	<table  id="tHead" class="ct-table" border="1" cellspacing="0" cellpadding="0" style="text-align: center;">
		<thead>
		<tr>
			<td width="200px;">序号</td>
			<td width="200px;">渠道编号</td>
			<td width="200px;">渠道名称</td>
			<td width="200px;">激活次数</td>
			<td width="200px;">注册人数</td>
			<td width="200px;">绑定人数</td>
			<td width="200px;">渠道转化率(%)</td>
			<td width="100px;">投资人数</td>
			<td width="200px;">首投人数</td>
			<td width="200px;">首投金额</td>
			<td width="200px;">复投人数</td>
			<td width="200px;">投资金额(元)</td>
			<td width="200px;">重复投资率(%)</td>
			<td width="200px;">充值次数</td>
			<td width="200px;">充值金额(元)</td>
		</tr>
	</thead>
		<tbody>
		<tr>
			<td>合计</td>
				<td></td>
				<td></td>
				<td>${tj[0].activityCount}</td>
				<td>${tj[0].regCount}</td>
				<td>${tj[0].bindCount}</td>
				<td><fmt:formatNumber value="${tj[0].qdzhl*100}" pattern="0.##"/></td>
				<td>${tj[0].tzrs}</td>
				<td>${tj[0].strs}</td>
				<td><fmt:formatNumber value="${tj[0].stje*0.01}" pattern="#,##0.##"/></td>
				<td>${tj[0].ftrs}</td>
				<td><fmt:formatNumber value="${tj[0].tzje*0.01}" pattern="#,##0.##"/></td>
				<td><fmt:formatNumber value="${tj[0].cftzl}" pattern="#,##0.##"/></td>
				<td>${tj[0].czcount}</td>
				<td><fmt:formatNumber value="${tj[0].czje*0.01}" pattern="#,##0.##"/></td>	
		</tr> 
		<c:forEach items="${list}" var="item"  varStatus="i">
			<tr>
				<td>${i.count}</td>
 				<td> <a class="a" target="_blank" href="${pageContext.request.contextPath}/Product/Admin/iphoneActivity!loadIphoneQdtjDetails.action?registChannel=${item.channel}">${item.channel}</a></td>
				<td> <a class="a" target="_blank" href="${pageContext.request.contextPath}/Product/Admin/iphoneActivity!loadIphoneQdtjDetails.action?registChannel=${item.channel}">  <c:if test="${item.channel eq 'aisi'}">爱思助手</c:if> </a></td>
				<td>${item.activityCount}</td>
				<td>${item.regCount}</td>
				<td>${item.bindCount}</td>
				<td><fmt:formatNumber value="${item.qdzhl*100}" pattern="#,##0.##"/></td>
				<td>${item.tzrs}</td>
				<td>${item.strs}</td>
				<td><fmt:formatNumber value="${item.stje*0.01}" pattern="#,##0.##"/></td>
				<td>${item.ftrs}</td>
				<td><fmt:formatNumber value="${item.tzje*0.01}" pattern="#,##0.##"/></td>
				<td><fmt:formatNumber value="${item.cftzl}" pattern="#,##0.##"/></td>
				<td>${item.czcount}</td>
				<td><fmt:formatNumber value="${item.czje*0.01}" pattern="#,##0.##"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<c:choose>
			<c:when test="${list ne '[]' &&  list ne '' && list ne null}"><jsp:include page="/Product/page.jsp" /></c:when>
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