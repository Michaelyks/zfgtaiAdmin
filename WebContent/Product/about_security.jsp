<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 安全保障</title>
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" text="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/about_us.css" rel="stylesheet" text="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/about_security.css" rel="stylesheet" text="text/css"/>
	</head>
	
	<body>
		<jsp:include page="header.jsp" />
		<div id="ab_us_Security" class="Ab_bg">
			<div class="Ab_width1200">
				<ul id="aboutus_nav">
					<li><a id="nav0" href="about_us.jsp">关于我们</a></li>
					<li><a href="about_advantage.jsp">核心优势</a></li>
					<li class="on"><a href="about_security.jsp">安全保障</a></li>
					<li><a href="about_team.jsp">合作机构</a></li>
					<li><a href="about_help.jsp">常见问题</a></li>
					<li><a href="about_recruit.jsp">招才纳贤</a></li>
					<li><a href="about_announce.jsp">网站声明</a></li>
					<li><a id="nav4" href="about_contact.jsp">联系我们</a></li>			
				</ul>
				<div id="us_security" class="Ab_box980">
					<img src="./images/About_us_securit_banner.jpg"/>
					<ul class="Ab_ul">
						<li>
							<div class="Ab_div510">
								<p class="Ab_p36">法律保障</p>
								<p class="Ab_p18">公司为确保债权项目的真实、合法及有效性，所有债权项目形成过程中由：广东天穗律师事务所、广东鹏鼎事务所全程审核及见证，并由律师监管，为投资者利益提供多重保障。</p>
							</div>
							<img src="./images/About_us_security1.jpg" class="img_rt" style="right:20px;"/>
						</li>
						<li>
							<img src="./images/About_us_security2.jpg"/>
							<div class="Ab_div510 fr">
								<p class="Ab_p36">账户保障</p>
								<p class="Ab_p18">针对用户的登陆密码和交易密码实施双重保护，重重保障账户信息及资金安全；</p>
								<p class="Ab_p18">账户资金体现只能到本人名下银行卡，确保资金与投资人账户一一对应。</p>
							</div>	
						</li>
						<li>
							<div class="Ab_div510">
								<p class="Ab_p36">数据保障——网络安全</p>
								<p class="Ab_p18">专业的阿里云服务器和专业的CDN服务；拥有APP欺骗嗅探攻击，DDOS轰炸攻击，XSS蠕虫脚本攻击，SQL注入等攻击的防护功能；最大力度保障平台数据的安全性。</p>
							</div>
							<img src="./images/About_us_security3.jpg" class="img_rt" style="right:20px;"/>
						</li>
						<li>
							<img src="./images/About_us_security4.jpg"/>
							<div class="Ab_div510 fr">
								<p class="Ab_p36">数据保障——加密技术</p>
								<p class="Ab_p18">拥有SSL数字签名、安全套接协议等技术，数据在网络传输中安全加密，防止数据在通信过程中被截取或篡改，保证数据的安全性。</p>
							</div>	
						</li>
						<li style="border-bottom:none;">
							<div class="Ab_div510">
								<p class="Ab_p36">数据保障——容灾备份</p>
								<p class="Ab_p18">生产。容灾两个数据中心，彼此同步，互为备份，所有信息数据自动加密传输，并且每个数据中心自身采用raid数组模式，确保历史数据永久保存。</p>
							</div>
							<img src="./images/About_us_security5.jpg" class="img_rt" style="right:20px;"/>
						</li>
					</ul>
				</div>
			</div>
		
		</div>
		
		<!--
		<div id="ab_sec_f0">
			<div class="layout">
				<div class="fl">
					<h1>法律保障</h1>
					<p>公司为确保债权项目的真实、合法及有效性，所有债权项目形成过程中由：广东天穗律师事务所、广东鹏鼎律师事务所全程审核及见证，并由律师监管，为投资者利益提供多重保障。</p>
				</div>
				<div class="fr">
					<img src="${pageContext.request.contextPath}/Product/images/security_f0.png" />
				</div>
				<div class="clea"></div>
			</div>	
		</div>
		<div id="ab_sec_f1">
			<div class="layout">
				<div class="fl">
					<img src="${pageContext.request.contextPath}/Product/images/security_f1.png" />
				</div>
				<div class="fr">
					<h1>账户保障</h1>
					<p><i class="in_display"></i>登录密码和交易密码的双重保护，重重保障账户信息及资金安全</p>
					<p><i class="in_display"></i>资金提现只能到本人名下银行卡，确保资金与投资人账户一一对应</p>
				</div>
				<div class="clea"></div>
			</div>	
		</div>
		<div id="ab_sec_f2">
			<h1>数据保障</h1>
			<img src="${pageContext.request.contextPath}/Product/images/security_f2.png"/>
			<ul>
				<li>
					<p>网络安全</p>
					<span>专业的阿里云服务器和专业的CDN服务；拥有ARP欺骗嗅探攻击, DDOS轰炸攻击，XSS蠕虫脚本攻击,SQL注入等攻击的防护功能；最大力度保障平台数据的安全性</span>
				</li>
				<li>
					<p>加密技术</p>
					<span>拥有SSL数字签名、安全套接协议等技术,数据在网络传输中完全加密，防止数据在通信过程中被截取或篡改，保证数据的安全性</span>
				</li>
				<li>
					<p>容灾备份</p>
					<span>生产、容灾两个数据中心，彼此同步，互为备份，所有信息数据自动加密传输，并且每个数据中心自身采用raid数组模式，确保历史数据永久保存</span>
				</li>
			</ul>
		</div>
		-->
		<jsp:include page="footer.jsp" />
	</body>
	
</html>
	
