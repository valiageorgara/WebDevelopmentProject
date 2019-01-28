<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>
<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/jobs.css">
<link rel="shortcut icon" href="css/images/in.png" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<title>Jobs | LinkedIn</title>
</head>
<body>

<div class="topnav" id="myTopnav">
		<div id="logo">
			<a href="home.jsp"><img src="css/images/in.png" alt="" title="" /></a>
		</div>
		<div id="home">
			<a style="float: right; font-family: Roboto Slab, serif;"
				href="jobs.jsp">Back to Jobs <img src="css/images/jobs.png"
				alt="" title="" /></a>
		</div>
	</div>
<br><br><br><br>

	
	<!--  ola ta jobs tou current user ginontai display -->
	<jsp:useBean id="getAllJobs" class="db.HelpingFunctions" />
			<c:forEach items="${sessionScope.currentSessionUser.getJobs()}" var="job">
			<c:set var="jobEmail" scope="session"
				value="${job.getUser().getEmail()}" />
			<jsp:useBean id="searchedUser" class="Bean.UserDAO" />
			<c:set var="user" scope="session"
				value="${searchedUser.searchUser(jobEmail)}" />
			<div class="tweet-body" style="margin-left:250px;">
				<h1 style="float: left;">
					<img src=<c:out value="${user.getImageURL()}"/>
						style="object-fit: cover; width: 50px; height: 50px;">
				</h1>
				<h4 style="margin: 5px; margin-left: 110px;">
					<c:out value="${user.getFirstname()}" />
					<c:out value="${user.getLastname()}" />
				</h4>

				<br>
				<br>
				<h4 style="margin-left: 34px;">
					Company:<c:out value="${job.getCompany()}" />
				</h4>
				<h4 style="margin-left: 34px;">
					Job title:<c:out value="${job.getJobtitle()}" />
				</h4>
				<h4 style="margin-left: 34px;">
					Location:<c:out value="${job.getLocation()}" />
				</h4>
				
				<div class="w3-dropdown-hover" class="btn">
    				<button class="w3-button w3-black">See who applied</button>
    				<div class="w3-dropdown-content w3-bar-block w3-border">
    				<jsp:useBean id="getAllApplications" class="Bean.ApplicationDAO" />
    				<c:set var="jobId" scope="session" value="${job.getJobsId()}" />
					<c:set var="allapplications" scope="session" value="${getAllApplications.searchApplication(jobId,job)}" />
					<c:forEach items="${allapplications}" var="appl">
						<jsp:useBean id="searchUser" class="Bean.UserDAO" />
						<c:set var="userlist" scope="session" value="${searchUser.searchUser(appl.getEmail())}" />
							<form method="post" action="UserProfile">
								<input type="hidden" name="email" value=<c:out value="${userlist.getEmail()}"/> />	
					      		<button class="w3-bar-item w3-button"><c:out value="${userlist.getFirstname()}${' '}${userlist.getLastname()}" /></button>
					      		
				      		</form>
					</c:forEach>      
			    	</div>
  				</div>
				<hr>
				
			</div>
			  <br>
			</c:forEach>
            
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
	