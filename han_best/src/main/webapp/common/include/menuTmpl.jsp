<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ page session="true" %>
<c:set var="saveMenuVO" value="${menuVO}" scope="page"/>
<li data-itsp-seq="<c:out value="${saveMenuVO.seq}"/>" data-itsp-depth="<c:out value="${saveMenuVO.depth}"/>" data-itsp-parentSeq="<c:out value="${saveMenuVO.parentSeq}"/>" data-itsp-sortOrder="<c:out value="${saveMenuVO.sortOrder}"/>">
	<a href="#" data-itsp-edit-link="<c:out value="${saveMenuVO.seq}"/>" data-itsp-parentSeq="<c:out value="${saveMenuVO.seq }"/>" data-itsp-depth="<c:out value="${saveMenuVO.depth }"/>">${saveMenuVO.title }</a>
<c:set var="menuList" value="${saveMenuVO.children}" scope="request"/>
<jsp:include page="menuDepth3Tmpl.jsp" />
<c:set var="menuVO" value="${saveMenuVO}" scope="request"/>