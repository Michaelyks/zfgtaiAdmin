<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加模块功能</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
</head>
<script type="text/javascript">
	function query(){
		var type=$("#type").val(); 	
		if(type!='1'){
			$("#parentId option").remove();
			$("#path").css("display","block");	
			$("#tab").css("align","center");
			$("#parent").css("display","block");
			$("#parent").css("align","center");
			var url = "${pageContext.request.contextPath}/Product/Admin/modul!findModulByType.action?"; 	
			var su = "";
			$.ajax({
				type : "post",
				url : url,
				async: true,
				data:"type="+(parseInt(type)-1),
				success : function(data, textStatus) {
					su = data;
				},
				complete : function(XMLHttpRequest, textStatus) {
					if ("ok" == su.status) {
						var list=su.json;
						for ( var i = 0; i < list.length; i++) {
							var modul = list[i];
							$("#parentId").append("<option value='"+modul.id+"'>"+modul.modulName+"</option>");
						}
					}
				}
			});
		}else{
			$("#path").css("display","none");	
			$("#parent").css("display","none");
		}
	}
	function isSubmit(){
		var roleName=$("#roleName").val(); 	
		if(roleName==''){
			alert("请输入角色名称");
			return false;
		}
	}
</script>
<body>

<div class="center">	
	<jsp:include page="/Product/Admin/common/head.jsp"/>		
	<div class="main" align="center" style="text-align: center;">
		<form action="${pageContext.request.contextPath}/Product/Admin/roles!rolesAdd.action" enctype="multipart/form-data" onsubmit="return isSubmit();">
			<table id="tab" align="center">
				<tr>
					<td>角色名称</td>
					<td><input type="text" id="roleName" name="role.roleName"></td>
				</tr>				
				<tr>
					<td>备注</td>
					<td><input type="text" name="role.note"></td>
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
	$(function(){
		var message='${message}';
		if(message!=''){
		alert(message);
		if(message='添加成功'){			
			window.location.href="${pageContext.request.contextPath}/Product/Admin/roles!findRoles.action"
		}else{
			window.location.href="${pageContext.request.contextPath}/Product/Admin/functionManager/rolesAdd.jsp"
		}
	   }
	});
</script>
</html>