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
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=default"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
	  <script type="text/javascript">
	   //活动连接
		 function theClick(url){		
			 window.open(url);
	     }
		</script>
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
	<body>
		<jsp:include page="../common/header.jsp" />
		
		<!-- 主内容 -->
		<div class="main">
			<!-- 产品投资头部 -->
			<div class="product_head">
			<form id="buyNowFrom" method="post">
			<input type="hidden" name="inv.productId" value="${product.id}">
				<div class="layout">
					<div class="fl">
						<div class="product_name">${product.title} <a>项目到期：<span><fmt:formatDate value="${product.finishTime}" pattern="yyyy-MM-dd"/></span></a>
						<c:choose>
						 <c:when test="${product.hdby eq null || product.hdby eq ''}">
						 </c:when>
						 <c:otherwise>
						    <c:choose>
						       <c:when test="${product.hdljWeb eq null || product.hdljWeb eq '' }">
						       <span class="hot-news  " style="cursor: default">${myel:getSubString(product.hdby,80)}
						           <b class="b linemove"></b>
						       </span></c:when>
						       <c:otherwise><span class="hot-news  " style="cursor: pointer;" onclick="theClick('${product.hdljWeb}');">${myel:getSubString(product.hdby,80)}
							              <b class="b linemove"></b>
							            </span>
							</c:otherwise>
						    </c:choose>						   
						 </c:otherwise>
						</c:choose>
						</div>
						<ul>
							<li>年化收益</li>
							<li>项目总额</li>
							<li>投资期限</li>
							<li>起投金额</li>
							<li class="jdt">当前进度</li>
							<div class="clea"></div>
						</ul>
						<ul>
							<li id="jlv" data-jl='<fmt:formatNumber value="${product.jiangLiEarnings}" />'><span class="b_red"><fmt:formatNumber value="${product.baseEarnings}" pattern="#.#"/></span><a>%</a></li>
							<li><span><fmt:formatNumber value="${product.financingAmount * 0.01}" pattern="#,###.#"/></span>元</li>
							<c:choose>
								<c:when test="${product.productStatus eq 0}">
								<li><span style="color: red; ">${product.tzqx}</span>天</li>
								</c:when>
								<c:when test="${product.productStatus eq 1}">
								<li><span style="color: red; ">进行中</span></li>
								</c:when>
								<c:otherwise>
								<li><span style="color: #35c600; ">${product.cpzt}</span></li>
								</c:otherwise>
							</c:choose>
							
							
							<li><span><fmt:formatNumber value="${product.qtje * 0.01}" pattern="#,###.##"/></span>元</li>
							<li class="jdt"><p class="jindutiao"><b class="qwy_progress" data-value="${product.wcjd}" style="width:0px;"></b></p><span><fmt:formatNumber value="${product.wcjd * 100}" pattern="#.#"/>%</span></li>
							<div class="clea"></div>
						</ul>
						<div class="clea"></div>
						<div class="pay_modth">
							<p><span>起息日：</span>${product.jxfs}</p>
							<p><span>还款方式：</span>${product.fxfs}</p>
							<a href="calculator.jsp" target="_blank">计算收益</a>
						</div>
					</div>
					<div class="fr">
					<c:if test="${product.productStatus eq '0'}" >
						<p><a class="zw">剩余金额</a><span><fmt:formatNumber value="${product.leftCopies}" pattern="#,###.#"/></span><a>元</a></p>
						<p><a class="zw">投资金额</a><input id="input_copies" type="text" name="inv.copies" value="" /><a>元</a></p>
						<a class="touzi" style="cursor: pointer;" id="buyNow" >立即投资</a>
					</c:if>
					<c:if test="${product.productStatus ne '0'}" >
						<p><a class="zw">剩余金额</a>${product.cpzt}</p>
						<p><a class="zw">投资金额</a></p>
						<a class="touzi" style="background: #AAAAAB;" >${product.cpzt}</a>
					</c:if>
					</div>
					<div class="clea"></div>
				</div>
				<input type="text" style="display: none;">
				</form>
			</div>
			<!-- 产品投资头部 end-->
			
			<!-- 专业安全的管理 -->
			<ul class="manage">
				<li class="mana_li1">
					<p>交易实时监控</p>
					<span>7*24全天交易监控</span>
				</li>
				<li class="mana_li2">
					<p>全实名认证交易</p>
					<span>所有投资者必须实名认证</span>
				</li>
				<li class="mana_li3">
					<p>资金认证提现</p>
					<span>资金只能提现到您名下的银行</span>
				</li>
				<li class="mana_li4">
					<p>信息安全保障</p>
					<span>腾讯云提供最安全技术</span>
				</li>
				<div class="clea"></div>
			</ul>
			<!-- 专业安全的管理 end-->
			
						<!-- 产品投资底部 -->
			<div class="product_bottom" id="details">
				<ul class="product_title" id="product_title">
					<li  class="on">投资方向</li>
					<li>还款保障</li>
					<li class="">投资记录</li>
					<div class="clea"></div>
				</ul>
				<div class="product_Content">
			<!--------------------->		
					
<div class="content_box" style="display: block;">
	<div class="content_list mt20" >
						<div class="list_title" style="margin-bottom: 20px;border-bottom: 1px solid #e6e6e6;padding-bottom: 10px;">
							<h4>产品说明</h4>
							 
						</div>
						<div id="productDescription">
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
						<div class="list_title" style="margin-bottom: 20px;border-bottom: 1px solid #e6e6e6;padding-bottom: 10px;">
							<h4>项目说明：</h4>
						</div>
						<div id="productDescription">
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
					
	<div class="content_list tab mt20" >
						<div class="list_title" style="margin-bottom: 20px;border-bottom: 1px solid #e6e6e6;padding-bottom: 10px;">
							<h4>相关图片</h4>
						</div>
						<div id="productDescription" class="productDescription"> 						
							 <c:forEach items="${infoList}" var="list">
								<p>&nbsp;</p>
								<img src="${list}" style="width:100%;"/>
							</c:forEach>
							<c:if test="${infoList eq '' || infoList eq '[]' || infoList eq null}">
								<div class="product_img" style="font-size: 28px;">暂无图片</div>
							</c:if>
						</div>
					</div>
	
	
</div>
<div class="content_box" style="display: none;">
					<div class="content_list tab" >
						<div class="list_title" style="margin-bottom: 20px;border-bottom: 1px solid #e6e6e6;padding-bottom: 10px;">
							<h4>还款来源</h4>
						</div>
						<div id="productDescription" style="padding-left: 20px;"> 						
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
					
					<div class="content_list tab mt20" >
						<div class="list_title" style="margin-bottom: 20px;border-bottom: 1px solid #e6e6e6;padding-bottom: 10px;">
							<h4>资金保障</h4>
						</div>
						<div id="productDescription" style="padding-left: 20px;"> 						
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
				<div class="content_box" style="display: none;">
 
					<div class="content_list" style="display:none;">
						<div class="list_title">
							<h4>投资记录</h4>
						</div>
						<div>
							<table class="tzjl">
								<tbody>
									<tr>
										<td>用户名</td>
										<td>投资金额(元)</td>
										<td>投资时间</td>
										<td>投资状态</td>
									</tr>
									<c:forEach items="${investorsList}" var="list">
			                			<tr>
			                				<td>${list.username}</td>
			                				<td><fmt:formatNumber value="${list.copies}" pattern="#,##0.##"/></td>
			                				<td><fmt:formatDate value="${list.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                				<td><i class="status_yes"></i>投资成功</td>
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
						            <div align="right" style="margin: 15px;">
						            	<a href="${pageContext.request.contextPath}${url}&currentPage=1">首页</a>
						            	<a id="last" href="${pageContext.request.contextPath}${url}&currentPage=${currentPage-1}">上一页</a>
						            	<a id="next" href="${pageContext.request.contextPath}${url}&currentPage=${currentPage+1}">下一页</a>
						            	<a href="${pageContext.request.contextPath}${url}&currentPage=${pageCount}">末页</a>
						            </div>
					            </c:otherwise>
				           </c:choose>
						</div>
					</div>
</div>
<!------------------------------->
				</div>
			</div>
			<!-- 产品投资底部 end-->
		
		</div>
		<!-- 主内容 end-->
		
		<jsp:include page="../common/footer.jsp" />
		
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
				
				if("${usersLogin.isBindBank}"!="1"){
					jingao("必须先绑定银行卡才能投资!", "提示", function(){
						window.location.href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action";
					}, 0);
					return false;
				}
			
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
				}else if(parseInt(num)%100!=0){
					jingao("投资金额必须为100的整数倍", "提示", "", 0);
					return false;
				}else{
					return true;
				}

			}
		
		
		
		$("#buyNow").click(function() {
		//alert($("#buyNowFrom").serialize());
		if ("${usersLogin}" == "") {
			if (confirm("需要登录才能购买是否立即登录?")) {
				window.location.href = "${pageContext.request.contextPath}/Product/usersLogin!saveUrl.action?url=${url}";
				return false;
			} else {
				return false;
			}
		} else {
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
		</script>
	</body>
</html>