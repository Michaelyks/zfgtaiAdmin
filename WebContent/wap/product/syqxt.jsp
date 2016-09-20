<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>中泰理财 - 收益曲线图</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/wap/product/css/reset.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/wap/product/css/layout.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
	<script src="${pageContext.request.contextPath}/wap/product/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/wap/product/js/highcharts/highcharts.js"></script>
	<script type="text/javascript">
		var profits;
		var dates;
		var usersId;
		$(function(){
			if(isWeiXin()){
				$("#header").css("display","none");
			}
			usersId = $("#usersId").val();
			if(usersId==null || usersId==""){
				alert("请先登录");
				return;
			}
			//加载统计数据
			$.ajax({
				type : "post",
				async : false,
				url : "${pageContext.request.contextPath}/Product/statistics!queryUserProfit.action?usersId="+usersId,
				success : function(data) {
					if(data.status=="ok"){
						profits = data.profit;
						dates = data.date;
					}else if(data.status=="noLogin"){
						alert(data.json);
					}else {
						alert("系统繁忙，请稍后重试");
					}
				},
				error: function(data){
					alert("系统繁忙，请稍后重试");
				}
			});
			
			loadQxt();
			
		});
		function loadQxt(){
			$('#qxt').highcharts({
		        title: {
		            text: '收益曲线图',
		            x: -20
		        },
		        xAxis: {
		            categories: dates
		        },
		        yAxis: {
		            title: {
		                text: '收益'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }],
		            min:0
		        },
		        tooltip: {
		            valueSuffix: '元'
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            name: '',
		           	data: profits
		        }]
		    });
		}
</script>
</head>
<body>
<% 
	String usersId = request.getParameter("usersId");
%>
<input type="hidden" id="usersId" value="<%=usersId %>" />
<%-- <header id="header"><a href="javascript:history.go(-1);" class="header_l"><img src="${pageContext.request.contextPath}/wapNotice/images/return_icon.png"/></a>登 录
</header> --%>
	<div id="qxt" style="height: 200px;"></div>
</body>

</html>