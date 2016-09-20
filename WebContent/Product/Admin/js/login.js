
	$(function(){					
					$("#username").bind("input propertychange",function(){e($(this));}).blur(function(){
						if($(this).val() == ""){
							$(this).siblings("span").html("请输入登录账号");
						}						
					});
					
					$("#password").bind("input propertychange",function(){e($(this));}).blur(function(){
						if($(this).val() == ""){
							$(this).siblings("span").html("请输入登录密码");
						}						
					});
					function e(obj){						
						obj.siblings("span").html("");
					}
					
					//兼容ie写的方法，原因是透明之后点击文字焦点没落在input上
					$(".user li span").click(function(){
						$(this).siblings("input").focus();
					})
				})