<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - IOS下载</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
		<link href="${pageContext.request.contextPath}/Product/css/app_down.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
	<%-- <img id="phone_tips" width="100%"  style="display:none;  position: absolute;z-index: 100;" src="${pageContext.request.contextPath}/Product/images/tips.png" /> --%>  
		<div id="download_box">
			<a class="down_IOS" href="javascript:void(0);" onclick="downIphone()" onmousedown="ee(this)" onmouseup="ee2(this)"></a>
		</div>
	</body>
	<script>
		function ee(obj){
			obj.style.background = 'url("images/IOS-D.png") no-repeat center center';
			obj.style.backgroundSize = '100% 100%';
		}
		function ee2(obj){
			obj.style.background = 'url("images/IOS.png") no-repeat center center';
			obj.style.backgroundSize = '100% 100%';
		}
		 function downIphone(){
			 var wal = navigator.userAgent;
			 var num = wal.toLocaleLowerCase().indexOf("micromessenger", 0);
			 var ua = window.navigator.userAgent.toLowerCase();
			   // alert(ua);
			 if(ua.match(/MicroMessenger/i) == 'micromessenger'){
				 window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.android.baiyimao";
			 		return true;
			 }
			 else{
			 
			 window.location.href="https://itunes.apple.com/cn/app/bai-yi-mao-li-cai/id998821116?mt=8";
		 }}
	</script>
</html>
