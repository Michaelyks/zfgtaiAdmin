<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div style="bordre:1px solid #ddd">
<div class="mainleft fl" style="min-height:500px;">
    	<ul>
        	<li id="leftMenu_account" class="menu1 on">
            	<h2><i></i>账户管理</h2>
                <ul>
                	<li><a href="${pageContext.request.contextPath}/Product/User/myAccount!showMyAccount.action">我的钱包</a></li>
                  	<li><a href="${pageContext.request.contextPath}/Product/User/myCoupon!loadCoupon.action">我的投资券</a></li>
                    <li><a href="${pageContext.request.contextPath}/Product/User/userRecharge!showUsersMoney.action">充值</a></li>
					<li><a href="${pageContext.request.contextPath}/Product/User/userRecharge!getMoney.action">提现</a></li>
                    <li><a href="${pageContext.request.contextPath}/Product/User/fundRecord!loadFundRecord.action?type=all">资金流水</a></li>
                    <!-- li><a href="${pageContext.request.contextPath}/Product/User/productMingxi.jsp">产品明细</a></li -->
                </ul>
            </li>
			<li id="leftMenu_invest" class="menu4">
            	<h2><b></b>投资中心</h2>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/Product/User/investorsRecord!getInvestorsRecords.action">投资记录</a></li>
                    <%--<!--已有页面  <li><a href="${pageContext.request.contextPath}/Product/User/buyCar!loadInvestors.action">购物车</a></li>-->--%>
                    <li><a href="${pageContext.request.contextPath}/Product/User/investorsRecord!getInvestorsRunningRecords.action">流水记录</a></li>
                </ul>
            </li>
            <li id="leftMenu_userInfo" class="menu3">
            	<h2><s></s>用户信息</h2>
                <ul>
                	<li><a href="${pageContext.request.contextPath}/Product/User/userInfo!queryUsersInfoByid.action">基本资料</a></li>
                    <li><a href="${pageContext.request.contextPath}/Product/User/validate!getValidataInfo.action">认证资料</a></li>
                </ul>
            </li>
        </ul>
    </div>
 </div>   
    <script type="text/javascript">
    $(function(){
    	var ss = window.location.href.split("/").pop();
    	//alert(ss.indexOf("getInvestorsRecords"));
        var len = 0; 
   		if(ss.indexOf("myAccount")==0){
   			
    	}
   		else if(ss.indexOf("myCoupon")==0){
   			len = 1;
   		}
		else if(ss.indexOf("showUsersMoney")>=0){
			len = 2;		
		   		}
		else if(ss.indexOf("getMoney")>=0){
			len = 3;	
			}
		else if(ss.indexOf("fundRecord")==0){
			len = 4;	
			}
		else if(ss.indexOf("getInvestorsRecords")==16){
			len = 5;	
			}
		else if(ss.indexOf("getInvestorsRunningRecords")==16){
			len = 6;	
			}
		else if(ss.indexOf("userInfo")==0){
			len = 7;	
			}
		else if(ss.indexOf("validate")==0){
			len = 8;	
			}
   		$('.mainleft a').eq(len).addClass("l_on");
    });
    function chooseMenu(menu) {
		if ("1" == menu) {
			$("#leftMenu_account").addClass('on').siblings().removeClass('on');
		} else if ("2" == menu) {
			$("#leftMenu_invest").addClass('on').siblings().removeClass('on');
		} else if ("3" == menu) {
			$("#leftMenu_userInfo").addClass('on').siblings().removeClass('on');
		}else if("4" == menu){
			$("#leftMenu_account").addClass('on').siblings().removeClass('on');
		}
	}
    </script>