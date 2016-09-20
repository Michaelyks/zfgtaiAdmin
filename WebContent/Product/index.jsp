<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
     <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>

<!doctype html>
<html>

	<head>
		<title>中泰理财官网</title>
		<meta charset="utf-8" />
		<meta name = "Description" content = "中泰理财提供安全有保障的互联网金融投资产品，年化收益高，公司实力雄厚。最权威安全的P2P网贷平台，首选中泰理财。一只会赚钱的猫。详询：400-806-5993" />
		<meta name = "Keywords" content ="投资理财,p2p理财,融资融券,理财产品,互联网金融,小额贷款,金融投资,理财小知识,理财产品排行,理财方法,p2p网贷,理财规划师,网贷平台,信托,无抵押贷款,风投,小额投资,正规信贷,个人贷款,证券,项目融资,P2P" />
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/common_product.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/index.css" rel="stylesheet" type="text/css"/>
		<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	</head>
	<body>
		<jsp:include page="header.jsp" />
		
		<!-- Banner -->
		<div class="banner">
			<ul class="lun">
				<li class="aa" style="background: url('${pageContext.request.contextPath}/Product/images/banner_zcwy.jpg') no-repeat center center;">
					<a href="http://www.baiyimao.com/wgtz/Product/indexNotice!getNoticeById.action?noticeId=8a7dac54539f04e20153a24d21805f5c#ab_us_f2"></a>
				</li>
				<li  style="background: url('${pageContext.request.contextPath}/Product/images/banner3.jpg') no-repeat center center;">
					<a href="http://www.baiyimao.com/wgtz/Product/indexNotice!getNoticeById.action?noticeId=8a7dac5453854516015388b699805c74#ab_us_f2"></a>
				</li>
			</ul> 
			<div class="layout box">				
				<div class="right_box" style="right:-7px;">
					<div class="right_box_bg filte"></div>
					<div class="indexReg">
						<p class="to_login">
						
						<c:choose>
							<c:when test="${usersLogin eq null}">
								<a class="regBnt" style="color:#FFF;" href="${pageContext.request.contextPath}/Product/registerUser!regist.action">注册送50元,首投最高返1000元</a>
								已有账号？<a href="${pageContext.request.contextPath}/Product/login.jsp">立即登录</a>
							</c:when>
							<c:otherwise>
								<a class="regBnt" style="color:#FFF;" href="${pageContext.request.contextPath}/Product/registerUser!regist.action">进入我的账户</a>
								
								上次登录时间：
							</c:otherwise>
						</c:choose>
						</p>
					</div>
				</div>
			</div>
			
		</div>
		<!-- Banner end-->
		<!-- 专业团队 -->
		<div class="height40"></div>
		<div class="team">			
			<ul class="layout">
				<li class="team1">
					<p>专业的投资顾问团队</p>
					<span>拥有连续十年以上的国内外证券，期货，商品，外汇等实战经验。拥有连续五年以上的持续实盘盈利记录。</span>
				</li>
				<li class="team2">
					<p>创新安全的资管模式</p>
					<span>中泰理财创新的资金管理模式，让用户的投资本金在投资期，安全停留在平台第三方托管账户内。确保用户本金随时可取回。</span>
				</li>
				<li class="team3">
					<p>覆盖全球的投资渠道</p>
					<span>中泰理财与全球知名银行、房产、证券、基金、贸易等公司达成战略合作协议，拥有资深丰厚的全球投资经验，跨界资源整合能力以及投资信息来源。</span>
				</li>
			</ul>
		</div>
		<!-- 专业团队 end-->
		<div class="height40"></div>
		
		<!-- 主内容 -->
		<div class="main">
			<div class="layout">
							
				<div class="height40"></div>
				<div class="fl">	
					<div class="car_product xsfl" id="xsfl">
						<a class="fl car_dec" href="">
							<h4>新人福利</h4>
							<span>新手特享，超高收益，每日限量，先到先得！</span>							
						</a>
						<div class="fr">
							<ul>
								<li class="fl hot_product">
									<div class="hot_product_box">
										<p class="title">新手尊享NO.001<span class="xrzx">100元起购，首投最高返1000元</span></p>
										<ul>
											<li class="rate fl rate1"><span class="red text_bt2"><span class="s48">15.00</span>%</span><br><span class="text_bt text_bt1">年化收益率</span></li>
											<li class="rate fl"><span class="black text_bt2"><span class="s36 l_black">100</span>天</span><br><span class="text_bt">理财期限</span></li>
											<li class="fl"><a class="choose" href="">立即购买</a></li>
										</ul>		
									</div>	
									<div class="hot_product_box xszx_bt">
										<p class="title">新手尊享<img src="./images/index_xsfl3.jpg"/><span>首投奖励</span></p>
										<p class="xszx_jl">
											<span class="red fl"><span class="s_green s_green1">奖励<br>50元</span><br><span class="text_bt">满500元</span></span>
											<span class="red fl"><span class="s_green">奖励<br>100元</span><br><span class="text_bt">满1000元</span></span>
											<span class="red fl"><span class="s_green">奖励<br>300元</span><br><span class="text_bt">满3000元</span></span>
											<span class="red fl"><span class="s_green">奖励<br>600元</span><br><span class="text_bt">满5000元</span></span>
											<span class="red red1 fl"><span class="s_green">奖励<br>1000元</span><br><span class="text_bt">满10000元</span></span>
										</p>
									</div>										
								</li>
								<li class="fl xszx_rt">
									<a href=""><img src="./images/index_xsfl1.jpg"/></a><br>
									<a href=""><img src="./images/index_xsfl2.jpg"/></a>
								</li>
							</ul>
						</div>
					</div>
					<div class="height40"></div>
					<!-- 热门推荐
					<div class="car_product tslc" id="tslc">
						<div class="fl car_dec">
							<h4>热门推荐</h4>
							<span>精选平台热门产品，为您节省选择时间，快速锁定心仪标的！</span>							
						</div>
						<div class="fr">
						</div>
					</div>
					热门推荐 end-->
					<!-- 优选理财 -->
					<div class="car_product yxlc" id="yxlc">
						<a class="fl car_dec" href="">
							<h4>理财专区</h4>
							<span>海量标的，任您自由选择！</span>
						</a>
						<div class="fr">
							<ul>
								<li class="fl yxlc_product">
									<p class="title">贸易通产品名NO.001</p>
									<ul>
										<li class="rate fl rate1"><span class="red  text_bt"><span class="s48">15.00</span>%</span><br><span class="text_bt text_bt1">年化收益率</span></li>
										<li class="rate fl"><span class="black"><span class="s36 l_black">100</span>天</span><br><span class="text_bt  text_bt2">理财期限</span></li>
									</ul>
									<p class="jine"><span class="lf">起购金额</span><span class="rt">1,000</span>元</p>
									<p class="jine" ><span  class="lf">剩余金额</span><span class="rt" >1,000,000</span>元</p>
									<p><a class="choose" href="">立即购买</a></p>
									<p class="zhenxuan">多重安全保障&nbsp;&nbsp;严格甄选产品</p>
								</li>
								<li class="fl yxlc_product">
									<p class="title">贸易通产品名NO.001</p>
									<ul>
										<li class="rate fl rate1"><span class="red  text_bt"><span class="s48">15.00</span>%</span><br><span class="text_bt text_bt1">年化收益率</span></li>
										<li class="rate fl"><span class="black"><span class="s36 l_black">100</span>天</span><br><span class="text_bt  text_bt2">理财期限</span></li>
									</ul>
									<p class="jine"><span class="lf">起购金额</span><span class="rt">1,000</span>元</p>
									<p class="jine" ><span  class="lf">剩余金额</span><span class="rt" >1,000,000</span>元</p>
									<p><a class="choose  choose1" href="">已售罄</a></p>
									<p class="zhenxuan">多重安全保障&nbsp;&nbsp;严格甄选产品</p>
								</li>
								<li class="fl yxlc_product no-b">
									<p class="title">贸易通产品名NO.001</p>
									<ul>
										<li class="rate fl rate1"><span class="red  text_bt"><span class="s48">15.00</span>%</span><br><span class="text_bt text_bt1">年化收益率</span></li>
										<li class="rate fl"><span class="black"><span class="s36 l_black">100</span>天</span><br><span class="text_bt  text_bt2">理财期限</span></li>
									</ul>
									<p class="jine"><span class="lf">起购金额</span><span class="rt">1,000</span>元</p>
									<p class="jine" ><span  class="lf">剩余金额</span><span class="rt" >1,000,000</span>元</p>
									<p><a class="choose choose1" href="">已还款</a></p>
									<p class="zhenxuan">多重安全保障&nbsp;&nbsp;严格甄选产品</p>
								</li>
							</ul>
						</div>
						<div class="clea"></div>
					</div>
					<!-- 优选理财 end-->
				
				</div>
				<div class="clea"></div>
							

			</div>
			
		</div>		
		<!-- 主内容 end-->
		
		<!-- 网站动态 -->
		<div  class="height40" style="background:#F3F3F3;"></div>
		<div class="dynamic" style="position:relative;">
			
			<div class="layout">
				<div class="fl problem information">
					<p><span class="fl">动态资讯</span><a href="">查看更多&GT;&GT;</a></p>
					<ul>
						<li><a href=""><img src="./images/index_information1.jpg"/>公司为确保债权项目的真实、合法及有效性，所有债权项目形成过程中由：广州天穗律师事务......</a></li>
						<li><a href=""><img src="./images/index_information2.jpg"/>我们通过与合作持牌的金融机构推荐的优质项目中为广大投资者筛选出远高于银行年化3%......</a></li>
					</ul>
				</div>
				<div class="fr problem">
					<p><span>常见问题</span><a href="">查看更多&GT;&GT;</a></p>
					<ul>
						<li><a href="">我的资金在中泰理财安全吗？</a></li>
						<li><a href="">充值、投资时是否有手续费？</a></li>
						<li><a href="">我可以申请注册几个实名认证的账号？</a></li>
						<li><a href="">投资金额是否有限制？</a></li>
						<li><a href="">投资后可以撤回吗？</a></li>
						<li><a href="">提现需要多长时间到达银行账户？</a></li>
					</ul>
				</div>
				<!-- 
				<div class="fr notice_c" style="display:block;">
					<div class="notice">平台公告<a href="${pageContext.request.contextPath}/Product/indexNotice!loadNotice.action#ab_us_f2">更多&gt;</a></div>
					<ul class="notice_detail">
						<c:forEach items="${noticeList}" var="notice">
							<li>
							<a href="${pageContext.request.contextPath}/Product/indexNotice!getNoticeById.action?noticeId=${notice.id}#ab_us_f2">
							<font class="shenglve">${notice.title}</font>
							<span><fmt:formatDate value="${notice.insertTime}" pattern="yyyy-MM-dd" /></span></a></li>
						</c:forEach>												
					</ul>
				</div>
				<div class="fr notice_c" style="display:none;">
					<div class="notice">SEO优化</div>
					<ul class="notice_detail">
						<c:forEach items="${hideList}" var="notice">
							<li>
							<a href="${pageContext.request.contextPath}/Product/indexNotice!getNoticeById.action?noticeId=${notice.id}">
							<font class="shenglve">${notice.title}</font>
							<span><fmt:formatDate value="${notice.insertTime}" pattern="yyyy-MM-dd" /></span></a></li>
						</c:forEach>												
					</ul>
				</div>
				 -->
				<div class="clea"></div>
				<div class="partner">
					<p>合作机构</p>
					<ul>
						<a href="http://toutiao.com/" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/parter_toutiao.jpg"/></a>
						<a href="http://www.ifeng.com/" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/parter_fenghuang.jpg"/></a>
						<a href="http://www.sohu.com/" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/parter_sohu.jpg"/></a>
						<a href="http://www.lianlianpay.com/" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/parter_yibao.jpg"/></a>
						<a href="http://sj.360.cn/index.html" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/parter_360.jpg"/></a>
						<a href="http://shouji.baidu.com/" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/baidu_helper.jpg"/></a>
					</ul>
					<ul>
						<a href="http://www.gtja.com/" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/parter_guotaija.jpg"/></a>
						<a href="http://www.lawtime.cn/lawfirm/lll107816910107822004" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/parter_lowyer.jpg"/></a>
						<a href="http://www.people.com.cn/" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/parter_people.jpg"/></a>
						<a href="http://www.aliyun.com/" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/parter_tengxuny.jpg"/></a>
						<a href="http://www.ccb.com/cn/home/index.html" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/parter_ccb.jpg"/></a>
						<a href="http://www.sina.com.cn/" target="_blank"><img src="${pageContext.request.contextPath}/Product/images/parter_sina.jpg"/></a>
					</ul>
				</div>
			</div>
		</div>
		<!-- 网站动态 end-->
		<jsp:include page="footer.jsp" />
	
		<script>	
			var lun;
			var i_ban = 0;
			var len = 0;
			var lun;
			//banner轮播初始值
			$(function(){
				$("#yxlc .list_status").eq(0).css("display","block");
				$("#yxlc .car_title li").click(function(){
					$("#yxlc").find(".on").removeClass("on");
					$(this).addClass("on");
					var ind = $(this).index();
					$("#yxlc .list_status").css("display","none");
					$("#yxlc .list_status").eq(ind).css("display","block");
				});
				//优选理财书签效果结束
				$("#tslc .list_status").eq(0).css("display","block");
				$("#tslc .car_title li").click(function(){
					$("#tslc").find(".on").removeClass("on");
					$(this).addClass("on");
					var ind = $(this).index();
					$("#tslc .list_status").css("display","none");
					$("#tslc .list_status").eq(ind).css("display","block");
				});
				//特色理财书签效果结束
				$(function(){
				//文字滚动
				var num = $("#scroll li").length;
				if(num>=7){
					 $(".touz_list").textSlider({line:4,type:1,speed:800,timer:4000});
				}
				else if(num>=5 && num<=6 ){
					 $(".touz_list").textSlider({line:1,type:1,speed:800,timer:2000});
					}
					
				});
				(function($){
					$.fn.textSlider = function(settings){    
						settings = jQuery.extend({
							speed : "normal",
								line : 1,
									type:1,
								timer : 1000
						}, settings);
						return this.each(function() {
							$.fn.textSlider.scllor( $( this ), settings );
						});
					}; 
					$.fn.textSlider.scllor = function($this, settings){
						var ul = $( "ul:eq(0)", $this );
						var timerID;
						var li = ul.children();
						var _btnUp=$(".up:eq(0)", $this)
						var _btnDown=$(".down:eq(0)", $this)
						var liHight=$(li[0]).height();
						var upHeight=0-settings.line*liHight;
						var scrollUp=function(){
							_btnUp.unbind("click",scrollUp);
							ul.animate({marginTop:upHeight},settings.speed,function(){
								for(i=0;i<settings.line;i++){
									 ul.find("li:first").appendTo(ul);
						}
								ul.css({marginTop:0});
						_btnUp.bind("click",scrollUp); 
							});	
						};
						var scrollDown=function(){
							_btnDown.unbind("click",scrollDown);
							ul.css({marginTop:upHeight});
							for(i=0;i<settings.line;i++){
								ul.find("li:last").prependTo(ul);
							}
							ul.animate({marginTop:0},settings.speed,function(){
								_btnDown.bind("click",scrollDown); 
							});	
						};
						var autoPlay=function(){
							timerID = window.setInterval(scrollUp,settings.timer);
						};
						var autoStop = function(){
							window.clearInterval(timerID);
						};
						autoPlay();
						_btnUp.css("cursor","pointer").click( scrollUp );
						_btnDown.css("cursor","pointer").click( scrollDown );
						
					};
				})(jQuery);
				//他们正在理财滚动结束
				
				len = $(".lun li").length;
				var btn = "<div class='btn'><ul>";
				for(var i2=0; i2 < len; i2++){
					btn += "<li></li>";
				}
				btn += "</ul></div>";
				$(".lun").append(btn);
				$(".lun .btn li").removeClass("on").eq(i_ban).addClass("on");
				$(".lun .btn li").bind("mouseover",function(){
					i_ban = $(".lun .btn li").index(this);
					clearInterval(lun);
					ee();
				}).bind("mouseout",function(){
					if(window.lun)
						window.clearInterval(lun);
					lun = setInterval("ee()",5000);
				});
				
				$(".lun").bind("mouseover",function(){
					clearInterval(lun);
				}).bind("mouseleave",function(){
					if(window.lun)
						window.clearInterval(lun);
					lun = setInterval("ee()",5000);
				});
				
				lun = setInterval("ee()",5000);
				//banner轮播结束
	
			});
			function ee(){
					$(".lun .btn li").removeClass("on").eq(i_ban).addClass("on");
					$(".lun li.aa").removeClass("aa").stop().animate({"opacity":"0","z-index":"0"},500,function(){jQuery(this).hide()});	
					$(".lun li").eq(i_ban).addClass("aa").show().stop().animate({"opacity":"1","z-index":"1"},500);
					i_ban++;
					if(i_ban>=len){
						i_ban=0;
					}
				}
			//banner轮播调用函数结束	
	
		</script>
	
	</body>
</html>