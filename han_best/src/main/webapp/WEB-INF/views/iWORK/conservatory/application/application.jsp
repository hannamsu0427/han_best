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
					<div class="post_tbl">
						<div class="post_view">
							<div class="post_view_head">
								<table class="grid">
									<caption>원서접수</caption>
									<colgroup><col width="25%"><col width="75%"></colgroup>
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
							
							<div class="post_view_body">
								<div class="pdf_toolbar">
									<div class="bt_list">
										<a href="<c:url value="/file/fileDownLoad.do?seq=${majorApplicationData.attachFileList[0].seq}"/>" title="파일다운로드" class="bt_pdf pdf_download">PDF 모집요강 다운로드</a>
										<a href="https://lifeiis.chugye.ac.kr/2010/admission/application.asp?chasu=${not empty majorApplicationData.applicationCode ? majorApplicationData.applicationCode : data.applicationCode }" target="_blank" title="새창으로 이동" class="bt_apply">원서접수</a>
									</div>
								</div>
								<div class="pdf_viewer">
									<c:if test="${'pdf' eq majorApplicationData.attachFileList[0].fileType}">
										<iframe src="/PdfView.do?fileSeq=${majorApplicationData.attachFileList[0].seq}" width="100%" height="99.99%" frameborder="0"></iframe>
									</c:if>
								</div>
							</div>
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