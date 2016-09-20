

	$(function(){
					$(".list_bottom").eq(3).css("display","block");
					$(".list_t > li").click(function(){
						$(this).parent().find(".change2").removeClass("change2")
						$(this).attr("class","change2");
						$(".list_bottom").css("display","none");
						$(".list_bottom").eq($(this).index()).css("display","block");
					});
					
					if($(".list_tu:eq(0) img").size()==0){
						$(".list_tu:eq(0)").html('<p style="font-size:20px;text-align:center">暂无证书</p>');
					}
					if($(".list_tu:eq(1) img").size()==0){
						$(".list_tu:eq(1)").html('<p style="font-size:20px;text-align:center">暂无证书</p>');
					}
					
			/*注释掉的是法律意见书里面的缩略图片点击切换效果*/		
			/*	
					big_p = $("#big_photo");
					small_p = $("#small_photo > li");
					lef = $("#left");
					righ = $("#right");
					i2 = 0;		  //判断在哪里个li
					l = 0;   
					lef.css("display","none");	

					//法律意见书
					big_p2 = $("#big_photo_2");
					small_p2 = $("#small_photo_2 > li");
					lef2 = $("#left_2");
					right2 = $("#right_2"); 
					lef2.css("display","none");
					i22 = 0;		  //判断在哪里个li
					l2 = 0;        //负值往左滚
					li_num = $("#big_photo li").length - 1;
					li_num2 = $("#big_photo_2 li").length - 1;
					if(li_num<1)
						{
							lef.css("display","none");
							righ.css("display","none");
							if(li_num<0){
								$(".list:eq(1)").html('<div class="list_center"><p style="font-size:20px;text-align:center">暂无证书</p>');								
							}
						}
						if(li_num2<1)
						{
							lef2.css("display","none");
							right2.css("display","none");
							if(li_num2<0){
								$(".list:eq(2)").html('<div class="list_center"><p style="font-size:20px;text-align:center">暂无证书</p>');		
							}
						}
					//缩略图点击
					$("#small_photo > li").click(function(){
						var num = $(this).index();					
						var on2 = $(".on_t");
						on2.attr("class"," ");
						small_p.eq(num).find(" > img").attr("class","on_t");
						l = num * -955;
						i2 = num;
						big_p.css("left",l+"px");
						if(num>0){
							lef.css("display","block");
						}
						else{
							lef.css("display","none");
						}
						if(num<li_num){
							righ.css("display","block");
						}
						else{
							righ.css("display","none");
						}
								
					});
					
					$("#small_photo_2 > li").click(function(){
						var num = $(this).index();					
						var on2 = $(".on_t2");
						on2.attr("class"," ");
						small_p2.eq(num).find(" > img").attr("class","on_t2");
						l2 = num * -955;
						i22 = num;
						big_p2.css("left",l+"px");
						if(num>0){
							lef2.css("display","block");
						}
						else{
							lef2.css("display","none");
						}
						if(num<li_num2){
							right2.css("display","block");
						}
						else{
							right2.css("display","none");
						}
							
					});*/	
					
			});
	/*注释掉的是法律意见书里面的箭头点击切换效果*/	
	
/*	var left;
    var left2;
	var time;
	var zuo   //获取左右对象
	window.onload = function(){		
			 left = document.getElementById("left");
			 left2 = document.getElementById("left_2");		 
	}
			function move(obj){
						if(obj===left){
							if(l>0){
							}
							else{
								l+=955;
								i2--;
							}
							if(i2==0){
								lef.css("display","none");
							}
							if(i2<li_num){
								righ.css("display","block");
							}
						}
						else{
							if(l<=(-955*li_num)){
							}
							else{
								l-=955;
								i2++;
							}
							if(i2==li_num){
								
								righ.css("display","none");
							}
							if(i2>0){
								lef.css("display","block");
							}
							
						}
						zuo = obj;
						var on2 = $(".on_t");
						on2.attr("class"," ");
						small_p.eq(i2).find(" > img").attr("class","on_t");
						big_p.css("left",l+"px");
					}
						
			
			function move2(obj){
				if(obj===left2){
					if(l2>0){
					}
					else{
						l2+=955;
						i22--;
					}
					if(i22==0){
						lef2.css("display","none");
					}
					if(i22<li_num2){
							right2.css("display","block");
						}
				}
				else{
						if(l2<=(-955*li_num2)){
						}
						else{
							l2-=955;
							i22++;
						}
						if(i22==li_num2){
							right2.css("display","none");
						}
						if(i22>0){
							lef2.css("display","block");
						}
					
				}
				
				big_p2.css("left",l2+"px");;	
				var on3 = $(".on_t2");
				on3.attr("class"," ");
				small_p2.eq(i22).find(" > img").attr("class","on_t2");
			}*/
	