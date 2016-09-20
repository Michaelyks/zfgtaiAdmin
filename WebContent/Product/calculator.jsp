<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 首页</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/pay_online.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		
		<div class="mian">
			<div class="layout jisuanqi">
				<div class="jsq_title">
					<div><!-- <a class="" href="#">&lt;返回</a> --><span>收益计算器</span></div>
				</div>
				<form>
					<ul>
					<li style="margin-left: 20px;">带<a class="red">*</a>号为必填选项</li>
						<li><label><a class="red">*</a>投资金额：</label><input type="text" id="tzje" maxlength="9"/>元<span class="calcu_tis shenglve"></span></li>
						<li><label><a class="red">*</a>投资期限：</label><input type="text" id="tzqx" maxlength="3"/>天<span class="calcu_tis shenglve"></span></li>
						<li><label><a class="red">*</a>年化收益率：</label><input type="text" id="nhsy" maxlength="5"/>%<span class="calcu_tis shenglve"></span></li>
						<!-- <li><label><a class="red">*</a>计息方式：</label><select><option>T+0</option><option>T+1</option></select></li> -->
						<li ><label><a class="red">*</a>还款方式：</label><span class="huaik">按日计息，按月付息，到期还本</span></li>
						<li><a class="jisuan" id="calc_result">计算</a><input type="reset" value="重算" /></li>
					</ul>				
				</form>
				
				<div class="result">
					<h4>计算结果</h4>
					<p>到期收益：本息合计<a id="benXi">0</a>元，利息收入共<a id="interest">0</a>元</p>
					<span>计算结果仅供参考，最终以实际收益为主</span>
				</div>
			</div>
		</div>
		
	<jsp:include page="footer.jsp"/>
		
		
		<script type="text/javascript">
			$(function(){
				$(".jisuanqi input:lt(3)").blur(function(){
					var val = $(this).val();
					var ind = $(this).parent("li").index();
					var check =  /^\d+$/;
					if(ind<2){
						if(val==""){
							$(this).siblings(".calcu_tis").html("");
						}
						else if(check.test(val)){
							$(this).siblings(".calcu_tis").html("");
						}
						else if(!check.test(val)){
							$(this).siblings(".calcu_tis").html("输入的数必须为正整数");
						}
						
					}
					else{
						ckeck =  /^[0-9]+.?[0-9]*$/;
						if(val==""){
							$(this).siblings(".calcu_tis").html("");
						}
						else if(ckeck.test(val)){
							$(this).siblings(".calcu_tis").html("");
						}
						else if(!ckeck.test(val)){
							$(this).siblings(".calcu_tis").html("输入的数必须为数字格式");
						}
					}
					
				});
			});
			$("#calc_result").click(function(){
				var check =  /^\d+$/;
				var tzje = $("#tzje").val();
				var tzqx = $("#tzqx").val();
				var nhsy = $("#nhsy").val();
				var isOk = true;
				if(!check.test(tzje)){
					$("#tzje").siblings(".calcu_tis").html("输入的数必须为正整数");
					isOk = false;
				}
				if(!check.test(tzqx)){
					$("#tzqx").siblings(".calcu_tis").html("输入的数必须为正整数");
					isOk = false;
				}
				var ckeck2 =  /^[0-9]+.?[0-9]*$/;
				if(!ckeck2.test(nhsy)){
					$("#nhsy").siblings(".calcu_tis").html("输入的数必须为数字格式");
					isOk = false;
				}
				if(!isOk){
					return false;
				}
				tzje = parseFloat(tzje);
				tzqx = parseFloat(tzqx);
				nhsy = parseFloat(nhsy);
				$("#tzje").val(tzje);
				$("#tzqx").val(tzqx);
				$("#nhsy").val(nhsy);
				var interest = tzje*nhsy*0.01/365*tzqx;
				interest = Math.floor(interest*100)/100;
				$("#interest").text(interest);
				$("#benXi").text(parseFloat(tzje+interest));
			});
			
			
		</script>
		
	</body>
</html>