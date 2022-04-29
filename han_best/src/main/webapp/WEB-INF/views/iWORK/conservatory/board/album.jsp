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
					<jsp:include page="/iWORK/conservatory/include/post_tabs.jsp" />
					<!-- // post_tabs -->
					<div class="post_tbl">
						<div class="post_top">
							<div class="ls">
								<div class="post_search">
									<form name="newSearchForm" id="newSearchForm">
										<input type="hidden" name="menuSeq" value="<c:out value="${menuSeq }"/>" />
										<input type="hidden" name="configSeq" value="<c:out value="${configSeq }"/>" />
										<input type="hidden" name="categorySeq" value="<c:out value="${categorySeq }"/>" />
										<input type="hidden" name="pageNum" value="<c:out value="${pageNum }"/>" />
										<input type="hidden" name="curPage" value="<c:out value="${curPage }"/>" />
										<fieldset>
											<legend>검색</legend>
											<ul>
												<li>
													<label for="searchBy" class="blind">검색조건 선택</label>
													<select name="searchBy" id="searchBy" title="검색조건 선택" class="txt_field">
														<option value="all" ${'all' eq searchBy || empty searchBy ? 'selected="selected"':'' }>전체</option>
														<option value="title" ${'title' eq searchBy ? 'selected="selected"':'' }>제목</option>
														<option value="contents" ${'contents' eq searchBy ? 'selected="selected"':'' }>내용</option>
													</select>
												</li>
												<li><label for="" class="blind">검색어 입력</label><input type="text" placeholder="검색어 입력" class="txt_field" value="${searchValue }" id="searchValue" name="searchValue"></li>
												<li><label for="" class="blind">검색</label><button type="button" title="검색" class="bt_srch" data-itsp-new-search-link>검색</button></li>
											</ul>
										</fieldset>
									</form>
								</div>
							</div>
							<div class="rs">${pageMap.pageNum }/${0 eq pageMap.pageCnt  ? '1' : pageMap.pageCnt} 페이지, 총 게시글 <strong class="em">${totalCount }</strong>개</div>
						</div>
						<!-- //post_top -->
						
						<c:if test="${not empty noticeList}">
							<div class="post_list">
								<table class="post_head">
									<caption>게시판 목록</caption>
									<colgroup><col width="8%"><col width="48%"><col span="2" width="10%"><col span="3" width="8%"></colgroup>
									<thead>
										<tr>
											<th>번호</th>
											<th>제목</th>
											<th>작성자</th>
											<th>작성일</th>
											<th>조회</th>
											<th>댓글</th>
											<th>첨부</th>
										</tr>
									</thead>
								</table>
								<ul class="noti_area">
									<c:forEach items="${noticeList }" var="notice">
										<c:set var="tempRegDate" value="${fn:replace(notice.regDate , '-', '')}" />
										<fmt:parseNumber integerOnly="true" type="number" value="${tempRegDate}" var="regDate" />
										<li class="post_tit">
											<div class="subject"><span class="txt_head">[공지]</span>
												<a href="#" class="subject_click" data-itsp-edit-link="<c:out value="${notice.seq}"/>">
													<c:if test="${not empty notice.categoryName}"> [${notice.categoryName }] </c:if><c:out value="${notice.title}"/>
													<c:if test="${today < regDate }"><span class="ico_new"></span></c:if>
												</a>
												<c:if test="${today < regDate }"><span class="ico_new"></span></c:if>
											</div>
											<div class="subject_info">
												<ul>										
													<li class="write_name"><span><span class="visible_xs">작성자 : </span>${notice.regName}</span></li>
													<li class="write_date"><span><span class="visible_xs">작성일 : </span>${notice.regDate}</span></li>
													<li class="write_hit"><span><span class="visible_xs">조회수 : </span>${notice.hitCnt}</span></li>
													<li class="write_reply"><span><span class="visible_xs">댓글 : </span>${notice.commentCnt}</span></li>
													<li class="ico_state"><c:if test="${not empty notice.attachFileList }"><span class="ico_file_down"></span></c:if></li>
												</ul>
											</div>
										</li>
									</c:forEach>
								</ul>
							</div>
							<!-- //post_list -->
						</c:if>
						
						<div class="post_list border_top0 ">
							<ul class="list_thumb">
								<c:forEach items="${dataList }" var="data">
									<c:set var="tempRegDate" value="${fn:replace(data.regDate , '-', '')}" />
									<fmt:parseNumber integerOnly="true" type="number" value="${tempRegDate}" var="regDate" />
									<li class="list_thumb_item">
										<div class="list_thumb_cont">
											<a href="#" class="thumb_link" data-itsp-view-link="<c:out value="${data.seq}"/>">
												<div class="thumb_img">
													<c:choose>
														<c:when test="${'movie' eq dataConfig.type }">
															<c:choose>
																<c:when test="${'#' ne data.url && not empty data.url }">
																	<iframe id="ytplayer_${seq}" type="text/html" width="100%" height="100%" src="https://www.youtube.com/embed/${fn:substringAfter(data.url, 'be/') }?rel=0&amp;controls=1&amp;showinfo=0" frameborder="0" allowfullscreen> </iframe>  
																</c:when>
																<c:otherwise>
																	<img src="<c:url value="/file/imgResizeView.do?seq=${data.attachFileList[0].seq}"/>" onerror="javascript:this.src='/images/front/sub/album_blank_image.png'"  width="100%" height="100%" alt="">
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${not empty data.attachFileList}">
																	<c:set var="photoListLoop" value="false" />
																	<c:forEach var="file" items="${data.attachFileList}">
																		<c:if test="${not photoListLoop}">
																			<c:if test="${ file.fileType eq 'jpeg' || file.fileType eq 'pjpeg' || file.fileType eq 'jpg' || file.fileType eq 'gif' || file.fileType eq 'png' || file.fileType eq 'bmp' }">
																				<img alt="${file.realFileName}" src="/file/imgResizeView.do?seq=${file.seq }" onerror="javascript:this.src='/images/front/sub/album_blank_image.png'" width="100%" height="100%" alt=""/>
																				<c:set var="photoListLoop" value="true" />
																			</c:if>
																		</c:if>
																	</c:forEach>
																</c:when>
																<c:otherwise>
																	<div data-itsp-temp>
																		<div style="visibility: hidden; width: 0px; height: 0px" data-itsp-temp-contents><c:out value="${data.contents}" escapeXml="false"/></div>
																		<img src="" onerror="javascript:this.src='/images/front/sub/album_blank_image.png'" width="100%" height="100%" >
																	</div>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</div>
											</a>
											<div class="subject">
												<div class="subject_click"><c:if test="${not empty data.categoryName}"> [${data.categoryName }] </c:if><c:out value="${data.title}"/></div>
												<div class="write_date">${data.regDate}</div>
											</div>
										</div>
										<!-- //list_thumb_cont -->
									</li>
									<!-- //list_thumb_item -->
								</c:forEach>
							</ul>
						</div>
						
					</div>
					<!-- //post_tbl -->
					<div class="post_bottom">
						<div class="ls">
							<%@ include file="/iWORK/conservatory/include/paginate.jsp" %>
							<!-- //pagenage -->
						</div>
						<c:if test="${not empty sessionScopeMember || 'Y' eq dataConfig.nonMember}">
							<div class="rs">
								<div class="bt_list">
									<ul>
										<li><label for="" class="blind">글쓰기</label><button type="button" name="" id="" class="bt bt_on" data-itsp-edit-link="0">글쓰기</button></li>
									</ul>
								</div>
								<!-- // bt_list -->
							</div>
						</c:if>
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
<script type="text/javascript">
	$(document).ready(function() {
		$("[data-itsp-temp]").each(function(){
			var imgSrc = $(this).find('[data-itsp-temp-contents]').find('img').eq(0).attr('src');
			if(imgSrc != "" && imgSrc != null && typeof imgSrc != "undefined"){
				$(this).find('img').last().attr("src", imgSrc);
			}
		});
	});
</script>
</body>
</html>