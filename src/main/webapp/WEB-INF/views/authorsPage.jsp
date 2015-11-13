<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div id="mainAuthorsPageDiv">

	<c:forEach var="authors" items="${authorsList}">

		<form:form action="redirectToEditAuthorPage" modelAttribute="newsVO">

			<div class="inputAuthorDiv">
				<spring:message code="local.message.author" />
			</div>
			<div class="inputAreaAuthorDiv">
				<input value="<c:out value="${authors.authorName}"/>" size="38" readonly="readonly"
					id="inputAuthorName" />
			</div>
			<div id="buttonArea">
				<div id="buttonEdit">

					<form:hidden path="author.authorId" value="${authors.authorId}" />
					<form:hidden path="author.authorName" value="${authors.authorName}" />
					<form:button class="buttomMenuEdit">
						<spring:message code="local.button.editAdmin" />
					</form:button>

				</div>
			</div>
		</form:form>

	</c:forEach>





	<form:form action="addAuthorAction" commandName="newsVO">

		<div class="inputAuthorDiv">
			<spring:message code="local.message.addAuthor" />
		</div>
		<div class="inputAreaAuthorDiv">
			<form:input path="author.authorName" size="39" pattern="[\\w\\W]{3,30}$" title="length must be 3 - 30 symbols"/>
		</div>

		<form:button id="bottomAddAuthor">
			<spring:message code="local.button.save" />
		</form:button>

	</form:form>
	<div id="notValidDivMessage">${notValid}</div>
</div>
