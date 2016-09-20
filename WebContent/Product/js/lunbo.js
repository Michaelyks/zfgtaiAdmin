$(function(){ 
	$(".img-layout").width($(window).width());
	$("#closeLayout").click(function(){ $(".img-layout").hide();})
	openImg("#lbul");
	lunbo({						//初始化
		prev:"#prev",		//上一页
		next:"#next",		//下一页
		ul:"#lbul",			//列表
		page:"3",				//每次滚动页数
		margin:"0",			//margin 左右 边距
		border:"0"				//边框
	});
})
/* 
 *	图片轮播切换代码
 *  彪
 */
function lunbo(arr){
	arr=$.extend({prev:'',next:'',ul:'',page:'',margin:'',border:''},arr||{});
	var liwidth=parseFloat($(arr.ul).find("li").width())+parseFloat(arr.margin)+parseFloat(arr.border)*2;	
	var movepage=liwidth*parseFloat(arr.page)-parseFloat(arr.margin);		
	var yu=$(arr.ul).find("li").length % arr.page;			
	var ulwidth=parseInt($(arr.ul).find("li").length/arr.page)*movepage+yu*liwidth;		
	$(arr.ul).width(ulwidth);
	var left=0;
	$(arr.next).click(function(){
			if(left-movepage<=-ulwidth){
			left=left;}else {left-=movepage;}
			move(left);
	})
	$(arr.prev).click(function(){
		if(left+movepage>0){left=left;	
		}else {left+=movepage;}
		move(left);	
	})
	function move(left){
		if(left-movepage<=-ulwidth){
			$(arr.next).removeClass("page-next-on").addClass("page-next");
		}else {
			$(arr.next).removeClass("page-next").addClass("page-next-on");
		}
		if(left+movepage>0){
			$(arr.prev).removeClass("page-prev-on").addClass("page-prev");	
		}else {
			$(arr.prev).removeClass("page-prev").addClass("page-prev-on");	
		}
		$(arr.ul).animate({left:left+"px"},"300");
	}
}
function openImg(objId){
	var index=0;
	var imgList=new Array();
	$(objId).find("li").click(function(){ 
		$(".img-layout").show();
	})
	for(var i=0;i<$(objId).children("li").length;i++){
		imgList[i]=$(objId).children("li").eq(i).find("img").attr("data-src");
	}
	$(objId).children("li").click(function(){ 
		index=$(this).index();
		show(index);
	})
	$(".btn .left").click(function(){ 
		index >= 1 ? index-- : index;
		show(index);
	})
	$(".btn .right").click(function(){
		index < imgList.length-1  ? index++: index;
		show(index);
	})
	function show(cur){ 
		$(".img-layout .img-box img").attr("src",imgList[cur]);
	}
}
