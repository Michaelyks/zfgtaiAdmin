<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>
<meta charset="utf-8" />
<title>角色列表</title>
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
</head>
	<body>
		<div class="center">		
		<jsp:include page="/Product/Admin/common/head.jsp"/>	
			<div class="main" align="center">
			<h3 align="center">角色列表</h3>
				<div class="table">
					<table cellspacing="0" cellpadding="0" style="  width: 100%;">
						<tbody>
						<tr>
							<td style="width: 366px;">角色名称</td>
							<td style="width: 366px;">创建时间</td>
							<td style="width: 58px;">创建人</td>
							<td style="width: 366px;">备注</td>
							<td style="width: 366px;">状态</td>
						</tr>
						<c:forEach items="${list}"  var="item" varStatus="i">
						<tr>
								<td>${item.roleName}</td>
								<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
									${myel:jieMiUsername(item.usersAdmin.username)}
								</td>
								<td>
									${item.note}
								</td>
								<td>
									${item.statusChina}
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<c:choose>
	<c:when test="${list ne '[]' &&  list ne '' && list ne null}">
		</c:when>
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