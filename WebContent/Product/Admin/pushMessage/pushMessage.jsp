<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/artDialog4.1.7/artDialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dropzone/dropzone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/dropzone/dropzone.css">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
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
</style>
<script type="text/javascript">
function isSubmit(){
	var title=$("#title").val();
	var content=$("#content").val();
	if(title=''){
		alert("标题为空");
		return false;
	}
	if(content=''){
		alert("内容为空");
		return false;
	}
	return true;
}
	
</script>
<title>推送消息</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h3>推送消息</h3>
		<form action="${pageContext.request.contextPath}/Product/Admin/pushMessage!savePushMessage.action" method="post" onsubmit="return isSubmit()">
			 <table border="1">
				<tr>
					<td>推送标题：</td>
					<td><input type="text" id="title"  name="pushMessage.title"></td>
				</tr>
				<tr>
					<td>推送用户:</td>
					<td>不填,则推送所有用户<br><input type="text" id="title"  name="topic" maxlength="11"></td>
				</tr>
				<tr>
					<td >推送内容：</td>
					<td >
						<textarea id="content" name="pushMessage.content" style="width:300px;height:50px;"></textarea>
					</td>
				</tr>
<!-- 				<tr>
					<td>类型</td>
					<td>
						<select name="versions.type" >
							<option value="0">IOS</option>
							<option value="1" selected="selected">Android</option>
						</select>
					</td>
				</tr> -->
				<tr>
					<td colspan="2" align="center">
						<input type="submit" id="btnSubmit" style="text-align: center;width:200px; height: 50px; font-size: 24px" value="推送" >
					</td>
				</tr>
			</table> 
			
		</form>
	</div>
</div>
</body>
<script type="text/javascript">
 if("${isOk}"=="ok"){
 	alert("发布成功,待审核!");
 	window.location.href="${pageContext.request.contextPath}/Product/Admin/pushMessage!loadPushMessage.action";
 }else if("${isOk}"=="no"){
 	alert("发布失败,待审核!");
 	window.location.href="${pageContext.request.contextPath}/Product/Admin/pushMessage!loadPushMessage.action";
 }
</script>
</html>