<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 安卓app下载71</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
		<link href="${pageContext.request.contextPath}/Product/css/app_down.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
	<img id="phone_tips" width="100%"  style="display:none;  position: absolute;z-index: 100;" src="${pageContext.request.contextPath}/Product/images/tips.png" />  
		<div id="download_box">
			<a class="down_anzhuo" href="javascript:void(0);" onclick="downAndroid()" onmousedown="ee(this)" onmouseup="ee2(this)"></a>
		</div>
	</body>
	 <script type="text/javascript">
/*
* 智能机浏览器版本信息:
*
*/
var browser = {
versions: function() {
var u = navigator.userAgent, app = navigator.appVersion;
return {//移动终端浏览器版本信息 
trident: u.indexOf('Trident') > -1, //IE内核
presto: u.indexOf('Presto') > -1, //opera内核
webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
iPad: u.indexOf('iPad') > -1, //是否iPad
webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
};
}(),
language: (navigator.browserLanguage || navigator.language).toLowerCase()
}
 
if (browser.versions.ios || browser.versions.iPhone || browser.versions.iPad) {
window.location="${pageContext.request.contextPath}/Product/app_ios.jsp";
}
else if (browser.versions.android) {
//window.location="${pageContext.request.contextPath}/Product/app_apk_2.jsp";
}else{
	//alert(navigator.userAgent);
}
 
// document.writeln("语言版本: " + browser.language);
// document.writeln(" 是否为移动终端: " + browser.versions.mobile);
// document.writeln(" ios终端: " + browser.versions.ios);
// document.writeln(" android终端: " + browser.versions.android);
// document.writeln(" 是否为iPhone: " + browser.versions.iPhone);
// document.writeln(" 是否iPad: " + browser.versions.iPad);
// document.writeln(navigator.userAgent);
 
</script>
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
				 $("#phone_tips").attr("style","display:block;  position: absolute;z-index: 100;");
			 }
			 //window.location.href="http://www.pgyer.com/baiyimao";
			 window.location.href="http://www.baiyimao.com/download/baiyimao_31.apk";
			 
		 }
	</script>
</html>
