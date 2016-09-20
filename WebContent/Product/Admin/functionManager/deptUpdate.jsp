<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改部门</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript">
function isSubmit(){
	var deptName=$("#deptName").val(); 		

	if(deptName==''){
		alert("请输入部门名称");
		return false;
	}

}
</script>
</head>
<body>

<div class="center">	
	<jsp:include page="/Product/Admin/common/head.jsp"/>		
	<div class="main" align="center" style="text-align: center;">
	  <h3 align="center">修改部门</h3>
		<form action="${pageContext.request.contextPath}/Product/Admin/dept!deptUpdate.action" onsubmit="return isSubmit();">
			<table id="tab" align="center">
				<tr>
					<td>部门名称</td>
					
					<td><input type="text" id="deptName" name="dept.deptName" value="${dept.deptName}">
					    <input type="hidden" id="id" name="dept.id" value="${dept.id}"></td>
				</tr>
				<tr>
					<td>备注</td>
					<td><input type="text" id="note" name="dept.note" value="${dept.note}"></td>
				</tr>				

				<tr>
					<td colspan="2"><input type="submit" value="提交"></td>
				</tr>
			</table>
		</form>
	</div>
	</div>
</body>
<script type="text/javascript">
 var id=document.getElementById("id").value;
	$(function(){
		var message='${message}';
		if(message!=''){
			alert(message);	
			if(message!='修改成功'){
				window.location.href="${pageContext.request.contextPath}/Product/Admin/dept!toDeptUpdate.action?id="+id;
			}else{
				window.location.href="${pageContext.request.contextPath}/Product/Admin/dept!findDeptList.action";
			}
		}
	});
</script>
</html>