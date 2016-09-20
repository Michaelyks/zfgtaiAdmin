<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加新管理员</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
</head>
<script language="javascript" type="text/javascript">
$("#password2").blur(function() {//失去焦点是触发
	var password=$("#password").val();
	 var password2 = $("#password2").val();
	 if(password2==null||""==password2){
		 alert("请确认密码");
		 return false;
	 }
	 if(password!=password2){
			alert("确认密码不一致");
			return false;
	 }
	 }
 );
function isSubmit(){
	var username=$("#username").val();
	var password=$("#password").val(); 	
	var password2=$("#password2").val();
	var deptId=$("#deptId").val(); 
	if(username==''){
		alert("请填写用户名");
		return false;
	}
	var exp = /^[0-9A-Za-z_]{6,16}$/;
	 var objExp=new RegExp(exp);
	if(objExp.test(username)==false){
		alert("用户名由6-16个字母、数字或下划线组成");
		return false;
	}
	if(password==''){
		alert("请填写密码");
		return false;
	}
		
	if(password2==''){
		alert("请填写确认密码");
		return false;
		}
	if(deptId==''){
		alert("请填写部门");
		return false;
		}
	};

</script>
<body>

<div class="center">	
	<jsp:include page="/Product/Admin/common/head.jsp"/>		
	<div class="main" align="center" style="text-align: center;">
	  <h3 align="center">添加新管理员</h3>
		<form action="${pageContext.request.contextPath}/Product/Admin/usersAdmin!usersAdminAdd.action" onsubmit="return isSubmit();">
			<ul id="tab" align="center">
				<li>
					<label>用户名</label>
					<label><input type="text" id="username" maxlength="16" name="admin.username"></label>
				</li>
				<li>
					<label>设置密码</label>
					<label><input type="password" id="password1" maxlength="16" name="admin.password"></label>
				</li>
				<li>
					<label>确认密码</label>
					<label><input id="password2" maxlength="16" type="password" name="password2" /></label>
				</li>
				
<%-- 				<li>
					<label>角色设置</label>
					<label>
						<select id="rolesId" name="usersAdmin.rolesId" onchange="query()">
							<c:forEach items="${list}"  var="item" varStatus="i">
							     <option value="usersAdmin.rolesId">${item.roleName}</option>
							</c:forEach>
						</select>
					</label>
				</li> --%>
				<li>
					<label>用户类型</label>
					<label><select id="userType" name="admin.userType">
					   <c:if test="${usersAdmin.userType eq -1}">						
							<option value="0">管理员</option>
						</c:if>
							<option value="1">普通人员</option>
						</select>
		              </label>
				</li>
				<li>
					<label>所属部门</label>
					<label>
					  <select id="deptId" name="admin.deptId"> 
							<c:forEach var="depts" items="${deptList}" > 
							    <option value="${depts.id}">${depts.deptName}</option> 
							</c:forEach> 
					</select></label>
				</li>
				<li>
					<label colspan="2"><input type="submit" value="提交"></label>
				</li>
			</ul>
		</form>
	</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		var message='${message}';
		if(message!=''){
			alert(message);
			if(message=='添加成功'){
				window.location.href="${pageContext.request.contextPath}/Product/Admin/usersAdmin!findUsersAdminList.action";				
			}else{
				window.location.href="${pageContext.request.contextPath}/Product/Admin/usersAdmin!toUsersAdminAdd.action";
			};
		};
	});
</script>
</html>