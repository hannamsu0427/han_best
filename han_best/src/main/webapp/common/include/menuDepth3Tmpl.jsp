<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
	<c:if test="${not empty menuList}">
	<ul class="depth_3">
	<c:forEach var="menuVOOne" items="${menuList}" varStatus="status">
	<c:set var="menuVO" value="${menuVOOne}" scope="request" />
		<li data-itsp-seq="<c:out value="${menuVO.seq}"/>" data-itsp-depth="<c:out value="${menuVO.depth}"/>" data-itsp-parentSeq="<c:out value="${menuVO.parentSeq}"/>" data-itsp-sortOrder="<c:out value="${menuVO.sortOrder}"/>">
			<a href="#" data-itsp-edit-link="<c:out value="${menuVO.seq}"/>" data-itsp-parentSeq="<c:out value="${menuVO.seq }"/>" data-itsp-depth="<c:out value="${menuVO.depth }"/>">${menuVO.title}</a>
		</li>
	</c:forEach>
	</ul>
	</c:if>
</li>