<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/main.css" type="text/css" rel="stylesheet" />

<title>Employee Task Tracker</title>

</head>
<body>


	<div style="margin: 0 auto" align=center>
		<h1>
			<label> Employee Task Tracker</label>
		</h1>

		<img alt="Logo" src="img/aer_lingus_logo.jpg">
		<form action="user.login" id="loginForm" method="post">

			<table>

				<tr>
					<td><label>EMP ID</label></td>
					<td>:</td>
					<td><input type="text" id="empID" name="empID" /></td>
				</tr>

				<tr>
					<td><label>Password</label></td>
					<td>:</td>
					<td><input type="password" id="password" name="password" /></td>
				</tr>


			</table>
			<input type="submit" value="Submit"  /><br /> <br />
			<!--  <a
				href="${pageContext.request.contextPath}/WEB-INF/jsp/fragments/header.jsp"
				target="">Forgot password ? Click here !!</a> -->

			<a href="#" onclick="sendTo();">Forgot password ? Click here !!</a>

		</form>

	</div>
</body>
</html>