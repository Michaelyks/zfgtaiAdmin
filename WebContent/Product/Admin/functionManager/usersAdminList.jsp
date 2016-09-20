<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>
<meta charset="utf-8" />
<title>管理员列表</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
<style type="text/css">
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
<script type="text/javascript">
 
 //禁用或启用
 function toChange(obj){
	 if(confirm("你确定要修改当前状态?")){
	     var id="button_"+obj;
		 var url="${pageContext.request.contextPath}/Product/Admin/usersAdmin!changeStatus.action?id="+obj;
			$.post(url,null,function(data){
				 if("ok"==data.status){				 
					 $("#"+id).val(data.json);
				 }else if("error"==data.status){			 
					 	return false;
				 }
			 }); 
	 }else{
		 return false;
	 }

 }
</script>	 
</head>
	<body>
		<div class="center">		
		<jsp:include page="/Product/Admin/common/head.jsp"/>	
			<div class="main" align="center">
			<h3 align="center">管理员列表</h3>
				<div class="table">
					<table cellspacing="0" cellpadding="0" style="  width: 100%;">
						<tbody>
						<tr>
							<td style="width: 366px;">用户名</td>
							<td style="width: 366px;">创建时间</td>
							<td style="width: 366px;">所属部门</td>
                            <td style="width: 366px;">用户类型</td>
                            <td style="width: 58px;">创建人</td>							
							<td style="width: 366px;">当前状态</td>
							<td style="width: 366px;">拥有权限</td>
							<td style="width: 366px;">操作</td>
						</tr>
						<c:forEach items="${list}"  var="item" varStatus="i">
						<tr>
								<td>${myel:jieMiUsername(item.username)}</td>
								<td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>                                
                                <td>
								   ${item.dept.deptName}
								</td>
								<td style="width: 366px;">${item.typeChina}</td>
								<td>
									${myel:jieMiUsername(item.usersAdmin.username)}
								</td>
								<td>
								<c:if test="${item.userType ne '-1'}">
							      <input type="button" id="button_${item.id}" value="<c:if test="${item.userStatus eq '0'}">已可用</c:if><c:if test="${item.userStatus ne '0'}" >已禁用</c:if>"
								     onclick="toChange('${item.id}');">
								   </c:if>  
								</td>
								<td>
									<c:choose>
										<c:when test="${item.userType eq '-1'}">
											所有权限
										</c:when>
										<c:otherwise>
											${map[item.id]}
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<a href="${pageContext.request.contextPath}/Product/Admin/distributePermission!toDistributePermission.action?userAdminId=${item.id}" class="a">分配权限</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
        <c:choose>
			<c:when test="${list ne '[]' &&  list ne '' && list ne null}">
			     <jsp:include page="/Product/page.jsp" /></c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
			  		<img src="../images/no_record.png" />
			  	</div>
			</c:otherwise>
		</c:choose>
				</div>
			</div>
		</div>
</html>