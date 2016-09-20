// JavaScript Document
$(document).ready(function(){
	$('.topic_from').slide({mainCell:".tabcnt",titCell:".tabnav span",trigger:"click"});
	//点击接收验证码
	var time = 60, isEnabled = true;
	$('.topic_from .verify p.code a').click(function(e){
		e.preventDefault();
		if(isEnabled){
			timer(this);
		}
	});
	function timer(o){
		var t = setInterval(function(){
			if(time > 1){
				time--;
				isEnabled = false;
				$(o).html(time+'秒后重新接收');
			}else{
				$(o).html('接收验证码');
				isEnabled = true;
				time = 60;
				clearInterval(t);
			}
		},1000);
	}
});