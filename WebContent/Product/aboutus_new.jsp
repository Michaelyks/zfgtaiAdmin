<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 公司简介</title>
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
					<p class="g_title">公司简介</p>
					<div class="conta_us about_us">
						<p>深圳中泰理财金融服务有限公司成立于2015年，注册资本1000万元人民币，在职员工100余名，为中小企业和个人投资者提供高效，安全，便捷的P2P理财服务，管理团队成员均来自银行，法律，风控，互联网资深专业人士，拥有国内外从业相关经验，公司与合作的金融机构严格为投资者把控风险。树立独特竞争优势，实现全民理财梦想</p>
						<p>“中泰理财”携手平安保险，为公司平台的个人投资者投保个人账户资金安全保险，最大限度保障投资者的资金安全，为投资者争取更多的权益和保障</p>	
						<img class="aboutus_img" src="images/aboutus_img.jpg" />
					</div>
					<p class="g_title">公司资质</p>
					<div class="conta_us about_us2">
						<ul class="lun">
							<li class="li_1"><img class="aboutus_zhengshu" src="images/ab_zhengshu_0.jpg" /></li>
							<li class="li_1"><img class="aboutus_zhengshu" src="images/ab_zhengshu_1.jpg" /></li>
							<li class="li_1"><img class="aboutus_zhengshu" src="images/ab_zhengshu_2.jpg" /></li>
						</ul>
					</div>
				</div>	
				<div class="clea"></div>
			</div>
		</div>	
		<jsp:include page="footer.jsp" />
		<script>
			ab_left_memu(0);
			var lun;
			var i_ban = 0;
			var len = 0;
			lun = setInterval("ee()",5000);
			//banner轮播初始值
			$(function(){
				len = $(".lun li").length;
				var btn = "<div class='btn'><ul>";
				for(var i2=0; i2 < len; i2++){
					btn += "<li></li>";
				}
				btn += "</ul></div>";
				$(".lun").append(btn);
				$(".lun .btn li").removeClass("on").eq(i_ban).addClass("on");
				$(".lun .btn li").bind("mouseover",function(){
					i_ban = $(".lun .btn li").index(this);
					clearInterval(lun);
					ee();
				}).bind("mouseout",function(){
					lun = setInterval("ee()",5000);
				});
				
				$(".lun").bind("mouseover",function(){
					clearInterval(lun);
				}).bind("mouseleave",function(){
					lun = setInterval("ee()",5000);
				})
				
				ee();
				//banner轮播结束
			});
			function ee(){
					$(".lun li.aa").removeClass("aa").stop().animate({"opacity":"0","z-index":"0"},500,function(){jQuery(this).hide()});
					$(".lun .btn li").removeClass("on").eq(i_ban).addClass("on");
					$(".lun li").eq(i_ban).addClass("aa").show().stop().animate({"opacity":"1","z-index":"1"},500);
					i_ban++;
					if(i_ban>=len){
						i_ban=0;
					}
				}
			//banner轮播调用函数结束
			
		</script>
	</body>
</html>