<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>积分记录表</title>
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
	var url="${pageContext.request.contextPath}/Product/Admin/mCoinDayDetail!exportCoinDayDetail.action?insertTime="+$("#insertTime").val();
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
</script>

</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">

	<h3>积分报表</h3><br/>
        <div id="div_condition" style="text-align: center;" >
		<span>时间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
		<input type="button" value="search" id="frm" onclick="Byname()"></label>&nbsp;&nbsp;
		<input type="button" value="导出报表" onclick="ireportDo()">&nbsp;&nbsp;
		</div>
	<table border="1" cellspacing="0" cellpadding="0" style="text-align: center;">
		<tr>
		<td width="100px;">序号</td>
			<td width="200px;">日期</td>
			<td width="200px;">发放积分</td>
			<td width="100px;">扣除积分</td>
			<td width="200px;">剩余积分</td>
		</tr>
 		<c:forEach items="${list}" var="item" varStatus="i">
			<tr>
				<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
					<td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd"/></td>
					<td>
					<a class="a"  target="_blank" href="${pageContext.request.contextPath}/Product/Admin/meowIncome!showMeowIncome.action?insertTime=<fmt:formatDate value="${item.insertTime}" pattern="MM/dd/yyyy"/>">${item.coinAdd}</a></td> 
					<td>
					<a class="a"  target="_blank" href="${pageContext.request.contextPath}/Product/Admin/meowPay!showMeowPay.action?insertTime=<fmt:formatDate value="${item.insertTime}" pattern="MM/dd/yyyy"/>">${item.coinPay}</a> </td>
				<td>${item.leftCoin}</td>
			</tr>
		</c:forEach> 
	</table>
	<c:choose>
			<c:when test="${pageUtil.list ne '[]' &&  pageUtil.list ne '' && pageUtil.list ne null}"><jsp:include page="/Product/page.jsp" /></c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;">
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
$(function(){
	$("body").keydown(function(e){
		var ev = document.all ? window.event : e;
		 if(ev.keyCode==13) 
		 {
			 $("#frm").click();
		 }

	    });
	$("#setCleanMcoin").click(function(){
		if($("#cleanMcoin").attr('disabled'))
		$("#cleanMcoin").attr('disabled',false);
		else{
			$("#cleanMcoin").attr('disabled',true);
			
		}
		
	});
});
	function Byname(){
	
		var url="${pageContext.request.contextPath}/Product/Admin/mCoinDayDetail!loadMCoinDayDetail.action?insertTime="+$("#insertTime").val();

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