<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 安卓app下载1</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
		<link href="${pageContext.request.contextPath}/Product/css/app_down.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
	<%-- <img id="phone_tips" width="100%"  style="display:none;  position: absolute;z-index: 100;" src="${pageContext.request.contextPath}/Product/images/tips.png" /> --%>  
		<div id="download_box">
			<a class="down_anzhuo" href="javascript:void(0);" onclick="downAndroid()" onmousedown="ee(this)" onmouseup="ee2(this)"></a>
		</div>
	</body>
	<script>
		function ee(obj){
			obj.style.background = 'url("images/android-d.png") no-repeat center center';
			obj.style.backgroundSize = '100% 100%';
		}
		function ee2(obj){
			obj.style.background = 'url("images/android.png") no-repeat center center';
			obj.style.backgroundSize = '100% 100%';
		}
		 function downAndroid(){
			 var wal = navigator.userAgent;
			 var num = wal.toLocaleLowerCase().indexOf("micromessenger", 0);
			 if(num!=-1){
				 window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.android.baiyimao";
				 return true;
				 //$("#phone_tips").attr("style","display:block;  position: absolute;z-index: 100;");
			 }
			 
			 
			 //window.location.href="http://www.pgyer.com/baiyimao";
			 window.location.href="http://www.baiyimao.com/download/baiyimao.apk";
			 
		 }
	</script>
</html>
