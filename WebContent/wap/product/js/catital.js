var base="";
var usersId="";
$(function(){ 
	$("body").height($(window).height());
	$("#loadmore").click(function(){loadMore(); })
	function loadMore(){ 
		loadshow();
		loadJson();
	}
	function loadshow(){ 
		$("#loadmore").hide();
		$(".onload-img").show();
	}
	function loadhide(){ 
		$("#loadmore").html('点击加载更多...');
		$("#loadmore").show();
		$(".onload-img").hide();
	}
	function loadError(){ 
		$(".onload-img").hide();
		$("#loadmore").html('数据加载错误，点击重新加载');
		$("#loadmore").show();
	}
	function loadnull(){ 
		$(".onload-img").hide();
		$("#loadmore").html('没有了……');
		$("#loadmore").show();
	}
 
 

 
  var currentPage = 2;
  /*异步加载更多数据*/
  var i = 0;
 function loadJson(){
		$.ajax({
			type:'POST',
			url:base+'/Product/webView!loadFundRecordMore.action?usersId='+usersId+'&currentPage='+currentPage,
			success:function(data){
				//alert(data);
				if(data.status == 'noData'){ 
					//没有数据;
					loadnull();
				}else if(data.status == 'error'){ 
					//没有数据;
					alert(data.json);
				}else if(data.status == 'ok'){ 
					currentPage +=1;
					var data_json = data.json;
					for(var i=0;i<data_json.length;i++){ 
							var str='';
							if(data_json[i].jylx=='投资'){
					  			str+='<li class="bill-tz">';
					  		}else if(data_json[i].jylx=='提现'){
					  			str+='<li class="bill-tx">';
					  		}else if(data_json[i].jylx=='返款'){
					  			str+='<li class="bill-fk">';
					  		}else if(data_json[i].jylx=='充值'){
					  			str+='<li class="bill-cz">';
					  		}else if(data_json[i].jylx=='收益'){
					  			str+='<li class="bill-sy">';
					  		}else { 
					  			str+='<li class="bill-qt">';
					  		} 
//					  		str+='<span class="time-day">'+data_json[i].insertTime.month+'月<br/>'+data_json[i].insertTime.day+'日</span>';
							str+='<span class="time-day">'+(data_json[i].insertTime.month+1)+'月<br/>'+data_json[i].insertTime.date+'日</span>';
					  		str+='<div class="bill-detail"><p class="time-trading">交易时间：'+new Date(data_json[i].insertTime.time).getFullYear()+'/'+(data_json[i].insertTime.month+1)+'/'+data_json[i].insertTime.date+'&nbsp;&nbsp;'+data_json[i].insertTime.hours+':'+data_json[i].insertTime.minutes+':'+data_json[i].insertTime.seconds+'</p>';
					  		str+='<div class="trading"><span class="bill-in"></span><div class="trading-box"><p class="line">交易类型：'+data_json[i].jylx+'</p>';
					  		str+='<p class="line">交易本金：<span class="color">'+(data_json[i].money*0.01).toFixed(2)+'</span></p>';
					  		if(data_json[i].jylx=='投资'){
					  			str+='<p class="line">投 资 券 ：<span class="color">'+(data_json[i].couponValue).toFixed(2)+'</span></p>';
					  		}
					  		str+='<p class="line">账户余额：'+(data_json[i].usersCost*0.01).toFixed(2)+'</p>';
					  		str+='<p class="line">交易说明：'+data_json[i].note+'</p></div>';
							str+='</div></div></li>';
							$("#ul_list").append(str);
					  	}
					loadhide();
					 		
				}
			},error:function(){ 
				loadError();
			}
		
	})
}
})
 
 
  