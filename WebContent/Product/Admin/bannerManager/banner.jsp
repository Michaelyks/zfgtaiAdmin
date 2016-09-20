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
	function updateStatus(value,title){
		$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath}/Product/Admin/banner!updateStatuById.action?banner.id="+value,
			/* data:"inv.productId=${product.id}", */
			success : function(data) {
				if(data.status == 'ok'){
					alert(title+"成功");
					window.location.href="${pageContext.request.contextPath}/Product/Admin/banner!loadBanner.action"
				}else{
					alert(title+"失败");
					window.location.href="${pageContext.request.contextPath}/Product/Admin/banner!loadBanner.action"
				}
			}
		});
	}
//修改	
	function toModifyBanner(id){
		var status=$("#modify_"+id).text();
		if("修改"==status){
			$("#modify_"+id).text("保存");
			$(".dis_"+id).removeAttr("disabled");
			$(".dis_"+id).removeAttr("readonly");
		}else{			
			var title = $("#title_"+id).val();
			var noticeId = $("#noticeId_"+id).val();
			var hdUrl = $("#hdUrl_"+id).val();
			var url = "${pageContext.request.contextPath}/Product/Admin/banner!modifyBanner.action?";
 			var formData="banner.id="+id;
			    formData+="&banner.title="+title;
			    formData+="&banner.noticeId="+noticeId;
			    formData+="&banner.hdUrl="+hdUrl;
		if(confirm("是否确定保存修改？")){
			 $.post(url,formData,function(data){
				 if(data.status=="ok"){
						//验证码发送成功，跳转到验证页面
						alert(data.json);
						$("#modify_"+id).text("修改");
						$(".dis_"+id).attr("disabled","disabled");
						$(".dis_"+id).attr("readonly","readonly");
						location.reload();
				 }else{
					 alert(data.json);
					 return false;
				 }
			 });
		 }	    

		}
	}
</script>
<title>banner图片</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h3>banner图片</h3>
		<table border="1">
			<tr>
				<td width="50px" style="text-align: center;">序号</td>
				<td width="100px" style="text-align: center;">标题</td>
				<td width="100px" style="text-align: center;">类型</td>
				<td width="200px" style="text-align: center;">插入时间</td>
				<td width="200px" style="text-align: center;">公告ID</td>
				<td width="300px" style="text-align: center;">活动链接</td>
				<td width="100px" style="text-align: center;">目前状态</td>
				<td width="100px" style="text-align: center;">操作</td>
			</tr>
		<c:forEach items="${list}"  var="item" varStatus="i">
			<tr>
				<td style="text-align: center;">${i.index+1}</td>
				<td style="text-align: center;"><input class="dis_${item.id}" style="width:100%;" id="title_${item.id}" readonly="readonly" disabled="disabled" value="${item.title}" /></td>
				<td style="text-align: center;">${item.pt}</td>
				<td style="text-align: center;">${item.insertTime}</td>
				<td style="text-align: center;"><input class="dis_${item.id}" style="width:100%;" id="noticeId_${item.id}" readonly="readonly" disabled="disabled" value="${item.noticeId}" /></td>
				<td style="text-align: center;"><input class="dis_${item.id}" style="width:100%;" id="hdUrl_${item.id}" readonly="readonly" disabled="disabled" value="${item.hdUrl}" /></td>
				<td style="text-align: center;">
					 <c:choose>
						<c:when test="${item.status != null && item.status == '0' }"><a href="javascript:updateStatus('${item.id}','停用')" class="a">启用</a></c:when>
						<c:otherwise><a href="javascript:updateStatus('${item.id}','启用')" class="a">停用</a></c:otherwise>
					</c:choose>
				</td>
				<td><a  id="modify_${item.id}"  href="javascript:toModifyBanner(${item.id})" class="a">修改</a></td>
			</tr>
		</c:forEach>
		</table>
	</div>
</div>
</body>
</html>