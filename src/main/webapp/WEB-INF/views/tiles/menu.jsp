
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>

	<sec:authentication property="principal" var="principal" />
	<c:if test="${principal != 'anonymousUser'}">

		<div id="menuBar">
			<div id="menuNavi">
				<ul type="circle" id="ul">
					<li>
						<form action="/news-admin/news/adminHome/1" class="menuForm">
							<button class="bottomMenu">
								<spring:message code="local.message.newsList" />
							</button>
						</form>
					</li>
					<li>
						<form action="/news-admin/news/addNewsPage" class="menuForm">
							<button class="bottomMenu">
								<spring:message code="local.message.addNews" />
							</button>
						</form>
					</li>
					<li>
						<form action="/news-admin/news/authorsPage" class="menuForm">
							<button class="bottomMenu">
								<spring:message code="local.message.addUpdateAuthors" />
							</button>
						</form>
					</li>
					<li>
						<form action="/news-admin/news/tagsPage" class="menuForm">
							<button class="bottomMenu">
								<spring:message code="local.message.addUpdateTags" />
							</button>
						</form>
					</li>
					<li>
						<form action="/news-admin/rest/restT" class="menuForm">
							<button class="bottomMenu">
								restTest
							</button>
						</form>
					</li>
				</ul>
			</div>
		</div>

	</c:if>

</body>
</html>