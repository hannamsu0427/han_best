<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<div class="location">
	<ul>
		<li>Home</li>
		<c:forEach var="curMenu" items="${curMenuPath}" varStatus="status">
			<c:if test="${!status.last}">
				<li><c:out value="${curMenu.title}" /></li>
			</c:if>
			<c:if test="${status.last}">
				<c:set var="curTitle" value="${curMenu.title }" scope="request" />
				<c:set var="curSeq" value="${curMenu.seq }" scope="request" />
				<li class="here"><c:out value="${curMenu.title}" /></li>
			</c:if>
		</c:forEach>
	</ul>
</div>
<!-- //location -->