<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<title>中泰理财 - 我的账户 - 我的红包</title>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<link href="../css/account.css" rel="stylesheet" type="text/css">
<link href="../css/red_paper.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../js/cz_bang.js"></script>
</head>

<body>
<jsp:include page="../top.jsp" />
<div class="lujin"><div class="crumbs"><a href="#"><img src="../images/home_ico.png" /></a> &gt; <a href="#">我的账户</a> &gt; <a href="#">帐户管理</a> &gt; <em>我的投资券</em></div></div>
<div class="maincnt wrap cf">
	<jsp:include page="leftMenu.jsp" />
    <div class="mainright fr">
        <div class="recharge_box">
        	<div class="asset"><label>我的投资券</div>
            <div class="cntbox">
            	<ul class="red_head"><li class="on">未使用</li><!-- <li>未用完</li> --><li>已使用</li><li>已过期</li><div class="clea"></div></ul>
                <div class="red_paper ing">
                	<ul>
                	<c:forEach items="${list0}" var="list" varStatus="index">
                		<li>
                			<img src="../images/red_paper_open.png"/>
                			<div class="mingxi_neirong">
                				<p><fmt:formatNumber value="${list.initMoney * 0.01}" pattern="#.##"/>元投资券</p>
                				<ul>
                					<li>到期时间:<c:if test="${list.overTime eq null}">永久有效</c:if><fmt:formatDate value="${list.overTime}" pattern="yyyy-MM-dd"/></li>
                					<li>使用范围:用于投资抵用</li>
                				</ul>
                			</div>
                		</li>
                	</c:forEach>
               		<c:if test="${list0 eq null || list0 eq '[]'}">
					<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
	            	<img src="../images/no_record.png" />
	            	</div>
	            	</c:if>
               		<div class="clea"></div>
                	</ul>                  
                </div> 
           <%--      <div class="red_paper ing">
                	<ul>
                	<c:forEach items="${list1}" var="list" varStatus="index">
                		<li>
                			<img src="../images/red_paper_open.png"/>
                			<div class="mingxi_neirong">
                				<p><fmt:formatNumber value="${list.initMoney * 0.01}" pattern="#.##"/>元投资券</p>
                				<ul>
                					<li>到期时间:<fmt:formatDate value="${list.overTime}" pattern="yyyy-MM-dd"/></li>
                					<li>余额剩余:<fmt:formatNumber value="${list.money * 0.01}" pattern="#.##"/></li>
                				</ul>
                			</div>
                		</li>
               		</c:forEach>
                		<c:if test="${list1 eq null  || list1 eq '[]'}">
						<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
		            	<img src="../images/no_record.png" />
		            	</div>
		            	</c:if>
                		<div class="clea"></div>
                	</ul>
                </div>	 --%>
                 <div class="red_paper over">
                	<table>              		
                		<tbody>
                			<tr>
                				<td>图标</td>
                				<td>序号</td>
                				<td>红包名</td>
                				<td>投资券(元)</td>
                				<td>类型</td>
                				<td>获得时间</td>
                				<td>使用时间</td>
                			</tr>
                			<c:forEach  items="${list2}" var="list" varStatus="index">
                			<tr>
                				<td><img src="../images/red_paper_open.png"/></td>
                				<td>${index.count}</td>
                				<td>活动投资券</td>
                				<td><fmt:formatNumber value="${list.initMoney * 0.01}" pattern="#.##"/></td>
                				<td>${list.type}</td>
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
                <div class="red_paper over">
                	<table>              		
                		<tbody>
                			<tr>
                				<td>图标</td>
                				<td>序号</td>
                				<td>红包名</td>
                				<td>投资券(元)</td>
                				<td>类型</td>
                				<td>获得时间</td>
                				<td>过期时间</td>
                			</tr>
                			<c:forEach  items="${list3}" var="list" varStatus="index">
                			<tr>
                				<td><img src="../images/red_paper_over.png"/></td>
                				<td>${index.count}</td>
                				<td>新手红包</td>
                				<td><fmt:formatNumber value="${list.initMoney * 0.01}" pattern="#.##"/></td>
                				<td>${list.type}</td>
                				<td><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                				<td>
                				<fmt:formatDate value="${list.overTime}" pattern="yyyy-MM-dd HH:mm:ss" />
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
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp" />
</body>
<script type="text/javascript">
	$(function(){
		$(".red_paper:eq(0)").css("display","block");
		$(".red_head li").click(function(){
			$('.red_head li.on').removeClass("on");
			$(this).addClass('on');
			$('.red_paper').css("display","none");
			$('.red_paper').eq($(this).index()).css("display","block");
		})
	/*	$('.red_paper:eq(0) ul li').mouseover(function(){
			if($(this).data("class")!="on"){
				$(this).children("img").attr("src","../images/red_paper_open.png");
				$(this).children("div").css("display","block");
			}
			$(this).data("class","on");
		});*/
		
		
		
	});

choosePage("3");
chooseMenu("1");
</script>
</html>
