

	$(function(){
		
		$("#touzi").click(function(){
			$(".ping").css("display","block");
			
		})
		
		$(".head_ul li").click(function(){
			$(".change").attr("class"," ");
			$(this).attr("class","change");
			if($(this).index()){
				$(".info").css("display","none");
				$(".tab").css("display","block");
			
			}
			else{
				$(".tab").css("display","none");
				$(".info").css("display","block");
			}
		
		
		});
		
		$(".header img").click(function(){
			$(".ping").css("display","none");	
		});
		
		
	
	
	
	
	
	
	});


















