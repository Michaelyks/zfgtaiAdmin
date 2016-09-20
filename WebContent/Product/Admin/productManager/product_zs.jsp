<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<meta charset="utf-8" />
<title>正在发售的产品</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<style type="text/css">
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
<script type="text/javascript">
	function sq(id){
		if(confirm("是否把产品立即进入【已售罄】状态?")){
			$.ajax({ 
				url: "${pageContext.request.contextPath}/Product/Admin/releaseProduct!nowSq.action?product.id="+id,
				success: function(data){
		       		if(data.status=='ok'){
		       			alert("售罄成功");
		       			window.location.href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productZS.action";	
		       		}else{
		       			alert("售罄失败");
		       			window.location.href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productZS.action";	
		       		}
		      }});
		}
	}
</script> 		 
</head>
	<body>
		<div class="center">		
		<jsp:include page="/Product/Admin/common/head.jsp"/>	
			<div class="main" align="center">
			<h3 align="center">在售产品</h3>
			<form action="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productZS.action">
				产品名称:<input type="text" name="product.title" value="${product.title}">
				发布时间:<input id="insertTime" name="insertTime" type="text" value="${insertTime}">
				到期时间:<input  type="text"  id="finishTime" name="finishTime" value="${finishTime}">
				<input type="submit" value="查询">
			</form>
				<p>共有<span>${pageUtil.count}</span>个项目</p>
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
								<td>产品状态</td>
								<td>发布时间</td>
								<td>操作</td>
							</tr>
						<c:forEach items="${list}"  var="item" varStatus="i">
							<tr>
								<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
								<td>提交成功</td>
								<td>${item.title}</td>
								<td>
									${item.cplx}
								</td>
								<td><fmt:formatNumber value="${item.annualEarnings}" pattern="#.##" />%</td>
								<td><fmt:formatNumber value="${item.financingAmount * 0.01}" pattern="#,##0.##"/></td>
								<td><fmt:formatDate value="${item.finishTime}" pattern="yyy-MM-dd"/></td>
								<td>
									${item.cpzt}
								</td>
								<td><fmt:formatDate value="${item.insertTime}" pattern="yyy-MM-dd HH:mm:ss"/></td>
								<td><a class="a" href="javascript:sq('${item.id}');">立即进入售罄状态</a>|<br>
									<a class="a" target="_blank" href="${pageContext.request.contextPath}/Product/Admin/virtualInsRecord!toVirtualIns.action?productId=${item.id}">虚拟投资</a>
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
	</body>
	<script type="text/javascript">
var k4 = new Kalendae.Input("insertTime", {
	attachTo:document.body,
	months:2,//多少个月显示出来,即看到多少个日历
	mode:'range'
	/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
});
</script> 
<script type="text/javascript">
	var k4 = new Kalendae.Input("finishTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
</script>
</html>