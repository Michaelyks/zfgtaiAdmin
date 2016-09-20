<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 我的投资券</title>
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/account.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/coupons.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
		<jsp:include page="../header.jsp" />
		<!-- 主内容 -->
		<div class="main">
			<div class="layout my_account">
				<jsp:include page="leftMenu.jsp" />
				
				<!-- 内容页 -->
				<div class="fr my_info">
					<div class="title">我的投资券</div>
					<ul class="list_title" id="list_title">
						<li class="on">未使用</li>
						<!-- <li>未使用</li> -->
						<li>已使用</li>
						<li>已过期</li>
						<div class="clea"></div>
					</ul>
					<div class="coupons_list">
						<div class="hongbao_c" style="display:block;">
						<c:forEach items="${list0}" var="list" varStatus="index">
							<div class="hb">
								<p class="fl"><a>活动投资券</a></p>
								<div class="fr">
									<p class="hb_touziquan"><span class="red_big"><fmt:formatNumber value="${list.initMoney * 0.01}" pattern="#.##"/></span>元</p>
									<span>适用范围：用于投资抵用</span>
									<p class="hb_time" title="<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">获得时间：<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd"/></p>
									<p class="hb_time">到期时间：<c:if test="${list.overTime eq null}">永久有效</c:if><fmt:formatDate value="${list.overTime}" pattern="yyyy-MM-dd"/></p>
								</div>
							</div>
						</c:forEach>
							<%-- <div class="hb hb1">
								<p class="fl"><a>投资券b</a></p>
								<div class="fr">
									<p class="hb_touziquan"><span class="red_big">520</span>元</p>
									<span>适用范围：用于投资抵用</span>
									<p class="hb_time">获取时间：2015-6-1</p>
									<p class="hb_time">到期时间：2015-6-8</p>
								</div>
							</div> --%>
							<div class="clea"></div>
						</div>
						<c:choose>
							<c:when test="${list0 eq null || list0 eq '' || list0 eq '[]'}">
								<div class="dingwei">							
									<div class="touzi_tip">
										<span>很抱歉，这儿是个荒地。</span>
										<p>您还没有任何投资券，去看看有什么[<a href="${pageContext.request.contextPath}/Product/productCategory!loadProduct.action">理财产品</a>]感兴趣的吧！</p>
									</div>
								</div>			
							</c:when>
							<c:otherwise>
								<div class='hongbao_rule'> 
								 <p class='rule-title'>投资券使用规则：</p>
								 <p class="rule-line">1、投资券在投资中可抵用现金，每次投资只可使用一张投资券</p>
								<p class='rule-line'>2、每张投资券只可使用一次</p>
								<p class='rule-line'>3、新手专享产品不可使用投资券</p>
								<p class='rule-line'>4、投资券仅用于投资抵用现金，不可提现</p>
								<p class='rule-line'>5、超过有效期的投资券将不可使用</p>
								</div>
						</c:otherwise>
						</c:choose>
					</div>
					<%-- <div class="coupons_list">
						<div class="dingwei">
							<div class="touzi_tip">
								<span>很抱歉，这儿是个荒地。</span>
								<p>您还没有任何投资券，去看看有什么[<a href="${pageContext.request.contextPath}/Product/productCategory!loadProduct.action">理财产品</a>]感兴趣的吧！</p>
							</div>
						</div>							
					</div>	 --%>				
					<div class="coupons_list coupons_tab">
						<table>
							<tbody>
								<tr>
									<td>图标</td>
									<td>序号</td>
									<td>标题</td>
									<td>投资券(元)</td>
									<!-- <td>类型</td> -->
									<td>获得时间</td>
									<td>使用时间</td>
								</tr>
								<c:forEach  items="${list2}" var="list" varStatus="index">
                			<tr>
                				<td><img width="12" height="12"  src="../images/red_paper_open.png"/></td>
                				<td>${index.count}</td>
                				<td>活动投资券</td>
                				<td><fmt:formatNumber value="${list.initMoney * 0.01}" pattern="#.##"/></td>
                				<%-- <td>${list.type}</td> --%>
                				<td><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                				<td><fmt:formatDate value="${list.useTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                			</tr>
                			</c:forEach>
							</tbody>
						</table>
						<c:if test="${list2 eq null  || list2 eq '[]'}">
						<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
		            	<img src="../images/no_record.png" />
		            	</div>
	            		</c:if>						
					</div>
					<div class="coupons_list coupons_tab">
						<table>
							<tbody>
								<tr>
									<td>图标</td>
									<td>序号</td>
									<td>标题</td>
									<td>投资券(元)</td>
									<!-- <td>类型</td> -->
									<td>获得时间</td>
									<td>过期时间</td>
								</tr>
								<c:forEach  items="${list3}" var="list" varStatus="index">
	                			<tr>
	                				<td><img width="12" height="12" src="../images/red_paper_over.png"/></td>
	                				<td>${index.count}</td>
	                				<td>活动投资券</td>
	                				<td><fmt:formatNumber value="${list.initMoney * 0.01}" pattern="#.##"/></td>
	                				<%-- <td>${list.type}</td> --%>
	                				<td><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                				<td>
	                				<fmt:formatDate value="${list.overTime}" pattern="yyyy-MM-dd" />
	                				</td>
	                			</tr>
                				</c:forEach>
							</tbody>
						</table>	
						<c:if test="${list3 eq null  || list3 eq '[]'}">
						<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
		            	<img src="../images/no_record.png" />
		            	</div>
	            		</c:if>					
					</div>
					
				</div>
				<!-- 内容页 end-->
				<div class="clea"></div>
			</div>
			
		
		</div>
		<!-- 主内容 end-->
		<jsp:include page="../footer.jsp" />
		<script>
			$(function(){
				$(".coupons_list").eq(0).css("display","block");
				$("#list_title li").click(function(){
					$("#list_title").find(".on").removeClass("on");
					$(this).addClass("on");
					var ind = $(this).index();
					$(".coupons_list").css("display","none");
					$(".coupons_list").eq(ind).css("display","block");
				});
				//书签切换结束
			})
		</script>
		
		
	</body>
</html>