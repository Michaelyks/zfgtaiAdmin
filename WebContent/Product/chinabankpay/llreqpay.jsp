<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<title>认证支付</title>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
		<form name="payOrder" id="payOrder" action="${payOrder.auth_pay_url}" method='POST'>
			<input type='hidden' name='version'   value='${payOrder.version}'>
			<input type='hidden' name='oid_partner' value='${payOrder.oid_partner}'>
			<input type='hidden' name='user_id' value='${payOrder.user_id}'>
			<input type="hidden" name='timestamp'   value='${payOrder.timestamp}'>
			<input type='hidden' name='sign_type'   value='${payOrder.sign_type}'>
			<input type='hidden' name='busi_partner'   value='${payOrder.busi_partner}'>
			<input type='hidden' name='no_order'  value='${payOrder.no_order}'>
			<input type='hidden' name='dt_order' value='${payOrder.dt_order}'>
			<input type='hidden' name='risk_item' value='${payOrder.risk_item}'>
			<input type='hidden' name='name_goods'   value='${payOrder.name_goods}'>
			<input type='hidden' name='money_order'   value='${payOrder.money_order}'>
			<input type='hidden' name='url_return'    value='${payOrder.url_return}'>
			<input type='hidden' name='notify_url' value='${payOrder.notify_url}'>
			<input type="hidden" name="pay_type"  value="${payOrder.pay_type}">
			<input type='hidden' name='id_type'     value='${payOrder.id_type}'>
			<input type='hidden' name='id_no'     value='${payOrder.id_no}'>
			<input type='hidden' name='acct_name'     value='${payOrder.acct_name}'>
			<input type='hidden' name='card_no'     value='${payOrder.card_no}'>
			<input type='hidden' name='bank_code'     value='${payOrder.bank_code}'>
			<input type='hidden' name='info_order'     value='${payOrder.info_order}'>
			<input type='hidden' name='userreq_ip'     value='${payOrder.userreq_ip}'>
			<input type='hidden' name='valid_order'     value='${payOrder.valid_order}'>
			<input type='hidden' name='sign'     value='${payOrder.sign}'>
		</form>
	</body>
<script>  
		
 	 	$("#payOrder").submit(); 
</script>  	
</html>
