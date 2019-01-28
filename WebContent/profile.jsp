<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/profile.css">
<link rel="shortcut icon" href="css/images/in.png" />
<title>Profile | LinkedIn</title>
</head>
<body>

	<div class="topnav" id="myTopnav">
  <div id="logo">
    <a href="home.jsp"><img src="css/images/in.png" alt="" title="" /></a>
  </div>
  <a style="margin-left:470px;" href="home.jsp" ><img src="css/images/home.png" /><br>Home</a>
  <a href="mynetwork.jsp"><img src="css/images/network.png" /><br>My Network</a>
  <a href="jobs.jsp"><img src="css/images/jobs.png" /><br>Jobs</a>
  <a href="messaging.jsp"><img src="css/images/messages.png" /><br>Messaging</a>
  <a href="notifications.jsp"><img src="css/images/notifications.png" /><br>Notifications</a>
  <div class="dropdown">
    <button class="dropbtn" class="active"><img src="css/images/me.png" /><br><span>Me <i class="fa fa-caret-down"></i></span>
    </button>
    <div class="dropdown-content">
      <a href="profile.jsp">View Profile</a>
      <a href="index.jsp">Sign Out</a>
    </div>
  </div> 
  <a href="settings.jsp"><img src="css/images/settings.png" /><br>Settings</a>
  <a href="javascript:void(0);" style="font-size:15px;" class="icon" onclick="myFunction()">&#9776;</a>
</div>


<br><br>
	<br>
	<br>
	<div class="card">

		<img src=<c:out value="${sessionScope.currentSessionUser.getImageURL()}" />
			style="object-fit: cover; width: 230px; height: 230px;">
		<h1><c:out value="${sessionScope.currentSessionUser.getFirstname()}${' '}${sessionScope.currentSessionUser.getLastname()}" /></h1>
		<p>
			Carrier:
			<c:out value="${sessionScope.currentSessionUser.getCarrier()}" /></p>
		<p>
			Company:
			<c:out value="${sessionScope.currentSessionUser.getCompany()}" /></p>
		<p>
			Job Experience:
			<c:out value="${sessionScope.currentSessionUser.getJobexperience()}" /></p>
		<p>
			Education:
			<c:out value="${sessionScope.currentSessionUser.getEducation()}" /></p>
		<p>
			Skills:
			<c:out value="${sessionScope.currentSessionUser.getSkills()}" /></p>

		<button onclick="myFunction1()">
			Edit<i class="fa fa-edit"></i>
		</button>


	</div>


	<dialog id="myDialog">
	<h1>Edit Your Information</h1>

	<form method="post" action="EditProfile">

		<p>Carrier:</p>
		<input name="carrier" value="${sessionScope.currentSessionUser.getCarrier()}" />><br>

		<input type="radio" checked="checked" name="carrierRadio"
			onclick=getAnswer( 'public') value="public">Public <input
			type="radio" name="carrierRadio" onclick=getAnswer(
			'private') value="private">Private

		<p>Company:</p>
		<input name="company" value="${sessionScope.currentSessionUser.getCompany()}" />><br>

		<input type="radio" checked="checked" name="companyRadio"
			onclick=getAnswer( 'public') value="public">Public <input
			type="radio" name="companyRadio" onclick=getAnswer(
			'private') value="private">Private

		<p>Job Experience:</p>
		<input name="jobexperience"
			value="${sessionScope.currentSessionUser.getJobexperience()}" />><br> <input
			type="radio" checked="checked" name="jobexperienceRadio"
			onclick=getAnswer( 'public') value="public">Public <input
			type="radio" name="jobexperienceRadio" onclick=getAnswer(
			'private') value="private">Private

		<p>Education:</p>
		<input name="education" value="${sessionScope.currentSessionUser.getEducation()}" />><br>

		<input type="radio" checked="checked" name="educationRadio" onclick=getAnswer(
			'public') value="public">Public <input type="radio"
			name="educationRadio" onclick=getAnswer( 'private') value="private">Private

		<p>Skills:</p>
		<input name="skills" value="${sessionScope.currentSessionUser.getSkills()}" />><br>

		<input type="radio" checked="checked" name="skillsRadio" onclick=getAnswer(
			'public') value="public">Public <input type="radio"
			name="skillsRadio" onclick=getAnswer( 'private') value="private">Private
		<br> <br>
		<button type="submit">Save</button>
		<button type="button" onclick="window.location.href='profile.jsp'">Cancel</button>

	</form>

	</dialog>


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
		function myFunction1() {
			document.getElementById("myDialog").showModal();
			
		}
		
	</script>
	<script>
		function saveEdits() {

			//get the editable element
			var editElem = document.getElementById("edit");

			//get the edited element content
			var userVersion = editElem.innerHTML;

			//save the content to local storage
			localStorage.userEdits = userVersion;

			//write a confirmation to the user
			document.getElementById("update").innerHTML = "Edits saved!";

		}
	</script>



</body>
</html>