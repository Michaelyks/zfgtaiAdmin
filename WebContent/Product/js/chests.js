// JavaScript Document
$(document).ready(function(){
	$('.ui_banner .img li').each(function(){
		var imgurl = $(this).find('img').attr('src');
		$(this).css('background','url('+imgurl+') no-repeat center top');
		$(this).find('img').remove();
	});
	$('.ui_banner').slide({mainCell:'.img ul',titCell:'.intro li',effect:'leftLoop',autoPlay:true});
	$('.chests_announce').slide({mainCell:'ul',effect:'topLoop',autoPlay:true});
});