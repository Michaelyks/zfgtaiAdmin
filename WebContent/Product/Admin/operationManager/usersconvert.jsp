<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户转换数据报表</title>
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
	var url = "${pageContext.request.contextPath}/Product/Admin/usersConvert!loadUsersConvert.action?insertTime="+insertTime+"&registPlatform="+registPlatform;
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
 		var url='${pageContext.request.contextPath}/Product/Admin/usersConvert!iportTable.action?';	
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
	$("#insertTime,#registPlatform").keydown(function(event){
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
	<h3>用户转换数据报表</h3>
	<form id="frm" action="${pageContext.request.contextPath}/Product/Admin/usersConvert!loadUsersConvert.action">
	<span>查询期间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
	<select id="registPlatform" name="registPlatform">
		<option value="" selected="selected">所有</option>
		<option value="2">IOS</option>
		<option value="1">Android</option>
		<option value="3">web</option>
	</select>
	<input type="submit" value="查询">
	<input type="button" value="导出报表" onclick="ireportDo()">
	<input type="button" value="更新当天数据" id="startCurrEveryDayStatsOperate">	
	</form>
	<table border="1" cellspacing="0" cellpadding="0" style="text-align: center;">
		<tr>
			<td width="200px;">日期</td>
			<td width="200px;">注册人数</td>
			<td width="200px;">绑定人数</td>
			<td width="100px;">投资人数</td>
			<td width="200px;">投资金额(元)</td>
			<td width="200px;">人均投资金额(元)</td>
			<td width="200px;">投资次数</td>
			<td width="200px;">复投次数</td>
			<td width="200px;">注册投资人数</td>
			<td width="200px;">注册投资金额(元)</td>
			<td width="200px;">转换率(%)</td>
			<td width="200px;">首投人数</td>
			<td width="200px;">复投人数</td>
			<td width="200px;">复投率(%)</td>
		</tr>
		<tr>
				<td>合计</td>
				<td>${tj.reuserscount}</td>
				<td>${tj.bindcount}</td>
				<td>${tj.insusercount}</td>
				<td><fmt:formatNumber value="${tj.allcopies/100}" pattern="0.##"/></td>
				<td> <fmt:formatNumber value="${tj.rjtz/100}" pattern="0.##"/></td>
				<td>${tj.inscount}</td>
				<td>${tj.ftinscs}</td>
				<td>${tj.reginscount}</td>
				<td><fmt:formatNumber value="${tj.regcopies/100}" pattern="0.##"/></td>
				<td><fmt:formatNumber value="${tj.zhl}" pattern="0.##"/></td>
				<td>${tj.stinsrs}</td>
				<td>${tj.ftinsrs}</td>
				<td><fmt:formatNumber value="${tj.ftl}" pattern="0.##"/></td>
		</tr>
		<c:forEach items="${pageUtil.list}" var="item">
			<tr>
				<td>${item.date}</td>
				<td>${item.reuserscount}</td>
				<td>${item.bindcount}</td>
				<td>${item.insusercount}</td>
				<td>${item.allcopies}</td>
				<td>${item.rjtz}</td>
				<td>${item.inscount}</td>
				<td>${item.ftinscs}</td>
				<td>${item.reginscount}</td>
				<td>${item.regcopies}</td>
				<td><fmt:formatNumber value="${item.zhl}" pattern="0.##"/></td>
				<td>${item.stinsrs}</td>
				<td>${item.ftinsrs}</td>
				<td><fmt:formatNumber value="${item.ftl}" pattern="0.##"/></td>
			</tr>
		</c:forEach>
	</table>
		<c:choose>
			<c:when test="${pageUtil.list ne '[]' &&  pageUtil.list ne '' && pageUtil.list ne null}">
			<jsp:include page="/Product/page.jsp" />
			</c:when>
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