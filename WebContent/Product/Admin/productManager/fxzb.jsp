<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<meta charset="utf-8" />
<title>利息总表</title>
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
 		var url='${pageContext.request.contextPath}/Product/Admin/releaseProduct!iportFXTable.action';	
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
			<h3 align="center">利息总表</h3>
			<form id="frm" action="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productFx.action">
				产品名称:<input type="text" name="title" value="${title}">
				<%-- 发布时间:<input id="insertTime" name="insertTime" type="text" value="${insertTime}"> --%>
				投资类别:<select id="type" name="product.investType">
					<option value="" selected="selected">所有</option>
					<option value="0">车无忧</option>
					<option value="1">贸易通</option>
					<option value="2">牛市通</option>
					<option value="3">房盈宝</option>
				</select>
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
							<td style="width: 58px;">投资类别</td>
							<td style="width: 113px;">项目总额</td>
							<td style="width: 68px;">起投金额</td>
							<td style="width: 58px;">年化收益</td>
							<td style="width: 58px;">还款方式</td>
							<td style="width: 58px;">起息日</td>
							<td style="width: 82px;">上线时间</td>
							<td style="width: 82px;">项目结束时间</td>
							<td style="width: 82px;">付息总额</td>
						</tr>
						<c:forEach items="${list}"  var="item" varStatus="i">
							<tr>
								<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td> 
								<td>${item.title}</td>
								<td>${item.cplx}</td>
								<td><fmt:formatNumber value="${item.allCopies}" pattern="#,##0.##"/></td>
								<td><fmt:formatNumber value="${item.qtje*0.01}" pattern="#,##0.##"/></td>
								<td><fmt:formatNumber value="${item.annualEarnings}" pattern="#.##" />%</td>
								<td>${item.fxfs}</td>
								<td>${item.jxfs}</td>
								<td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:MM:ss"/></td>
								<td><fmt:formatDate value="${item.finishTime}" pattern="yyyy-MM-dd"/></td>
								<td><a class="a" href="${pageContext.request.contextPath}/Product/Admin/interestDetails!findInvertorsByProduct.action?productId=${item.id}"><fmt:formatNumber value="${map[item.id] * 0.01}" pattern="#,##0.##"/></a></td>
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
<script type="text/javascript">
var investType='${product.investType}';
$("#type").find("option[value='"+investType+"']").attr("selected",true);
</script>
</html>