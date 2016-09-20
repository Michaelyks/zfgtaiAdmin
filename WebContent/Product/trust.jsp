<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 用户保障</title>
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
					<p class="g_title">用户保障</p>
					<div class="conta_us trust">
						<div class="trust_list">
							<h3>法律保障</h3>
							<p>公司为确保债权项目的真实、合法及有效性，所有债权项目形成过程中由：广东天穗律师事务所、广东鹏鼎律师事务所全程审核及见证，并由律师监管，为投资者利益提供多重保障。</p>
						</div>
						<div class="trust_list">
							<h3>账户保障</h3>
							<ul>
								<li>登录密码和交易密码的双重保护，重重保障账户信息及资金安全</li>
								<li>资金提现只能到本人名下银行卡，确保资金与投资人账户一一对应</li>
							</ul>
							<p>用户通过充值与提现形成的每一笔支付，都将通过第三方支付平台易宝支付有限公司完成，并对资金流转过程进行监管，并携手平安保险，为公司平台的个人投资者投保个人账户资金安全保险，最大限度确保用户资金安全。，为用户争取更多的权益和保障</p>
						</div>
						<div class="trust_list">
							<h3>隐私保障</h3>			
							<p>权限管理：中泰理财严格遵守国家相关的法律法规，对用户的隐私信息进行保护。未经用户本人同意，中泰理财不会向任何第三方公司、组织和个人披露您的个人信息、账户信息以及交易信息</p>
						</div>
						<div class="trust_list">
							<h3>数据保障</h3>
							<dt>网络安全</dt>
							<dd>专业的腾讯云服务器和专业的CDN服务；拥有ARP欺骗嗅探攻击,DDOS轰炸攻击,XSS蠕虫脚本攻击，SQL注入等攻击的防护功能；最大力度保障平台数据的安全性</dd>
							<dt>加密技术</dt>
							<dd>拥有SSL数字签名、安全套接协议等技术，数据在网络传输中完全加密，防止数据在通信过程中被截取或篡改，保证数据的安全性</dd>
							<dt>容灾备份</dt>
							<dd>生产、容灾两个数据中心，彼此同步，互为备份，所有信息数据自动加密传输，并且每个数据中心自身采用raid数组模式，确保历史数据永久保存。</dd>
						</div>
						
					</div>
				</div>	
				<div class="clea"></div>
			</div>
		</div>	
		<jsp:include page="footer.jsp" />
		<script>
			ab_left_memu(3);
		</script>
	</body>
</html>