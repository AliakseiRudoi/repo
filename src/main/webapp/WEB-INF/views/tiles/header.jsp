<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="mainHeaderDiv">
		<div id="topHeaderDiv">
			<spring:message code="local.message.headerMessage" />
		</div>
		<br>
		<br>
		<div id="localeDiv">
			<a
				href="?language=en <c:if test="${queryString!=null}">${queryString}</c:if>"><spring:message
					code="local.button.en" /></a> | <a
				href="?language=ru <c:if test="${queryString!=null}">${queryString}</c:if>"><spring:message
					code="local.button.ru" /></a>
		</div>
		<sec:authentication property="principal" var="principal" />
		<c:if test="${principal != 'anonymousUser'}">
			<form action="<c:url value="/news/logout" />" method="POST">
			<input type="submit" value="<spring:message
					code="local.button.logout" />">
			</form>
		</c:if>
	</div>
</body>
</html>