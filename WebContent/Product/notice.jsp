<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 平台公告</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/repayment_tip.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<style type="text/css">
		.shenglve{
		  width: 710px;
		  white-space: nowrap;
		  overflow: hidden;
		  text-overflow: ellipsis;
		    display: inline-block;
		  }
		</style>
	</head>
	<body>
		<jsp:include page="header.jsp" />
		<jsp:include page="about_nav.jsp" />
		<div class="main">
			<div class="layout gonggao">
				<jsp:include page="notice_left_memu.jsp" />
				<div class="fr g_content">
					<p class="g_title">平台公告</p>
					<div class="pingtai_centent">
						<c:forEach items="${pageUtil.list}" var="list">
						<p>
							<a class="shenglve" href="${pageContext.request.contextPath}/Product/indexNotice!getNoticeById.action?noticeId=${list.id}#ab_us_f2"><c:if test="${list.isTop eq '1' }"><label style="color:red;">[置顶]</label>&nbsp;&nbsp;</c:if>${list.title}</a>
							<span><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd" /></span>
						</p>
						</c:forEach>
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
					<c:if test="${pageUtil.list eq null || pageUtil.list eq '[]' }">
					<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
		           	<img src="${pageContext.request.contextPath}/Product/images/no_record.png" />
		           	</div>
		           	</c:if>
					</div>
					<div style="text-align:center">
						<jsp:include page="page.jsp" />
					</div>
				<div class="clea"></div>
			</div>
		</div>	
	

		<jsp:include page="footer.jsp" />
		<script>
		
		ab_left_memu(1);
		</script>
	</body>
</html>