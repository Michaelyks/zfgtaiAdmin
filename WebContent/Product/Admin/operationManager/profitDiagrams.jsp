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

</head>
<body style="background: #fff;">
<div class="center">	
 		
	<div >
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	</div>
	<div class="main" align="center" style="margin-top:  50px;">
	<h3 style="text-align: center;">用户零钱包收益曲线图</h3><br>
	<span>用户ID:</span> <input id="usersIds" name="usersIds" type="text"   style="border:1px solid gray"/>
	<span>用户名:</span> <input type="text" name="username" id="username"  maxlength="11" style="border:1px solid gray"/>
	<input type="button" value="查询" id="frm" onclick="Byname()" style="background-color: rgb(235, 235, 228);border:1px solid gray">&nbsp;&nbsp;
	<jsp:include page="/wap/product/syqxt.jsp"/>
	</div>
	<% 
	String id = request.getParameter("usersId");
    %>
    <div id="div_condition" style="text-align: center;" >
   <input type="hidden" id="hidUsersId" value="<%=id %>" />
<span>时间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}"  style="border:1px solid gray">
	<input type="button" value="查询" id="frm" onclick="seleProfit()" style="background-color: rgb(235, 235, 228);border:1px solid gray">&nbsp;&nbsp;

<span>用户Id: ${usersId}</span><span style="padding-left: 20px">零钱包剩余金额:<fmt:formatNumber value="${quertProfit}" pattern="#,##0.##"/></span>
	</div>
	
	<div class="main" align="center">
<table style="width:1200px;margin-top: 20px;width: 100%;text-align: center;" border="1" cellspacing="0" cellpadding="0" >
		<tr>
		<td>序号</td>
		<td>零钱包收益</td>
		<td>收益时间</td>
		</tr>
		<c:forEach items="${pageUtil.list}" var="item" varStatus="i">
		<tr>
		<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
		<td><fmt:formatNumber value="${item.payInterest}" pattern="#,##0.##" /></td>
		<td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd" /></td>
		</tr>
		</c:forEach>
		</table>
	<c:choose>
			<c:when test="${pageUtil.list ne '[]' &&  pageUtil.list ne '' && pageUtil.list ne null}">
			<jsp:include page="/Product/page.jsp" /></c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
			  			<img src="images/no_record.png">
			  	</div>
			</c:otherwise>
		</c:choose>
		</div> </div> 
			<script type="text/javascript">
$(function(){
	$("#username").val(getCookie("username"));
	$("#usersIds").val(getCookie("userId"));
	delCookie("username");
	delCookie("userId");
	$("body").keydown(function(e){
	var ev = document.all ? window.event : e;
	 if(ev.keyCode==13) 
	 {
		 $("#frm").click();
	 }
	 });
	
		
function delCookie(name)
{
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(name);
	if(cval!=null)
		document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
	
	
	function getCookie(name)
	{
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
		else
			return null;
	}
});



function setCookie(name,value)
{
var Days = 30;
var exp = new Date();
exp.setTime(exp.getTime() + Days*24*60*60*1000);
document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

function Byname()
{
   var username = $("#username").val();
   var id = $("#usersIds").val();
   setCookie("username",username);
   setCookie("userId",id);
     if(username!="" ){
	 	$.ajax({
				type : "post",
				async : false,
				url : "${pageContext.request.contextPath}/Product/Admin/zeroPurse!zeroPurseId.action?username="+username+"&usersId="+$("#usersIds").val(),
				success : function(data) {
	
					if(data.status=="ok"){
						var url = "${pageContext.request.contextPath}/Product/statistics!queryAllUserProfit.action?usersId="+data.date+"";
					 	location.href =url;
					}
					else{
						 $("#qxt").html("<img src='../../images/no_record.png'>");
					}
				
					},
					error: function(data){
						alert("系统繁忙，请稍后重试");
					}
			});
  }
  	else if(id!=""){
  		var url = "${pageContext.request.contextPath}/Product/statistics!queryAllUserProfit.action?usersId="+id+"";
		location.href =url;
  		}
    
}


function seleProfit(){

	var url="${pageContext.request.contextPath}/Product/statistics!queryAllUserProfit.action?usersId="+$("#hidUsersId").val()+"&insertTime="+$('#insertTime').val();
	window.location.href=url;
}

var k4 = new Kalendae.Input("insertTime", {
	attachTo:document.body,
	months:2,//多少个月显示出来,即看到多少个日历
	mode:'range'
	/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
});
</script>
</body>
</html>	