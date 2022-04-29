<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<c:if test="${pageMap.ePage > 1}">
	<div class="paginate">
		<c:if test="${pageMap.pageNum != 1}">
			<span class="left">
			<a href="#" class="roll first" title="go first page" data-itsp-page-link="1" data-itsp-curPage="<c:out value="${curPage }"/>">first</a>
			<a href="#" class="roll prev" title="go prev page" data-itsp-page-link="${pageMap.pageNum -1}" data-itsp-curPage="<c:out value="${curPage }"/>">prev</a>
			</span>
		</c:if>
		<ul class="list"> 
			<c:forEach var="pageNumber" begin="${pageMap.sPage}" end="${pageMap.ePage}" step="1">
				<c:choose>
					<c:when test="${pageNumber eq pageMap.pageNum}">
						<li><strong>${pageNumber}</strong></li>
					</c:when>
					<c:otherwise>
						<li><a href="#" data-itsp-page-link="${pageNumber}" data-itsp-curPage="<c:out value="${curPage }"/>">${pageNumber}</a></li> 
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
		<c:if test="${pageMap.totalCnt > 10 && pageMap.pageNum ne pageMap.pageCnt}">
			<span class="right">
			<a href="#" class="roll next" title="go next page" data-itsp-page-link="${pageMap.pageNum + 1}" data-itsp-curPage="<c:out value="${curPage }"/>">next</a>
			<a href="#" class="roll last" title="go last page" data-itsp-page-link="${pageMap.pageCnt}" data-itsp-curPage="<c:out value="${curPage }"/>">last</a>
			</span>
		</c:if>
	</div>
	<!-- //pagenage -->
</c:if>