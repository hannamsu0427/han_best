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
							<form action="/Board/Record/saveProcData.do" name="fm" id="fm" method="post" enctype="multipart/form-data">
							<input type="hidden" name="seq" id="seq" value="<c:out value="${data.seq }"/>" readonly="readonly">
							<input type="hidden" name="configSeq" value="<c:out value="${BOARD_CONFIG_SEQ }"/>" readonly="readonly">
							<ul class="post_form_head">
								<c:choose>
									<c:when test="${not empty dataCategoryList}">
										<li class="post_form_item">
											<div class="form_title">카테고리</div>
											<div class="form_item">
												<select name="categorySeq" id="categorySeq" title="구분" class="p100 txt_field">
													<option value="">선택</option>
													<c:forEach items="${dataCategoryList }" var="category">
														<option value="${category.categorySeq}" <c:if test="${category.categorySeq eq data.categorySeq }">selected="selected"</c:if>>${category.categoryName}</option>
													</c:forEach>
												</select>
											</div>
										</li>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="categorySeq" id="categorySeq" value="<c:out value="${empty data ? BOARD_CATEGORY_SEQ : data.categorySeq }"/>" readonly="readonly">
									</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${'Y' eq dataConfig.noticeYn}">	
										<li class="post_form_item">
										<div class="form_title">공지여부</div>
											<div class="form_item">
												<ul class="dp_inline">								
													<li><input type="radio" name="noticeYn" id="noticeY" value="Y" <c:if test="${'Y' eq data.noticeYn }">checked="checked"</c:if> data-itsp-title="공지여부" data-itsp-required><label for="noticeY" class="pl5">공지</label></li>
													<li><input type="radio" name="noticeYn" id="noticeN" value="N" <c:if test="${'Y' ne data.noticeYn }">checked="checked"</c:if> data-itsp-title="공지여부" data-itsp-required><label for="noticeN"  class="pl5">일반</label></li>
												</ul>
											</div>
										</li>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="noticeYn" value="N" readonly="readonly">
									</c:otherwise>
								</c:choose>
								
								<c:if test="${'Y' eq dataConfig.secretYn }">
									<li class="post_form_item">
										<div class="form_title">공개여부</div>
										<div class="form_item">
											<ul class="dp_inline">
												<li><input type="radio" name="secretYn" value="N" id="secretN" <c:if test="${'N' eq data.secretYn }">checked="checked"</c:if> data-itsp-title="공개여부" data-itsp-required><label for="secretN" class="pl5">공개</label></li>
												<li><input type="radio" name="secretYn" value="Y" id="secretY" <c:if test="${'N' ne data.secretYn }">checked="checked"</c:if> data-itsp-title="공개여부" data-itsp-required><label for="secretY"  class="pl5">비공개</label></li>
											</ul>
										</div>
									</li>
								</c:if>
							
								<li class="post_form_item">
									<div class="form_title">제목</div>
									<div class="form_item"><input type="text" class="p100 txt_field" name="title" id="title" placeholder="제목을 입력하세요." value="<c:out value="${data.title }"/>" data-itsp-title="제목" data-itsp-required></div>
								</li>
								<c:if test="${not empty data && not empty sessionScopeMember }">
									<li class="post_form_item">
										<div class="form_title">작성일</div>
										<div class="form_item"><input type="text" class="p100 txt_field" placeholder="년-월-일" title="년-월-일" id="regDate" name="regDate" value="${data.regDate }" data-itsp-type-date></div>
									</li>
								</c:if>
								
								<c:if test="${'Y' eq dataConfig.nonMember && empty sessionScopeMember }">
									<li class="post_form_item">
										<div class="form_title">작성자</div>
										<div class="form_item"><input type="text" class="p100 txt_field" name="regName" id="regName" value="${data.regName }" placeholder="작성자" data-itsp-title="작성자" data-itsp-required></div>
									</li>
									<li class="post_form_item half">
										<div class="form_title">비밀번호</div>
										<div class="form_item"><input type="password" class="p100 txt_field" name="password" value="" <c:if test="${empty data }">data-itsp-title="비밀번호" data-itsp-required</c:if> data-itsp-len-range="4,12" placeholder="비밀번호는 4자이상 12자이하로 입력바랍니다."></div>
									</li>
									<li class="post_form_item half">
										<div class="form_title">비밀번호 확인</div>
										<div class="form_item"><input type="password" class="p100 txt_field" name="" id="" value="" ></div>
									</li>
								</c:if>
							
								<c:if test="${'movie' eq dataConfig.type }">
									<li class="post_form_item">
										<div class="form_title">youtube Url</div>
										<div class="form_item"><input type="text" class="p100 txt_field" placeholder="youtube 공유되는 url을 입력해주세요." title="youtube Url" id="url" name="url" value="${data.url }"></div>
									</li>
								</c:if>
							
								<c:if test="${'Y' eq dataConfig.fileYn }">
									<li class="post_form_item">
										<div class="form_title">첨부파일</div>
										<div class="form_item">
											<ul class="pb5" id="fileList">
												<c:if test="${not empty data.attachFileList}">
													<c:forEach items="${data.attachFileList }" var="file">
														<li class="add_field">
															<a href="<c:url value="/file/fileDownLoad.do?seq=${file.seq}"/>">${file.realFileName }</a>
															<ul class="add_edit">
																<li><a href="#" class="bt_inner" data-itsp-file-delete-link="${file.seq}">삭제</a></li>
															</ul>
														</li>
													</c:forEach>
												</c:if>
												<c:set var="configMapFileCnt" value="${'pdf' ne dataConfig.type ? dataConfig.fileCnt : '1'}" />
												<c:set var="fileCnt" value="${configMapFileCnt - fn:length(data.attachFileList)}" />
												<input type="hidden" name="fileCnt" id="fileCnt" value="${configMapFileCnt - fn:length(data.attachFileList)}"/>
												<c:if test="${fileCnt > 0}">
													<li class="add_field">
														<input type="file" name="file_1" id="file_1" class="txt_field"  <c:if test="${'pdf' eq dataConfig.type}">accept=".pdf"</c:if>>
														<input type="text" name="fileEtc_1" id="fileEtc_1" placeholder="첨부파일 설명글 또는 파일명" class="alt_txt">
														<c:if test="${'pdf' ne dataConfig.type }">
															<ul class="add_edit">
																<li><a href="#" class="bt bt_inner" data-itsp-fileAdd>추가</a></li>
															</ul>
														</c:if>
													</li>
												</c:if>
											</ul>
										</div>
									</li>
								</c:if>
							</ul>
							<c:if test="${'pdf' ne dataConfig.type }">
								<div class="post_view_body">
									<div class="post_body_cke">
										<textarea style="border:1px solid #afafaf" name="contents" id="contents" data-itsp-html-editor data-itsp-title="내용"><c:out value="${cmutls:removeScript(data.contents) }"/></textarea>
									</div>
								</div>
							</c:if>
							
							<c:if test="${empty sessionScopeMember ||  'Y' eq dataConfig.nonMember}">
								<div class="captcha_area">
									<ul class="">
										<li class="post_form_item">
											<div class="form_title" id="captcha"><img height="100%"></div>
											<div class="form_item">
												<div class="cmt_txt">문자와 숫자를 보이는 순서대로 공백없이 정확하게 입력해주세요.</div>
												<input type="text" class="txt_field" name="captchaInput" data-itsp-title="도용방지문자" data-itsp-required> 
												<input type="button" name="refreshBtn" id="refreshBtn" title="다른문자보기" value="다른문자보기" class="bt bt_s bt_inner" >
											</div>
										</li>
									</ul>
								</div>
							</c:if>
							</form>
						</div>
						<!-- //post_form -->
					</div>
					<!-- //post_tbl -->

					<div class="post_bottom">
						<div class="ls"></div>
						<div class="rs">
							<c:if test="${not empty sessionScopeMember || 'Y' eq boardConfig.nonMember}">
								<div class="bt_list">
									<ul>
										<li><label for="" class="blind">취소</label><button type="button" name="" id="" class="bt bt_nor" data-itsp-list-link>취소</button></li>
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
<jsp:include page="search.jsp" />
</body>
</html>