<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
     <%@ taglib uri="/WEB-INF/zlqEl.tld" prefix="myel"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 招兵买马</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/repayment_tip.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
		<style type="text/css">
		.emplo_hid P span{
		font-family: 宋体;
		font-size: 12px;
		}
		</style>
	</head>
	<body>
		<jsp:include page="header.jsp" />
		<div class="main">
			<div class="layout gonggao">
				<jsp:include page="notice_left_memu.jsp" />
				<div class="fr g_content">
					<p class="g_title">招兵买马</p>
					<div class="emplo">
						<p class="emplo_dec">中泰理财视员工为企业的第一财富。我们为员工营造了轻松愉快的工作环境、充足的学习和成长机会，以充分展现员工的才华。作为创业企业，我们愿与员工一起成长，共创互联网金融的美好未来。 请将您的简历通过电子邮件投递到mail.baiyimao.com ，邮件标题格式： 姓名-应聘职位-招聘信息来源，谢谢！</p>
					<dl class="zhaoping">
						<dt>高级java工程师</dt>
						<dd class="emplo_hid">
							<p>
								<span>高级工程师（java）：</span>
							</p>
							<p>
								<span>岗位要求：</span>
							</p>
							<p>
								<span>1、大专或以上学历，3年以上J2EE开发经验。</span>
							</p>
							<p>
								<span>2、对Java语言基础有较好的掌握（Collection、Reflect、IO、Thread）。</span>
							</p>
							<p>
								<span>3、掌握J2EE基础开发技术（Servlet、Filter、JDBC）。</span>
							</p>
							<p>
								<span>4、熟悉SpringMVC、Spring、Ibatis、Hibernate等开源框架，对上述一个以上框架有深入的理解。</span>
							</p>
							<p>
								<span>5、熟悉Oracle、MySQL数据库应用，掌握数据库优化者优先。</span>
							</p>
							<p>
								<span>6、熟悉UML和OOAD设计方法，有系统架构经验并对其有理解。</span>
							</p>
							<p>
								<span>7、能承受一定的工作压力，有责任心和上进心，能通过持续学习完善自身。</span>
							</p>
							<p>
								<span>8、从事过大规模Web应用开发，熟悉性能优化、系统安全者优先。</span>
							</p>
							<p>
								<span>9、对互联网金融行业有一定理解者优先。</span>
							</p>
							<p>
								<span>10、有较强的客户服务意识、团队沟通能力、项目管理经验者优先。</span>
							</p>
						</dd>
					</dl>
					<dl class="zhaoping">
						<dt>UI设计师</dt>
						<dd class="emplo_hid">
							<p>
								<span>UI设计师：</span>
							</p>
							<p>
								<span>一、职责描述：</span>
							</p>
							<p>
								<span>1、负责公司网站策划设计与实施。</span>
							</p>
							<p>
								<span>2、负责公司网页版面设计。</span>
							</p>
							<p>
								<span>3、负责公司App页面设计工作。</span>
							</p>
							<p>
								<span>二、职位要求：</span>
							</p>
							<p>
								<span>1、热爱设计工作，思维活跃，富有创意，表现能力强，能独立完成设计方案。</span>
							</p>
							<p>
								<span>2、平面设计、美术专业毕业或受过美术设计类培训。</span>
							</p>
							<p>
								<span>3、有良好的美术功底，审美能力和创意，色彩感强；具有较强的网页设计能力和平面艺术感悟能力。</span>
							</p>
							<p>
								<span>4、熟练使用photoshop，Iuustrator，flash等网页设计制作软件。</span>
							</p>
							<p>
								<span>5、有良好的学习能力，沟通能力和领悟能力，工作效率高，能够承受工作压力。</span>
							</p>
							<p> 
								<span>6、有良好的团队合作意识，耐心，诚恳，有强烈的责任心和积极主动的工作态度。</span>
							</p>
							<p>
								<span>7、期待优秀的你加入我们！</span>
							</p>
						</dd>
					</dl>
					</div>					
		           	<div style="text-align:center; margin-bottom: 20px;" >
						<jsp:include page="page.jsp" />
					</div>
				</div>
				<div class="clea"></div>
			</div>
		</div>	
		<jsp:include page="footer.jsp" />
		<script>
		ab_left_memu(8);
		$(function(){
			$(".zhaoping dt").click(function(){
				var fa = $(this).parents(".zhaoping");			
				$(".emplo").find(".sho").hide();
				if(fa.find(".emplo_hid").hasClass("sho")){
					$(this).css("background-position","-484px -96px");
					fa.find(".emplo_hid").hide();
					fa.find(".emplo_hid").removeClass("sho");				
				}
				else{
					$(this).css("background-position","-484px -135px");
					$(".emplo").find(".sho").removeClass("sho");
					fa.find(".emplo_hid").slideToggle("show");
					fa.find(".emplo_hid").addClass("sho");					
				}
			})
			
		})
		
		</script>
	</body>
</html>