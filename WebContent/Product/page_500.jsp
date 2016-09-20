<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!doctype html>
<html>

	<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<meta charset="utf-8"/>
		<title>500</title>
		<link href="css/page_404.css" rel="stylesheet" type="text/css"/>
		<link href="css/common.css" rel="stylesheet" type="text/css">
	</head>
	
	<body>
		<jsp:include page="header.jsp" />
		<div class="main">
			<div class="tis2">
				<div class="Reason">
					<p>你要查看的网页找不到！</p>
					<div>
						<b>疑难解答</b>
						<ul>
							<li>如果你是手动输入网址，请查看网址拼写是否正确</li>
							<li>你可以点击后退回到正常的页面</li>
							<li>你可以通过底部导航进行搜索</li>
						</ul>
					</div>
					
				</div>
			</div>
			
		</div>
		<jsp:include page="footer.jsp" />
	</body>

</html>