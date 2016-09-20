<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>零钱包--转入转出曲线图</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/wap/product/js/highcharts/highcharts.js"></script>
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
		    loadInMoney();
// 		    loadOutMoney();
	  	}
	  	
	  	function loadData(){
	  		$.ajax({
				type : "post",
				async : false,
				url : "${pageContext.request.contextPath}/Product/Admin/coinPurseReport!lqbqxt.action?insertTime="+date,
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
		
		//转入
		function loadInMoney(){
			$('#inMoney').highcharts({
		        title: {
		            text: '零钱包转入转出金额时间统计分布表',
		            x: -20
		        },
		        xAxis: {
		            categories: hours
		        },
		        yAxis: {
		            title: {
		                text: '金额'
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
		            name: '零钱包转入金额统计',
		           	data: datas.inMoney
		        },{
		            name: '零钱包转出金额统计',
		           	data: datas.outMoney
		        }]
		    });
		}
		//转出
		function loadOutMoney(){
			$('#outMoney').highcharts({
		        title: {
		            text: '零钱包转出时间统计分布表',
		            x: -20
		        },
		        xAxis: {
		            categories: hours
		        },
		        yAxis: {
		            title: {
		                text: '转出金额'
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
		            name: '零钱包转出金额统计',
		           	data: datas.outMoney
		        }]
		    });
		}
		
		
	  </script>
</head>
<body>
<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
	<div class="main" align="center">
	<h3>零钱包--转入转出曲线图</h3>
	<form id="frm" action="${pageContext.request.contextPath}/Product/Admin/coinPurseReport!coinPurseDetailReport.action">
	查询日期：<input type="text" id="searchDate" onclick="WdatePicker()" value="">&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" value="查询" onclick="search();return false;"><br/>
	</form>
	<table>
	  		<tr align="center">
	  			<td>
	  				<div id="inMoney" style="min-width:800px;height:400px"></div>
	  			</td>
	  		</tr>
	  		<!-- <tr align="center">
	  			<td>
	  				<div id="outMoney" style="min-width:800px;height:400px"></div>
	  			</td>
	  		</tr> -->
	  	</table>
</div>
</div>
  </body>
  <script type="text/javascript">
  $("#searchDate").val(date);
  </script>
</html>