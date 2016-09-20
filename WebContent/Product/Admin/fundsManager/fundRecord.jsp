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
<title>资金流水记录</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	
</script>
<style type="text/css">
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
</head>
<body>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
		<h1 style="text-align: center;">资金流水记录</h1>
		<div id="div_condition" style="text-align: center;" >
		<label>用户名:<input type="text" name="name" id="name" value="${name}" maxlength="11">
		
		<input type="button" value="search" id="frm" onclick="Byname()"></label>&nbsp;&nbsp;
		<label><input type="radio" value="all" name="status" checked="checked">全部</label>&nbsp;&nbsp;
		
		</div>
		
		<table  style="width:100%;margin-top: 20px;text-align: center;">
		<tr>
		<td>序号</td>
		<td>交易金额(元)</td>
		<td>交易类型</td>
		<td>记录产生时间</td>
		<td>账户余额(元)</td>
		<td>备注</td>
		<td>操作</td>
		</tr>
		<c:forEach items="${fundRecordList}" var="list" varStatus="i">
		<tr>
		<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
		 <td><fmt:formatNumber value="${list.money * 0.01}" pattern="#,##0.##"/></td>
		<td>
			${list.jylx}
		</td>
		<td><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td><fmt:formatNumber value="${list.usersCost * 0.01}" pattern="#,##0.##"/></td>
		<td>${list.note}</td>
<%-- 		<td>
			<a class="a" href="javascript:queryTx('${myel:jieMiUsername(list.users.username)}','${list.requestId}');">查看记录</a>
		</td> --%>
		</tr>
		</c:forEach>
		</table>
		<c:choose>
			<c:when test="${fundRecordList ne '[]' &&  fundRecordList ne '' && fundRecordList ne null}"><jsp:include page="/Product/page.jsp" /></c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
			  		<img src="../images/no_record.png" />
			  	</div>
			</c:otherwise>
		</c:choose>
	</div>
</div> 
<script type="text/javascript">
$("#div_condition input[name='status']").click(function(){
	window.location.href="${pageContext.request.contextPath}/Product/Admin/checkTxsq!loadTxsq.action?name="+$("#name").val()+"&status="+$('input[name="status"]:checked').val()+"&insertTime="+$('#insertTime').val();
});

	$("#div_condition input[name='status']").each(function(){
		if($(this).attr("value")=="${status}" || "${status}"==""){
			$(this).attr("checked",true);
		}
	});
	function Byname(){
		window.location.href="${pageContext.request.contextPath}/Product/Admin/fundRecord!loadFund.action?name="+$("#name").val();
	}
	//为input框绑定回车事件，当用户在文本框中输入搜索关键字时，按回车键，即可触发search():

	//回车绑定
	$("#name").keydown(function(event){
				if(event.keyCode==13){
					Byname();
				}
			});
</script>
</body>
</html>	