<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<meta charset="utf-8" />
<title>虚拟投资</title>
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />		
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.css" type="text/css">
<link href="${pageContext.request.contextPath}/Product/Admin/css/product_fabu_history.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/Product/Admin/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/Product/Admin/plugins\kalendae\build\kalendae.standalone.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<script src="${pageContext.request.contextPath}/js/artDialog4.1.7/jquery.artDialog.js?skin=blue"></script>
<style type="text/css">
.a{
		color: blue;
		text-decoration: underline ;
	}
</style>	 
<script type="text/javascript">
	function findLeftCopies(){
		var productId=$("#productId").val();
		$.ajax({ 
			dataType: 'json',
			url: "${pageContext.request.contextPath}/Product/Admin/virtualInsRecord!leftCopies.action?productId="+productId,
			success: function(data){
	       		$("#leftCopies").val(data.productLeftCopies);
	       		$("#qtje").val(data.qtje);
	       		$("#productStatus").val(data.productStatus);
	      }});
	}
</script> 	
</head>
	<body>
		<div class="center">		
	<jsp:include page="/Product/Admin/common/head.jsp"/>	
		<div class="main" align="center">
		<h3 align="center">虚拟投资</h3>
		 <span style="color: red">注意：虚拟投资金额必须大于虚拟投资人数*起投金额(起投金额不是动态的),<br>如果虚拟投资金额大于虚拟投资人数-1*起投金额(起投金额不是动态的),并且虚拟投资金额小于虚拟投资人数*起投金额(起投金额不是动态的)，请分两次或者请减少1人次</span><br>
		<%--<c:if test="${product !=null || product !='' }">
			项目名称：${product.title}<br>
			状态:${product.cpzt}<br>
			收益率：<fmt:formatNumber value="${product.annualEarnings}" pattern="#,##0.##"/>%<br>
			理财期限：<fmt:formatNumber value="${product.lcqx}" pattern="#,##0"/><br>
			项目起投金额：<fmt:formatNumber value="${product.qtje*0.01}" pattern="#,##0.##"/><br>
			项目余额：<fmt:formatNumber value="${product.leftCopies}" pattern="#,##0"/><br>
			项目到期时间：<fmt:formatDate value="${product.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/><br>
			参加人数：<fmt:formatNumber value="${product.userCount}" pattern="#,##0"/><br>
			
		</c:if> --%>
		
		<form action="${pageContext.request.contextPath}/Product/Admin/virtualInsRecord!virtualIns.action" onsubmit="return isSubmit();">
			虚拟投资产品:
			<select id="productId" name="productId" onchange="findLeftCopies()">
				<option value="0">请选择</option>
				<c:forEach items="${list}" var="item">
					<option value="${item.id}">${item.title}</option>
				</c:forEach>
			</select>
					剩余余额：<input type="text" id="leftCopies" value="<fmt:formatNumber value="${productLeftCopies}" pattern="#,##0"/>" readonly="readonly" style="color: gray;" >
					起投金额:<input type="text" id="qtje" value="${qtje}" readonly="readonly" name="qtje" style="color: gray;" ><br>
			<br>
			投资人数:<input type="text" id="userCount" name="userCount">
			投资金额:<input type="text" id="money" name="money"><br>
			<input type="hidden" id="productStatus" name="productStatus" value="${productStatus}">
			<input type="submit" value="提交">
		</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		var productId='${productId}';
		if(productId!=''){
			$("#productId").val(productId);
		}else{
			$("#productId").val("0");
		}
	});
	$(function(){
		var message='${message}';
		if(message!=''){
			if(message=='虚拟投资成功'){
				window.location.href="${pageContext.request.contextPath}/Product/Admin/virtualInsRecord!toVirtualIns.action?productId=${product.id}";
			}
			alert(message);
		}
	});
	function isSubmit(){
		var productId=$("#productId").val();
		if(productId=='0' || productId==''){
			alert("请选择产品");
			return false;
		}
		var status=$("#productStatus").val();
		if(status!='0'){
			alert("产品不在营销中");
			return false;
		}
		var userCount=$("#userCount").val();
		var money=$("#money").val();
		if(money==''){
			alert("请输入投资金额");
			return false;
		}
		if(userCount==''){
			alert("请输入投资人数");
			return false;
		}
		if(!isOnlyNumber(userCount)){
			alert("投资人数请输入整数");
			return false;	
		}
		if(parseInt(money)%50!=0){
			jingao("投资金额必须为50的整数倍", "提示", "", 0);
			return false;
		}
		if(!isOnlyNumber(money)){
			alert("投资金额请输入整数");
			return false;
		}
	}
</script>
</html>