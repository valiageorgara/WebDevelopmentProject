<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/notifications.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="shortcut icon" href="css/images/in.png" />
<title>Notifications | LinkedIn</title>
</head>
<body>

	<div class="topnav" id="myTopnav">
		<div id="logo">
			<a href="home.jsp"><img src="css/images/in.png" alt="" title="" /></a>
		</div>
		<a style="margin-left: 470px;" href="home.jsp"><img
			src="css/images/home.png" /><br>Home</a> <a href="mynetwork.jsp"><img
			src="css/images/network.png" /><br>My Network</a> <a
			href="jobs.jsp"><img src="css/images/jobs.png" /><br>Jobs</a>
		<a href="messaging.jsp"><img src="css/images/messages.png" /><br>Messaging</a>
		<a href="notifications.jsp" class="active"><img
			src="css/images/notifications.png" /><br>Notifications</a>
		<div class="dropdown">
			<button class="dropbtn">
				<img src="css/images/me.png" /><br>
				<span>Me <i class="fa fa-caret-down"></i></span>
			</button>
			<div class="dropdown-content">
				<a href="profile.jsp">View Profile</a> <a href="index.jsp">Sign
					Out</a>
			</div>
		</div>
		<a href="settings.jsp"><img src="css/images/settings.png" /><br>Settings</a>
		<a href="javascript:void(0);" style="font-size: 15px;" class="icon"
			onclick="myFunction()">&#9776;</a>
	</div>

	<br>
	<br>
	<br>
	<br>
	<div class="card"
		style="position: fixed; margin-left: 20px; max-width: 230px;">
		<img
			src=<c:out value="${sessionScope.currentSessionUser.getImageURL()}"/>
			style="object-fit: cover; width: 230px; height: 230px;">
		<p>
			<c:out value="${sessionScope.currentSessionUser.getFirstname()}" />
			, you have
			<c:out
				value="${sessionScope.currentSessionUser.getNotifications().size()}" />
			notifications.
		</p>
	</div>

	<div class="mydiv" style="margin-left: 270px;">
		<h3>Your Connection Requests:</h3>
		<c:forEach
			items="${sessionScope.currentSessionUser.getNotifications()}"
			var="notification">
			<jsp:useBean id="searchedUser" class="Bean.UserDAO" />
			<c:set var="user" scope="session"
				value="${searchedUser.searchUser(notification.getEmail())}" />
			<c:choose>
				<c:when test="${notification.getType() eq 'Request'}">
					<div class="box">

						
							<a href="UserProfile?email=${user.getEmail()}"><img src=<c:out value="${user.getImageURL()}"/>
								style="object-fit: cover; width: 70px; height: 70px; display: inline-block; vertical-align: middle;"> </a>
							<p style="display: inline-block; vertical-align: middle;">
								<a href="UserProfile?email=${user.getEmail()}"><c:out value="${user.getFirstname()}" />
								<c:out value="${user.getLastname()}" /></a>
								sent you a connection request.
							</p>
							<form method="post" action="AcceptRequest">
							<input type="hidden" name="email"
								value=<c:out value="${user.getEmail()}"/> />
						
						<div class="date">
							<c:out value="${notification.getDatetime()}" />
						</div>
						<input type="number" name="id"
							value=<c:out value="${notification.getIdnotifications()}"/>
							style="opacity: 0;">
						
							<button class="button1" name="accept" value="Yes">Accept</button>
							<button class="button1" name="accept" value="No">Decline</button>
						</form>
					</div>

				</c:when>
			</c:choose>
		</c:forEach>
		<h3>Other notifications:</h3>
		<c:forEach
			items="${sessionScope.currentSessionUser.getNotifications()}"
			var="notification">
			<jsp:useBean id="searchedUser1" class="Bean.UserDAO" />
			<c:set var="user" scope="session"
				value="${searchedUser1.searchUser(notification.getEmail())}" />

			<c:choose>
				<c:when test="${notification.getType() eq 'Like'}">
					<div class="box">
						<form method="post" action="ViewPost">
							<img src=<c:out value="${user.getImageURL()}"/>
								style="object-fit: cover; width: 70px; height: 70px; display: inline-block; vertical-align: middle;">
							<p style="display: inline-block; vertical-align: middle;">
								<c:out value="${user.getFirstname()}" />
								<c:out value="${user.getLastname()}" />
								liked one of your posts.
							</p>
							<div class="date">
								<c:out value="${notification.getDatetime()}" />
							</div>
							<input type="number" name="id"
								value=<c:out value="${notification.getIdnotifications()}"/>
								style="opacity: 0;">
							<button class="button1" name="PostId"
								value=<c:out value="${notification.getPostid()}"/>>View
								Post</button>
						</form>
					</div>
				</c:when>
				<c:when test="${notification.getType() eq 'Comment'}">
					<div class="box">
						<form method="post" action="ViewPost">
							<p>
								<img src=<c:out value="${user.getImageURL()}"/>
									style="object-fit: cover; width: 70px; height: 70px;">
								<c:out value="${user.getFirstname()}" />
								<c:out value="${user.getLastname()}" />
								commented one of your posts.
							</p>
							<div class="date">
								<c:out value="${notification.getDatetime()}" />
							</div>
							<input type="number" name="id"
								value=<c:out value="${notification.getIdnotifications()}"/>
								style="opacity: 0;">
							<button class="button1" type="hidden" name="PostId"
								value=<c:out value="${notification.getPostid()}"/>>View
								Post</button>
						</form>
					</div>
				</c:when>
			</c:choose>
		</c:forEach>
	</div>
	<script>
		function myFunction() {
			var x = document.getElementById("myTopnav");
			if (x.className === "topnav") {
				x.className += " responsive";
			} else {
				x.className = "topnav";
			}
		}
	</script>

</body>
</html>
