<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<HTML>
<HEAD>
<TITLE>分配权限</TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/Product/Admin/css/public.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/zTree_v3/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>

</head>
<SCRIPT type="text/javascript">
	var setting = {
		view: {
			selectedMulti: false
		},
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeCheck: beforeCheck,
			onCheck: onCheck
		}
	};
	var zNodes =${list};//创建一个数组
	
	//alert(zNodes);
	/* var zNodes =[
 		{ id:1, pId:0, name:"随意勾选 1", open:true},
		{ id:11, pId:1, name:"随意勾选 1-1"},
		{ id:12, pId:1, name:"随意勾选 1-2", open:true},
		{ id:121, pId:12, name:"随意勾选 1-2-1"},
		{ id:122, pId:12, name:"随意勾选 1-2-2"},
		{ id:2, pId:0, name:"禁止勾选 2", open:true},
		{ id:21, pId:2, name:"禁止勾选 2-1"},
		{ id:22, pId:2, name:"禁止勾选 2-2", checked:true, open:true},
		{ id:221, pId:22, name:"禁止勾选 2-2-1"},
		{ id:222, pId:22, name:"禁止勾选 2-2-2", checked:true},
		{ id:23, pId:2, name:"禁止勾选 2-3", checked:true} 
	]; */
	/*  var list='${list}';
	for (var int = 0; int < list.length; int++) {
		var modul=list[i];
		var item="{id:"+modul.id+",pId:"+modul.pId+"}"
		zNodes[i]=modul;
		
	} */
	
	var code, log, className = "dark";
	function beforeCheck(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
		return (treeNode.doCheck !== false);
	}
	function onCheck(e, treeId, treeNode) {
		showLog("[ "+getTime()+" onCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
	}		
	function showLog(str) {
		if (!log) log = $("#log");
		log.append("<li class='"+className+"'>"+str+"</li>");
		if(log.children("li").length > 6) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	function getTime() {
		var now= new Date(),
		h=now.getHours(),
		m=now.getMinutes(),
		s=now.getSeconds(),
		ms=now.getMilliseconds();
		return (h+":"+m+":"+s+ " " +ms);
	}

	function checkNode(e) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		type = e.data.type,
		nodes = zTree.getSelectedNodes();
		if (type.indexOf("All")<0 && nodes.length == 0) {
			alert("请先选择一个节点");
		}

		if (type == "checkAllTrue") {
			zTree.checkAllNodes(true);
		} else if (type == "checkAllFalse") {
			zTree.checkAllNodes(false);
		} else {
			var callbackFlag = $("#callbackTrigger").attr("checked");
			for (var i=0, l=nodes.length; i<l; i++) {
				if (type == "checkTrue") {
					zTree.checkNode(nodes[i], true, false, callbackFlag);
				} else if (type == "checkFalse") {
					zTree.checkNode(nodes[i], false, false, callbackFlag);
				} else if (type == "toggle") {
					zTree.checkNode(nodes[i], null, false, callbackFlag);
				}else if (type == "checkTruePS") {
					zTree.checkNode(nodes[i], true, true, callbackFlag);
				} else if (type == "checkFalsePS") {
					zTree.checkNode(nodes[i], false, true, callbackFlag);
				} else if (type == "togglePS") {
					zTree.checkNode(nodes[i], null, true, callbackFlag);
				}
			}
		}
	}
	function setAutoTrigger(e) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.setting.check.autoCheckTrigger = $("#autoCallbackTrigger").attr("checked");
		$("#autoCheckTriggerValue").html(zTree.setting.check.autoCheckTrigger ? "true" : "false");
	}
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		$("#checkTrue").bind("click", {type:"checkTrue"}, checkNode);
		$("#checkFalse").bind("click", {type:"checkFalse"}, checkNode);
		$("#toggle").bind("click", {type:"toggle"}, checkNode);
		$("#checkTruePS").bind("click", {type:"checkTruePS"}, checkNode);
		$("#checkFalsePS").bind("click", {type:"checkFalsePS"}, checkNode);
		$("#togglePS").bind("click", {type:"togglePS"}, checkNode);
		$("#checkAllTrue").bind("click", {type:"checkAllTrue"}, checkNode);
		$("#checkAllFalse").bind("click", {type:"checkAllFalse"}, checkNode);

		$("#autoCallbackTrigger").bind("change", {}, setAutoTrigger);
	});
	
	function destributePersion(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		var modulIds="";
		if(nodes.length>0){
			for (var i = 0; i < nodes.length; i++) {
				modulIds+=nodes[i].id+",";
			}
			modulIds=modulIds.substr(0,modulIds.length-1)
			var userAdminid=$("#adminId").val();
			var url="${pageContext.request.contextPath}/Product/Admin/distributePermission!savePermission.action?userAdminId="+userAdminid+"&modulIds="+modulIds;
			$.ajax({
				type : "post",
				url : url,
				data:null,
				success : function(data) {
					su=data;
					if(su.status=='ok'){
						alert("分配权限成功");
						window.location.href="${pageContext.request.contextPath}/Product/Admin/usersAdmin!findUsersAdminList.action";
					}else{
						alert("分配权限失败");
					}	
				},
			});
		}else{
			alert("请选择模块");
			return false;
		}
		/* alert(nodes[0].id) */
	}
</SCRIPT>
</HEAD>

<BODY>

<div class="center">	
	<jsp:include page="/Product/Admin/common/head.jsp"/>		
	<div class="main" align="center" style="text-align: center;">
		<h1 style="text-align: center;">分配权限</h1>
		<div class="content_wrap" align="center" >
			<div class="zTreeDemoBackground left">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
		<input type="hidden" id="adminId" value="${userAdminId}" >
		<c:choose>
			<c:when test="${list !=null && list !='[]' && list !=''  }">
				<input type="button" value="分配权限" onclick="destributePersion()">
			</c:when>
			<c:otherwise >
				<h1 style="color: gray;">您无权限分配</h1>
			</c:otherwise>
		</c:choose>
	</div>
</div>
</BODY>
</HTML>