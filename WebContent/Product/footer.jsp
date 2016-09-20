<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
    <style>
<!--
/*以上代码无用，复制以下代码*/
.return-top { width:45px; height: 45px; background:#fff; position:fixed; right:10px; bottom:10px; border:1px solid #ccc; overflow: hidden; cursor: pointer;}
.return-top span { display: block; width: 45px; height: 45px; background: url(${pageContext.request.contextPath}/Product/images/top_02.png) center center no-repeat;}
.return-top:hover  span{ display: none;}
.return-top i {display: none;text-decoration: none;}
.return-top:hover i { font-size: 12px; color:#222; text-align: center; display: block; line-break: 45px;  margin-top: 15px;font-style: normal; text-decoration: none;}
/*到这里结束*/
-->

</style>
<!-- 底部 -->
<div class="footer">
	<!--
	<div class="layout">
		<ul class="foo_top">
			<li>
				<ul id="index_help">
					<li class="a_title">关于我们</li>
					<li class="a_title">帮助中心</li>
					<li><a href="${pageContext.request.contextPath}/Product/about_us.jsp#ab_us_f2">公司简介</a></li>
					<%-- <li><a href="${pageContext.request.contextPath}/">常见问题</a></li> --%>
					<li><a href="${pageContext.request.contextPath}/Product/about_team.jsp#ab_us_f2">团队介绍</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/about_security.jsp#ab_us_f2">用户保障</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/indexNotice!loadMedia.action#ab_us_f2">媒体报道</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/about_us.jsp#mao">联系我们</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/about_help.jsp#ab_us_f2">常见问题</a></li>
				</ul>
			</li>
			<li>
				<span>
					<img src="${pageContext.request.contextPath}/Product/images/footer_app.jpg" />
					 <p>官方微信</p>
				</span>
				<span>
					<img src="${pageContext.request.contextPath}/Product/images/footer_weixin.png" />
					<p>手机App</p>
				</span>
			</li>
			<li class="f_top_li">
				<p>服务热线</p>
				<span>400-806-5993</span>
				<p>服务时间：9:00-18:00</p>
			</li>
			<div class="clea"></div>
		</ul>
	</div>	
	-->
	<div class="foo_bot">
		<ul>
			<li style="width:1200px;">友情链接：
				<a target="_blank" href="http://www.p2peye.com/">网贷天眼</a>
				<a target="_blank" href="http://www.p2pchina.com/">网贷中国</a>
				<a target="_blank" href="http://www.wangdaizhijia.com/">网贷之家</a>
				<a target="_blank" href="javascript:void(0)">理财资讯</a>
				<a target="_blank" href="http://www.wodai.com">我贷网</a>
				<a target="_blank" href="http://www.p2p110.com">网贷110</a>
				<a target="_blank" href="http://www.wangdaitiandi.com/">网贷天地</a>
				<a target="_blank" href="http://www.wdzx.com/">网贷中心</a>
				<a target="_blank" href="http://www.wdlm.cn">网贷联盟</a>
				<a target="_blank" href="http://www.wangdaidp.com/">网贷友情网</a>
				<a target="_blank" href="http://www.erongtu.com/">融途网</a>
			</li>
			<li>关于我们：
				<a href="#">帮助中心</a>
				<a href="#">公司简介</a>
				<a href="#">团队介绍</a>
				<a href="#">用户保障</a>
				<a href="#">媒体报道</a>
				<a href="#">联系我们</a>
				<a href="#">常见问题</a>
			</li>
		</ul>
		<div style="text-align:center;">
			<span>©&nbsp;Copyright2015-2016&nbsp;&nbsp;&nbsp;&nbsp;粤ICP备:15043489号-1&nbsp;&nbsp;&nbsp;&nbsp;深圳中泰理财金融服务有限公司&nbsp;&nbsp;&nbsp;&nbsp;版权所有</span><br>
			<a target="_blank" id='___szfw_logo___' href='https://search.szfw.org/cert/l/CX20150612010331010860' ><img src="${pageContext.request.contextPath}/Product/images/cert.jpg" /> </a>
			<a target="_blank" id='___szfw_logo___' href='https://ss.knet.cn/verifyseal.dll?sn=e16021544030062447r6r0000000&ct=df&a=1&pa=0.24548142555258312'><img src="${pageContext.request.contextPath}/Product/images/aboutus_temp11.jpg" /></a>
			<a target="_blank" href="http://www.zoyinnh.com/"><img src="${pageContext.request.contextPath}/Product/images/footer_lowyer.png" /></a>
			<a target="_blank" href="http://7805186.czvv.com/"><img src="${pageContext.request.contextPath}/Product/images/footer_lowyer2.jpg" /></a>
		</div>
	</div>
</div>
<!-- 底部 end-->

<div id="psi_load" style="display:none;">
	<img width="18" src="${pageContext.request.contextPath}/Product/images/progress.gif"><span  id="psi_tip">正在分析投资利息,请稍等...</span>
</div>
	<script src='${pageContext.request.contextPath}/Product/js/lazyload.js'></script>
<script>
(function(){document.getElementById('___szfw_logo___').oncontextmenu = function(){return false;}})();
//诚信网站
$(function(){
	//延迟加载图片
	$("img").lazyload({
		placeholder : "${pageContext.request.contextPath}/Product/images/onload.gif",				  
		effect : "fadeIn"
	}); 
	
		var ss = window.location.href.split("/").pop();
		var len = 0; //我的账户导航
		var len2 = 0;//头部导航
		if(ss.indexOf("index!showProductDetails")==0){
   			len2 = 1;
    	}
		else if(ss.indexOf("myAccount")==0){
   			len2 = 2;
    	}
   		else if(ss.indexOf("myCoupon")==0){
   			len = 1;
   			len2 = 2;
   		}
		else if(ss.indexOf("showUsersMoney")>=0){
			len = 2;
			len2 = 2;
		}
		else if(ss.indexOf("directBindPay")>=0){
			len = 2;
			len2 = 2;
		}
		else if(ss.indexOf("getMoney")>=0){
			len = 3;
			len2 = 2;
		}
		else if(ss.indexOf("fundRecord")==0){
			len = 4;
			len2 = 2;
		}
		else if(ss.indexOf("getInvestorsRecords")==16){
			len = 5;
			len2 = 2;
		}
		else if(ss.indexOf("getInvestorsRunningRecords")==16){
			len = 6;	
			len2 = 2;
		}
		else if(ss.indexOf("userInfo")==0){
			len = 7;
			len2 = 2;
		}
		else if(ss.indexOf("validate")==0){
			len = 8;	
			len2 = 2;
		}
		else if(ss.indexOf("loadProduct")==0){	
			len2 = 0;
		}
		else if(ss.indexOf("productCategory")==0){	
			len2 = 1;
		}
		else if(ss.indexOf("about_advantage")==0 ||ss.indexOf("about_help")==0 ||
				ss.indexOf("indexNotice")==0 || ss.indexOf("about_recruit")==0 ||
				ss.indexOf("about_security")==0 || ss.indexOf("about_team")==0 ||
				ss.indexOf("about_us")==0){	
			len2 = 3;
		}
	//alert(len2);
		
		$("#nav").find(".on").removeClass("on");
		$("#nav a").eq(len2).addClass("on");
		if($("#menu")){
			$('#menu_l li').eq(len).addClass("l_munu_on");
		}
   		
	   //头部导航和我的账户导航焦点结束
	   
	   $("#nav .nav_li4").mouseover(function(){
		   $(this).children("a").css({"background":"#3598db","color":"#fff"});
		   $("#nav .nav_li4 .index_hidelist").css("display","block");
	   }).mouseout(function(){
		   $("#nav .nav_li4 .index_hidelist").css("display","none");
		 if(len2!=3){	
			// alert(3);
			 $(this).children("a").css({"background":"#Fff","color":"black"});
		 }
	   });
		
	});
</script>

<!--返回顶部 -->
		<div class="return-top" id="top" >
			<a href="javascript:;" >
				<span></span>
				<i>顶部</i>
			</a>
		</div>
		<script>
			$(function(){ 
				$("#top").click(function(){
					$('body,html').animate({scrollTop:0},100);
				})
			})
		</script>
		<!--返回顶部-->

					<!-- WPA Button Begin -->
<!-- <script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwNzQwNV8yOTI4MzdfNDAwODgwNDMxMF8"></script> -->
<!-- WPA Button END -->
<!-- WPA Button Begin -->
<!-- <script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwNzQwNV8yOTc1MDJfNDAwODgwNDMxMF8"></script> -->
<!-- WPA Button End -->