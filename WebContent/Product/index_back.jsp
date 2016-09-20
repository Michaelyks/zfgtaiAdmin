<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
<meta charset="utf-8">
<meta name="360-site-verification" content="5177cb9b0587840b5310d20e74d6e289" />
<title>中泰理财 - 首页</title>
<link href="css/common.css" rel="stylesheet" type="text/css">
<link href="css/index.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/jquery.superslide.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
</head>

<body>
<jsp:include page="top.jsp" />
<script type="text/javascript">choosePage("1");</script>
<div class="ui_slide">
	<ul class="lun">
		<li style=" background:url('images/baner_pa_bar.jpg') repeat-x"><a href="#"><img src="images/baner_pa.png" /></a></li>
		<li style=" background:url('images/li_bar.jpg') repeat-x"><a href="#"><img src="images/slide_img2.png" /></a></li>
		<%-- <li style="background:url('images/reg_1000_bg.jpg') repeat-x"> <a href="${pageContext.request.contextPath}/Product/productCategory!loadProduct.action"><img src="images/reg_1000_2.png" /></a></li> --%>
	</ul>
	<c:if test="${usersLogin eq null}">
		<div class="ui_slide_box" id="divRegister" style="display:block;">
			<div class="ui_slide_box_right">
				<h2>国有银行资金监管、保险公司承保</h2>
				<a  href="${pageContext.request.contextPath}/Product/register.jsp" class="btn">免费注册 <span>送1000元收益</span></a>
				<p class="ui_slide_box_login"><a href="${pageContext.request.contextPath}/Product/login.jsp">立即登录</a></p>
				<p class="ui_slide_box_info">实时数据，极致透明</p>
			</div>
		</div>
	</c:if>
</div>
<div class="content wrap cf">
	<div class="ui_describe">
    	<div class="item item_1">
        	<h4><img src="images/describe_tit1.png" /></h4>
            <p>拥有连续十年以上的国内外证券，期货，商品，外汇等实战经验。</p>
            <p>拥有连续五年以上的持续实盘盈利记录。</p>
        </div>
        <div class="item item_2">
        	<h4><img src="images/describe_tit2.png" /></h4>
            <p>中泰理财创新的资金管理模式，让用户的投资本金，在投资期，安全停留在平台第三方托管账户内。确保用户本金随时可取回。</p>
        </div>
        <div class="item item_3">
        	<h4><img src="images/describe_tit3.png" /></h4>
            <p>中泰理财创新的投资模式。不仅提供保本到高收益等不同收益级别的理财产品，还能让用户把握全球热点投资机会。</p>
        </div>
    </div>
    <div class="about_site">
    	<div class="title">
        	<h2><!-- <img src="images/about_tit.png" /> --><font size="5"><b>关于中泰理财</b></font></h2>
        </div>
        <div class="cont">
        	<p>中泰理财专注于为不同风险偏好的用户，设计并提供从零风险保本到高风险极高收益的不同风险等级的理财方法。做每个人的专业财富管家。</p>
        </div>
    </div>
    <div class="cf cf2">
    	<div  class="indleft invest_list ">
        	<div class="total">
            	<span style="width:32%;display: inline-block;">平台发布总额：<span style="font-size: 18px;color: #d24740;"><fmt:formatNumber value="${platform.totalMoney * 0.01}" pattern="#,##0" /> </span></span>
                <span style="width:32%;display: inline-block;">平台融资总额：<span style="font-size: 18px;color: #d24740;"><fmt:formatNumber value="${platform.collectMoney * 0.01}" pattern="#,##0" /></span></span>
                <span style="width:34%;display: inline-block;">平台总收益：<span style="font-size: 18px;color: #d24740;"><fmt:formatNumber value="${platform.totalProfit * 0.01}" pattern="#,##0.##" /> </span></span>
            </div>
           <!-- 模仿草根 <div class="main">
            	<div class="left">
            		<div class="car">
            			<div class="car_ad">
            				<h4>车无忧产品</h4>
            				<b>长期稳健</b>
            				<p>高收益,稳如山</p>
            				<span>持有满<em>60</em>天可转让</span>
            			</div>
            			<div class="car_list">
							<p><a href="#">更多>></a></p>
							<div car_bot>
								<ul>
									<li class="li li1"><i class="xinshou"></i><a href="#">新手专享No.010</a></li>
									<li class="li li2">12 %</li>
									<li class="li li3">121天</li>
									<li class="li li4">1000元</li>
									<li class="li li5"><span>49.3%</span></li>
									<li class="li li6"><a href="#">立即投资</a></li>
									<div class="clea"></div>
								</ul>
								<ul>
									<li class="li li1"><i class="xinshou"></i><a href="#">新手专享No.010</a></li>
									<li class="li li2">12 %</li>
									<li class="li li3">121天</li>
									<li class="li li4">1000元</li>
									<li class="li li5"><span>49.3%</span></li>
									<li class="li li6"><a href="#">立即投资</a></li>
									<div class="clea"></div>
								</ul>
								<ul>
									<li class="li li1"><i class="xinshou"></i><a href="#">新手专享No.010</a></li>
									<li class="li li2">12 %</li>
									<li class="li li3">121天</li>
									<li class="li li4">1000元</li>
									<li class="li li5"><span>49.3%</span></li>
									<li class="li li6"><a href="#">立即投资</a></li>
									<div class="clea"></div>
								</ul>
								<ul>
									<li class="li li1"><i class="xinshou"></i><a href="#">新手专享No.010</a></li>
									<li class="li li2">12 %</li>
									<li class="li li3">121天</li>
									<li class="li li4">1000元</li>
									<li class="li li5"><span>49.3%</span></li>
									<li class="li li6"><a href="#">立即投资</a></li>
									<div class="clea"></div>
								</ul>
								<ul>
									<li class="li li1"><i class="xinshou"></i><a href="#">新手专享No.010</a></li>
									<li class="li li2">12 %</li>
									<li class="li li3">121天</li>
									<li class="li li4">1000元</li>
									<li class="li li5"><span>49.3%</span></li>
									<li class="li li6"><a href="#">立即投资</a></li>
									<div class="clea"></div>
								</ul>
							</div>
						</div>
						<div class="clea"></div>
            		</div>
            		<div class="room"></div>
            	
            	</div>
            	<div class="right">
            		<div class="gonggao"> 
            			<div class="gong_head"><i class="gong_pic"></i>理财项目公告<a href="#">更多</a></div>
            			<ul class="gong_list">
	            			<li>公告1 <span>5-10</span></li>
	            			<li>公告1 <span>5-10</span></li>
	            			<li>公告1 <span>5-10</span></li>
	            			<li>公告1 <span>5-10</span></li>
            			</ul>
            		</div>
            		<div class="touzi">
            			<p></p>
            			<div class="tz_list">
            			
            			</div>
            		</div>
            	
            	</div>
            	<div class="clea"></div>
            </div>-->
            <!-- 一个个的小模块<div class="main2">
            	<div class="left">
	            	<div class="chedai">
	            		<div class="che_top">
	            			<a class="che_pro">车无忧产品</a><a href="javascript:viod(0)" >更多>></a>
	            		</div>
	            		<div class="che_bot">
	            			<img src="images/product.png"/>
	            			<img src="images/product.png"/>
	            			<img src="images/product.png"/>
	            		</div>
	            	</div>
	            	<div class="chedai" style="border-left:7px solid #4D9AF1">
	            		<div class="che_top">
	            			<a class="che_pro" style="color:#4D9AF1">房盈宝产品</a><a href="javascript:viod(0)" >更多>></a>
	            		</div>
	            		<div class="che_bot">
	            			<img src="images/product.png"/>
	            			<img src="images/product.png"/>
	            			<img src="images/product.png"/>
	            		</div>
	            	</div>
	            </div>
            	<div class="right">
            		<div class="gonggao">
            			<img src="images/gonggao.png" style="width:238px"/>
            		</div>
            		<div class="liebiao">
            			<img src="images/liebiao.png" style="width:238px"/>
            		</div>
            	
            	</div>
            	<div class="clea"></div>
            </div>-->
           <ul id="parentUL">
            <c:forEach items="${productList}" var="product">
            <c:if test="${product.productType eq 0}">
            <%@ include file="productItem01.jsp"%> 
            </c:if>
             <c:if test="${product.productType eq 1}">
            <%@ include file="productItem02.jsp"%>
             </c:if>
           </c:forEach>
            </ul>
            <c:choose>
            <c:when test="${productList eq '[]' || productList eq ''}">
            	<div style="text-align: center;margin-top: 15px;">
            	<img src="images/no_record.png" />
            	</div>
            </c:when>
            <c:otherwise>
             <div id="loadMore" class="morebtn" style="display: block;cursor: pointer;" ><a>查看更多</a></div>
            </c:otherwise>
           </c:choose>
           
       </div>
       <div class="invest_right">
       		<div class="gonggao" style="display: none;"> 
      			<div class="gong_head">公告<a href="#">更多</a></div>
      			<ul class="gong_list">
       				<li>公告1<a>5-10</a></li>
       				<li>公告1<a>5-10</a></li>
       				<li>公告1<a>5-10</a></li>
       				<li>公告1<a>5-10</a></li>
       				<li>公告1<a>5-10</a></li>
       				<li>公告1<a>5-10</a></li>
       				<li>公告1<a>5-10</a></li>
       				<li>公告1<a>5-10</a></li>
      			</ul>
            </div>
            <c:if test="${investorsList ne '[]' && investorsList ne ''}">
            <div class="touzi"> 
      			<div class="touz_head">他们正在理财</div>
      			<div class="touz_list">
	      			<ul id="scroll">
						<c:forEach items="${investorsList}" var="list">
	       				<li>
	       					<i class="touz_che"></i>
	       					<div>
	       						<p>投资：<span><td><fmt:formatNumber value="${list.copies}" pattern="#,##0.##"/></td>元</span></p>
	       						<p>用户：${list.username}<a>年化收益：<span><fmt:formatNumber value="${list.product.annualEarnings}" pattern="0.#"/>%</span></a></p>
	       					</div>
						</li>
						</c:forEach>
	      			</ul>
	      		</div>	
            </div>
            </c:if>
       </div>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
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
				//alert(settings.timer);
			};
			var autoStop = function(){
	            window.clearInterval(timerID);
	        };
	        autoPlay();
			_btnUp.css("cursor","pointer").click( scrollUp );
			_btnDown.css("cursor","pointer").click( scrollDown );
			
		};
	})(jQuery);
	$(window).scroll(function(){
		var ss = $(window).scrollTop();//滚动条高度
		var sh = $(document).height();
		var ss2 = ss - 645;
		
			if(ss>600){ //滚动条大于600时公告进行下移
				if(ss<=(sh-163-693)){
					$(".invest_right").css("top",ss2+"px");
				}
			}
			else{	
				$(".invest_right").css("top","-18px");
			}
				
	})
 var lun;
var i_ban = 0;
var len = 0;
lun = setInterval("ee()",5000);
$(function(){
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
		lun = setInterval("ee()",5000);
	});
	
	$(".lun").bind("mouseover",function(){
		clearInterval(lun);
	}).bind("mouseleave",function(){
		lun = setInterval("ee()",5000);
	})
	
	ee();
	
});

function ee(){
	$(".lun li.aa").removeClass("aa").stop().animate({"opacity":"0","z-index":"0"},500,function(){jQuery(this).hide()});
	$(".lun .btn li").removeClass("on").eq(i_ban).addClass("on");
	$(".lun li").eq(i_ban).addClass("aa").show().stop().animate({"opacity":"1","z-index":"1"},500);
	i_ban++;
	if(i_ban>=len){
		i_ban=0;
	}
	//alert(i);
}

$(document).ready(function(){
	var currentPage = 2;
	var pageCount = "${pageCount}";
	$("#loadMore").click(function(){
		//alert(3);
		function chageDiv(){
			$("#loadMore").text("已加载完全部");
			$("#loadMore").css("cursor","auto");
			$("#loadMore").css("color","gray");
		}
		//alert(currentPage);
		if(currentPage>parseInt(pageCount)){
			//alert("当前页大于总页数");
			chageDiv();
			return false;
		}
		var url = "${pageContext.request.contextPath}/Product/index!loadProductByAjax.action?"; 
		/* $.post(url, function(data){
			   alert("Data Loaded: " + data);
		 }); */
		var su = "";
		$.ajax({
			type : "post",
			url : url,
			async: true,
			data:"currentPage="+currentPage,
			beforeSend : function(XMLHttpRequest){
				$("#loadMore").html("<a style='margin-top: -11px'><img  src='../Product/images/progress.gif'></a>");
			},
			success : function(data, textStatus) {
				su = data;
				//alert("数据: "+data);
				//alert(su);
			},
			complete : function(XMLHttpRequest, textStatus) {
				$("#loadMore").html("<a>查看更多</a>");
				//alert("完成");
				//alert(textStatus);
				if ("ok" == su.status) {
					//解析json,进行拼接;
					//alert(su);
					//alert(su.json);
					currentPage++;
					appendProduct(su.json);
				} else if ("ok_none" == su.status) {
					//已经加载完数据了;
					//alert(su.json);
					appendProduct(su.json);
					currentPage = (pageCount+1);
					chageDiv();
				} else if ("not" == su.status) {
					//没有数据返回;
					alert(su.json);
					chageDiv();
					currentPage =  (pageCount+1);
				} else if ("err" == su.status) {
					alert(su.json);
				} else{
					alert(su.json);
				}
			}
		});
	});
	//alert(fmoney(123456.56, 0));
});//JQuery的结束符号;


function appendProduct(list){
	for ( var i = 0; i < list.length; i++) {
		var product = list[i];
		var status=product.productStatus;
		var dqjd="";
		var jdbf="";
		if(status=="0"){
			dqjd=parseFloat(product.wcjd);
			jdbf=parseFloat(product.wcjd * 100);
		}else
		if(status=="1"){
			dqjd=parseFloat(product.wcjd);
			jdbf=parseFloat(product.wcjd * 100);
		}else{
			dqjd=1;
			jdbf=100;
		}
		var myEndTime = product.finishTime;
		if(myEndTime==null || myEndTime==""){
			myEndTime="暂无显示";
		}else{
			myEndTime = format(product.finishTime.time);
		}
		
		 var listTr="";
			 listTr = "<li><div class='item'><div class='title'><h4><a href='${pageContext.request.contextPath}/Product/index!showProductDetails.action?";
		 if(product.productType == 0){
			if(product.productStatus == 1){
				//如果是已售罄,则灰色
		 		listTr = "<li><div class='item' style='background-color: #D2D2D2;'><div class='title'><h4><a href='${pageContext.request.contextPath}/Product/index!showProductDetails.action?";
			}
			listTr += "productId="+product.id+"' target='_blank'>"+product.title+"</a>";
			if(product.isRecommend==1){
				listTr +="<img src='${pageContext.request.contextPath}/Product/images/recommend.png'/>";
			}
			listTr +="</h4></div>";
		 }else if(product.productType == 1){
			 if(product.productStatus == 0){
			 	listTr = "<li><div class='item' style='background-color: #FFFAC5;'><div class='title'><h4><a style='color: #FF0000;' href='${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?";
			 }
		 	if(product.productStatus == 1){
				//如果是已售罄,则灰色
		 		listTr = "<li><div class='item' style='background-color: #D2D2D2;'><div class='title'><h4><a style='color: #FF0000;' href='${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?";
			}
			listTr += "productId="+product.id+"' target='_blank'>"+product.title+"</a></h4></div>";
		 }
			listTr +="<div class='data'> <ul class='cf'>";
			//listTr +="<li><h4>投资期限</h4><p><span class='color_d24740'>"+product.tzqx+"</span>天</p></li>";
			if(product.productStatus == 1){
				listTr +="<li><h4>投资期限</h4><p><span class='color_d24740' style='font-size: 24px;'>"+product.cpzt+"</p></li>";
			}else if(product.productStatus == 2){
				listTr +="<li><h4>投资期限</h4><p><span class='color_d24740' style='font-size: 24px;'>"+product.cpzt+"</p></li>";
			}else if(product.productStatus == 3){
				listTr +="<li><h4>投资期限</h4><p><span class='color_d24740' style='font-size: 24px;'>"+product.cpzt+"</p></li>";
			}else{
				if(product.productType == 1){
					listTr +="<li><h4>投资期限</h4><p><span class='color_d24740' style='font-size: 24px;'>"+product.lcqx+"</span>天</p></li>"
				}else{
					listTr +="<li><h4>投资期限</h4><p><span class='color_d24740' style='font-size: 24px;'>"+product.tzqx+"</span>天</p></li>"
				}
			}	
			listTr +="<li> <h4>项目总额</h4><p><span>"+(fmoney(parseFloat(product.financingAmount * 0.01)))+"</span></p></li>";
			listTr +="<li> <h4>年化收益</h4><p><span class='color_d24740'>"+product.annualEarnings+"</span>%</p></li>";
			listTr +="<li> <h4>起投金额</h4><p><span>"+(fmoney(parseFloat(product.atleastMoney * 0.01)))+"</span></p></li>";
			if(product.productType == 1){
				listTr +="<li> <img src='${pageContext.request.contextPath}/Product/images/gold.png' /></li>";
			}
			listTr +="</ul></div><div class='bot'>";
			if(product.productStatus == 0){
				if(product.productType == 0){
					listTr += "<a href='${pageContext.request.contextPath}/Product/index!showProductDetails.action?";
					listTr += "productId="+product.id+"' class='btn' target='_blank'>立即投资</a>" ; 
				}else if(product.productType == 1){
					listTr += "<a href='${pageContext.request.contextPath}/Product/index!showProductDetailsFreshman.action?";
					listTr += "productId="+product.id+"' class='btn' target='_blank'>立即投资</a>" ; 
				}
			}
			
			 if(product.productType == 0){
				listTr +="<ul><li class='li_3'> <label>项目到期：</label>";
				listTr +="<p>"+myEndTime+"</p></li>";
				listTr +="<li class='li_4'> <label>当前进度：</label><p class='pross'><span progress='"+(dqjd)+"'></span></p>";
				listTr +="<b>"+(jdbf)+"%</b></li>  </ul>  ";
			 }else if(product.productType == 1){
			 	/* listTr +="<ul><li class='li_3'> <label>项目到期：</label>";
				listTr +="<p>"+myEndTime+"</p></li>";
				listTr +="<li class='li_4'> <label>当前进度：</label><p class='pross'><span progress='"+(dqjd)+"'></span></p>";
				listTr +="<b>"+(jdbf)+"%</b></li></ul>"; */
			 }
			
			if(product.productStatus == 1){
				listTr +="</div><div class='invest_z' style='background:url(${pageContext.request.contextPath}/Product/images/yishouqing.png);'>";
				listTr +="<img class='maiwang' src='images/shouq.png' />";
				listTr +="</div>";
			
			}else if(product.productStatus == 2){
				listTr +="</div><div class='invest_z' style='background:url(${pageContext.request.contextPath}/Product/images/jiesuanzhong.png);'></div>";
			}else if(product.productStatus == 3){
				listTr +="</div><div class='invest_z' style='background:url(${pageContext.request.contextPath}/Product/images/yihuankuan.png);'></div>";
			}else{
			listTr +="</div><div class='invest_z'></div></div></li>";
			
			}
			
	 
			
		$("#parentUL").append(listTr);
	}
	//初始化进度条
	initProgress();	
}
function initProgress(){
	$("p.pross span").each(function(){
		//alert(1);
		$(this).animate({width:parseInt($(this).parent().width()*$(this).attr("progress"))+"px"},1000);
	});
}

initProgress();
</script>
<a id="_pinganTrust" target="_blank" href="http://c.trustutn.org/s/baiyimao.com" style="display:none;"></a><script type="text/javascript" src="http://c.trustutn.org/show?type=1&sn=201506101004050755"></script>
</html>