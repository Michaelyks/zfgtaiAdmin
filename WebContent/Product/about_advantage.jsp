<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 核心优势</title>
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/about_us.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/about_advantage.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body>
		<jsp:include page="header.jsp" />
		
		<div id="ab_us_Advantage" class="Ab_bg">
			<div class="Ab_width1200">
				<ul id="aboutus_nav">
					<li><a id="nav0" href="about_us.jsp">关于我们</a></li>
					<li class="on"><a href="about_advantage.jsp">核心优势</a></li>
					<li><a href="about_security.jsp">安全保障</a></li>
					<li><a href="about_team.jsp">合作机构</a></li>
					<li><a href="about_help.jsp">常见问题</a></li>
					<li><a href="about_recruit.jsp">招才纳贤</a></li>
					<li><a href="about_announce.jsp">网站声明</a></li>
					<li><a id="nav4" href="about_contact.jsp">联系我们</a></li>			
				</ul>
				<div id="us_advantage" class="Ab_box980">
					<img src="./images/About_us_advantage_banner.jpg"/>
					<ul class="Ab_ul">
						<li>
							<img src="./images/About_us_advantage1.jpg"/>
							<div class="Ab_div510 fr">
								<p class="Ab_p36">低门槛高收益</p>
								<p class="Ab_p18">我们通过与合作持牌的金融机构推荐的优质项目中为广大投资者筛选出远高于银行年化3%~4%的投资收益，让投资者实现通过短期的理财获得高达年化9%~16%的投资收益，并且真正实现低门槛即可参与投资理财。</p>
							</div>
						</li>
						<li>
							<div class="Ab_div510">
								<p class="Ab_p36">安全保障</p>
								<p class="Ab_p18">用户通过充值与提现形成的每一笔支付，都将通过第三方支付平台完成，并对资金流转过程进行监管，平台技术采用了阿里云服务加密系统，保障用户的交易信息及个人信息。</p>
							</div>
							<img src="./images/About_us_advantage2.jpg" class="img_rt"/>
						</li>
						<li>
							<img src="./images/About_us_advantage3.jpg"/>
							<div class="Ab_div510 fr">
								<p class="Ab_p36">专业风控团队</p>
								<p class="Ab_p18">中泰理财拥有自己严谨的风控团队，对债权投资项目进行严格的审核，进行信息调查，确保投资者在投资过程中每一个环节的安全，降低最小的风险。</p>
							</div>
						</li>
						<li>
							<div class="Ab_div510">
								<p class="Ab_p36">贴心服务</p>
								<p class="Ab_p18">实时追踪账户安全，为您打造安全、专业、高效、便捷的金融服务平台，客服热线一对一在线答疑！您的梦想，是我们一直追求实现的目标。</p>
								<p class="Ab_p18">服务热线：400-806-5993</p>
								<p class="Ab_p18">官方微信：zhongtailicai</p>
							</div>
							<img src="./images/About_us_advantage4.png" class="img_rt"/>
						</li>
					</ul>
					
				</div>
			</div>
		<!-- 
			<div class="layout">
				<div class="fl">
					<h1><span class="colo">低</span>门槛<span class="colo">高</span>收益</h1>
					<p>我们通过与合作持牌的金融机构推荐的优质项目中为广大投资者筛选出远高于银行年化3%~4%的投资收益，让投资者实现通过短期的理财获得高达年化9%~16%的投资收益，并且真正实现低门槛即可参与投资理财。</p>
				</div>
				<div class="fr">
					<img src="${pageContext.request.contextPath}/Product/images/advantage_f0.png" />
				</div>
				<div class="clea"></div>
			</div>	
		</div>
		<div id="ab_adv_f1">
			<div class="layout">
				<div class="fl">
					<img src="${pageContext.request.contextPath}/Product/images/advantage_f1.png" />
				</div>
				<div class="fr">
					<h1>安全保障</h1>
					<p>
						用户通过充值与提现形成的每一笔支付，都将通过第三方支付平台完成，并对资金流转过程进行监管，平台技术采用了阿里云服务加密系统，保障用户的交易信息及个人信息。
					</p>
				</div>
				<div class="clea"></div>
			</div>	
		</div>
		<div id="ab_adv_f2">
			<div class="layout">
				<div class="fl">
					<h1>专业风控团队</h1>
					<p>中泰理财拥有自己严谨的风控团队<br/>对债权投资项目进行严格的审核<br/>进行信息调查<br/>确保投资者在投资过程中每一个环节的安全<br/>降低最小的风险</p>
				</div>
				<div class="fr">
					<img src="${pageContext.request.contextPath}/Product/images/advantage_f2.png" />
				</div>
				<div class="clea"></div>
			</div>	
		</div>
		<div id="ab_adv_f3">
			<div class="layout">
				<div class="fl">
					<img src="${pageContext.request.contextPath}/Product/images/advantage_f3.png" />					
				</div>
				<div class="fr">
					<h1>贴心服务</h1>
					<p>
					实时追踪账户安全<br/>客服热线一对一在线答疑<br/>您的梦想，是我们一直追求实现的目标<br/>
					服务热线 400-806-5993<br/>
					官方微信 baiyimao
					</p>
				</div>
				<div class="clea"></div>
			</div>	
		-->
		</div>
		<jsp:include page="footer.jsp" />
	</body>
	
</html>
	
