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
<style type="text/css">
.a {
  color: blue;
  text-decoration: underline;
}
</style>

<title>渠道投资统计</title>
</head>
<body>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
		<h3 style="text-align: center;">渠道投资统计</h3><br/>
		<div id="div_condition" style="text-align: center;" >
		
		<label>渠道号:<input type="text" name="channel" id="channel" value="${channel}" maxlength="5"  onkeypress="if(event.keyCode==13) {frm.click();}">
		<span>时间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}"  onkeypress="if(event.keyCode==13) {frm.click();}">
		<input type="button" value="search" id="frm" onclick="Byname(1)"></label>&nbsp;&nbsp;
		</div>
		<br>
		<p style="color: red;">* 红色列名,支持排序,点击即可</p>
		<table style="margin-top: 20px;width: 80%;" >
		<tr>
		<td>序号</td>
		<td>用户名</td>
		<td>姓名_投资次数</td>
		<td><a id="sort_title" onclick="BynameSort($(this))" style="color: red;">产品</a></td>
		<td>支付时间</td>
		<td><a id="sort_copies" onclick="BynameSort($(this))" style="color: red;">总投资(元)</a></td>
		<td><a id="sort_in_money" onclick="BynameSort($(this))" style="color: red;">投资本金(元)</a></td>
		<td><a id="sort_coupon" onclick="BynameSort($(this))" style="color: red;">投资券金额(元)</a></td>
		<td><a id="sort_province" onclick="BynameSort($(this))" style="color: red;">省份</a></td>
		<td>渠道号</td>
		</tr>
		<c:forEach items="${investChannelList}" var="item" varStatus="i">
		<tr>
		<td>${i.count + (currentPage-1)*100}</td>
		<td><a class="a"  target="_blank" href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(item['username'])}">${myel:jieMiUsername(item['username'])}</a></td> 
		<td>${item['username,investCount']}</td> 
		<td>${item['title']}</td>
	 	<td> <fmt:formatDate value="${item['pay_time']}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
	     <td><fmt:formatNumber value="${item['copies']}" pattern="#,##0.##"/></td>
		<td><fmt:formatNumber value="${item['in_money']}" pattern="#,##0.##"/></td>
		<td><fmt:formatNumber value="${item['coupon']}" pattern="#,##0.##"/></td>
		<td>${item['province']}</td>
		<td>${item['regist_channel']}</td>
		</tr>
		</c:forEach>
		</table>
		<c:choose>
			<c:when test="${investChannelList ne '[]' &&  investChannelList ne '' && investChannelList ne null}">
			<span>当前第${currentPage}页</span>
			<a onclick="Byname('1')">首页</a>
			<a onclick="Byname('${currentPage-1}')">上一页</a>
			<a onclick="Byname('${currentPage+1}')">下一页</a>
			</c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
			  		<img src="../images/no_record.png" />
			  	</div>
			</c:otherwise>
		</c:choose>
	</div>
</div> 
<script type="text/javascript">
	function Byname(currentPage){
		//alert(currentPage);
	var realname = $('#realname').val();
	//var  realnames = encodeURI(encodeURI(realname,"utf-8"),"utf-8");
		var url="${pageContext.request.contextPath}/Product/Admin/investors!investChannel.action?channel="+$("#channel").val()+"&insertTime="+$('#insertTime').val()+"&currentPage="+currentPage;

		window.location.href=url;
	}
	
	function BynameSort(obj){
		var tpName = obj.text();
		//if((tpName=="产品" || tpName=="产品↓") && "title ↓")
		var sortName = "";
		//alert(tpName);
		if(tpName.indexOf("产品")!=-1){
// 			if(tpName=="产品" || tpName=="产品↓")
// 				sortName="title 1";
// 			else
// 				sortName="title 0";
			sortName = ((tpName=="产品" || tpName=="产品↓") && "title 1") || "title 0";
		}
			
		else if(tpName.indexOf("总投资")!=-1)
			sortName = ((tpName=="总投资(元)" || tpName=="总投资(元)↓") && "copies 1") || "copies 0";
		else if(tpName.indexOf("投资本金")!=-1)
			sortName = ((tpName=="投资本金(元)" || tpName=="投资本金(元)↓") && "in_money 1") || "in_money 0";
		else if(tpName.indexOf("投资券金额")!=-1)
			sortName = ((tpName=="投资券金额(元)" || tpName=="投资券金额(元)↓") && "coupon 1") || "coupon 0";
		else if(tpName.indexOf("省份")!=-1)
			sortName = ((tpName=="省份" || tpName=="省份↓") && "province 1") || "province 0";
		//alert(sortName);
		//alert(((tpName=="产品" || tpName=="产品↓") && "title ↓") || "title ↑");
		//return false;
		//alert(currentPage);
	var realname = $('#realname').val();
	//var  realnames = encodeURI(encodeURI(realname,"utf-8"),"utf-8");
		var url="${pageContext.request.contextPath}/Product/Admin/investors!investChannel.action?channel="+$("#channel").val()+"&insertTime="+$('#insertTime').val()+"&currentPage="+${currentPage}+"&myOrder="+sortName;

		window.location.href=url;
	}
	
	function showSortName(){
		var tpName = "${myOrder}";
		var sortName = "";
		if(tpName.indexOf("title")!=-1)
			sortName = ((tpName=="title" || tpName=="title 1") && "产品↑") || "产品↓";
		else if(tpName.indexOf("copies")!=-1)
			sortName = ((tpName=="copies" || tpName=="copies 1") && "总投资(元)↑") || "总投资(元)↓";
		else if(tpName.indexOf("in_money")!=-1)
			sortName = ((tpName=="in_money" || tpName=="in_money 1") && "投资本金(元)↑") || "投资本金(元)↓";
		else if(tpName.indexOf("coupon")!=-1)
			sortName = ((tpName=="coupon" || tpName=="coupon 1") && "投资券金额(元)↑") || "投资券金额(元)↓";
		else if(tpName.indexOf("province")!=-1)
			sortName = ((tpName=="province" || tpName=="province 1") && "省份↑") || "省份↓";
		var newSortNameArray = tpName.split(" ");
		var newSortName = newSortNameArray[0];
		//alert(newSortName);
		var sortId = "sort_"+newSortName;
		//alert(("sort_us.province"==sortId));
		$("#"+sortId).text(sortName);
	}
	showSortName();
</script>
<script type="text/javascript">
	var k4 = new Kalendae.Input("insertTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
</script>
</body>
</html>	