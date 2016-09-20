<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加新活动标识符</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/Product/Admin/css/product.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script type="text/javascript">
function isSubmit(){
	var flag=$("#flag").val();
	var endTime=$("#endTime").val();
	var note=$("#note").val(); 
	if(flag==''){
		alert("请输入活动标识符");
		return false;
	}

	var exp = /^[0-9A-Za-z_]{3,10}$/;
	 var objExp=new RegExp(exp);
	if(objExp.test(flag)==false){
		alert("活动标识符由3-10个字母、数字或下划线组成");
		return false;
	}
	if(endTime==''){
		alert("结束时间不能为空");
		return false;
	}
	if(note==''){
		alert("备注不能为空");
		return false;
	}

}
</script>
</head>
<body>

<div class="center">	
	<jsp:include page="/Product/Admin/common/head.jsp"/>		
	<div class="main" align="center" style="text-align: center;">
	  <h3 align="center">添加新活动标识符</h3>
		<form action="${pageContext.request.contextPath}/Product/Admin/hdFlag!addHdFlag.action" onsubmit="return isSubmit();">
			<ul id="tab">
				<li>
					<label>活动标识符:</label>
					<input type="text" id="flag" name="hdFlag.flag" maxlength="10">*请输入4至10位的数字，大写字母或下划线
				</li>
				<li>
					<label>开始时间:</label>
					<input type="text" id="insertTime" name="insertTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>*必填
				</li>	
				<li>
					<label>结束时间:</label>
					<input type="text" id="endTime" name="endTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>*必填
				</li>				
				<li>
					<label>备        注:</label>
					<input type="text" id="note" name="hdFlag.note">*必填
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
            window.location.href="${pageContext.request.contextPath}/Product/Admin/operationManager/hdFlagAdd.jsp";

		}
	});
</script>
</html>