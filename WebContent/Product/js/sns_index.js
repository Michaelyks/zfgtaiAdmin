// JavaScript Document
$(document).ready(function(){
	$('.sns_nav ul li').hover(function(){
		$(this).find('.box').show();
	},function(){
		$(this).find('.box').hide();
	});
	//编辑器配置
	var um = UM.getEditor('mp_address',{
		autoHeightEnabled:false
	});
	$('.sns_list_box .list').slide({mainCell:'.table',titCell:'.head span',trigger:'click',titOnClassName:'cur'});
});