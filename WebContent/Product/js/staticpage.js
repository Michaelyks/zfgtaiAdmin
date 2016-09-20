// JavaScript Document
function leftRightAlign (){
	//左右对齐
	var hoh = $('.help_menu').outerHeight(true);
	var hch = $('.help_main').outerHeight(true);
	if(hoh > hch){
		$('.help_main').height(hoh-192);
	}
}
$(document).ready(function(){
	if($('.static_main').has('.help_menu').length){
		leftRightAlign()
		//菜单展开
		$('.help_menu dl').click(function(){
			$(this).addClass('on').siblings().removeClass('on');
			$('.help_menu').height('auto');
			$('.help_main').height('auto');
			leftRightAlign();
		});
	}
});