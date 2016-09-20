<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>中泰理财 - 团队介绍</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Product/images/favicon.ico" type="image/x-icon">
		<link href="${pageContext.request.contextPath}/Product/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/Product/css/repayment_tip.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.request.contextPath}/Product/js/jquery-1.9.1.min.js"></script>
	</head>
	<body>
		<jsp:include page="header.jsp" />
		<div class="main">
			<div class="layout gonggao">
				<jsp:include page="notice_left_memu.jsp" />
				<div class="fr g_content">
					<p class="g_title">团队介绍</p>
					<ul class="conta_us my_team">					
						<li>
							<div class="fl">
								<img src="images/aboutus_cto.png" />
								<h4>方锋杰</h4>
								<span>首席风控官     CRO</span>
								<p>中泰理财联合创始人，高级合伙人，拥有超过二十年创业及企业管理实践经验，成功从实业运营到资本运作的转型经验，多年企业资本运作及参与私募股权投资，熟悉互联网金融运作，对互联网战略发展方向独特的见解。运用自己丰富的风控管理经验，加强平台风控能力。帮助平台完善风控体系建设，给投资人带来更安全的投资环境。</p>
							</div>
							<div class="fr">
								<img src="images/aboutus_ceo.png" />
								<h4>陈恺</h4>
								<span>首席执行官     CEO</span>
								<p>中泰理财联合创始人，中泰理财金融服务有限公司CEO， 曾先后任职于杭州掘宝科技有限公司、杭州恩科科技有限公司、浙江草根网络科技有限公司、广州菁杉商贸有限公司等知名科技互联网贸易公司，拥有多年的实业+互联网金融、项目风险控制经验，熟悉国内外贸易进出口业务，资产配置，投资理财，在互联网营销以及用户运营领域积累了丰富的实战经验，曾管理的团队业绩优秀，曾管理的资产规模超过10亿元，致力于中国互联网金融改革浪潮。</p>
							</div>
						</li>
						<li>
							<div class="fl">
								<img src="images/aboutus_coo.png" />
								<h4>叶峰</h4>
								<span>首席运营官     COO</span>
								<p>中泰理财联合创始人，中泰理财金融服务有限公司COO，拥有强大海外背景和跨界资源整合能力，10年以上的金融投融资经验，长期致力于私募基金的设立与运营，互联网金融，公司股份制改造，上市公司重大资产重组，上市公司非公开发行，并购重组、私募投资，发行非金融企业债务融资工具。先后任职于国内外知名投资公司，擅长项目投融资业务，地产私募，过桥业务、银行渠道的企业贷款等。参与成立过多家私募基金公司以及互联网金融公司，其参与设立的私募基金公司和互联网金融公司发展运作优秀。</p>
							</div>
							<div class="fr" id="qwy">
								<img src="images/aboutus_cto.png" />
								<h4>qwy</h4>
								<span>首席技术官     CTO</span>
								<p>拥有多年互联网工作经验，对互联网及移动互联网的产品研发有充分的了解，曾任职于金蝶国际软件集团等知名科技企业，熟悉互联网金融项目的后台开发，包括项目核心技术开发。致力于计算机体系结构和网络存储技术的研究和实践，并积极地制订平台有关技术的愿景和战略。拥有品牌的调研、筹建、运营等方面具有的丰富实战经验。</p>
							</div>
							
						</li>	
						<li>
							<div class="fl">
								<img src="images/aboutus_ceo.png" />
								<h4>王心仪</h4>
								<span>产品中心总监     CPO</span>
								<p>曾先后历任于财富资产管理公司，擅长于企业融资、并购和私募基金，互联网金融产品，金融衍生品的研发与发行，曾参与过光伏新能源基金，保理基金、影视基金，影院基金，一线城市保障房项目发起成立等多个项目。熟悉资产证券化，互联网金融等模式，在品牌推广及战略规划、投资领域拥有丰富的理论和实战经验。</p>
							</div>
							<div class="fr">
								<img src="images/aboutus_ceo.png" />
								<h4>徐洪发</h4>
								<span>互联网产品总监     CPO</span>
								<p>管理学与经济学双学士，曾任职于金蝶集团、用友集团等知名IT上市公司，从事互联网产品、实施及相关管理工作多年，擅长互联网产品整体架构分析设计和团队建设，曾参与华侨城集团，金地集团等多个大型项目，具有丰富的项目管理经验、团队管理经验以及商务谈判能力。</p>
							</div>
						</li>
						<div class="clear"></div>				
					</ul>
				</div>	
				<div class="clea"></div>
			</div>
		</div>	
		<jsp:include page="footer.jsp" />
		<script>
			ab_left_memu(1);
		</script>
	</body>
</html>