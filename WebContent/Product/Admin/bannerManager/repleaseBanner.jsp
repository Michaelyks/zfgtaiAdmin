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
	$("#hidDescr").val($("#description").html());
	$("#btnSubmit").attr("disabled","disabled");
	return true;
}
	
</script>
<title>baner图片</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h3>baner图片</h3>
		<form action="${pageContext.request.contextPath}/Product/Admin/banner!saveBanner.action" method="post" onsubmit="return isSubmit()">
			 <table border="1">
				<tr>
					<td>标题</td>
					<td><input type="text" name="banner.title"></td>
				</tr>
				<tr>
					<td class="rightText" style="text-align: right;vertical-align: top;">描述：</td>
					<td class="leftText">
						<input type="hidden" id="hidDescr" name="banner.description">
						<div id="description" class="divEdit" name="banner.description" contenteditable="true" > </div>
					</td>
				</tr>
				<tr>
					<td>公告ID</td>
					<td><input type="text" name="banner.noticeId"></td>
				</tr>
				<tr>
					<td>活动链接</td>
					<td><input type="text" name="banner.hdUrl"></td>
				</tr>
				<tr>
					<td>类型</td>
					<td>
						<select id="type" name="banner.type" onchange="changgeURL()">
							<option value="0">web</option>
							<option value="1" selected="selected">mobile</option>
							<option value="2">喵商城</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="rightText" style="text-align: right;vertical-align: top;">banner图片：</td>
					<td class="leftText">
					<input type="hidden" name="banner.imgURL" value="" id="infoImg">
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
 	window.location.href="${pageContext.request.contextPath}/Product/Admin/banner!loadBanner.action";
 }else if("${isOk}"=="no"){
 	alert("发布失败!");
 	window.location.href="${pageContext.request.contextPath}/Product/Admin/banner!loadBanner.action";
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
         url: "${pageContext.request.contextPath}/Product/Admin/banner!uploadMobileBannerImage.action",
         addRemoveLinks: true,
         dictRemoveLinks: "x",
         dictCancelUpload: "x",
         maxFiles: maxFiles,
         maxFilesize: 1,
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
            	 	var delUrl="${pageContext.request.contextPath}/Product/Admin/banner!removeMobileBannerImage.action";
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
 
 
//信息披露;
 $("#dropz2").dropzone({
         url: "${pageContext.request.contextPath}/Product/Admin/banner!uploadWebBannerImage.action",
         addRemoveLinks: true,
         dictRemoveLinks: "x",
         dictCancelUpload: "x",
         maxFiles: maxFiles,
         maxFilesize: 1,
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
            	 	var delUrl="${pageContext.request.contextPath}/Product/Admin/banner!removeWebBannerImage.action";
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