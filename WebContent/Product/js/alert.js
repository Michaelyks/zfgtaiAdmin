//第四版本解决同时弹出问题,添加文字wen,警告标题til,操作Activity,只存在确定键请输入0		
		
	function jingao(wen,til,Activity,key){
		var wen = wen || "Error";
		var til = til || "Tips !";
		var acty = Activity || "";
		var k = key || 0;
		var i = "";
		var len = $(".ping").length;
		var flag = new Date().getTime();
		var p = "#ping"+len;
		$(p).css("display","none");
		$(p).remove();
		i += '<div class="ping" id="ping'+len+'">';
		i += '<div class="ping_bg""></div>';
		i += '<div class="jingao">';
		i += '<div class="head_tan";>'+til+'</div>';
		i += '<div class="bottom_tan">';
		i += '<div class="bottom1"><p id="j_neirong_">'+wen+'</p></div>';
		i += '<div>';				
		i += '<a href="javascript:void(0)" id="false_'+flag+'">取消</a>';
		i += '<a href="javascript:void(0)" id="true_'+flag+'">确定</a>';
		i += '</div>';
		i += '</div>';
		i += '</div>';	
		i += '</div>';
		$(i).appendTo("body");
		var fal = "#false_"+flag;
		var tue = "#true_"+flag;
		if(key){
			$(fal).on("click",function(){
				$(p).css("display","none");
				$(p).remove();
			});
		}
		else{
			$(fal).remove();	
		}
		$(tue).focus().on("click",function(){
			$(p).css("display","none");
			$(p).remove();
			try{
				acty();
			}
			catch(e){
			
			}
			
		});
		return p;
	}
	
	function setInput(types,til,Activity,key){		//类型，提示文字，方法， 值
		var wen = "<input id='alert_input' type='"+types+"' />" || "<input id='alert_input' type='text' />";
		var til = til || "Tips !";
		var k = key || 0;
		var acty = Activity || "";
		var i = "";
		var len = $(".ping").length;
		var flag = new Date().getTime();
		var p = "#ping"+len;
		$(p).css("display","none");
		$(p).remove();
		i += '<div class="ping" id="ping'+len+'">';
		i += '<div class="ping_bg""></div>';
		i += '<div class="jingao">';
		i += '<div class="head_tan";>'+til+'</div>';
		i += '<div class="bottom_tan">';
		i += '<div class="bottom1"><p id="j_neirong_">'+wen+'</p></div>';
		i += '<div>';				
		i += '<a href="javascript:void(0)" id="false_'+flag+'">取消</a>';
		i += '<a href="javascript:void(0)" id="true_'+flag+'">确定</a>';
		i += '</div>';
		i += '</div>';
		i += '</div>';	
		i += '</div>';
		$(i).appendTo("body");
		var fal = "#false_"+flag;
		var tue = "#true_"+flag;
		$("#alert_input").keyup(function(){setPwd=$(this).val();});
		if(key){
			$(fal).on("click",function(){
				$(p).css("display","none");
				$(p).remove();
			});
		}
		else{
			$(fal).remove();
			//$("#payPwd").val("");
		}
		$(tue).on("click",function(){
			$(p).css("display","none");
			$(p).remove();
			try{
				acty();
			}
			catch(e){
			
			}
		});
	}
	
	
	function setDoubleInput(types,dbtypes,til,Activity,key){		//类型，提示文字，方法， 值
		var wen = "<input id='alert_input' type='"+types+"' />" || "<input id='alert_input' type='text' />";
		var wendb = "<input id='alertDB_input' type='"+dbtypes+"' />" || "<input id='alertDB_input' type='text' />";
		var til = til || "Tips !";
		var k = key || 0;
		var acty = Activity || "";
		var i = "";
		var len = $(".ping").length;
		var flag = new Date().getTime();
		var p = "#ping"+len;
		$(p).css("display","none");
		$(p).remove();
		i += '<div class="ping" id="ping'+len+'">';
		i += '<div class="ping_bg""></div>';
		i += '<div class="jingao">';
		i += '<div class="head_tan";>'+til+'</div>';
		i += '<div class="bottom_tan">';
		i += '<div class="bottom1"><p style="margin-bottom:10px;" id="j_neirong_"><span style="width:80px;display:inline-block;float:left;text-align:left">交易密码</span>'+wen+'</p><p id="j_neirong_"><span  style="width:80px;display:inline-block;float:left;text-align:left">确认交易密码</span>'+wendb+'</p></div>';
		i += '<div>';				
		i += '<a href="javascript:void(0)" id="false_'+flag+'">取消</a>';
		i += '<a href="javascript:void(0)" id="true_'+flag+'">确定</a>';
		i += '</div>';
		i += '</div>';
		i += '</div>';	
		i += '</div>';
		$(i).appendTo("body");
		var fal = "#false_"+flag;
		var tue = "#true_"+flag;
		$("#alert_input").keyup(function(){setPwd=$(this).val();});
		$("#alertDB_input").keyup(function(){setDbPwd=$(this).val();});
		if(key){
			$(fal).on("click",function(){
				$(p).css("display","none");
				$(p).remove();
			});
		}
		else{
			$(fal).remove();
			//$("#payPwd").val("");
		}
		$(tue).on("click",function(){
			$(p).css("display","none");
			$(p).remove();
			try{
				acty();
			}
			catch(e){
			
			}
		});
	}
	
	
	function chuangkou(wen,til,Activity,key,isLock){
		//var text = "<img src='http://192.168.0.100:8080/wgtz/Product/images/progress.gif' width='24' height='24'> <span style='font-family:楷体;font-size:22px;'>"+wen+"</span>";
		var wen = wen || "Error";
		var til = til || "Tips !";
		var acty = Activity || "";
		var k = key || 0;
		var i = "";
		var len = $(".ping").length;
		var flag = new Date().getTime();
		var p = "#qwy_ping"+len;
		$(p).css("display","none");
		$(p).remove();
		i += '<div class="ping" id="qwy_ping'+len+'">';
		i += '<div class="ping_bg"';
		if(isLock){
			i += 'style="opacity:0.5;filter:alpha(opacity=50);moz-opacity:0.5;"';
		}
		i += '	></div>';
		i += '<div class="jingao">';
		i += '<div class="head_tan";>'+til+'</div>';
		i += '<div class="bottom_tan">';
		i += '<div class="bottom1"><p id="j_neirong_">'+wen+'</p></div>';
		i += '<div>';				
		i += '<a href="javascript:void(0)" id="false_'+flag+'">取消</a>';
		i += '<a href="javascript:void(0)" id="true_'+flag+'">确定</a>';
		i += '</div>';
		i += '</div>';
		i += '</div>';	
		i += '</div>';
		$(i).appendTo("body");
		var fal = "#false_"+flag;
		var tue = "#true_"+flag;
		if(key>=1){
			if(!(key==1)){
				$(fal).on("click",function(){
					$(p).css("display","none");
					$(p).remove();
				});
			}
			else{
				$(fal).remove();	
			}
			$(tue).focus().on("click",function(){
				$(p).css("display","none");
				$(p).remove();
				try{
					acty();
				}
				catch(e){
				
				}
				
			});
		}else{
			$(fal).remove();	
			$(tue).remove();	
		}
		
		return p;
	}
	
	/**关闭窗口
	 * @param id 窗口Id
	 */
	function closeAlert(id){
		$(id).css("display","none");
		$(id).remove();
	}