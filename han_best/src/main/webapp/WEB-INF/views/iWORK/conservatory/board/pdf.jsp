<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<!doctype html>
<html lang="ko">
<head>
<%@ include file="/iWORK/conservatory/include/meta.jsp"%>
<%@ include file="/iWORK/conservatory/include/link.jsp"%>
<%@ include file="/iWORK/conservatory/include/script.jsp"%>
<script type="text/javascript">
	//app 전용 js는 injectFile로 호출할 것(이유 : 캐시 방지 코드를 붙임) 
	injectFile("/iWORK/conservatory/js/board/board.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<%@ include file="/iWORK/conservatory/include/u_skip.jsp"%>
	<%@ include file="/iWORK/conservatory/include/header.jsp"%>
	<!-- //header -->
	<div id="container">
		<div class="sub_container">
			<section>
				<article>
					<div class="page_header visual06">
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
					<%@ include file="/iWORK/conservatory/include/post_tabs.jsp"%>
					<!-- // post_tabs -->
					
					<div class="post_tbl">
						<div class="post_view">
							<div class="post_view_head">
								<ul class="noti_area">
									<li class="post_tit">
										<div class="subject"><a href="#" class="subject_click">${not empty data ? data.title : '데이터가 없습니다.' }</a></div>
									</li>
								</ul>
							</div>
							<c:if test="${not empty data }">
								<div class="post_view_body">
									<div class="pdf_toolbar">
										<div class="bt_list">
											<a href="<c:url value="/file/fileDownLoad.do?seq=${data.attachFileList[0].seq}"/>" title="파일다운로드" class="bt_pdf pdf_download">PDF 모집요강 다운로드</a>
										</div>
									</div>
									<div class="pdf_viewer">
										<c:if test="${'pdf' eq data.attachFileList[0].fileType}">
											<iframe src="/PdfView.do?fileSeq=${data.attachFileList[0].seq}" width="100%" height="99.99%" frameborder="0"></iframe>
										</c:if>
									</div>
								</div>
							</c:if>
						</div>
						<!-- //post_view -->
						
					</div>
					<!-- //post_tbl -->
					
					<div class="post_bottom">
						<div class="bt_list">
							<ul>
								<c:if test="${not empty sessionScopeMember}">
									<c:choose>
										<c:when test="${not empty data }">
											<li><label for="" class="blind">신규</label><button type="button" class="bt bt_on" data-itsp-edit-link="0">신규</button></li>
											<li><label for="" class="blind">수정</label><button type="button" class="bt bt_on" data-itsp-edit-link="<c:out value="${data.seq}"/>">수정</button></li>
											<c:choose>
												<c:when test="${'Y' eq dataConfig.delYn }">
													<li><label for="" class="blind">휴지통</label><button type="button" class="bt bt_on" data-itsp-delGb-link="<c:out value="${data.seq }"/>" data-itsp-delGb="Y">휴지통</button></li>
												</c:when>
												<c:otherwise>
													<li><label for="" class="blind">삭제</label><button type="button" class="bt bt_on" data-itsp-delete-link="<c:out value="${data.seq}"/>">삭제</button></li>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<li><label for="" class="blind">등록</label><button type="button" class="bt bt_on" data-itsp-edit-link="0">등록</button></li>
										</c:otherwise>
									</c:choose>
								</c:if>
							</ul>
						</div>
					</div>
					<!-- //post_bottom -->
					
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
	<!-- // container -->
	<%@ include file="/iWORK/conservatory/include/footer.jsp"%>
<jsp:include page="search.jsp" />
</body>
</html>