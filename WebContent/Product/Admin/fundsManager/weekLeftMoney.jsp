<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
<title>一周不可动金额</title>
<style type="text/css">
	.sereach {
  width: 200px;
  height: 32px;
  line-height: 32px;
  text-align: center;
  border: 1px solid #009DDA;
  border-radius: 5px;
}
 .select1 {
  border-radius: 5px;
  border-color: #009DDA;
  margin-right: 5px;
}
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	
	<span>一周不可动金额</span>
	<input type="button" value="导出报表" onclick="ireportDo()" style="float:rigth;margin-left: 30px" >
		
		
	<table border="0.5" cellspacing="0" cellpadding="0">
		<tbody>
				<tr>
					<td width="200px" style="text-align: center;">日期</td>
					<td width="200px" style="text-align: center;">预期充值金额手续费</td>
					<td width="200px" style="text-align: center;">预期还本付息金额</td>
					<td width="200px" style="text-align: center;">账户余额总和</td>
					<td width="100px" style="text-align: center;">预计提现手续费</td>
					<td width="100px" style="text-align: center;">合计</td>
				</tr>
			<c:forEach items="${list}"  var="item" >
				<tr>
					<td style="text-align: center;">${item.date}</td>
					<td style="text-align: center;">
						<fmt:formatNumber value="${item.yjczsxf}" pattern="#,##0.##" /> 
					</td>
					<td style="text-align: center;">
						<fmt:formatNumber value="${item.dqhbfxje}" pattern="#,##0.##" /> 
					</td>
					<td style="text-align: center;">
						<fmt:formatNumber value="${item.allAccountLeftMoney}" pattern="#,##0.##" /> 
					</td>
					<td style="text-align: center;">
						<fmt:formatNumber value="${item.yjtxsxf}" pattern="#,##0.##" /> 
					</td>
					<td style="text-align: center;">
						<fmt:formatNumber value="${item.sum}" pattern="#,##0.##" /> 
					</td>
					<td style="text-align: center;">
					
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!--  
	<div>
			<p><span>今日用户最大余额:</span>
			<span><fmt:formatNumber value="${allLeftMoney*0.01}" pattern="#,##0.##" /></span></p>
			<p><span>今日用户最大提现额度</span>
			<span><fmt:formatNumber value="${leftMoney * 0.01}" pattern="#,##0.##" /></span></p>
			<p><span>今日最大提现手续费:</span>
			<span><fmt:formatNumber value="${txMaxCount*1}" pattern="#,##0.##" /></span></p>
	</div>		
	
	<div>当日不可动金额:<fmt:formatNumber value="${(allCzMoney/30)* 0.01+leftMoney * 0.01+txMaxCount*1}" pattern="#,##0.##" />
	</div>-->
</div>
</div>

<div id="psi_load" style="display:none;">
<img width="18" src="${pageContext.request.contextPath}/Product/images/progress.gif"><span  id="psi_tip">正在导出,请稍等...</span>
</div>	
<script type="text/javascript">
function ireportDo(){
			var url="${pageContext.request.contextPath}/Product/Admin/recharge!exportWeekLeftMoney.action";
			var my= art.dialog({	
			    title: '提示',
			    content:document.getElementById("psi_load"),
			    height: 60,
			    lock: true,
			    cancel: false
			});  
			$.post(url,$("#frm").serialize(),function(data){			
				my.close();
				var realPath = '${pageContext.request.contextPath}'+data;
					var ssss="导出成功&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+realPath+"' style='color:red;'>点击下载</a>";
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
</body>
</html>