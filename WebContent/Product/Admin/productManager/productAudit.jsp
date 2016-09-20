
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<meta charset="utf-8" />
<title>产品审核</title>
<title>产品审核</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css"/>
<style type="text/css">
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
<script type="text/javascript">
	function audit(id,productStatus){
		$.ajax({ 
			url: "${pageContext.request.contextPath}/Product/Admin/releaseProduct!auditProduct.action?product.id="+id+"&productStatus="+productStatus,
			success: function(data){
	       		if(data.status=='ok'){
	       			alert("审核成功");
	       			window.location.href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productAudit.action";
	       		}else{
	       			alert("审核失败");
	       		window.location.href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productAudit.action";	
	       		}
	      }});
	}
</script> 		 
</head>
	<body>
		<div class="center">		
		<jsp:include page="/Product/Admin/common/head.jsp"/>	
			<div class="main">
			<h3 align="center">产品审核记录</h3>
				<div class="table">
					<table cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td>序号</td>
								<td>发布状态</td>
								<td>产品名称</td>
								<td>产品类型</td>
								<td>年化收益</td>
								<td>项目总额</td>
								<td>到期时间</td>
								<td>发布时间</td>
								<td>操作</td>
							</tr>
						<c:forEach items="${list}"  var="item" varStatus="i">
							<tr>
								<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
								<td>未审核</td>
								<td>
									<c:choose>
										<c:when test="${item.productType eq '1' }">
											<a class="a"  href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!showProductDetailsFreshman.action?productId=${item.id}" target="_blank">${item.title}</a>
										</c:when>
										<c:when test="${item.productType eq '0' }">
											<a class="a" href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!showProductDetails.action?productId=${item.id}" target="_blank">${item.title}</a>
										</c:when>
										<c:otherwise>
											${item.title}
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									${item.cplx}
								</td>
								<td><fmt:formatNumber value="${item.annualEarnings}" pattern="#.##" />%</td>
								<td>
									<fmt:formatNumber value="${item.financingAmount * 0.01}" pattern="#,##0.##"/>
								</td>
								<td>${item.finishTime}</td>
								<td>${item.insertTime}</td>
								<td>
									<%-- <a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!auditProduct.action?product.id=${item.id}&productStatus=0">通过</a>|<a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!auditProduct.action?product.id=${item.id}&productStatus=-2">不通过</a> --%>
									<a href="javascript:audit('${item.id}','0')">通过</a>|<a href="javascript:audit('${item.id}','-2')">不通过</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<c:choose>
	<c:when test="${list ne '[]' &&  list ne '' && list ne null}">
		<%-- <jsp:include page="/Product/page.jsp" /> --%></c:when>	
		<c:otherwise>
			<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
	  			<img src="../images/no_record.png" />
	 	 	</div>
		</c:otherwise>
	</c:choose>
				</div>
			</div>
		</div>	
	</body>
</html>