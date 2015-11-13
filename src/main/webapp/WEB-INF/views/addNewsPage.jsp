<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head> 
<script type="text/javascript" src="<c:url value="/resources/js/validationNews.js" />" > </script>

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
<form:form id="newsForm"  action="addNewsAction/1" commandName="newsVO">

	<div class="inputInfoDiv">
		<spring:message code="local.message.title" />
	</div>
	<div class="inputDiv">
		<form:input id="inputDivM" path="news.newsTitle" size="38" 
		 placeholder="max lengh 30 symbols"  />
	</div>

	<div class="inputInfoDiv">
		<spring:message code="local.message.brief" />
	</div>
	<div class="inputDiv">
		<form:input path="news.newsShortText" value="${newsVO.news.newsShortText}"
					size="38" id="inputShortText" 	placeholder="max lengh 100 symbols" />
		
	</div>
	<div class="inputInfoDiv">
		<spring:message code="local.message.content" />
	</div>
	<div class="inputDiv">
	<form:textarea path="news.newsFullText" cols="50" rows="12" placeholder="max lengh 2000 symbols" value="${newsVO.news.newsFullText}"/>
	
	</div>

	<table id="tableSearchAddNewsPage">
		<tr>
			<td><form:select path="author.authorId">
					<form:option value="0" label="Please select the author" />
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

	<input type="submit" id="bottomAddNewsAddPage" value="<spring:message code="local.button.save" />">
	<%-- <form:button path="" id="bottomAddNewsAddPage">
		<spring:message code="local.button.save" />
	</form:button> --%>
</form:form>

<div id="messageMissAuthorOrTags">${messageMissAuthorOrTags}</div>