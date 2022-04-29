<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<!doctype html>
<html lang="ko">
<head>
<%@ include file="/iWORK/conservatory/include/meta.jsp"%>
<%@ include file="/iWORK/conservatory/include/link.jsp"%>
<%@ include file="/iWORK/conservatory/include/script.jsp"%>
<script type="text/javascript">
	var recordSeq = "<c:out value="${BOARD_RECORD_SEQ }"/>";
	var sessionScopeMemberId = "";
	<c:if test="${not empty sessionScopeMember }">
		var sessionScopeMemberId = "<c:out value="${sessionScopeMember.user_id }"/>";
	</c:if>
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
						<div class="post_view">
							<div class="post_view_head">
								<ul class="noti_area">
									<li class="post_tit">
										<div class="subject">
											<c:set var="tempRegDate" value="${fn:replace(data.regDate , '-', '')}" />
											<fmt:parseNumber integerOnly="true" type="number" value="${tempRegDate}" var="regDate" />
											<c:if test="${'Y' eq data.noticeYn}"><span class="txt_head">[공지]</span></c:if>
											<a href="#" class="subject_click"><c:if test="${not empty data.categoryName}"> [${data.categoryName }] </c:if><c:out value="${data.title}"/><c:if test="${today < regDate }"><span class="ico_new"></span></c:if></a><c:if test="${today < regDate }"><span class="ico_new"></span></c:if></div>
										<div class="subject_info">
											<ul>
												<li class="write_name"><span><span class="visible">작성자 : </span>${data.regName}</span></li>
												<li class="write_date"><span><span class="visible">작성일 : </span>${data.regDate }</span></li>
												<li class="write_hit"><span><span class="visible">조회수 : </span>${data.hitCnt }</span></li>
											</ul>
										</div>
										<c:if test="${'Y' eq dataConfig.fileYn && not empty data.attachFileList }">
											<div class="post_add">
												<dl>
													<dt>첨부파일</dt>
													<dd>
														<c:forEach items="${data.attachFileList }" var="file">
															<a href="<c:url value="/file/fileDownLoad.do?seq=${file.seq}"/>" title="파일다운로드">${file.realFileName }</a>
														</c:forEach>
													</dd>
												</dl>
											</div>
										</c:if>
									</li>
								</ul>
							</div>
							
							<div class="post_view_body">
								<div class="post_body_txt">
									<div class="image">
									<c:if test="${'movie' eq dataConfig.type || 'album' eq dataConfig.type }">
										<c:forEach var="file" items="${data.attachFileList }">
											<c:if test="${file.fileType eq 'jpeg' || file.fileType eq 'pjpeg' || file.fileType eq 'jpg' || file.fileType eq 'gif' || file.fileType eq 'png' || file.fileType eq 'bmp' }">
												<img alt="${file.realFileName}" src="/file/imgView.do?seq=${file.seq }">
											</c:if>
										</c:forEach>
									</c:if>
									</div>
									<c:if test="${'movie' eq dataConfig.type }">
										<iframe id="ytplayer_${data.seq }" width="420" height="315" type="text/html" src="https://www.youtube.com/embed/${fn:substringAfter(data.url, 'be/') }?rel=0&amp;controls=1&amp;showinfo=0" frameborder="0" allowfullscreen=""> </iframe>
									</c:if>
									${cmutls:removeScript(data.contents)}
								</div>
								
								<c:if test="${ 'Y' eq dataConfig.replyYn || 'qna' eq dataConfig.type}">
									<div class="message_write_comment reply">
										<c:if test="${not empty replyData }">
											<div class="post_body_reply">
												${cmutls:removeScript(replyData.contents)}
											</div>
										</c:if>
										<form action="/Board/Reply/saveProcData.do" name="fmReply" id="fmReply" method="post">
											<input type="hidden" name="seq" id="seq" value="${replyData.seq }" readonly="readonly" />
											<input type="hidden" name="recordSeq" id="recordSeq" value="<c:out value="${data.seq }"/>" readonly="readonly" />
												<div class="write_main">
													<div class="write_text">
														<textarea class="input_write_text" cols="30" rows="3" name="contents" placeholder="답변을 입력해주세요." data-itsp-title="답변" data-itsp-required>${cmutls:removeScript(replyData.contents)}</textarea>
													</div>
													<div class="bt_list">
														<ul>
															<li><label for="" class="blind">답변 ${empty replyData ? '등록':'수정'}</label><button type="button" name="" id="" class="bt bt_black" data-itsp-reply-save-link>답변${empty replyData ? '등록':'수정'}</button></li>
														</ul>
													</div>
												</div>
										</form>
									</div>
								</c:if>
							</div>
						</div>
						<!-- //post_view -->
					</div>
					<!-- //post_tbl -->

					<div class="post_bottom">
						<div class="ls"></div>
						<div class="rs">
								<div class="bt_list">
									<ul>
									<c:if test="${not empty sessionScopeMember || 'Y' eq boardConfig.nonMember}">
							
										<c:if test="${('Y' eq dataConfig.replyYn || 'qna' eq dataConfig.type ) && not empty replyData}">
											<li><label for="" class="blind">답변 삭제</label><button type="button" name="" id="" class="bt bt_on" data-itsp-reply-delete-link="<c:out value="${replyData.seq}"/>">답변 삭제</button></li>
										</c:if>
										<li><label for="" class="blind">수정</label><button type="button" name="" id="" class="bt bt_on" data-itsp-edit-link="<c:out value="${data.seq}"/>">수정</button></li>
										
										<c:choose>
											<c:when test="${'N' eq dataConfig.delYn }">
												<li><label for="" class="blind">휴지통</label><button type="button" name="" id="" class="bt bt_on" data-itsp-use-link="<c:out value="${data.seq}"/>">휴지통</button></li>
											</c:when>
											<c:otherwise>
												<li><label for="" class="blind">삭제</label><button type="button" name="" id="" class="bt bt_on" data-itsp-delete-link="<c:out value="${data.seq}"/>">삭제</button></li>
											</c:otherwise>
										</c:choose>
										</c:if>
										<li><label for="" class="blind">목록</label><button type="button" name="" id="" class="bt bt_nor" data-itsp-list-link>목록</button></li>
									</ul>
								</div>
							<!-- // bt_list -->
							
						</div>
					</div>
					<!-- //post_bottom -->
					
					<c:if test="${'Y' eq dataConfig.preNextYn }">
						<div class="post_list_parent">
							<table class="post_list">
								<caption>글목록</caption>
								<tbody>
									<tr>
										<th>이전글<i class="fa fa-chevron-up"></i></th>
										<td><span class="subject"><a href="#" class="subject_click" data-itsp-view-link="<c:out value="${data.preSeq}"/>"><c:out value="${data.preTitle }"/></a></span></td>
									</tr>
									<tr>
										<th>다음글<i class="fa fa-chevron-down"></i></th>
										<td><span class="subject"><a href="#" class="subject_click" data-itsp-view-link="<c:out value="${data.nextSeq}"/>"><c:out value="${data.nextTitle }"/></a></span></td>
									</tr>
								</tbody>
							</table>
						</div>
					<!-- //post_list_parent -->
				</c:if>
				
				<c:if test="${'Y' eq dataConfig.commentYn}">	
					<div class="message_wrap">
						<div class="pt10"><strong class="fc0">댓글 <span class="em" id="commentCnt">[${data.commentCnt}]</span></strong></div>
						<div class="message_write_comment">
								<form action="/Board/Comments/saveProcData.do" name="fmComments" id="fmComments" method="post">
								<input type="hidden" name="recordSeq" value="<c:out value="${BOARD_RECORD_SEQ }"/>" readonly="readonly">
								<input type="hidden" name="seq" value="" readonly="readonly">
								<div class="write_main">
									<div class="write_text">
										<textarea class="input_write_text" cols="30" rows="3" placeholder="주제와 무관한 댓글, 악플은 삭제될 수 있습니다." name="contents" id="contents" data-itsp-title="내용" data-itsp-required></textarea>
									</div>
									<div class="bt_list">
										<ul>
											<li><label for="" class="blind">등록</label><button type="button" name="" id="" class="bt bt_black" data-itsp-comments-save-link>등록</button></li>
										</ul>
									</div>
								</div>
								</form>
						</div>
					</div>
					<!-- //message_wrap -->
					
					<div class="post_comment_wrap">
						<div class="comment_list" id="comment_list">
							<c:forEach var="comments" items="${data.commentList }">
								<div class="comment_item">
									<div class="write_info">
										<strong class="write_name"><c:out value="${comments.regName }"/></strong>
										<span class="write_date"><c:out value="${comments.regDate}"/></span>
									</div>
									<c:if test="${not empty sessionScopeMember && (sessionScopeMember.user_id eq comments.regId)}">
										<ul class="write_edit">
											<li><a href="#" class="on" data-itsp-comments-modify="<c:out value="${comments.seq }"/>"><span id="modify_${comments.seq }">수정</span></a></li>
											<li><a href="#" data-itsp-comments-delete="<c:out value="${comments.seq }"/>">삭제</a></li>
										</ul>
									</c:if>
									<div class="write_subject">${comments.contents }</div>
									<div class="message_write_comment" style="display: none;" id="comments_box_${comments.seq}">
										<div class="write_main">
											<div class="write_text">
												<textarea class="input_write_text" cols="30" rows="3" placeholder="댓글을 입력해주세요." id="contents_${comments.seq}">${comments.contents }</textarea>
											</div>
											<div class="bt_list">
												<ul>
													<li><label for="" class="blind">수정</label><button type="button" name="" id="" class="bt bt_black" data-itsp-comments-update-link="${comments.seq}">수정</button></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<!--//comment_item -->
							</c:forEach>
						</div>
					</div>
					<!-- //post_comment_wrap -->
				</c:if>
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