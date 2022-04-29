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
<title>글로벌문화예술교육원</title>

<link rel="shortcut icon" type="image/x-icon" href="/images/admin/favicon.ico">
<link href="/css/admin/style.css" rel="stylesheet" type="text/css">

<%@ include file="/common/include/script.jsp" %>
<link href="/common/js/yakutree/tree.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/common/js/yakutree/jquery.yakutree.js"></script>
<script type="text/javascript">
// <![CDATA[

$(function(){
    $("#sdk").yakutree({
    	unique: false,
        animate: true,
        duration: 100 ,
        aTagClick : true ,
        btnAllExpand : '.expend',
        btnAllColspand : '.colspend',
        btnFirst : '.btnFirst',
        btnLast : '.btnLast',
        btnUp : '.btnUp',
        btnDown : '.btnDown',
        /*
        btnAdd : '.btnAdd',
        btnDel : '.btnDel',
        */
        dragndrop : true,
        btnCallback : function ( type, $source, $target) {
            switch( type ) {
                case 'moveUp':
                    //this.moveAnimation(  $source, $target, 'revert' );
                    break;
            }
        }
    });
    $('.expend').trigger('click');
});

// ]]>
</script>
<script type="text/javascript">
	//app 전용 js는 injectFile로 호출할 것(이유 : 캐시 방지 코드를 붙임)
	var type = "${empty data ? 'Link' : data.type}";
	var configSeq = "${data.configSeq}";
	var categorySeq = "${data.categorySeq}";
	var recordSeq = "${data.recordSeq}";
	injectFile("/js/menu/menu.js");
	injectFile("/js/ready.js");
</script>
</head>
<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>메뉴 관리</h3>
					<ul class="location">
						<li>CMS 관리</li>
						<li class="here">메뉴 관리</li>
					</ul>
					<div class="client"><img src="/images/common/admin_logo.png"></div>
				</div>
			</header>
			
			<section>
				<div class="container">
					<div class="contents_inner">
						<article>
							<div class="pt20">
								<table class="menu_wrap">
									<colgroup><col width="300px"><col></colgroup>
										<tr>
											<td class="tree_area">
												<div class="menu_tree">
													<div id="category">
														<div class="category_control">
															<button type="button" class="btn colspend">전체닫기<i class="fa fa-minus"></i></button>
															<button type="button" class="btn expend">전체열기<i class="fa fa-plus"></i></button>
														</div>
														<div class="directory">
															<div class="root" data-itsp-edit-link="0" data-itsp-parentSeq data-itsp-depth>Root</div>
															<ul id="sdk">
																<c:forEach var="menuVOOne" items="${menuList}" varStatus="status">
																<li data-itsp-seq="<c:out value="${menuVOOne.seq}"/>" data-itsp-depth="<c:out value="${menuVOOne.depth}"/>" data-itsp-parentSeq="<c:out value="${menuVOOne.parentSeq}"/>" data-itsp-sortOrder="<c:out value="${menuVOOne.sortOrder}"/>">
																	<a href="#" data-itsp-edit-link="<c:out value="${menuVOOne.seq}"/>" data-itsp-parentSeq="<c:out value="${menuVOOne.seq }"/>" data-itsp-depth="<c:out value="${menuVOOne.depth }"/>" ><strong>${menuVOOne.title}</strong></a>
																	<c:set var="menuVO" value="${menuVOOne}" scope="request"/>
																	<c:set var="menuList" value="${menuVOOne.children}" scope="request"/>
																	<jsp:include page="/common/include/menuListTmpl.jsp" />
																</li>
																</c:forEach>
															</ul>
														</div>
														<div class="category_control">
															<button type="button" class="btn change" data-itsp-menu-sortOrder-link>순서변경 저장</button>
														</div>
													</div>
													<!-- //category -->
												</div>
											</td>
											
											<td class="setting_area">
												<div class="post_tbl">
													<div class="grid_top">
														<div class="ls"><h3>메뉴 관리</h3></div>
														<div class="rs"><span class="req fs13">입력후 반드시 아래 적용하기 버튼을 누르세요</span></div>
													</div>
													<form action="/iWORK/Menu/saveDataProc.do" method="post" name="fm" id="fm">
														<input type="hidden" name="seq" id="seq" value="<c:out value="${data.seq }"/>" />
														<input type="hidden" name="parentSeq" id="parentSeq" value="<c:out value="${parentSeq }"/>" />
														<input type="hidden" name="depth" id="depth" value="<c:out value="${depth }"/>" />
														<table class="grid view">
														<caption>메뉴 항목</caption>
														<colgroup><col width="20%"><col width="80%"></colgroup>
														<tbody>
															<c:if test="${not empty data}">
																<tr>
																	<th><span class="pl10">메뉴코드</span></th>
																	<td><strong>${data.sortOrder }</strong></td>
																</tr>
																<tr>
																	<th><span class="pl10">메뉴위치</span></th>
																	<td>
																		<c:forEach var="curMenu" items="${curMenuPath}" varStatus="status">
																			<c:if test="${!status.last}"><c:out value="${curMenu.title}" /> > </c:if>
																			<c:if test="${status.last}"><strong><c:out value="${curMenu.title}" /></strong></c:if>
																		</c:forEach>
																	</td>
																</tr>
															</c:if>
															
															<tr>
																<th><span class="pl10">사용여부</span></th>
																<td>
																	<div class="row">
																		<ul class="dp_inline">
																			<li><input type="radio" name="useYn" id="useY" value="Y" <c:if test="${'N' ne data.useYn}">checked="checked"</c:if>><label for="useY" class="pl5">사용</label></li>
																			<li><input type="radio" name="useYn" id="useN" value="N" <c:if test="${'N' eq data.useYn}">checked="checked"</c:if>><label for="useN" class="pl5">미사용</label></li>
																		</ul>
																	</div>
																</td>
															</tr>
															<tr>
																<th><span class="pl10">메뉴명</span></th>
																<td>
																	<div class="row"><input type="text" name="title" id="title" value="<c:out value="${data.title }"/>" title="메뉴명" placeholder="메뉴명" class="p100" data-itsp-title="메뉴명" data-itsp-required></div>
																</td>
															</tr>
															<tr>
																<th><span class="pl10">메뉴설명</span></th>
																<td>
																	<div class="row"><input type="text" name="explanation" id="explanation" value="<c:out value="${data.explanation }"/>" title="메뉴설명" placeholder="메뉴설명" class="p100"></div>
																</td>
															</tr>
															<tr>
															<th><span class="pl10">메뉴형태</span></th>
															<td>
																<div class="row">
																	<ul class="dp_inline">
																		<li><input type="radio" name="treeType" id="treeMenu" value="menu" <c:if test="${'tabMenu' ne data.treeType}">checked="checked"</c:if>><label for="treeMenu" class="pl5">일반</label></li>
																		<li><input type="radio" name="treeType" id="tabMenu" value="tabMenu"<c:if test="${'tabMenu' eq data.treeType}">checked="checked"</c:if>><label for="tabMenu" class="pl5">탭메뉴</label></li>
																	</ul>
																</div>
															</td>
														</tr>
															<tr>
																<th><span class="pl10">컨텐츠 표출</span></th>
																<td>
																	<div class="row">
																		<ul class="dp_inline">
																			<li><input type="radio" name="linkTarget" id="linkTargetSelf" value="_self" <c:if test="${'_blank' ne data.linkTarget}">checked="checked"</c:if>><label for="linkTargetSelf" class="pl5">_self : 현재 창에 링크된 내용이 보여진다. </label></li>
																			<li><input type="radio" name="linkTarget" id="linkTargetBlank" value="_blank" <c:if test="${'_blank' eq data.linkTarget}">checked="checked"</c:if>><label for="linkTargetBlank" class="pl5">_blank : 링크된 내용이 새창으로 보여진다.</label></li>
																		</ul>
																	</div>
																</td>
															</tr>
															<tr>
																<th rowspan="2"><span class="pl10">컨텐츠 타입</span></th>
																<td>
																	<div class="row">
																	<ul class="dp_inline fl">
																			<li><input type="radio" name="type" id="type_Link" value="Link" <c:if test="${'Link' eq data.type || empty data}">checked="checked"</c:if>><label for="type_Link" class="pl5">Link</label></li>
																			<li><input type="radio" name="type" id="type_Include" value="Include" <c:if test="${'Include' eq data.type}">checked="checked"</c:if>><label for="type_Include" class="pl5">File Include</label></li>
																			
																			<li><input type="radio" name="type" id="type_BoardList" value="BoardList" <c:if test="${'BoardList' eq data.type}">checked="checked"</c:if>><label for="type_BoardList" class="pl5">게시판</label></li>
																			<li><input type="radio" name="type" id="type_Edtor" value="Editor" <c:if test="${'Editor' eq data.type}">checked="checked"</c:if>><label for="type_Edtor" class="pl5">에디터</label></li>
																			<li><input type="radio" name="type" id="type_Historyr" value="History" <c:if test="${'History' eq data.type}">checked="checked"</c:if>><label for="type_Historyr" class="pl5">연혁</label></li>
																			<li><input type="radio" name="type" id="type_Schedule" value="Schedule" <c:if test="${'Schedule' eq data.type}">checked="checked"</c:if>><label for="type_Schedule" class="pl5">일정</label></li>
																			<li><input type="radio" name="type" id="type_Directions" value="Directions" <c:if test="${'Directions' eq data.type}">checked="checked"</c:if>><label for="type_Directions" class="pl5">오시는 길</label></li>
																			
																			<li><input type="radio" name="type" id="type_Facility" value="Facility" <c:if test="${'Facility' eq data.type}">checked="checked"</c:if>><label for="type_Facility" class="pl5">시설</label></li>
																			<li><input type="radio" name="type" id="type_Major" value="Major" <c:if test="${'Major' eq data.type}">checked="checked"</c:if>><label for="type_Major" class="pl5">계열</label></li>
																			<li><input type="radio" name="type" id="type_Application" value="Application" <c:if test="${'Application' eq data.type}">checked="checked"</c:if>><label for="type_Application" class="pl5">원서접수</label></li>
																			<li><input type="radio" name="type" id="type_ApplicationConfirm" value="ApplicationConfirm" <c:if test="${'ApplicationConfirm' eq data.type}">checked="checked"</c:if>><label for="type_ApplicationConfirm" class="pl5">원서접수확인</label></li>
																			<li><input type="radio" name="type" id="type_SuccessfulConfirm" value="SuccessfulConfirm" <c:if test="${'SuccessfulConfirm' eq data.type}">checked="checked"</c:if>><label for="type_SuccessfulConfirm" class="pl5">합격자확인</label></li>
																		</ul>
																	</div>
																</td>
															</tr>	
															<tr>
																<td class="pb10">
																	<div class="pt10">
																		<span class="req">컨텐츠 타입을 Link 선택 시 이동할 Url을 <strong>컨텐츠 경로</strong>입력란에 기입해주세요. <strong>외부 Url일 경우 http:// 입력은 필수입니다.</strong></span>
																		<div>
																			<ul class="pb5">
																				<li class="pt5 cols" id="functionData">
																					<div class="col p100">
																						<label for="configSeq" class="p30">프로그램 : </label>
																						<select id="configSeq" name="configSeq" title="선택" class="p70">
																							<option value="">선택</option>
																						</select>
																					</div>
																				</li>
																				<li class="pt5 cols" id="functionInclude">
																					<div class="col p100">
																						<label for="includePath" class="p30">File 경로 : </label>
																						<input type="text" name="includePath" id="includePath" value="${data.includePath }" placeholder="include할 file경로를 기입해주세요." class="p70">
																					</div>
																				</li>
																				<li class="pt5 cols" id="functionLink">
																					<div class="col p100">
																						<label for="url" class="p30">Url 경로 : </label>
																						<input type="text" name="url" id="url" value="${empty data ? '#' : data.url }" placeholder="link할 url경로를 기입해주세요." class="p70" readonly="readonly">
																					</div>
																				</li>
																			</ul>
																		</div>
																	</div>
																</td>
															</tr>
														</tbody>
													</table>
													</form>
												</div>
												<!-- //post_tbl -->
												
												
												<div class="post_bottom">
													<div class="bt_list">
														<c:choose>
															<c:when test="${empty data }">
																<button type="button" name="" id="" class="bt bt_on" data-itsp-save-link>확인</button>
															</c:when>
															<c:otherwise>
																<ul>
																	<c:if test="${data.depth < 3  }">
																		<li><button type="button" name="" id="" class="bt bt_roll" data-itsp-edit-link="0" data-itsp-parentSeq="${data.seq }" data-itsp-depth="${data.depth }">하위메뉴등록</button></li>
																	</c:if>
																	<li><button type="button" name="" id="" class="bt bt_on" data-itsp-save-link>확인</button></li>
																	<li><button type="button" name="" id="" class="bt bt_nor" data-itsp-delete-link="${data.seq }">삭제하기</button></li>
																</ul>
															</c:otherwise>
														</c:choose>
													</div>
												</div>
												<!-- //post_bottom -->
											</td>
										</tr>
									</tbody>
								</table>
								<!-- //menu_wrap -->
							</div>
							
							<div class="comment_box">
								<ul>
									<li><strong class="em">반드시 읽어보세요</strong></li>
									<li>- 컨텐츠의 그룹의 정보를 입력 후 , 저장하기 버튼을 클릭하시면 컨텐츠 그룹이 생성됩니다.</li>
									<li>- 컨텐츠 그룹이 생성된후, 생성된 그룹의 컨텐츠를 관리 하실 수 있습니다.</li>
									<li>- 페이지별 만족도 사용여부를 체크하시면, 페이지 만족도 기능을 사용할 수 있습니다.</li>
								</ul>
							</div>
						</article>
					</div>
				</div>
			</section>
		</div>
		<!-- //wrap -->
	</div>
	<!-- //right_column -->
	
	<%@ include file="/common/include/left_column.jsp"%>
	<!-- // left_column -->
	
	<form name="curSearchForm" id="curSearchForm" action="/iWORK/Menu.do" method="post">
		<input type="hidden" name="seq" value="<c:out value="${seq }"/>" />
		<input type="hidden" name="parentSeq" value="<c:out value="${parentSeq }"/>" />
		<input type="hidden" name="depth" value="<c:out value="${depth }"/>" />
	</form>
</body>
</html>