<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<!doctype html>
<html lang="ko">
<head>
<%@ include file="/iWORK/conservatory/include/meta.jsp"%>
<%@ include file="/iWORK/conservatory/include/link.jsp"%>
<link href="/css/front/sub.css" rel="stylesheet" type="text/css">
<%@ include file="/iWORK/conservatory/include/script.jsp"%>
</head>
<body>
	<%@ include file="/iWORK/conservatory/include/u_skip.jsp"%>
	<%@ include file="/iWORK/conservatory/include/header.jsp"%>
	<!-- //header -->
	<div id="container">
		<div class="sub_container">
			<section>
				<article>
					<div class="page_header visual<fmt:formatNumber value="${menuCount}" minIntegerDigits="2"/>">
						<div class="page_header_inner">
							<%@ include file="/iWORK/conservatory/include/location.jsp"%>
							<h2 class="gnb_depth1_tit"><c:out value="${curTitle }"/></h2>
						</div>
					</div>
				</article>
				<article class="page_conts">
					<div class="page_conts_tit">
						<h3 class=""><c:out value="${curTitle }"/></h3>
					</div>
					<jsp:include page="/iWORK/conservatory/include/post_tabs.jsp" />
					<!-- // post_tabs -->
					<!-- 원서접수확인 -->
					<div class="confirm_box">
						<div class="box_inner">
							<div class="check_icon"><span class="ico_check"></span></div>
							<div class="check_txt">글로벌문화예술교육원 <strong class="fc0">계열별 원서접수 현황</strong>을 확인하세요.</div>
							<div class="check_btn"><a href="https://lifeiis.chugye.ac.kr/2010/admission/check_accept.asp?chasu=${not empty majorApplicationData.applicationCode ? majorApplicationData.applicationCode : data.applicationCode }" target="_blank" title="새창으로 이동" class="bt_apply">인터넷 원서접수 확인</a></div>
						</div>
					</div>
					
					<div class="post_tbl">
						<div class="post_view">
							<div class="post_view_head mb40">
								<table class="grid">
									<caption>원서접수확인</caption>
									<colgroup><col width="20%"><col width="80%"></colgroup>
									<thead>
										<tr>
											<th colspan="2" class="tl"><span>${aboutData.title }</span></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th><span>원 서 접 수</span></th>
											<td><span>${not empty majorApplicationData.applicationPeriod ? majorApplicationData.applicationPeriod : data.applicationPeriod }</span></td>
										</tr>
										<tr>
											<th><span>입 시 전 형</span></th>
											<td><span>${not empty majorApplicationData.entrancePeriod ? majorApplicationData.entrancePeriod : data.entrancePeriod }</span></td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- //post_view_head -->
						</div>
						<!-- //post_view -->
						
					</div>
					<!-- //post_tbl -->
				</article>
				<!-- // page_conts -->
			</section>
		</div>
		<!-- //sub_container -->
		<aside>
			<%@ include file="/iWORK/conservatory/include/floatMenu.jsp"%>
		</aside>
	</div>
	<!-- // container -->
	
	<%@ include file="/iWORK/conservatory/include/footer.jsp"%>
</body>
</html>