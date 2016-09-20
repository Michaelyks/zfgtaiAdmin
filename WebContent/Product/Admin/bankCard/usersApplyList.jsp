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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<title>帐号变更列表</title>
<script type="text/javascript">
	function toModifyUsers(id){
		var status=$("#modify_"+id).text();
		if("修改"==status){
			$("#modify_"+id).text("保存");
			$(".dis_"+id).removeAttr("disabled");
			$(".dis_"+id).removeAttr("readonly");
		}else{			
			//获取要修改的用户名;
			var username = $("#username_"+id).val();
			if(username==""||username==null){
				alert("用户名不能为空");
				return false;
			}
			 if(!isPhoneNumber(username)){
				 alert("填入的用户帐号不是手机号格式！");
				 return false;
			 }
			var url = "${pageContext.request.contextPath}/Product/Admin/usersApply!sendYzm.action?name="+username;
/*  			var formData="applyId.id="+id;
			    formData+="&name="+applyId; */
			 $.post(url,null,function(data){
				 if(data.status=="ok"){
						//验证码发送成功，跳转到验证页面
						alert(data.json);
						$("#modify_"+id).text("修改");
						$(".dis_"+id).attr("disabled","disabled");
						$(".dis_"+id).attr("readonly","readonly");
						window.location.href="${pageContext.request.contextPath}/Product/Admin/usersApply!toModifyUsers.action?applyId="+id+"&name="+username;
				 }else{
					 alert(data.json);
					 return false;
				 }
			 });
		}
	}
</script>
<style type="text/css">
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>
</head>
<body>
<%-- <jsp:include page="top.jsp" /> --%>
<div class="center">			
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<div class="main" align="center">
		<h1 style="text-align: center;">帐号变更列表</h1>
		<div id="div_condition">
		<label>用户名:<input type="text" name="name" id="name" value="${name}" maxlength="11">
		<span>申请时间:</span> <input id="insertTime" name="insertTime" type="text" value="${insertTime}">
		<input type="button" value="search" id="frm" onclick="Byname()"></label>&nbsp;&nbsp;
		<label><input type="radio" id="" value="all" name="status" checked="checked">全部</label>&nbsp;&nbsp;
		<label><input type="radio" value="1" name="status">待处理</label>&nbsp;&nbsp;
		<label><input type="radio" value="2" name="status">已处理</label>&nbsp;&nbsp;
		</div>
		
		<table style="width:100%;margin-top: 20px;text-align: center;" border="1px;">
		<tr>
		<td>序号</td>
		<td>用户名</td>
		<td>姓名</td>
		<td>申请时间</td>
		<td>处理时间</td>
		<td>操作人</td>
		<td>备注</td>
		<td>状态</td>
		<td>操作</td>
		</tr>
		<c:forEach items="${list}" var="list" varStatus="i">
		<tr>
		<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
		<td><input class="dis_${list.id}" id="username_${list.id}" readonly="readonly" disabled="disabled" value="${myel:jieMiUsername(list.users.username)}" /></td> 
		<td>${list.users.usersInfo.realName}</td>
		<td><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td><fmt:formatDate value="${list.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td>${myel:jieMiUsername(list.usersAdmin.username)}</td>
		<td>${list.note}</td>
		<td>${list.statusChina}</td>
		<td>
		<c:if test="${list.status eq 1}">
		      <a id="modify_${list.id}" class="a" href="javascript:toModifyUsers('${list.id}');">修改</a>
		</c:if>			
		</td>
		</tr>
		</c:forEach>
		</table>
		<c:choose>
			<c:when test="${list ne '[]' &&  list ne '' && list ne null}">
			<jsp:include page="/Product/page.jsp" /></c:when>
			<c:otherwise>
				<div style="text-align: center;margin-top: 15px;">
			  		<img src="../images/no_record.png" />
			  	</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<script type="text/javascript">
$("#div_condition input[name='status']").click(function(){
	   window.location.href="${pageContext.request.contextPath}/Product/Admin/usersApply!loadUsersApplyList.action?name="+$("#name").val()+"&status="+$('input[name="status"]:checked').val()+"&insertTime="+$('#insertTime').val();
 });
	$("#div_condition input[name='status']").each(function(){
		if($(this).attr("value")=="${status}" || "${status}"==""){
			$(this).attr("checked",true);
		}
	});
	function Byname(){
		window.location.href="${pageContext.request.contextPath}/Product/Admin/usersApply!loadUsersApplyList.action?name="+$("#name").val()+"&status="+$('input[name="status"]:checked').val()+"&insertTime="+$('#insertTime').val();
	}
	
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