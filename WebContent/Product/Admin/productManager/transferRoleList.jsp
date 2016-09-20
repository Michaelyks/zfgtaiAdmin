<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<meta charset="utf-8" />
<title>基金产品规则记录</title>
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
			<h3 align="center">基金产品规则记录</h3>
			<form action="${pageContext.request.contextPath}/Product/Admin/transferCostRate!transferRoleList.action">
				产品名称:<input type="text" name="productTitle" value="${productTitle}">
				状态：
				<select name="status">
					<option value="" selected="selected">全部</option>
					<option value="0">可用</option>
					<option value="1">不可用</option>
				 </select>
				<input type="submit" value="查询">
				<input type="button" value="导出报表" onclick="ireportDo()">
			</form>
				<div class="table">
					<table cellspacing="0" cellpadding="0" style="  width: 100%;">
						<tbody>
						<tr>
							<td style="width: 366px;">产品名称</td>
							<td style="width: 82px;">最小天数</td>
							<td style="width: 82px;">最大天数</td>
							<td style="width: 82px;">折损率</td>
							<td style="width: 82px;">状态</td>
						</tr>
						<c:forEach items="${list}"  var="item" varStatus="i">
							<tr>
								<td>${item.product.title}</td>
								<td>${item.minDate}</td>
								<td>
									${item.maxDate}
								</td>
								<td>
									${item.rate}%
								</td>
								<td>
									<c:choose>
										<c:when test="${item.status eq '0'}">可用</c:when>
										<c:otherwise>
											不可用
										</c:otherwise>
									</c:choose>
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
<div id="psi_load" style="display:none;">
<img width="18" src="${pageContext.request.contextPath}/Product/images/progress.gif"><span  id="psi_tip">正在导出,请稍等...</span>
</div>	
	</body> 
<script type="text/javascript">
	var k4 = new Kalendae.Input("finishTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
</script>
</html>