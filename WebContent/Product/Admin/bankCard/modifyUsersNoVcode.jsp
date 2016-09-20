<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无验证变更用户帐号</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css"/>
<style type="text/css">
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
<script type="text/javascript">
	function queryInfo(){
		if($("#userName").val()==''||$("#name").val()==''){
			alert("表单不能为空");
			return false;
		}else{
			var userName=$("#userName").val();
			var name=$("#name").val();
			$.ajax({ 
				url: "${pageContext.request.contextPath}/Product/Admin/usersApply!findUsersInfoByUsersName.action?userName="+userName,
				success: function(data){
					if(data.status=='ok'){			       		
			       		if(confirm("用户姓名："+data.json+"     ;是否变更用户帐号?")){
			    			$.ajax({ 
			    				url: "${pageContext.request.contextPath}/Product/Admin/usersApply!modifyUsersBySuperAdmin.action?userName="+userName+"&name="+name,
			    				success: function(data){
			    					if(data.status=='ok'){
			    						alert(data.json);
			    			       		window.location.href="${pageContext.request.contextPath}/Product/Admin/bankCard/modifyUsersNoVcode.jsp";			    			       			
			    					}else{
			    						alert(data.json);
			    					}
			    		      }});
			       		}	
					}else{
						alert(data.json);
					}
		      }});	
		}		
	}
</script> 	
</head>
<body>
<div class="center">		
<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h3>无验证变更用户帐号</h3>
		<form action="" id="frm" method="post" enctype="multipart/form-data" onsubmit="return isSubmit()" >
		<table>
			<tr align="center"><td>旧用户名:<input type="text" name="userName" id="userName" maxlength="11" value="${userName}"></td></tr>
			<tr align="center"><td>新用户名：<input type="text" name="name" id="name" maxlength="11" value="${name}"></td></tr>
	        <tr align="center"><td><input type="button" value="提交"  onclick="queryInfo()"></td></tr>
        </table>
        </form>
	</div>
</div>

</body>
</html>