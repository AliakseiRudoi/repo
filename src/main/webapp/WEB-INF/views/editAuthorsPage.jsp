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

			<c:choose>
				<c:when test="${authors.authorId eq newsVO.author.authorId}">

					<form:form action="updateAuthorAction"
						modelAttribute="newsVO">

						<div class="inputAuthorDiv">
							<spring:message code="local.message.author" />
						</div>
						<div class="inputAreaAuthorDiv">

							<form:hidden path="author.authorId" value="${authors.authorId }"/>
							<form:input path="author.authorName"
							value="${authors.authorName}" id="inputAuthorNameTrue" size="38" pattern="[\\w\\W]{3,30}$" title="length must be 3 - 30 symbols"/>

						</div>
							
							<div id="buttonAction">
									<input type="submit" class="bottomMenu" value="<spring:message code="local.button.update" />">
							</div>
							</form:form>
							<div id="buttonAction2">
								
								<form:form action="expireAuthorAction" modelAttribute="newsVO">
								<form:hidden path="author.authorId" value="${authors.authorId }" />
								<form:button class="bottomMenu" ><spring:message code="local.button.expire" /></form:button>
								</form:form>
								
							</div>
							<div id="buttonAction3">
								<form action="authorsPage" class="buttomMenuEdit">
									<button class="bottomMenu">
										<spring:message code="local.button.cancel" />
									</button>
								</form>
							</div>
					

				
				</c:when>
				<c:otherwise>

					<form:form action="redirectToEditAuthorPage"
						modelAttribute="newsVO">

						<div class="inputAuthorDiv">
							<spring:message code="local.message.author" />
						</div>
						<div class="inputAreaAuthorDiv">
							<input value="<c:out value="${authors.authorName}"/>" size="38"
								readonly="readonly" id="inputAuthorName" />
						</div>
						<div id="buttonArea">
							<div id="buttonEdit">

								<form:hidden path="author.authorId" value="${authors.authorId}" />
								<form:button class="buttomMenuEdit">
									<spring:message code="local.button.editAdmin" />
								</form:button>

							</div>
						</div>
					</form:form>

				</c:otherwise>
			</c:choose>




		</c:forEach>





		<form:form action="addAuthorAction" commandName="newsVO">

			<div class="inputAuthorDiv">
				<spring:message code="local.message.addAuthor" />
			</div>
			<div class="inputAreaAuthorDiv">
				<form:input path="author.authorName" size="58" pattern="[\\w\\W]{3,30}$" title="length must be 3 - 30 symbols"/>
			</div>

			<form:button path="" id="bottomAddAuthor">
				<spring:message code="local.button.save" />
			</form:button>

		</form:form>
		<div id="notValidDivMessage">
		${notValid}
		</div>
	</div>
