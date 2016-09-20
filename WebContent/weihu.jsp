<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<meta charset="utf-8" />
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css">
</head>
<div class="topbar">
			<div class="wrap">
				<div class="phone_num"><img src="${pageContext.request.contextPath}/Product/images/tel_ico.png" />400-806-5993<span>(工作时间：早上9：00-下午18:00)</span></div>
				<ul>
					<li><a class="ico4">退出</a></li>
				</ul>
			<p>欢迎来到中泰理财！<a >登录</a> | <a >注册</a></p>
		</div>
</div>
<div class="header">
	<div class="wrap cf">
		<div class="logo">
			<a ><img
				src="${pageContext.request.contextPath}/Product/images/logo.png" /></a>
		</div>
		<ul class="nav" style="padding-top:8px">
			<li><a id="top_index"
				 class="on">首页</a></li>
			<li><a id="top_category"
				>理财产品</a></li>
			<li><a id="top_account"
				>我的账户</a></li>
			<!--<li><a href="#">帮助中心</a></li>-->
			<li><a id="top_aboutUs" >关于我们</a></li>
		</ul>
	</div>
</div>
<div style="text-align: center;margin-top: 200px;margin-bottom: 330px;">
<img src="${pageContext.request.contextPath}/Product/images/weihu.png" />
</div>

<div class="footer">
	<div class="botnav">
    	<div class="botnav_c">
    		<div class="b_logo">
    			<img src="${pageContext.request.contextPath}/Product/images/botom_logo.png" />
    		</div>
    		<div class="kefu">
    			<p class="rex">客服热线</p>
    			<p>400-806-5993</p>
    			<span>(工作日 9:00-17:30）</span>
    		</div>
    		<div class="zhanghu">
    			<img src="${pageContext.request.contextPath}/Product/images/d1.jpg" />
    			<span class="wei_x">
    				<a class="wei_gf">官方微信</a><br/>
    				微信扫一扫<br/>
    				信息早知道
    			</span>
    			<img src="${pageContext.request.contextPath}/Product/images/d1.jpg" />
    			<span class="wei_x">
    				<a class="wei_gf"><i></i>中泰理财手机 APP</a><br/>
    				<span>手机理财随时随地</span><br/>
    				<span>一手掌握明明白白</span>
    			</span>
    			
    		</div>
    		<div class="chanp">
    			<p class="licai_zs">理财助手</p>
    			<ul>
    				<li><a href="http://www.eastmoney.com/" target="_blank">东方财富网</a></li>
    				<li><a href="http://finance.sina.com.cn/" target="_blank">新浪财经</a></li>
    				<li><a href="http://www.hexun.com/" target="_blank">和讯财经</a></li>
    				<li><a href="http://www.jrj.com.cn/" target="_blank">金融界</a></li>
    				<li><a href="http://money.163.com/" target="_blank">网易财经</a></li>
    				<li><a href="http://finance.qq.com/" target="_blank">腾讯财经</a></li>
    				<li><a href="http://business.sohu.com/" target="_blank">搜狐财经</a></li>
    				<li><a href="http://www.ce.cn/" target="_blank">中国经济网</a></li>
    				<div class="clea"></div>
    			</ul>
    		
    		</div>
    		<div class="clea"></div>
    	</div>
    </div>
  <div class="copyright">
    	<div class="copy">Copyright 2015-2016, 中泰理财 粤ICP备:15043489号-1</div>
        <ul>
        	<li class="copy1">
            	<p>公共信息安</p>
                <p>全网络监督</p>
            </li>
            <li class="copy2">
            	<p>公共信息安</p>
                <p>全网络监督</p>
            </li>
            <li class="copy3">
            	<p>公共信息安</p>
                <p>全网络监督</p>
            </li>
            <div class="clea"></div>
        </ul>
    </div>

</div>
<script type="text/javascript">
	function choosePage(menu) {
		if ("1" == menu) {
			$("#top_index").addClass("on");
			$("#top_category").removeClass("on");
			$("#top_account").removeClass("on");
			$("#top_aboutUs").removeClass("on");
		} else if ("2" == menu) {
			$("#top_index").removeClass("on");
			$("#top_category").addClass("on");
			$("#top_account").removeClass("on");
			$("#top_aboutUs").removeClass("on");
		} else if ("3" == menu) {
			$("#top_index").removeClass("on");
			$("#top_category").removeClass("on");
			$("#top_account").addClass("on");
			$("#top_aboutUs").removeClass("on");
		}else if("4" == menu){
			$("#top_index").removeClass("on");
			$("#top_category").removeClass("on");
			$("#top_account").removeClass("on");
			$("#top_aboutUs").addClass("on");
		}
	}
</script>
</html>