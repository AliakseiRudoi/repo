<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
"http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/adminHome.css" />">
 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/menyBar.css" />">
 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tilesMainTemplate.css" />">
 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/addNews.css" />">
 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/searchForm.css" />">
 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/author.css" />">  
 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/editSingleNews.css" />">
 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tags.css" />">
 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/login.css" />">
 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/searchResults.css" />">
 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/pagination.css" />">
 
<script type="text/javascript" src="<c:url value="/resources/js/checkboxes.js" />" > </script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.3.js" />" > </script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.simplePagination.js" />" > </script>
 <script type="text/javascript" >
 $(function() {
    $('#light-theme').pagination({
        pages: '${paginNum}',
        cssStyle: 'light-theme',
        hrefTextPrefix: '',
        currentPage: '${pageNumber}'
        	
    });
});

</script>
  

</head>
<body>
<div id="mainDiv">

	<div class="headerAndFooterDiv">
		<tiles:insertAttribute name="header" />
	</div>
	
	<div id="bodyMainDiv">
	
	<table id="mTable">
	<tr>
	<td id="td">
	<div id="menuDiv">
		<tiles:insertAttribute name="menu" />
		</div>
	</td>
	<td>
	<div id="bodyDiv">
		<tiles:insertAttribute name="body" />
		</div>
	</td>
	</tr>
	</table>
	
	</div>
	<div class="headerAndFooterDiv">
		<tiles:insertAttribute name="footer" />
	</div>


</div>
</body>
</html>