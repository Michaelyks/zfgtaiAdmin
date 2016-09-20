<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		<meta name="apple-touch-fullscreen" content="yes">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<title>全民理财狂欢季</title>
		<link rel="stylesheet" href="css/yy_dy.css" />
	</head>
	<body class="redbody">
		<div class="pic-box">
			<img src="img/header_img_01.png" alt="2015全民理财狂欢季 " />
		</div>
		<div class="pic-box">
			<img src="img/head_img2_02.png" alt="投资有惊喜" />
		</div>
		<div class="clear"></div>
		<div class="dy-center">
			<div class="titlt-ct">
				<img src="img/hdgl_03.png" alt=""活动概览 />
			</div>
			<div class="hdgl-word">
				为回馈广大用户长久以来的支持与厚爱，中泰理财现隆重推出“全民理财狂欢季 投资有惊喜”活动。即日起，中泰理财平台用户投资理财产品【贸易通-汽车配件】，即有机会获得丰厚礼品，满足条件即可参加每月8日的抽奖活动，中奖率百分百，最高可获5000元投资券，更有神秘大礼等你拿。
			</div>
			<div class="hdgl-ft"></div>
			<div class="titlt-ct">
				<img src="img/hdgz_03.png" alt="活动规则" />
			</div>
			<ul class="gz-list">
				<li><span class="num">1.</span>活动期间，中泰理财平台用户投资理财产品【贸易通-汽车配件】，按累计投资金额可获得相应奖励。</li>
				<li><span class="num">2.</span>本活动仅限投资【贸易通-汽车配件】，其他产品暂不参与本次活动。</li>
				<li><span class="num">3.</span>用户累计投资金额满1万元即可获得一次抽奖机会，将于每月8日进行抽奖（每人每月有1次抽奖机会）。</li>
				<li><span class="num">4.</span>如用户在每月8日前兑奖，则该兑奖份额清零，失去抽奖机会（中泰理财良心建议用户每月8日抽奖后再兑奖）。</li>
				<li><span class="num">5.</span>用户如需兑奖请与中泰理财客服联系400-806-5993。</li>
				<li><span class="num">6.</span>投资券、话费等奖品将于用户兑奖后三个工作日内发放，实物奖品将于五个工作日内以快递方式发放。</li>
				<li><span class="num">7.</span>本活动最终解释权归中泰理财所有。</li>
			</ul>
			<div class="titlt-ct"><img src="img/hdjp_03.png" alt="活动奖品等级分布" /></div>
			<ul class="ft-jp clearfix">
				<li><a href="#"><img src="img/hdjl_03.png" alt="" /></a></li>
				<li><a href="#"><img src="img/hdjl_05.png" alt="" /></a></li>
				<li><a href="#"><img src="img/hdjl_07.png" alt="" /></a></li>
				<li><a href="#"><img src="img/hdjl_12.png" alt="" /></a></li>
				<li><a href="#"><img src="img/hdjl_13.png" alt="" /></a></li>
				<li><a href="#"><img src="img/hdjl_14.png" alt="" /></a></li>
			</ul>
		</div>
		<c:if test="${param.type ne 1 }">
	<div class="dy-foot">
		<a href="#" class="dy-link"><img src="img/foot_btn_03.png" alt="立即投资" /></a>
	</div>
	</c:if>
	</body>
</html>
