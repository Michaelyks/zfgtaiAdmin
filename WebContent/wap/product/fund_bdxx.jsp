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
		<title>产品详情-标的信息</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/wap/product/css/cpxq.css" />
	</head>
	<body class="body-f0f4f5">
	<div class="title-box">
		<div class="head-title">市场前景:</div>
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
	</div>
		<div class="title-box">
		<div class="head-title">还款保障：</div>
		<div class="head-content">
			<c:choose>
				<c:when test="${cplxjs ne '' && cplxjs ne ' ' && cplxjs ne null }">
					${cplxjs}
				</c:when>
				<c:otherwise>
					暂无记录
				</c:otherwise>
			</c:choose>
		</div>
	</div>
 	<div class="title-box">
		<div class="head-title">相关图片：<span class="titlt-jd">100%律师监督下实物拍摄，盗图必究</span></div>
		<div class="head-content">
			<c:forEach items="${infoList}" var="list">
				<p>&nbsp;</p>
				<img src="${list}" style="width:100%;"/>
			</c:forEach>
			<c:if test="${infoList eq '' || infoList eq '[]' || infoList eq null}">
				<div class="product_img" style="font-size: 28px;">暂无图片</div>
			</c:if>
		</div>
	</div> 
		<%-- <div class="title-box">
		<div class="head-title">项目详情：</div>
		<div class="head-content">
			<ul>
				<li>借款人为澳门人士，从事美容美发连锁经营。借款人曾向出借人借款，借款金额为150万元，并且已经按期还款。因近期扩大经营，急需资金周转，向出借人借款，借款金额150万元，以公司经营回款最为还款来源，同时，借款人以其名下一套写字楼作为该笔借款的还款保障，并且在杭州市房管局办理房屋抵押登记手续，若到期无法偿还借款，借款人自愿放弃申请诉讼解决的权利并同意中泰理财金融将房屋进行转让过户，所得款项用于偿还该笔借款。</li>
			</ul>
		</div>
	</div>
		<div class="title-box">
		<div class="head-title">借款人信息：</div>
		<div class="head-content">
			<ul>
				<li>借款金额：150万元人民币</li>
				<li>资金用途：用于美容美发连锁店的再扩大经营，急需资金周转</li>
				<li>抵押信息：以位于杭州市下城区繁华地段朝晖路嘉汇大厦的写字楼为抵押作为此次借款的还款保障。已在杭州市房管局办理相关房屋抵押登记手续若到期无法偿还借款，借款人自愿放弃申请诉讼解决的权利并同意中泰理财金融将房屋进行转让过户，所得款项用于偿还该笔借款</li>
				<li>价值评估：该小区地理位置优越，交通便利，周边配套设施齐全，目前小区的售价为20000元/平方米。此写字楼建筑面积120.5平方米50万元精装修市场估值240万元。专业房产评估机构评估210万元，抵押率60%。</li>
				<li>其他内容：以上内容均为示例</li>
			</ul>
		</div>
	</div>
	<div class="title-box">
		<div class="head-title">项目介绍：<span class="titlt-jd">100%律师监督下实物拍摄，盗图必究</span></div>
		<div class="head-content">
			<img src="${pageContext.request.contextPath}/Product/images/1.jpg" alt="" />
			<img src="${pageContext.request.contextPath}/Product/images/2.jpg" alt="" />
			<img src="${pageContext.request.contextPath}/Product/images/3.jpg" alt="" />
		</div>
	</div> --%>
</body>
</html>
