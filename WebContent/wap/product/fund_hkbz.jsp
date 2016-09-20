<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		<meta name="apple-touch-fullscreen" content="yes">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<title>产品详情-还款保障</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/wap/product/css/cpxq.css" />
	</head>
	<body class="body-f0f4f5">
	<div class="title-box">
		<div class="head-title">项目详情：</div>
		<div class="head-content">
			<c:choose>
				<c:when test="${hkly ne '' && hkly ne ' ' && hkly ne null }">
					${hkly}
				</c:when>
				<c:otherwise>
					暂无记录
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	
<%-- 	<div class="title-box">
		<div class="head-title">资金保障：</div>
		<div class="head-content">
			<c:choose>
				<c:when test="${zjbz ne '' && zjbz ne ' ' && zjbz ne null }">
					${zjbz}
				</c:when>
				<c:otherwise>
					暂无记录
				</c:otherwise>
			</c:choose>
		</div>
	</div> --%>
	<!-- <div class="title-box">
		<div class="head-title">还款来源</div>
		<div class="head-content">
			<ul>
				<li>第一还款来源：借款人名下市值240万元的抵押写字楼，足额覆盖借款金额</li>
				<li>第二还款来源：为借款人企业营业收入，该企业2014年的纯利润收入为400
万元，2014年开始扩大经营规模，预计2015年的营业收入达到600万元</li>
				<li>第三还款来源：为借款人企业营业收入，该企业2014年的纯利润收入为400
万元，2014年开始扩大经营规模，预计2015年的营业收入达到600万元</li>
				<li>第四还款来源：为借款人企业营业收入，该企业2014年的纯利润收入为400
万元，2014年开始扩大经营规模，预计2015年的营业收入达到600万元</li>
				<li>第五还款来源：（1）平安保险对个人用户的资金安全进行承保； 
&nbsp;&nbsp; 2）中泰理财金融高额风险保障金</li>
			</ul>
		</div>
	</div>
	
	<div class="title-box">
		<div class="head-title">资金保障：</div>
		<div class="head-content">
			<ul>
				<li>实力担保：由实力雄厚的担保公司提供连带责任的保证担保</li>
				<li>本息保障：若出现未履行还款义务时，依规定将抵押写字楼变现，所得款项有限偿还投资者本息</li>
				<li>资金托管：交易流程中中泰理财不会触碰用户资金，交易资金将由第三方支付机构托管</li>
				<li>平安理赔：平安保险将全程对个人资金账户进行安全承保，若在交易过程中因账户安全问题导致资金损失，平安保险将全额赔付</li>
				<li>律所监管：广东鹏鼎律师事务所个广东天穗律师事务所对债券的行程进行监管见证，确保债权合法有效</li>
			</ul>
		</div>
	</div> -->
	
</body>
</html>
