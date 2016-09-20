<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 我的账户左边导航 -->
<div class="fl account_menu_l">
	<div class="menu_l" id="menu_l">
		<!-- 
		<h4 class="me_1">账户管理</h4>
		 -->
		<ul>
		<!-- 	<li class="l_munu_on">我的钱包</li>
			<li>我的投资劵</li>
			<li>充值</li>
			<li>提现</li>
			<li>资金流水</li> -->
			<li><a href="${pageContext.request.contextPath}/Product/User/myAccount!showMyAccount.action">我的账户</a></li>
			<li><a href="${pageContext.request.contextPath}/Product/User/investorsRecord!getInvestorsRecords.action">投资列表</a></li>
			<li><a href="${pageContext.request.contextPath}/Product/User/myCoupon!loadCoupon.action">我的券包</a></li>
			<li><a href="${pageContext.request.contextPath}/Product/User/fundRecord!loadFundRecord.action?type=all">资金流水</a></li>
			<li><a href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action">充值</a></li>
			<li><a href="${pageContext.request.contextPath}/Product/User/userRecharge!getMoney.action">提现</a></li>
			<li><a href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action">账户设置</a></li>
			
		</ul>
		<!-- 
		<h4 class="me_2">投资中心</h4>
		 -->
		<!-- 
		<ul>
            <li><a href="${pageContext.request.contextPath}/Product/User/investorsRecord!getInvestorsRunningRecords.action">流水记录</a></li>
		</ul>
		 -->
		<!-- 
		<h4 class="me_3">用户中心</h4>
		 -->
		<!-- 
		<ul>
			<li><a href="${pageContext.request.contextPath}/Product/User/userInfo!queryUsersInfoByid.action">基本资料</a></li>
            <li><a href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action">认证资料</a></li>
		</ul>
		 -->
	</div>
</div>
<!-- 我的账户左边导航 end-->