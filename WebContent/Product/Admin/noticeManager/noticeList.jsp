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
			url : "${pageContext.request.contextPath}/Product/Admin/notice!updateNotice.action?notice.id="+value,
			/* data:"inv.productId=${product.id}", */
			success : function(data) {
				if(data.status == 'ok'){
					alert("下架成功");
					window.location.href="${pageContext.request.contextPath}/Product/Admin/notice!findNotice.action";
				}else{
					alert("下架失败");
					window.location.href="${pageContext.request.contextPath}/Product/Admin/notice!findNotice.action";
				}
			}
		});
	}
</script>
<title>公告</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h3>公告</h3>
		<table border="1"  style="text-align: center;table-layout:fixed;width: 1500px;word-break:break-all; word-wrap:break-all;">
			<tr>
				<td style="width: 50px;">序号</td>
				<td style="width: 200px;">公告ID</td>
				<td style="width: 200px;">标题</td>
				<td style="width: 50px;">公告类型</td>
				<td style="width: 500px;">公告描述</td>
				<td style="width: 50px;">是否置顶</td>
				<td style="width: 50px;">操作</td>
			</tr>
		<c:forEach items="${list}"  var="item" varStatus="i">
			<tr>
				<td >${i.index+1}</td>
				<td >${item.id}</td>
				<td >${item.title}</td>
				<td >${item.typeZw}</td>
				<td >${item.description}</td>
				<td ><c:if test="${item.isTop == 1 }">是</c:if>
					<c:if test="${item.isTop == 0 || item.isTop == '' }">否</c:if>
				</td>
				<td >
						<a href="${pageContext.request.contextPath}/Product/Admin/notice!toEditNotice.action?notice.id=${item.id}">修改</a>
						|<a href="javascript:updateStatus('${item.id}');">下架</a>
				</td>
			</tr>
		</c:forEach>

		</table>
	</div>
</div>
</body>
</html>