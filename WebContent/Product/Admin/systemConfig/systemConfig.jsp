<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<style type="text/css">
	.sereach {
  width: 200px;
  height: 32px;
  line-height: 32px;
  text-align: center;
  border: 1px solid #009DDA;
  border-radius: 5px;
}
 .select1 {
  border-radius: 5px;
  border-color: #009DDA;
  margin-right: 5px;
}
</style>
<title>系统配置</title>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
		<form action="${pageContext.request.contextPath}/Product/Admin/systemConfig!updateSystemConfig.action">
			<table>
				<tr>
					<td>服务器硬盘的地址:</td>
					<td>
						<input type="hidden" name="config.id" value="${config.id}">
						<input type="text" readonly="readonly"  name="config.fileUrl" value="${config.fileUrl}" style="width:500px;">
					</td>
				</tr>
				<tr>
					<td>服务器ip的地址:</td>
					<td><input type="text" name="config.httpUrl" value="${config.httpUrl}" style="width:500px;"></td>
				</tr>
				<tr>
					<td>图片项目名:</td>
					<td><input type="text" name="config.fileName" readonly="readonly" value="${config.fileName}" style="width:500px;"></td>
				</tr>
				<tr>
					<td>客服电话:</td>
					<td><input type="text" name="config.companyTel" value="${config.companyTel}" style="width:500px;"></td>
				</tr>
				<tr>
					<td>公司名称:</td>
					<td><input type="text" name="config.companyName" value="${config.companyName}" readonly="readonly" style="width:500px;"></td>
				</tr>
				<tr>
					<td>发息日;1为每个月的1号;28为每个月的28号</td>
					<td><input type="text" name="config.payInterestDay" value="${config.payInterestDay}" style="width:500px;"></td>
				</tr>
				<tr>
					<td>推荐语</td>
					<td><input type="text" name="config.tjy" value="${config.tjy}" style="width:500px;"></td>
				</tr>
				<tr>
					<td>活动标语</td>
					<td><input type="text" name="config.hdby" value="${config.hdby}" style="width:500px;"></td>
				</tr>
				<tr>
					<td>活动链接</td>
					<td><input type="text" name="config.hdlj" value="${config.hdlj}" style="width:500px;"></td>
				</tr>
				<tr>
					<td>零钱包收益率</td>
					<td><input type="text" name="config.earnings" value="${config.earnings}" style="width:500px;"></td>
				</tr>
				<tr>
					<td>零钱包说明 </td>
					<td><input type="text" name="config.coinPurseExplanation" value='${config.coinPurseExplanation}' style="width:500px;">
					
					</td>
				</tr>
				<tr>
					<td>基金转让规则 </td>
					<td><input type="text" name="config.zrgzUrlMobile" value='${config.zrgzUrlMobile}' style="width:500px;">
					
					</td>
				</tr>
				<tr>
					<td>银行安全规则 </td>
					<td><input type="text" name="config.bankSafeUrlMobile" value='${config.bankSafeUrlMobile}' style="width:500px;">
					
					</td>
				</tr>
				<tr>
					<td>短信签名 </td>
					<td><input type="text" name="config.smsQm" value='${config.smsQm}' style="width:500px;">
					
					</td>
				</tr>
				<tr>
					<td>话费短信 </td>
					<td><input type="text" name="config.msgCost" value='${config.msgCost}' style="width:500px;">
					
					</td>
				</tr>				
				<tr>
					<td colspan="2" align="center">
						<input type="button" id="btn" style="text-align: center;width:200px; height: 50px; font-size: 24px" onclick="javascript:history.go(-1);" value="返回">
						<input type="submit" id="btnSubmit"  style="text-align: center;width:200px; height: 50px; font-size: 24px" value="修改"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript">
	 if("${isOk}"=="ok"){
	 	alert("修改成功!");
	 	window.location.href="${pageContext.request.contextPath}/Product/Admin/systemConfig!toSystemConfig.action";
	 }else if("${isOk}"=="no"){
	 	alert("修改失败!");
	 	window.location.href="${pageContext.request.contextPath}/Product/Admin/systemConfig!toSystemConfig.action";
	 }
 </script>
</body>
</html>