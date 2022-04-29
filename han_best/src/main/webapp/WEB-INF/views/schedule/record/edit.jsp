<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no, address=no, email=no">
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta name = "keywords" content ="">
<meta name = "description" content ="">
<title>게시판 관리_게시물관리 목록</title>
<!-- PC Favicon -->
<link href="/css/admin/style.css" rel="stylesheet" type="text/css">
<%@ include file="/common/include/script.jsp" %>
<script type="text/javascript">
	//app 전용 js는 injectFile로 호출할 것(이유 : 캐시 방지 코드를 붙임) 
	injectFile("/js/schedule/record.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>연혁 관리</h3>
					<ul class="location">
						<li>기능 관리 </li>
						<li>연혁 관리 </li>
						<li>${dataConfig.title} </li>
						<li class="here">연혁 목록</li>
					</ul>
					<div class="client"><img src="/images/common/admin_logo.png"></div>
				</div>
			</header>
			<section>
				<div class="container">
					<div class="contents_inner">
						<article>
							<div class="tabs">
								<ul>
									<li class="p33"><a href="#" data-itsp-config-link="<c:out value="${SCHEDULE_CONFIG_SEQ}"/>"><span>기본 설정</span></a></li>
									<li class="p33"><a href="#" data-itsp-record-link="<c:out value="${SCHEDULE_CONFIG_SEQ}"/>" class="on"><span>일정 관리</span></a></li>
								</ul>
							</div>
							<div class="pt20">
								<div class="post_tbl">
									<form action="/Schedule/Record/saveProcData.do" method="post" name="fm" id="fm">
										<input type="hidden" name="configSeq" value="${empty data ? SCHEDULE_CONFIG_SEQ : data.configSeq}" readonly="readonly"/>
										<input type="hidden" name="seq" value="${data.seq}" readonly="readonly"/>
										<table class="grid view">
											<caption>기본설정 목록</caption>
											<colgroup><col width="20%"><col width="80%"></colgroup>
											<tbody>
												<tr>
													<th><span class="req pl10">사용여부</span></th>
													<td>
														<div class="row">
															<ul class="dp_inline">
																<li><input type="radio" name="useYn" id="useY" value="Y" ${'N' ne data.useYn ? 'checked="checked"':'' }><label for="useY" class="pl5">사용</label></li>
																<li><input type="radio" name="useYn" id="useN" value="N" ${'N' eq data.useYn ? 'checked="checked"':'' }><label for="useN" class="pl5">미사용</label></li>
															</ul>
														</div>
													</td>
												</tr>
												<tr>
													<th><span class="req pl10">시작 날짜</span></th>
													<td>
														<div class="row">
															<ul class="dp_inline fl">
																<li>
																	<c:set var="today" value="<%=new java.util.Date()%>" />
																	<fmt:formatDate value="${today}" pattern="yyyy" var="nowYear"/> 
																	<select id="startYear" name="startYear">
																		<option value="">선택</option>
																		<option value="${nowYear + 1}" <c:if test="${nowYear + 1 eq data.startYear}">selected="selected"</c:if>>${nowYear + 1}</option>
																		<c:forEach var="i" begin="0" end="${nowYear - 2004}">
																			<c:set var="yearOption" value="${nowYear - i}" />
																			<option value="${yearOption}" <c:if test="${yearOption eq data.startYear}">selected="selected"</c:if>>${yearOption}</option>
																		</c:forEach>
																	</select>
																	<label for="year">년</label>
																</li>
																<li>
																	<select id="startMonth" name="startMonth">
																		<option value="">선택</option>
																		<c:forEach var="i" begin="1" end="12" step="1" >
																			<fmt:formatNumber value="${i}" minIntegerDigits="2" var="startMonth"/>
																			<option value="${startMonth }" <c:if test="${startMonth eq data.startMonth}">selected="selected"</c:if>>${startMonth }</option>
																		</c:forEach>
																	</select>
																	<label for="month">월</label>
																</li>
																<li><select id="startDay" name="startDay">
																		<option value="">선택</option>
																		<c:forEach var="i" begin="1" end="31" step="1" >
																			<fmt:formatNumber value="${i}" minIntegerDigits="2" var="startDay"/>
																			<option value="${startDay }" <c:if test="${startDay eq data.startDay}">selected="selected"</c:if>>${startDay }</option>
																		</c:forEach>
																	</select>
																	<label for="day">일</label>
																</li>
																<li class="em">ex) 0000년 00월 00일 형태로 정렬됩니다.</li>
															</ul>
														</div>
													</td>
												</tr>
												<tr>
													<th><span class="pl10">종료 날짜</span></th>
													<td>
														<div class="row">
															<ul class="dp_inline fl">
																<li><select id="endYear" name="endYear">
																		<option value="">선택</option>
																		<option value="${nowYear + 1}" <c:if test="${nowYear + 1 eq data.endYear}">selected="selected"</c:if>>${nowYear + 1}</option>
																		<c:forEach var="i" begin="0" end="${nowYear - 2004}">
																			<c:set var="yearOption" value="${nowYear - i}" />
																			<option value="${yearOption}" <c:if test="${yearOption eq data.endYear}">selected="selected"</c:if>>${yearOption}</option>
																		</c:forEach>
																	</select>
																	<label for="year">년</label>
																</li>
																<li><select id="endMonth" name="endMonth">
																		<option value="">선택</option>
																		<c:forEach var="i" begin="1" end="12" step="1" >
																			<fmt:formatNumber value="${i}" minIntegerDigits="2" var="endMonth"/>
																			<option value="${endMonth }" <c:if test="${endMonth eq data.endMonth}">selected="selected"</c:if>>${endMonth }</option>
																		</c:forEach>
																	</select>
																	<label for="month">월</label>
																</li>
																<li><select id="endDay" name="endDay">
																		<option value="">선택</option>
																		<c:forEach var="i" begin="1" end="31" step="1" >
																			<fmt:formatNumber value="${i}" minIntegerDigits="2" var="endDay"/>
																			<option value="${endDay }" <c:if test="${endDay eq data.endDay}">selected="selected"</c:if>>${endDay }</option>
																		</c:forEach>
																	</select>
																	<label for="day">일</label>
																</li>
																<li class="em">ex) 종료날짜는 정렬과 상관없이 시작날짜와 같이 표기됩니다.</li>
															</ul>
														</div>
													</td>
												</tr>
												<tr>
													<th><span class="req pl10">내용</span></th>
													<td>
														<div class="row pt5 pb5">
															<textarea name="contents" id="contents" rows="5" class="p100" placeholder="연혁내용을 입력해주세요." data-itsp-title="연혁내용" data-itsp-required>${data.contents}</textarea>
														</div>
													</td>
												</tr>
											</tbody>
										</table>
									</form>
								</div>
								<!-- //post_tbl -->
							</div>

							<div class="post_bottom">
								<div class="bt_list">
									<ul>
										<li><button type="button" name="" id="" class="bt bt_nor" onclick="location.href='/iWORK/Schedule/RecordList.do?configSeq=${SCHEDULE_CONFIG_SEQ}'">목록으로</button></li>
										<li><button type="button" name="" id="" class="bt bt_on" data-itsp-save-link="${empty data ? SCHEDULE_CONFIG_SEQ : data.configSeq}">저장하기</button></li>
									</ul>
								</div>
							</div>
							
							<div class="comment_box">
								<ul>
									<li class="pb10"><strong class="em">반드시 읽어보세요</strong></li>
								</ul>
							</div>
						</article>
					</div>
				</div>
			</section>
		</div>
		<!-- //wrap -->
	</div>
	<%@ include file="/common/include/left_column.jsp" %>
	<!-- //left_column -->
</body>
</html>