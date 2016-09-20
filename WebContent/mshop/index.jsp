<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<title>喵商城商品列表</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/mshop/css/style.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/mshop/css/swiper.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/mshop/css/animate.css"/>
</head>
<body>
<div class="shopbox" id="shopbox">
<div class="swiper-container" id="swiperbox">
    <div class="swiper-wrapper">
    	<!--------slide 1---------->
		<div class="swiper-slide">
			<div class="swiper-container" id="shop_air">
				<div class="swiper-wrapper ">
					<!--------slide1-1---------->
					<div class="swiper-slide swiper-slide1 box-bg1">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-air-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-air-p2.png"   style="left:10px;top:142px;width:304px; " swiper-animate-effect="zoomIn" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168ee89f41607" data-num='0' data-level="0" /> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-air-p3.png"  class="ani resize swiper-lazy" style="left:90px; top:341px; width:139px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide1-1---------->
					<!--------slide1-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-air-p4.png" class="ani resize swiper-lazy" style="width:237px;left:22px; top:65px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-air-p5.png"  class="ani resize swiper-lazy" style="width:275px;left:22px;top:160px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide1-2---------->
				</div>
				<div id="shop_air_pagination" class="swiper-pagination"></div>
				
			</div>
		</div>
		<!--------slide1---------->
		<!--------slide2---------->
	<div class="swiper-slide">
		<div class="swiper-container" id="shop_watch">
				<div class="swiper-wrapper ">
					<!--------slide2-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-watch-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-watch-p2.png"   style="left:27.75px;top:126px;width:264.5px;" swiper-animate-effect="bounceIn" swiper-animate-duration="1s" swiper-animate-delay="0s"  onclick="paypro(this)" data-id="8ae99e115168c681015168ed748c1606"  data-index='1'  data-level="0"/> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-watch-p3.png"  class="ani resize swiper-lazy" style="left:104.5px; top:380px; width:111px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide2-1---------->
					<!--------slide2-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-watch-p4.png" class="ani resize swiper-lazy" style="width:237px; left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-watch-p5.png"  class="ani resize swiper-lazy" style="width:275px;left:22px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					
					<!--------slide2-2---------->
				</div>
				<div id="shop_watch_pagination" class="swiper-pagination"></div>
			</div>
	</div>
	<!--------slide2---------->
    <!--------slide3---------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_phone6s">
				<div class="swiper-wrapper ">
					<!--------slide3-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-ph6s-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-ph6s-p2.png"   style="left:53.5px;top:123px;width:213px;" swiper-animate-effect="bounceIn" swiper-animate-duration="1s" swiper-animate-delay="0s"  onclick="paypro(this)" data-id="8ae99e115168c681015168f7553c1686"  data-index='2' data-level="0"/> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ph6s-p3.png"  class="ani resize swiper-lazy" style="left:99px; top:415px; width:121px; " swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide3-1---------->
					<!--------slide3-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ph6s-p4.png" class="ani resize swiper-lazy" style="width:195px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ph6s-p5.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide3-2---------->
				</div>
				<div id="shop_phone6s_pagination" class="swiper-pagination"></div>
			</div>
    </div>
    <!--------slide3---------->
    <div class="swiper-slide">
    	<!--------slide4---------->
    	<div class="swiper-container" id="shop_phone6sp">
				<div class="swiper-wrapper ">
					<!--------slide4-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-ph6sp-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-ph6sp-p2.png"   style="left:53.5px;top:145px;width:187.5px;" swiper-animate-effect="bounceIn" swiper-animate-duration="1s" swiper-animate-delay="0s"  onclick="paypro(this)" data-id="8ae99e115168c681015168f5aad81684"  data-index='3'  data-level="0"/> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ph6sp-p3.png"  class="ani resize swiper-lazy" style="left:116px; top:405px; width:87.5px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide4-1---------->
					<!--------slide4-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ph6sp-p4.png" class="ani resize swiper-lazy" style="width:236.5px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ph6sp-p5.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide4-2---------->
				</div>
				<div id="shop_phone6sp_pagination" class="swiper-pagination"></div>
			</div>
    </div>
    <!--------slide4---------->
    
    	<!--------slide5---------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_iPmin">
				<div class="swiper-wrapper ">
					<!--------slide5-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-ipmin4-p1.png"  style="left:0px;top:10 px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-ipmin4-p2.png"   style="left:53.5px;top:132px;width:213px;" swiper-animate-effect="bounceIn" swiper-animate-duration="1s" swiper-animate-delay="0s"  onclick="paypro(this)" data-id="8ae99e115168c681015168f69d461685"  data-index='4'  data-level="0" /> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ipmin4-p3.png"  class="ani resize swiper-lazy" style="left:88px; top:430px; width:144px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide5-1---------->
					<!--------slide5-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ipmin4-p4.png" class="ani resize swiper-lazy" style="width:190px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ipmin4-p5.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide5-2---------->
				</div>
				<div id="shop_iPmin_pagination" class="swiper-pagination"></div>
			</div>
    </div>
    <!--------slide5---------->
    	<!--------slide6---------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_xmphc">
				<div class="swiper-wrapper ">
					<!--------slide6-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-xmphc-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-xmphc-p2.png"   style="left:0px;top:142px;width:320px;" swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="0s"   onclick="paypro(this)" data-id="8ae99e115168c681015168e684ca15bc"  data-index='5'  data-level="0"/> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmphc-p3.png"  class="ani resize swiper-lazy" style="left:98.75px; top:405px; width:122.5px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide6-1---------->
					<!--------slide6-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmphc-p4.png" class="ani resize swiper-lazy" style="width:130px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmphc-p5.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide6-2---------->
				</div>
				<div id="shop_xmphc_pagination" class="swiper-pagination"></div>
			</div>
    </div>
    <!--------slide6---------->
    
    <!--------slide7---------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_xmyddy">
				<div class="swiper-wrapper ">
					<!--------slide7-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-xmyddy-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-xmyddy-p2.png"   style="left:14px;top:160px;width:292px;" swiper-animate-effect="rotateIn" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168efa789163a"  data-index='6'  data-level="0"/> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmyddy-p3.png"  class="ani resize swiper-lazy" style="left:98.75px; top:400px; width:122.5px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide7-1---------->
					<!--------slide7-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmyddy-p4.png" class="ani resize swiper-lazy" style="width:240px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmyddy-p5.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide7-2---------->
				</div>
				<div id="shop_xmyddy_pagination" class="swiper-pagination"></div>
			</div>
    </div>
    <!--------slide7---------->
    
    <!--------slide8---------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_xmyzpg">
				<div class="swiper-wrapper ">
					<!--------slide8-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-xmyzpg-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-xmyzpg-p2.png"   style="left:115px;top:108px;width:89px;" swiper-animate-effect="flipInX" swiper-animate-duration="1s" swiper-animate-delay="0s"  onclick="paypro(this)" data-id="8ae99e115168c681015168f010f2167b"  data-index='7'  data-level="0"/> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmyzpg-p3.png"  class="ani resize swiper-lazy" style="left:125px; top:420px; width:70px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide8-1---------->
					<!--------slide8-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmyzpg-p4.png" class="ani resize swiper-lazy" style="width:109px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmyzpg-p5.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide8-2---------->
				</div>
				<div id="shop_xmyzpg_pagination" class="swiper-pagination"></div>
			</div>
    </div>
    <!--------slide8---------->
   
    <!--------slide9---------->
	<div class="swiper-slide">
		<div class="swiper-container" id="shop_xmphone">
				<div class="swiper-wrapper ">
					<!--------slide9-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-xmphone-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-xmphone-p2.png"   style="left:40.75px;top:120px;width:238.5px;" swiper-animate-effect="rotateInUpRight" swiper-animate-duration="1s" swiper-animate-delay="0s"  onclick="paypro(this)" data-id="8ae99e115168c681015168e779e815fd"  data-index='8'  data-level="0"/> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmphone-p3.png"  class="ani resize swiper-lazy" style="left:87.5px; top:405px; width:145px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0.5s"/>
					</div>
					<!--------slide9-1---------->
					<!--------slide9-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmphone-p4.png" class="ani resize swiper-lazy" style="width:130px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmphone-p5.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide9-2---------->
				</div>
				<div id="shop_xmphone_pagination" class="swiper-pagination"></div>
			</div>
	</div>
	 <!--------slide9---------->
	 <!--------slide10---------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_xmtv">
				<div class="swiper-wrapper ">
					<!--------slide10-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-xmds-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-xmds-p2.png"   style="left:31.25px;top:120px;width:257.5px;" swiper-animate-effect="flipInY" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168e8150f15fe"  data-index='9'  data-level="0"/> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmds-p3.png"  class="ani resize swiper-lazy" style="left:93px; top:400px; width:134px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide10-1---------->
					<!--------slide10-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmds-p4.png" class="ani resize swiper-lazy" style="width:137px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-xmds-p5.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide10-2---------->
				</div>
				<div id="shop_xmtv_pagination" class="swiper-pagination"></div>
			</div>
    </div>
    <!--------slide10---------->
    
    <!--------slide11---------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_tzq">
				<div class="swiper-wrapper ">
					<!--------slide11-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-tzq-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-tzq-p2.png"   style="left:25px;top:110px; height:99px;width:270px;" swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168f35b791681"  data-index='10'  data-level="0"/> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-tzq-p3.png"  class="ani resize swiper-lazy product" style="left:25px;height:99px; top:220px; width:270px;" swiper-animate-effect="bounceInRight" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168f3bcd31682"  data-index='10'  data-level="0"/>
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-tzq-p4.png"  class="ani resize swiper-lazy product" style="left:25px;height:99px; top:330px; width:270px;" swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168f427061683"  data-index='10'  data-level="0"/>
					</div>
					<!--------slide11-1---------->
					<!--------slide11-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-tzq-p5.png" class="ani resize swiper-lazy" style="width:90px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-tzq-p6.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide11-2---------->
					<!--------slide11-3---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-tzq-p7.png" class="ani resize swiper-lazy" style="width:100px;left:22px; top:112px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-tzq-p6.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:197px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide11-3---------->
					<!--------slide11-4---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-tzq-p8.png" class="ani resize swiper-lazy" style="width:100px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-tzq-p6.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide11-4---------->
				</div>
				<div id="shop_tzq_pagination" class="swiper-pagination"></div>
			</div>
    </div>
    <!--------slide11---------->
    
    <!--------slide12---------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_hf">
				<div class="swiper-wrapper ">
					<!--------slide12-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-hfcz-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-hfcz-p2.png"   style="left:16px;top:160px; height:88px;width:288px;" swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168f2882b1680"  data-index='11'  data-level="0"/> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-hfcz-p3.png"  class="ani resize swiper-lazy product" style="left:16px;height:88px; top:260px; width:288px;" swiper-animate-effect="bounceInRight" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168f21f30167f"  data-index='11'  data-level="0"/>
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-hfcz-p4.png"  class="ani resize swiper-lazy product" style="left:16px; top:360px;height:88px; width:288px;" swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168f1b1ff167e"  data-index='11'  data-level="0"/>
					</div>
					<!--------slide12-1---------->
					<!--------slide12-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-hfcz-p5.png" class="ani resize swiper-lazy" style="width:108px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-hfcz-p6.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide12-2---------->
					<!--------slide12-3---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-hfcz-p7.png" class="ani resize swiper-lazy" style="width:108px;left:22px; top:92px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-hfcz-p8.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:177px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide12-3---------->
					<!--------slide12-4---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-hfcz-p9.png" class="ani resize swiper-lazy" style="width:119px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-hfcz-p10.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide12-4---------->
				</div>
				<div id="shop_hf_pagination" class="swiper-pagination"></div>
			</div>
    </div>
    <!--------slide12---------->
    
    <!--------slide13---------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_jde">
				<div class="swiper-wrapper ">
					<!--------slide13-1---------->
					<div class="swiper-slide swiper-slide1 box-bg2">
						<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-jdek-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
						<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-jdek-p2.png"   style="left:51px;top:150px; height:138px;width:218px;" swiper-animate-effect="flipInX" swiper-animate-duration="1s" swiper-animate-delay="0s"  onclick="paypro(this)" data-id="8ae99e115168c681015168f13935167d"  data-index='12'  data-level="0"/> 
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-jdek-p3.png"  class="ani resize swiper-lazy product" style="left:51px;height:138px; top:310px; width:218px;" swiper-animate-effect="flipInY" swiper-animate-duration="1s" swiper-animate-delay="0s"  onclick="paypro(this)" data-id="8ae99e115168c681015168f0dbc3167c"  data-index='12'  data-level="0"/>
					</div>
					<!--------slide13-1---------->
					<!--------slide13-2---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-jdek-p4.png" class="ani resize swiper-lazy" style="width:119px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-jdek-p5.png"  class="ani resize swiper-lazy" style="width:275px;left:22px;top:180px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide13-2---------->
					<!--------slide13-3---------->
					<div class="swiper-slide swiper-slide2">
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-jdek-p6.png" class="ani resize swiper-lazy" style="width:119px;left:22px; top:112px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
						<img data-src="${pageContext.request.contextPath}/mshop/img/shop-jdek-p5.png"  class="ani resize swiper-lazy" style="width:275.5px;left:22.5px;top:197px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
					</div>
					<!--------slide13-3---------->
 				</div>
				<div id="shop_jde_pagination" class="swiper-pagination"></div>
			</div>
    </div>
    <!--------slide13---------->
    
    <!-----------slide14-------------->
    <div class="swiper-slide">
		<div class="swiper-container" id="shop_ghptj">
					<div class="swiper-wrapper ">
						<!--------slide14-1---------->
						<div class="swiper-slide swiper-slide1 box-bg2">
							<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-ghptj-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
							<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-ghptj-p2.png"   style="left:121.5px;top:144px;width:77px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168e9eb651600"  data-index='13'  data-level="0"/> 
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ghptj-p3.png"  class="ani resize swiper-lazy" style="left:81px; top:435px; width:157px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0.5s"/>
						</div>
						<!--------slide14-1---------->
						<!--------slide14-2---------->
						<div class="swiper-slide swiper-slide2">
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ghptj-p4.png" class="ani resize swiper-lazy" style="width:262px;left:22px; top:70px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ghptj-p5.png"  class="ani resize swiper-lazy" style="width:275px;left:22px;top:190px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
						</div>
						<!--------slide14-2---------->
					</div>
					<div id="shop_ghptj_pagination" class="swiper-pagination"></div>
				</div>
    </div>
    <!------------slide14------------------->
    
    <!-----------slide15-------------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_bedgh">
					<div class="swiper-wrapper ">
						<!--------slide15-1---------->
						<div class="swiper-slide swiper-slide1 box-bg2">
							<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-bedgh-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
							<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-bedgh-p2.png"   style="left:129px;top:166px;width:62px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"  onclick="paypro(this)" data-id="8ae99e115168c681015168eb14551602"  data-index='14'  data-level="0"/> 
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-bedgh-p3.png"  class="ani resize swiper-lazy" style="left:81px; top:425px; width:157px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0.5s"/>
						</div>
						<!--------slide15-1---------->
						<!--------slide15-2---------->
						<div class="swiper-slide swiper-slide2">
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-bedgh-p4.png" class="ani resize swiper-lazy" style="width:192px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-bedgh-p5.png"  class="ani resize swiper-lazy" style="width:275px;left:22px;top:175px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
						</div>
						<!--------slide15-2---------->
					</div>
					<div id="shop_bedgh_pagination" class="swiper-pagination"></div>
				</div>
    </div>
    <!-----------slide15-------------->
     <!-----------slide16-------------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_mrygh">
					<div class="swiper-wrapper ">
						<!--------slide16-1---------->
						<div class="swiper-slide swiper-slide1 box-bg2">
							<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-mrygh-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
							<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-mrygh-p2.png"   style="left:128px;top:167.5px;width:64px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"  onclick="paypro(this)" data-id="8ae99e115168c681015168eb801f1603"  data-index='15'  data-level="0"/> 
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-mrygh-p3.png"  class="ani resize swiper-lazy" style="left:81px; top:435px; width:157px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0.5s"/>
						</div>
						<!--------slide16-1---------->
						<!--------slide16-2---------->
						<div class="swiper-slide swiper-slide2">
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-mrygh-p5.png" class="ani resize swiper-lazy" style="width:140px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-mrygh-p6.png"  class="ani resize swiper-lazy" style="width:275px;left:22px;top:175px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
						</div>
						<!--------slide16-2---------->
					</div>
					<div id="shop_mrygh_pagination" class="swiper-pagination"></div>
				</div>
    </div>
    <!-----------slide16-------------->
    
    <!-----------slide17-------------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_tlms">
					<div class="swiper-wrapper ">
						<!--------slide17-1---------->
						<div class="swiper-slide swiper-slide1 box-bg2">
							<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-tlms-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
							<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-tlms-p2.png"   style="left:27.5px;top:200px;width:265px;" swiper-animate-effect="wobble" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168ec940f1605"  data-index='16'  data-level="0"/> 
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-tlms-p3.png"  class="ani resize swiper-lazy" style="left:125px; top:425px; width:70px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0.5s"/>
						</div>
						<!--------slide17-1---------->
						<!--------slide17-2---------->
						<div class="swiper-slide swiper-slide2">
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-tlms-p4.png" class="ani resize swiper-lazy" style="width:177px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-tlms-p5.png"  class="ani resize swiper-lazy" style="width:275px;left:22px;top:175px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
						</div>
						<!--------slide17-2---------->
					</div>
					<div id="shop_tlms_pagination" class="swiper-pagination"></div>
				</div>
    </div>
    <!-----------slide17-------------->
    
    <!-----------slide18-------------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_ajbc">
					<div class="swiper-wrapper ">
						<!--------slide18-1---------->
						<div class="swiper-slide swiper-slide1 box-bg2">
							<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-ajbc-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
							<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-ajbc-p2.png"   style="left:12px;top:212px;width:296px;" swiper-animate-effect="fadeInLeft" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168e8fd9a15ff"  data-index='17'  data-level="0"/> 
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ajbc-p3.png"  class="ani resize swiper-lazy" style="left:99px; top:425px; width:122px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
						</div>
						<!--------slide18-1---------->
						<!--------slide18-2---------->
						<div class="swiper-slide swiper-slide2">
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ajbc-p4.png" class="ani resize swiper-lazy" style="width:122px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-ajbc-p5.png"  class="ani resize swiper-lazy" style="width:275px;left:22px;top:175px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
						</div>
						<!--------slide18-2---------->
					</div>
					<div id="shop_ajbc_pagination" class="swiper-pagination"></div>
				</div>
    </div>
    <!-----------slide18-------------->
    <!-----------slide19-------------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_yzc">
					<div class="swiper-wrapper ">
						<!--------slide19-1---------->
						<div class="swiper-slide swiper-slide1 box-bg2">
							<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-fmyzc-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
							<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-fmyzc-p2.png"   style="left:50px;top:195px;width:220px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168ec2d371604"  data-index='18'  data-level="0"/> 
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-fmyzc-p3.png"  class="ani resize swiper-lazy" style="left:125px; top:445px; width:70px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
						</div>
						<!--------slide19-1---------->
						<!--------slide19-2---------->
						<div class="swiper-slide swiper-slide2">
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-fmyzc-p4.png" class="ani resize swiper-lazy" style="width:170px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-fmyzc-p5.png"  class="ani resize swiper-lazy" style="width:275px;left:22px;top:175px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
						</div>
						<!--------slide19-2---------->
					</div>
					<div id="shop_yzc_pagination" class="swiper-pagination"></div>
				</div>
    </div>
    <!-----------slide19-------------->
    
    <!-----------slide20-------------->
    <div class="swiper-slide">
    	<div class="swiper-container" id="shop_bllh">
					<div class="swiper-wrapper ">
						<!--------slide20-1---------->
						<div class="swiper-slide swiper-slide1 box-bg2">
							<img class="ani resize swiper-lazy" data-src="${pageContext.request.contextPath}/mshop/img/shop-bllh-p1.png"  style="left:0px;top:10px;width:320px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s"/> 
							<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-bllh-p2.png"   style="left:12px;top:141px;width:297px;" swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168ea6f5b1601"  data-index='19'  data-level="0"/> 
							<img class="ani resize swiper-lazy product" data-src="${pageContext.request.contextPath}/mshop/img/shop-bllh-p3.png"   style="left:12px;height:131px;top:286px;width:297px;" swiper-animate-effect="bounceInRight" swiper-animate-duration="1s" swiper-animate-delay="0s" onclick="paypro(this)" data-id="8ae99e115168c681015168ea6f5b1601"  data-index='19'  data-level="0"/> 
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-bllh-p4.png"  class="ani resize swiper-lazy" style="left:125px;height:17px; top:440px; width:69px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
						</div>
						<!--------slide20-1---------->
						<!--------slide20-2---------->
						<div class="swiper-slide swiper-slide2">
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-bllh-p5.png" class="ani resize swiper-lazy" style="width:119px;left:22px; top:90px;" swiper-animate-effect="fadeInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
							<img data-src="${pageContext.request.contextPath}/mshop/img/shop-bllh-p6.png"  class="ani resize swiper-lazy" style="width:275px;left:22px;top:175px;" swiper-animate-effect="fadeInUp" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
						</div>
						<!--------slide20-2---------->
					</div>
					<div id="shop_bllh_pagination" class="swiper-pagination"></div>
				</div>
    </div>
    <!-----------slide20-------------->
	</div>
</div>
	<img id="array" class="resize" style="width:20px;height:15px;left:150px;top:450px;" src="${pageContext.request.contextPath}/mshop/img/down.png"/>
</div>
<div class="help" id="help">
	<div class="black">
	<img class="resize" style="width:159px; top:60px; left:130px;" src="${pageContext.request.contextPath}/mshop/img/rightclick.png"/>
	<img class="resize" style="width:113px; top:181px;  left:103px;" src="${pageContext.request.contextPath}/mshop/img/ctclick.png"/>
	<img class="resize" style="width:153px; top:340px;  left:83.5px;" src="${pageContext.request.contextPath}/mshop/img/downclick.png"/>		
	</div>
</div>
<script src="${pageContext.request.contextPath}/mshop/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/mshop/js/swiper.jquery.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/mshop/js/swiper.animate1.0.2.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
scaleW=window.innerWidth/320;
scaleH=window.innerHeight/480;
var resizes = document.querySelectorAll('.resize');
          for (var j=0; j<resizes.length; j++) {
           resizes[j].style.width=parseInt(resizes[j].style.width)*scaleW+'px';
		   resizes[j].style.height=parseInt(resizes[j].style.height)*scaleH+'px';
		   resizes[j].style.top=parseInt(resizes[j].style.top)*scaleH+'px';
		   resizes[j].style.left=parseInt(resizes[j].style.left)*scaleW+'px'; 
}
</script>
<script src="${pageContext.request.contextPath}/mshop/js/Initswiper.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>

