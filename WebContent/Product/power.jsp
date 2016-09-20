<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 核心优势</title>
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
					<p class="g_title">核心优势</p>
					<div class="conta_us power">
						<div class="power_list">
							<h3>高收益低门槛</h3>
							<p>我们通过与合作持牌的金融机构推荐的优质项目中为广大投资者筛选出远高于银行年化3%~4%的投资收益，让投资者实现通过短期的理财获得高达年化9%~16%的投资收益，并且真正实现低门槛即可参与投资理财。</p>
						</div>
						<div class="power_list">
							<h3>安全保障</h3>
							<p>用户通过充值与提现形成的每一笔支付，都将通过第三方支付平台易宝支付有限公司完成，并对资金流转过程进行监管，此外公司携手平安保险，为公司平台的用户投保个人账户资金安全保险，最大限度确保用户资金安全。平台技术采用了腾讯云服务加密系统，保障用户的交易信息及个人信息。</p>
						</div>
						<div class="power_list">
							<h3>专业透明</h3>
							<p>中泰理财拥有自己严谨的风控团队，对债权投资项目进行严格的审核，进行信息调查，确保投资者在投资过程中每一个环节的安全，降低最小的风险。</p>
						</div>
						<div class="power_list">
							<h3>法律保障</h3>
							<p>公司为确保债权项目的真实、合法及有效性，所有债权项目形成过程中由：广东天穗律师事务所、广东鹏鼎律师事务所全程审核及见证，并由律师监管，为投资者利益提供多重保障。</p>
						</div>
						<div class="power_list">
							<h3>贴心服务</h3>
							<p>你的需求，我们服务，全员为您解决。</p>
							<p>确保高收益的同时，为你降低最小的风险。</p>
							<p>您的梦想，是我们一直追求实现的目标。</p>
						</div>
						
						
					</div>
				</div>	
				<div class="clea"></div>
			</div>
		</div>	
		<jsp:include page="footer.jsp" />
		<script>
			ab_left_memu(2);
		</script>
	</body>
</html>