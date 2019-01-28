<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/mynetwork.css">
<link rel="shortcut icon" href="css/images/in.png" />
<title>My Network | LinkedIn</title>
</head>
<body>
	
<div class="topnav" id="myTopnav">
  <div id="logo">
    <a href="home.jsp"><img src="css/images/in.png" alt="" title="" /></a>
  </div>
  <a style="margin-left:470px;" href="home.jsp" ><img src="css/images/home.png" /><br>Home</a>
  <a href="mynetwork.jsp" class="active"><img src="css/images/network.png" /><br>My Network</a>
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
<br><br><br><br>
<div class="card" style="position:fixed; margin-left:20px; max-width:230px;">

		<img src=<c:out value="${sessionScope.currentSessionUser.getImageURL()}"/> style="object-fit: cover; width: 230px; height: 230px;">
		<p><c:out value="${sessionScope.currentSessionUser.getFirstname()}"/>, you have <c:out value="${sessionScope.currentSessionUser.getContacts().size()}"/> connections so far.</p>
		
</div>
<div class="mydiv" style="margin-left:270px;">
	<form class="searchbox_1" method="post" action="SearchCheck">
		<input type="search" class="search_1" placeholder="Who are you searching for?" name="input"/>
		<button type="submit" class="submit_1" value="search">&nbsp;</button>
	</form>
	
	<div  class="card">
	<h2>People you may know</h2>	
						
						<c:forEach items="${sessionScope.SearchedUsers}" var="user">
									<div class="card-inline" >
						
				<form method= "post" action="UserProfile" >
										
			<button name="email" value=<c:out value="${user.getEmail()}"/>>
				<img src=<c:out value="${user.getImageURL()}"/> style="object-fit: cover; width: 100px; height: 100px;">
				<p><c:out value="${user.getFirstname()}" /> <c:out value="${user.getLastname()}" /></p>
				<c:if test="${user.getCarrier() != 'not yet filled' && user.getCarrierRadio() == 'public'}" > 
					<p><c:out value="${user.getCarrier()}"/></p>
				</c:if>
					
				<c:if test="${user.getCompany() != 'not yet filled' && user.getCompanyRadio() == 'public'}" > 
					<p><c:out value="${user.getCompany()}"/></p>
				</c:if>
				</button>
			
			</form>	</div> </c:forEach>
	 </div>
	
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
	