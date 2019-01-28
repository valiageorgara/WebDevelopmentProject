<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/userprofile.css">
<link rel="shortcut icon" href="css/images/in.png" />
<title>LinkedIn User Profile</title>
</head>
<body>

	<div class="topnav" id="myTopnav">
		<div id="logo">
			<a href="home.jsp"><img src="css/images/in.png" alt="" title="" /></a>
		</div>
		<a style="margin-left: 470px;" href="home.jsp"><img
			src="css/images/home.png" /><br>Home</a> <a href="mynetwork.jsp"
			class="active"><img src="css/images/network.png" /><br>My
			Network</a> <a href="jobs.jsp"><img src="css/images/jobs.png" /><br>Jobs</a>
		<a href="messaging.jsp"><img src="css/images/messages.png" /><br>Messaging</a>
		<a href="notifications.jsp"><img
			src="css/images/notifications.png" /><br>Notifications</a>
		<div class="dropdown">
			<button class="dropbtn">
				<img src="css/images/me.png" /><br>Me <i
					class="fa fa-caret-down"></i>
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

	<c:if test="${sessionScope.connect eq 'friend'}">
		<form method="post" action="MessageCheck">
	</c:if>
	<c:if test="${sessionScope.connect ne 'friend'}">
		<form method="post" action="SendRequest">
	</c:if>

	<img src=<c:out value="${sessionScope.UserInfoData.getImageURL()}"/>
		style="object-fit: cover; width: 230px; height: 230px;">
	<div class="card">
		<h1>
			<c:out value="${sessionScope.UserInfoData.getFirstname()}" />
			<c:out value="${sessionScope.UserInfoData.getLastname()}" />
		</h1>

		<h2>Short bio:</h2>
		<c:if
			test="${sessionScope.connect eq 'friend'&&sessionScope.UserInfoData.getCarrier() ne 'not yet filled' || sessionScope.UserInfoData.getCarrierRadio() eq 'public'&&sessionScope.UserInfoData.getCarrier() ne 'not yet filled'}">
			<p>
				Works as a <strong> <c:out
						value="${sessionScope.UserInfoData.getCarrier()}" /></strong>
			</p>
		</c:if>
		<c:if
			test="${sessionScope.connect eq 'friend'&&sessionScope.UserInfoData.getCompany() ne 'not yet filled' || sessionScope.UserInfoData.getCompanyRadio() eq 'public'&&sessionScope.UserInfoData.getCompany() ne 'not yet filled'}">
			<p>
				at <strong> <c:out
						value="${sessionScope.UserInfoData.getCompany()}" />
				</strong>
			</p>
		</c:if>
		<c:if
			test="${sessionScope.connect eq 'friend'&&sessionScope.UserInfoData.getJobexperience() ne 'not yet filled' || sessionScope.UserInfoData.getJobexperienceRadio() eq 'public'&&sessionScope.UserInfoData.getJobexperience() ne 'not yet filled'}">
			<p>
				Worked as a <strong> <c:out
						value="${sessionScope.UserInfoData.getJobexperience()}" /></strong>
			</p>
		</c:if>

		<c:if
			test="${sessionScope.connect eq 'friend'&&sessionScope.UserInfoData.getEducation() ne 'not yet filled' || sessionScope.UserInfoData.getEducationRadio() eq 'public'&&sessionScope.UserInfoData.getEducation() ne 'not yet filled'}">
			<p>
				Studied at <strong> <c:out
						value="${sessionScope.UserInfoData.getEducation()}" />
				</strong>
			</p>
		</c:if>
		<c:if
			test="${sessionScope.connect eq 'friend'&&sessionScope.UserInfoData.getSkills() ne 'not yet filled' || sessionScope.UserInfoData.getSkillsRadio() eq 'public'&&sessionScope.UserInfoData.getSkills() ne 'not yet filled'}">
			<p>
				Likes <strong> <c:out
						value="${sessionScope.UserInfoData.getSkills()}" /></strong>
			</p>
		</c:if>
		<c:if test="${sessionScope.connect eq 'friend'}">
			<button class="button1" name="contactemail"
				value="${sessionScope.UserInfoData.getEmail()}">Message</button>
		</c:if>
		<c:if test="${sessionScope.connect eq 'no'}">
			<button class="button1" name="email"
				value="${sessionScope.UserInfoData.getEmail()}">Connect</button>
		</c:if>
		<br>
		<br>
	</div>
	</form>



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