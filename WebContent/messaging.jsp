<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/messaging.css">
<link rel="shortcut icon" href="css/images/in.png" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<jsp:useBean id="returnUnreadCount" class="db.HelpingFunctions" />
<c:set var="unreadMsgs" scope="session" value="${returnUnreadCount.getUnread(sessionScope.currentSessionUser.getMessages())}" />
<title>Messaging(<c:out value="${unreadMsgs}"/>) | LinkedIn</title>
</head>
<body>

	
<div class="topnav" id="myTopnav">
  <div id="logo">
    <a href="home.jsp"><img src="css/images/in.png" alt="" title="" /></a>
  </div>
  <a style="margin-left:470px;" href="home.jsp" ><img src="css/images/home.png" /><br>Home</a>
  <a href="mynetwork.jsp"><img src="css/images/network.png" /><br>My Network</a>
  <a href="jobs.jsp" ><img src="css/images/jobs.png" /><br>Jobs</a>
  <a href="messaging.jsp" class="active"><img src="css/images/messages.png" /><br>Messaging</a>
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

	<br><br><br><br>

<div class="centered" style="display: flex;flex-direction: row; align-items:flex-start;">
		<form method="post" action="MessageCheck">
			

				<c:set var="contactemail" value="null" />
				<c:forEach items="${sessionScope.currentSessionUser.getMessages()}"
					var="message">
					<c:choose>
						<c:when test="${contactemail ne message.getContactemail()}">
							<c:set var="contactemail" value="${message.getContactemail()}" />
							<c:if test="${message.getUnread() eq '1' }">
							<strong style="background-color:light-grey;">
							</c:if>
							
							<button type="submit" name="contactemail"
				value="${message.getContactemail()}">
							<div class="allmessages" style="float:left;">
								<div class="centered" style="display: flex;flex-direction: row;align-items: center;">
								<jsp:useBean id="searchUser" class="Bean.UserDAO" />
								<c:set var="user" scope="session" value="${searchUser.searchUser(contactemail)}" />
									<img
										src=<c:out value="${user.getImageURL()}"/>>
									<h3>
										<c:out
											value="${user.getFirstname()}" />
										<c:out
											value="${user.getLastname()}" />
									</h3>
								</div>
								<p>
									<c:out value="${message.getText()}" />
								</p>
							</div>
						</button>
						<c:if test="${message.getUnread() eq '1' }">
							</strong>
							</c:if>
						<br>
						</c:when>
					</c:choose>
				</c:forEach>

			
		</form>

		<c:if test="${sessionScope.contactSession eq null}">
			<h3 style="margin-top:200px; margin-left:520px;">You have no messages yet.</h3>
		</c:if>
		<c:if test="${sessionScope.contactSession ne null}">
			<form method="post" action="SendMessage">
				<div class="thismessage" style="margin-top:0%;margin-left:0px;">
					<div class="centered" style="display: flex;flex-direction: row;align-items: center;">
						<img src="${sessionScope.contactSession.getImageURL()}" />
						<h2>
							<c:out value="${sessionScope.contactSession.getFirstname()}" />
							<c:out value="${sessionScope.contactSession.getLastname()}" />
						</h2>
					</div>
					<br>
					<div style="overflow:scroll; overflow-x: hidden; height:370px;" id="yourDivID">
					<c:forEach items="${sessionScope.MessageList}" var="message">
					<c:if test="${message.getState() eq 'send'}">
						<div class="send">
							<c:out value="${message.getText()}"/>
						</div>
					</c:if>
					<c:if test="${message.getState() eq 'receive'}">
						<div class="receive" style="background-color: #e1e3df;">
							<c:out value="${message.getText()}"/>
						</div>
					</c:if>
					<br>
					</c:forEach>
					</div>
					<div class="centered" style="display: flex;flex-direction: row; align-items:center;">
					<input type="text" name="text" autocomplete="off" placeholder="Write your message..." style="width:280px;height:37px;"/>
					<input style="width:250px;height:50px;" type="hidden" name="contactemail"
						value="${sessionScope.contactSession.getEmail()}" />
						<button class="button1" style="border: none;cursor: pointer;margin: 8px 0;"><img src="css/images/send.png" style="border-radius:50%;height:40px;width:40px;"></button>
				</div>
				</div>
			</form>
		</c:if>
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
	
	var element = document.getElementById("yourDivID");
	element.scrollTop = element.scrollHeight;
	</script>

</body>
</html>