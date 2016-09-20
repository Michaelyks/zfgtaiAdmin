<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!doctype html>
<html>
	<head>
		<title>ÍøÒøÖ§¸¶</title>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
		<form name="yeepay" id="yeepay" action="${yeepay.yeepayCommonReqURL}" method='POST'>
			<input type='hidden' name='p0_Cmd'   value='${yeepay.p0_Cmd}'>
			<input type='hidden' name='p1_MerId' value='${yeepay.p1_MerId}'>
			<input type='hidden' name='p2_Order' value='${yeepay.p2_Order}'>
			<input type="hidden" name='p3_Amt'   value='${yeepay.p3_Amt}'>
			<input type='hidden' name='p4_Cur'   value='${yeepay.p4_Cur}'>
			<input type='hidden' name='p5_Pid'   value='${yeepay.p5_Pid}'>
			<input type='hidden' name='p6_Pcat'  value='${yeepay.p6_Pcat}'>
			<input type='hidden' name='p7_Pdesc' value='${yeepay.p7_Pdesc}'>
			<input type='hidden' name='p8_Url'   value='${yeepay.p8_Url}'>
			<input type='hidden' name='p9_SAF'   value='${yeepay.p9_SAF}'>
			<input type='hidden' name='pa_MP'    value='${yeepay.pa_MP}'>
			<input type='hidden' name='pd_FrpId' value='${yeepay.pd_FrpId}'>
			<input type="hidden" name="pr_NeedResponse"  value="${yeepay.pr_NeedResponse}">
			<input type='hidden' name='hmac'     value='${yeepay.hmac}'>
		</form>
	</body>
<script>  
		$("#yeepay").submit();
</script>  	
</html>
