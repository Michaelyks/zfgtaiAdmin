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

<title>启动页</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h3>启动页</h3>
		<form action="${pageContext.request.contextPath}/Product/Admin/startPage!saveStartPage.action" method="post">
			 <table border="1">
				<tr>
					<td>标题</td>
					<td><input type="text" name="startPage.title"></td>
				</tr>
				<tr>
					<td>类型</td>
					<td>
						<select id="type" name="startPage.type">
							<option value="2" selected="selected">所有</option>
							<option value="0">IOS</option>
							<option value="1">Android</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="rightText" style="text-align: right;vertical-align: top;">启动页：</td>
					<td class="leftText">
					<input type="hidden" name="startPage.imgUrl" value="" id="infoImg">
					<div id="dropz" class="dropzone" name="file"></div>
					<div id="dropz2" class="dropzone" name="file" style="display: none;"></div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" id="btnSubmit" style="text-align: center;width:200px; height: 50px; font-size: 24px" value="发布" >
					</td>
				</tr>
			</table> 
			
		</form>
	</div>
</div>
</body>

<script type="text/javascript">

 if("${isOk}"=="ok"){
 	alert("发布成功!");
 	window.location.href="${pageContext.request.contextPath}/Product/Admin/startPage!loadStartPage.action";
 }else if("${isOk}"=="no"){
 	alert("发布失败!");
 	window.location.href="${pageContext.request.contextPath}/Product/Admin/startPage!loadStartPage.action";
 }
 var maxFiles=20;
 var fileNamesInfo = "";
 var fileNamesLaw = "";
 function changgeURL(){
		var type=$("#type option:selected").val();
		if(type==0){
			$("#dropz2").css("display","block");
			$("#dropz").css("display","none");
		}else{
			$("#dropz2").css("display","none");
			$("#dropz").css("display","block");
		}
}
 //信息披露;
 $("#dropz").dropzone({
         url: "${pageContext.request.contextPath}/Product/Admin/startPage!uploadMobileStartPageImage.action",
         addRemoveLinks: true,
         dictRemoveLinks: "x",
         dictCancelUpload: "x",
         maxFiles: maxFiles,
         maxFilesize: 3,
         uploadMultiple:false,
         dictDefaultMessage: "请点击此添加图片或拖放图片进此",
         dictInvalidFileType: "图片格式错误,只支持‘png、jpg、jpeg’格式",
         dictFileTooBig: "图片大小超出上传范围",
         dictMaxFilesExceeded: "该图片上传失败,每次只能上传"+maxFiles+"张",
         acceptedFiles: ".png,.jpg,.jpeg",
         dictRemoveFile:"删除图片",
         init: function() {
             this.on("success", function(file,response) {
             	var json = eval('(' + response + ')');
             	if("ok"==json.status){
 	            	fileNamesInfo+=json.json;
 	            	file.serverId = json.json;
 	            	//alert(fileNamesInfo);
 	                console.log("File " + file.name + "uploaded");
 	                $("#infoImg").val(fileNamesInfo);
             	}else{
             		alert("上传图片失败");
             	}
             });
             this.on("removedfile", function(file) {
            	 	var delUrl="${pageContext.request.contextPath}/Product/Admin/startPage!removeMobileStartPageImage.action";
             	var removeId = file.serverId;
             	fileNamesInfo = fileNamesInfo.replace(removeId, "");
             	//alert("undefined"==removeId+"");
             	if("undefined"==removeId+""){
             		return false;
             	}
             	 $("#infoImg").val(fileNamesInfo);
             		//alert(removeId);
             	$.post(delUrl,"removeId="+removeId,function(data){
             		if("ok"!=data.status)
     					alert(data.json);//失败的结果
     			}); 
                 console.log("File " + file.name + "removed");
                 
             });
         }
 });
 
</script>
</html>