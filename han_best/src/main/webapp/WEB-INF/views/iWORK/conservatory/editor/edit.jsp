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
	injectFile("/iWORK/conservatory/js/editor/record.js");
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
					<div class="post_tbl">
						<div class="post_form">
							<form action="/Editor/Record/insertProcData.do" name="fm" id="fm" method="post">
							<input type="hidden" name="menuSeq" value="<c:out value="${menuSeq}"/>" readonly="readonly">
							<input type="hidden" name="configSeq" value="<c:out value="${dataConfig.seq}"/>" readonly="readonly">
							<ul class="post_form_head">
								<li class="post_form_item">
									<div class="form_title">선택한 내용</div>
									<div class="form_item">
										<select name="seq" id="seq" title="구분" class="p100 txt_field">
											<option value="">페이지 편집 히스토리</option>
											<c:forEach var="data" items="${dataList }" varStatus="status">
												<option value="<c:out value="${data.seq}" />" <c:if test="${status.first }">selected="selected"</c:if>>${status.count }_<c:out value="${data.regDateFmt}" /></option>
											</c:forEach>
										</select>
										<button type="button" name="" id="" class="bt_inner" data-itsp-data-choice>불러오기</button>
									</div>
								</li>
							</ul>
							<div class="post_view_body">
								<div class="post_body_cke">
									<textarea name="contents" id="contents" rows="5" class="p100" data-itsp-title="내용" data-itsp-html-editor><c:out value="${cmutls:removeScript(dataList[0].contents) }"/></textarea>
								</div>
							</div>
							</form>
						</div>
						<!-- //post_form -->
					</div>
					<!-- //post_tbl -->

					<div class="post_bottom">
						<div class="ls"></div>
						<div class="rs">
							<c:if test="${not empty sessionScopeMember}">
								<div class="bt_list">
									<ul>
										<li><label for="" class="blind">취소</label><button type="button" name="" id="" class="bt bt_nor" data-itsp-list-link="${configSeq }" data-itsp-menuSeq="${menuSeq }">취소</button></li>
										<li><label for="" class="blind">저장</label><button type="button" name="" id="" class="bt bt_on" data-itsp-save-link>저장</button></li>
									</ul>
								</div>
							</c:if>
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
	<%@ include file="/iWORK/conservatory/include/footer.jsp"%>
</body>
</html>