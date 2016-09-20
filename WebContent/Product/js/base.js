// JavaScript Document
$(document).ready(function(){
	//提示
	$('.account_asset label i').on({
		"mouseenter":function(){
			$(this).addClass('hover');
			var tx = $(this).position().top,ty = $(this).position().left,tips = $(this).parents('li').find('div.tips');
			var tips_w = tips.outerWidth(true);
			if(tips_w > 300){
				tips_w = 300
			}
			tips.width(tips_w-22).css({'left':ty,'top':tx+35,'margin-left':-(tips_w/2)+13}).show();
			//alert(tx+'：'+ty);
		},
		"mouseleave":function(){
			$(this).removeClass('hover');
			$(this).parents('li').find('div.tips').hide().removeAttr('style');
		}
	});
	//首页项目提示
	$('.invest_list .bot li i').on({
		"mouseenter":function(){
			$(this).addClass('hover');
			var tx = $(this).position().top,ty = $(this).position().left,tips = $(this).parents('li').find('div.tips');
			var tips_w = tips.outerWidth(true);
			if(tips_w > 300){
				tips_w = 300
			}
			tips.width(tips_w-22).css({'left':ty,'top':tx+32,'margin-left':-(tips_w/2)+13}).show();
			//alert(tx+'：'+ty);
		},
		"mouseleave":function(){
			$(this).removeClass('hover');
			$(this).parents('li').find('div.tips').hide().removeAttr('style');
		}
	});
	
	$('.invest_list .bot .pross p em').animate({"width":"60%"},1000);
	
	$('.search_list .pross p em').animate({"width":"60%"},1000);
	//切换
	if($('.conjuncture').length != 0){
		$('.conjuncture').slide({mainCell:".tabcnt",titCell:".tabnav span",trigger:"click"});
	}
	
	//search页面 筛选
	var isMulti = true;
	$('.filter_cate h4 a').click(function(e){
		e.preventDefault();
		$('.filter_cate dd a.all').addClass('on').siblings().removeClass('on');
		if($(this).text() === '多选'){
			$(this).text("单选");
			isMulti = false;
		}else{
			$(this).text("多选");
			isMulti = true;
		}
	});
	/*$('.filter_cate dd a').click(function(e){
		e.preventDefault();
		if(isMulti){
			if(!$(this).hasClass('all')){
				$(this).addClass('on').siblings('.all').removeClass('on');
			}else{
				$(this).addClass('on').siblings().removeClass('on');
			}
		}else{
			$(this).addClass('on').siblings().removeClass('on');
		}
	});*/
	//搜索结果提示
	$('.search_list .info .cnt li i').on({
		"mouseenter":function(){
			$(this).addClass('hover');
			var tx = $(this).position().top,ty = $(this).position().left,tips = $(this).parents('li').find('div.tips');
			var tips_w = tips.outerWidth(true);
			if(tips_w > 300){
				tips_w = 300
			}
			tips.width(tips_w-22).css({'left':ty,'top':tx+42,'margin-left':-(tips_w/2)+13}).show();
			//alert(tx+'：'+ty);
		},
		"mouseleave":function(){
			$(this).removeClass('hover');
			$(this).parents('li').find('div.tips').hide().removeAttr('style');
		}
	});
	$('.search_list .price a').click(function(e){
		e.preventDefault();
		$(this).addClass('on').siblings().removeClass('on');
	})
	//添加
	
	$('.search_list .hand .num a.minus').click(function(e){
		e.preventDefault();
		var value = parseInt($(this).parent().find('input').val());
		if(value > 0){
			value = value - 10;
		}
		$(this).parent().find('input').val(value);
	});
	$('.search_list .hand .num a.plus').click(function(e){
		e.preventDefault();
		var value = parseInt($(this).parent().find('input').val());
		if(value < 10000){
			value = value + 10;
		}
		$(this).parent().find('input').val(value);
	});
	function pageCenter(o){
		var num = $(o).width();
		//分页页码长度
		$(o).css('padding-left',(960-num)/2)
	}
	pageCenter('.search_list .page ul');
	pageCenter('.search2_list .page ul');
	//search2页面
	$('.search2_list .bot li i').on({
		"mouseenter":function(){
			$(this).addClass('hover');
			var tx = $(this).position().top,ty = $(this).position().left,tips = $(this).parents('li').find('div.tips');
			var tips_w = tips.outerWidth(true);
			if(tips_w > 300){
				tips_w = 300
			}
			tips.width(tips_w-22).css({'left':ty,'top':tx+32,'margin-left':-(tips_w/2)+13}).show();
			//alert(tx+'：'+ty);
		},
		"mouseleave":function(){
			$(this).removeClass('hover');
			$(this).parents('li').find('div.tips').hide().removeAttr('style');
		}
	});
	//search2页面
	$('.search2_list .data li i').on({
		"mouseenter":function(){
			$(this).addClass('hover');
			var tx = $(this).position().top,ty = $(this).position().left,tips = $(this).parents('li').find('div.tips');
			var tips_w = tips.outerWidth(true);
			if(tips_w > 300){
				tips_w = 300
			}
			tips.width(tips_w-22).css({'left':ty,'top':tx+30,'margin-left':-(tips_w/2)+9}).show();
			//alert(tx+'：'+ty);
		},
		"mouseleave":function(){
			$(this).removeClass('hover');
			$(this).parents('li').find('div.tips').hide().removeAttr('style');
		}
	});
	//保本收益入口
	$('.breakeven .from .num input').val(0)
	$('.breakeven .from .num a.minus').click(function(e){
		e.preventDefault();
		var value = parseInt($(this).parent().find('input').val());
		if(value > 0){
			value = value - 10;
		}
		$(this).parent().find('input').val(value);
	});
	$('.breakeven .from .num a.plus').click(function(e){
		e.preventDefault();
		var value = parseInt($(this).parent().find('input').val());
		if(value < 10000){
			value = value + 10;
		}
		$(this).parent().find('input').val(value);
	});
	
});
//加载图片
function imgLoad(obj,e){
	$(obj).each(function(){
		var o = $(this);
		var u = o.find('img').data('image');
		var img = new Image();
		var w = 645;
		var h = 390;
		img.src=u;
		function imgSize(q,p){
			q.attr({'src':u});
			if(p.width*h>=p.height*w&&p.width>w){
				var newHeight=p.height*(w/p.width);
				var newMarginTop=(h-newHeight)/2;
				q.css({'width':w+'px','height':newHeight+'px','marginTop':newMarginTop+'px'});
			}else if(p.width*h<p.height*w&&p.height>h){
				q.css({'width':p.width*(h/p.height)+'px','height':h+'px'});
			}else{
				q.css({'width':p.width+'px','height':p.height+'px','marginTop':(h-p.height)/2+'px'});
			}
			if(e=='fadeIn'){
				q.fadeIn('slow');
			}else{
				q.show();
			}
		}
		if(img.complete){
			imgSize(o.find('img'),img);
		}else{
			img.onload=function(){
				imgSize(o.find('img'),img);
			}
		}
	})
}