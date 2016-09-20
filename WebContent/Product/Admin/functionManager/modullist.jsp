<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<meta charset="utf-8" />
<title>模块功能展示</title>
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
</head>
	<body>
		<div class="center">		
		<jsp:include page="/Product/Admin/common/head.jsp"/>	
			<div class="main" align="center">
			<h3 align="center">模块功能展示</h3>
				<div class="table">
					<table cellspacing="0" cellpadding="0" style="  width: 100%;">
						<tbody>
						<tr>
							<td style="width: 166px;">模块名称</td>
							<td style="width: 566px;">模块路径</td>
							<td style="width: 78px;">模块类型</td>
							<td style="width: 166px;">父节点</td>
							<td style="width: 56px;">排序</td>
							<td style="width: 366px;">模块备注</td>
							<td style="width: 66px;">操作</td>
						</tr>
						<c:forEach items="${list}"  var="item" varStatus="i">
						<tr>
								<td><input class="dis_${item.id}" id="modulName_${item.id}" readonly="readonly" disabled="disabled" value="${item.modulName}" style="width:100%;"/></td>
								<td><input class="dis_${item.id}" id="modulPath_${item.id}" readonly="readonly" disabled="disabled" value="${item.modulPath}" style="width:100%;"/></td>
								<td>
									第
									<select class="dis_${item.id}" disabled="disabled" id="type_${item.id}" onchange="query('${item.id}')">
									<c:forEach begin="1" end="3" step="1" var="mi">
										<option  value="${mi}" <c:if test='${item.type eq mi}'>selected='selected'</c:if> >${mi}</option>
									</c:forEach>
									</select>
									级
								</td>
								<td id="td_${item.id}">
								<c:choose>
									<c:when test="${0 eq item.type-1}">
									无
										<%-- <input class="dis_${item.id}" id="modulParent_${item.id}" readonly="readonly" disabled="disabled" value="${item.modul.modulName}" style="width:100%;"/> --%>
									</c:when>
									<c:otherwise>
										<select class="dis_${item.id}" disabled="disabled" id="modulParent_${item.id}">
										<c:forEach items="${list}" var="mitem">
											<c:if test='${mitem.type eq item.type-1}'>
												<option  value="${mitem.id}" <c:if test='${item.modul.modulName eq mitem.modulName}'>selected='selected'</c:if> >${mitem.modulName}</option>
											</c:if>
										</c:forEach>
										</select>
									</c:otherwise>
								</c:choose>
									
								</td>
								<td><input class="dis_${item.id}" id="sort_${item.id}" readonly="readonly" disabled="disabled" value="${item.sort}" style="width:100%;"/></td>
								<td>
									<input class="dis_${item.id}" id="note_${item.id}" readonly="readonly" disabled="disabled" value="${item.note}" style="width:100%;"/>
								</td>
								<td>
								<c:if test="${usersAdmin.userType eq '-1'}">
									<a id="modify_${item.id}" class='a' href="javascript:void(0);" onclick="updateModul('${item.id}')">修改</a>
								</c:if>
								<c:if test="${usersAdmin.userType ne '-1'}">
									<span>无权限修改</span>
								</c:if>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<c:choose>
	<c:when test="${list ne '[]' &&  list ne '' && list ne null}">
		</c:when>
		<c:otherwise>
			<div style="text-align: center;margin-top: 15px;"><!-- <img src="images/lh.jpg"> -->
	  			<img src="../images/no_record.png" />
	 	 	</div>
		</c:otherwise>
	</c:choose>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		function updateModul(id){
			var status = $("#modify_"+id).text();
			if("修改"==status){
				//准备修改;开启修改模式;
				if("-1"!="${usersAdmin.userType}"){
					alert("没有操作权限，请联系超级管理员！");
					return false;
				}
				$("#modify_"+id).text("保存");
				$(".dis_"+id).removeAttr("disabled");
				$(".dis_"+id).removeAttr("readonly");
			}else{
				//准备保存;开启保存模式;
				$("#modify_"+id).text("修改");
				
				//获取要修改的值;
				var modulName = $("#modulName_"+id).val();
				var modulPath = $("#modulPath_"+id).val();
				var type = $("#type_"+id).val();
				var modulParent = $("#modulParent_"+id).val();
				var sort = $("#sort_"+id).val();
				var note = $("#note_"+id).val();
				
				var url = "${pageContext.request.contextPath}/Product/Admin/modul!modifyModul.action?"; 
				var formData ="modul.id="+id;
					formData+="&modul.modulName="+modulName;
					formData+="&modul.modulPath="+modulPath;
					formData+="&modul.type="+type;
					if(type!=1){
						formData+="&modul.parentId="+modulParent;
					}
					formData+="&modul.sort="+sort;
					formData+="&modul.note="+note;
					$(".dis_"+id).attr("disabled","disabled");
					$(".dis_"+id).attr("readonly","readonly");
					//alert(formData);
					//return false;
				 $.post(url,formData,function(data){
					 alert(data.json);
					 if(data.status=="ok"){
						 location.reload();
					 }
				 }); 
			}
		}
		
		function query(id){
			var type=$("#type_"+id).val(); 	
			//var modualName =$("#modulName_"+id).val();
			var td = $("#td_"+id);
			if(type!='1'){
				$("#modulParent_"+id+" option").remove();
				var url = "${pageContext.request.contextPath}/Product/Admin/modul!findModulByType.action?"; 	
				var su = "";
				$.ajax({
					type : "post",
					url : url,
					async: true,
					data:"type="+(parseInt(type)-1),
					success : function(data, textStatus) {
						su = data;
					},
					complete : function(XMLHttpRequest, textStatus) {
						if ("ok" == su.status) {
							var list=su.json;
							var newOption = '<select class="dis_'+id+'"  id="modulParent_'+id+'">';
							for ( var i = 0; i < list.length; i++) {
								var modul = list[i];
								//$("#modulParent_"+id).append("<option value='"+modul.id+"'>"+modul.modulName+"</option>");
								newOption+="<option value='"+modul.id+"'>"+modul.modulName+"</option>";
							}
							newOption+='</select>';
							td.html(newOption);
						}
					}
				});
			}else{
				//var parentInp = '<input class="dis_"'+id+' id="modulParent_'+id+' " value="'+modualName+'" style="width:100%;"/>';
				td.html("无");
			}
		}
		</script>
</html>