<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!doctype html>
<html>
	<meta charset="utf-8" />
	<title>产品历史记录</title>
	<title>产品历史记录</title>
		<link href="${pageContext.request.contextPath}/Product/Admin/product/css/public.css" rel="stylesheet" type="text/css" />		
		<link href="${pageContext.request.contextPath}/Product/Admin/product/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
		<script src="${pageContext.request.contextPath}/Product/Admin/product/js/jquery-1.9.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/Product/Admin/product/js/head.js"></script>
		<script src="${pageContext.request.contextPath}/Product/Admin/product/plugins\kalendae\build\kalendae.standalone.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/product/plugins\kalendae\build\kalendae.css" type="text/css">
	<script type="text/javascript">
		var context='${pageContext.request.contextPath}';
		function queryProduct(){
			var tj1=$("#tj1").val();
			var tj2=$("#tj2").val();
			var tj3=$("#tj3").val();
			var tj4=$("#tj4").val();
			var value1=$("#value1").val();
			var value2=$("#value2").val();
			var value3=$("#value3").val();
			var value4=$("#value4").val();
			var url = "${pageContext.request.contextPath}/Product/registerUser!isUserExist.action?";
			/* "users.username="+$(this).val(); */
			var data={tj1:value1,tj2:value2,tj3:value3,tj4:value4}
			$.post(url,data,function(result){
				if(data.status=="err"){
					jingao(data.json,"提示","",0);
					return false;
				}else{
				}
			});
		}
	</script>
</head>
	<body>
		<div class="center">			
			<div class="main">
				<form>			
					<ul class="select">
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
							<input name="value1" id="value1"  type="text" />
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
							<input name="value2" id="value2" type="text"/>
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
							<input name="value3" id="value3" type="text" />
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
							<input name="value4" id="value4" type="text"/>
						</li>
						<a class="sereach" href="javascript:void(0)" id="sereach">查询</a>
						<div class="clea"></div>
					</ul>
				</form>
				<script>
				
					
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
						/*		if($(this).find("option:selected").text()=="发布时间" || $(this).find("option:selected").text()=="到期时间"){
									$(this).parents("li").find("input").attr("onClick","WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})");												
							}
								else{
									//$(this).parents("li").find("input").removeAttr("onclick");
									$(this).parents("li").find("input").remove();
									$('<input type="text" />').appendTo($(this).parents("li"));
								}*/
								
								if($(this).find("option:selected").text()=="发布时间"){
									$(this).siblings("input").attr("id","time1");	
									 ssss = "time1";				
									
								}
								else if( $(this).find("option:selected").text()=="到期时间"){
										$(this).siblings("input").attr("id","time2");
										var ssss = "time2";													
								}
								else{
									//$(this).parents("li").find("input").removeAttr("onclick");
									$(this).parents("li").find("input").remove();
									$('<input type="text" />').appendTo($(this).parents("li"));
								}
								var k4 = new Kalendae.Input(ssss, {
										attachTo:document.body,
										months:2,//多少个月显示出来,即看到多少个日历
										mode:'range',
										selected:[Kalendae.moment().subtract({d:7}), Kalendae.moment().add({d:0})]
									});
								
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
							
							//点击查询
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
								}*/
							})
							
							
							
						});
					</script>
				<p>共有<span>20</span>个项目</p>
				<div class="table">
					<table cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td>序号</td>
								<td>发布状态</td>
								<td>产品名称</td>
								<td>产品类型</td>
								<td>年化收益</td>
								<td>项目总额</td>
								<td>到期时间</td>
								<td>产品状态</td>
								<td>发布时间</td>
							</tr>
						<c:forEach items="${list}"  var="item" varStatus="i">
							<tr>
								<td>${i.count + (pageUtil.currentPage-1)*pageUtil.pageSize}</td>
								<td>提交成功</td>
								<td>${item.title}</td>
								<td><c:if test="${item.investType eq '0' }">
										车无忧
									</c:if>
									<c:if test="${item.investType eq '1' }">
										贸易通
									</c:if>
								</td>
								<td>${item.annualEarnings}</td>
								<td>${item.financingAmount}</td>
								<td>${item.finishTime}</td>
								<td>${item.productStatus}</td>
								<td>${item.startTime}</td>
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
</html>