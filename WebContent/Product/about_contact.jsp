<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 联系我们</title>
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" text="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/about_us.css" rel="stylesheet" text="text/css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
		
		<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script><!--百度地图-->
		
		<style>
			#nav a{background:#fff;color:black;}
			#nav #top_aboutUs{background:#3598db;color:#fff}
		</style>
	</head>
	
	<body>
		<jsp:include page="header.jsp" />
		<div id="ab_us_Contact" class="layout Ab_bg">
			<div class="Ab_width1200">
				<ul id="aboutus_nav">
					<li><a id="nav0" href="about_us.jsp">关于我们</a></li>
					<li><a href="about_advantage.jsp">核心优势</a></li>
					<li><a href="about_security.jsp">安全保障</a></li>
					<li><a href="about_team.jsp">合作机构</a></li>
					<li><a href="about_help.jsp">常见问题</a></li>
					<li><a href="about_recruit.jsp">招才纳贤</a></li>
					<li><a href="about_announce.jsp">网站声明</a></li>
					<li  class="on"><a id="nav4" href="about_contact.jsp">联系我们</a></li>			
				</ul>
				<div id="us_contact" class="Ab_box980" style="padding-top:30px;">
				  <div class="container">
  					<div style="width:920px;height:300px;margin:0px 30px;" id="dituContent"></div>
					<script type="text/javascript">
    					//创建和初始化地图函数：
    					function initMap(){
        					createMap();//创建地图
        					setMapEvent();//设置地图事件
        					addMapControl();//向地图添加控件
        					addMarker();//向地图中添加marker
    					}
    
    					//创建地图函数：
    					function createMap(){
        					var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        					var point = new BMap.Point(120.11494,30.299598);//定义一个中心点坐标
        					map.centerAndZoom(point,16);//设定地图的中心点和坐标并将地图显示在地图容器中
        					window.map = map;//将map变量存储在全局
    					}
    
    					//地图事件设置函数：
    					function setMapEvent(){
        					map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        					map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        					map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        					map.enableKeyboard();//启用键盘上下左右键移动地图
    					}
    
    					//地图控件添加函数：
    					function addMapControl(){
        					//向地图中添加缩放控件
							var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
							map.addControl(ctrl_nav);
        					//向地图中添加缩略图控件
							var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
							map.addControl(ctrl_ove);
        					//向地图中添加比例尺控件
							var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
							map.addControl(ctrl_sca);
    					}
    
    					//标注点数组
    					var markerArr = [{title:"杭州奥发金融服务外包有限公司",content:"浙江省杭州市拱墅余杭塘路莱茵矩阵国际6号楼406室",point:"120.11494|30.299598",isOpen:0,icon:{w:21,h:21,l:0,t:0,x:6,lb:5}}
		 				];
    					//创建marker
    					function addMarker(){
        					for(var i=0;i<markerArr.length;i++){
            				var json = markerArr[i];
            				var p0 = json.point.split("|")[0];
            				var p1 = json.point.split("|")[1];
            				var point = new BMap.Point(p0,p1);
							var iconImg = createIcon(json.icon);
            				var marker = new BMap.Marker(point,{icon:iconImg});
							var iw = createInfoWindow(i);
							var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});
							marker.setLabel(label);
            				map.addOverlay(marker);
            				label.setStyle({
                        		borderColor:"#808080",
                        		color:"#333",
                        		cursor:"pointer"
            				});
			
							(function(){
								var index = i;
								var _iw = createInfoWindow(i);
								var _marker = marker;
								_marker.addEventListener("click",function(){
				    				this.openInfoWindow(_iw);
			    				});
			    				_iw.addEventListener("open",function(){
				    				_marker.getLabel().hide();
			    				})
			    				_iw.addEventListener("close",function(){
				    				_marker.getLabel().show();
			    				})
								label.addEventListener("click",function(){
				    				_marker.openInfoWindow(_iw);
			    				})
								if(!!json.isOpen){
									label.hide();
									_marker.openInfoWindow(_iw);
								}
							})()
        					}
    					}
    					//创建InfoWindow
    					function createInfoWindow(i){
        					var json = markerArr[i];
        					var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>"+json.content+"</div>");
        					return iw;
    					}
    					//创建一个Icon
    					function createIcon(json){
        					var icon = new BMap.Icon("http://app.baidu.com/map/images/us_mk_icon.png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})
        					return icon;
    					}
    
    					initMap();//创建和初始化地图
					</script>
					</div>
			
					<ul class="Ab_ul">
						<li>
							<p class="p_title"><span class="s22">联系我们</span><br><span class="s14">Contact</span></p>
							<p class="Ab_p18">办公地址：杭州市拱墅区余杭塘路515号矩阵国际6号楼</p>
							<p class="Ab_p18">邮政编码：310000</p>
								
						</li>
						<li>
							<p class="p_title"><span class="s22">客户服务</span><br><span class="s14">Customer server</span></p>
							<p class="Ab_p18">如果您在使用中泰理财的过程中，有任何疑问请您与中泰理财客服人员联系。</p>
							<p class="Ab_p18">客服电话：400-123-6123</p>
							<p class="Ab_p18">工作时间：9:00-21:00</p>
							<p class="Ab_p18">zhongtailicai</p>
							
						</li>
						<li>
							<p class="p_title"><span class="s22">核心管理团队</span><br><span class="s14">Core management team</span></p>
							<p class="Ab_p18">欢迎您用以下方式留言：</p>
							<form class="message_box">
								<label>姓名：</label><input type="text" class="em"/>
								<label style="margin-left:30px;">联系方式：</label><input type="text" value="请输入您的手机号或邮箱" id="contact_way"/>
								<script>
									$("#contact_way").focus(function(){
										if($(this).val()=="请输入您的手机号或邮箱"){
											//$(this).attr("value","");
											$(this).val("");
											$(this).css({
												color:"black"
											});
										}	
									});
									$("#contact_way").blur(function(){
										if($(this).val()==""){
											//$(this).attr("value","请输入您的手机号或邮箱");
											$(this).val("请输入您的手机号或邮箱");
											$(this).css({
												color:"#CCC"
											});
										}				
									});
								</script>
								<br>
								<label class="liuyan">留言：</label>
								<textarea></textarea>
								<input type="submit" value="发送消息" class="send"/>
							</form>
						</li>			
					</ul>									
				</div>
			</div>						
		</div>
		
		<!--
		<div id="ab_us_f0">
			<div class="layout">
				<img src="${pageContext.request.contextPath}/Product/images/aboutus_top_img.png"/>
				<div class="link_btn"><a href="#" class="in_display">帮助中心</a><a href="mao" class="in_display">联系我们</a></div>
			</div>
		</div>
		<div id="ab_us_f1">
			<div class="layout">
				<div class="fl">
					<ul>
						<li>【xxxxx】通告</li>
					</ul>
				</div>
				<div class="fr"><a href="#"><span>more</span>&raquo;</a></div>
				<div class="clea"></div>
			</div>
		</div>
		<div id="ab_us_f2" class="layout">
			<ul id="aboutus_nav">
				<a href="about_us.jsp"><li class="ab_li0 ab_us_on">公司简介</li></a>
				<a href="about_team.jsp"><li class="ab_li1">团队介绍</li></a>
				<a href="about_advantage.jsp"><li class="ab_li2">核心优势</li></a>
				<a href="about_security.jsp"><li class="ab_li3">用户保障</li></a>
				<a href="about_media.jsp"><li class="ab_li4">媒体报道</li></a>
				<a href="about_recruit.jsp"><li class="ab_li5">招兵买马</li></a>
			</ul>
			<div class="jianjie">
				<p class="gaikuang"></p>
				<p>
					深圳中泰理财金融服务有限公司成立于2015年，注册资本1000万元人民币，在职员工100余名，为中小企业和个人投资者提供高效，安全，便捷的
					P2P理财服务，管理团队成员均来自银行，法律，风控，互联网资深专业人士，拥有国内外从业相关经验，公司与合作的金融机构严格为投资者把控风
					险。树立独特竞争优势，实现全民理财梦想
				</p>
				<p>“中泰理财”携手平安保险，为公司平台的个人投资者投保个人账户资金安全保险，最大限度保障投资者的资金安全，为投资者争取更多的权益和保障</p>
				<img src="${pageContext.request.contextPath}/Product/images/aboutus_liuchen.png"/>
			</div>
		</div>
		<div id="ab_us_f3">
			<div class="layout">
				<p class="zizhi"></p>
				<ul>
					<li><img src="${pageContext.request.contextPath}/Product/images/aboutus_zhengshu_0.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/aboutus_zhengshu_1.jpg"/></li>
					<li><img src="${pageContext.request.contextPath}/Product/images/aboutus_zhengshu_2.jpg"/></li>
				</ul>
				<div id="prev"></div>
				<div id="next"></div>
			</div>
		</div>
		<div id="ab_us_f4">
			<div class="layout">
				<div class="fl">
					<h4>联系我们</h4>
					<p><b>办公地址：</b>深圳市罗湖区桂园街道和平路3001鸿隆世纪广场A座26A</p>
					<p>【老街地铁口B出口直走穿过天桥即可看到鸿隆世纪广场】</p>
					<ul>
						<li><b>客服热线：</b>400-806-5993    0755-33255530 </li>
						<li><b>服务时间：</b>9:00-18:00</li>
						<li><b>客服邮箱：</b>baiyimao@baiyimao.com</li>
						<li><b>官方微信：</b>baiyimao</li>
					</ul>
				</div>
				<div class="fr">
					<h4>欢迎您用以下方式留言：</h4>
					<form>
						<label>姓名</label><input type="text" class="em"/>
						<label>Email</label><input type="text" />
						<p></p>
						<label class="liuyan">留言</label><textarea></textarea>
					</form>
					<div class="sent_btn">
						<span>Sent message</span><p>发送消息</p>
					</div>
				</div>
				<div class="clea"></div>
			</div>

			<img src="${pageContext.request.contextPath}/Product/images/aboutus_location.jpg"/>
		</div>
		-->
		
		<jsp:include page="footer.jsp" />
	</body>
	
</html>		
