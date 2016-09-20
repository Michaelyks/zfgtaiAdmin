<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
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
	tr{
	  padding-bottom: 20px;
	}
	.input-text{ width:200px; height:24px;}
</style>

<title>后台管理 - 发布积分兑换商品</title>
</head>	
<body>
<%-- <jsp:include page="top.jsp" /> --%>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
	<h1 style="text-align: center;">发布商品</h1>
		<form id="exchangeForm" method="post">
			<table>
				<tr>
					<td><span style="color: red;">*</span>商品名称</td>
					<td><input type="text" name="mProduct.title" id="title" maxlength="32" class="input-text"/>-不可超过32个字符</td>
				</tr>
			    <tr style="height: 10px;">
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr>
				<tr>
					<td><span style="color: red;">*</span>商品价值:</td>
					<td><input type="text" name="mProduct.price" id="price" class="input-text">积分</td>
				</tr>
				<tr style="height: 10px;">
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr>
				<tr>
					<td><span style="color: red;">*</span>商品类型</td>
					<td>
						<select id="type" name="mProduct.type" onchange="changgeType()" class="input-text">
						<option value="1" selected="selected">理财券</option>
							<option value="2" >话费券</option>
							<option value="3" >流量券</option>
							<option value="4" >实物</option>
						</select>
					</td>
				</tr>
				<tr style="height: 10px;">
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr>
				<tr id="mPrice" style="display: none" >
					<td><span style="color: red;">*</span>市场价格</td>
					<td>
						<input type="text" id="marketPrice" name="mProduct.marketPrice" class="input-text">元
					</td>
				</tr>
				 <tr style="height: 10px;display: none" id="msgPrice">
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr> 
				<tr id="coupon">
					<td><span style="color: red;">*</span>卡券金额</td>
					<td>
						<input type="text" id="money" name="mProduct.money" class="input-text"><span id="coupType">元</span>
					</td>
				</tr>
				<tr style="height: 10px;">
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr>
				<tr>
					<td><span style="color: red;">*</span>库存量:</td>
					<td><input type="text" name="mProduct.stock" id="stock" class="input-text"></td>
				</tr>
				<tr>
					<tr style="height: 10px;">
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr>
				<!-- <tr>
					<td>VIP等级</td>
					<td>
						<select id="vip" name="mProduct.vip" class="input-text">
						<option value="0" selected="selected">VIP0</option>
							<option value="1" >VIP1</option>
							<option value="2" >VIP2</option>
							<option value="3" >VIP3</option>
							<option value="4" >VIP4</option>
							<option value="5" >VIP5</option>
							<option value="6" >VIP6</option>
							
						</select>
					</td>
				</tr>
					<tr style="height: 10px;">
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr> -->
				<tr>
					<td>邮费</td>
					<td>
						<select id="postage" name="mProduct.postage" class="input-text">
							<option value="0" selected="selected">中泰理财包邮</option>
							<option value="1" >包邮自理</option>
						</select>
					</td>
				</tr>
				<tr style="height: 10px;">
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr>
				<!-- <tr>
					<td><span style="color: red;">*</span>商品详情地址:</td>
					<td><input type="text" name="mProduct.detailURL" id="detailURL" class="input-text" class="input-text"></td>
				</tr> -->
				<tr>
					<td><span style="color: red;">*</span> 商品描述</td>
					<td><textarea rows="3" cols="70" name="mProduct.description" id="description"></textarea>
				</tr>
				<tr style="height: 10px;">
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr>
				<!-- <tr>
					<td>兑换说明</td>
					<td><textarea rows="3" cols="70" name="mProduct.explains" id="explains"></textarea>
				</tr>
				<tr style="height: 10px;">
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr>
				<tr>-->
					<td>商品图片:</td>
					<td>	
					<input type="hidden" name="mProduct.img" value="" id="infoImg">
					<div id="dropz" class="dropzone" name="file" >				
					</div>
						<div id="dropz2" class="dropzone" name="file" style="display: none;"></div>
					</td>
				</tr>
				<tr style="height: 10px;">
					<td colspan="2" align="center"><span id="rechargeMoney_msg"></span></td>
				</tr> 
				<tr style="text-align: center;">
					<td colspan="2"><input type="button" id="btnUpload" value="发布" style="text-align: center;width:200px; height: 50px; font-size: 24px"> </td>
				</tr>
			</table>
		</form>
	</div>
</div>
	<script type="text/javascript">
	
	 $("#title").blur(function(){
			var title=$("#title").val();
			 if(isEmpty(title)){
				$("#title").select();
				alert("商品名称不能为空");
				return false;
			}
			var url="${pageContext.request.contextPath}/Product/Admin/releaseMeow!findTitle.action";
			$.ajax({
				type:"post",
				url:url,
				data:$("#exchangeForm").serialize(),
				success:function(data){
					if(data.status=="err"){
						alert(data.json);
					}
				}
			});
		});
		
	 
	 
	 function changgeType(){
			var type=$("#type option:selected").val();
			if(type==1 || type==2){
				$("#coupType").text("元");
				$("#mPrice").hide();//隐藏
				$("#msgPrice").hide();//隐藏 
			}else if (type==3){
				$("#coupType").text("兆");
				$("#mPrice").hide();//隐藏
				$("#msgPrice").hide();//隐藏
			}else{
				$("#coupType").text("元");
				$("#mPrice").show();//显示
				$("#msgPrice").show();//隐藏
			}
	      }

	$(document).ready(function(){
		
		function CheckUrl(str) { 
				 var checkfiles=new RegExp("((^http)|(^https)|(^ftp)):\/\/(\\w)+\.(\\w)+");  
				// var checkfiles=new RegExp("^[A-Za-z+]+:\/\/(\\w)+\.(\\w)+");  
				   if(!checkfiles.test(str)){  
				          return true ;
				   }  
				   else{  
				         return false;
				   }      
				   
			} 
		
		
		$("#btnUpload").click(function(){
			var type=$("#type option:selected").val();
			//alert(type ==2);
			var title=$("#title").val();
			var price=$("#price").val();
			var stock=$("#stock").val();
			var money=$("#money").val();
			var description=$("#description").val();
			if(isEmpty(title)){
				alert("请填写商品名称");
				return false;
			}
		 if(isEmpty(price)){
				alert("请填写商品价值");
				return false;
			}
			else if(!(/(^[1-9]\d*$)/.test(price))){
				alert("商品价值只能是正整数");
		 		return false;
			}
			
			 if(isEmpty(stock)){
				alert("请填写库存量");
				return false;
			}
			
		else if(!(/(^[1-9]\d*$)/.test(stock))){
				alert("库存量只能是正整数");
		 		return false;
			}

			 if(type ==5 && isEmpty(money)){
					alert("请填写卡券金额");
					return false;
				}
				
			else if(type ==5 &&!(/(^[1-9]\d*$)/.test(money))){
					alert("卡券金额只能是正整数");
			 		return false;
				}
			 
			 
		 else if (isEmpty(description)){
			alert("请填商品描述");
			return false;
		} else if(type == 4){
			var money=$("#marketPrice").val();
			if(isEmpty(money)){
				alert("请填写市场价格");
				return false;
			}
		}
			var url="${pageContext.request.contextPath}/Product/Admin/releaseMeow!releaseMeowProduct.action";
			$.ajax({
				type:"post",
				url:url,
				data:$("#exchangeForm").serialize(),
				success:function(data){
					if(data.status=="ok"){
						alert(data.json);
						window.location.reload();
					}else{			 
					 	alert(data.json);
					 	return false;
				 }
				}
			});
			
		});
	});
	 var maxFiles=20;
 var fileNamesInfo = "";
 var fileNamesLaw = "";
	//信息披露;
 $("#dropz").dropzone({
         url: "${pageContext.request.contextPath}/Product/Admin/releaseMeow!uploadMobileMeowProductImage.action",
         addRemoveLinks: true,
         dictRemoveLinks: "x",
         dictCancelUpload: "x",
         maxFiles: maxFiles,
         maxFilesize: 1,
         uploadMultiple:false,
         dictDefaultMessage:  "请点击此添加图片或拖放图片进此(图片大小不大于1MB)",
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
            	 	var delUrl="${pageContext.request.contextPath}/Product/Admin/releaseMeow!removeMobileMeowProductImage.action";
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
         url: "${pageContext.request.contextPath}/Product/Admin/releaseMeow!uploadWebMeowProductImage.action",
         addRemoveLinks: true,
         dictRemoveLinks: "x",
         dictCancelUpload: "x",
         maxFiles: maxFiles,
         maxFilesize: 1,
         uploadMultiple:false,
         dictDefaultMessage: "请点击此添加图片或拖放图片进此(图片大小小于1MB)",
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
            	 	var delUrl="${pageContext.request.contextPath}/Product/Admin/releaseMeow!removeWebMeowProductImage.action";
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
</body>
</html>
