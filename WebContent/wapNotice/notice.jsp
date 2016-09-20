<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>最新动态</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/wapNotice/css/reset.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/wapNotice/css/layout.css">
<link href="${pageContext.request.contextPath}/wapNotice/css/jquery.circliful.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/wapNotice/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wapNotice/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
<script src="${pageContext.request.contextPath}/wapNotice/js/rem.js"></script>
<script src="${pageContext.request.contextPath}/wapNotice/js/js.js"></script>

<script type="text/javascript"> 

var httpUrl='${systemConfig.httpUrl}';
var fileName='${systemConfig.fileName}';
$(document).ready(function(){
	var currentPage = 2;
	var pageCount = "${pageCount}";
	/* alert(systemConfig) */
	$("#medialoadMore").click(function(){
		//alert(3);
		function chageDiv(){
			$("#medialoadMore").text("已加载完全部");
			$("#medialoadMore").css("cursor","auto");
			$("#medialoadMore").css("color","gray");
			$("#medialoadMore").unbind("click");
		}	
		//alert(currentPage);
		if(currentPage>parseInt(pageCount)){
			//alert("当前页大于总页数");
			chageDiv();
			return false;
		}
		var url = "${pageContext.request.contextPath}/Product/indexNotice!loadMediaNoticeByAjax.action?"; 
		/* $.post(url, function(data){
			   alert("Data Loaded: " + data);
		 }); */
		var su = "";
		$.ajax({
			type : "post",
			url : url,
			async: true,
			data:"currentPage="+currentPage,
			beforeSend : function(XMLHttpRequest){
				$("#medialoadMore").html("<a style='margin-top: -11px'><img  src='${pageContext.request.contextPath}/wapNotice/images/progress.gif'></a>");
			},
			success : function(data, textStatus) {
				su = data;
				//alert("数据: "+data);
				//alert(su);
			},
			complete : function(XMLHttpRequest, textStatus) {
				$("#medialoadMore").html("<a>查看更多</a>");
				//alert("完成");
				//alert(textStatus);
				if ("ok" == su.status) {
					//解析json,进行拼接;
					//alert(su);
					//alert(su.json);
					currentPage++;
					appendMediaNoticeNew(su.json);
				} else if ("ok_none" == su.status) {
					//已经加载完数据了;
					//alert(su.json);
					appendMediaNoticeNew(su.json);
					currentPage = (pageCount+1);
					chageDiv();
				} else if ("not" == su.status) {
					//没有数据返回;
					alert(su.json);
					chageDiv();
					currentPage =  (pageCount+1);
				} else if ("err" == su.status) {
					alert(su.json);
				} else{
					alert(su.json);
				}
			}
		});
	});
	//alert(fmoney(123456.56, 0));
});//JQuery的结束符号;


function appendMediaNoticeNew(list){
	if(""==list || null==list){
		return false;
	}
	for ( var i = 0; i < list.length; i++) {
		 var notice = list[i];
		 var listTr = "<div class='platAnno'>";
		 listTr += "<h1><a href='${pageContext.request.contextPath}/Product/indexNotice!getMobileNoticeById.action?noticeId="+notice.id+"'>"+notice.title;
		 listTr += "</a><strong><span class='time'>"+format(notice.insertTime.time)+"</span></strong></h1>";
		 if(notice.titleUrl != ''&& notice.titleUrl != null){
			 listTr += "<h2><a href='${pageContext.request.contextPath}/Product/indexNotice!getMobileNoticeById.action?noticeId="+notice.id+"'>";
			 listTr += " <img style='height:150px;' src='"+httpUrl+fileName+"/web_img/notice/slt/"+notice.titleUrl+"'/>";
			 listTr += "</a></h2>";
		 }
		 listTr += "</div>";
		$("#media").append(listTr);
	}	
}
$(document).ready(function(){
	var currentPage = 2;
	var pageCount = "${pageCount}";
	/* alert(systemConfig) */
	$("#noticeloadMore").click(function(){
		//alert(3);
		function chageDiv(){
			$("#noticeloadMore").text("已加载完全部");
			$("#noticeloadMore").css("cursor","auto");
			$("#noticeloadMore").css("color","gray");
			$("#noticeloadMore").unbind("click");
		}	
		//alert(currentPage);
		if(currentPage>parseInt(pageCount)){
			//alert("当前页大于总页数");
			chageDiv();
			return false;
		}
		var url = "${pageContext.request.contextPath}/Product/indexNotice!loadNoticeByAjax.action?"; 
		/* $.post(url, function(data){
			   alert("Data Loaded: " + data);
		 }); */
		var su = "";
		$.ajax({
			type : "post",
			url : url,
			async: true,
			data:"currentPage="+currentPage,
			beforeSend : function(XMLHttpRequest){
				$("#noticeloadMore").html("<a style='margin-top: -11px'><img  src='${pageContext.request.contextPath}/Product/images/progress.gif'></a>");
			},
			success : function(data, textStatus) {
				su = data;
				//alert("数据: "+data);
				//alert(su);
			},
			complete : function(XMLHttpRequest, textStatus) {
				$("#noticeloadMore").html("<a>查看更多</a>");
				//alert("完成");
				//alert(textStatus);
				if ("ok" == su.status) {
					//解析json,进行拼接;
					//alert(su);
					//alert(su.json);
					currentPage++;
					appendNoticeNew(su.json);
				} else if ("ok_none" == su.status) {
					//已经加载完数据了;
					//alert(su.json);
					appendNoticeNew(su.json);
					currentPage = (pageCount+1);
					chageDiv();
				} else if ("not" == su.status) {
					//没有数据返回;
					alert(su.json);
					chageDiv();
					currentPage =  (pageCount+1);
				} else if ("err" == su.status) {
					alert(su.json);
				} else{
					alert(su.json);
				}
			}
		});
	});
	//alert(fmoney(123456.56, 0));
});//JQuery的结束符号;


function appendNoticeNew(list){
	if(""==list || null==list){
		return false;
	}
	for ( var i = 0; i < list.length; i++) {
		 var notice = list[i];
		 var listTr = "";
		 listTr += "<dd><a href='${pageContext.request.contextPath}/Product/indexNotice!getMobileNoticeById.action?noticeId="+notice.id+"'>"+notice.title;
		 listTr += "</a><span class='time'>"+formatMMdd(notice.insertTime.time)+"</span></dd>";
		$("#notice").append(listTr);
	}	
}
</script>
</head>
<body>
	<%-- <header><a href="javascript:history.go(-1);" class="header_l"><img src="${pageContext.request.contextPath}/Product/images/return_icon.png"/></a>最新动态
	<a onclick="javascript:location.replace('${pageContext.request.contextPath}/Product/indexNotice!loadIndexNotice.action'); event.returnValue=false; " class="header_home"><img src="${pageContext.request.contextPath}/Product/images/home_icon.png" alt=""></a></header> --%>
	<div class="coupons_tag news_tag">
		<ul>
			<li >媒体报道</li>
			<li class="vis">平台公告</li>	
		</ul>
	</div>
	<div class="moredllist newsList">
		<dl style="display:none;">
			<div  id="media">
				<c:forEach items="${mediaList}" var="list">
					<div class="platAnno">
			            <h1><a href="${pageContext.request.contextPath}/Product/indexNotice!getMobileNoticeById.action?noticeId=${list.id}">${list.title}</a><strong><span class="time">[<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd"/>]</span></strong></h1>
			            <c:if test="${list.titleUrl != ''&& list.titleUrl != null}">
			            <h2><a href="${pageContext.request.contextPath}/Product/indexNotice!getMobileNoticeById.action?noticeId=${list.id}"><img src="${systemConfig.httpUrl}${systemConfig.fileName}/web_img/notice/slt/${list.titleUrl}"/></a></h2>
			            </c:if>
	      		  </div>
				</c:forEach>
	        </div>
	        <div id="medialoadMore" class="abtn"  ><a>查看更多</a></div>
		</dl>
		<dl style="display:block;">
			<div id="notice">
				<c:forEach items="${noticeList}" var="list">
					<dd><a href="${pageContext.request.contextPath}/Product/indexNotice!getMobileNoticeById.action?noticeId=${list.id}">${list.title}</a><span class="time">[<fmt:formatDate value="${list.insertTime}" pattern="MM-dd"/>]</span></dd>
				</c:forEach>
			</div>
			 <div id="noticeloadMore" class="abtn"  ><a>查看更多</a></div>
		</dl>
	</div>

</body>
<script src="${pageContext.request.contextPath}/wapNotice/js/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/wapNotice/js/rem.js"></script>
<script>
$(function(){
	var tag = $('.coupons_tag li');
	var ul = $('.newsList dl');

	//ul.first().show(1);

	tag.click(function(){
		var index = $(this).index();
		$(this).addClass('vis').siblings('li').removeClass('vis');

		ul.eq(index).show(0).siblings('dl').hide(0);
	})
})
</script>
</html>