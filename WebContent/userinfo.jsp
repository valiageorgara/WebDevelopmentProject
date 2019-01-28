<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="model.User"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
	xmlns:style="http://www.w3.org/1999/xhtml"
	xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">
<head>
<link rel="shortcut icon" href="css/images/in.png" />


<meta charset="UTF-8" />
<!--bootstrap and jquery-->


<!--fonts and icons-->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Raleway" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet" />


<!--custom css/js-->
<link rel="stylesheet" type="text/css" th:href="@{/css/profile.css}"
	href="css/profile.css" />

<link rel="stylesheet" type="text/css" th:href="@{/css/main.css}"
	href="css/main.css" />
<link rel="stylesheet" href="css/userinfo.css">
<title>LinkedIn | User Profile</title>
</head>
<body>
	<div class="topnav" id="myTopnav">
		<div id="logo">
			<a href="home.jsp"><img src="css/images/in.png" alt="" title="" /></a>
		</div>
		<div id="home">
			<a style="float: right; font-family: Roboto Slab, serif;"
				href="admin.jsp">Back<img src="css/images/home.png" alt=""
				title="" /></a>
		</div>
	</div>
	<br><br><br><br>
	<div class="card" style="background-color: white;">

		<img src=<c:out value="${sessionScope.UserInfoData.getImageURL()}" />
			style="object-fit: cover; width: 230px; height: 230px;">

		<p>
			Name:
			<c:out value="${sessionScope.UserInfoData.getFirstname()}" />
		</p>
		<p>
			Lastname:
			<c:out value="${sessionScope.UserInfoData.getLastname()}" />
		</p>
		<p>
			Email:
			<c:out value="${sessionScope.UserInfoData.getEmail()}" />
		</p>
		<p>
			Number:
			<c:out value="${sessionScope.UserInfoData.getNumber()}" />
		</p>
		<p>
			Carrier:
			<c:out value="${sessionScope.UserInfoData.getCarrier()}" />
		</p>
		<p>
			Company:
			<c:out value="${sessionScope.UserInfoData.getCompany()}" />
		</p>
		<p>
			Job Experience:
			<c:out value="${sessionScope.UserInfoData.getJobexperience()}" />
		</p>
		<p>
			Education:
			<c:out value="${sessionScope.UserInfoData.getEducation()}" />
		</p>
		<p>
			Skills:
			<c:out value="${sessionScope.UserInfoData.getSkills()}" />
		</p>
		
		<form method="post" action="CreateXML">
            <button class="btn-info" type="submit" name="email" value="${sessionScope.UserInfoData.getEmail()}">
                <i class="fa fa-download"  aria-hidden="true"></i>
            </button>
		</form>

	</div>
	<script>
		function hide() {
			document.getElementById("button_show").style.display = "none";
		}
	</script>

	<script th:src="@{/js/jquery.js}" src="js/jquery.js"></script>
	<script th:src="@{/js/bootstrap.min.js}" src="js/bootstrap.min.js"></script>
	<script th:src="@{/js/profile.js}" src="js/profile.js"></script>
	<script type="text/javascript"
		src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
	<script type="text/javascript" th:src="@{/js/daterangepicker.js}"
		src="js/daterangepicker.js"></script>
</body>

</html>