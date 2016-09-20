<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 我的账户左边导航 -->
<div class="fl account_menu_l">
	<div class="user_img">
		<img src="${pageContext.request.contextPath}/Product/images/user_img.png"/>
		<p>欢迎您</p>
	</div>
	<div class="menu_l" id="menu_l">
		<h4 class="me_1">账户管理</h4>
		<ul>
		<!-- 	<li class="l_munu_on">我的钱包</li>
			<li>我的投资劵</li>
			<li>充值</li>
			<li>提现</li>
			<li>资金流水</li> -->
			<li><a >我的钱包</a></li>
			<li><a >我的投资券</a></li>
			<li><a >充值</a></li>
			<li><a >提现</a></li>
			<li><a >资金流水</a></li>
		</ul>
		<h4 class="me_2">投资中心</h4>
		<ul>
			<li><a >投资记录</a></li>
            <li><a>流水记录</a></li>
		</ul>
		<h4 class="me_3">用户中心</h4>
		<ul>
			<li><a>基本资料</a></li>
            <li><a>认证资料</a></li>
		</ul>
	</div>
</div>
<!-- 我的账户左边导航 end-->