var mySwiperall = new Swiper('#swiperbox', {
	direction: 'vertical', //方向  vertical上下   ；horizontal左右
	lazyLoading: true,
	lazyLoadingInPrevNext: true,
	preloadImages: false,
	onInit: function(swiper) {
		swiperAnimateCache(swiper);
		swiperAnimate(swiper);
	},
	onSlideChangeEnd: function(swiper) {
		swiperAnimate(swiper);
	},
	onTransitionEnd: function(swiper) {
		swiperAnimate(swiper);
	},
	watchSlidesProgress: true,
	onProgress: function(swiper) {
		for (var i = 0; i < swiper.slides.length; i++) {
			var slide = swiper.slides[i];
			var progress = slide.progress;
			var translate = progress * swiper.height / 4;
			scale = 1 - Math.min(Math.abs(progress * 0.5), 1);
			var opacity = 1 - Math.min(Math.abs(progress / 2), 0.5);
			slide.style.opacity = opacity;
			es = slide.style;
			es.webkitTransform = es.MsTransform = es.msTransform = es.MozTransform = es.OTransform = es.transform = 'translate3d(0,' + translate + 'px,-' + translate + 'px) scaleY(' + scale + ')';

		}
	},
	onSetTransition: function(swiper, speed) {
		for (var i = 0; i < swiper.slides.length; i++) {
			es = swiper.slides[i].style;
			es.webkitTransitionDuration = es.MsTransitionDuration = es.msTransitionDuration = es.MozTransitionDuration = es.OTransitionDuration = es.transitionDuration = speed + 'ms';
		}
	}
});
/*
 *  商品一
 */
var mySwiper_air = new Swiper('#shop_air', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//    pagination: '#shop_air_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true
	})
	/***
	 * 商品二 
	 */
var mySwiper_watch = new Swiper('#shop_watch', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//  pagination: '#shop_watch_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品三 
	 */
var mySwiper_phone6s = new Swiper('#shop_phone6s', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//   pagination: '#shop_phone6s_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品四
	 */
var mySwiper_phone6sp = new Swiper('#shop_phone6sp', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//   pagination: '#shop_phone6sp_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品五
	 */
var mySwiper_iPmin = new Swiper('#shop_iPmin', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//    pagination: '#shop_iPmin_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品六
	 */
var mySwiper_xmphc = new Swiper('#shop_xmphc', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//    pagination: '#shop_xmphc_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品七
	 */
var mySwiper_xmyddy = new Swiper('#shop_xmyddy', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//    pagination: '#shop_xmyddy_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品八
	 */
var mySwiper_xmyddy = new Swiper('#shop_xmyzpg', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//   pagination: '#shop_xmyzpg_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品九
	 */
var mySwiper_xmyddy = new Swiper('#shop_xmphone', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//   pagination: '#shop_xmphone_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品十
	 */
var mySwiper_xmyddy = new Swiper('#shop_xmtv', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//    pagination: '#shop_xmtv_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品十一
	 */
var mySwiper_xmyddy = new Swiper('#shop_tzq', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//  pagination: '#shop_tzq_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品十二
	 */
var mySwiper_xmyddy = new Swiper('#shop_hf', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//    pagination: '#shop_hf_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品十三
	 */
var mySwiper_xmyddy = new Swiper('#shop_jde', {
	direction: 'horizontal', //方向  vertical上下   ；horizontal左右
	//   pagination: '#shop_jde_pagination',
	lazyLoading: true,
	paginationHide: true,
	lazyLoadingInPrevNext: true,
	preloadImages: false
})

/***
 * 商品十四
 */
var mySwiper_xmyddy = new Swiper('#shop_ghptj', {
	direction: 'horizontal', //方向  vertical上下   ；horizontal左右
	//    pagination: '#shop_ghptj_pagination',
	lazyLoading: true,
	paginationHide: true,
	lazyLoadingInPrevNext: true,
	preloadImages: false
})

/***
 * 商品十五
 */
var mySwiper_xmyddy = new Swiper('#shop_bedgh', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//   pagination: '#shop_bedgh_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品十六
	 */
var mySwiper_xmyddy = new Swiper('#shop_mrygh', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//   pagination: '#shop_mrygh_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品十七
	 */
var mySwiper_xmyddy = new Swiper('#shop_tlms', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//   pagination: '#shop_tlms_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品十八
	 */
var mySwiper_xmyddy = new Swiper('#shop_ajbc', {
		direction: 'horizontal', //方向  vertical上下   ；horizontal左右
		//   pagination: '#shop_ajbc_pagination',
		lazyLoading: true,
		paginationHide: true,
		lazyLoadingInPrevNext: true,
		preloadImages: false
	})
	/***
	 * 商品十九
	 */
var mySwiper_xmyddy = new Swiper('#shop_yzc', {
	direction: 'horizontal', //方向  vertical上下   ；horizontal左右
	//   pagination: '#shop_yzc_pagination',
	lazyLoading: true,
	paginationHide: true,
	lazyLoadingInPrevNext: true,
	preloadImages: false
})

/***
 * 商品二十
 */
var mySwiper_xmyddy = new Swiper('#shop_bllh', {
	direction: 'horizontal', //方向  vertical上下   ；horizontal左右
	//   pagination: '#shop_bllh_pagination',
	lazyLoading: true,
	paginationHide: true,
	lazyLoadingInPrevNext: true,
	preloadImages: false
})
var u = navigator.userAgent;
$(function() {
	if (u.indexOf('Andriod') > -1 || u.indexOf('Linux') > -1) { //安卓手机
		andriodInit();
	} else if (u.indexOf('iPhone') > -1) { //苹果手机
		iosInit();
	} else {
		andriodInit();
	}
	$("#help").click(function() {
		$(this).hide();
		$("#shopbox").show();
	})
})

function iosInit() {
	try {
		connectWebViewJavascriptBridge(function(bridge) {
			var productId = "";
			bridge.init(function(message, responseCallback) {
				productId = message;
				responseCallback('get');
				if (productId != '') {
					proindex = '';
					productId = productId.replace(/\s/g, "");
					$(".product").each(function() {
						var proId = $(this).data("id");
						if (proId == productId) {
							proindex = $(this).data("index");
						}
					})
					mySwiperall.slideTo(proindex, 1000, true);
				}
			})
			bridge.registerHandler('testJavascriptHandler', function(data, responseCallback) {
				responseCallback('post');
			})
		})

	} catch (e) {
		console.log(e);
	}
}

function andriodInit() {
	try {
		console.log('andriod');
		var productId = window.android.getContent(); //从安卓客户端获取数据
		if (productId != '') {
			proindex = '';
			$(".product").each(function() {
				var proId = $(this).data("id");
				if (proId == productId) {
					proindex = $(this).data("index");
				}
			})
			mySwiperall.slideTo(proindex, 1000, true);
		}
	} catch (e) {
		console.log(e);
	}
}

function paypro(obj) {
	var proid = $(obj).data("id");
	var level = $(obj).data("level");
	if (u.indexOf('Andriod') > -1 || u.indexOf('Linux') > -1) { //安卓手机
		android.showToast(proid, level); //发送数据给安卓客户端
	} else if (u.indexOf('iPhone') > -1) { //苹果手机
		connectWebViewJavascriptBridge(function(bridge) {
			bridge.registerHandler('testJavascriptHandler', function(data, responseCallback) {
				responseCallback('post');
			})
			bridge.send({
				"productId": proid,
				"level": level
			});
		});
	} else { //安卓手机
		android.showToast(proid, level); //发送数据给安卓客户端
	}
}
function connectWebViewJavascriptBridge(callback) {
	if (window.WebViewJavascriptBridge) {
		callback(WebViewJavascriptBridge)
	} else {
		document.addEventListener('WebViewJavascriptBridgeReady', function() {
			callback(WebViewJavascriptBridge)
		}, false)
	}
}