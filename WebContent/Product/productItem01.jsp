<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	//普通产品的展示
%>
<li>
<c:choose>
	<c:when test="${product.productStatus == 1}">
		<div class="item" style="background-color: #D2D2D2;">
	</c:when>
	<c:otherwise>
	<div class="item" >
	</c:otherwise>
</c:choose>
		<div class="title">
			<h4>
				<a href="${pageContext.request.contextPath}/Product/index!showProductDetails.action?productId=${product.id}"target="_blank">${product.title}</a>
					<c:if test="${product.isRecommend eq 1}">
					<img src="images/recommend.png"/>
					</c:if>
			</h4>
		</div>
		<div class="data">
			<ul class="cf">
				<li>
					<h4>投资期限</h4> <c:choose>
						<c:when test="${product.productStatus == 1}">
							<p>
								<span class="color_d24740" style="font-size: 20px;">${product.cpzt}</span>
							</p>
						</c:when>
						<c:when test="${product.productStatus== 2}">
							<p>
								<span class="color_d24740" style="font-size: 20px;">${product.cpzt}</span>
							</p>
						</c:when>
						<c:when test="${product.productStatus== 3}">
							<p>
								<span class="color_d24740" style="font-size: 20px;">${product.cpzt}</span>
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<span class="color_d24740">${product.tzqx}</span>天
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li>
					<h4>项目总额</h4>
					<p>
						<span><fmt:formatNumber
								value="${product.financingAmount * 0.01}" pattern="#,##0.#" /></span>
					</p>
				</li>
				<li>
					<h4>年化收益</h4>
					<p>
						<span class="color_d24740"><fmt:formatNumber
								value="${product.annualEarnings}" pattern="#.##" /></span>%
					</p>
				</li>
				<li>
					<h4>起投金额</h4>
					<p>
						<span><fmt:formatNumber
								value="${product.qtje * 0.01}" pattern="#,##0.##" /></span>
					</p>
				</li>
				<c:if test="${product.productType eq 1}">
					<li><img
						src="${pageContext.request.contextPath}/Product/images/gold.png" /></li>
				</c:if>
			</ul>
		</div>
		<div class="bot">
			<c:if test="${product.productStatus == 0}">
				<a
					href="${pageContext.request.contextPath}/Product/index!showProductDetails.action?productId=${product.id}"
					class="btn" target="_blank">立即投资</a>
			</c:if>
			<ul>
				<li class="li_3"><label>项目到期：</label>
					<p>
						<fmt:formatDate value="${product.finishTime}" pattern="yyyy-MM-dd" />
					</p></li>
				<c:if
					test="${product.productStatus == 0 || product.productStatus == 1}">
					<li class="li_4"><label>当前进度：</label>
						<p class="pross">
							<span progress="${product.wcjd}"></span>
						</p> <b><fmt:formatNumber value="${product.wcjd * 100}"
								pattern="#.##" />%</b></li>
				</c:if>
				<c:if
					test="${product.productStatus == 2 || product.productStatus == 3}">
					<li class="li_4"><label>当前进度：</label>
						<p class="pross">
							<span progress="1"></span>
						</p> <b><fmt:formatNumber value="100" pattern="#.##" />%</b></li>
				</c:if>
			</ul>
		</div>
		<c:choose>
			<c:when test="${product.productStatus == 1}">
				<div class="invest_z"
					style="background:url(${pageContext.request.contextPath}/Product/images/yishouqing.png); "></div>
				<img class="maiwang" src="images/shouq.png" />
			</c:when>
			<c:when test="${product.productStatus == 2}">
				<div class="invest_z"
					style="background:url(${pageContext.request.contextPath}/Product/images/jiesuanzhong.png);"></div>
			</c:when>
			<c:when test="${product.productStatus == 3}">
				<div class="invest_z"
					style="background:url(${pageContext.request.contextPath}/Product/images/yihuankuan.png);"></div>
			</c:when>
			<c:otherwise>
				<div class="invest_z"></div>
			</c:otherwise>
		</c:choose>
	</div>
</li>