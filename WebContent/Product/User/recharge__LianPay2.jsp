<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 充值</title>
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/account.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/recharge.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/recharge_wycz.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
		<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
	</head>
	<body>
		<jsp:include page="../header.jsp" />
		
		<!-- 主内容 -->
		<div class="main">
			<div class="layout my_account">
				<jsp:include page="leftMenu.jsp" />
				<!-- 内容页 -->
				<div class="fr my_info">
					<div class="recharge_zzc">
						<p>可用余额(元)</p>
						<span style="color:red;"><fmt:formatNumber value="${users.usersInfo.leftMoney * 0.01}" pattern="0.00"/></span>
						<div>中泰理财为您提供银行级别的帐户和资金安全保障</div>
					</div>
					<div class="recharge_money">
						<form id="recharge_form" action="${pageContext.request.contextPath}/Product/User/userRecharge!directBindPay.action" method="post">
							<ul>
								<li><span class="recharge_inp"><label>充值金额<a class="red">*</a></label><input maxlength="9" id="txt_rechargeMoney" autocomplete="off" name="rechargeMoney" type="text" class="txt" value=""><span>元</span></span><span class="input_tips" style="color:red;">充值的金额只能1元起</span></li>
								<c:if test="${isBindBank ne '1'}">															 	
								    <li><span class="recharge_inp"><label>真实姓名<a class="red">*</a></label><input type="text" autocomplete="off" id="uname"  name="name" value="" ></span><span class="input_tips" style="color:red;"></span></li>							
									<li><span class="recharge_inp"><label>身份证号码<a class="red">*</a></label><input type="text" autocomplete="off" min='0' id="ucarid" maxlength="18"  name="idcard" value="" ></span><span class="input_tips" style="color:red;"></span></li>
									<li><span class="recharge_inp"><label>银行卡号<a class="red">*</a></label><input type="text" autocomplete="off" min='0' id="ubankid" maxlength="20"   name="cardNo" value="" ></span><span class="input_tips" style="color:red;"></span></li> 
									<li><span class="input_tips" style="color:red;">温馨提示： 首次充值可完成实名认证，绑定银行卡，绑定手机！</span></li>
								</c:if>

							</ul>
						</form>
					</div>
			<!-- 网上充值  -->
			<div>		
			<!-- 支付 -->
			
			<!-- 支付 end -->
			<a class="pay-tx" id="pay_now">确定充值</a>
			</div>
					<div class="recharge_jilu">
						<p id="cz_record">充值流水记录</p>
						<table>
							<tbody>
								<tr class="list_title">
									<td>流水号</td>
									<td>充值类型</td>
									<td>充值金额(元)</td>
									<td>充值机构</td>
									<td>充值方式</td>	
									<td>充值时间</td>				
									<td>充值状态<div style="  margin-right: -21px;">
						<select id="recordType" onchange="showRecharge(this.value)">
						<option value="all">全部</option>
						<option value="0">处理中</option>
						<option value="1">充值成功</option>
						<option value="2">充值失败</option>
						</select>
						</div></td>
								</tr>	
								<c:forEach items="${listCzRecord}" var="list" varStatus="s">
								<tr ${s.count % 2 == 0 ? 'style=\"background-color: #FCFCFC;\"':''}>
								<td class="td0" title="${list.recordNumber}">${list.recordNumber}</td>
								<td class="td1">在线充值</td>
								<td class="td2" ><fmt:formatNumber value="${list.money*0.01}" pattern="#,##0.##" type="number" /></td>
								<td class="td3" >${list.account.bankName}<c:if test="${list.type eq '1'}">网上银行</c:if></td>
								<td class="td4" >
									<c:if test="${list.type eq '0' || list.type eq null || list.type eq ''}">快捷支付</c:if>
									<c:if test="${list.type eq '1'}">网银支付</c:if>
									<c:if test="${list.type eq '3'}">认证支付</c:if>
								</td>
								<td class="td5" title="<fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${list.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="td6" title="${list.note}">${list.czzt}</td>
							</tr>
							</c:forEach>								
							</tbody>
						</table>
						<div style="  text-align: right;margin: 30px;">
					<jsp:include page="../page.jsp" />
					</div>
					<c:if test="${listCzRecord eq '' || listCzRecord eq '[]' || listCzRecord eq null}">
					<div class="wujilu">
						<span>暂无记录</span>						
					</div>
					</c:if>
					</div>
				</div>
				<!-- 内容页 end-->
				<div class="clea"></div>
			</div>
		</div>
		<!-- 主内容 end-->		
		<jsp:include page="../footer.jsp" />
		<script type="text/javascript">
		
		
		$(function(){
			var msg='${msg}';
			if(msg!=''&&msg!=null){
				jingao(msg,"提示",function(){
					//location.href=location.href;
					var url = "${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action?msg="+msg;
					window.location.href=url;
				},0);
			}
		});
		
		function LLPay(){		
			
			var isBindBank='${isBindBank}';
			if(isBindBank!='1'){
				if($("#uname").val()==""){
					jingao("真实姓名不能为空","提示","",0);
					return false;
				}

				if($("#ucarid").val()=="" ||  ($("#ucarid").val().length !=16  && $("#ucarid").val().length!=18) || !isOnlyNumber($("#ucarid").val().substr(0,15))){
					jingao("身份证号码输入有误","提示","",0);
					return false;
				}
				if($("#ubankid").val()=="" || !isOnlyNumber($("#ubankid").val()) || ($("#ubankid").val().length <16  || $("#ubankid").val().length>19) ){
					jingao("银行卡输入有误","提示","",0);
					return false;
				}
				
			}
			if(!isPrice($("#txt_rechargeMoney").val())){// || 
				jingao("【充值金额】格式错误","提示","",0);
				return false;
			}
 			if(parseFloat($("#txt_rechargeMoney").val())<1){
				jingao("【充值金额】必须1元起","提示","",0);
				return false;
			}
 			if(hasDot(parseFloat($("#txt_rechargeMoney").val()))){
 				if(parseFloat($("#txt_rechargeMoney").val()).toString().split(".")[1].length >2){
 					jingao("【充值金额】不能超过2位小数","提示","",0);
 					return false;
 				}
 			}
 			$("#txt_rechargeMoney").val(parseFloat($("#txt_rechargeMoney").val()));
			$("#recharge_form").submit();		 

		}
		
		
		function hasDot(num) {
		    if (!isNaN(num)) {
		        return ((num + '').indexOf('.') != -1) ? true : false;
		    }
		}
		</script>
		<script type="text/javascript">
/* 		var IsNameTrue=true;
		if(!IsNameTrue){ 
			jingao("您还没有实名认证，请先实名认证","提示",function(){
				window.location.href="http://www.baidu.com";
			},0);
			
		} */
		
		$("#pay_now").click(function(){
				LLPay();
		});
		
 		
		function showRecharge(type){
			var url = "${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action?recordType="+type+"#recordType";
			window.location.href=url;
		}
		
		var recordType = "${recordType}";
		$("#recordType option").each(function(){
			if(recordType==$(this).val()){
				$(this).attr("selected",true);
			}
			
		});
		
		</script>
	</body>
</html>