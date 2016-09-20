<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
    <link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" text="text/css"/>
<link href="${pageContext.request.contextPath}/Product/css/about_us.css" rel="stylesheet" text="text/css"/>
     <script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<!--
		<div id="ab_us_f1">
			<div class="layout">
			 	<div class="fl" id="scoll_gg">
					<ul >
						<li>【xxxxx】通告1</li>
					</ul>
					<ul >
						<li>【xxxxx】通告2</li>
					</ul>
					<ul >
						<li>【xxxxx】通告3</li>
					</ul>
				</div>
				<div class="fr"><a href="#"><span>more</span>&raquo;</a></div>
				<div class="clea"></div>
			</div>
		</div>
		-->
		<div  class="aboutus_box">
			<div class="width1200">
				<ul id="aboutus_nav">
					<a id="nav0" href="about_us.jsp#ab_us_f2"><li class="on">关于我们</li></a>
					<a href="about_advantage.jsp#ab_us_f2"><li>核心优势</li></a>
					<a href="about_security.jsp#ab_us_f2"><li>安全保障</li></a>
					<a href="about_team.jsp#ab_us_f2"><li>合作伙伴</li></a>
					<a href="about_help.jsp#ab_us_f2"><li>常见问题</li></a>
					<a href="about_recruit.jsp#ab_us_f2"><li>招才纳贤</li></a>
					<a href="about_announce.jsp#ab_us_f2"><li>网站声明</li></a>
					<a id="nav4" href="${pageContext.request.contextPath}/Product/indexNotice!loadMedia.action#ab_us_f2"><li>联系我们</li></a>
				
				</ul>
			</div>	
		</div>
		
			
		
		<script>
			$(function(){
				var h = window.location.href.split("/").pop();
				var ind = 0;
				if(h.indexOf("about_advantage.jsp")==0){
		   			ind = 1;
		    	}
				else if(h.indexOf("indexNotice")==0){
		   			ind = 3;
		    	}
				else if(h.indexOf("about_recruit.jsp")==0){
		   			ind = 4;
		    	}
				else if(h.indexOf("about_help.jsp")==0){
		   			ind = 5;
		    	}
				else if(h.indexOf("about_security.jsp")==0){
		   			ind = 2;
		    	}
				else if(h.indexOf("about_team.jsp")==0){
		   			ind = 1;
		    	}
				$("#aboutus_nav li").eq(ind).addClass("ab_us_on");
				//公告滚动
				//$("#scoll_gg").textSlider({line:1,type:1,speed:800,timer:2000});
			});	
			
			
		</script>
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
		
		
		
