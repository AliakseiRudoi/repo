
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<script type="text/javascript"
	src="<c:url value="/resources/js/validationNews.js" />">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/validationComment.js" />">
	
</script>

<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.js" />">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.validate.js" />">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/formValidation.js" />">
	
</script>


</head>
<div id="mainBody">

	<fmt:setBundle basename="local" var="bundle" />
	<fmt:message bundle="${bundle}" key="local.date.format" var="pattern" />

<%-- 
<form class="cmxform" id="signupForm" method="get" action="">
		<fieldset>
			<legend>Validating a complete form</legend>
			<p>
				<label for="firstname">Firstname</label>
				<input id="firstname" name="firstname" type="text">
			</p>
			<p>
				<label for="lastname">Lastname</label>
				<input id="lastname" name="lastname" type="text">
			</p>	newsTitle
			<p>
				<label for="username">Username</label>
				<input id="username" name="username" type="text">
			</p>
			<p>
				<input class="submit" type="submit" value="Submit">
			</p>
		</fieldset>
	</form>

 --%>


	<div id="showSingleNewsDiv">
		<div id="notValidDivMessageUpdateNews">${failMessage}</div>
		<form:form id="newsForm" action="updateNews" commandName="newsVO">

			<div id="newsTitleDiv">
				<form:hidden path="news.version" value="${news.version}" />
				<form:hidden path="news.newsId" value="${currentNewsId}" />
				<form:hidden path="author.authorName"
					value="${newsVO.author.authorName}" />
				 <form:input title=""  
					path="news.newsTitle" value="${newsVO.news.newsTitle}" size="38"
					id="inputNewsTitle"  />  
				<br>
				<form:input 
					path="news.newsShortText" value="${newsVO.news.newsShortText}"
					size="38" id="inputShortText" />

			</div>
			<div id="newsCreationDateDiv">
				<fmt:formatDate value="${newsVO.news.newsModificationDate}"
					pattern="${pattern}" var="formattedDate" />
				<c:out value="${formattedDate}" />
			</div>
			<br>
			<br>
			<div id="newsFullTextDiv">
				<form:textarea path="news.newsFullText" cols="61" rows="12" placeholder="max lengh 2000 symbols" maxlength="2000" value="${newsVO.news.newsFullText}"/>
				
<%-- 				<textarea 
					name="news.newsFullText" cols="61" rows="12"
					placeholder="max lengh 2000 symbols" maxlength="2000"> ${newsVO.news.newsFullText} </textarea> --%>
			</div>

			<table id="tableUpdateNewsPage">
				<tr>
					<td><form:select path="author.authorId">

							<c:forEach var="author" items="${authorsList}">

								<form:option value="${author.authorId}">
									<c:out value="${author.authorName}" />
								</form:option>
							</c:forEach>
						</form:select></td>
					<td>
						<div class="multiselect">
							<div class="selectBox" onclick="showCheckboxes()">
								<select>
									<option>Select the tags</option>
								</select>
								<div class="overSelect"></div>
							</div>
							<div id="checkboxes">
								<c:forEach var="tags" items="${tagsList}">

									<label for="${tags.tagId}"> <form:checkbox
											path="tagsIdList" value="${tags.tagId}" /> <c:out
											value="${tags.tagName}" />
									</label>
								</c:forEach>
							</div>
						</div>
					</td>

				</tr>

			</table>

			<form:button path="" id="bottomUpdateNews"> Update News </form:button>
			<br>
			<br>

		</form:form>

		<div id="newsCommentsDivEditSinglNews">
			<c:forEach var="commentL" items="${commentsList}">

				<div id="newsCommentCreationDateDiv">
					<fmt:formatDate value="${commentL.creationDate}"
						pattern="${pattern}" var="formattedDate" />
					<c:out value="${formattedDate}" />
				</div>
				<div id="newsCommentsTextDiv">

					<c:out value="${commentL.commentText}"></c:out>

					<div style="float: right;">

						<form:form method="post" modelAttribute="comment"
							action="deleteCommentAdmin">
							<form:hidden path="commentId" value="${commentL.commentId}" />
							<form:hidden path="newsId" value="${newsVO.news.newsId}" />
							<form:button path="" id="bottomDeleteComment"> X </form:button>
						</form:form>
					</div>
				</div>
			</c:forEach>
		</div>

		<div id="commentForm">

			<form:form  id="formComment" method="post"
				modelAttribute="comment" action="addCommentAdmin">
				<textarea id="commentValid" name="commentText"
					cols="49" rows="4"></textarea>

				<form:hidden path="newsId" value="${newsVO.news.newsId}" />
				<br>
				<input class="addComment" type="submit" value="Submit">

			</form:form>

		</div>

	</div>

	<div id="notValidDivMessage">${messageCommentNotValid}</div>

	<div id="previousNextButtons">
		<div id="buttomPrevDiv">
			<c:if
				test="${newsVO.news.newsId != resultPreviousNewsId && resultPreviousNewsId != null}">
				<a href="/news-admin/editSingleNews?&newsId=${resultPreviousNewsId}"><spring:message
						code="local.button.previous" /></a>
			</c:if>
		</div>

		<div id="buttomNextDiv">
			<c:if
				test="${newsVO.news.newsId != resultNextNewsId && resultNextNewsId != null}">
				<a href="/news-admin/editSingleNews?&newsId=${resultNextNewsId}"><spring:message
						code="local.button.next" /></a>
			</c:if>

		</div>

	</div>

</div>
