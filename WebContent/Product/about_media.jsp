<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>
	<head>
<meta charset="utf-8" />
<title>中泰理财 - 媒体报道</title>
<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" text="text/css"/>
<link href="${pageContext.request.contextPath}/Product/css/about_us.css" rel="stylesheet" text="text/css"/>
<link href="${pageContext.request.contextPath}/Product/css/about_media.css" rel="stylesheet" text="text/css"/>
<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
<style type="text/css">
.text{
overflow : hidden;
text-overflow: ellipsis;
display: -webkit-box;
-webkit-line-clamp: 4;
-webkit-box-orient: vertical;
}

.aBtn{
  color: #333;
  background-color: #fff;
  border-color: #ccc;
  display: inline-block;
  padding: 6px 12px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: 400;
  line-height: 1.42857143;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  -ms-touch-action: manipulation;
  touch-action: manipulation;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
  }
</style>
	</head>
	
	<body>
		<jsp:include page="header.jsp" />
		<jsp:include page="about_nav.jsp" />	
		<div id="media_list">
			<div class="layout">
				<c:forEach items="${pageUtil.list}" var="item">
					<div class="m_list">
					<c:choose>
						<c:when test="${item.titleUrl!='' && item.titleUrl!=null }">
							<img src="${systemConfig.httpUrl}${systemConfig.fileName}/web_img/notice/slt/${item.titleUrl}" />
						</c:when>
						<c:otherwise>
							<img src="${pageContext.request.contextPath}/Product/images/notice_default.jpg" />
						</c:otherwise>
						</c:choose>
						<div>
							<h3>${item.title}</h3>
							<p class="text">${item.description}</p>
						</div>
					</div>
				</c:forEach>
				<c:choose>
	            <c:when test="${pageUtil.list eq '[]' || pageUtil.list eq ''}">
	            	<div style="text-align: center;margin-top: 15px;">
	            	<img src="images/no_record.png" />
	            	</div>
					<!-- <div class="index_wujilu">
						<p>瞄~ 这里空空如也！</p>
					</div> -->
	            </c:when>
	            <c:otherwise>
		            <div class="layout" style="padding:20px 0;text-align:center">
					<ul id="pages_biao">
						<!-- <li class='btn first'><a class='aBtn' >首页</a></li>
						<li class="prev"><a class='aBtn'>上一页</a></li>
						<li><a class='aBtn'>1</a></li>
						<li><a class='aBtn'>2</a></li>
						<li><a class='aBtn'>3</a></li>
						<li><a class='aBtn'>4</a></li>
						<li><a class='aBtn'>5</a></li>
						<li class="next"><a class='aBtn'>下一页</a></li>
						<li class="last"><a class='aBtn'>末页</a></li>  -->
					</ul>
					</div>
	            </c:otherwise>
	           </c:choose>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
		<form id="myForm" method="post">
		<input id="currentPage" type="hidden" value="1" name="currentPage">
		</form>
	</body>
	<script>

		var pageCount = parseInt("${pageCount}");

		function getNoticeByPage(currentPage){
			//alert(currentPage);
			//console.log(currentPage);
			//alert("");
			$("#currentPage").val(currentPage);
			$("#myForm").attr("action","${pageContext.request.contextPath}/Product/indexNotice!loadMedia.action#ab_us_f2");
			$("#myForm").submit();
		}
		appendDivA(parseInt("${currentPage}"), parseInt("${pageCount}"));
		/**
		 *在div下追加a标签;
		 *@param currentPage : 当前页数;
		 *@param pageCount : 总页数;
		 *@author qwy
		 */
		function appendDivA(currentPage,pageCount){
			  var id = "#pages_biao";
				$(id).html("");
				var home = "<li class='first'><a  class='aBtn' onclick='getNoticeByPage(1)'>首页</a></li>";
				var end = "<li class='last'><a class='aBtn' onclick='getNoticeByPage(${pageCount})'>末页</a></li>";
				var tempPrev = "  ";
				var tempNext = "  ";
				//如果总页数大于5页;
				if(pageCount<=5){
					for(var i=1;i<=pageCount;i++){
						//alert(i);
						tempNext +="<li ><a class='aBtn' onclick='getNoticeByPage("+i+")' >"+i+"</a></li>";
					}
				}else{
					if(currentPage>=4){
						var tempPage = currentPage+2>=pageCount?pageCount:currentPage+2;
						//alert(tempPage);
						if(tempPage==pageCount){
							for(var i=pageCount-4;i<=pageCount;i++){
								//alert(i);
								tempNext +="<li ><a class='aBtn' onclick='getNoticeByPage("+i+")' >"+i+"</a></li>";
							}
						}else{
							for(var i=currentPage-2;i<currentPage;i++){
								tempPrev +="<li ><a class='aBtn' onclick='getNoticeByPage("+i+")' >"+i+"</a></li>";
							}
							for(var i=currentPage;i<=tempPage;i++){
								tempNext +="<li ><a class='aBtn' onclick='getNoticeByPage("+i+")' >"+i+"</a></li>";
							}
						}
					}else{
						for(var i=1;i<3;i++){
							tempPrev +="<li ><a class='aBtn' onclick='getNoticeByPage("+i+")' >"+i+"</a></li>";
						}
						for(var i=3;i<=5;i++){
							tempNext +="<li ><a class='aBtn' onclick='getNoticeByPage("+i+")' >"+i+"</a></li>";
						}
					}
				}
				var result = home+tempPrev+tempNext+end;
				$(id).html(result);
		}
		 
		 $("#pages_biao").children().each(function(){
			 if("${currentPage}"==$(this).text()){
				 $(this).attr("class"," selected");
			 }
		 });
		</script>
</html>
	
