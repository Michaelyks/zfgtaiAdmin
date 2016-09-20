<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<!doctype html>
<html> 
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"  />
		<meta  name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" />
		<title>APP下载</title> 
		<style>
			*{margin:0;padding:0}
			img{border:none 0;}
			body{font-family:"Microsoft Yahei";}
			#wrapper{position:relative}
			#wrapper img{width:100%;height:100%}
			#wrapper .gy{text-align:center;color:#fff;}
			/* @media screen and (max-width:640px){
				.gy{width:100%;position:absolute;top:220px}
				.gy p{font-size:42px}
				.gy p span{color:#fffc00;font-size:42px}
				.gy p+p{padding:6px 0}
				.gy span{font-size:24px}
			    .dl{position:absolute;bottom:135px;}
				.dl a{text-decoration:none;margin-left:43px;color:#fff;display:block;*display:inline;display:inline-block;width:191px;padding-left:15px;height:62px;line-height:62px;text-align:center;background:url("images/d2.jpg")}
				.dl a + a{background:url("images/d3.png");margin-left:120px;width:180px;padding-left:26px}
			} */
.gy{width:100%;position:absolute;top:220px}
.gy p{font-size:42px}
.gy p span{color:#fffc00;font-size:42px}
.gy p+p{padding:6px 0}
.gy span{font-size:24px}
   .dl{position:absolute;bottom:135px;}			
.dl a{text-decoration:none;margin-left:43px;color:#fff;display:block;*display:inline;display:inline-block;width:191px;padding-left:15px;height:62px;line-height:62px;text-align:center;background:url("images/d2.png")}
.dl a + a{background:url("images/d3.png");margin-left:120px;width:180px;padding-left:26px}			
@media only screen and (max-width:300px){
	body { font-size:6px; }
}
@media only screen and (min-width:301px){
	body { font-size:10px; }
}
@media only screen and (min-width:350px){
	body { font-size:12px; }
}
@media only screen and (min-width:380px){
	body { font-size:14px; }
}
@media only screen and (min-width:500px){
	body { font-size:16px; }
}
@media only screen and (min-width:500px){
	body { font-size:16px; }
}
@media only screen and (min-width:550px){
	body { font-size:18px; }
}
@media only screen and (min-width:600px){
	body { font-size:20px; }
}			
			
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head> 
	<body>
	<img id="phone_tips" width="100%"  style="display:none;  position: absolute;z-index: 100;" src="${pageContext.request.contextPath}/Product/images/tips.png" />  
		<div id="sdf">
		<img alt=""  style="width:100%;height:100%;position: absolute;" src="${pageContext.request.contextPath}/Product/images/down_index.png"> 	
			<div style="margin-left:2em;">				
				<%-- <img alt="iphone版下载" style="width:10em;height:3em;position: absolute;" src="${pageContext.request.contextPath}/Product/images/down_iphone.png">
				<img alt="Android版下载" onclick="downAndroid()"  style="margin-left:2.5em;width:10em;height:3em;position: absolute;" src="${pageContext.request.contextPath}/Product/images/down_android.png"> --%>
				<img alt="iphone版下载" onclick="downIphone()" style="left: 18%;  top: 60%;height:5em;position: absolute;" src="${pageContext.request.contextPath}/Product/images/down_iphone.png">
				<img alt="Android版下载" onclick="downAndroid()"  style="left: 18%;  top: 80%;height:5em;position: absolute;" src="${pageContext.request.contextPath}/Product/images/down_android.png">
			</div>
		</div> 
	</body> 
 <script type="text/javascript">
 function downAndroid(){
	 var wal = navigator.userAgent;
	 var num = wal.toLocaleLowerCase().indexOf("micromessenger", 0);
	 if(num!=-1){
		 $("#phone_tips").attr("style","display:block;  position: absolute;z-index: 100;");
	 }
	 //window.location.href="http://www.pgyer.com/baiyimao";
	 window.location.href="http://www.baiyimao.com/download/baiyimao.apk";
	 
 }
 
 function downIphone(){
	 var wal = navigator.userAgent;
	 var num = wal.toLocaleLowerCase().indexOf("micromessenger", 0);
	 $("#phone_tips").attr("style","display:block;  position: absolute;z-index: 100;");
	 window.location.href="https://itunes.apple.com/cn/app/bai-yi-mao-li-cai/id998821116?mt=8";
 }
 
 
 </script>
</html> 
