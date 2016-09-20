<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>绑定银行卡</title>
</head>
<body>
<h1>绑定银行卡</h1>
<form action="${pageContext.request.contextPath}/Product/demo!bindCard.action" method="post">
商户编号<input type="text" name="card.merchantaccount" value="10000419568" ><br>
用户标识<input type="text" name="card.identityid" value="1003"><br>
用户标识类型<input type="text" name="card.identitytype" value="2"><br>
绑卡请求号<input type="text" name="card.requestid" value="1003"><br>
银行卡号<input type="text" name="card.cardno" value="6214837554545537"><br>
证件类型<input type="text" name="card.idcardtype" value="01"><br>
证件号<input type="text" name="card.idcardno" value="441723199302145632"><br>
持卡人姓名<input type="text" name="card.username" value="qwy"><br>
银行预留手机号<input type="text" name="card.phone" value="15112304365"><br>
用户请求ip<input type="text" name="card.userip" value="192.168.0.100"><br>
签名<input type="text" name="card.sign" value=""><br>
<input type="submit" value="绑定银行卡" >
</form>
<h1>检查银行卡</h1>
<form action="${pageContext.request.contextPath}/Product/demo!checkBankCard.action" method="post">
商户编号<input type="text" name="card.merchantaccount" value="10000419568" ><br>
用户标识<input type="text" name="card.identityid" value="1003"><br>
用户标识类型<input type="text" name="card.identitytype" value="2"><br>
绑卡请求号<input type="text" name="card.requestid" value="1003"><br>
银行卡号<input type="text" name="card.cardno" value="6214837554545537"><br>
证件类型<input type="text" name="card.idcardtype" value="01"><br>
证件号<input type="text" name="card.idcardno" value="441723199302145632"><br>
持卡人姓名<input type="text" name="card.username" value="qwy"><br>
银行预留手机号<input type="text" name="card.phone" value="15112304365"><br>
用户请求ip<input type="text" name="card.userip" value="192.168.0.100"><br>
签名<input type="text" name="card.sign" value=""><br>
<input type="submit" value="绑定银行卡" >
</form>

<h1>支付接口</h1>
<form action="${pageContext.request.contextPath}/Product/demo!pay.action" method="post">
<input type="submit" value="支付" >
</form>

<h1>绑卡确认</h1>
<form action="${pageContext.request.contextPath}/Product/demo!confirmBindBankcard.action" method="post">
商户编号<input type="text" name="card.merchantaccount" value="10000419568" ><br>
绑卡请求号<input type="text" name="card.requestid" value="1003"><br>
<input type="submit" value="确定绑定银行卡" >
</form>

</body>
</html>