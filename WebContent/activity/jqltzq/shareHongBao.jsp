<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<style type="text/css">
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

<title>红包发放记录</title>
</head>
<body>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
		<h3 style="text-align: center;">红包发放记录</h3><br>
		<div id="div_condition" style="text-align: center;" >
		<label>用户名:<input  class="input-text"  type="text" name="username" id="username" value="${username}">
		<span>日期:</span> <input id="insertTime"  class="input-text" name="insertTime" type="text" value="${insertTime}">
		<input type="button" value="查询" id="frm" onclick="Byname()" class="input-button"></label>&nbsp;&nbsp;
		</div>
		<div class="mes-div">
		<span class="span">红包发放总金额：<fmt:formatNumber value="${(all.inMoney+all.inviteInMoney+all.inviteInMoneys*2)* 0.01}" pattern="#,##0.##"/> </span>
		<span class="span">拆红包注册人数：${all.numner} </span>
	</div>
		<br><br>
		<table  id="tHead" class="ct-table" border="1" cellspacing="0" cellpadding="0" style="text-align: center;width:1200px">
		<tr>
		<td>序号</td>
		<td>用户名</td>
		<td>拆红包金额（元）</td>
		<td>分享好友获取红包金额（30%）元</td>
		<td>好友首投奖励（元）</td>
		<td>发放日期</td>
		<td>首投金额（元）</td>
		
		</tr>
		
		<tr>
				<td>合计</td>
				<td></td>
				<td><fmt:formatNumber value="${all.inMoney * 0.01}" pattern="#,##0.##"/></td>
				 <td><fmt:formatNumber value="${all.inviteInMoney * 0.01}" pattern="#,##0.##"/></td>
			     <td><fmt:formatNumber value="${all.inviteInMoneys * 0.01}" pattern="#,##0.##"/></td>
				<td></td>
				 <td><fmt:formatNumber value="${all.inMoneys*0.01}" pattern="#,##0.##"/></td>
		</tr>	
		<c:forEach items="${pageUtil.list}" var="item" varStatus="i">
		<tr>
		<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
		<td><a class="a"  target="_blank" href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item.username)}">${myel:jieMiUsername(item.username)}</a></td>
		 <td><fmt:formatNumber value="${item.inMoney * 0.01}" pattern="#,##0.##"/></td>
		 <td><fmt:formatNumber value="${item.inviteInMoney * 0.01}" pattern="#,##0.##"/></td>
		  <td><fmt:formatNumber value="${item.inviteInMoneys * 0.01}" pattern="#,##0.##"/></td>
	 <td><fmt:formatDate value="${item.insertTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		  <td><fmt:formatNumber value="${item.inMoneys*0.01}" pattern="#,##0.##"/></td>
			</tr>
		</c:forEach>
		</table>
	    <c:choose>
			<c:when test="${pageUtil.list ne '[]' &&  pageUtil.list ne '' && pageUtil.list ne null}"><jsp:include page="/Product/page.jsp" /></c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
			  		<img src="../images/no_record.png" />
			  	</div>
			</c:otherwise>
		</c:choose>
	</div>
</div> 
<script type="text/javascript">
$(function(){
	$("body").keydown(function(e){
		var ev = document.all ? window.event : e;
		 if(ev.keyCode==13) 
		 {
			 $("#frm").click();
		 }
	
	});
});


	function Byname(){
		var url="${pageContext.request.contextPath}/Product/Admin/shareHongBao!showShareHongBao.action?username="+$("#username").val()+"&insertTime="+$('#insertTime').val();
		window.location.href=url;
	}
	
		var k4 = new Kalendae.Input("insertTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
		

</script>


</body>
</html>	