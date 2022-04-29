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
	injectFile("/iWORK/conservatory/js/schedule/schedule.js");
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
							<form action="/Schedule/Record/saveProcData.do" method="post" name="fm" id="fm">
							<input type="hidden" name="configSeq" value="${empty data ? SCHEDULE_CONFIG_SEQ : data.configSeq}" readonly="readonly"/>
							<input type="hidden" name="seq" value="${data.seq}" readonly="readonly"/>
							<ul class="post_form_head">
								<li class="post_form_item">
									<div class="form_title">사용여부</div>
									<div class="form_item">
										<ul class="dp_inline">
											<li><input type="radio" name="useYn" id="useY" value="Y" ${'N' ne data.useYn ? 'checked="checked"':'' }><label for="useY" class="pl5">사용</label></li>
											<li><input type="radio" name="useYn" id="useN" value="N" ${'N' eq data.useYn ? 'checked="checked"':'' }><label for="useN" class="pl5">미사용</label></li>
										</ul>
									</div>
								</li>

								<li class="post_form_item">
									<div class="form_title">시작 날짜</div>
									<div class="form_item">
										<ul class="dp_inline">
											<li class="p30">
												<c:set var="today" value="<%=new java.util.Date()%>" />
												<fmt:formatDate value="${today}" pattern="yyyy" var="nowYear"/> 
												<select id="startYear" name="startYear" class="txt_field p80">
													<option value="">선택</option>
													<option value="${nowYear + 1}" <c:if test="${nowYear + 1 eq data.startYear}">selected="selected"</c:if>>${nowYear + 1}</option>
													<c:forEach var="i" begin="0" end="${nowYear - 2004}">
														<c:set var="yearOption" value="${nowYear - i}" />
														<option value="${yearOption}" <c:if test="${yearOption eq data.startYear}">selected="selected"</c:if>>${yearOption}</option>
													</c:forEach>
												</select>
												<label for="year">년</label>
											</li>
											<li class="p30">
												<select id="startMonth" name="startMonth" class="txt_field p80">
													<option value="">선택</option>
													<c:forEach var="i" begin="1" end="12" step="1" >
														<fmt:formatNumber value="${i}" minIntegerDigits="2" var="startMonth"/>
														<option value="${startMonth }" <c:if test="${startMonth eq data.startMonth}">selected="selected"</c:if>>${startMonth }</option>
													</c:forEach>
												</select>
												<label for="month">월</label>
											</li>
											<li class="p30">
												<select id="startDay" name="startDay" class="txt_field p80">
													<option value="">선택</option>
													<c:forEach var="i" begin="1" end="31" step="1" >
														<fmt:formatNumber value="${i}" minIntegerDigits="2" var="startDay"/>
														<option value="${startDay }" <c:if test="${startDay eq data.startDay}">selected="selected"</c:if>>${startDay }</option>
													</c:forEach>
												</select>
												<label for="day">일</label>
											</li>
										</ul>
									</div>
								</li>
								
								<li class="post_form_item">
									<div class="form_title">종료 날짜</div>
									<div class="form_item">
										<ul class="dp_inline">
											<li class="p30">
												<select id="endYear" name="endYear"  class="txt_field p80">
													<option value="">선택</option>
													<option value="${nowYear + 1}" <c:if test="${nowYear + 1 eq data.endYear}">selected="selected"</c:if>>${nowYear + 1}</option>
													<c:forEach var="i" begin="0" end="${nowYear - 2004}">
														<c:set var="yearOption" value="${nowYear - i}" />
														<option value="${yearOption}" <c:if test="${yearOption eq data.endYear}">selected="selected"</c:if>>${yearOption}</option>
													</c:forEach>
												</select>
												<label for="year">년</label>
											</li>
											<li class="p30">
												<select id="endMonth" name="endMonth"  class="txt_field p80">
													<option value="">선택</option>
													<c:forEach var="i" begin="1" end="12" step="1" >
														<fmt:formatNumber value="${i}" minIntegerDigits="2" var="endMonth"/>
														<option value="${endMonth }" <c:if test="${endMonth eq data.endMonth}">selected="selected"</c:if>>${endMonth }</option>
													</c:forEach>
												</select>
												<label for="month">월</label>
											</li>
											<li class="p30">
												<select id="endDay" name="endDay"  class="txt_field p80">
													<option value="">선택</option>
													<c:forEach var="i" begin="1" end="31" step="1" >
														<fmt:formatNumber value="${i}" minIntegerDigits="2" var="endDay"/>
														<option value="${endDay }" <c:if test="${endDay eq data.endDay}">selected="selected"</c:if>>${endDay }</option>
													</c:forEach>
												</select>
												<label for="day">일</label>
											</li>
										</ul>
									</div>
								</li>
							</ul>
							<div class="post_view_body">
								<div class="post_body_cke">
									<textarea style="border:1px solid #afafaf;padding:10px;" name="contents" id="contents" data-itsp-title="학사일정을 등록하세요." data-itsp-required>${data.contents}</textarea>
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