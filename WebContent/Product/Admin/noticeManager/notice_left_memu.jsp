<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="fl left_meue">
	<ul id="ab_left_memu">
		<li><a class="m1">公司简介</a></li>
		<li><a class="m1">团队介绍</a></li>
		<li><a class="m1">核心优势</a></li>
		<li><a class="m1">用户保障</a></li>
		<li><a class="m1">媒体报道</a></li>
		<li><a class="m2">平台公告</a></li>
		<li><a class="m1">合作机构</a></li>
		<li><a class="m1">帮助中心</a></li>
		<li><a class="m4">招兵买马</a></li>
		<li><a class="m3">联系我们</a></li>
		<%-- <li><a class="m5" href="${pageContext.request.contextPath}/Product/help.jsp">常见问题</a></li> --%>
	</ul>
</div>

<script>
	function ab_left_memu(num){
		var ind = num || 0;
		$("#ab_left_memu li").eq(ind).addClass("g_on");
	}
</script>