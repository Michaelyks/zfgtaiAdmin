<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  errorPage="page_404.jsp" %>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<title>中泰理财 - 我的账户</title>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<link href="../css/account.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../js/base.js"></script>
</head>

<body>
<jsp:include page="../top.jsp" />
<div class="lujin"><div class="crumbs"><a href="#"><img src="../images/home_ico.png" /></a> &gt; <a href="#">我的账户</a> &gt; <a href="#">帐户管理</a> &gt; <em>我的钱包</em></div></div>
<div class="maincnt wrap cf">
	<jsp:include page="leftMenu.jsp" />
    <div class="mainright fr">
        <div class="account_info">
        	<div class="level">
            	<label>我的账户安全等级：</label>
            	<c:choose>
            	<c:when test="${safeLevel ==30}">
            		<p><i style="width: 100%"></i></p>
                	<span>高</span>
                </c:when>
                <c:when test="${safeLevel ==20}">
            		<p><i style="width: 60%"></i></p>
                	<span>中</span>
                </c:when>
                <c:otherwise>
            		<p><i></i></p>
                	<span>低</span>
                </c:otherwise>
                </c:choose>
            </div>
            <div class="safe cf">
            	<ul>
                	<li class="safe1">
                	<c:choose>
                		<c:when test="${users.usersInfo.isVerifyPhone ==1}">
                    		<h4>手机</h4>
                    		<p class="yet">已绑定</p>
                    	</c:when>
                    	<c:otherwise>
                    		<h4>手机<a href="${pageContext.request.contextPath}/Product/User/userInfo!queryUsersInfoByid.action">(立即绑定)</a></h4>
                    		<p class="not">未绑定</p>
                    	</c:otherwise>
                    </c:choose>    
                    </li>
                   <%--  <li class="safe2">
                    	<c:choose>
                    		<c:when test="${users.usersInfo.isVerifyEmail ==1}">
                    			 <h4>邮箱</h4>
                    			<p class="yet">已绑定</p>
                    		</c:when>
                    		<c:otherwise>
                    			<h4>邮箱<a href="${pageContext.request.contextPath}/Product/User/userInfo!queryUsersInfoByid.action">(立即绑定)</a></h4>
                    			<p class="not">未绑定</p>
                    		</c:otherwise>
                    	 </c:choose>
                    </li> --%>
                    <li class="safe3">
                    <c:choose>
                    	<c:when test="${users.usersInfo.isVerifyIdcard ==1}">
                    		<h4>实名认证&银行卡绑定</h4>
                    		<p class="yet">已绑定认证</p>
                    	</c:when>
                    	<c:otherwise>
                    		<h4>实名认证<a href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action">(立即认证)</a></h4>
                    		<p class="not">未认证</p>
                    	</c:otherwise>
                    </c:choose>
                    </li>
                    <li class="safe4">
                    	<h4>密码安全</h4>
                    	<p class="yet">已修改</p>
                    </li>
                </ul>
            </div>
        </div>
        <div class="account_asset cf">
        	<div class="asset_l fl">
            	<h2>账户总资产</h2>
                <ul>
                	<li class="sum">
                        <a href="${pageContext.request.contextPath}/Product/User/userRecharge!getMoney.action" class="tx">提现</a>
                        <a href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action" class="cz">充值</a>
                        <span><b><fmt:formatNumber value="${users.usersInfo.leftMoney * 0.01 + freezeMoney + coupon}" pattern="0.00"/></b> 元</span>
                    </li>
                    <li>
                    	<!-- <a href="#">出借收息</a> -->
                        <label><span>可用余额</span> <i></i></label>
                        <p><fmt:formatNumber value="${users.usersInfo.leftMoney * 0.01}" pattern="0.00"/> 元</p>
                        <div class="tips"><span></span>可用余额，用于购买平台的产品</div>
                    </li>
                    <li>
                    	<!-- <a href="#">赎回</a> -->
                        <label><span>冻结金额</span> <i></i></label>
                        <p><fmt:formatNumber value="${freezeMoney}" pattern="0.00"/> 元</p>
                        <div class="tips"><span></span>包括 已购买营销中的产品总额 和 提现申请 的金额</div>
                    </li>
                    <!-- <li>
                    	<a href="#">查看利息</a>
                        <label><span>已出借</span> <i></i></label>
                        <p>20000.00元</p>
                        <div class="tips"><span></span>借入钱购买的产品，需要支付一定的利息需要支付一定的利息需要支付一定的利息</div>
                    </li> -->
                    <li>
                    	<a href="${pageContext.request.contextPath}/Product/productCategory!loadProduct.action">使用</a>
                        <label><span>投资券余额</span> <i></i></label>
                        <p><fmt:formatNumber value="${coupon}" pattern="0.00"/> 元</p>
                        <div class="tips"><span></span>平台赠送的投资券，用来抵购理财产品</div>
                    </li>
                    <!-- <li>
                    	<a href="#">查看详细</a>
                        <label><span>借入</span> <i></i></label>
                        <p><span class="color_8ab46e">-100.00元</span></p>
                        <div class="tips"><span></span>借入钱购买的产品，需要支付一定的利息</div>
                    </li> -->
                </ul>
            </div>
            <div class="asset_r fr">
            	<ul>
                	<li>
                    	<label><span>产品总收益</span><i></i></label>
                        <p><span><fmt:formatNumber value="${users.usersInfo.totalProfit * 0.01}" pattern="0.00"/> </span> 元</p>
                        <a href="${pageContext.request.contextPath}/Product/User/investorsRecord!getInvestorsRecords.action">查看明细</a>
                        <div class="tips"><span></span>购买产品的总收益</div>
                    </li>
                    <!-- <li>
                    	<label><span>利息总收入</span><i></i></label>
                        <p><span>100.00</span>元</p>
                        <a href="#">查看明细</a>
                        <div class="tips"><span></span>借入钱购买的产品，需要支付一定的利息</div>
                    </li> -->
                </ul>
            </div>
        </div>
        <div class="adbox">
			<div class="adbox_c">
				<span>你还在等什么，试试你的投资水平。</span>
				<a href="${pageContext.request.contextPath}/Product/productCategory!loadProduct.action">马上投资</a>
			</div>
		</div>
        
    </div>
</div>
<jsp:include page="../footer.jsp" />
</body>
<script type="text/javascript">
choosePage("3");
chooseMenu("1");
</script>
</html>

