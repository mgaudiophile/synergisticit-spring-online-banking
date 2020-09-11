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

<title>Home</title>

<style>
.jumbotron {
	background-image: url("${pageContext.request.contextPath}/img/online-banking-banner.jpg");
	height: 450px;
	background-size: cover;
}
</style>
</head>
<body>

	<div class="jumbotron jumbotron-fluid">
		<div class="container"></div>
	</div>
	<br>
	<br>
	<div class="container">
		<div class="row">
			<div class="col">
				<div class="card" style="width: 22rem;">
					<div class="card-header">Admins</div>
					<div class="card-body">
						<p class="card-text">Access advanced features with your admin
							account</p>
						<a href="${pageContext.request.contextPath}/admin" class="btn btn-primary">Enter</a>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="card" style="width: 22rem;">
					<div class="card-header">Customers</div>
					<div class="card-body">
						<p class="card-text">Access online banking features with your
							customer account</p>
						<a href="${pageContext.request.contextPath}/customer" class="btn btn-primary">Enter</a>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>