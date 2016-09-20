<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 常见问题</title>
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" text="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/about_us.css" rel="stylesheet" text="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/about_help.css" rel="stylesheet" text="text/css"/>
	</head>
	
	<body>
		<jsp:include page="header.jsp" />
		<div id="ab_us_Help" class="Ab_bg">
			<div class="Ab_width1200">
				<ul id="aboutus_nav">
					<li><a id="nav0" href="about_us.jsp">关于我们</a></li>
					<li><a href="about_advantage.jsp">核心优势</a></li>
					<li><a href="about_security.jsp">安全保障</a></li>
					<li><a href="about_team.jsp">合作机构</a></li>
					<li class="on"><a href="about_help.jsp">常见问题</a></li>
					<li><a href="about_recruit.jsp">招才纳贤</a></li>
					<li><a href="about_announce.jsp">网站声明</a></li>
					<li><a id="nav4" href="about_contact.jsp">联系我们</a></li>			
				</ul>
				<div id="us_help" class="Ab_box980">
					<img src="./images/About_us_proble_banner.jpg"/>
					<ul  id="us_help_page1" class="Ab_ul" style="display:block;">
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							01、忘记用户名、密码怎么办？
							<div class="problem_answer" style="display:none;">
								<p>用户名即为您的手机号，登陆密码可以在网站首页"登陆"界面，点击"忘记密码"，按照系统提示操作即可。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							02、注册时手机收不到验证码怎么办？
							<div class="problem_answer" style="display:none;">
								<p>1.确认短信是否被手机软件拦截或过滤。</p>
								<p>2.确认手机是否能正常接收短信（信号问题、欠费、停机等）。</p>
								<p>3.短信收发过程中可能会有延时，请耐心等候。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							03、为什么要进行实名认证？
							<div class="problem_answer" style="display:none;">
								<p>为了提高账户安全等级，保障资金的安全性和合同的有效性，客户需在首次充值前完成实名登记,中泰理财承诺会对所有用户资料严格保密。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							04、为什么要绑定银行卡？
							<div class="problem_answer" style="display:none;">
								<p>绑定银行卡是第三方资金托管安全方式中重要的一环，也是实名认证的一种方式，资金流动在绑定银行卡内，对投资者提供了资金安全保障，同时对投资也较为方便。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							05、银行卡绑定后是否可更改？中泰理财支持信用卡充值吗？
							<div class="problem_answer" style="display:none;">
								<p>在绑定银行卡后，点击解绑然后联系客服人员，再重新绑定。银行卡的开户名与实名认证姓名必须一致，否则会导致提现失败。中泰理财暂不支持信用卡充值。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							06、充值金额范围、到账时间和手续费收取规则分别是怎样的？
							<div class="problem_answer" style="display:none;">
								<p>目前所支持的银行中除：建行（10万）交行（10万）工行（1万）单笔以外。其它所以支持的单笔限额均是5万元人民币，每天限额为30万元（招行20万、工行2万）。充值不收取手续费，实时到账。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							07、为什么银行已扣款，但平台账户余额没有增加？
							<div class="problem_answer" style="display:none;">
								<p>您的原因很很可能是下面的情况：银行已扣款，但是可能由于银行系统原因，资金没有同步到第三方支付平台，，这种情况您不用担心，如果长时间还没有到帐，请客服热线。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							08、提现需要多长时间到达银行账户？
							<div class="problem_answer" style="display:none;">
								<p>通过审核的提现申请，资金将于下一个工作日到达银行卡，具体到卡时间以银行处理为准。</p>
							</div>
						</li>	
					</ul>
					
					<ul  id="us_help_page2" class="Ab_ul" style="display:none;">
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							09、为什么提现申请会失败？
							<div class="problem_answer" style="display:none;">
								<p>1.账户余额不足。</p>
								<p>2.银行开户行信息错误。</p>
								<p>3.银行账号/户名错误，或是账号和户名不符。</p>
								<p>若遇到提现失败，千万别担心资金安全，及时联系客服电话：400-806-5993</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							10、提现次数和额度有限制吗？
							<div class="problem_answer" style="display:none;">
								<p>每天提现次数为一次，提现额度没有限额</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							11、投资项目已到期，收益什么时候发放？
							<div class="problem_answer" style="display:none;">
								<p>项目已到期，收益次日发放。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							12、哪些资金是可以提现的？提现需要手续费吗？
							<div class="problem_answer" style="display:none;">
								<p>账户中的可用余额可提现，提现无需任何手续费。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							13、平台如何确保交易的安全性？
							<div class="problem_answer" style="display:none;">
								<p>用户通过充值与提现形成的每一笔支付，都将通过第三方支付平台完成，并对资金流转过程进行监管，用户提现时只能将资金转入与本人实名认证信息一致的银行卡内，杜绝了他人冒充提现的可能性。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							14、资金放在中泰理财账户里安全吗？
							<div class="problem_answer" style="display:none;">
								<p>用户未投资的资金全部存放于第三方支付平台易宝支付有限公司，受到第三方支付平台的独立监管，确保用户账户资金安全。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							15、中泰理财平台上的合同、协议和条款这些都有法律效力吗？
							<div class="problem_answer" style="display:none;">
								<p>根据《合同法》第11条规定，当事人可以采用合同书、信件和数据电文等形式订立合同。电子合同是法律认可的书面合同形式之一。所有中泰理财平台上的电子合同（借款合同、协议和法律条款等）均具有法律效力，接受司法认可和保护。</p>
							</div>
						</li>
						<li class="problem_title">
							<img src="./images/About_us_proble_open.png"/>
							16、中泰理财提供的服务是否合法？
							<div class="problem_answer" style="display:none;">
								<p>中泰理财作为一家互联网金融信息服务平台，根据《合同法》相关规定，向借款人、投资人双方提供的中介信息服务完全合法合规。</p>
							</div>
						</li>	
					</ul>
				</div>
				<div id="help_page_box" class="Ab_box980">
						<ul class="help_page">
							<li class="last">上一页</li>
							<li value="1" id="p_label1" class="page on">1</li>
							<li value="2" id="p_label2" class="page">2</li>
							<li class="next">下一页</li>
						</ul>
				</div>
				
					<script>
						
						/* 翻页 */
						$("#help_page_box .page").click(function(){
							/*翻页时所有展开项全部收起*/
							var img1="./images/About_us_proble_open.png";
							$(".problem_title").children(".problem_answer").hide();
							$(".problem_title").children("img").attr("src",img1);
							
							var num=$(this).val();
							$("#help_page_box li").removeClass("on");
							$("#us_help>ul").hide();
							$("#us_help_page"+num).show();
							$(this).addClass("on");	
						});
						$("#help_page_box .last").click(function(){
							/*翻页时所有展开项全部收起*/
							var img1="./images/About_us_proble_open.png";
							$(".problem_title").children(".problem_answer").hide();
							$(".problem_title").children("img").attr("src",img1);
							
							var P=$("#help_page_box li[class*='on']");
							var num=P.prev("li").val();
							//console.log(P.prev("li").val());
							if(num!=0&&num!=undefined){
								$("#help_page_box li").removeClass("on");
								$("#us_help>ul").hide();
								$("#us_help_page"+num).show();
								$("#p_label"+num).addClass("on");
							}
						});
						$("#help_page_box .next").click(function(){
							/*翻页时所有展开项全部收起*/
							var img1="./images/About_us_proble_open.png";
							$(".problem_title").children(".problem_answer").hide();
							$(".problem_title").children("img").attr("src",img1);
							
							var P=$("#help_page_box li[class*='on']");
							var num=P.next("li").val();
							//console.log(P.next("li").val());
							if(num!=0&&num!=undefined){
								$("#help_page_box li").removeClass("on");
								$("#us_help>ul").hide();
								$("#us_help_page"+num).show();
								$("#p_label"+num).addClass("on");
							}
						});
						
						/*展开收起*/
						$(".problem_title").click(function(){
							var path=$(this).children("img").attr("src");
							var img1="./images/About_us_proble_open.png";
							var img2="./images/About_us_proble_close.jpg";
							console.log(path);
							if(path==img1){
								$(".problem_title").children(".problem_answer").slideUp(300);
								$(".problem_title").children("img").attr("src",img1);
								$(this).children(".problem_answer").slideDown(300);
								$(this).children("img").attr("src",img2);
							}else if(path==img2){
								$(this).children(".problem_answer").slideUp(300);
								$(this).children("img").attr("src",img1);
							}
							
						});
					</script>	
				
			</div>
		</div>
		
		<!--
			
		<div class="box" style="overflow:hidden;" id="help">
			<div id="ab_hep_f0">
				<div class="layout">
					<div class="fl">
						<img class="kefu" src="${pageContext.request.contextPath}/Product/images/recruit_f4.png" />
					</div>
					<div class="fr">
						<img src="${pageContext.request.contextPath}/Product/images/help_title1.png" />
						<p>用户名即为您的手机号，登录密码可以在网站首页“登录”界面，点击“忘记密码”，按系统提示操作即可。</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fl">
						<img src="${pageContext.request.contextPath}/Product/images/help_title2.png" />
						<p>（1）确认短信是否被手机软件拦截或过滤<br />（2）确认手机是否能正常接收短信（信号问题、欠费、停机等）<br />（3）短信收发过程中可能会有延时，请耐心等候</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fr">
						<img src="${pageContext.request.contextPath}/Product/images/help_title3.png" />
						<p>为了提高账户安全等级，保障资金的安全性和合同的有效性，客户需在首次充值前完成实名登记,中泰理财承诺会对所有用户资料严格保密。</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fl">
						<img src="${pageContext.request.contextPath}/Product/images/help_title4.png" />
						<p>绑定银行卡是第三方资金托管安全方式中重要的一环，也是实名认证的一种方式，资金流动在绑定银行卡内，对投资者提供了资金安全保障，同时对投资也较为方便。</p>
					</div>
				</div>
			</div>
			<div id="ab_hep_f1">
				<div class="layout">				
					<div class="fr">
						<img src="${pageContext.request.contextPath}/Product/images/help_title5.png" />
						<p>在绑定银行卡后，点击解绑然后联系客服人员，再重新绑定。银行卡的开户名与实名认证姓名必须一致，否则会导致提现失败。中泰理财暂不支持信用卡充值。</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fl">
						<img src="${pageContext.request.contextPath}/Product/images/help_title6.png" />
						<p>目前所支持的银行中除：建行（10万）交行（10万）工行（1万）单笔以外。其它所以支持的单笔限额均是5万元人民币，每天限额为30万元（招行20万、工行2万）。充值不收取手续费，实时到账。</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fr">
						<img src="${pageContext.request.contextPath}/Product/images/help_title7.png" />
						<p>您的原因很很可能是下面的情况：银行已扣款，但是可能由于银行系统原因，资金没有同步到第三方支付平台，，这种情况您不用担心，如果长时间还没有到帐，请客服热线。</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fl">
						<img src="${pageContext.request.contextPath}/Product/images/help_title8.png" />
						<p>通过审核的提现申请，资金将于下一个工作日到达银行卡，具体到卡时间以银行处理为准。</p>
					</div>
				</div>
			</div>
			<div id="ab_hep_f2">
				<div class="layout">				
					<div class="fr">
						<img src="${pageContext.request.contextPath}/Product/images/help_title9.png" />
						<p>1.账户余额不足。<br />2.银行开户行信息错误。<br />3.银行账号/户名错误，或是账号和户名不符。<br />若遇到提现失败，千万别担心资金安全，及时联系客<br />服电话：400-806-5993</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fl">
						<img src="${pageContext.request.contextPath}/Product/images/help_title10.png" />
						<p>每天提现次数为一次，提现额度没有限额</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fr">
						<img src="${pageContext.request.contextPath}/Product/images/help_title11.png" />
						<p>项目已到期，收益次日发放</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fl">
						<img src="${pageContext.request.contextPath}/Product/images/help_title12.png" />
						<p>账户中的可用余额可提现，提现无需任何手续费</p>
					</div>
				</div>
			</div>
			<div id="ab_hep_f3">
				<div class="layout">				
					<div class="fr">
						<img src="${pageContext.request.contextPath}/Product/images/help_title13.png" />
						<p>用户通过充值与提现形成的每一笔支付，都将通过第三方支付平台易宝支付有限公司完成，并对资金流转过程进行监管，并携手平安保险，为公司平台的个人投资者投保个人账户资金安全保险，最大限度确保用户资金安全。用户提现时只能将资金转入与本人实名认证信息一致的银行卡内，杜绝了他人冒充提现的可能性</p>
						<p>用户通过充值与提现形成的每一笔支付，都将通过第三方支付平台完成，并对资金流转过程进行监管，用户提现时只能将资金转入与本人实名认证信息一致的银行卡内，杜绝了他人冒充提现的可能性</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fl">
						<img src="${pageContext.request.contextPath}/Product/images/help_title14.png" />
						<p>用户未投资的资金全部存放于第三方支付平台易宝支付有限公司，受到第三方支付平台的独立监管，确保用户账户资金安全</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fr">
						<img src="${pageContext.request.contextPath}/Product/images/help_title15.png" />
						<p>根据《合同法》第11条规定，当事人可以采用合同书、信件和数据电文等形式订立合同。电子合同是法律认可的书面合同形式之一。所有中泰理财平台上的电子合同（借款合同、协议和法律条款等）均具有法律效力，接受司法认可和保护</p>
					</div>
				</div>
				<div class="layout">				
					<div class="fl">
						<img src="${pageContext.request.contextPath}/Product/images/help_title16.png" />
						<p>中泰理财作为一家互联网金融信息服务平台，根据《合同法》相关规定，向借款人、投资人双方提供的中介信息服务完全合法合规</p>
					</div>
				</div>
			</div>	
			<div class="bg_line" ></div>
		</div>	
		-->
		<jsp:include page="footer.jsp" />
	</body>
	
</html>
	
