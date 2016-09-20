<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>零钱包 - 一周不可动金额报表</title>
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
		var insertTime=$("#insertTime").val();
		var username=$("#username").val();
 		var url="${pageContext.request.contextPath}/Product/Admin/coinPurseReport!importCoinPurseDetailReport.action?queryDate="+insertTime+"&mobileNum="+username;	
    	var my= art.dialog({
    	    title: '提示',
    	    content:document.getElementById("psi_load"),
    	    height: 60,
    	    lock: true,
    	    cancel: false
    	});  
    	$.post(url,$("#frm").serialize(),function(data){			
    		my.close();
    			var realPath = '${pageContext.request.contextPath}'+data;
    			var ssss="导出成功&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+realPath+"' style='color:red;'>点击下载</a>";
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
	$("#insertTime,#username").keydown(function(event){
		if(event.keyCode==13){
			$("#frm").submit();
		}
	});
 });
</script> 
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>零钱包 - 零钱包明细</h3>
	<form id="frm" action="${pageContext.request.contextPath}/Product/Admin/coinPurseReport!coinPurseDetailReport.action">
	<span>用户名:</span> <input id="username" name="username" type="text" value="${username}">
	<input type="hidden" id="insertTime" name="insertTime" type="text" value="${insertTime}">
	<input type="submit" value="查询">
	<input type="button" value="导出报表" onclick="ireportDo()">
	</form>
	<table border="1" cellspacing="0" cellpadding="0" style="text-align: center;">
		<tr>
			<td width="200px;">序号</td>
			<td width="200px;">用户名</td>
			<td width="200px;">转入金额</td>
			<td width="200px;">转入时间</td>
			<td width="200px;">转出金额</td>
			<td width="200px;">转出时间</td>
			<td width="200px;">付息金额</td>
			<td width="200px;">付息时间</td>
		</tr>
		<c:forEach items="${pageUtil.list}" var="item">
			<tr>
				<td>${item.id}</td>
				<td>${item.mobileNum}</td>
				<td>${item.inMoney}</td>
				<td>${item.inTime}</td>
				<td>${item.outMoney}</td>
				<td>${item.outTime}</td>
				<td>${item.shouyiMoney}</td>
				<td>${item.shouyiTime}</td>
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
</body>
</html>