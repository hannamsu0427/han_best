<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="true"%>
<c:set var="curParentMenuVO" value="" />
<c:if test="${not empty curMenuTab}">
	<div class="post_tabs <c:if test="${fn:contains(curTitle, '인사말')}">greetings</c:if>">
		<ul <c:if test="${fn:length(curMenuTab) eq 2}">class="half"</c:if>>
			<c:forEach var="tabMenu" items="${curMenuTab}">
				<li <c:if test="${tabMenu.seq eq menuSeq }">class="active"</c:if>>
					<c:choose>
						<c:when test="${'Link' eq tabMenu.type}">
							<a href="${tabMenu.url}" target="${tabMenu.linkTarget}">${tabMenu.title }</a>
						</c:when>
						<c:otherwise>
							<a href="${tabMenu.type}.do?menuSeq=${tabMenu.seq}&configSeq=${tabMenu.configSeq}" target="${tabMenu.linkTarget}">${tabMenu.title }</a>
						</c:otherwise>
					</c:choose>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>