<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 四重保障</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/common.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/security_four.css" type="text/css" />		
	</head>
	
	<body>
	<jsp:include page="header.jsp" />
		<div id="head_img">
			<div class="layout">
				<img src="${pageContext.request.contextPath}/Product/images/baoz_img.jpg" />
			</div>
		</div>
		<div id="baoz_one">
			<div class="layout min-height">
				<h3>1.法律保障</h3>
				<p>公司为确保债权项目的真实、合法及有效性，所有债权项目形成过程中由：广东天穗律师事务所、广东鹏鼎律师事务所全程审核及见证，并由律师监管，为投资者利益提供多重保障。</p>
			</div>
		</div>
		<div id="baoz_tow">
			<div class="layout min-height">
				<h3>2.账户保障</h3>
				<p>登录密码和交易密码的双重保护，重重保障账户信息及资金 安全资金提现只能到本人名下银行卡，确保资金与用户账户 一一对应。 </p>
			</div>
		</div>
		<div id="baoz_three">
			<div class="layout min-height">
				<h3>3.隐私保障</h3>
				<p>权限管理保障：中泰理财严格遵守国家相关的法律法规，对用户的隐私信息进行保护。未经用户本人同意，中泰理财不会向任何第三方公司、组织和个人披露您的个人信息、账户信息以及交易信息。</p>
			</div>
		</div>
		<div id="baoz_four">
			<div class="layout min-height">
				<h3>4.数据保障</h3>
				<p>网络安全: 专业的腾讯云服务器和专业的CDN服务；拥有ARP欺骗嗅探攻击,DDOS轰炸攻击,XSS蠕虫脚本攻击，SQL注入等攻击的防护功能；最大力度保障平台数据的安全性</p>
				<p>加密技术: 拥有SSL数字签名、安全套接协议等技术，数据在网络传输中完全加密，防止数据在通信过程中被截取或篡改，保证数据的安全性。</p>
				<p>容灾备份:  生产、容灾两个数据中心，彼此同步，互为备份，所有信息数据自动加密传输，并且每个数据中心自身采用raid数组模式，确保历史数据永久保存。</p>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</body>
</html>