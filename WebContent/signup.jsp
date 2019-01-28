<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="db.HelpingFunctions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/signup.css">
<link rel="shortcut icon" href="css/images/in.png" />
<title>LinkedIn Sign Up</title>
</head>
<body style="background-color: #BBD6E7;">
	<form method="post" action="SignUpCheck">
		<center>
			<table border="1" width="30%" cellpadding="5">
				<thead>
					<tr>
						<h1>Be great at what you do</h1>
						<h2>Get started - it's free.</h2>
					</tr>
				</thead>

				<div class="container">
					<label for="image"><b>Profile Photo</b></label><input id="image"
						type="text" name="image"><br> 
						<label for="name"><b>First
							Name</b></label> <input type="text" name="name" required> <label
						for="last"><b>Last Name</b></label> <input type="text" name="last"
						required> <label for="email"><b>Email</b></label> <input
						type="email" name="email" id="email" required><br> <label
						for="number"><b>Number</b></label> <input type="text"
						name="number"> <label for="psw"><b>Password</b></label> <input
						type="password" name="psw" id="password" required> <label
						for="psw"><b>Confirm Password</b></label> <input type="password"
						name="psw" id="confirm_password" required>

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

					<button type="submit">Join now</button>
				</div>
			</table>
		</center>
	</form>
</body>
</html>