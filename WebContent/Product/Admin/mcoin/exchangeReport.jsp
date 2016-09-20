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
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
<style type="text/css">
.a {
  color: blue;
  text-decoration: underline;
}
</style>
<script type="text/javascript">

function modifyExchange(mCoinPayId){
	if(confirm('确定发送短信？')){
		var id="modify_"+mCoinPayId;
		var url="${pageContext.request.contextPath}/Product/Admin/mExchangeReport!modifyMCoinPay.action?&mCoinPayId="+mCoinPayId;
		$.post(url,null,function(data){
			 if("ok"==data.status){
				 alert("短信发送成功");				 
				 $("#"+id).parents("td").html("已发送");
			 }else{			 
				 	alert("发送短信失败");
				 	return false;
			 }
		 }); 
	}
}
</script>
<title>商品兑换报表</title>
</head>
<body>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
	<h3 style="text-align: center;">商品兑换报表</h3><br>
		<div id="div_condition" style="text-align: center;" >
		<label>用户名:<input type="text" name="username" id="username" value="${username}" maxlength="11">
		<span>兑换日期:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
		<input type="button" value="search" id="frm" onclick="Byname()"></label>&nbsp;&nbsp;
		<input type="button" value="导出报表" onclick="ireportDo()">
		</div>
		
		<table style="width:1200px;margin-top: 20px;width: 100%;" >
		<tr>
		<td>序号</td>
		<td>兑换日期</td>
		<td>商品名称</td>
		<td>Vip等级</td>
		<td>单价(积分)</td>
		<td>兑换用户</td>
		<td>兑换数量</td>
		<td>价格(积分)</td>
		<td>收货地址</td>
		<td>联系人</td>
	    <td>短信发送情况</td> 
		
		</tr>
		<c:forEach items="${pageUtil.list}" var="item" varStatus="i">
		<tr>
		<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
	    <td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	    <td>${item.title}</td>
	    <td>${item.level}</td>
	    <td>${item.price}</td>
	    <td><a class="a"  target="_blank" href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item.username)}">${myel:jieMiUsername(item.username)}</a></td> 
	    <td>${item.copies}</td>
	    <td>${item.copies*item.price}</td>
	    <td>${item.address}<br/>${item.address_detail}</td>
	    <td>${item.contractName}</td>
  
	    <td><c:if test="${item.msgStatus eq '0' && item.type eq '3' }">
				    <a id="modify_${item.mCoinPayId}" class="a" href="javascript:modifyExchange('${item.mCoinPayId}');">未发送</a>
				</c:if>
				<c:if test="${item.msgStatus eq '1' && item.type eq '3'}">
				     已发送
				 </c:if>
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
	});

	function Byname(){
		var url="${pageContext.request.contextPath}/Product/Admin/mExchangeReport!loadMExchangeReport.action?username="+$("#username").val()+"&insertTime="+$('#insertTime').val();
     	window.location.href=url;
	}
	
	function ireportDo(){
 		var url="${pageContext.request.contextPath}/Product/Admin/mExchangeReport!iportFXTable.action?username="+$("#username").val()+"&insertTime="+$('#insertTime').val();
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