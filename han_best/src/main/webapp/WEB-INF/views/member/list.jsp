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
<title>원서정보 관리</title>
<!-- PC Favicon -->
<link href="/css/admin/style.css" rel="stylesheet" type="text/css">
<%@ include file="/common/include/script.jsp" %>
<script type="text/javascript">
	//app 전용 js는 injectFile로 호출할 것(이유 : 캐시 방지 코드를 붙임)
	injectFile("/js/member/member.js");
	injectFile("/js/ready.js");
</script>
</head>

<body>
	<div class="right_column">
		<div class="wrap">
			<header>
				<div class="header_inner">
					<h3>관리자 관리</h3>
					<ul class="location">
						<li>관리자 관리 </li>
						<li class="here">관리자 등록/삭제 </li>
					</ul>
					<div class="client"><img src="/images/common/admin_logo.png"></div>
				</div>
			</header>
			<section>
				<div class="container">
					<div class="contents_inner">
						<article>
							<div class="">
								<div class="post_tbl">
									<div class="grid_top">
										<div class="ls"><h3>관리자 정보</h3></div>
										<div class="rs"><span class="req fs13">은 필수입력 사항입니다.</span></div>
									</div>
									<table class="grid view">
										<caption>관리자 정보</caption>
										<colgroup><col width="20%"><col width="80%"></colgroup>
										<tbody>
											<tr>
												<th><span class="req pl10">관리자</span></th>
												<td>
													<div class="row">
														<ul class="dp_inline">
															<li class="fca"><span class="slash">슈퍼관리자</span></li>
															<li id="memberList"></li>
															<li><button type="button" class="bt_inner" data-itsp-modal-open> 직원검색</button></li>
														</ul>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<!-- //post_tbl -->
							</div>
							
							<div class="post_bottom"></div>
							
							<div class="comment_box">
								<ul>
									<li><strong class="em">반드시 읽어보세요</strong></li>
									<li>- 관리자는 멀티로 선택할 수 있습니다.</li>
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
	
	<div class="modal_popup"><!-- display:none 기본값, "on"클래스로 활성화 -->
		<div class="modal_popup_page">
			<div class="modal_popup_wrap" style="width:900px;">
				<div class="header">
					<h1>관리자 관리</h1>
					<div class="bt_close"><a href="#">창닫기</a></div>
				</div>
				<div class="section">
					<div class="pㅠ20">
						<div class="dp_tbl p100">
							<div class="dp_cell w400 pb30">
								<div class="post_tbl">
									<div class="grid_top">
										<div class="ls"><h3>관리자 목록</h3></div>
									</div>
									<table class="grid">
										<caption>관리자 목록</caption>
										<colgroup></colgroup>
										<thead>
											<tr>												
												<th>이름</th>
												<th>아이디</th>
												<th>등록일</th>
												<th>삭제</th>
											</tr>
										</thead>
									</table>
									<div class="overflow_y" style="height:467px">
										<table class="grid">
											<caption>관리자 목록</caption>
											<colgroup></colgroup>
											<tbody id="MemberAjaxDataList">
											</tbody>
										</table>
									</div>
								</div>
								<!-- //post_tbl -->
							</div>
							<!--//Left End -->
							
							<div class="dp_cell "></div>
							
							<div class="dp_cell w430 pb30">
								<div class="post_tbl">
									<div class="grid_top">
										<div class="ls"><h3>교직원 찾기</h3></div>
									</div>
									
									<div class="grid_search">
										<fieldset>
											<legend>게시판 검색</legend>
											<ul class="dp_inline tc">
												<li>
													<label for="" class="blind">검색조건 선택</label>
													<select name="searchBy" id="searchBy" title="" class="w130">
														<option value="all">이름 + 아이디</option>
														<option value="use_nm">이름</option>
														<option value="user_id">아이디</option>
													</select>
												</li>
												<li>
													<label for="" class="blind">검색어 입력</label><input type="text" placeholder="검색어 입력" id="searchName" value="" name="searchName">
												</li>
												<li>
													<label for="" class="blind">검색</label><button type="button" class="bt_s bt_search">검색</button>
												</li>
											</ul>
										</fieldset>
									</div>
									
									<div class="pt15">
										<p class="tr pb5">
											<label for="" class="pr10"><strong>선택한 교직원에게 입학처 사이트 관리자 권한</strong></label><button type="button" class="bt_inner" data-itsp-member-save-link>부여하기</button>
										</p>
									</div>
									
									<table class="grid">
										<caption>교직원 목록</caption>
										<colgroup></colgroup>
										<thead>
											<tr>
												<th></th>
												<th>이름</th>
												<th>아이디</th>
											</tr>
										</thead>
										
									</table>
									<div class="overflow_y" style="height:360px;">
										<table class="grid">
											<caption>교직원 목록</caption>
											<colgroup></colgroup>
											<tbody id="hjAjaxDataList">
												<tr>
													<td colspan="3">검색 후 사용하세요.</td>
												</tr>
											</tbody>
										</table>
									</div>
									
								</div>
								<!-- //post_tbl -->
							</div>
						</div>
						
					</div>
					<div class="comment_box">
						<ul>
							<li><strong class="em">반드시 읽어보세요</strong></li>
							<li>- 관리자는 멀티로 선택할 수 있습니다. </li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" id="hjDataTemplate">
	<tr>
		<td><input type="checkbox" name="chkBoxs" value="{user_id}_{user_nm}"></td>
		<td>{user_nm}</td>
		<td>{user_id}</td>
	</tr>
	</script>
	
	<script type="text/javascript" id="dataTemplate">
	<span class="slash">{user_nm}</span>
	</script>
	
	<script type="text/javascript" id="hjDataEmptyTemplate">
		<tr>
			<td colspan="3">검색된 데이터가 없습니다.</td>
		</tr>
	</script>
	
	<script type="text/javascript" id="memberDataTemplate">
	<tr>
		<td>{user_nm}</td>
		<td>{user_id}</td>
		<td>{reg_date}</td>
		<td><button type="button" class="bt_inner" data-itsp-member-del-link="{user_id}">삭제</button></td>
	</tr>
	</script>
	
	<script type="text/javascript" id="memberDataEmptyTemplate">
		<tr>
			<td colspan="4">데이터가 없습니다.</td>
		</tr>
	</script>
	
</body>
</html>