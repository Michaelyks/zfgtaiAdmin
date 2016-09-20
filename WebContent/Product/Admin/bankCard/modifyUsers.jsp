<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户帐号</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>

<script type="text/javascript">
	

	function modify(id){
			
            //获取新用户名;
			var name = "${name}";
			var applyId="${applyId}";
			//获取验证码;
			var vcode=$("#vcode_"+id).val();

			 if(vcode==null||""==vcode){
				 alert("填入的验证码不能为空");
				 return false;
			 }
			var url = "${pageContext.request.contextPath}/Product/Admin/usersApply!modifyUsers.action?";
 			var formData="applyId="+applyId;
			    formData+="&name="+name;
			    formData+="&vcode="+vcode;
			if(confirm("请问确认修改？")){
			 $.post(url,formData,function(data){
				 if(data.status=="ok"){
						//验证成功，更新用户帐号
						alert(data.json);
						window.location.href="${pageContext.request.contextPath}/Product/Admin/usersApply!loadUsersApplyList.action";
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

<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>修改用户帐号</h3>
	<table style="width:100%;margin-top: 20px;text-align: center;" border="1px;">
		<tr>
			<td width="100px;">用户ID</td>
			<td width="100px;">新用户名</td>
			<td width="100px;">旧用户名</td>
			<td width="100px;">姓名</td>
			<td width="100px;">所属省份</td>
			<td width="100px;">所属城市</td>
			<td width="100px;">电话类型</td>
			<td width="200px;">注册时间</td>
			<td width="100px;">注册平台</td>
			<td width="200px;">填写验证码</td>
			<td width="200px;">操作</td>
		</tr>
			<tr>				
				<td>${users.id}</td>
				<td>${name}</td>
				<td>${myel:jieMiUsername(users.username)}</td>
				<td>${users.usersInfo.realName}</td>
				<td>${users.province}</td>
				<td>${users.city}</td>
				<td>${users.cardType}</td>
				<td>${users.insertTime}</td>
				<td>
					<c:if test="${users.registPlatform == 0}">web端注册</c:if>
					<c:if test="${users.registPlatform == 1}">Android移动端</c:if>
					<c:if test="${users.registPlatform == 2}">IOS移动端</c:if>
					<c:if test="${users.registPlatform == 3}">微信注册</c:if>
				
				</td>
				<td>
                    <input class="dis_${users.id}" id="vcode_${users.id}"  name="vcode" maxlength="6"/>
				</td>
				<td>
					<a id="modify_${users.id}" class="a" href="javascript:modify('${users.id}')">保存</a>
					<p>&nbsp;</p>
				</td>
			</tr>
	</table>
	</div>
	
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