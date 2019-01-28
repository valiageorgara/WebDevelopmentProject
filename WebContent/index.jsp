<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>                    
<head>    
<meta name="viewport" content="width=device-width, initial-scale=1">                
<link rel="stylesheet" href="css/index.css">
<link rel="shortcut icon" href="css/images/in.png" />
<title>Welcome to LinkedIn</title> 
</head>  
<body>
	<br>
	<br>  
	<h2 style="color: white; float: right; margin-right: 600px;">Welcome</h2> 
        
	<button onclick="document.getElementById('id01').style.display='block'" style="width: auto; float: right; margin-right: 660px;">Login</button> 
	<button onclick="window.location.href='signup.jsp'" style="width: auto; float: right; margin-right: -180px;">Sign up</button>   
             
	<div id="id01" class="modal">           
	            
		<form method="post" class="modal-content animate" action="LoginCheck">
			<div class="container"> 
				<label for="email"><b>Email</b></label><br> <input type="email"
					placeholder="Enter Email" name="email" required> <br>
				<label for="psw"><b>Password</b></label> <input type="password"
					placeholder="Enter Password" name="psw" required>
				<div id="error"></div>
				<button type="submit">Login</button>
			</div>
			           
			<div class="container" style="background-color: #f1f1f1">
				<button type="button"
					onclick="document.getElementById('id01').style.display='none'"
					class="cancelbtn">Cancel</button>
			</div>
		</form>
	</div>      
                       
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

</body>       
</html>
