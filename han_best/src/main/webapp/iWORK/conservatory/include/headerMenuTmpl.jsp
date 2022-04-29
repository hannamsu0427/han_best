<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ page session="true" %>
<c:set var="saveMenuVO" value="${menuVO}" scope="page"/>
<li>
	<c:choose>
		<c:when test="${empty saveMenuVO.children[0]}">
			<c:choose>
				<c:when test="${'Link' eq saveMenuVO.type}">
					<a href="${saveMenuVO.url}" target="${saveMenuVO.linkTarget}">
				</c:when>
				<c:otherwise>
					<a href="${saveMenuVO.type}.do?menuSeq=${saveMenuVO.seq}&configSeq=${saveMenuVO.configSeq}" target="${saveMenuVO.linkTarget}">
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${'Link' eq saveMenuVO.children[0].type}">
					<a href="${saveMenuVO.children[0].url}" target="${saveMenuVO.children[0].linkTarget}">
				</c:when>
				<c:otherwise>
					<a href="${saveMenuVO.children[0].type}.do?menuSeq=${saveMenuVO.children[0].seq}&configSeq=${saveMenuVO.children[0].configSeq}" target="${saveMenuVO.children[0].linkTarget}">
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	<span>${saveMenuVO.title}</span></a>
</li>
<c:set var="menuVO" value="${saveMenuVO}" scope="request"/>