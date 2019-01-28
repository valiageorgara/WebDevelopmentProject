<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/home.css">
<link rel="shortcut icon" href="css/images/in.png" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/ratchet/2.0.2/css/ratchet.css"
	rel="stylesheet" />
<title>Home | LinkedIn</title>
</head>
<body>

	
<div class="topnav" id="myTopnav">
  <div id="logo">
    <a href="home.jsp"><img src="css/images/in.png" alt="" title="" /></a>
  </div>
  <a class="active" style="margin-left:470px;" href="home.jsp" ><img src="css/images/home.png" /><br>Home</a>
  <a href="mynetwork.jsp" ><img src="css/images/network.png" /><br>My Network</a>
  <a href="jobs.jsp"><img src="css/images/jobs.png" /><br>Jobs</a>
  <a href="messaging.jsp"><img src="css/images/messages.png" /><br>Messaging</a>
  <a href="notifications.jsp"><img src="css/images/notifications.png" /><br>Notifications</a>
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
	<br>
	<br>
	<br>

	<div class="card" style="position: fixed; margin-left: 20px">

		<img
			src=<c:out value="${sessionScope.currentSessionUser.getImageURL()}"/>
			style="object-fit: cover; width: 230px; height: 230px;">

		<p>
			Welcome,
			<c:out value="${sessionScope.currentSessionUser.getFirstname()}" />
		</p>
		<p>
			<a href="profile.jsp">Go to profile</a>
			<br>
			<a href="mynetwork.jsp">Grow your network</a>
		</p>

	</div>

	<div class="mydiv">
		<div class="tweet-body">
			<form id="form1" runat="server" method="post" action="PostCheck">
				<textarea class="status" name="status"
					placeholder="Share an article, photo, video ,audio or idea"></textarea>
				<br> <img class="myimg" id="output" /> <br> <input
					type="button" id="get_file" value="Image"> <input
					type="file" accept="image/*" id="my_file"
					onchange="loadFile(event)" name="image" /> <input type="button"
					id="get_file2" value="Video" /> <input type="file"
					accept="video/*" id="my_file2" onchange="loadFile(event)"
					name="video" /> <input type="button" id="get_file3" value="Audio" />
				<input type="file" accept="audio/*" id="my_file3"
					onchange="loadFile(event)" name="audio" /> <input
					style="background-color: #0080FF; float: right;" id="get_file"
					type="submit" value="Post" />


			</form>
		</div>

		<br>
		<!--  ola ta post tou current user kai twn epafwn tou ginontai display -->
		<jsp:useBean id="getAllPosts" class="db.HelpingFunctions" />
		<c:set var="allposts" scope="session"
			value="${getAllPosts.getAllPosts(sessionScope.currentSessionUser)}" />


		<c:forEach items="${allposts}" var="post">
			<c:set var="postEmail" scope="session"
				value="${post.getUser().getEmail()}" />
			<jsp:useBean id="searchedUser" class="Bean.UserDAO" />
			<c:set var="user" scope="session"
				value="${searchedUser.searchUser(postEmail)}" />
			<!--  -->
			<div class="tweet-body">
				<h1 style="float: left;">
					<img src=<c:out value="${user.getImageURL()}"/>
						style="object-fit: cover; width: 50px; height: 50px;">
				</h1>
				<h4 style="margin: 5px; margin-left: 110px;">
					<c:out value="${user.getFirstname()}" />
					<c:out value="${user.getLastname()}" />
					<p style="float: right;">
						<c:out value="${post.getDatetime()}" />
					</p>
				</h4>

				<br> <br>
				<h4 style="margin-left: 34px;">
					<c:out value="${post.getText()}" />
				</h4>
				<form method="post" action="LikeCheck">
					<button class="btn" type="submit">
						<i class="fa fa-heart"></i> Like
					</button>
					<p>
						<c:out value="${post.getLikes().size()}" />
						likes
					</p>
					<input type="hidden" name="postid"
						value=<c:out value="${post.getPostsId()}"/>> <input
						type="hidden" name="email"
						value=<c:out value="${sessionScope.currentSessionUser.getEmail()}"/> />
				</form>
				<hr>
				<div class="commentdiv">

					<h4 style="margin-left: 34px;">Comments</h4>

					<c:forEach items="${post.getComments()}" var="comment">
						<h1 style="float: left;">
							<img src=<c:out value="${comment.getCommenterImage()}"/>
								style="object-fit: cover; width: 28px; height: 28px;">
						</h1>
						<h5 style="margin-left: 30px;">
							<strong><c:out value="${comment.getCommenterName()}" />
							</strong>
							<p style="float: right;">
								<c:out value="${comment.getDatetime()}" />
							</p>
						</h5>

						<h5 style="margin-left: 30px;">
							<c:out value="${comment.getCommentstext()}" />
						</h5>
						<br>
					</c:forEach>
					<form method="post" action="CommentCheck">



						<input type="hidden" name="postid"
							value=<c:out value="${post.getPostsId()}"/>> <input
							type="text" class="comment" name="comment"
							placeholder="Add your comment here.." autocomplete="off"
							style="display: inline-block;">
						<button style="display: inline-block;" class="btn" type="submit">
							<i class="fa fa-comment"></i>Comment
						</button>


					</form>
				</div>
			</div>
			<br>
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
