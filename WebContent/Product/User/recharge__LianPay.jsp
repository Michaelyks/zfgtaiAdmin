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
					<ul class="list_title" id="list_title">
						<li class="on"><a href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action">充值</a></li>
						<li><a href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action">充值记录</a></li>
						<div class="clea"></div>
					</ul>
					<div class="recharge_zzc"></div>
					<div class="recharge_money box-chan">
						<form id="recharge_form" class="mt40" action="${pageContext.request.contextPath}/Product/User/userRecharge!directBindPay.action" method="post">
							<ul>
								<li class="fun-cz">
									<span class="recharge_inp">
										<label>充值方式：</label>
										<em class="on">网银充值</em>
										<em>快捷充值</em>
									</span>
								</li>
							</ul>
							<div class="clea"></div>
							<ul>
								<li>
									<span class="recharge_inp">
										<label>充值银行：</label>
										<span class="banks"></span>
									</span>
								</li>
								<li>
									<span class="recharge_inp">
										<label>账户余额：</label>
										<span class="orange-color f24"> 0.00 </span>元
									</span>
								</li>
								<li>
									<span class="recharge_inp">
										<label>充值金额：</label>
										<input maxlength="9" id="txt_rechargeMoney" autocomplete="off" name="rechargeMoney" type="text" class="txt" value="">
										
									</span>
									<span class="orange-color">*充值金额必须≥1元</span>
								</li>

							</ul>
						</form>
						<a class="pay-tx" id="pay_now">确定充值</a>
					</div>
				<div>		
			</div>
					<div class="recharge_jilu box-chan" style="display:none">
						<div class="nav-menu">
							<ul id="recordType" onchange="showRecharge(this.value)">
								<span class="mr30 mt5 fl">充值状态 </span>
								<li class="on2"><a value="all">全部</a></li>
								<li><a value="0">处理中</a></li>
								<li><a value="1">充值成功</a></li>
								<li><a value="2">充值失败</a></li>
								<div class="clea"></div>
							</ul>
						</div>
						<!-- <div>
							<select id="recordType" onchange="showRecharge(this.value)">
							<option value="all">全部</option>
							<option value="0">处理中</option>
							<option value="1">充值成功</option>
							<option value="2">充值失败</option>
							</select>
						</div> -->
						<table>
							<tbody>
								<tr class="list_title list_head">
									<td>流水号</td>
									<!-- <td>充值类型</td> -->
									<td>充值金额</td>
									<td>充值银行</td>
									<td>充值方式</td>	
									<td>充值时间</td>				
									<td>充值状态</td>
								</tr>
								<c:forEach items="${listCzRecord}" var="list" varStatus="s">
								<tr ${s.count % 2 == 0 ? 'style=\"background-color: #FCFCFC;\"':''}>
									<td class="td0" title="${list.recordNumber}">${list.recordNumber}</td>
									<!-- <td class="td1">在线充值</td> -->
									<td class="td2 orange-color" ><fmt:formatNumber value="${list.money*0.01}" pattern="#,##0.##" type="number" /></td>
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
						<div class="mt40" id="page">
							<jsp:include page="../page.jsp" />
						</div>
					<c:if test="${listCzRecord eq '' || listCzRecord eq '[]' || listCzRecord eq null}">
						<div class="nothing-img">
		            		<img src="../images/nothing-img.jpg"/>
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
			//状态
			$("#recordType li").click(function(){
				$(this).addClass("on2").siblings().removeClass("on2");	
				$(this).find("a").attr("value");
			});

			//切换
			$(".list_title li").click(function(){
				$(this).addClass("on").siblings().removeClass("on");
				$(".box-chan:eq("+$(this).index()+")").show().siblings(".box-chan").hide();
			})
			$(".fun-cz em").click(function(){
				$(this).addClass("on").siblings().removeClass("on");
			})

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
		$("#menu_l li:eq(4)").addClass("l_munu_on").siblings().removeClass("l_munu_on");
		
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