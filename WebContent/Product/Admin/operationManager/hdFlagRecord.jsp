<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动记录表</title>
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
<script type="text/javascript">

function modifyHdFlag(id){
	if(confirm("是否确定结束该活动？")){
		var url="${pageContext.request.contextPath}/Product/Admin/hdFlag!modifyHdFlag.action?id="+id;
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

</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>活动记录表</h3>

	<table border="1" cellspacing="0" cellpadding="0" style="text-align: center;">
		<tr>
			<td width="100px;">标识符</td>
			<td width="100px;">状态</td>
			<td width="200px;">开始时间</td>
			<td width="200px;">结束时间</td>
			<td width="200px;">操作时间</td>
			<td width="200px;">备注</td>
			<td width="200px;">操作</td>
			
			
		</tr>
 		<c:forEach items="${list}" var="item">
			<tr>
				<td>${item.flag}</td>
				<td>${item.statusChina}</td>
				<td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${item.note}</td>
				<td>
				<c:if test="${item.status eq '0'}">
				    <a id="modify_${item.id}" class="a" href="javascript:modifyHdFlag('${item.id}');">结束活动</a>
				</c:if>
				</td>
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

<!-- <script type="text/javascript">
	var k4 = new Kalendae.Input("insertTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
	 $(function(){
		$("#isbindbank option[value='${isbindbank}']").attr("selected",true);
	 });
</script> -->
</body>
</html>