<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$('.lnb .depth1 li').click(function(e) {
		var me = $(this);
		if(me.hasClass("on") === true) {
			me.removeClass('on');
			me.children('ul').hide();
		}else{
			me.children('ul').show();
			me.addClass('on');
		}
		e.stopPropagation();
	});
}); 
</script>
<div class="left_column">
	<div class="left_header">
		<h1 onclick="location.href='/'">글로벌문화예술교육원</h1>
		<span class="log_info"><button type="button" name="" id="" title="로그아웃" class="bt_ico_logout" onclick="location.href='/logOutProc.do'">로그아웃</button></span>
	</div>
	<nav>
		<div class="lnb">
			<ul class="depth1">
				<li <c:if test="${'menu' eq flag }">class="on"</c:if>><a href="#"><span>CMS 관리</span></a>
					<ul class="depth2" <c:if test="${ 'menu' eq flag }">style="display: block;"</c:if>>
						<li <c:if test="${'menu' eq flag }">class="on"</c:if>><a href="/iWORK/Menu.do"><span>메뉴 관리</span></a></li>
					</ul>
				</li>
				<li <c:if test="${'board' eq flag || 'editor' eq flag || 'history' eq flag || 'major' eq flag || 'directions' eq flag || 'facility' eq flag || 'schedule' eq flag }">class="on"</c:if>><a href="#"><span>기능 관리</span></a>
					<ul class="depth2" <c:if test="${'board' eq flag || 'editor' eq flag || 'history' eq flag || 'major' eq flag || 'directions' eq flag || 'facility' eq flag || 'schedule' eq flag || 'application' eq flag }">style="display: block;"</c:if>>
						<li <c:if test="${'board' eq flag }">class="on"</c:if>><a href="/iWORK/Board/ConfigList.do"><span>게시판 관리</span></a></li>
						<li <c:if test="${'editor' eq flag }">class="on"</c:if>><a href="/iWORK/Editor/ConfigList.do"><span>에디터 관리</span></a></li>
						<li <c:if test="${'history' eq flag }">class="on"</c:if>><a href="/iWORK/History/ConfigList.do"><span>연혁 관리</span></a></li>
						<li <c:if test="${'schedule' eq flag }">class="on"</c:if>><a href="/iWORK/Schedule/ConfigList.do"><span>일정 관리</span></a></li>
						<li <c:if test="${'directions' eq flag }">class="on"</c:if>><a href="/iWORK/Directions/RecordList.do"><span>오시는길 관리</span></a></li>
						
						<li <c:if test="${'facility' eq flag }">class="on"</c:if>><a href="/iWORK/Facility/ConfigList.do"><span>시설 관리</span></a></li>
						<li <c:if test="${'major' eq flag }">class="on"</c:if>><a href="/iWORK/Major/AboutList.do"><span>계열 관리</span></a></li>
						
						<li <c:if test="${'application' eq flag }">class="on"</c:if>><a href="/iWORK/Application.do"><span>원서정보 관리</span></a></li>
						
					</ul>
				</li>
				<li <c:if test="${'popUp' eq flag || 'visual' eq flag  }">class="on"</c:if>><a href="#"><span>초기화면 관리</span></a>
					<ul class="depth2" <c:if test="${'popUp' eq flag || 'visual' eq flag  }">style="display: block;"</c:if>>
						<li <c:if test="${'popUp' eq flag }">class="on"</c:if>><a href="/iWORK/PopUpList.do"><span>팝업 관리</span></a></li>
						<li <c:if test="${'visual' eq flag }">class="on"</c:if>><a href="/iWORK/VisualList.do"><span>비쥬얼 관리</span></a></li>
					</ul>
				</li>
				<li <c:if test="${'member' eq flag || 'swearword' eq flag}">class="on"</c:if>><a href="#"><span>관리자 관리</span></a>
					<ul class="depth2" <c:if test="${'swearword' eq flag}">style="display: block;"</c:if>>
						<li <c:if test="${'member' eq flag }">class="on"</c:if>><a href="/iWORK/MemberList.do"><span>관리자 관리</span></a></li>
						<li <c:if test="${'swearword' eq flag }">class="on"</c:if>><a href="/iWORK/SwearWord.do"><span>비허용 단어관리</span></a></li>
					</ul>
				</li>
				<li <c:if test="${'googleanalytics' eq flag}">class="on"</c:if>><a href="#"><span>통계</span></a>
					<ul class="depth2"  <c:if test="${'googleanalytics' eq flag}">style="display: block;"</c:if>>
						<li <c:if test="${'googleanalytics' eq flag }">class="on"</c:if>><a href="/iWORK/GoogleAnalytics.do"><span>google analytics</span></a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
</div>