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
					<ul class="list_title" id="list_title">
						<li class="on">未使用的红包</li>
						<li>已使用的红包</li>
						<li>已过期的红包</li>
						<div class="clea"></div>
					</ul>
					<div class="coupons_list">
						<div class="hongbao_c" style="display:block;">
							<c:forEach items="${list0}" var="list" varStatus="index">
								<div class="hb">
									<div class="mob">
										<p>新人注册券</p>
										<p class="money-b"><label>￥</label><span><fmt:formatNumber value="${list.initMoney * 0.01}" pattern="#.##"/></span>元</p>
									</div>
									<div class="picker-con">
										<p>使用条件：满10000元 </p>
										<p class="hb_time" title="<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">领券时间：<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd"/></p>
										<p class="hb_time">有效期至：<c:if test="${list.overTime eq null}">永久有效</c:if><fmt:formatDate value="${list.overTime}" pattern="yyyy-MM-dd"/></p>
									</div>
								</div>
							</c:forEach>

							<div class="clea"></div>
						</div>
						<c:choose>
							<c:when test="${list0 eq null || list0 eq '' || list0 eq '[]'}">
								<div class="nothing-img">
				            		<img src="../images/nothing-img.jpg"/>
				            	</div>		
							</c:when>
						</c:choose>
					</div>				
					<div class="coupons_list coupons_tab">
						<table>
							<tbody>
								<tr class="ative">
									<!--<td>图标</td>-->
									<td>序号</td>
									<td>标题</td>
									<td>红包金额</td>
									<!-- <td>类型</td> -->
									<td>领券时间</td>
									<td>使用时间</td>
								</tr>
								<c:forEach  items="${list2}" var="list" varStatus="index">
		                			<tr>
		                				<!--<td><img width="12" height="12"  src="../images/red_paper_open.png"/></td>-->
		                				<td>${index.count}</td>
		                				<td>新人注册红包</td>
		                				<td class="orange-color"><fmt:formatNumber value="${list.initMoney * 0.01}" pattern="#.##"/></td>
		                				<%-- <td>${list.type}</td> --%>
		                				<td><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                				<td><fmt:formatDate value="${list.useTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                			</tr>
	                			</c:forEach>
							</tbody>
						</table>
						<c:if test="${list2 eq null  || list2 eq '[]'}">
							<div class="nothing-img">
			            		<img src="../images/nothing-img.jpg"/>
			            	</div>
	            		</c:if>						
					</div>
					<div class="coupons_list coupons_tab">
						<table>
							<tbody>
								<tr class="ative">
									<!--<td>图标</td>-->
									<td>序号</td>
									<td>标题</td>
									<td>红包金额</td>
									<!-- <td>类型</td> -->
									<td>领券时间</td>
									<td>过期时间</td>
								</tr>
								<c:forEach  items="${list3}" var="list" varStatus="index">
	                			<tr>
	                				<!--<td><img width="12" height="12" src="../images/red_paper_over.png"/></td>-->
	                				<td>${index.count}</td>
	                				<td>活动投资券</td>
	                				<td class="orange-color"><fmt:formatNumber value="${list.initMoney * 0.01}" pattern="#.##"/></td>
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
							<div class="nothing-img">
			            		<img src="../images/nothing-img.jpg"/>
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