<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<meta charset="utf-8" />
<title>产品发布记录</title>
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
function ireportDo(){
 		var url='${pageContext.request.contextPath}/Product/Admin/releaseProduct!iportTable.action?finishTime='+$("#finishTime").val()+"&product.title="+$('#title').val();;	
    	var my= art.dialog({
    	    title: '提示',
    	    content:document.getElementById("psi_load"),
    	    height: 60,
    	    lock: true,
    	    cancel: false
    	});  
    	$.post(url,null,function(data){			
    		my.close();
    		    data = '${pageContext.request.contextPath}'+data;
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
			<h3 align="center">产品发布记录</h3>
			<form action="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productList.action">
				产品名称:<input type="text" name="product.title" id="title" value="${product.title}">
				<%-- 发布时间:<input id="insertTime" name="insertTime" type="text" value="${insertTime}"> --%>
				到期时间:<input  type="text"  id="finishTime" name="finishTime" value="${finishTime}">
				<input type="submit" value="查询">
				<input type="button" value="导出报表" onclick="ireportDo()">
			</form>
				<div class="table">
					<table cellspacing="0" cellpadding="0" style="  width: 100%;">
						<tbody>
						<tr>
							<td style="width: 30px;">序号</td>
							<td style="width: 366px;">产品名称</td>
							<td style="width: 58px;">产品类型</td>
							<td style="width: 58px;">产品状态</td>
							<td style="width: 58px;">完成进度</td>
							<td style="width: 58px;">年化收益</td>
							<td style="width: 58px;">理财期限</td>
							<td style="width: 58px;">剩余天数</td>
							<td style="width: 68px;">起投金额</td>
							<td style="width: 113px;">项目总额</td>
							<td style="width: 76px;">已募金额</td>
							<td style="width: 76px;">实际募集金额</td>
							
							<td style="width: 76px;">投资券金额</td>
							<td style="width: 76px;">虚拟投资金额</td>
							<td style="width: 104px;">剩余金额</td>
							<td style="width: 143px;">发布时间</td>
							<td style="width: 82px;">到期时间</td>
							<td style="width: 82px;">预付利息</td>
							<td style="width: 82px;">实付利息</td>
							<td style="width: 82px;">虚拟投资</td>
						</tr>
						<c:forEach items="${list}"  var="item" varStatus="i">
							<tr <c:if test="${item.productStatus gt 0 && item.leftCopies ne 0}" >style="background: #ffff00;"</c:if>>
								<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
								<td>${item.title}</td>
								<td>${item.cplx}</td>
								<td>
									${item.cpzt}
								</td>
								<td>
									<fmt:formatNumber value="${item.wcjd * 100}" pattern="#.##" />%
								</td>
								<td><fmt:formatNumber value="${item.annualEarnings}" pattern="#.##" />%</td>
								<td>
									${item.lcqx}
								</td>
								<td>
									${item.tzqx}
								</td>
								<td><fmt:formatNumber value="${item.qtje*0.01}" pattern="#,##0.##"/></td>
								<td><fmt:formatNumber value="${item.allCopies}" pattern="#,##0.##"/></td>
								<td><fmt:formatNumber value="${item.hasCopies}" pattern="#,##0.##"/></td>
								
							
								<td><fmt:formatNumber value="${(item.hasCopies-mapProductToVirtual[item.id]*0.01 -map[item.id].coupon*0.01)	}" pattern="#,##0.##"/></td>
								<!--  
								<td><fmt:formatNumber value="${map[item.id].in_money*0.01}" pattern="#,##0.##"/></td>
								-->
									<td><fmt:formatNumber value="${map[item.id].coupon*0.01}" pattern="#,##0.##"/></td>
								<td><fmt:formatNumber value="${mapProductToVirtual[item.id]*0.01}" pattern="#,##0.##"/></td>
								
								<td><fmt:formatNumber value="${item.leftCopies}" pattern="#,##0.##"/></td>
								<td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${item.finishTime}" pattern="yyyy-MM-dd"/></td>
								<td><fmt:formatNumber value="${item.yflx}" pattern="#,##0.##"/></td>
								<td><a class="a" href="${pageContext.request.contextPath}/Product/Admin/investors!findInvertors.action?productId=${item.id}&productStatus=${item.productStatus}"><fmt:formatNumber value="${map[item.id].all_shouyi * 0.01}" pattern="#,##0.##"/></a></td>
								<td><a class="a" target="_blank" href="${pageContext.request.contextPath}/Product/Admin/virtualInsRecord!toVirtualIns.action?productId=${item.id}">虚拟投资</a><br>
									|<a class="a" target="_blank" href="${pageContext.request.contextPath}/Product/Admin/virtualInsRecord!virtualInsRecord.action?productId=${item.id}">虚拟投资记录</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<c:choose>
	<c:when test="${list ne '[]' &&  list ne '' && list ne null}">
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