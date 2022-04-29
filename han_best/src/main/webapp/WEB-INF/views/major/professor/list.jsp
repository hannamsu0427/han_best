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
<title>계열 관리</title>
<!-- PC Favicon -->
<link href="/css/admin/style.css" rel="stylesheet" type="text/css">
<%@ include file="/common/include/script.jsp" %>
<script type="text/javascript">
	//app 전용 js는 injectFile로 호출할 것(이유 : 캐시 방지 코드를 붙임) 
	injectFile("/js/major/professor.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>계열 관리</h3>
					<ul class="location">
						<li>기능 관리 </li>
						<li>계열 관리 </li>
						<li>${dataAbout.title } </li>
						<li class="here">교수진 목록</li>
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
									<li style="width: 12.5%"><a href="About.do?seq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>기본 설정</span></a></li>
									<li style="width: 12.5%"><a href="IntroList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>"><span>전공소개</span></a></li>
									<li style="width: 12.5%"><a href="ProfessorList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" class="on"><span>교수진</span></a></li>
									<li style="width: 12.5%"><a href="GoalList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>교육목표</span></a></li>
									<li style="width: 12.5%"><a href="CurriculumList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>교육과정</span></a></li>
									<li style="width: 12.5%"><a href="CourseList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>교과과정</span></a></li>
									<li style="width: 12.5%"><a href="FieldList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>진로 및 활동분야</span></a></li>
									<li style="width: 12.5%"><a href="FacilitiesList.do?aboutSeq=<c:out value="${MAJOR_ABOUT_SEQ}"/>" ><span>실습실</span></a></li>
								</ul>
							</div>
						
							<div class="post_tbl">
								<div class="grid_top">
									<div class="ls"><h3>교수진 목록</h3></div>
								</div>
								
								<div class="pt20 pb10">
									<ul class="sortings">
										<li>총 <strong><c:out value="${totalCount }"/></strong>건</li>
										<li class="pl10">
											<label for="curPage" class="blind" >리스트 수 선택</label>
											<select name="curPage" id="curPage" title="" class="opt">
												<option value="5" <c:if test="${'5' eq curPage}">selected="selected"</c:if>>5개씩 보기</option>
												<option value="10" <c:if test="${'10' eq curPage}">selected="selected"</c:if>>10개씩 보기</option>
												<option value="15" <c:if test="${'15' eq curPage}">selected="selected"</c:if>>15개씩 보기</option>
												<option value="20" <c:if test="${'20' eq curPage}">selected="selected"</c:if>>20개씩 보기</option>
												<option value="30" <c:if test="${'30' eq curPage}">selected="selected"</c:if>>30개씩 보기</option>
												<option value="40" <c:if test="${'40' eq curPage}">selected="selected"</c:if>>40개씩 보기</option>
												<option value="50" <c:if test="${'50' eq curPage}">selected="selected"</c:if>>50개씩 보기</option>
												<option value="100" <c:if test="${'100' eq curPage}">selected="selected"</c:if>>100개씩 보기</option>
											</select>
										</li>
									</ul>
								</div>
								
								<div class="post_list">
									<table class="grid">
										<caption>목록</caption>
										<colgroup><col width="6%"><col width="6%"><col width="10%"><col width="*"><col width="15%"><col width="15%"><col width="10%"></colgroup>
										<thead>
											<tr>
												<th>선택</th>
												<th>No.</th>
												<th>직급</th>
												<th>교수명</th>
												<th>기능설정</th>
												<th>생성일</th>
												<th>사용여부</th>
											</tr>
										</thead>
										<tbody id="changeOrder">
											<c:if test="${empty dataList }">
												<tr>
													<td colspan="7">생성된 데이터가 없습니다.</td>
												</tr>
											</c:if>
											<c:forEach items="${dataList}" var="data">
												<tr data-itsp-seq="<c:out value="${data.seq}"/>" data-itsp-orderNum="<c:out value="${data.rnum}"/>">
													<td><input type="checkbox" name="chkBoxs" id="chkBox_<c:out value="${data.seq}"/>" value="<c:out value="${data.seq}"/>" ></td>
													<td><c:out value="${totalCount - data.rnum + 1}"/></td>
													<td><c:out value="${data.position}"/></td>
													<td><c:out value="${data.title}"/></td>
													<td>
													<button type="button" name="" value="" title="" class="bt_inner" data-itsp-edit-link="<c:out value="${data.seq }"/>">기능설정</button>
													<button type="button" name="" value="" title="" class="bt_inner" data-itsp-delete-link="<c:out value="${data.seq }"/>">삭제</button>
													</td>
													<td><c:out value="${data.regDate}"/></td>
													<td>${'Y' eq data.useYn ? '사용' : '미사용'}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								
							</div>
							<!-- //post_tbl -->
							
							<div class="post_bottom">
								<div class="bt_list">
									<ul>
										<li><button type="button" name="" id="" class="bt bt_nor" data-itsp-delete-link="chkBox">선택 삭제</button></li>
										<li><button type="button" name="" id="" class="bt bt_nor" data-itsp-orderNum-link>순서 변경</button></li>
										<li><button type="button" name="" id="" class="bt bt_on" data-itsp-edit-link="0">생성</button></li>
									</ul>
								</div>
								<%@ include file="/common/include/paginate.jsp" %>
							</div>
							
							<div class="comment_box">
								<ul>
									<li><strong class="em">반드시 읽어보세요</strong></li>
									<li>- 컨텐츠의 그룹의 정보를 입력 후 , 저장하기 버튼을 클릭하시면 컨텐츠 그룹이 생성됩니다.</li>
									<li>- 컨텐츠 그룹이 생성된후, 생성된 그룹의 컨텐츠를 관리 하실 수 있습니다.</li>
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
<jsp:include page="search.jsp" />
</body>
</html>