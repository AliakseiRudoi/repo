<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<div id="mainAuthorsPageDiv">

		<c:forEach var="tags" items="${tagsList}">

			<c:choose>
				<c:when test="${tags.tagId eq newsVO.tag.tagId}">

					<form:form action="updateTagAction"
						modelAttribute="newsVO">

						<div class="inputAuthorDiv">
							<spring:message code="local.message.tag" />
						</div>
						<div class="inputAreaAuthorDiv">

							<form:hidden path="tag.tagId" value="${tags.tagId }"/>
							<form:input path="tag.tagName"
							value="${tags.tagName}" id="inputAuthorNameTrue" size="38" pattern="[\\w\\W]{3,30}$" title="length must be 3 - 30 symbols"/>

						</div>
							
							<div id="buttonAction">
									<input type="submit" class="bottomMenu" value="<spring:message code="local.button.update" />">
							</div>
							</form:form>
							<div id="buttonAction2">
								
								<form:form action="deleteTagAction" modelAttribute="newsVO">
								<form:hidden path="tag.tagId" value="${tags.tagId }" />
								<form:button class="bottomMenu" ><spring:message code="local.button.delete" /></form:button>
								</form:form>
								
								<%-- <form action="expireAuthorAction" class="buttomMenuEdit">
									<button class="bottomMenu">
										<spring:message code="local.button.expire" />
									</button>
								</form> --%>
							</div>
							<div id="buttonAction3">
								<form action="tagsPage" class="buttomMenuEdit">
									<button class="bottomMenu">
										<spring:message code="local.button.cancel" />
									</button>
								</form>
							</div>
					

				
				</c:when>
				<c:otherwise>

					<form:form action="redirectToEditTagPage"
						modelAttribute="newsVO">

						<div class="inputAuthorDiv">
							<spring:message code="local.message.tag" />
						</div>
						<div class="inputAreaAuthorDiv">
							<input value="<c:out value="${tags.tagName}"></c:out>" size="38"
								readonly="readonly" id="inputAuthorName" />
						</div>
						<div id="buttonArea">
							<div id="buttonEdit">

								<form:hidden path="tag.tagId" value="${tags.tagId}" />
								<form:button class="buttomMenuEdit">
									<spring:message code="local.button.editAdmin" />
								</form:button>

							</div>
						</div>
					</form:form>

				</c:otherwise>
			</c:choose>

		</c:forEach>

		<form:form action="addTagAction" commandName="newsVO">

			<div class="inputAuthorDiv">
				<spring:message code="local.message.addAuthor" />
			</div>
			<div class="inputAreaAuthorDiv">
				<form:input path="tag.tagName" size="58" pattern="[\\w\\W]{3,30}$" title="length must be 3 - 30 symbols"/>
			</div>

			<form:button path="" id="bottomAddAuthor">
				<spring:message code="local.button.save" />
			</form:button>

		</form:form>
		<div id="notValidDivMessage">
		${notValid}
		</div>
	</div>
