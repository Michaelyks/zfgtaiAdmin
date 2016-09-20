<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>
<meta charset="utf-8" />
<title>利息明细</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
<style type="text/css">
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>	 
<script type="text/javascript">
function sub(){
	$("#productId").val("");
}
$("#type").find("option[value="+'${product.investType}'+"]").attr("selected",true);
function ireportDo(){
 		var url='${pageContext.request.contextPath}/Product/Admin/interestDetails!iportFXMXTable.action';	
    	var my= art.dialog({	
    	    title: '提示',
    	    content:document.getElementById("psi_load"),
    	    height: 60,
    	    lock: true,
    	    cancel: false
    	});  
    	$.post(url,$("#frm").serialize(),function(data){			
    		my.close();
    			var ssss="导出成功&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+data+"' style='color:red;'>点击下载</a>";
           		art.dialog({
       		    title: '提示',
       		    content:ssss,
       		    height: 60,
       		    lock: true,
       		    ok:function(){
       		    	//mysss.close();
       		    }
       		});   
    	});
}
</script> 
</head>
	<body>
		<div class="center">		
		<jsp:include page="/Product/Admin/common/head.jsp"/>	
			<div class="main" align="center">
			<h3 align="center">利息明细</h3>
			<form id="frm" action="${pageContext.request.contextPath}/Product/Admin/interestDetails!findInvertorsByProduct.action" onsubmit="sub()">
				用户名:<input type="text" name="username" value="${username}" maxlength="11">
				发期时间:<input  type="text"  id="finishTime" name="finishTime" value="${finishTime}">
				<input type="hidden" id="productId" name="productId" value="${productId}">
				<input type="submit" value="查询">
				<input type="button" value="导出报表" onclick="ireportDo()">
			</form>
				<div class="table">
					<table cellspacing="0" cellpadding="0" style="  width: 100%;">
						<tbody>
						<tr>
							<td style="width: 30px;">序号</td>
							<td style="width: 366px;">产品名称</td>
							<td style="width: 58px;">用户名</td>
							<td style="width: 113px;">真实姓名</td>
							<td style="width: 68px;">项目年化收益</td>
							<td style="width: 68px;">加息券年化收益</td>
							<td style="width: 68px;">最终年化收益</td>
							<td style="width: 68px;">本次收益</td>
							<td style="width: 68px;">投资本金</td>
							<td style="width: 58px;">付息</td>
							<td style="width: 82px;">发布日期</td>
							<td style="width: 82px;">购买日期</td>
							<td style="width: 82px;">结算日期</td>
						</tr>
						<c:forEach items="${pageUtil.list}"  var="item" varStatus="i">
							<tr <%-- <c:if test="${item.productStatus gt 0 && item.leftCopies ne 0}" >style="background: #ffff00;"</c:if> --%>>
								 <td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td> 
								<td>${item.product.title}</td>
								<td><a class="a"  href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item.users.username)}">${myel:jieMiUsername(item.users.username)}</a></td>
								<td>${item.users.usersInfo.realName}</td>
								<td><fmt:formatNumber value="${item.product.annualEarnings}" pattern="#.##" />%</td>
								<td><fmt:formatNumber value="${item.investors.couponAnnualRate}" pattern="#.##" />%</td>
								<td><fmt:formatNumber value="${item.investors.annualEarnings}" pattern="#.##" />%</td>
								<td><fmt:formatNumber value="${(item.payInterest+item.payMoney) * 0.01}" pattern="#,##0.##"/></td>
								<td><fmt:formatNumber value="${item.inMoney*0.01}" pattern="#,##0.##"/></td>
								<td><fmt:formatNumber value="${item.payInterest*0.01}" pattern="#,##0.##"/></td>
								<td><fmt:formatDate value="${item.product.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${item.returnTime}" pattern="yyyy-MM-dd"/></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<c:choose>
	<c:when test="${pageUtil.list ne '[]' &&  pageUtil.list ne '' && pageUtil.list ne null}">
		<jsp:include page="/Product/page.jsp" /></c:when>
		<c:otherwise>
			<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
	  			<img src="../images/no_record.png" />
	 	 	</div>
		</c:otherwise>
	</c:choose>
				</div>
			</div>
		</div>
<div id="psi_load" style="display:none;">
<img width="18" src="${pageContext.request.contextPath}/Product/images/progress.gif"><span  id="psi_tip">正在导出,请稍等...</span>
</div>	
	</body> 
<script type="text/javascript">
	var k4 = new Kalendae.Input("finishTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
</script>
</html>