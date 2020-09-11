<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css">

<title>403 Forbidden</title>
</head>
<body>


	<div align="center">
		<img alt="forbidden" src="${pageContext.request.contextPath}/img/403-banner.jpg"> <br> <br>
		<p>${ message }</p>
		<p>Hi ${ pageContext.request.userPrincipal.name }, you don't have
			access to this page.</p>
		<br><a href="${pageContext.request.contextPath}/home" class="btn btn btn-primary">Home</a>
		<br>
		<br><a href="login?logout" class="btn btn-primary">Logout</a>
	</div>



	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>