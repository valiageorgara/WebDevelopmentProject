<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page
	import="Bean.*,model.*,db.*,java.sql.*, java.util.*,java.io.Serializable,javax.persistence.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/home.css">
<link rel="shortcut icon" href="css/images/in.png" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/ratchet/2.0.2/css/ratchet.css"
	rel="stylesheet" />
<title>Notifications | LinkedIn</title>
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
  <a href="notifications.jsp" class="active"><img src="css/images/notifications.png" /><br>Notifications</a>
  <div class="dropdown">
    <button class="dropbtn"><img src="css/images/me.png" /><br><span>Me <i class="fa fa-caret-down"></i></span>
    </button>
    <div class="dropdown-content">
      <a href="profile.jsp">View Profile</a>
      <a href="index.jsp">Sign Out</a>
    </div>
  </div> 
  <a href="settings.jsp"><img src="css/images/settings.png" /><br>Settings</a>
  <a href="javascript:void(0);" style="font-size:15px;" class="icon" onclick="myFunction()">&#9776;</a>
</div>
	<br>
	<br><br><br>

	<div class="card" style="position: fixed; margin-left: 20px">

		<img src=<c:out value="${sessionScope.currentSessionUser.getImageURL()}"/>
			style="object-fit: cover; width: 230px; height: 230px;">

		<p>
			Welcome,
			<c:out value="${sessionScope.currentSessionUser.getFirstname()}"/></p>
		<p>
			<a href="mynetwork.jsp">Grow your network</a>
		</p>

	</div>

	<div class="mydiv">
		
		<div class="tweet-body">
			<h1 style="float: left;">
				<img src=<c:out value="${sessionScope.post.getUser().getImageURL()}"/>
					style="object-fit: cover; width: 50px; height: 50px;">
			</h1>
			<h4 style="margin:5px; margin-left:110px;">
				<c:out value="${sessionScope.post.getUser().getFirstname()}"/>
				<c:out value="${sessionScope.post.getUser().getLastname()}"/> <p style="float:right;"><c:out value="${sessionScope.post.getDatetime()}"/></p>
			</h4>
			
			<br><br>
			<h4 style="margin-left:34px;"><c:out value="${sessionScope.post.getText()}"/></h4>
			<p>
				<c:out value="${sessionScope.post.getLikes().size()}"/> likes
			</p>
			<hr>
		<div class="commentdiv">
			
			<h4 style="margin-left:34px;">Comments</h4> 
			
<c:forEach items="${sessionScope.post.getComments()}" var="comment">
 
			<h1 style="float: left;">
				<img
					src=<c:out value="${comment.getCommenterImage()}"/>
					style="object-fit: cover; width: 28px; height: 28px;">
			</h1>
			<h5 style="margin-left:30px;">
				<strong><c:out value="${comment.getCommenterName()}"/> </strong><p style="float:right;"><c:out value="${comment.getDatetime()}"/></p>
			</h5>
			
			<h5 style="margin-left:30px;"><c:out value="${comment.getCommentstext()}"/> </h5>
			<br>
			
</c:forEach>
			
		</div>
		</div>
		<br>
	</div>

	<script>
		function myFunction() {
			var x = document.getElementById("myTopnav1");
			if (x.className === "topnav1") {
				x.className += " responsive";
			} else {
				x.className = "topnav1";
			}
		}

		function submitFormAjax() {
			//alert('I might have sent something, somewhere...Perhaps...');
		}
	
		document.getElementById('get_file').onclick = function() {
			document.getElementById('my_file').click();
		};

		document.getElementById('get_file2').onclick = function() {
			document.getElementById('my_file2').click();
		};

		document.getElementById('get_file3').onclick = function() {
			document.getElementById('my_file3').click();
		};

		var loadFile = function(event) {
			var output = document.getElementById('output');
			output.src = URL.createObjectURL(event.target.files[0]);
		};
	</script>

</body>
</html>
