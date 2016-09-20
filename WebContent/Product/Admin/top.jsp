<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
<!--
	.topLi{
		list-style: none;
		padding: 0px;
		display: block;
	}
	.topLi li{
		float: left;
		margin-left: 20px;
	}
-->
</style>
<p style="text-align: center;margin-top: 20px;">推荐使用谷歌浏览器(Chrome)进行操作</p>
<div style="height: 60px;margin-top: 50px;">
<ul class="topLi">
	<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!sendProduct.action">发布常规产品</a></li>
	<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseFreshmanProduct!sendProduct.action">发布新手产品</a></li>
	<li><a href="${pageContext.request.contextPath}/Product/Admin/recharge.jsp">用户充值</a></li>
	<li><a href="${pageContext.request.contextPath}/Product/Admin/sendCoupon.jsp">发送投资券</a></li>
	<li><a href="${pageContext.request.contextPath}/Product/Admin/checkTxsq!loadTxsq.action?status=all">审核提现</a></li>
	<li><a href="${pageContext.request.contextPath}/Product/Admin/recharge!rechargeMoneyRecord.action?status=all">充值记录</a></li>
	<li><a href="${pageContext.request.contextPath}/Product/Admin/function.jsp">功能操作</a></li>
	<li><a href="${pageContext.request.contextPath}/Product/loginBackground.jsp">登录</a></li>
	<li><a href="${pageContext.request.contextPath}/Product/Admin/notice.jsp">发布公告</a></li>
</ul>
</div>
<p></p>