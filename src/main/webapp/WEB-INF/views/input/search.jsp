<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<div id="searchMainDiv">


		<table id="tableSearch">
			<form:form action="/news-admin/news/searchForm/1" commandName="searchCriteria"
				modelAttribute="searchCriteria">

				<tr>
					<td>
				
					<form:select path="author.authorId" >
							<form:option value="0" label="Please select the author" />
							<c:forEach var="author" items="${authorsList}">

								<form:option value="${author.authorId}" >
								<c:out value="${author.authorName}"/>
														</form:option>
							</c:forEach>

						</form:select>
						
						</td>

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

									 <c:set var="contains" value="false" />
								<c:forEach var="item" items="${sessionScope.tagListId}">
									<c:if test="${item eq tags.tagId}">
										<c:set var="contains" value="true" />
									</c:if>
								</c:forEach> 

									<label for="${tags.tagId}"> 
									<form:checkbox path="tagsIdList" value="${tags.tagId}" 
											checked="${contains? 'cheked' :  '' }" /> 
										<c:out value="${tags.tagName}"/>
									</label>

								</c:forEach>

							</div>
						</div>
					</td>
					<td>
						<div id="submitButtonDiv">

							<input type="submit"
								value="<spring:message code="local.button.submitSearch" />" />
						</div>
					</td>
				</tr>
			</form:form>

		</table>

		<table id="resetTable">
			<tr>
				<td>
					<div id="recetAreaDiv">
						<form action="reset" method="get">
							<input type="submit"
								value="<spring:message code="local.button.resetSearch" />">
						</form>
					</div>

				</td>
			</tr>
		</table>
	</div>