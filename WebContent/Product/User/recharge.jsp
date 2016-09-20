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
						<form id="recharge_form">
							<ul>
								<li><span class="recharge_inp"><label>充值金额<a class="red">*</a></label><input maxlength="9" id="txt_rechargeMoney" autocomplete="off" name="rechargeMoney" type="text" class="txt" value=""><span>元</span></span><span class="input_tips" style="color:red;">充值的金额只能1元起</span></li>
								<li style="display:none;"><input type="text"value=""></li>
								<li><span class="recharge_inp"><label>支付密码<a class="red">*</a></label><input type="password" autocomplete="off" id="pay_password" maxlength="16" name="payPassword" value="" ></span><span class="input_tips" style="color:red;">默认的支付密码为平台的注册密码</span></li>
								<c:if test="${listBankCard ne null}">
								<%-- <li><a id="goToRecharge" class="to_cz" style="cursor:pointer;" >确认充值</a></li> --%>
								</c:if>
							</ul>
							<input type="hidden" id="pd_FrpId" name="pd_FrpId" >
						</form>
					</div>
			<!-- 网上充值  -->
			<div>		
			<!-- 支付 -->
			<div class="pay_hongbao bank">
				
				<ul class="payway">
					<li class="onway" data-value="0">快捷支付</li>
					<li data-value="1">网银支付</li>
					<div class="clea"></div>
				</ul>
				<div class="hongbao_cc">
				<c:choose>
						<c:when test="${listBankCard ne null}">
						<p style="color:red;">建议:快捷支付，适合小笔金额充值（如：1万元以下）；1~3分钟内到账；</p>
						<c:forEach items="${listBankCard}" var="list" varStatus="index">
							<div class="add_bank mybank on_bank">
								<p>${list.bankName}</p>
								<p>**** **** **** ${list.cardLast}</p>
							</div>
						</c:forEach>
						</c:when>
						<c:otherwise>
							<p>
								您的账户尚未绑定任何银行卡&nbsp;&nbsp;&nbsp;&nbsp;<a class="red" href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action">立即绑定</a>
							</p>
							<div class="add_bank" id="div_bind_card">
								<span>添加银行卡</span>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="clea"></div>
				</div>
				<ul class="hongbao_cc">
				<p style="color:red;">建议:网银支付，适合大笔金额充值（如：1万元以上）；1~30分钟内到账；</p>
					<li>
						<img src="${pageContext.request.contextPath}/Product/images/bank2_icbc.jpg" data-value="ICBC-NET-B2C" title="中国工商银行"/>
					</li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_cmbchina.jpg" data-value="CMBCHINA-NET-B2C" title="招商银行"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_abc.jpg" data-value="ABC-NET-B2B" title="农业银行"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_ccb.jpg" data-value="CCB-NET-B2C" title="建设银行"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_ceb.jpg" data-value="CEB-NET-B2C" title="光大银行"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_boc.jpg" data-value="BOC-NET-B2C" title="中国银行"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_pingan.jpg" data-value="PINGANBANK-NET-B2C" title="平安银行"/></li>
					<%-- <li><img src="${pageContext.request.contextPath}/Product/images/bank2_spdb.jpg" data-value="SPDB-NET-B2B" title="浦发银行"/></li> --%>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_boco.jpg" data-value="BOCO-NET-B2C" title="交通银行"/></li>
					<%-- <li><img src="${pageContext.request.contextPath}/Product/images/bank2_bjrcb.jpg" data-value="BJRCB-NET-B2C" title="北京农商银行"/></li> --%>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_bccb.jpg" data-value="BCCB-NET-B2C" title="北京银行"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_ecitic.jpg" data-value="ECITIC-NET-B2C" title="中信银行"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_post.jpg" data-value="POST-NET-B2C" title="中国邮政储蓄银行"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_gdb.jpg" data-value="GDB-NET-B2C" title="广发银行"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/bank2_cmbc.jpg" data-value="CMBC-NET-B2C" title="中国民生银行"/></li>
					<div class="clea"></div>
				</ul>
			</div>
			<!-- 支付 end -->
			<a class="pay_now" id="pay_now">确定支付</a>
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
		<input type="hidden" id="pay_type" value="0">
		<!-- 主内容 end-->		
		<jsp:include page="../footer.jsp" />
		<script type="text/javascript">
		
		$("#div_bind_card").click(function(){
			window.location.href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action";
			
		});
		
		<%--$("#goToRecharge").click(function(){
			
		});--%>
		
		function fastPay(){
			if("1"!="${isBindBank}"){
				jingao("使用快捷支付,请先绑定银行卡","提示","",0);
				return false;
			}
			var url = "${pageContext.request.contextPath}/Product/User/userRecharge!directBindPay.action";
			var su = "";
			//alert($("#recharge_form").serialize());
			/* if(!isOnlyNumber($("#txt_rechargeMoney").val())){
				jingao("【充值金额】格式错误","提示","",0);
				return false;
			} */
			if(!isPrice($("#txt_rechargeMoney").val()) || parseFloat($("#txt_rechargeMoney").val())<1){
				jingao("【充值金额】格式错误","提示","",0);
				return false;
			}
			if(""==$("#pay_password").val()){
				jingao("支付密码不能为空","提示","",0);
				return false;
			}
			var text = "<img src='${pageContext.request.contextPath}/Product/images/progress.gif' width='24' height='24'> <span style='font-family:楷体;font-size:22px;'>正在充值,请稍等...</span>";
			var id = chuangkou(text,"正在充值...","",0,true);
			$.ajax({
				url:url,
				type:"post",
				data:$("#recharge_form").serialize(),
				beforeSend:function(XMLHttpRequest){
					//$("#psi_tip").text("正在充值,请稍等...");
					//var dialog = art.dialog({id: 'dialog_goToRecharge',title: false,lock:true,fixed:true,content:document.getElementById("psi_load")});
				},
				success:function(data){
					su=data;
				},
				complete:function(XMLHttpRequest,textStatus){
					closeAlert(id);
					art.dialog({id: 'dialog_goToRecharge'}).close();
					if("noLogin"==su.status){
						if(confirm("登录已失效,请重新登录")){
							//跳转到登录界面;
							window.location.href="${pageContext.request.contextPath}/Product/login.jsp";
						}
						return false;
					}else if("ok"==su.status){
						jingao("充值成功","提示",function(){
							window.location.href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action";
						},0);
						//var dialog = art.dialog({id: 'dialog_getValidateCode2',title: false,lock:true,fixed:true,content:"充值成功"});
					}else if("ok"!=su.status){
						jingao(su.json,"提示",function(){
							window.location.href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action";
						},0);
						
						return false;
					}
				}
			});
		}
	
		$(function(){
			$(".mybank").click(function(){
				$(".hongbao_cc").find(".chooseimg").remove();
				$(".bank").find(".on_bank").removeClass("on_bank");
				$(this).addClass("on_bank");
			});
			//选择银行结束
			var use_hb = false;
			$(".hb").click(function(){
				if(use_hb){
					$(".on_red").css("display","block");
				}
				else{
					$(".on_red").css("display","none");
				}
				use_hb = !use_hb; 
			});
			$(".pay_hongbao .hongbao_cc").eq(0).css("display","block");
			$(".pay_hongbao .payway li").click(function(){
				$(".payway").find(".onway").removeClass("onway");
				$(this).addClass("onway");
				var ind = $(this).index();
				$(".pay_hongbao .hongbao_cc").css("display","none");
				$(".pay_hongbao .hongbao_cc").eq(ind).css("display","block");
				var pay_type = $(this).attr("data-value");
				//alert(pay_type);
				$("#pay_type").val(pay_type);
			});
			$(".pay_hongbao .hongbao_cc li img").click(function(){
				$(".hongbao_cc").find(".on_bank").removeClass("on_bank");
				$(this).addClass("on_bank");
				var dom = '<img src="${pageContext.request.contextPath}/Product/images/bank2_yes.jpg" class="chooseimg"/>';
				$(".hongbao_cc").find(".chooseimg").remove();
				var pd_FrpId=$(this).attr("data-value");
				/* alert(pd_FrpId); */
				$("#pd_FrpId").val(pd_FrpId);
				$(dom).insertAfter(this);
			})
		});
		</script>
		<script type="text/javascript">
		$("#pay_now").click(function(){
			var pay_type = $("#pay_type").val();
			if(pay_type=="0"){
				fastPay();
			}else{
				if(!isPrice($("#txt_rechargeMoney").val()) || parseFloat($("#txt_rechargeMoney").val())<1){
					jingao("【充值金额】格式错误","提示","",0);
					return false;
				} 
				if(""==$("#pay_password").val()){
					jingao("支付密码不能为空","提示","",0);
					return false;
				}
				var url = "${pageContext.request.contextPath}/Product/User/userRecharge!validateDate.action";
				$.ajax({
					url:url,
					type:"post",
					async:false,
					data:$("#recharge_form").serialize(),
					success:function(data){
						su=data;
					},
					complete:function(XMLHttpRequest,textStatus){
						if("noLogin"==su.status){
							if(confirm("登录已失效,请重新登录")){
								//跳转到登录界面;
								window.location.href="${pageContext.request.contextPath}/Product/login.jsp";
							}
							return false;
						}else if("ok"==su.status){
							wapPay();
						}else if("ok"!=su.status){
							jingao(su.json,"提示",function(){
								//window.location.href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action";
							},0);
							
							return false;
						}
					}
				});
				
			}
			
		});
		
		function wapPay(){
			var url = "${pageContext.request.contextPath}/Product/User/userRecharge!chinabankPay.action";
			var su = "";
			//alert($("#recharge_form").serialize());
			/* if(!isOnlyNumber($("#txt_rechargeMoney").val())){
				jingao("【充值金额】格式错误","提示","",0);
				return false;
			} */
		 	if(!isPrice($("#txt_rechargeMoney").val()) || parseFloat($("#txt_rechargeMoney").val())<0){
				jingao("【充值金额】格式错误","提示","",0);
				return false;
			} 
			if(""==$("#pay_password").val()){
				jingao("支付密码不能为空","提示","",0);
				return false;
			}
			alert("请在15分钟之内完成订单交易!");
			var text = "<img src='${pageContext.request.contextPath}/Product/images/progress.gif' width='24' height='24'> <span style='font-family:楷体;font-size:22px;'>正在充值,请稍等...</span>";
			var id = chuangkou(text,"正在充值...","",0,true);
			$("#recharge_form").attr("target","_blank");
			$("#recharge_form").attr("method","post");
			$('#recharge_form').attr("action", url).submit();
			closeAlert(id);
		}
		
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