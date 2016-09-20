<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>加密解密</title>
<%
 String jiemi2=request.getParameter("jiemi2");
String jiami2=request.getParameter("jiami2");
System.out.print(jiemi2);
String jiami=request.getParameter("jiami");
String jiemi=request.getParameter("jiemi");
System.out.print(jiami);
request.setAttribute("jiami",jiami);
request.setAttribute("jiemi2",jiemi2);
request.setAttribute("jiemi",jiemi);
request.setAttribute("jiami2",jiami2);
%>
</head>
<body>
	<form action="${pageContext.request.contextPath}/Product/Admin/jiami.jsp">
		<input type="text" name="jiami" id="jiami" value="${jiami}"/> <input type="submit"  value="解密"/>
		<br>
		解密后的结果
		<input type="text" name="jiemi" id="jiemi" value="${myel:jieMiUsername(jiami)}"/>
	</form> 
	<form action="${pageContext.request.contextPath}/Product/Admin/jiami.jsp">
		<input type="text"  name="jiemi2" id="jiemi2" value="${jiemi2}" /> <input type="submit"  value="加密" />
		<br>
		加密后的结果
		<input type="text" name="jiami2" id="jiami2"  value="${myel:jiaMiUsername(jiemi2)}">
	</form>
</body>
</html>