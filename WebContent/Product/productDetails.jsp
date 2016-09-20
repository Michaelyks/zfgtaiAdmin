<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
    	 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    	 <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 产品详情</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/product_detail.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/interest.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/login.css" rel="stylesheet" type="text/css"/>
		
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
		<style type="text/css">
		#productDescription table{
			width:100%;
		}
		.mt20 { margin-top: 20px;}
		.productDescription {text-align: center; padding: 0px 10px;}
		.productDescription img{ max-width: 100%;}
		#jlv { position:relative;}
			#jlv b { padding:0px 2px; display: block; font-size: 10px; font-weight: normal; color: #333; border:1px solid #ff5f65; position:absolute; border-radius: 3px;left:55px; top:-12px; z-index: 10;}
			#jlv b::before { content:''; display: block; width: 0px;height: 0px;   border-left: 5px solid transparent; border-right: 4px solid transparent;  border-top: 8px solid #fff; position: absolute;left: 3px; bottom: -7px; z-index: 11;}
			#jlv b::after { content:''; display: block; width: 0px;height: 0px;   border-left: 5px solid transparent; border-right: 4px solid transparent;  border-top: 8px solid #ff5f65; position: absolute;left:3px; bottom: -9px; z-index: 9;}
		</style>
	</head>
		<script type="text/javascript">
	//活动连接
		function theClick(url){
			window.open(url);
		}
		</script>
	<body>
		<jsp:include page="header.jsp" />
		
		<!-- 主内容 -->
		<div class="main">
			<!-- 产品投资头部 -->
			<div class="product_head">
			<!-- 新增当前位置地标 -->
			<p class="location">您的当前位置：&nbsp;<a href="">理财产品</a>&nbsp;&GT;&nbsp;<a href="">贸易通</a>&nbsp;&GT;&nbsp;<a href="">${product.title}</a></p>
			<!-- 新增当前位置地标 end-->
			
			<form id="buyNowFrom" method="post">
			<input type="hidden" name="inv.productId" value="${product.id}">
				<div class="layout">
					<div class="fl">
						<div class="product_name">${product.title}
							<p class="product_category">贸易通</p>
						</div>
						<ul>
							<li id="jlv" data-jl='<fmt:formatNumber value="${product.jiangLiEarnings}" />' class="l_lf"><span  style="color:#888">年化收益</span><br><span id="nhsy" class="b_red"><fmt:formatNumber value="${product.baseEarnings}" pattern="#.#"/></span><a class="red">%</a></li>
							<li class="l_md"><span style="color:#888">理财期限</span><br>
								<c:choose>
									<c:when test="${product.productStatus eq 0}">
									<span id="lcqx" style="font-size:48px;">${product.tzqx}</span>天
									</c:when>
									<c:when test="${product.productStatus eq 1}">
									<span style="color: red; ">进行中</span>
									</c:when>
									<c:otherwise>
									<span style="color: #35c600; ">${product.cpzt}</span>
									</c:otherwise>
								</c:choose>
							</li>
							<li class="l_rt"><span  style="color:#888">起购金额</span><br><span style="font-size:48px;"><fmt:formatNumber value="${product.qtje * 0.01}" pattern="#,###.##"/></span>元</li>
							
							<div class="clea"></div>
						</ul>
						<div class="clea"></div>
						<div class="pay_modth">
							<p><span class="s_lf">项目总额：</span><span class="s_l_content"><fmt:formatNumber value="${product.financingAmount * 0.01}" pattern="#,###.#"/>元</span><span class="s_lf">起息时间：</span>${product.jxfs}</p>
							<p><span class="s_lf">还款方式：</span><span class="s_l_content">${product.fxfs}</span><span class="s_lf">项目到期：</span><span><fmt:formatDate value="${product.finishTime}" pattern="yyyy-MM-dd"/></span></p>
							<p><span class="s_lf">担保机构：</span><span class="s_l_content">深圳市振业担保有限公司</span><span class="s_lf">当前进度：</span><span class="jdt"></span><span class="jdt"><span class="jindutiao"><b class="qwy_progress" data-value="${product.wcjd}" style="width:0px;"></b></span><span><fmt:formatNumber value="${product.wcjd * 100}" pattern="#.#"/>%</span></span></p>
							
						</div>
					</div>
					<div class="fr">
					<c:if test="${product.productStatus eq '0'}" >
						<p><a class="zw">剩余金额：</a><span><fmt:formatNumber value="${product.leftCopies}" pattern="#,###.#"/></span>元</p>
						<!-- 登录前默认样式 -->
						<p style="display:block;"><a class="zw">账户余额：</a><a id="to_login" class="red20">登陆</a>后可见</p>
						<!-- 登录前默认样式 end-->
						
						<!-- 登陆后返回样式 -->
						<p  style="display:none;"><a class="zw">账户余额：</a><a id="after_login" class="after_login">10,000</a>元<span id="recharge" class="recharge">充值</span></p>
						<!-- 登陆后返回样式end -->
						
						<input id="input_copies" type="text" name="inv.copies" value="投资金额为100的整数倍"/><span class="yuqi">元</span>
						<!-- 投资金额填写框鼠标聚焦离开状态 变化-->
						<script>
							$(function(){
								$("#input_copies").focus(function(){
									//console.log($(this).val());
									if($(this).val()=="投资金额为100的整数倍"){
										$(this).val("");
										$(this).css({
										color:"black"
										});
									}
								});
								
								$("#to_login").click(function(){
									$("#login_alert").show();
								});
							})
						</script>
						<p id="income_box" style="display:block;"><a class="zw">预期收益：</a><!-- 计算器 <a href="calculator.jsp" target="_blank">计算收益</a>--><a  id="income" class="red20">0.00</a>元</p>
						<p id="error_warn" style="display:none;">投资金额请输入100的整数倍</p>
						<!-- 预期理财收益计算 -->
						<script>
							$(function(){
								$("#input_copies").blur(function(){
									$("#income_box").show();
									$("#error_warn").hide();
									var tzje=parseFloat($(this).val());
									var nhsy=parseFloat($("#nhsy").html());
									var lcqx=parseFloat($("#lcqx").html());
									//console.log(tzje);
									//console.log(nhsy);
									//console.log(lcqx);
									if($(this).val()==""){
										$(this).val("投资金额为100的整数倍");
										$(this).css({
											color:"#D0D0D0"
										});
										$("#income").text("0.00");
									}else if(tzje%100!=0){
										$("#income_box").hide();
										$("#error_warn").show();
									}else{
										var interest = tzje*nhsy*0.01/365*lcqx;
										interest = Math.floor(interest*100)/100;
										$("#income").text(parseFloat(interest).toFixed(2));
									}
								});
								
							});
						</script>
						<a class="touzi" style="cursor: pointer;" id="buyNow" >立即投资</a>
						<p class="agree"><span id="agree_check" class="check checked"></span>我已阅读，并同意<a href="" style="color:#3598DB;">《中泰理财购买协议》</a></p>
						<!-- 勾选框选中状态变化 -->
						<script>
							$(function(){
								$("#agree_check").click(function(){
									if($(this).hasClass("checked")){
										$(this).removeClass("checked");
								}else{
										$(this).addClass("checked");
								}	
								});
							});
						</script>
						
					</c:if>
					
					<!-- 以下样式为已还款返回的页面 -->
					<c:if test="${product.productStatus eq '3'}" >
						<p class="p_bg"></p>
						<p class="canotby">该项目已还款</p>
						<a class="touzi" style="cursor: pointer;" id="buyNow" >投资其他项目</a>
					</c:if>
					
					<!-- 以下样式为已售罄返回的页面 -->
					<c:if test="${product.productStatus eq '1'}" >
						<p class="p_bg"></p>
						<p class="canotby">售罄时间：<b>2016-5-18&nbsp;&nbsp;10:30:20</b></p>
						<a class="touzi" style="cursor: pointer;" id="buyNow" >投资其他项目</a>
					</c:if>
					
					</div>
					<div class="clea"></div>
				</div>
				<input type="text" style="display: none;">
				</form>
			</div>
			<!-- 产品投资头部 end-->
			
			<div style="height:30px;"></div>
			<!-- 未登录前显示（新手投资流程） -->
			<div id="xinshou" class="manage" style="display:block;">
				<p>新手投资流程<span style="color:#888;font-size:16px;float:right">温馨提示：为了快速买到心仪产品，建议账户提前充值足够金额！</span></p>
				<ul>
					<li class="mana_li1">
						<span>新人注册</span>
					</li>
					<li class="arrow"></li>
					<li class="mana_li2">
						<span>实名认证</span>
					</li>
					<li class="arrow"></li>
					<li class="mana_li3">
						<span>账户充值</span>
					</li>
					<li class="arrow"></li>
					<li class="mana_li4">
						<span>设置交易密码</span>
					</li>
					<li class="arrow"></li>
					<li class="mana_li5">
						<span>购买产品</span>
					</li>
					<li class="arrow"></li>
					<li class="mana_li6">
						<span>产品计息中</span>
					</li>
					<li class="arrow"></li>
					<li class="mana_li7">
						<span>收益到账</span>
					</li>
				</ul>
				<div class="clea"></div>
			</div>
			<!-- 未登录前显示（新手投资流程） end-->
			
			<!-- 登陆后显示（安全保障） -->
			<div id="anquan" class="manage" style="display:none;">
				<p>安全保障</p>
				<ul>
					<li class="mana_li1">
						<p class="p_up">交易实施 监控</p>
						<p class="p_down">7*24全天交易监控</p>
					</li>
					<li class="mana_li2">
						<p class="p_up">全实名认证交易</p>
						<p class="p_down">所有投资者必须实名认证</p>
					</li>
					<li class="mana_li3">
						<p class="p_up">资金认证提现</p>
						<p class="p_down">只能提现到您名下银行账户</p>
					</li>
					<li class="mana_li4">
						<p class="p_up">信息安全保障</p>
						<p class="p_down">阿里云提供最安全技术</p>
					</li>				
				</ul>
				<div class="clea"></div>
			</div>
			<!-- 登陆后显示（安全保障 end-->
			
			<div style="height:30px;"></div>
			
			<!-- 产品投资底部 -->
			<div class="product_bottom" id="details">
				<ul class="product_title" id="product_title">
					<li class="on">产品详情</li>
					<li>还款保障</li>
					<li class="">投资记录</li>
					<div class="clea"></div>
				</ul>
				<div class="product_Content">
			<!--------------------->		
					
					<div class="content_box" style="display: block;">
						<div class="content_list" >
							<p class="p_title"><span class="s22">产品说明</span><br><span class="s14">Product description</span></p>
							<div id="productDescription" class="text_box">
							<c:choose>
								<c:when test="${product.cplxjs ne '' && product.cplxjs ne ' ' && product.cplxjs ne null }">
									${product.cplxjs}
								</c:when>
								<c:otherwise>
									暂无记录
								</c:otherwise>
							</c:choose>
							<!-- <span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 16px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; 借款人为澳门人士，从事美容美发连锁经营。借款人曾向出借人借款，借款金额为150万元，并且已经按期还款。因近期扩大经营，急需资金周转，向出借人借款，借款金额150万元，以公司经营回款最为还款来源，同时，借款人以其名下一套写字楼作为该笔借款的还款保障，并且在杭州市房管局办理房屋抵押登记手续，若到期无法偿还借款，借款人自愿放弃申请诉讼解决的权利并同意佰亿</span> -->
							</div>
						</div>
	
						<div class="content_list" >
							<p class="p_title"><span class="s22">项目说明</span><br><span class="s14">Project description</span></p>
							<p class="p_star">资金用途：</p>
							<div class="text_box">
								融资方名下企业向海外知名供应商采购钛合金
							</div>
							<p class="p_star">融资方简介：</p>
							<div id="projectDescription"  class="text_box"> 
							<c:choose>
								<c:when test="${product.description ne '' && product.description ne ' ' && product.description ne null }">
									${product.description}
								</c:when>
								<c:otherwise>
									暂无记录
								</c:otherwise>
							</c:choose>
							<!-- <span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>借款金额：</strong>150万元人民币</span><br/>
							<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>资金用途：</strong>用于美容美发连锁店的再扩大经营，急需资金周转</span><br/>
							<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>抵押信息：</strong>以位于杭州市下城区繁华地段朝晖路嘉汇大厦的写字楼为抵押作为此次借款的还款保障。已在杭州市房管局办理相关房屋抵押登记手续若到期无法偿还借款，借款人自愿放弃申请诉
  讼解决的权利并同意中泰理财金融将房屋进行转让过户，所得款项用于偿还该笔借款</span><br/>
							<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>价值评估：</strong>该小区地理位置优越，交通便利，周边配套设施齐全，目前小区的售价为20000元/平方米。此写字楼建筑面积120.5平方米50万元精装修市场估值240万元。专业房产评估机构评估
   210万元，抵押率60%。</span><br/>
							<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>其他内容：</strong>以上内容均为示例</span>
							 -->
							</div>
						</div>
					
						<div class="content_list tab" >
							<p class="p_title"><span class="s22">相关图片</span><br><span class="s14">Related pictures</span></p>
							<div id="productDescription"> 						
							 <c:forEach items="${infoList}" var="list">		 	
							 	<ul id="R_pictures">
							 		<li class="s_img fl" id="s_img1" value="1"><img src="${list}" style="width:100%;"/><p>实地照片1</p></li>
							 		<li class="s_img fl" id="s_img2" value="2"><img src="${list}" style="width:100%;"/><p>实地照片2</p></li>
							 		<li class="s_img fl" id="s_img3" value="3"><img src="${list}" style="width:100%;"/><p>实地照片3</p></li>
							 		<li class="s_img fl" id="s_img4" value="4"><img src="${list}" style="width:100%;"/><p>实地照片4</p></li>
							 		<li class="s_img fl" id="s_img5" value="5"><img src="${list}" style="width:100%;"/><p>实地照片5</p></li>
							 		<li class="s_img fl" id="s_img6" value="6"><img src="${list}" style="width:100%;"/><p>实地照片6</p></li>
							 		<li class="s_img fl" id="s_img7" value="7"><img src="${list}" style="width:100%;"/><p>实地照片7</p></li>
							 	</ul>					 	
							 	<div class="clea"></div>			
							</c:forEach>
							<c:if test="${infoList eq '' || infoList eq '[]' || infoList eq null}">
								<div class="product_img" style="font-size: 28px;">暂无图片</div>
							</c:if>
							</div>
							
						</div>
					</div>
					
					
					<div class="content_box" style="display: none;">			
						<div class="content_list" >
							<p class="p_title"><span class="s22">还款来源</span><br><span class="s14">The source of repayment</span></p>
							<div id="productDescription"  class="text_box"> 						
							<c:choose>
								<c:when test="${product.hkly ne '' && product.hkly ne ' ' && product.hkly ne null }">
								${product.hkly}
								</c:when>
								<c:otherwise>
								暂无记录
								</c:otherwise>
							</c:choose>
						<!-- <span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>第一还款来源：</strong>借款人名下市值240万元的抵押写字楼，足额覆盖借款金额</span><br/>
						<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>第二还款来源：</strong>为借款人企业营业收入，该企业2014年的纯利润收入为400万元，2014年开始扩大经营规模，预计2015年的营业收入达到600万元</span><br/>
						<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>第三还款来源：</strong>为借款人企业营业收入，该企业2014年的纯利润收入为400万元，2014年开始扩大经营规模，预计2015年的营业收入达到600万元</span><br/>
						<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>第四还款来源：</strong>为借款人企业营业收入，该企业2014年的纯利润收入为400万元，2014年开始扩大经营规模，预计2015年的营业收入达到600万元</span><br/>
						<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>第五还款来源：</strong>为借款人企业营业收入，该企业2014年的纯利润收入为400万元，2014年开始扩大经营规模，预计2015年的营业收入达到600万元</span> -->
						</div>
					</div>
					
					<div class="content_list  tab" >
						<p class="p_title"><span class="s22">资金保障</span><br><span class="s14">Capital guarantee</span></p>
						<p class="p_star">融资方筛选：</p>
						<div id="productDescription"  class="text_box"> 						
						<!-- <span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>实力担保：</strong>由实力雄厚的担保公司提供连带责任的保证担保</span><br/>
						<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>本息保障：</strong>若出现未履行还款义务时，依规定将抵押写字楼变现，所得款项有限偿还投资者本息</span><br/>
						<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>资金托管：</strong>交易流程中中泰理财不会触碰用户资金，交易资金将由第三方支付机构托管</span><br/>
						<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>平安理赔：</strong>平安保险将全程对个人资金账户进行安全承保，若在交易过程中因账户安全问题导致资金损失，平安保险将全额赔付</span><br/>
						<span style="color: rgb(76, 76, 76); font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 14px; line-height: 24px; background-color: rgb(255, 255, 255);">&nbsp; &nbsp; &nbsp; &bull;&nbsp;&nbsp;<strong>律所监管：</strong>广东鹏鼎律师事务所个广东天穗律师事务所对债券的行程进行监管见证，确保债权合法有效</span> -->
						<c:choose>
							<c:when test="${product.zjbz ne '' && product.zjbz ne ' ' && product.zjbz ne null }">
								${product.zjbz}
							</c:when>
							<c:otherwise>
								暂无记录
							</c:otherwise>
						</c:choose>
						</div>
						<p class="p_star">多重信用调查：</p>
						<div id="productDescription"    class="text_box"> 	
						<c:choose>
							<c:when test="${product.zjbz ne '' && product.zjbz ne ' ' && product.zjbz ne null }">
								${product.zjbz}
							</c:when>
							<c:otherwise>
								暂无记录
							</c:otherwise>
						</c:choose>
						</div>
						<p class="p_star">还款能力审查：</p>
						<div id="productDescription"    class="text_box"> 	
						<c:choose>
							<c:when test="${product.zjbz ne '' && product.zjbz ne ' ' && product.zjbz ne null }">
								${product.zjbz}
							</c:when>
							<c:otherwise>
								暂无记录
							</c:otherwise>
						</c:choose>
						</div>
						<p class="p_star">风险准备金：</p>
						<div id="productDescription"    class="text_box"> 	
						<c:choose>
							<c:when test="${product.zjbz ne '' && product.zjbz ne ' ' && product.zjbz ne null }">
								${product.zjbz}
							</c:when>
							<c:otherwise>
								暂无记录
							</c:otherwise>
						</c:choose>
						</div>
						<p class="p_star">专业团队贷后监督：</p>
						<div id="productDescription"    class="text_box"> 	
						<c:choose>
							<c:when test="${product.zjbz ne '' && product.zjbz ne ' ' && product.zjbz ne null }">
								${product.zjbz}
							</c:when>
							<c:otherwise>
								暂无记录
							</c:otherwise>
						</c:choose>
						</div>
						<p class="p_star">注：（浙江泽鼎律师事务所与广东天穗律师事务所对本项目债权形成予以监督，确保债权真实合法。）</p>
						<div id="productDescription" class="text_box"></div>
					</div>
					<!--图片部分，已隐藏-->
					<!--<div class="content_list tab mt20" >
						<div class="list_title" style="margin-bottom: 20px;border-bottom: 1px solid #e6e6e6;padding-bottom: 10px;">
							<h4>合同协议</h4>
						</div>
						<div id="productDescription" class="productDescription"> 						
						 <img src="https://ss3.bdstatic.com/iPoZeXSm1A5BphGlnYG/skin/22.jpg" alt="" />
						</div>
					</div>-->
					
				</div>
				<div class="content_box" style="display: none">
 
					<div class="content_list tab" style="display:none;">
						<div>
							<table class="tzjl">
								<tbody>
									<tr>
										<td>序号</td>
										<td>投资人</td>
										<td>投资金额(元)</td>
										<td>投资方式</td>
										<td>投资时间</td>
										<!-- <td>投资状态</td> -->
									</tr>
									<c:forEach items="${investorsList}" var="list">
			                			<tr>
			                				<td></td>
			                				<td>${list.username}</td>
			                				<td class="red"><fmt:formatNumber value="${list.copies}" pattern="#,##0.##"/></td>
			                				<td></td>
			                				<td><fmt:formatDate value="${list.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                				<!-- <td><i class="status_yes"></i>投资成功</td> -->
			                			</tr>
		                			</c:forEach>
								</tbody>
							</table>
							<c:choose>
					            <c:when test="${investorsList eq '[]' || investorsList eq '' || investorsList eq null}">
					            	<div style="text-align: center;margin-top: 15px;">
					            	<img src="${pageContext.request.contextPath}/Product/images/no_record.png" />
					            	</div>
					            </c:when>
					            <c:otherwise>
						            <%-- <jsp:include page="page.jsp" /> --%>
						            
						            <div id="pageUI-box;" style="background:#fff;text-align:center;height:95px;">
										<ul id="pageUl" style="height:95px;padding-top:30px;">
											<li class='btn first'><a href="${pageContext.request.contextPath}${url}&currentPage=1" style='cursor:pointer;' >首页</a></li>
											<li class="prev"><a  id="last" href="${pageContext.request.contextPath}${url}&currentPage=${currentPage-1}" style='cursor:pointer;'>上一页</a></li>
											<li><a style='cursor:pointer;background:#3898DB;color:#FFF;' href="${pageContext.request.contextPath}${url}&currentPage=${currentPage}">1</a></li>
											<li><a >2</a></li>
											<li class="next"><a  id="next" href="${pageContext.request.contextPath}${url}&currentPage=${currentPage+1}" style='cursor:pointer;'>下一页</a></li>
											<li class="last"><a  href="${pageContext.request.contextPath}${url}&currentPage=${pageCount}" style='cursor:pointer;'>末页</a></li>
										</ul>
									</div>
									
									
						            <%-- 
						            <div align="right" style="margin: 15px;">
						            	<a href="${pageContext.request.contextPath}${url}&currentPage=1">首页</a>
						            	<a id="last" href="${pageContext.request.contextPath}${url}&currentPage=${currentPage-1}">上一页</a>
						            	<a id="next" href="${pageContext.request.contextPath}${url}&currentPage=${currentPage+1}">下一页</a>
						            	<a href="${pageContext.request.contextPath}${url}&currentPage=${pageCount}">末页</a>
						            </div>
						            --%>
					            </c:otherwise>
				           </c:choose>
						</div>
					</div>
				</div>
<!------------------------------->
				</div>
			</div>
			<!-- 产品投资底部 end-->
			<div style="height:30px;"></div>
		
		</div>		
		<!-- 主内容 end-->
		
		<!-- 实名未认证弹出框 -->
		<div id="NameSign" class="mask" style="display:none;">
			<div class="message_box">
				<p class="title">账户未实名认证<span class="fr close"></span></p>
				<div class="message_text">
					<p>为保障资金安全，请先完善实名认证信息。</p>
				</div>
				<a href="" class="bt on">确定</a><a id="cancel" class="bt">取消</a>
			</div>
		</div>
		<script>
			$(function(){
				$("#NameSign .close").click(function(){
					$("#NameSign").hide();
				});
				$("#NameSign #cancel").click(function(){
					$("#NameSign").hide();
				});
			});
		</script>
		<!-- 实名未认证弹出框end -->
	
		<!-- 投资登陆弹出框 -->
		<div id="login_alert" class="mask"  style="display:none;">
			<div class="login_c">
				<div class="login_info">
					<p>欢迎登录中泰理财<span class="fr login_close" id="login_close"></span></p>
					<form id="loginForm">
						<ul>
							<li><span class="inp_border"><label></label><input  type="text" id="username" name="users.username" maxlength="11" value="" /><span class="inp_tip"/>请输入手机号码</span></span></li>
							<li><span class="inp_border"><label></label><input type="password" id="password" name="users.password" maxlength="16" value="" /><span class="inp_tip"/>请输入登录密码</span></span></li>
							<li class="forget"><span><span id="IDremember" class="check"></span>记住账号</span><a href="find_password.jsp">忘记密码?</a></li>
							<li><a class="logo_now" id="btnLogin" onclick="goIn()" style="cursor:pointer;">立即登录</a></li>
							<li class="to_reg"><span style="font-size:18px;">还没账号？</span><a href="${pageContext.request.contextPath}/Product/registerUser!regist.action">立即注册</a></li>
						</ul>
					</form>
					<div class="login_border"><i class="txyun"></i><span>您的信息已使用SSL加密技术，数据传输安全</span></div>
				</div>	
			</div>
		</div>
		<script>
			/*勾选框打钩*/
			$(function(){
				$(function(){
					$("#IDremember").click(function(){
						if($(this).hasClass("checked")){
							$(this).removeClass("checked");
						}else{
							$(this).addClass("checked");
						}
						
					});
					$(".inp_tip").click(function(){
						$(this).siblings("input").focus();
					});
					$("#username").bind("input propertychange",function(){e($(this));}).blur(function(){
						if($(this).val() == ""){
							e($(this),1);
						}else{
							e($(this),0);
						}						
					});
					
					$("#password").bind("input propertychange",function(){e($(this));}).blur(function(){
						if($(this).val() == ""){
							e($(this),1);
						}else{
							e($(this),0);
						}						
					});

				});
				function e(obj,tip){			
					if(tip){
						obj.siblings(".inp_tip").css("display","block");
					}
					else{
						obj.siblings(".inp_tip").css("display","none");
					}
				
				}
				
				function goIn(){
					var url = "${pageContext.request.contextPath}/Product/usersLogin!usersLogin.action";
					//alert("登录"+$("#loginForm").serialize());
					//url += "&attention.userId=" + 123456;
					//alert(url);
					var su = "";
					$.ajax({
						type : "post",
						url : url,
						data:$("#loginForm").serialize(),
						success : function(data, textStatus) {
							su = data;
						},
						complete : function(XMLHttpRequest, textStatus) {
							if ("ok" == su.status) {
								jingao(su.json,"提示",function(){
									if("ok" == su.status){
										url="${pageContext.request.contextPath}/Product/login.jsp";
										if(su.url!=null&&su.url!=url){
											window.location.href=""+su.url;	
										}else{
										window.location.href="${pageContext.request.contextPath}/Product/index!loadProduct.action";	
										}
										
									}else{
										window.location.href="${pageContext.request.contextPath}/Product/index!loadProduct.action";
									}
								},0);
							} else if ("err" == su.status) {
								jingao(su.json,"提示","",0);
							} else{
								jingao(su.json,"提示","",0);
							}

						}
					});
				}

				$("#username,#password").keydown(function(event){
					if(event.keyCode==13){
						goIn();
					}
				});
				/*关闭登陆弹出框*/
				$("#login_alert #login_close").click(function(){
					$("#login_alert").hide();
				});
			});		
		</script>
		<!-- 投资登陆弹出框end -->
		
		<!-- 购买流程弹出框 -->
		<div id="buy_progress" class="mask" style="display:none;">
			<div class="message_box">
				<p class="title">确认订单信息<span class="fr close"></span></p>
				<div class="jine_box">
					<p><span class="s_lf">本次投资金额</span><span class="s_lf">预期赚取收益：</span></p>
					<p><span class="s_lf red"><span  id="tz_jine">10000</span><span class="s16">元</span></span><span class="s_lf red"><span id="tz_shouyi">52.83</span><span class="s16">元</span></span></p>
				</div>
				
				<form>
					<p>优&nbsp;&nbsp;惠&nbsp;&nbsp;券：<input type="text" id="youhuiquan" value="暂无可用优惠券" disabled="disabled";/><span id="s_xiala"></span></p>
					<ul id="yhq_xiala" style="display:none;">
						<li>现金券1000元</li>
						<li>现金券100元</li>
					</ul>		
					<p class="p_mid">实际支付：<span class="red"><span id="tz_shiji">10000</span><span class="s16">元</span></span></p>	
					<p class="m_bt15">交易密码：<input type="password" id="mima"/><a href="">忘记交易密码？</a></p>
					<p id="error_psw" style="display:none;">交易密码输入错误</p>
				</form>
				<input id="queren_zf" type="submit" value="确认支付" class="bt"/>
			</div>
		</div>
		<script>
			$(function(){
				/*客户已登录状态下可购买*/
				$("#buyNow").click(function() {	
					if("${usersLogin}" == "") {
						$("#login_alert").show();
					}else{
						var tzje=parseFloat($("#input_copies").val());
						var tzsy=parseFloat($("#income").html());
						if(tzje%100==0){
							$("#buy_progress").show();
							$("#tz_jine").text(tzje);
							$("#tz_shouyi").text(tzsy);
							$("#tz_shiji").text(tzje);
							$("#youhuiquan").attr("value","暂无可用优惠券");
							$("#youhuiquan").css({
								color:"#CCC"
							});	
						}
					}
				});
				/*优惠券选择-有优惠券时才可下拉*/
				$("#s_xiala").click(function(){
					var len=$("#yhq_xiala").children().length;
					var st=$("#yhq_xiala").attr("style");			
					if(len>0){
						/*每次下拉优惠券都初始化"实际应付金额"与"投资金额"相等*/
						$("#tz_shiji").text($("#tz_jine").html());
						$("#yhq_xiala").attr("style","display:block;");
						$("#s_xiala").css({
							background:"url('./images/product_xiala2.jpg') no-repeat center center"
						});
					}
				});
				
				/*鼠标选择优惠券，选好后自动计算应付金额*/
				$("#yhq_xiala>li").mousedown(function(){
					var str=$(this).html();
					$("#youhuiquan").attr("value",str);
					$("#youhuiquan").css({
						color:"#555"
					});	
				}).mouseup(function(){
					$("#yhq_xiala").attr("style","display:none;");
					$("#s_xiala").css({
						background:"url('./images/product_xiala.jpg') no-repeat center center"
					});
					/*每次关闭优惠券都截取优惠券的金额并进行运算，得出"实际应付金额"*/
					var str=$("#youhuiquan").val();
					//console.log(str);
					var L=str.length;
					str=str.slice(3,L-1);
					var youhui=parseFloat(str);
					var yingfu=parseFloat($("#tz_shiji").html())-youhui;
					$("#tz_shiji").text(yingfu);
				});
				$("#buy_progress .close").click(function(){
					$("#buy_progress").hide();
				});
			});
		</script>
		<!-- 购买流程弹出框end -->
		
		<!-- 购买成功弹出框 -->
		<div id="buy_true" class="mask" style="display:none;">
			<div class="message_box">
				<p class="title">购买状态<span class="fr close"></span></p>
				<div class="message_text">
					<p class="p30">恭喜您，购买成功！</p>
					<p class="p16">您本次投资：<span>10,000</span>元，预期收益：<span>50.28</span>元</p>
					<p class="p16">预计还款日期：<span>2017-02-18</span></p>
				</div>
				<a href="" class="bt on bt_lf">继续购买</a><a href="" class="bt">查看投资记录</a>
			</div>
		</div>
		<script>
			$(function(){
				$("#buy_true .close").click(function(){
					$("#buy_true").hide();
				});
			});
		</script>
		<!-- 购买成功弹出框end -->
		
		
		<!-- 购买失败弹出框 -->
		<div id="buy_false" class="mask" style="display:none;">
			<div class="message_box">
				<p class="title">购买状态<span class="fr close"></span></p>
				<div class="message_text">
					<p class="p30">非常遗憾，购买失败！</p>
					<p class="p16">失败原因</p>
				</div>
				<a href="" class="bt on bt_lf">重新购买</a><a href="" class="bt">查看其他产品</a>
			</div>
		</div>
		<script>
			$(function(){
				$("#buy_false .close").click(function(){
					$("#buy_false").hide();
				});
			});
		</script>
		<!-- 购买失败弹出框end -->
	
		<!-- 图片放大查看弹出框 -->
		<div id="R_pictures_big" class="mask" style="display:none;">			 	
			<div id="R_pictures_box">	
				<!-- 参考拼接内容	
				<img src=""/>
				<p style="width:"></p>
				<p class="count" style="display:none;"></p>
				-->				
			</div>									 							
			<span class="close"></span>
			<span class="img_bt_lf"></span>
			<span class="img_bt_rt"></span> 
			<p class="warn_notice" id="no_1" style="display:none;">当前图片为第一张！</p>
			<p class="warn_notice" id="no_last" style="display:none;">当前图片为最后一张！</p>
		</div>
		<script>
		/*图片放大查看效果*/
		$(function(){
			/*点击小图出现大图*/
			$("#R_pictures>li").click(function(){
				$("#R_pictures_big").show();
				var path=$(this).children("img").attr("src");
				var t=$(this).children("p").html();
				var count=$(this).val();
				//console.log(path);	
				//console.log(t);
				//console.log(count);
				var str1='';
				var str2='';
				str1+='<img src="';
				str1+=path;
				str1+='"/>'
				//console.log(str1);
				$("#R_pictures_box").append(str1);
				var w=$("#R_pictures_box").children("img").width();
				//console.log(w);
				str2+='<p style="width:';
				str2+=w+'px';
				str2+='">';
				str2+=t;
				str2+='</p><p class="count" style="display:none;">';
				str2+=count;
				str2+='</p>';
				//console.log(str2);
				$("#R_pictures_box").append(str2);
				
			});
			/*向左翻页*/
			$("#R_pictures_big .img_bt_lf").click(function(){
				$("#no_last").hide();
				var val_lf=$("#R_pictures_box").children(".count").html()-1;
				//console.log(val_lf);
				var path_lf=$('#s_img'+val_lf).children("img").attr("src");
				var t_lf=$('#s_img'+val_lf).children("p").html();
				//console.log(path_lf);
				//console.log(t_lf);
				var str1='';
				var str2='';
				if(val_lf!=0){
					$("#R_pictures_box").empty();
					str1+='<img src="';
					str1+=path_lf;
					str1+='"/>'
					//console.log(str1);
					$("#R_pictures_box").append(str1);
					var w=$("#R_pictures_box").children("img").width();
					//console.log(w);
					str2+='<p style="width:';
					str2+=w+'px';
					str2+='">';
					str2+=t_lf;
					str2+='</p><p class="count" style="display:none;">';
					str2+=val_lf;
					str2+='</p>';
					//console.log(str2);
					$("#R_pictures_box").append(str2);	
				}else{
					$("#no_1").show();
				}
			});
			 /*向右翻页*/
			$("#R_pictures_big .img_bt_rt").click(function(){
				$("#no_1").hide();
				var val_rt=$("#R_pictures_box").children(".count").html();
				val_rt++;
				//console.log(val_rt);
				var path_rt=$('#s_img'+val_rt).children("img").attr("src");
				var t_rt=$('#s_img'+val_rt).children("p").html();
				var len=$("#R_pictures>li").length;
				//console.log(path_rt);
				//console.log(t_rt);
				//console.log(len);
				var str1='';
				var str2='';
				if(val_rt<=len){
					$("#R_pictures_box").empty();
					str1+='<img src="';
					str1+=path_rt;
					str1+='"/>'
					//console.log(str1);
					$("#R_pictures_box").append(str1);
					var w=$("#R_pictures_box").children("img").width();
					//console.log(w);
					str2+='<p style="width:';
					str2+=w+'px';
					str2+='">';
					str2+=t_rt;
					str2+='</p><p class="count" style="display:none;">';
					str2+=val_rt;
					str2+='</p>';
					//console.log(str2);
					$("#R_pictures_box").append(str2);	
				}else{
					$("#no_last").show();
				}
			});
			
			/*关闭大图*/
			$("#R_pictures_big .close").click(function(){
				$("#R_pictures_box").empty();
				$("#no_1").hide();
				$("#no_last").hide();
				$("#R_pictures_big").hide();
				
			});
			
		});
		</script>
		<!-- 图片放大查看弹出框end -->
			
		<jsp:include page="footer.jsp" />
		
		<script>
	
			$(function(){
				
				/*收益奖励*/
				var data_jl=$("#jlv").attr("data-jl");
				console.log(data_jl);
				if(data_jl != "" && data_jl > 0) { 
					var str='<b>+'+data_jl+'%</b>';
					$("#jlv").append(str);
				}
				
				
				var currentPage='${currentPage}';
				var pageCount='${pageCount}';
				if(currentPage==1){
					$("#last").css("color","gray");
					$("#last").removeAttr("href");
				} 
				if(currentPage==pageCount){
					$("#next").css("color","gray");
					$("#next").removeAttr("href");
				}
			} );
			/* $(function(){
				$(".content_list").eq(0).css("display","block");
				$("#product_title").find(".on").removeClass("on");
				$("#product_title li").eq(3).addClass("on");
				$(".content_list").css("display","none")
				$(".content_list").eq(3).css("display","block");
				$("#product_title li").click(function(){
					$("#product_title").find(".on").removeClass("on");
					$(this).addClass("on");
					var ind = $(this).index();
					$(".content_list").css("display","none")
					$(".content_list").eq(ind).css("display","block");
					
				});
			}); */
			$(function(){
				if(getUrlParam("myCss")=='2'){
					$(".content_box").eq(2).css("display","block");
					$(".content_box").eq(2).find(".content_list").css("display","block");
					$("#product_title").find(".on").removeClass("on");
					$("#product_title li").eq(2).addClass("on");
				}else{
					$(".content_box").eq(0).css("display","block");
					$(".content_box").eq(0).find(".content_list").css("display","block");
					$("#product_title").find(".on").removeClass("on");
					$("#product_title li").eq(0).addClass("on");
				}
				//$(".content_list").css("display","none")
				//$(".content_list").eq(3).css("display","block");
				$("#product_title li").click(function(){
					$("#product_title").find(".on").removeClass("on");
					$(this).addClass("on");
					var ind = $(this).index();
					$(".content_box").css("display","none")
					$(".content_box").eq(ind).css("display","block");
					$(".content_box").eq(ind).find(".content_list").css("display","block");
					
				});
			});
			function getUrlParam(name)
			{
				var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
				var r = window.location.search.substr(1).match(reg);  //匹配目标参数
				if (r!=null) return unescape(r[2]); return null; //返回参数值
			} 
			function initProgress(){
				$(".qwy_progress").each(function(){
					//alert(1);
					var val = $(this).attr("data-value");
					val = parseFloat(val)*100;
					$(this).animate({width:val+"%"},1000);
				});
			};
			initProgress();
		</script>
		
		<script type="text/javascript">
		function checkInput(){
			//return true;
				var leftCopies= "${product.leftCopies}";//剩余可投资
				var atleastMoney="${product.qtje}";//起投金额
				var status="${product.productStatus}";
				var num=document.getElementById("input_copies").value;
				//alert(num);
				// var endTime="${product.endTime}";
				//var date1= new Date(Date.parse(strTime.replace(/-/g,   "/"))); 
				//var date=new Date();
				//alert(endTime);
				var strTime=$("#endTime").text();
				var endDate=$("#endDate").text();
				var date1= new Date(Date.parse(strTime.replace(/-/g,   "/"))); 
				var date2= new Date(Date.parse(endDate.replace(/-/g,   "/"))); 
				var date=new Date();
				//alert(format(date));
				//alert(format(date2));
				//alert(format(date2)<format(date));
				
/* 				if("${usersLogin.isBindBank}"!="1"){
					jingao("必须先绑定银行卡才能投资!", "提示", function(){
						window.location.href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action";
					}, 0);
					return false;
				} */
			
				if (isEmpty(num)) {
					jingao("投资金额不能为空", "提示", "", 0);
					return false;
				} else if (isNaN(num)) {
					jingao("投资金额不能为非数字", "提示", "", 0);
					return false;
				} else if (!(/(^[1-9]\d*$)/.test(num))) {
					jingao("投资金额只能是正整数", "提示", "", 0);
					return false;
				} else if (format(date2) <= format(date)) {
					jingao("项目到期前3天不能购买", "提示", "", 0);
					return false;
				} else if (format(date1) <= format(date)) {
					jingao("该投资已过期", "提示", "", 0);
					return false;
				} else if (num * 100 < atleastMoney) {
					jingao("投资金额必须大于等于起投金额", "提示", "", 0);
					return false;
				} else if (num.length > 9) {
					jingao("请输入小于等于九位数的金额", "提示", "", 0);
					return false;
				}
				if (status != 0) {
					jingao("非营销中无法购买", "提示", "", 0);
					return false;
				} else if (num * 1 > leftCopies) {
					jingao("投资金额必须小于等于剩余金额", "提示", "", 0);
					return false;
				}else if(parseInt(num)%50!=0){
					jingao("投资金额必须为50的整数倍", "提示", "", 0);
					return false;
				}else{
					return true;
				}

			}
		
		
		/*
		$("#buyNow").click(function() {
		//alert($("#buyNowFrom").serialize());	
		if("${usersLogin}" == "") {
			/*原来的页面跳转内容
			if (confirm("需要登录才能购买是否立即登录?")) {
				window.location.href = "${pageContext.request.contextPath}/Product/usersLogin!saveUrl.action?url=${url}";
				return false;
			} else {
				return false;
			}
			
		} else {		
			/*原来购买流程的页面跳转
			$.ajax({
				type : "post",
				async : false,
				url : "${pageContext.request.contextPath}/Product/productCategory!isLogin.action?product.id=${product.id}",
				success : function(data) {
					if (data.status == "ok") {
						if (checkInput()) {
							var dialog = art.dialog({
										id : 'dialog_showPayInfo',
										title : false,
										fixed : true,
										content : document.getElementById("psi_load")
									});
							$.ajax({
								type : "post",
								url : "${pageContext.request.contextPath}/Product/User/buyCar!showPayInfo.action",
								data : $("#buyNowFrom").serialize(),
								success : function(data) {
									dialog.content(data);
								}
							});
						} else {
							return false;
						}
					} else if ("hasBuy" == data.status) {
						alert("每个用户只能购买一次新手产品");
						return false;
					} else if ("no" == data.status) {
						if (confirm("需要登录才能购买是否立即登录?")) {
							window.location.href = "${pageContext.request.contextPath}/Product/usersLogin!saveUrl.action?url=${url}";
						} else {
							return false;
						}
						return false;
					} else {
						alert(data.json);
						return false;
					}
				}
			});
			
		}
		
		});
		*/
		</script>
	</body>
</html>