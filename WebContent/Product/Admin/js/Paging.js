
			//sum总页数，c当前页
			function page_list(sum,c){
				var total  = sum || 0; //总条数
				var cur    = parseInt(c || 1); //当前页
				var l_num  = 6; //显示可选择的按钮数为偶数，奇数会照成长度加一，因为基数FIRST为1在循环中加1总体长度加一，如果在循环中减一，会照成末尾减一，实际长度也减一
				var offset = Math.floor(l_num/2); //偏移量
				var first  = cur-offset; //第一页 
				var last   = cur+offset; //第一页
				var p      = "";
				if(sum>l_num){
					if(first<=0){
						first = 1;
						last  = l_num;						
					}
					else if(last>total){
						first = total-l_num;
						last = total;
					}
				}
				else{
					first = 1;
					last  = l_num;
				}	
					
				if(cur>1){			
					p += '<a href="1">首页</a><a href="'+(cur-1)+'">上一页</a>'; 
				}
				for(var i=first;i<=(first+l_num);i++){
					if(i==cur){
						p +='<a href="'+i+'" class="cur">'+i+'</a>';
					}
					else{
						p +='<a href="'+i+'">'+i+'</a>';
					}
					
				}
				if(cur<total){
					p += '<a href="'+(cur+1)+'">下一页</a><a href="'+total+'">末页</a>';
				}
				
				var page = document.getElementById("page");
					page.innerHTML = p;
	
 			}
			