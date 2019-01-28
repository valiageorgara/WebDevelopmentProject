<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
	xmlns:style="http://www.w3.org/1999/xhtml"
	xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">
<head>
<link rel="shortcut icon" href="css/images/in.png" />
<link rel="stylesheet" href="css/admin.css">

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

<title>LinkedIn | Admin</title>
</head>
<body>

	<div class="topnav" id="myTopnav">
		<div id="logo">
			<a href="home.jsp"><img src="css/images/in.png" alt="" title="" /></a>
		</div>
		<div id="home">
			<a style="float: right; font-family: Roboto Slab, serif;"
				href="index.jsp">Sign Out<img src="css/images/home.png" alt=""
				title="" /></a>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3">
				<div class="nav-side-menu"
					th:replace="profile_menu :: nav-bar-profile"></div>
				<!--end of side bar-->
			</div>
			<!--end of col-3 for the bar -->

			<div id="my_chats" class="col-sm-9">
				<h1>List Of Users</h1>
				<div id="mytable" class="table-responsive">
					<table class="table table-hover" >
						<tr>
							<th class="text-center">First Name</th>
							<th class="text-center">Last Name</th>
							<th class="text-center">Email</th>
							<th class="text-center">User's Information</th>
						</tr>
						
						
						
						<c:forEach var="user" items="${sessionScope.userlist}" varStatus="ctr">
						<tr>
							<td class="text-center"><c:out value="${user.getFirstname()}" /></td>


							<td class="text-center"><c:out value="${user.getLastname()}" /></td>


							<td class="text-center"><c:out value="${user.getEmail()}" /></td>


							<td class="text-center">

								<form method= "post" action="UserInfo">
									<button type="hidden" name="email"
										value="${user.getEmail()}">View</button>
								</form>		
							</td>
							</tr>
						</c:forEach>


					</table>
				</div>
			</div>
		</div>
	</div>


	<script th:src="@{/js/jquery.js}"></script>
	<script th:src="@{/js/bootstrap.min.js}"></script>
	<script type="text/javascript" th:src="@{/js/profile.js}"
		src="js/profile.js"></script>

</body>
</html>