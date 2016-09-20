<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 联系我们</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/repayment_tip.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
		<jsp:include page="header.jsp" />
		<div class="main">
			<div class="layout gonggao">
				<jsp:include page="notice_left_memu.jsp" />
				<div class="fr g_content">
					<p class="g_title">联系我们</p>
					<div class="conta_us">
						<!--  <h2 class="conta_us_title">深圳中泰理财金融服务有限公司</h2>-->
						<ul class="conta_center">
						<%--  	<li><strong>办公地址：</strong><span>深圳市罗湖区桂园街道深南中路与和平路交汇处鸿隆世纪广场A座26A</span></li>
							<li><strong>推广合作：</strong><span>XXXXXXX</span></li>
							<li><strong>市场合作：</strong><span>XXXXXXX</span></li>
							<li>
								<strong>客服电话：</strong><span>400-806-5993  0755-33255530</span>
								<ul class="conta_kf_time">
									<li>早09:00-晚24:00 （工作日）</li>
									<li>早09:00-晚18:00 （节假日）</li>
								</ul>					
							</li>
							<li><strong>联系邮箱：</strong><span>XXXXXXX</span></li>--%>
							<li><strong>办公地址：</strong><span>深圳市罗湖区桂园街道和平路3001鸿隆世纪广场A座26A【老街地铁口B出口直走穿过天桥即可看到鸿隆世纪广场】</span></li>
							<li><strong>客服热线：</strong><span>400-806-5993&nbsp;&nbsp;&nbsp;&nbsp;0755-33255530</span></li>
							<li><strong>服务时间：</strong><span>9:00-18:00</span></li>
							<li><strong>客服邮箱：</strong><span>baiyimao@baiyimao.com</span></li>
							<li><strong>官方微信：</strong><span>baiyimao</span></li>
						</ul>
						<div>
							<img class="conta_bem_adr" src="${pageContext.request.contextPath}/Product/images/bem_address.png" alt="公司地址"/>
						</div>
						
					</div>
				</div>	
				<div class="clea"></div>
			</div>
		</div>	
		<jsp:include page="footer.jsp" />
		<script>
			ab_left_memu(9);
		</script>
	</body>
</html>