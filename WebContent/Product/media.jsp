<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
     <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 媒体报道</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/repayment_tip.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
		<jsp:include page="header.jsp" />
		<jsp:include page="about_nav.jsp" />
		<div class="main">
			<div class="layout gonggao">
				<jsp:include page="notice_left_memu.jsp" />
				<div class="fr g_content">
					<p class="g_title">媒体报道</p>
					<div class="media_content">
					<c:forEach items="${pageUtil.list}" var="list">
						<div class="midia_list">
							<h4 style="width:600px;"><i class="red_point"></i><a href="${pageContext.request.contextPath}/Product/indexNotice!getMediaById.action?noticeId=${list.id}#ab_us_f2" title="${list.title}">${list.title}</a></h4>
							<p><a href="${pageContext.request.contextPath}/Product/indexNotice!getMediaById.action?noticeId=${list.id}#ab_us_f2">${list.description}<c:if test="${list.description eq null}">点击查看详情</c:if></a></p>
							<span><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd" /></span>
							<c:choose>
								<c:when test="${list.titleUrl!='' && list.titleUrl!=null }">
									<img src="${systemConfig.httpUrl}${systemConfig.fileName}/web_img/notice/slt/${list.titleUrl}" width="209" height="96"/>
								</c:when>
								<c:otherwise>
									<img src="${pageContext.request.contextPath}/Product/images/notice_default.jpg" width="209" height="96"/>
								</c:otherwise>
							</c:choose>
						</div>
					</c:forEach>
					<c:if test="${pageUtil.list eq null || pageUtil.list eq '[]' }">
					<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
		           	<img src="${pageContext.request.contextPath}/Product/images/no_record.png" />
		           	</div>
		           	</c:if>
					</div>
					<!-- <ul id="pageUl">
						<li class='btn first'><a style='cursor:pointer;' >首页</a></li>
						<li class="prev"><a style='cursor:pointer;'>上一页</a></li>
						<li><a style='cursor:pointer;'>1</a></li>
						<li><a style='cursor:pointer;'>2</a></li>
						<li><a style='cursor:pointer;'>3</a></li>
						<li><a style='cursor:pointer;'>4</a></li>
						<li><a style='cursor:pointer;'>5</a></li>
						<li class="next"><a style='cursor:pointer;'>下一页</a></li>
						<li class="last"><a style='cursor:pointer;'>末页</a></li>
					</ul> -->
		           	<div style="text-align:center; margin-bottom: 20px;" >
						<jsp:include page="page.jsp" />
					</div>
				</div>
				<div class="clea"></div>
			</div>
		</div>	
	

		<jsp:include page="footer.jsp" />
		<script>
		
		ab_left_memu(0);
		</script>
	</body>
</html>