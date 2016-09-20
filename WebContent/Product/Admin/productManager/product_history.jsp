
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
	<meta charset="utf-8" />
	<title>产品历史记录</title>
		<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
		<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
		<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
		 <link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
        <style>
        	.a{
		color: blue;
		text-decoration: underline ;
	     }
	</style> 
	<script type="text/javascript">
	function checkNum()
	{
		var reg=new RegExp("^[+-]?[1-9]?[0-9]*\.[0-9]*$");
		var reg2=new RegExp("^[0-9]*$");
		var annualEarnings=$("#annualEarnings").val();
		//alert(str);
		if(annualEarnings!=''){
			if(!reg.test(annualEarnings)||!reg2.test(annualEarnings)){
				alert("年化收益输入错误");
				return false;
			} 	
		}
		var financingAmount=$("#financingAmount").val();
		//alert(str);
		if(financingAmount!=''){
			if(!reg.test(financingAmount)||!reg2.test(financingAmount)){
				alert("总金额输入错误");
				return false;
			} 	
		}
		return true;
	}
		/* function queryProduct(){
			var tj1=$("#tj1").val();
			var tj2=$("#tj2").val();
			var tj3=$("#tj3").val();
			var tj4=$("#tj4").val();
			var value1=$("#value0").val();
			var value2=$("#value1").val();
			var value3=$("#value2").val();
			var value4=$("#value3").val();
			var url = "${pageContext.request.contextPath}/Product/Admin/releaseProduct!productRecord.action?";
			url+=tj1+"="+value1+"&";
			url+=tj2+"="+value2+"&";
			url+=tj3+"="+value3+"&";
			url+=tj4+"="+value4+"&";
			url+="tj1="+tj1+"&";
			url+="tj2="+tj2+"&";
			url+="tj3="+tj3+"&";
			url+="tj4="+tj4+"&"; 
			window.location.href=url;
		} */
		
	</script>
	<script type="text/javascript">
	function modifyProduct(id){
		window.location.href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!toModifyProduct.action?productId="+id;
	}
	function modifyFundProduct(id){
		window.location.href="${pageContext.request.contextPath}/Product/Admin/releaseFundProduct!toModifyFundProduct.action?productId="+id;
	}
	</script>
</head>
	<body>
		<div class="center">		
		<jsp:include page="/Product/Admin/common/head.jsp"/>	
			<div class="main" align="center">
			<h3 align="center">产品发布历史记录</h3>
				<form action="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productRecord.action" method="GET" onsubmit="return checkNum()">	
				产品名称:<input type="text" id="title" name="title" value="${title}"/>
				年化收益：<input type="text" id="annualEarnings" name="annualEarnings" value="${annualEarnings}"/>
				项目总额：<input type="text" id="financingAmount" name="financingAmount" value="${financingAmount}"/><br>
				发布时间：<input type="text" id="insertTime" name="insertTime" value="${insertTime}"/>	
				到期时间:<input type="text" id="finishTime" name="finishTime" value="${finishTime}"/>
				产品状态:<select id="productStatus" name="productStatus"> 
							<option value="" selected="selected">全部</option>
							<option value="-2">审核不通过 </option>
							<option value="-1">未审核</option>
							<option value="0">营销中</option>
							<option value="1">已售罄 </option>		
							<option value="2">结算中 </option>		
							<option value="3">已结算 </option>				
						</select>
				<!-- 产品类型:<select id="investType" name="investType"> 
							<option value="">全部</option>
							<option value="0">车无忧</option>
							<option value="1">贸易通</option>
							<option value="2">牛市通</option>
							<option value="3">房盈宝 </option>
						</select> -->
						<input type="submit" value="查询">
					<!-- <ul class="select">
						<li>
							<select class="select1" id="tj1" name="sel" >
								<option value="noparamer">筛选条件</option>
								<option value="product.productStatus">发布状态</option>
								<option value="product.title">产品名称</option>
								<option value="product.annualEarnings">年化收益</option>
								<option value="product.financingAmount">项目总额</option>
								<option value="finishTime">到期时间</option>
								<option value="insertTime">发布时间</option>
								<option value="username">发布人</option>						
							</select>
							<input type="text" id="value0" />
						</li>	
						
						<li>						
							<select class="select1" id="tj2" name="sel">
								<option value="noparamer">筛选条件</option>
								<option value="product.productStatus">发布状态</option>
								<option value="product.title">产品名称</option>
								<option value="product.annualEarnings">年化收益</option>
								<option value="product.financingAmount">项目总额</option>
								<option value="finishTime">到期时间</option>
								<option value="insertTime">发布时间</option>
								<option value="username">发布人</option>								
							</select>
							<input type="text" id="value1"/>
						</li>
						<li>
							<select class="select1" id="tj3" name="sel">
								<option value="noparamer">筛选条件</option>
								<option value="product.productStatus">发布状态</option>
								<option value="product.title">产品名称</option>
								<option value="product.annualEarnings">年化收益</option>
								<option value="product.financingAmount">项目总额</option>
								<option value="finishTime">到期时间</option>
								<option value="insertTime">发布时间</option>
								<option value="username">发布人</option>				
							</select>
							<input type="text" id="value2"/>
						</li>	
						
						<li>						
							<select class="select1" id="tj4" name="sel">
								<option value="noparamer">筛选条件</option>
								<option value="product.productStatus">发布状态</option>
								<option value="product.title">产品名称</option>
								<option value="product.annualEarnings">年化收益</option>
								<option value="product.financingAmount">项目总额</option>
								<option value="finishTime">到期时间</option>
								<option value="insertTime">发布时间</option>
								<option value="username">发布人</option>														
							</select>
							<input type="text" id="value3" />
						</li>
						<a class="sereach" href="javascript:queryProduct();" id="sereach">查询</a>
						<div class="clea"></div>
					</ul> -->
				</form>
				<!-- <script>
				 $(function(){
					var insertTime='${insertTime}';
					var title='${product.title}';
					var annualEarnings='${product.annualEarnings}';
					var financingAmount='${product.financingAmount}';
					var finishTime='${finishTime}';
					 var tj1='${tj1}';
					// alert(tj1);
					// alert(annualEarnings);
					 if(tj1!=''){
					 	$("#tj1").find("option[value='"+tj1+"']").attr("selected",true);
					 	if(tj1 == 'product.title'){
					 		//alert(title);
					 		$("input[type='text']")[0].value =title;
					 	}
						if(tj1 == 'product.annualEarnings'){
							//alert("000"+annualEarnings);
							$("input[type='text']")[0].value=annualEarnings;
					 	}
						if(tj1 == 'product.financingAmount'){
							//alert(financingAmount);
							$("input[type='text']")[0].value=financingAmount;
						}
						if(tj1 == 'finishTime'){
							//alert(finishTime);
							$("input[type='text']")[0].value=finishTime;
						}
						if(tj1 == 'insertTime'){
							//alert(insertTime);
							$("input[type='text']")[0].value=insertTime;
						}
					 }
					 var tj2='${tj2}';
					 if(tj1!=''){
					 	$("#tj2").find("option[value='"+tj2+"']").attr("selected",true);
					 	if(tj2 == 'product.title'){
					 		$("input[type='text']")[1].value =title;
					 	}
						if(tj2 == 'product.annualEarnings'){
							$("input[type='text']")[1].value=annualEarnings;
					 	}
						if(tj2 == 'product.financingAmount'){
							$("input[type='text']")[1].value=financingAmount;
						}
						if(tj2 == 'finishTime'){
							$("input[type='text']")[1].value=finishTime;
						}
						if(tj2 == 'insertTime'){
							
							$("input[type='text']")[1].value=insertTime;
						}
					 }
					 var tj3='${tj3}';
					 if(tj3!=''){
					 	$("#tj3").find("option[value='"+tj3+"']").attr("selected",true);
					 	if(tj3 == 'product.title'){
					 		$("input[type='text']")[2].value =title;
					 	}
						if(tj3 == 'product.annualEarnings'){
							$("input[type='text']")[2].value=annualEarnings;
					 	}
						if(tj3 == 'product.financingAmount'){
							$("input[type='text']")[2].value=financingAmount;
						}
						if(tj3 == 'finishTime'){
							$("input[type='text']")[2].value=finishTime;
						}
						if(tj3 == 'insertTime'){
							
							$("input[type='text']")[2].value=insertTime;
						}
					 }
					 var tj4='${tj4}';
					 if(tj4!=''){
					 	$("#tj4").find("option[value='"+tj4+"']").attr("selected",true);
					 	if(tj4 == 'product.title'){
					 		$("input[type='text']")[3].value =title;
					 	}
						if(tj4 == 'product.annualEarnings'){
							$("input[type='text']")[3].value=annualEarnings;
					 	}
						if(tj4 == 'product.financingAmount'){
							$("input[type='text']")[3].value=financingAmount;
						}
						if(tj4 == 'finishTime'){
							$("input[type='text']")[3].value=finishTime;
						}
						if(tj4 == 'insertTime'){
							
							$("input[type='text']")[3].value=insertTime;
						}
					 }
				}); 
					
						$(function(){
						var ind = 0;  //删除每个select选中的选项索引
						var li_ind = 0;  //区分那个select;
						var sele = new Array; //存储之前选中的选项
						sele[0] = -1;
						sele[1] = -1;
						sele[2] = -1;
						sele[3] = -1;
						
							$(".select1").change(function(){
								 ind = $(this).find("option:selected").index();//选中的选项
								 li_ind = $(this).parents("li").index();//那个select	
								var ssss;
								//第一个判断是选中时间，加点击时间事件
								/* if($(this).find("option:selected").text()=="发布时间" || $(this).find("option:selected").text()=="到期时间"){
									$(this).parents("li").find("input").attr("onClick","WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})");												
							}
								else{
									//$(this).parents("li").find("input").removeAttr("onclick");
									$(this).parents("li").find("input").remove();
									$('<input type="text" />').appendTo($(this).parents("li"));
								} */
								
								if($(this).find("option:selected").text()=="发布时间"){
									$(this).siblings("input").attr("id","value"+li_ind);	
									 ssss = "value"+li_ind;				
									
								}
								else if( $(this).find("option:selected").text()=="到期时间"){
										$(this).siblings("input").attr("id","value"+li_ind);
										 ssss = "value"+li_ind;													
								}else if($(this).find("option:selected").text()=="产品名称"){
									$(this).parents("li").find("input").remove();
									$('<input type="text" id="'+"value"+li_ind+'" />').appendTo($(this).parents("li"));
								}else{
									//$(this).parents("li").find("input").removeAttr("onclick");
									$(this).parents("li").find("input").remove();
									$('<input type="text" id="'+"value"+li_ind+'" onblur="checkNum(this.value)"/>').appendTo($(this).parents("li"));
								}
								if(ssss){
									var k4 = new Kalendae.Input(ssss, {
										attachTo:document.body,
										months:2,//多少个月显示出来,即看到多少个日历
										mode:'range',
										selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})]
									});
								}
								
								//在选中的基础上重新选择
								if(sele[li_ind]!=-1){ //判断是否已经选中							
									if(sele[li_ind]!=ind){ //判断是否改变选项
										//恢复其他三个selcet对这个选项的隐藏
										for(var i=0;i<4;i++){
											if(i!=li_ind){								
												$(".select1").eq(i).children("option").eq(sele[li_ind]).css("display","block");
											}
										}
									}
								}						
								
								//第二个判断是将选中的选项在其他三个下拉框隐藏							
							}).blur(function(){
								for(var i=0;i<4;i++){							
										if(i!=li_ind){	
											if(ind>=1){
												$(".select1").eq(i).children("option").eq(ind).css("display","none");
											}
										}
									else{
										//储存选中的选项
										sele[i] = ind; 									
									}
								}
							});			
							
							/* //点击查询
							$("#sereach").click(function(){
							//	var se = ""; //需要接受的参数是字符串
								var jsn = new Array();
								var ssss = new Array();
								
								for(var b=0;b<4;b++){
									if(sele[b]!=-1){
									//	se += $(".select1:eq(0)").children("option").eq(sele[b]).text();//拿到筛选的选项
									//	se += $(".select li input").eq(b).val(); //拿到筛选的值
										var key = $(".select1:eq(0)").children("option").eq(sele[b]).text();
										var valu = $(".select li input").eq(b).val();
										jsn[key] = valu;
										
									}
									
								}
								console.log(jsn);
								alert(jsn.length);
							/*	if(se==""){
									alert("请输入你要查询的内容");
								}
								else{
									alert(jsn);
								}*/
						/*		if(jsn.length==0){
									alert("请输入你要查询的内容");
								}
								else{
									alert(jsn);
								}
							}) */
							
							
							
						});
					</script> -->
				<p>共有<span>${pageUtil.count}</span>个项目</p>
				<div class="table">
					<table cellspacing="0" cellpadding="0"  style="  width: 100%;">
						<tbody>
							<tr>
								<td>序号</td>
								<td>发布状态</td>
								<td>产品名称</td>
								<td>产品类型</td>
								<td>年化总收益</td>
								<td>奖励（浮动）收益</td>
								<td>项目总额</td>
								<td>到期时间</td>
								<td>产品状态</td>
								<td>发布时间</td>
								<td>操作</td>
							</tr>
						<c:forEach items="${list}"  var="item" varStatus="i">
							<tr>
								<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
								<td>提交成功</td>
								<td>${item.title}</td>
								<td>
									${item.cplx}<c:if test="${item.fundType ne '' && item.fundType ne null}">${item.jjlx}</c:if>
								</td>
								<td><fmt:formatNumber value="${item.annualEarnings}" pattern="#.##" />%</td>
								<td><fmt:formatNumber value="${item.jiangLiEarnings}" pattern="#.##" />%</td>
								<td><fmt:formatNumber value="${item.financingAmount * 0.01}" pattern="#,##0.##"/></td>
								<td>${item.finishTime}</td>
								<td>
									${item.cpzt}
								</td>
								<td>${item.insertTime}</td>
								<td><c:if test="${item.productStatus=='0'||item.productStatus=='1'}">
								    <c:choose>
								      <c:when test="${item.productType=='2'}">
								       <a  class="a" id="modify_${item.id}" href="javascript:modifyFundProduct('${item.id}');">修改</a>
								      </c:when>
								      <c:otherwise>
								       <a  class="a" href="javascript:modifyProduct('${item.id}');">修改</a>
								      </c:otherwise>
								    </c:choose>								     
								</c:if>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<c:choose>
	<c:when test="${list ne '[]' &&  list ne '' && list ne null}">
		<jsp:include page="/Product/page.jsp" /></c:when>
		<c:otherwise>
			<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
	  			<img src="../images/no_record.png" />
	 	 	</div>
		</c:otherwise>
	</c:choose>
				</div>
			</div>
		</div>	
	</body>
	<script type="text/javascript">
	 var productStatus='${productStatus}';
	 if(productStatus!=''){
	 	$("#productStatus").find("option[value='"+productStatus+"']").attr("selected",true);
	 }
	 /* var productStatus='${productStatus}';
	 if(productStatus!=''){
	 	$("#productStatus").find("option[value='"+productStatus+"']").attr("selected",true);
	 } */
	</script>
	<script type="text/javascript">
var k4 = new Kalendae.Input("insertTime", {
	attachTo:document.body,
	months:2,//多少个月显示出来,即看到多少个日历
	mode:'range'
	/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
});
</script> 
<script type="text/javascript">
	var k4 = new Kalendae.Input("finishTime", {
		attachTo:document.body,
		months:2,//多少个月显示出来,即看到多少个日历
		mode:'range'
		/* selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})] */
	});
</script>
</html>