<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<script type="text/javascript" src="<c:url value="/resources/js/ajax.getNewsListPage.js" />" > </script>
</head>
<div id="mainBody">

	<jsp:include page="/WEB-INF/views/input/search.jsp" />

	<fmt:setBundle basename="local" var="bundle" />
	<fmt:message bundle="${bundle}" key="local.date.format" var="pattern" />

	<div id="ajaxAsynchronousArea">

<br>
	 <form:form action="/news-admin/delNews" modelAttribute="newsVO1">
		<c:forEach var="parseNews" items="${newsVO}">
			<div id="newsTitleDiv">
				<a id="titleButton"
					href="/news-admin/news/editSingleNews?&newsId=${parseNews.news.newsId}"><c:out
						value="${parseNews.news.newsTitle}"></c:out></a>
			</div>
			<div id="newsAuthorDiv">
				(
				<spring:message code="local.message.by" />
				<c:out value="${parseNews.author.authorName}" />
				)
			</div>
			<div id="newsCreationDateDiv">
				<fmt:formatDate value="${parseNews.news.newsModificationDate}"
					pattern="${pattern}" var="formattedDate" />
				<c:out value="${formattedDate}" />
			</div>
			<br>
			<br>
			<div id="newsShortTextDiv">
				<c:out value="${parseNews.news.newsShortText}"></c:out>
			</div>
			<br>
			<div id="linkReadMoreDiv">
				<a href="/news-admin/news/editSingleNews?&newsId=${parseNews.news.newsId}">
				<spring:message code="local.button.editAdmin" />
				</a> 
				<label for="${parseNews.news.newsId}">  
				<form:checkbox path="newsIdList" value="${parseNews.news.newsId}" />
				</label>
			</div>
			<div id="newsCommentsDiv">
				<c:out value="${parseNews.news.newsCommentAmmount}" />
			</div>
			<div id="newsTagsDiv">
				<c:forEach var="parseTags" items="${parseNews.tagsList}">
					<c:out value="${parseTags.tagName}" />
				</c:forEach>
			</div>
			<br>
			<br>
		</c:forEach>
		<form:button>
		
			<spring:message code="local.button.deleteNews" />
		
		</form:button>
	</form:form>

	</div>
	<br><br><br>
	<c:if test="${paginNum>1}">

		<div class="divPagination" align="center">
			<div id="light-theme"></div>
		</div>

	</c:if>

</div>
