
	$(function(){  		
    				$(".add_bangk2").click(function(){
    					$(".chongz").slideToggle("show");
    				});
    				
    				$(".next").click(function(){
    					$(".chongz").hide();
    					
    				});  				  		
    				
    				$("input").click(function(){
    					
    					if($(this).val()=="请输入姓名"||$(this).val()=="请输入身份证号"||$(this).val()=="请输入卡号"){
    						$(this).val("");
    					}
    				})
    				$(".chongz input:eq(0)").blur(function(){
    					if($(this).val()==""){
    						$(".name:eq(0)").css("display","block");
    						$(".name:eq(0)").html("请填写真实姓名");
    					}
    					else{
    						$(".name:eq(0)").css("display","none");
    						$(".name:eq(0)").html("");
    					}
    					
    				});
    				
    				$(".chongz input:eq(1)").blur(function(){
    					if($(this).val()==""){
    						$(".name:eq(1)").css("display","block");
    						$(".name:eq(1)").html("请输入身份证号");
    					}
    					else{
    						var ver = /^\d{17}(\d|X)$/;
    						if(ver.test($(this).val())){
    							$(".name:eq(1)").css("display","none");
        						$(".name:eq(1)").html("");
    						}
    						else{
    							$(".name:eq(1)").css("display","block");
        						$(".name:eq(1)").html("号码为18位数字或末尾字母X");
    						}
    						
    						
    					}
    					
    				});
    				
    				$(".chongz input:eq(2)").blur(function(){
    					if($(this).val()==""){
    						$(".name:eq(2)").css("display","block");
    						$(".name:eq(2)").html("请输入卡号");
    					}
    					else{
    						$(".name:eq(2)").css("display","none");
    						$(".name:eq(2)").html("");
    					}
    					
    				});
    				
    				$(".bang_list").mouseover(function(){
    					$(".b_list").css("display","block");
    				}).mouseout(function(){
    					$(".b_list").css("display","none");
    				})
    				
    			})

















