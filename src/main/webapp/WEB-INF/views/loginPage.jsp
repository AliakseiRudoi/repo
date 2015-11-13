<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head> 
<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.7.2.js" />" > </script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.form" />" > </script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.validate.js" />" > </script>
 <script type="text/javascript">

function validateLogin(input) {
	   if (input.value.length < 3 || input.value.length > 30 || input.value.length === 0) {
		  input.setCustomValidity("Please enter a value between 4 and 20 characters long.");   
	   }
	   else {
		  input.setCustomValidity("");
	   }
}
 
 function validatePassword(input) {
	   if (input.value.length < 6 || input.value.length > 35 || input.value.length === 0) {
			  input.setCustomValidity("Please enter a value between 4 and 100 characters long.");   
		   }
		   else {
			  input.setCustomValidity("");
		   }
	}
	  
	  </script>
</head>


	<div id="mainLoginDiv" >
		<!-- class="form-signin" -->
			<form  method="post" class="cmxform" id="signupForm"
				action="j_spring_security_check">
				<h2 class="form-signin-heading">
					<spring:message code="local.message.login"
						text="default text" />
				</h2>
				<div class="loginFormDiv">
				<div class="laberFormDiv">
				 <label for="inputLogin" >
				 <spring:message code="local.message.login" text="default text" /></label>
				 </div>
				 <div class="inputFormDiv">
					<input id="inputLogin" required="true" oninput="validateLogin(this)"  class="form-control" placeholder="Login"
					type="text" name='j_username' />
					</div> 
				</div>
				<div class="loginFormDiv">
				<div class="laberFormDiv">
					<label for="inputPassword" >
					<spring:message code="local.message.password" text="default text" /></label>
				</div>
				<div class="inputFormDiv">
					<input oninput="validatePassword(this)"  placeholder="Password" required="required" class="form-control" type="password"
					name='j_password' />
					</div>
				</div>
				 <div id="buttonLogin">
					<input class="submit" value="Submit" type="submit"
					value="<spring:message code="local.button.login"
						text="default text" />" />
						</div>
			</form>
			
			<div id="notValidDivMessage">
		${failMessage}
		</div>
	

	</div>
