<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="model.User" %>
	<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="css/images/in.png" />
<title>LinkedIn Settings</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://fonts.googleapis.com/css?family=Roboto+Slab|Roboto:300,700"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">


<link rel="stylesheet" href="css/settings.css">


</head>

<body>

	<div class="topnav" id="myTopnav">
		<div id="logo">
			<a href="home.jsp"><img src="css/images/in.png" alt="" title="" /></a>
		</div>
		<div id="home">
			<a style="float: right; font-family: Roboto Slab, serif;"
				href="home.jsp">Back to Home <img src="css/images/home.png"
				alt="" title="" /></a>
		</div>
	</div>
	<br><br><br><br>
	<div class="a">
		<h1>
			<strong><c:out value="${sessionScope.currentSessionUser.getFirstname()}"/> , you are the boss of your account.</strong>
		</h1>
		<h1>Login and security</h1>
	</div>

	<div class="container">
		<div class="tab-slider--nav">
			<ul class="tab-slider--tabs">
				<li class="tab-slider--trigger active" rel="tab1">Email
					Settings</li>
				<li class="tab-slider--trigger" rel="tab2">Password Settings</li>
			</ul>
		</div>
		<div class="tab-slider--container">
			<div id="tab1" class="tab-slider--body">
				<h2>Change email addresses on your account</h2>
				<form method="post" class="modal-content animate"
					action="ChangeEmail">
					<div class="container">
						<fieldset>

							<label for="oldemail"><b>Current Email</b></label> <input
								type="email" name="oldemail" required> <br> <br>
							<label for="newemail"><b>New Email</b></label> <input
								type="email" name="newemail" id="email" required> <br>
							<br> <label for="newemail"><b>Confirm Email</b></label> <input
								type="email" name="newemail" id="confirm_email" required>
							<br>
						</fieldset>

						<script type="text/javascript">
							var email = document.getElementById("email"), confirm_email = document
									.getElementById("confirm_email");

							function validateEmail() {
								if (email.value != confirm_email.value) {
									confirm_email
											.setCustomValidity("Emails Don't Match");
								} else {
									confirm_email.setCustomValidity('');
								}
							}
							email.onchange = validateEmail;
							confirm_email.onkeyup = validateEmail;
						</script>

						<script>
							//Get the modal
							var modal = document.getElementById('id01');

							// When the user clicks anywhere outside of the modal, close it
							window.onclick = function(event) {
								if (event.target == modal) {
									modal.style.display = "none";
								}
							}
						</script>

						<br>
						<button type="submit">Save</button>
					</div>
				</form>
			</div>
			<div id="tab2" class="tab-slider--body">
				<h2>Choose a unique password to protect your account</h2>
				<form method="post" class="modal-content animate"
					action="ChangePassword">


					<div class="container">
						<fieldset>
							<label for="oldpassword"><b>Current Password</b></label><br>
							<input type="password" name="oldpassword" required> <br>
							<br> <label for="newpassword"><b>New Password</b></label> <input
								type="password" name="newpassword" id="password" required>
							<br> <br> <label for="newpassword"><b>Confirm
									Password</b></label> <input type="password" name="newpassword"
								id="confirm_password" required> <br> <br>
						</fieldset>
						<script type="text/javascript">
							var password = document.getElementById("password"), confirm_password = document
									.getElementById("confirm_password");

							function validatePassword() {
								if (password.value != confirm_password.value) {
									confirm_password
											.setCustomValidity("Passwords Don't Match");
								} else {
									confirm_password.setCustomValidity('');
								}
							}
							password.onchange = validatePassword;
							confirm_password.onkeyup = validatePassword;
						</script>

						<script>
							//Get the modal
							var modal = document.getElementById('id02');

							// When the user clicks anywhere outside of the modal, close it
							window.onclick = function(event) {
								if (event.target == modal) {
									modal.style.display = "none";
								}
							}
						</script>


						<button type="submit">Save</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js'></script>

	<script src="js/index.js"></script>

</body>
</html>
