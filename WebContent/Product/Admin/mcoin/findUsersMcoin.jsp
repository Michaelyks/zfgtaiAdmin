<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人可以积分</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
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
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>积分报表</h3><br/>
	<table border="1" cellspacing="0" cellpadding="0" style="text-align: center;">
		<tr>
		<td width="100px;">序号</td>
		<td>用户名</td>
		<td width="200px;">积分数量</td>
		<td width="300px;">操作</td>
		</tr>
	<c:forEach items="${pageUtil.list}" var="item" varStatus="i">
		<tr>
		<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
		<td><a class="a"  target="_blank" href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item.users.username)}">${myel:jieMiUsername(item.users.username)}</a></td> 
		<td>${item.totalPoint}</td>
	    <td>
	    <a href="javascript:updateStatus('${item.id}')" class="a" style="padding-right: 10px">清零</a>
	    	<a style="padding-right: 10px" class="a" href="${pageContext.request.contextPath}/Product/Admin/meowIncome!showMeowIncome.action?username=${myel:jieMiUsername(item.users.username)}">收入详情</a>
	    		<a class="a" href="${pageContext.request.contextPath}/Product/Admin/meowPay!showMeowPay.action?username=${myel:jieMiUsername(item.users.username)}">支出详情</a>
	    <td>
		</tr>
		</c:forEach>
	</table>
	
	
	<c:choose>
			<c:when test="${pageUtil.list ne '[]' &&  pageUtil.list ne '' && pageUtil.list ne null}"><jsp:include page="/Product/page.jsp" /></c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;">
			  		<img src="../images/no_record.png" />
			  	</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<script type="text/javascript">

function updateStatus(id){
	if(confirm("是否确定清零该用户积分？")){
		var url="${pageContext.request.contextPath}/Product/Admin/meowIncome!cleanMcoin.action?id="+id;
		 $.post(url,null,function(data){
			 if(data.status=="ok"){
					//验证码发送成功，跳转到验证页面
					alert(data.json);
					location.reload();
			 }else{
				 alert(data.json);
				 return false;
			 }
		 });
	}
}

</script>
</body>
</html>