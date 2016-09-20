<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog4.1.7/artDialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dropzone/dropzone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/dropzone/dropzone.css">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<style type="text/css">
	.rightText{
		text-align: right;
	}
	.leftText{
		text-align: left;
	}
	.divEdit {
	  width: 1200px;
	  overflow: auto;
	  min-height: 500px;
	  height: auto;
	  _height: 120px;
	  max-height: 160px;
	  cursor: text;
	  outline: none;
	  white-space: normal;
	  padding: 1px 2px 1px 2px;
	  font-family: SimSun,Verdana,sans-serif;
	  font-size: 12px;
	  line-height: 16px;
	  /*border: 1px solid black;*/
	}
	.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
<script type="text/javascript">
	function updateStatus(value){
		$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath}/Product/Admin/pushMessage!updateStatuById.action?pushMessage.id="+value,
			/* data:"inv.productId=${product.id}", */
			success : function(data) {
				if(data.status == 'ok'){
					alert("取消成功");
					window.location.href="${pageContext.request.contextPath}/Product/Admin/pushMessage!loadPushMessage.action"
				}else{
					alert("取消失败");
					window.location.href="${pageContext.request.contextPath}/Product/Admin/pushMessage!loadPushMessage.action"
				}
			}
		});
	}
	function nowSend(value){
		$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath}/Product/Admin/pushMessage!nowSend.action?pushMessage.id="+value,
			/* data:"inv.productId=${product.id}", */
			success : function(data) {
				if(data.status == 'ok'){
					alert("立即推送成功");
					window.location.href="${pageContext.request.contextPath}/Product/Admin/pushMessage!loadPushMessage.action"
				}else{
					alert("立即推送失败");
					window.location.href="${pageContext.request.contextPath}/Product/Admin/pushMessage!loadPushMessage.action"
				}
			}
		});
	}
</script>
<title>推送消息</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h3>推送消息</h3>
		<table border="1">
			<tr>
				<td width="50px" style="text-align: center;">序号</td>
				<td width="100px" style="text-align: center;">标题</td>
				<td width="100px" style="text-align: center;">内容</td>
				<td width="100px" style="text-align: center;">推送目标</td>
				<td width="200px" style="text-align: center;">状态</td>
				<td width="200px" style="text-align: center;">插入时间</td>
				<td width="200px" style="text-align: center;">操作</td>
			</tr>
		<c:forEach items="${list}"  var="item" varStatus="i">
			<tr>
				<td style="text-align: center;">${i.index+1}</td>
				<td style="text-align: center;">${item.title}</td>
				<td style="text-align: center;">${item.content}</td>
				<td style="text-align: center;">${item.target eq 'baiyimao'?'所有用户':item.target}</td>
				<td style="text-align: center;">${item.statusName}</td>
				<td style="text-align: center;">${item.insertTime}</td>
				<td style="text-align: center;">
					<c:if test="${item.status == 0 }">
						<a href="javascript:nowSend('${item.id}');">立即推送</a>
						|<a href="javascript:updateStatus('${item.id}');">取消推送</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>

		</table>
	</div>
</div>
</body>
</html>