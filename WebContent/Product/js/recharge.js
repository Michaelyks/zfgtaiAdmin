// JavaScript Document
$(document).ready(function(){
	$('.recharge_box').slide({
		titCell:'.way label',
		mainCell:'.waycnt',
		trigger:'click',
		startFun:function(i,c,s,t){
			var val = t.eq(i).attr('value');
			$('#rechargePayWay').val(val);
		}
	})
	$('.recharge_box .bank a.choice').click(function(e){
		e.preventDefault();
		var itembox = $(this).closest('ul').next();
		itembox.slideToggle();
		itembox.find('label').click(function(){
			itembox.hide();
		});
	});
	$('.transfer_box .alipay_transfer p span').click(function(){
		$('.pop_example').show(50,function(){
			$(this).find('a').click(function(){
				$(this).parent().hide();
			});
		})
	});
});