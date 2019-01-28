<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/jobs.css">
<link rel="shortcut icon" href="css/images/in.png" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>Jobs | LinkedIn</title>
</head>
<body>

	<div class="topnav" id="myTopnav">
		<div id="logo">
			<a href="home.jsp"><img src="css/images/in.png" alt="" title="" /></a>
		</div>
		<a style="margin-left: 470px;" href="home.jsp"><img
			src="css/images/home.png" /><br>Home</a> <a href="mynetwork.jsp"><img
			src="css/images/network.png" /><br>My Network</a> <a
			href="jobs.jsp" class="active"><img src="css/images/jobs.png" /><br>Jobs</a>
		<a href="messaging.jsp"><img src="css/images/messages.png" /><br>Messaging</a>
		<a href="notifications.jsp"><img
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
	<div class="mydiv">
		<div class="tweet-body" style="width: 1000px; margin: 0 auto;">
			<a class="myjobs" href="myjobs.jsp">${sessionScope.currentSessionUser.getJobs().size()}
				My Jobs</a>
			<p style="margin-left: 540px; display: inline;">
				Looking for talent?
				<button class="postbtn" type="submit"
					onclick="document.getElementById('id01').style.display='block'"
					style="display: inline;">
					<i class="fa fa-edit"></i>Post a job
				</button>
			</p>
		</div>
	</div>
	<br>
	<div id="id01" class="modal">

		<form method="post" class="modal-content animate form1"
			action="JobCheck">


			<div class="container">

				<h1 align="center"
					style="color: white; font-family: 'Roboto', sans-serif;">Post
					a job on LinkedIn today!</h1>
				<h1 align="center"
					style="color: white; font-family: 'Roboto', sans-serif;">Reach
					quality candidates with your job posting.</h1>
				<label for="company"></label> <br> <input type="text"
					placeholder="Company" name="company"
					style="width: 400px; margin-left: 150px;"> <br> <label
					for="jobtitle"></label> <input type="text" placeholder="Job Title"
					name="jobtitle" style="width: 400px; margin-left: 150px;">
				<label for="location"></label> <br> <input type="text"
					placeholder="Location" name="location"
					style="width: 400px; margin-left: 150px;"> <br>
				<button type="submit" class="postbtn"
					style="width: 400px; margin-left: 150px;">
					<strong>Start job posting</strong>
				</button>
				<br>
				<br>

			</div>


		</form>
	</div>

	<c:if test="${!(empty sessionScope.SkillsJobList)}">
		<h1>Jobs based on your skills:</h1>
		<c:set var="alljobs" scope="session"
			value="${sessionScope.SkillsJobList}" />
		<c:forEach items="${alljobs}" var="job">
			<c:set var="jobEmail" scope="session"
				value="${job.getUser().getEmail()}" />
			<jsp:useBean id="searchedUser" class="Bean.UserDAO" />
			<c:set var="user" scope="session"
				value="${searchedUser.searchUser(jobEmail)}" />
			<div class="tweet-body" style="margin-left: 250px;">
				<h1 style="float: left;">
					<img src=<c:out value="${user.getImageURL()}"/>
						style="object-fit: cover; width: 50px; height: 50px;">
				</h1>
				<h4 style="margin: 5px; margin-left: 110px;">
					<c:out value="${user.getFirstname()}" />
					<c:out value="${user.getLastname()}" />
				</h4>

				<br> <br>
				<h4 style="margin-left: 34px;">
					Company:
					<c:out value="${job.getCompany()}" />
				</h4>
				<h4 style="margin-left: 34px;">
					Job title:
					<c:out value="${job.getJobtitle()}" />
				</h4>
				<h4 style="margin-left: 34px;">
					Location:
					<c:out value="${job.getLocation()}" />
				</h4>
				<form method="post" action="ApplyCheck">
					<button class="btn" type="submit">
						<i class="fa fa-heart"></i> Apply
					</button>

					<input type="hidden" name="jobid"
						value=<c:out value="${job.getJobsId()}"/>> <input
						type="hidden" name="email"
						value=<c:out value="${sessionScope.currentSessionUser.getEmail()}"/> />
				</form>
				<hr>

			</div>
			<br>
		</c:forEach>
	</c:if>

	<c:if test="${!(empty sessionScope.KnnJobList)}">
		<h1>Jobs based on your previous activities:</h1>
		<c:set var="alljobs" scope="session" value="${sessionScope.KnnJobList}" />
		<c:forEach items="${alljobs}" var="job">
			<c:set var="jobEmail" scope="session"
				value="${job.getUser().getEmail()}" />
			<jsp:useBean id="searchedUser1" class="Bean.UserDAO" />
			<c:set var="user" scope="session"
				value="${searchedUser1.searchUser(jobEmail)}" />
			<div class="tweet-body" style="margin-left: 250px;">
				<h1 style="float: left;">
					<img src=<c:out value="${user.getImageURL()}"/>
						style="object-fit: cover; width: 50px; height: 50px;">
				</h1>
				<h4 style="margin: 5px; margin-left: 110px;">
					<c:out value="${user.getFirstname()}" />
					<c:out value="${user.getLastname()}" />
				</h4>

				<br> <br>
				<h4 style="margin-left: 34px;">
					Company:
					<c:out value="${job.getCompany()}" />
				</h4>
				<h4 style="margin-left: 34px;">
					Job title:
					<c:out value="${job.getJobtitle()}" />
				</h4>
				<h4 style="margin-left: 34px;">
					Location:
					<c:out value="${job.getLocation()}" />
				</h4>
				<form method="post" action="ApplyCheck">
					<button class="btn" type="submit">
						<i class="fa fa-heart"></i> Apply
					</button>

					<input type="hidden" name="jobid"
						value=<c:out value="${job.getJobsId()}"/>> <input
						type="hidden" name="email"
						value=<c:out value="${sessionScope.currentSessionUser.getEmail()}"/> />
				</form>
				<hr>

			</div>
			<br>
		</c:forEach>
	</c:if>
	
	<c:if test="${!(empty sessionScope.OtherJobList)}">
		<h1>Other Jobs:</h1>
		<c:set var="alljobs" scope="session" value="${sessionScope.OtherJobList}" />
		<c:forEach items="${alljobs}" var="job">
			<c:set var="jobEmail" scope="session"
				value="${job.getUser().getEmail()}" />
			<jsp:useBean id="searchedUser2" class="Bean.UserDAO" />
			<c:set var="user" scope="session"
				value="${searchedUser2.searchUser(jobEmail)}" />
			<div class="tweet-body" style="margin-left: 250px;">
				<h1 style="float: left;">
					<img src=<c:out value="${user.getImageURL()}"/>
						style="object-fit: cover; width: 50px; height: 50px;">
				</h1>
				<h4 style="margin: 5px; margin-left: 110px;">
					<c:out value="${user.getFirstname()}" />
					<c:out value="${user.getLastname()}" />
				</h4>

				<br> <br>
				<h4 style="margin-left: 34px;">
					Company:
					<c:out value="${job.getCompany()}" />
				</h4>
				<h4 style="margin-left: 34px;">
					Job title:
					<c:out value="${job.getJobtitle()}" />
				</h4>
				<h4 style="margin-left: 34px;">
					Location:
					<c:out value="${job.getLocation()}" />
				</h4>
				<form method="post" action="ApplyCheck">
					<button class="btn" type="submit">
						<i class="fa fa-heart"></i> Apply
					</button>

					<input type="hidden" name="jobid"
						value=<c:out value="${job.getJobsId()}"/>> <input
						type="hidden" name="email"
						value=<c:out value="${sessionScope.currentSessionUser.getEmail()}"/> />
				</form>
				<hr>

			</div>
			<br>
		</c:forEach>
	</c:if>

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
	<script>
		// Get the modal
		var modal = document.getElementById('id01');

		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
			}
		}
	</script>

</body>
</html>
