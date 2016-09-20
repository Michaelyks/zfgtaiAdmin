<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Android单个渠道详情统计表</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
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
	.main {position:relative;}
.fixtop { position:fixed;width:1500px; line-height: 30px;height:30px;z-index:99;background:#c1c1c1;display:none;left:50%;margin-left:-750px;top:0px; overflow: hidden;}
.ct-table{border:1px solid #ccc;border-collapse:collapse;border-spacing:0;}
.ct-table thead { background: #c1c1c1;}
.ct-table thead.fix {position:fixed;left:50%;top:0px;margin-left:-750px;width:1500px;}
.ct-table tbody {margin-top: 30px}
.ct-table tbody.mt { margin-top:30px; }
.ct-table tr td { line-height: 30px; }
.ct-table tbody>tr>td:nth-child(even) { background: #e2e2e2; }
.ct-table tbody>tr>td:nth-child(odd) { background: #f9f9f9; }
.h3-title {}
.form { line-height:30px; font-size:14px;}
.input-text{ width:200px; height:24px;line-height:normal;}
.input-button {height:25px; line-height:25px; text-align:center; padding:0px 15px;}
.mes-div {line-height:30px; padding:0px 20px; margin-top:10px; text-align:left;}
.mes-div .span { margin:0px 50px 0px 0px; font-size:14px;}
</style>	
<!-- <script type="text/javascript">
function queryProduct(){
	var insertTime=$("#insertTime").val();

	var url = "${pageContext.request.contextPath}/Product/Admin/activity!loadQdtjDetails.action?insertTime="+insertTime;
	window.location.href=url;
}
</script> -->
<script type="text/javascript">
function ireportDo(){
		var list='${table}';
		if(list!='1'){
			alert("无数据");
			return false;
		}
		var registChannel=document.getElementById("registChannel").value;
		var insertTime=document.getElementById("insertTime").value;
 		var url='${pageContext.request.contextPath}/Product/Admin/activity!iportQdtjDetailTable.action?';
 		var list="${list}";
    	var my= art.dialog({
    	    title: '提示',
    	    content:document.getElementById("psi_load"),
    	    height: 60,
    	    lock: true,
    	    cancel: false,
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
       		    },
       		});   
    	});

}
$(function(){
 	var hTop=$("#tHead").offset().top-30; 	
 	$("#fixtop").append($("#tHead").clone());
 	$(window).scroll(function(){
 		var scrollTop=$(window).scrollTop();
 		 if(scrollTop > hTop){
 		 	$("#fixtop").show();
 		 }else {
 		 	$("#fixtop").hide();
 		 }
 	})
 })
</script> 
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3 class="h3-title">Android单个渠道统计详情</h3>
	<form class="form" id="frm" action="${pageContext.request.contextPath}/Product/Admin/activity!loadQdtjDetails.action">
	<span>查询期间:</span> <input class="input-text"  id="insertTime" name="insertTime" type="text" value="${insertTime}">
	<input id="registChannel" name="registChannel" type="hidden" value="${platChannel.channel}">
	<input class="input-button" type="submit" value="查询">
    <input class="input-button" type="button" value="导出报表" onclick="ireportDo()">
	</form>	
	<div class="mes-div">
		<span class="span">渠道名称：${platChannel.channelName} </span>
		<span class="span">渠道编号：${platChannel.channel} </span>
		<span class="span">投放日期：<fmt:formatDate value="${platChannel.date}" pattern="yyyy-MM-dd"/> </span>
	</div>
<div id="fixtop" class="fixtop"></div>
	<table  id="tHead" class="ct-table" border="1" cellspacing="0" cellpadding="0" style="text-align: center;">
		<thead>
		<tr>
			<td width="200px;">序号</td>
			<td width="200px;">日期</td>
			<td width="200px;">激活次数</td>
			<td width="200px;">注册人数</td>
			<td width="200px;">绑定人数</td>			
			<td width="200px;">渠道转化率(%)</td>
			<td width="200px;">投资人数</td>		
			<td width="200px;">首投人数</td>
			<td width="200px;">首投金额(元)</td>
			<td width="200px;">复投人数</td>
			<td width="200px;">投资金额(元)</td>
			<td width="200px;">重复投资率(%)</td>		
			<td width="200px;">充值次数</td>
			<td width="200px;">充值金额(元)</td> 
		</tr>
	</thead>

		<tbody>
		<c:forEach items="${list}" var="item" varStatus="i">
			<tr>
				<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
				<td><fmt:formatDate value="${item.date}" pattern="yyyy-MM-dd"/></td>
				<td>${item.activateUserSum}</td>
				<td>${item.regUserSum}</td>
				<td>${item.bindBankUserSum}</td> 
				<td><fmt:formatNumber value="${item.regActivityRate*100}" pattern="#,##0.##" /></td>
				<td>${item.investUserSum}</td>
				<td>${item.firstInvestUserSum}</td>
				<td><fmt:formatNumber value="${item.firstInvestCentSum*0.01}" pattern="#,##0.##"/></td>
				<td>${item.reInvestUserSum}</td>
				<td><fmt:formatNumber value="${item.investCentSum*0.01}" pattern="#,##0.##"/></td>
				<td><fmt:formatNumber value="${item.reInvestRate*100}" pattern="#,##0.##"/></td>
				<td>${item.rechargeCount}</td>
				<td><fmt:formatNumber value="${item.rechargeCentSum*0.01}" pattern="#,##0.##"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<c:choose>
			<c:when test="${list ne '[]' &&  list ne '' && list ne null}"><jsp:include page="/Product/page.jsp" /></c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
			  		<img src="../images/no_record.png" />
			  	</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div id="psi_load" style="display:none;">
<img width="18" src="${pageContext.request.contextPath}/Product/images/progress.gif"><span  id="psi_tip">正在导出,请稍等...</span>
</div>	
<script type="text/javascript">
	var k4 = new Kalendae.Input("insertTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
	 $(function(){
		$("#isbindbank option[value='${isbindbank}']").attr("selected",true);
	 });
</script>
</body>
</html>