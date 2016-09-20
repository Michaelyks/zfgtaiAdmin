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
		var modulName=$("#modulName").val(); 	
		var type=$("#type").val(); 	
		var parentId=$("#parentId").val(); 	
		var modulPath=$("#modulPath").val();
		if(type==''){
			alert("请选择类型");
			return false;
		}
		if(modulName==''){
			alert("请输入模块名称");
			return false;
		}
		if(type!='1'){
			if(modulPath==''){
				alert("请填写模块路径");
				return false;
			}
			
			if(parentId==''){
				alert("请选择父节点");
				return false;
			}
		}
	}
</script>
<body>

<div class="center">	
	<jsp:include page="/Product/Admin/common/head.jsp"/>		
	<div class="main" align="center" style="text-align: center;">
		<h1 style="text-align: center;">添加功能</h1>
		<form action="${pageContext.request.contextPath}/Product/Admin/modul!saveModul.action" onsubmit="return isSubmit();">
			<table id="tab" align="center">
				<tr>
					<td>模块名称</td>
					<td><input type="text" id="modulName" name="modul.modulName"></td>
				</tr>
				<tr>
					<td>类型</td>
					<td>
						<select id="type" name="modul.type" onchange="query()">
							<option value="1" selected="selected" >第一级</option>
							<option value="2">第二级</option>
							<option value="3">第三级</option>
						</select>
					</td>
				</tr>
				<tr id="path" style="display: none;">
					<td>模块路径</td>
					<td><input id="modulPath" type="text" name="modul.modulPath" style="width:500px;"></td>
				</tr>
				<tr id="parent" style="display: none;">
					<td>父节点</td>
					<td>
						<select id="parentId" name="modul.parentId">
							
						</select>
					</td>
				</tr>
				<tr>
					<td>模块备注</td>
					<td><input type="text" name="modul.note"></td>
				</tr>
				<tr>
					<td>模块编码</td>
					<td><input type="text" name="modul.modulCode"></td>
				</tr>
				<tr>
					<td>排序</td>
					<td><input type="text" name="modul.sort"></td>
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
			if(message!='添加成功'){
				window.location.href="${pageContext.request.contextPath}/Product/Admin/functionManager/replaseFunction.jsp"
			}else{
				//window.location.href="${pageContext.request.contextPath}/Product/Admin/modul!findModul.action"
				//location.reload();
				window.location.href="${pageContext.request.contextPath}/Product/Admin/functionManager/replaseFunction.jsp"
			}
		}
	});
</script>
</html>