<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<style>
	.errors{color:red}
</style>
</head>
<body>
<h1>Login</h1>
	<form:form action="/TravelThruAir_Spring/login/processForm" modelAttribute="user">
		<Label>UserId :</Label><br>
		<form:input path ="userId" type = "text" /><form:errors path="userId" cssClass="errors"/><br>
		<Label>Password :</Label><br>
		<form:input path ="password" type = "password" /><form:errors path="password" cssClass="errors"/><br>
		<input type="submit" value="submit">
	</form:form>
	
	<a href="./signup">signup</a>
</body>
</html>