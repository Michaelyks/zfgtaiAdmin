
	$(function(){
		var i ="";
		i +='<div class="logo">';
		i +='<img src="/wgtz/Product/Admin/img/logo.png"/><span>深圳中泰理财金融服务有限公司</span>';
		i +='</div>';
		i +='<div class="head">';
		i +='<ul class="head_list">';
		i +='<li><a href="/wgtz/Product/Admin/product/index_back.html">首页</a></li>';
		i +='<li>';
		i +='<a href="#">产品管理</a>';
		i +='<ul class="hide_list">';
		i +='<li>';
		i +='<a href="/wgtz/Product/Admin/releaseProduct!productSend.action">产品发布</a>';
		i +='<ul class="hide2_list">';
		i +='<li><a href="/wgtz/Product/Admin/releaseProduct!sendProduct.action">发布常规产品</a></li>';
		i +='<li><a href="/wgtz/Product/Admin/releaseFreshmanProduct!sendProduct.action">发布新手产品</a></li>';
		i +='<li><a href="/wgtz/Product/Admin/releaseProduct!productRecord.action">产品历史记录</a></li>';
		i +='</ul>';
		i +='</li>';
		i +='<li>';
		i +='<a href="#">产品台账</a>';
		i +='<ul class="hide2_list">';
		i +='<li><a href="#">结算历史记录</a></li>';
		i +='<li><a href="/wgtz/Product/Admin/interestDetails!findInvertors.action">结算祥单</a></li>';			
		i +='</ul>';
		i +='</li>';
		i +='<li><a href="#">产品结算</a></li>';
		i +='<div class="clea"></div>';
		i +='</ul>';
		i +='</li>';
		i +='<li>';
		i +='<a href="#">运营管理</a>';
		i +='<ul class="hide_list">';
		i +='<li><a href="#">运营首页</a></li>';
		i +='<li><a href="#">访问统计</a></li>';
		i +='<li><a href="#">资金流水</a></li>';
		i +='<li><a href="#">用户转化</a></li>';
		i +='<li><a href="#">用户行为</a></li>';
		i +='<div class="clea"></div>';
		i +='</ul>';
		i +='</li>';
		i +='<li>';
		i +='<a href="#">客服管理</a>';
		i +='<ul class="hide_list">';
		i +='<li><a href="#">客服首页</a></li>';
		i +='<li><a href="#">客户访谈记录</a></li>';
		i +='<li><a href="#">用户在线反馈</a></li>';
		i +='<li><a href="#">调查问卷</a></li>';
		i +='<li><a href="#">客服数据统计</a></li>';
		i +='<div class="clea"></div>';
		i +='</ul>';
		i +='</li>';
		i +='<li>';
		i +='<a href="#">资金管理</a>';
		i +='<ul class="hide_list">';
		i +='<li><a href="/wgtz/Product/Admin/fundsManager/recharge.jsp">用户充值</a></li>';
		i +='<li><a href="/wgtz/Product/Admin/fundsManager/sendCoupon.jsp">发送投资卷</a></li>';
		i +='<li><a href="/wgtz/Product/Admin/checkTxsq!loadTxsq.action?status=all">审核提现</a></li>';
		i +='<li><a href="/wgtz/Product/Admin/recharge!rechargeMoneyRecord.action?status=all">充值记录</a></li>';
		i +='<div class="clea"></div>';
		i +='</ul>';
		i +='</li>';
		i +='<li>';
		i +='<a href="#">公告管理</a>';
		i +='<ul class="hide_list">';
		i +='<li><a href="/wgtz/Product/Admin/noticeManager/notice.jsp">发布公告</a></li>';
		i +='<div class="clea"></div>';
		i +='</ul>';
		i +='</li>';
		i +='<li>';
		i +='<a href="#">功能管理</a>';
		i +='<ul class="hide_list">';
		i +='<li><a href="/wgtz/Product/Admin/functionManager/function.jsp">功能操作</a></li>';
		i +='<div class="clea"></div>';
		i +='</ul>';
		i +='</li>';
		i +='<li>';
		i +='<a href="#">个人中心</a>';
		i +='<ul class="hide_list">';
		i +='<li><a href="#">消息中心</a></li>';
		i +='<li><a href="#">密码安全</a></li>';
		i +='<li><a href="#">登录记录</a></li>';			
		i +='<div class="clea"></div>';
		i +='</ul>';
		i +='</li>';
		i +='<div class="clea"></div>';
		i +='</ul>';
		i +='<a href="/wgtz/Product/loginBackground!exitLogin.action">退出</a>';
		i +='<div class="bread">我的位置：首页&gt;</div>';				
		i +='</div>';
		$(i).prependTo(".center");
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