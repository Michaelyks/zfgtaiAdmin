<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
    	 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 投资时间段分布表</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />	
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/qwyUtilJS/qwyUtil.js"></script>
	  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/highcharts.js"></script>
	  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>

	  <script>
	  	$(function(){
			$("#searchDate").keydown(function(event){
				if(event.keyCode==13){
					search();
				}
			});
		});
	  
	  	var currentDate = new Date();
	  	var datas;
		var date = currentDate.getFullYear()+"-"+(currentDate.getMonth()+1)+"-"+currentDate.getDate();
		
	  	function search(){
	  		if($("#searchDate").val().replace(/(^\s*)|(\s*$)/g, "")==""){
	  			alert("请选择日期");
	  			return false;
	  		};
	  		date = $("#searchDate").val();
	  		StatisticalChart();
	  	}
	  	
	  	function StatisticalChart(){
	  		loadData();
		    loadPersonCount();
		    loadPersonTime();
		    loadInMoney();
		    loadCoupon();
	  	}
	  	
	  	function loadData(){
	  		$.ajax({
				type : "post",
				async : false,
				url : "${pageContext.request.contextPath}/Product/Admin/investors!queryInvestorsStatistics.action?targetDate="+date,
				success : function(data) {
					datas = data;
				},
				error: function(data){
					alert("系统错误");
				}
			});
	  	}
	  	
	  	var hours = ['0','1', '2', '3', '4', '5', '6','7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'];
	  	
	  	$(function () {
	  		StatisticalChart();
		});
		
		//personTime
		function loadPersonCount(){
			$('#personCount').highcharts({
		        title: {
		            text: '投资人数时间统计分布表',
		            x: -20
		        },
		        xAxis: {
		            categories: hours
		        },
		        yAxis: {
		            title: {
		                text: '投资人数'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
		        },
		        tooltip: {
		            valueSuffix: '人'
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            name: '投资人数统计',
		           	data: datas.personCount
		        }]
		    });
		}
		
		function loadPersonTime(){
			$('#personTime').highcharts({
		        title: {
		            text: '投资人次时间统计分布表',
		            x: -20
		        },
		        xAxis: {
		            categories: hours
		        },
		        yAxis: {
		            title: {
		                text: '投资次数'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
		        },
		        tooltip: {
		            valueSuffix: '次'
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            name: '投资人次统计',
		           	data: datas.personTime
		        }]
		    });
		}
		
		function loadInMoney(){
			$('#inMoney').highcharts({
		        title: {
		            text: '投资金额时间统计分布表',
		            x: -20
		        },
		        xAxis: {
		            categories: hours
		        },
		        yAxis: {
		            title: {
		                text: '用户投资本金(不含投资券)'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
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
		            name: '投资金额统计',
		           	data: datas.inMoney
		        }]
		    });
		}
		
		function loadCoupon(){
			$('#coupon').highcharts({
		        title: {
		            text: '投资券金额时间统计分布表',
		            x: -20
		        },
		        xAxis: {
		            categories: hours
		        },
		        yAxis: {
		            title: {
		                text: '投资券金额'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
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
		            name: '投资券金额统计',
		           	data: datas.coupon
		        }]
		    });
		}
	  </script>


  </head>
  
  <body>
  	<div class="center">
	<jsp:include page="/Product/Admin/common/head.jsp"/>
	<center><h1>投资分析曲线图</h1></center>
	<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
		<div align="center">
  		查询日期：<input type="text" id="searchDate" onclick="WdatePicker()" value="">&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" value="查询" onclick="search();return false;"><br/>
  		</div>
	  	<table>
	  		<tr align="center">
	  			<td>
	  				<div id="personCount" style="min-width:700px;height:400px"></div>
	  			</td>
	  			<td>
	  				<div id="personTime" style="min-width:700px;height:400px"></div>
	  			</td>
	  		</tr>
	  		<tr>
	  			<td>
	  				<div id="inMoney" style="min-width:700px;height:400px"></div>
	  			</td>
	  			<td>
	  				<div id="coupon" style="min-width:700px;height:400px"></div>
	  			</td>
	  		</tr>
	  	</table>
    </div>
  </body>
  <script type="text/javascript">
  $("#searchDate").val(date);
  </script>
</html>
