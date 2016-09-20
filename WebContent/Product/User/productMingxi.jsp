<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<title>中泰理财 - 我的账户 - 充值</title>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<link href="../css/account.css" rel="stylesheet" type="text/css">
<link href="../css/recharge.css?"+Math.random(); rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.superslide.js"></script>
<script type="text/javascript" src="../js/base.js"></script>
<script type="text/javascript" src="../js/recharge.js"></script>
</head>

<body>
<jsp:include page="../top.jsp" />
<div class="maincnt wrap cf">
	<jsp:include page="leftMenu.jsp" />
	
    <div class="mainright fr">
    	<div class="crumbs"><a href="#"><img src="../images/home_ico.png" /></a> &gt; <a href="#">我的账户</a> &gt; <a href="#">帐户管理</a> &gt; <em>产品明细</em></div>
        <div class="product_detail"> 
		   <h2>产品明细</h2> 
		   <c:choose>
		   <c:when test="${investorsList eq '[]'}">
		   <div class="table"> 
			<table> 
			 <tbody>
			  <tr> 
			   <th class="col1">产品名称</th> 
			   <th class="col2">领投人</th> 
			   <th class="col3">产品结束时间</th> 
			   <th class="col4">购买份数(元)</th> 
			   <th class="col5">收益</th> 
			   <th class="col6">收益率</th> 
			   <th class="col7">操作</th> 
			  </tr> 
			 </tbody>
			</table> 
			<div class="ui_page" id="ui_pageClass"> 
			 <ul id="countshangul"> 
			  <!-- <li id="count2"><a href="/Money/Users/userBuyDetail!checkBuyDetailNewVersion.action?&amp;pageCount=1" class="on" looyu_bound="1">1</a></li> -->
			 </ul> 
			</div> 
		   </div> 
		   </c:when>
		   <c:otherwise>
				<center><img src='${pageContext.request.contextPath}/Product/images/nobuy.png' /></center>
			</c:otherwise>
		   </c:choose>
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
