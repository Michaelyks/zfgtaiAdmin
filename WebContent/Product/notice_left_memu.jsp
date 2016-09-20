<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="fl left_meue">
	<ul id="ab_left_memu">
		<%-- <li><a class="m1" href="${pageContext.request.contextPath}/Product/aboutus_new.jsp#indexNotice">公司简介<i></i></a></li>
		<li><a class="m2" href="${pageContext.request.contextPath}/Product/team.jsp">团队介绍<i></i></a></li>
		<li><a class="m3" href="${pageContext.request.contextPath}/Product/power.jsp#indexNotice">核心优势<i></i></a></li>
		<li><a class="m4" href="${pageContext.request.contextPath}/Product/trust.jsp#indexNotice">用户保障<i></i></a></li> --%>
		<li><a class="m5" href="${pageContext.request.contextPath}/Product/indexNotice!loadMedia.action#ab_us_f2">媒体报道<i></i></a></li>
		<li><a class="m6" href="${pageContext.request.contextPath}/Product/indexNotice!loadNotice.action#ab_us_f2">平台公告<i></i></a></li>
		<%-- <li style="display: none;"><a class="m1" href="${pageContext.request.contextPath}/Product/">合作机构<i></i></a></li>
		<li style="display: none;"><a class="m1" href="${pageContext.request.contextPath}/Product/">帮助中心<i></i></a></li>
		<li><a class="m7" href="${pageContext.request.contextPath}/Product/emploies.jsp#indexNotice">招兵买马<i></i></a></li>
		<li><a class="m8" href="${pageContext.request.contextPath}/Product/contactus.jsp#indexNotice">联系我们<i></i></a></li> --%>
		<%-- <li><a class="m5" href="${pageContext.request.contextPath}/Product/help.jsp">常见问题</a></li> --%>
	</ul>
</div>

<script>
	function ab_left_memu(num){
		var ind = num || 0;
		$("#ab_left_memu li").eq(ind).addClass("g_on");
	}
</script>