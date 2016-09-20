<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定信息表</title>
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
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
<script type="text/javascript">
	$(function(){
		var message='${message}';
		if(message!=null&&message!=''){
			alert(message);
		}
		
		$("#username").keydown(function(event){
			if(event.keyCode==13){
				queryBindInfo();
			}
		});
		
	});
	function queryBindInfo(){
		var name=$("#username").val();
			if(name!=null&&name!=''){
				window.location.href="${pageContext.request.contextPath}/Product/Admin/bindCardRecord!findAccount.action?name="+name;
			}else{
				alert("请输入用户名");
			}
	}

</script>
<script type="text/javascript">

	
</script>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>绑定信息表</h3>
	<span>用户名:</span> <input id="username" name="username" type="text" value="${username}" maxlength="11" placeholder="用户名">
	<a class="sereach" href="javascript:queryBindInfo();" id="sereach">查询</a>
	<c:if test="${list ne '[]' &&  list ne '' && list ne null}">
		<table border="1" cellspacing="0" cellpadding="0" align="center" >
			<tr>
				<td width="100px;">用户名</td>
				<td width="100px;">姓名</td>
				<td width="200px;">身份证号码</td>
				<td width="200px;">银行卡号</td>
				<td width="100px;">开户银行</td>
				<td width="100px;">手机预留手机号码</td>
				<td width="200px;">绑定时间</td>
				<td width="200px;">第三方平台</td>
				<td width="200px;">省份编码</td>
				<td width="200px;">市级编码</td>
				<td width="200px;">支行</td>
				<td width="100px;">状态</td>
				
			</tr>
			<c:forEach items="${list}" var="item">
				<tr>
					<td width="100px;">${item.users.usernameJM}</td>
					<td width="100px;">${item.bankAccountName}</td>
					<td width="200px;">${item.jmIdcard}</td>
					<td width="200px;">${item.jmbankAccount}</td>
					<td width="200px;">${item.bankName}</td>
					<td width="100px;">${item.jmPhone}</td>
					<td width="100px;">${item.insertTime}</td>
					<td width="100px;">${item.typeChina}</td>
					<td width="100px;">${item.provinceCode}</td>
					<td width="100px;">${item.cityCode}</td>
					<td width="100px;">${item.braBankName}</td>
					<td width="100px;">${item.statusChina}</td>
										
					
				</tr>
			</c:forEach>	
		</table>
	</c:if>
	</div>
</div>
</body>
</html>