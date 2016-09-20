<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		<meta name="apple-touch-fullscreen" content="yes">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<title>加群领投资券</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link rel="stylesheet" href="css/demo.css" />
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
		
		<script type="text/javascript">
		
		function doActivity(){
			var mobileNum = $("#mobileNum").val();
			var xlhNum = $("#xlhNum").val();
			//var mobileNumReg = /^0?1[3|4|5|8][0-9]\d{8}$/;
			var xlhNumReg = /^\w{4,50}$/;
			if (!isPhoneNumber(mobileNum)) {
				alert("请输入正确的手机号码");
			    return false;
			} else if (!xlhNumReg.test(xlhNum)) {
				alert("序列号范围是4-50个字符");
			    return false;
			} else {
				$.ajax({
					type : "post",
					async : false,
					url : "${pageContext.request.contextPath}/Product/activityClient!doActivity.action?mobileNum="+mobileNum+"&xlhNum="+xlhNum,
					success : function(data) {
						alert(data.json);
						if(data.status=="ok"){
							$("#mobileNum").val("");
							$("#xlhNum").val("");
						}
						
					}
				});
			}
		}
		
		</script>
	</head>
	<body>
		<div class="top-img">
			<img src="img/fb.png" class="logo" /> <img src="img/regaddq.png" />
		</div>
		<div class="form-box">
			<form action="#" method="post">
				<div class="line">
					<input type="text" name="mobileNum" id="mobileNum" class="line-tel" placeholder="请输入手机号码"  required="required" />
				</div>
				<div class="line">
					<input type="text" name="xlhNum" id="xlhNum" class="line-q" placeholder="请输入序列号" required="required" />
				</div>
				<div class="line">
					<input type="button" class="line-sub" onclick="doActivity();"  name="submit" value="立即领取" />
				</div>
			</form>
		</div>
		<div class="rule">
			<p>活动规则:</p>
			<p>1.从未参与过此活动的用户.</p>
			<p>2.每个用户领取一次.</p>
			<p>3.投资券有效期为,即日起,为期10天的有效期;</p>
			<p>4.本次活动解释权归深圳中泰理财金融服务有限公司所有.</p>
		</div>
</body>
</html>
