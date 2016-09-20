<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/Product/js/alert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Product/css/alert.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:action name="loginBackground!getRolesRight" namespace="/Product" />
</head>
<body>
	<div class="logo">
		<img src="${pageContext.request.contextPath}/Product/Admin/img/logo.png"/><span>深圳中泰理财金融服务有限公司</span>
		 <p align="right" style="margin-right: 20px;padding-right: 20px;font-size: 16px;font-weight: 1">你好,${myel:jieMiUsername(usersAdmin.username)}	</p>
		
	</div>
	<div class="head">
		<ul class="head_list">
		<%-- <li><a href="${pageContext.request.contextPath}/Product/Admin/login/welcome.jsp">首页</a></li> --%>
		<!-- 遍历一级标题 -->
		<c:forEach items="${firstModul}" var="fm">
				<!-- 一级标题 -->
				<li>
				<c:choose>
					<c:when  test="${'' ne fm.modulPath && fm.modulPath ne '#'}">
						<a href="${pageContext.request.contextPath}${fm.modulPath}">${fm.modulName}</a>
					</c:when>
					<c:otherwise>
						<a href="#">${fm.modulName}</a>
					</c:otherwise>
				</c:choose>
				<ul class="hide_list">
				<c:forEach items="${userModul}" var="um">
					<c:choose>
						<c:when test="${fm.id eq um.parentId && um.type eq 2 && um.modulPath eq '#' }">
						<!-- 二级标题 -->
							<li>
								<a href="#">${um.modulName}</a>
								<ul class="hide2_list" style="z-index: 10;">
								<c:forEach items="${userModul}" var="um2">
									<c:if test="${um2.type eq 3 && um2.parentId eq um.id}">
									<!-- 三级标题 -->
									<li><a href="${pageContext.request.contextPath}${um2.modulPath}">${um2.modulName}</a></li>
									</c:if>
								</c:forEach>
								</ul>
							</li>
						</c:when>
						<c:when test="${fm.id eq um.parentId && um.type eq 2 && um.modulPath ne '#' }">
							<li><a href="${pageContext.request.contextPath}${um.modulPath}">${um.modulName}</a></li>
						</c:when>
					</c:choose>
				</c:forEach>
					<div class="clea"></div>
				</ul>
				</li>
		</c:forEach>
				<%-- <a href="#">产品管理1</a>um.id eq modulId eq fm.id && 
				<ul class="hide_list">
					<li>
						<a href="#">产品发布2</a>
						<ul class="hide2_list">
							<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productSend.action">产品统计3</a></li>
						</ul>
					</li>
					<li><a href="#">产品台账2</a>
						<ul class="hide2_list">
							<li><a href="${pageContext.request.contextPath}/Product/Admin/interestDetails!findInvertors.action">结算详单3</a></li>
						</ul>
					</li>
					<!-- <li><a href="#">产品结算</a></li> -->
					<div class="clea"></div>
				</ul>
			</li> --%>
			
			<%-- for 所有权限;modulList
			for 用户拥有的权限
			if modul.type == rolesModul.type == 1
			<li>
				<a href="#">账号管理</a>
				<ul class="hide_list">
				for 用户拥有的权限
				if(rolesModul.type == 2 && modulPath == "#"){
					<li>
						<a href="#">产品发布2</a>
						<ul class="hide2_list">
							遍历3级菜单;
							for 用户拥有的权限 rolesModul.type == 3
							<li><a></a></li>
						</ul>
						<div class="clea"></div>
					</li>
						
				}else if(type == 2){
					<ul class="hide_list">
						遍历2级菜单;
						for 用户拥有的权限 rolesModul.type == 2
						<li><a></a></li>
					<div class="clea"></div>
					</ul>
				}
				</ul>
			</li> -->
			<%-- <li><a href="${pageContext.request.contextPath}/Product/Admin/login/welcome.jsp">首页</a></li>
			<li>
				<a href="#">产品管理</a>
				<ul class="hide_list">
					<li>
						<a href="#">产品发布</a>
						<ul class="hide2_list">
							<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productSend.action">产品统计</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productAudit.action">产品审核</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!sendProduct.action">发布常规产品</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseFreshmanProduct!sendProduct.action">发布新手产品</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productRecord.action">产品历史记录</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productList.action">查看发布产品</a></li>
						</ul>
					</li>
					<li><a href="#">产品台账</a>
						<ul class="hide2_list">
							<!-- <li><a href="#">结算历史记录</a></li> -->
							<li><a href="${pageContext.request.contextPath}/Product/Admin/interestDetails!findInvertors.action">结算详单</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/investors!findInvertors.action?status=all">投资记录</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/investors!platInverstors.action">投资统计</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productFx.action">付息总表</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/interestDetails!findInvertorsByProduct.action">付息明细表</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!findZjsdmx.action">资金速动明细表</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/releaseProduct!productZS.action">在售项目</a></li>
							<li><a href="${pageContext.request.contextPath}/Product/Admin/virtualInsRecord!findvirtualIns.action">虚拟投资列表</a></li>
						</ul>
					</li>
					<!-- <li><a href="#">产品结算</a></li> -->
					<div class="clea"></div>
				</ul>
			</li>
			<li>
				<a href="#">运营管理</a>
				<ul class="hide_list">
					<!-- <li><a href="#">运营首页</a></li> -->
					<li><a href="${pageContext.request.contextPath}/Product/Admin/activity!loadQdtj.action">渠道统计汇总表</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/activity!loadQdcb.action">渠道成本</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/activity!loadActivityStat.action">入口统计</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUsersStat.action">平台注册人数</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/userStat!platUser.action">注册人数日统计</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/investors!platResUserInverstors.action">注册当日投资统计</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/platfromInversors!loadPlatfromInversors.action">投资情况</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/usersConvert!loadUsersConvert.action">用户转换数据</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/recharge!loadUserCzTx.action">充值提现数据报表</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/recharge!CapitalRecord.action">资金流水</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/usersConvert!loadOperation.action">运营数据</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/usersComment!showUsersComment.action">用户评论</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/userStat!loadProvince.action">地域统计</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/operationManager/bindInfo.jsp">绑卡人信息</a></li>
					<!-- <li><a href="#">访问统计</a></li>
					<li><a href="#">资金流水</a></li>
					<li><a href="#">用户转化</a></li>
					<li><a href="#">用户行为</a></li> -->
					<div class="clea"></div>
				</ul>
			</li>
			<li>
				<a href="#">账号管理</a>
				<ul class="hide_list">
					<li><a href="${pageContext.request.contextPath}/Product/Admin/bankCard/unbindBankCard.jsp">解绑银行卡</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/userStat!loadUserInfo.action">注册用户信息</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/usersConvert!userInfo.action">移动用户信息</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/usersConvert!allMobile.action">所有手机用户信息</a></li>
					<div class="clea"></div>
				</ul>
			</li>
			<li>
				<a href="#">banner管理</a>
				<ul class="hide_list">
					<li><a href="${pageContext.request.contextPath}/Product/Admin/bannerManager/repleaseBanner.jsp">发布banner</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/banner!loadBanner.action">banner展示</a></li>
					<div class="clea"></div>
				</ul>
			</li>
			<li>
				<a href="#">推送消息</a>
				<ul class="hide_list">
					<li><a href="${pageContext.request.contextPath}/Product/Admin/pushMessage/pushMessage.jsp">推送消息编辑</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/pushMessage!loadPushMessage.action">推送消息展示</a></li>
					<div class="clea"></div>
				</ul>
			</li>
			<li>
				<a href="#">资金管理</a>
				<ul class="hide_list"> 
				<li><a href="${pageContext.request.contextPath}/Product/Admin/recharge!weekLeftMoney.action">一周不可动金额</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/fundsManager/recharge.jsp">用户充值</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/recharge!platCzMoney.action">充值记录统计</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/recharge!platTxMoney.action">提现记录统计</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/fundsManager/sendCoupon.jsp">发送投资卷</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/fundsManager/sendCouponByIsBindBank.jsp">发送群体投资卷</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/sendCoupon!couponRecord.action">投资卷记录</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/checkTxsq!loadTxsq.action?status=all">提现记录</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/recharge!rechargeMoneyRecord.action?status=all">充值记录</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/fundRecord!queryFundRecord.action?status=all">用户资金流水</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/recharge!platLeftMoney.action">平台可用余额</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/ranking!showInvestorsRank.action">金额排行榜</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/querybalance!queryBalance.action">亿美当前余额</a></li>
					<div class="clea"></div>
				</ul>
			</li>
			<li>
				<a href="#">公告管理</a>
					<ul class="hide_list">
						<li><a href="${pageContext.request.contextPath}/Product/Admin/notice!findNotice.action">公告展示</a></li>
						<li><a href="${pageContext.request.contextPath}/Product/Admin/noticeManager/notice.jsp">发布公告</a></li>
						<div class="clea"></div>
					</ul>
			</li>
			<li>
				<a href="#">系统配置管理</a>
					<ul class="hide_list">
						<li><a href="${pageContext.request.contextPath}/Product/Admin/systemConfig!toSystemConfig.action">系统配置</a></li>
						<div class="clea"></div>
					</ul>
			</li>
			<li>
				<a href="#">功能管理</a>
				<ul class="hide_list">
					<li><a href="${pageContext.request.contextPath}/Product/Admin/functionManager/function.jsp">功能操作</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/dept!findDeptList.action">部门列表</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/functionManager/deptAdd.jsp">添加部门</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/usersAdmin!findUsersAdminList.action">管理员列表</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/usersAdmin!toUsersAdminAdd.action">添加管理员</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/modul!findModul.action">模块列表展示</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/functionManager/replaseFunction.jsp">添加新模块</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/roles!findRoles.action">角色列表展示</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/functionManager/rolesAdd.jsp">添加新角色</a></li>
					<div class="clea"></div>
				</ul>
			</li>
			<li>
				<a href="#">客户端版本管理</a>
				<ul class="hide_list">
					<li><a href="${pageContext.request.contextPath}/Product/Admin/clientVersionManager/repleaseClienVersion.jsp">发布新版本</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/Admin/clientVersion!loadClientVersion.action">版本展示</a></li>
					<div class="clea"></div>
				</ul>
			</li>
			<!-- <li>
				<a href="#">客服管理</a>
				<ul class="hide_list">
					<li><a href="#">客服首页</a></li>
					<li><a href="#">客户访谈记录</a></li>
					<li><a href="#">用户在线反馈</a></li>
					<li><a href="#">调查问卷</a></li>
					<li><a href="#">客服数据统计</a></li>
					<div class="clea"></div>
				</ul>
			</li> -->
			<li>
				<a href="#">个人中心</a>
				<ul class="hide_list">
					<!-- <li><a href="#">消息中心</a></li> -->
					<li><a href="${pageContext.request.contextPath}/Product/Admin/userAdmin/modifuPwd.jsp">密码修改</a></li>
					<!-- <li><a href="#">登录记录</a></li>			 -->			
					<div class="clea"></div>
				</ul>
			</li>
 --%>
			<div class="clea"></div>
		</ul>
		<a href="${pageContext.request.contextPath}/Product/loginBackground!exitLogin.action">退出</a>
		<div class="bread">我的位置：首页&gt;</div>				
		<script>
			$(function(){
				$(".head_list > li").mouseover(function(){
					$(this).children(".hide_list").css("display","block");
				}).mouseout(function(){
					$(this).children(".hide_list").css("display","none");
				});
				
				$(".hide_list > li").mouseover(function(){
					$(this).children(".hide2_list").css("display","block");
				}).mouseout(function(){
					$(this).children(".hide2_list").css("display","none");
				});
				
			})
			$("a").click(function(){
				i=0;
				var href =$(this).attr("href");
				if(href!='#'){
					var text = "<img src='${pageContext.request.contextPath}/Product/images/progress.gif' width='24' height='24'> <span style='font-family:楷体;font-size:22px;' id='my_alter_span'>正在加载,请稍等...</span>";
					var id = chuangkou(text,"正在加载...","",0,true);
					setInterval("sTime()",100);
				}
			});
			var i=0;
			function sTime(){
				i++;
				$("#my_alter_span").html("正在加载,请稍等...<br>耗时"+(i/10)+"秒");
				//console.log("i: "+i);
			}
		</script>
	</div>	
</body>
</html>