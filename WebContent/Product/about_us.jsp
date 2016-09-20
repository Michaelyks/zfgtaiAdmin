<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 关于我们</title>
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" text="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/about_us.css" rel="stylesheet" text="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
	</head>
	
	<body>
		<jsp:include page="header.jsp" />
		
		<div id="ab_us_Index" class="layout Ab_bg">
			<div class="Ab_width1200">
				<ul id="aboutus_nav">
					<li class="on"><a id="nav0" href="about_us.jsp">关于我们</a></li>
					<li><a href="about_advantage.jsp">核心优势</a></li>
					<li><a href="about_security.jsp">安全保障</a></li>
					<li><a href="about_team.jsp">合作机构</a></li>
					<li><a href="about_help.jsp">常见问题</a></li>
					<li><a href="about_recruit.jsp">招才纳贤</a></li>
					<li><a href="about_announce.jsp">网站声明</a></li>
					<li><a id="nav4" href="about_contact.jsp">联系我们</a></li>			
				</ul>
				<div id="us_introduce" class="Ab_box980">
					<img src="./images/About_us_index_banner.jpg"/>
					<ul class="Ab_ul">
						<li>
							<p class="p_title"><span class="s22">关于中泰理财</span><br><span class="s14">About zhongtailicai</span></p>
							<p class="p_detail">中泰理财(www.allyoubank.com)于2016年正式上线，是杭州奥发金融服务外包有限公司旗下专注于中小微企业和个人理财服务的互联网金融综合服务平台。自公司成立以来，秉承"安全、专业、高效、便捷"的经营理念，中泰理财通过将互联网技术与金融服务相融合，努力推动中国惠普金融的创新与发展，致力于广大投资人提供最诚信安全的互联网金融理财服务。</p>	
						</li>
						<li>
							<p class="p_title"><span class="s22">平台的运作模式</span><br><span class="s14">Platform operation mode</span></p>
							<img src="./images/About_us_structure.jpg"/>
						</li>
						<li>
							<p class="p_title"><span class="s22">核心管理团队</span><br><span class="s14">Core management team</span></p>
							<img src="./images/About_us_manager1.jpg">
							<p class="p_right"><span class="s22">祝利明</span><span class="s18">董事长</span></p>
							<p class="p_right p_right_detail">任中国房地产开发集团中房联合集团执行总裁，打造了以房地产开发、资产经营、金融服务为主营业务，产业涉及房地产开发、基础设施配套、规划设计、建材贸易、物业管理、投资信托、营销策划产业基金等各个领域的全国领先品牌，对城市经济发展和地产模式转型都具有重大意义。</p>
						</li>
						<li class="manager2">
							<img src="./images/About_us_manager1.jpg">
							<p class="p_right"><span class="s22">陈恺</span><span class="s18">总经理</span></p>
							<p class="p_right p_right_detail">精通互联网商业模式设计、产品运营与推广等各环节，拥有多年互联网运营与管理工作经历，先后从事过包括金融数据平台、供应链金融、互联网金融、基金等多个业务领域的工作，并于2015年入选年度互联网金融先锋人物。</p>
						</li>
						<li>
							<p class="p_title"><span class="s22">战略投资方</span><br><span class="s14">Strategic investment</span></p>
							<p class="p_detail">中房国泰投资管理有限公司是是由中房集团，国泰君安资产管理股份有限公司等联合成立的一家房地产基金管理公司。主要经营房地产基金、并购基金、股权基金、投资咨询等业务。中房国泰投资管理有限公司于2011年成立，注册资金为5千万人民币，是全国著名的在国家发改委成功备案及上海市规范注册的房地产基金管理公司。</p>
							<p class="p_detail" style="margin-top:40px;">中房国泰投资管理有限公司为体现自己的信心以及保证与其他有限合伙人利益的一致性，组成的投资顾问委员会和由多名专家组成的投资决策委员会进行精准判断与专业决策，为项目的风险控制增加了可靠保障。</p>
						</li>
						<li style="border-bottom:none;">
							<p class="p_title"><span class="s22">公司资质</span><br><span class="s14">Company qualification</span></p>
							<a class="big_show" id="certificate1"><img src="./images/About_us_certificate1.jpg"/ style="margin-left:180px;margin-right:80px;"></a><a class="big_show" id="certificate2"><img src="./images/About_us_certificate2.png"/></a>
						</li>
					</ul>									
				</div>
						
			</div>
			
		</div>
		<div  id="img_big" class="mask" style="display:none;">
			<div id="img_big_box" class="img_big">
				<div class="certificate1_big" style="display:none;" id="certificate1_big">		
					<img src="./images/About_us_certificate1_big.png"/>
				</div>
				<div class="certificate2_big" style="display:none;" id="certificate2_big">
					<img src="./images/About_us_certificate2_big.png"/>
				</div>
				<span class="close"></span>
			</div>
		</div>
		<script>
				$("#certificate1").click(function(){
					$("#img_big").show();
					$("#certificate2_big").hide();
					$("#certificate1_big").show();
				});
				$("#certificate2").click(function(){
					$("#img_big").show();
					$("#certificate1_big").hide();
					$("#certificate2_big").show();
				});
				$("#img_big .close").click(function(){		
					$("#img_big").hide();
				});		
		</script>
			
		<jsp:include page="footer.jsp" />
	</body>
	<script src="${pageContext.request.contextPath}/Product/js/lunbo.js"></script>
	<script>
		/** 文字滚动 */
(function($){
	$.fn.textSlider = function(settings){    
			settings = jQuery.extend({
				speed : "normal",
					line : 2,
						type:1,
					timer : 1000
			}, settings);
			return this.each(function() {
				$.fn.textSlider.scllor( $( this ), settings );
			});
		}; 
		$.fn.textSlider.scllor = function($this, settings){
			//alert($this.html());
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
				//alert(settings.timer);
			};
			var autoStop = function(){
				window.clearInterval(timerID);
			};
			ul.hover(autoStop,autoPlay).mouseout();
			_btnUp.css("cursor","pointer").click( scrollUp );
			_btnUp.hover(autoStop,autoPlay);
			_btnDown.css("cursor","pointer").click( scrollDown );
			_btnDown.hover(autoStop,autoPlay)
		};
	})(jQuery);
	/** 文字滚动 end */
	
	$(function(){
		var li_len = $("#scroll_msg li").length;
		if(li_len>1){
			$("#scroll_msg").textSlider({line:1,type:1,speed:800,timer:2000});
		}
	})
	 
	 
	</script>
	
	<script>
			$(function(){
				

			});
						
			function goComments(){
				if(""==$("#name").val()){
					jingao("姓名不能为空","提示","",0);
					return false;
				}
				if($("#name").val().length>10){
					jingao("请填写正确的姓名","提示","",0);
					return false;
				}
				if(""==$("#contact").val()){
					jingao("联系方式不能为空","提示","",0);
					return false;
				}	
				if($("#contact").val().length>30){
					jingao("联系方式不正确","提示","",0);
					return false;
				}
			//	var e_email = "^([a-zA-Z0-9]+[-|_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[-|_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
			//	var p_phone = "^[1][35789][0-9]{9}$";					
			//	if(e_email.match($("#contact").val())==null && p_phone.match($("#contact").val())==null){					
			//		jingao("联系方式格式有误","提示","",0);
			//		return false;
			//	} 
				var url = "${pageContext.request.contextPath}/Product/comments!saveComments.action";
				var su = "";
				$.ajax({
					type : "post",
					url : url,
					data:$("#CommentsForm").serialize(),
					success : function(data, textStatus) {
						su = data;
					},
					complete : function(XMLHttpRequest, textStatus) {
						if ("ok" == su.status) {
							jingao(su.json,"提示",function(){
								location.reload();
							},0);
						} else if ("err" == su.status) {
							jingao(su.json,"提示","",0);
						} else{
							jingao(su.json,"提示","",0);
						}

					}
				});
			}
			
		</script>
	
</html>
	