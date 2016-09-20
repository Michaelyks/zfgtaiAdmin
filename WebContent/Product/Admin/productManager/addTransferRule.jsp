<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
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
	function isSubmit() {
		var minDate=$("#mindate").val();
		var maxDate=$("#maxdate").val();
		var rate=$("#rate").val();
		if(!isOnlyNumber(minDate)){
			 alert("最小天数为整数");
			 return false;
		 }
		 if(!isOnlyNumber(maxDate)){
			 alert("最大天数为整数");
			 return false;
		 }
		 if(!isPrice(rate)){
			 alert("费率填写错误");
			 return false;
		 }
		return true;
	}
</script>
<title>基金产品转让规则</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<h3>添加转让规则</h3>
		<form action="${pageContext.request.contextPath}/Product/Admin/transferCostRate!addTransferRole.action" method="post" onsubmit="return isSubmit()">
			 <table border="1">
				<tr>
					<td>最小天数</td>
					<td><input type="text" id="mindate" name="transferCostRate.minDate"></td>
				</tr>
				<tr>
					<td>最大天数</td>
					<td><input type="text" id="maxdate" name="transferCostRate.maxDate"></td>
				</tr>
				<tr>
					<td>费率</td>
					<td><input type="text" id="rate" name="transferCostRate.rate">%</td>
				</tr>
				<tr>
					<td>基金产品选择</td>
					<td><select name="transferCostRate.productId">
						<c:forEach items="${list}" var="item">
							<option value="${item.id}">${item.title}</option>
						</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" id="btnSubmit" style="text-align: center;width:200px; height: 50px; font-size: 24px" value="添加" >
					</td>
				</tr>
			</table> 
			
		</form>
	</div>
</div>
</body>
<script type="text/javascript">
if("${isOk}"=="ok"){
 	alert("添加成功!");
 	window.location.href="${pageContext.request.contextPath}/Product/Admin/transferCostRate!transferRoleList.action";
 }else if("${isOk}"=="no"){
 	alert("添加失败!");
 	window.location.href="${pageContext.request.contextPath}/Product/Admin/transferCostRate!toAddTransferRole.action";
 }
</script>
</html>