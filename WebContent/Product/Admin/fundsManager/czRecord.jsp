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
<title>充值记录</title>
<script type="text/javascript">
	function queryCz(requestId,type,insertTime){
		var url="";
		if(type=='3'||type=='4'){//连连支付
			url="${pageContext.request.contextPath}/Product/Admin/recharge!CzMoneyQuery.action?requestId="+requestId+"&type="+type+"&insertTime="+insertTime;
		}else{
			url="${pageContext.request.contextPath}/Product/Admin/recharge!CzMoneyArrive.action?requestId="+requestId+"&type="+type;
		}
		$.ajax({ 
			url: url,
			success: function(data){
	       		alert(data.json);
	      }});
	}
</script>
<style type="text/css">
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
<script type="text/javascript">
function ireportDo(){
	    var name=document.getElementById("name").value;
	    var insertTime=document.getElementById("insertTime").value;
	    var status =  $('input[name=status]:checked').val();
 		var url='${pageContext.request.contextPath}/Product/Admin/recharge!iportCzTable.action?currentPage=${currentPage}&name='+name+'&insertTime='+insertTime+'&status='+status;
 		var list="${czRecordList}";
 		if(list!=null&&list!="[]"){
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
}
</script> 
</head>
<body>
<%-- <jsp:include page="top.jsp" /> --%>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
		<h1 style="text-align: center;">充值记录</h1>
		<div id="div_condition">
		<label>用户名:<input type="text" name="name" id="name" value="${name}" maxlength="11">
		<span>充值时间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
		<input type="button" value="search" id="frm" onclick="Byname()"></label>&nbsp;&nbsp;
		<label><input type="radio" id="" value="all" name="status" checked="checked">全部</label>&nbsp;&nbsp;
		<label><input type="radio" value="0" name="status">待充值</label>&nbsp;&nbsp;
		<label><input type="radio" value="1" name="status">充值成功</label>&nbsp;&nbsp;
		<label><input type="radio" value="2" name="status">充值失败</label>&nbsp;&nbsp;
		<label><input type="radio" value="3" name="status">宝付充值成功,数据库插入失败</label>&nbsp;&nbsp;
		<input type="button" value="导出报表" onclick="ireportDo()">
<!-- 		<select name="type">
			<option value="">全部</option>
			<option value="0">易宝</option>
			<option value="1">连连</option>
		</select> -->
		</div>
		
		<table style="width:100%;margin-top: 20px;text-align: center;" border="1px;">
		<tr>
		<td>序号</td>
		<td>流水号</td>
		<td>用户名</td>
		<td>持卡人姓名</td>
		<td>支付方式</td>
		<td>充值金额(元)</td>
		<td>申请充值时间</td>
		<td>到帐时间</td>
		<td>平台订单号</td>
		<td>交易流水号</td>
		<td>充值状态</td>
		<td>备注</td>
		<td>查看充值情况</td>
		</tr>
		<c:forEach items="${czRecordList}" var="list" varStatus="i">
		<tr>
		<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
		<td>${list.recordNumber}</td>
		<td><a class="a" href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action?username=${myel:jieMiUsername(list.users.username)}">${myel:jieMiUsername(list.users.username)}</a></td> 
		<td>${list.users.usersInfo.realName}</td>
		<td>
		<c:choose>
			<c:when test="${list.type eq '1'}">宝付快捷支付</c:when>
			<c:when test="${list.type eq '3'}">连连认证支付</c:when>
			<c:when test="${list.type eq '4'}">连连网银支付</c:when>
			<c:otherwise>宝付快捷支付</c:otherwise>
		</c:choose>
		</td>
		<td><fmt:formatNumber value="${list.money * 0.01}" pattern="#,##0.##"/></td>
		<td><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td><fmt:formatDate value="${list.checkTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td>${list.orderId}</td>
		<td>${list.ybOrderId}</td>
		<td>${list.czzt}</td>
		<td>${list.errCause}&nbsp;${list.note}</td>
		<td>
			<%-- <c:choose>
			<c:when test="${list.type eq '1'}">网银支付<br>暂不支持查询</c:when>
			<c:otherwise><a class="a" href="javascript:queryCz('${list.orderId}','${list.type}');">查看充值是否成功</a></c:otherwise>
			</c:choose> --%>
			<a class="a" href="javascript:queryCz('${list.orderId}','${list.type}','<fmt:formatDate value="${list.insertTime}" pattern="yyyyMMddHHmmss"/>');">查看充值是否成功</a>
		</td> 
		</tr>
		</c:forEach>
		</table>
		<c:choose>
			<c:when test="${czRecordList ne '[]' &&  czRecordList ne '' && czRecordList ne null}">
			<jsp:include page="/Product/page.jsp" /></c:when>
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
		$("#div_condition input[name='status']").click(function(){
			window.location.href="${pageContext.request.contextPath}/Product/Admin/recharge!rechargeMoneyRecord.action?name="+$("#name").val()+"&status="+$('input[name="status"]:checked').val()+"&insertTime="+$('#insertTime').val();
		});
			$("#div_condition input[name='status']").each(function(){
				if($(this).attr("value")=="${status}" || "${status}"==""){
					$(this).attr("checked",true);
				}
			});
			function Byname(){
				window.location.href="${pageContext.request.contextPath}/Product/Admin/recharge!rechargeMoneyRecord.action?name="+$("#name").val()+"&status="+$('input[name="status"]:checked').val()+"&insertTime="+$('#insertTime').val();
			}
		</script>
		<script type="text/javascript">
	var k4 = new Kalendae.Input("insertTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
	//为input框绑定回车事件，当用户在文本框中输入搜索关键字时，按回车键，即可触发search():

	//回车绑定
	$("#name,#insertTime").keydown(function(event){
				if(event.keyCode==13){
					Byname();
				}
			});
</script>
</body>
</html>