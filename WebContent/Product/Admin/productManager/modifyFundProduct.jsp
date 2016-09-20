<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  deferredSyntaxAllowedAsLiteral="true" %>
    <!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
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
<!-- kindeditor上传 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/kindeditor/themes/default/default.css" />
<script charset="utf-8"  type="text/javascript" src="${pageContext.request.contextPath}/Product/Admin/kindeditor/kindeditor.js"></script>
<script charset="utf-8"  type="text/javascript" src="${pageContext.request.contextPath}/Product/Admin/kindeditor/lang/zh_CN.js"></script>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu.css" rel="stylesheet" type="text/css" />
<title>中泰理财-修改基金产品</title>
<style type="text/css">
	.rightText{
		text-align: right;
	}
	.leftText{
		text-align: left;
	}
	.divEdit {
	  width: 545px;
	  overflow: auto;
	  min-height: 120px;
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
</head>
<script type="text/javascript">

var editor2;
KindEditor.ready(function(K) {
	var options = {
			
    	    filterMode : true,
    	    themeType : 'default',
    	    uploadJson : '${pageContext.request.contextPath}/Product/kindEditor!keuploadproduct.action',
			fileManagerJson : '<%=request.getContextPath()%>/keupload/manager',
			allowFileManager : false,
     	   resizeType : 1
		};
	editor2 = K.create('#hkly', options);
});

var editor3;
KindEditor.ready(function(K) {
	var options = {
			
    	    filterMode : true,
    	    themeType : 'default',
    	    uploadJson : '${pageContext.request.contextPath}/Product/kindEditor!keuploadproduct.action',
			fileManagerJson : '<%=request.getContextPath()%>/keupload/manager',
			allowFileManager : false,
     	   resizeType : 1
		};
	editor3 = K.create('#zjbz', options);
});

var editor4;
KindEditor.ready(function(K) {
	var options = {
			
    	    filterMode : true,
    	    themeType : 'default',
    	    uploadJson : '${pageContext.request.contextPath}/Product/kindEditor!keuploadproduct.action',
			fileManagerJson : '<%=request.getContextPath()%>/keupload/manager',
			allowFileManager : false,
     	   resizeType : 1
		};
	editor4 = K.create('#cplxjs', options);
});
var isShow=false;
function isSubmit(){
	var title=$("#title").val(); 
	$("#hidHkly").val(editor2.html());
	$("#hidZjbz").val(editor3.html());
	$("#hidCplxjs").val(editor4.html());
	if($("#title").val()==""){	
		alert("产品名称不能为空!");
		return false;
	}
	if($("#jiangLiEarnings").val()!=""&&!isPrice($("#jiangLiEarnings").val())){	
		alert("浮动收益格式不正确!");
		return false;
	}
	if(!confirm("重要的事，要说三遍！你确定修改浮动收益为："+$("#jiangLiEarnings").val()+"?")){
		return false;
	}
	isShow=true;
	return true;
}
//预览发布产品效果
function preview(){
	//alert("0000");
	var url = "${pageContext.request.contextPath}/Product/Admin/releaseFundProduct!showModifyFundProduct.action?";
	 $("#frm").attr("target","_blank");
	 $('#frm').attr("action", url).submit();
	 if(isShow){
	 	$("#btnSubmit").css('display','inline');
	 }
}
//修改产品
function sub(){
	//alert("0000");
	var url = "${pageContext.request.contextPath}/Product/Admin/releaseFundProduct!releaseFundProduct.action";
	$("#frm").attr("target","_self");
	$('#frm').attr("action", url).submit();
}
</script>
<body>
<div align="center">
	<jsp:include page="/Product/Admin/common/head.jsp"/>
  <div class="main">
	<h1 style="text-align: center;">修改基金产品</h1>
	<form action="${pageContext.request.contextPath}/Product/Admin/releaseFundProduct!releaseFundProduct.action?" id="frm" method="post" enctype="multipart/form-data" onsubmit="return isSubmit()" >
			<table border="1" style="width: 850px;">

				<tr>
					<td class="rightText" style="width:20%"><span style="color: red;">*</span>产品名称：</td>
					<td class="leftText" style="width:80%">
					<input id="id" type="hidden" name="product.id" value="${product.id}">
					<input  id="title" name="product.title" value="${product.title}" style="width: 500px">
					</td>
				</tr>
				<tr>
					<td class="rightText" style="width:20%">上市后的浮动收益：</td>
					<td class="leftText">
					<input  id="jiangLiEarnings" name="product.jiangLiEarnings" value="${product.jiangLiEarnings}" style="width: 50px">%
					</td>
				</tr>
				<tr>
					<td class="rightText" style="width:20%">进度通知：</td>
					<td class="leftText" style="width:80%">
					<input  id="hdby" name="product.hdby" value="${product.hdby}" style="width: 500px"><br>
					</td>
				</tr>
				<tr>
					<td class="rightText" style="width:20%">手机浮动收益说明链接</td>
					<td class="leftText" style="width:80%">
					<input  id="fdsysmUrl" name="product.fdsysmUrl" value="${product.fdsysmUrl}" style="width: 500px"><br>
					</td>
				</tr>				
				<tr>
					<td class="rightText" style="width:20%">网站浮动收益说明链接</td>
					<td class="leftText" style="width:80%">
					<input  id="fdsysmUrlWeb" name="product.fdsysmUrlWeb" value="${product.fdsysmUrlWeb}" style="width: 500px"><br>
					</td>
				</tr>
				<tr>
					<td class="rightText" style="width:20%">手机转让规则链接</td>
					<td class="leftText" style="width:80%">
					<input  id="zrgzUrl" name="product.zrgzUrl" value="${product.zrgzUrl}" style="width: 500px"><br>
					</td>
				</tr>				
				<tr>
					<td class="rightText" style="width:20%">网站转让规则链接</td>
					<td class="leftText" style="width:80%">
					<input  id="zrgzUrlWeb" name="product.zrgzUrlWeb" value="${product.zrgzUrlWeb}" style="width: 500px"><br>
					</td>
				</tr>				
				<tr>
					<td class="rightText" style="text-align: right;vertical-align: top;">还款保障：</td>
					<td class="leftText">
					<input type="hidden" id="hidHkly" name="product.hkly">
					<div id="hkly" class="divEdit" contenteditable="true"  style="height: 500px">${product.hkly}</div>
					</td>
				</tr>
				
				<tr>
					<td class="rightText" style="text-align: right;vertical-align: top;">资金用途：</td>
					<td class="leftText">
					<input type="hidden" id="hidZjbz" name="product.zjbz">
					<div id="zjbz" class="divEdit" contenteditable="true" style="height:500px;">${product.zjbz}</div>
					</td>
				</tr>
				<tr>
					<td class="rightText" style="text-align: right;vertical-align: top;">项目亮点：</td>
					<td class="leftText">
					<input type="hidden" id="hidCplxjs" name="product.cplxjs">
					<div id="cplxjs" class="divEdit" contenteditable="true" style="height:500px;">${product.cplxjs}</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" id="btnSubmit"  style="display:none;text-align: center;width:200px; height: 50px; font-size: 24px" value="发布" onclick="sub()">
						<input type="button" id="btn" style="text-align: center;width:200px; height: 50px; font-size: 24px" value="预览" onclick="preview()">
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
	<form action="${pageContext.request.contextPath}/Product/Admin/releaseProduct!test.action" style="display:none;">
	<input id="endTime2"  name="st" onClick="WdatePicker()" readonly="readonly" style="width:180px;text-align: center;">
	<div id="description2" class="divEdit" contenteditable="true" > </div>
	<input type="hidden" id="hidDescr" name="product.description">
	<input type="submit" style="text-align: center;width:200px; height: 50px; font-size: 24px" value="发布" onClick="javascript:Submit();">
	</form>
</body>
<script type="text/javascript">
 if("${isOk}"=="ok"){
 	alert("修改成功!");	
 	window.location.href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productRecord.action";
 }else if("${isOk}"=="no"){
 	alert("修改失败!");
 }
 
var maxFiles=20;
var fileNamesInfo = "";
var fileNamesLaw = "";
//信息披露;
$("#dropz").dropzone({
        url: "${pageContext.request.contextPath}/Product/Admin/releaseProduct!uploadInfoImage.action",
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
           	 	var delUrl="${pageContext.request.contextPath}/Product/Admin/releaseProduct!removeInfoImage.action";
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
  //法律意见书  
$("#dropz2").dropzone({
	url: "${pageContext.request.contextPath}/Product/Admin/releaseProduct!uploadLawImage.action",
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
        		fileNamesLaw+=json.json;
            	file.serverId = json.json;
            	//alert(fileNamesInfo);
                console.log("File " + file.name + "uploaded");
                $("#lawImg").val(fileNamesLaw);
        	}else{
        		alert("上传图片失败");
        	}
        });
        this.on("removedfile", function(file) {
       	 	var delUrl="${pageContext.request.contextPath}/Product/Admin/releaseProduct!removeLawImage.action";
        	var removeId = file.serverId;
        	fileNamesLaw = fileNamesLaw.replace(removeId, "");
        	 $("#lawImg").val(fileNamesLaw);
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