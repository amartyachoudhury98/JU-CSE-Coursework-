<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Signup</title>
<style>
	.error{color:red}
</style>
</head>
<body>
<h1>Signup</h1>
	<form:form action="/TravelThruAir_Spring/signup/processForm" modelAttribute="user">
		<Label>UserId :</Label><br>
		<form:input path ="userId" type = "text" /><form:errors path="userId" cssClass="error"/><br>
		
		<Label>Firstname :</Label><br>
		<form:input path ="firstname" type = "text" /><form:errors path="firstname" cssClass="error"/><br>
		<Label>Lastname :</Label><br>
		<form:input path ="lastname" type = "text" /><form:errors path="lastname" cssClass="error"/><br>
		<Label>Mobile No :</Label><br>
		<form:input path ="mobileNo" type = "text" /><form:errors path="mobileNo" cssClass="error"/><br>
		<Label>Password :</Label><br>
		<form:input path ="password" type = "password" /><form:errors path="password" cssClass="error"/><br>
		<input type="submit" value="submit">
	</form:form>
	<a href="./login">Login</a>  
</body>
</html>