<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.achoudhury.Item" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Online Apparel Store - Welcome!</title>
<link rel="stylesheet" href="./util/css/welcome_styles.css">
<link rel="icon" href="./util/img/hangerIcon.png">
</head>
<body>
	<%
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.html");
	}
	%>
	<form action="Welcome">
	<%
	out.print("<input type='text' name='searchstring' placeholder='search'" +" value="+request.getAttribute("searchstring").toString() +">");
	if(request.getAttribute("preference").toString().equals("discount")){
	out.print("<input type='radio' name='preference' value='discount' checked>Discount");
	out.print("<input type='radio'	name='preference' value='newArrival'>New Arrivals");
	}
	else{
		out.print("<input type='radio' name='preference' value='discount'>Discount");
		out.print("<input type='radio'	name='preference' value='newArrival' checked>New Arrivals");	
	}
	%>
	<button>Search</button>
	</form>
	<table border="1">
	<tr>
	<th>View</th>
	<th>Description</th>
	<th>Price(Rs.)</th>	
	</tr>
	<%
		if(request.getAttribute("items") != null){
		ArrayList<Item> items = (ArrayList<Item>)request.getAttribute("items");
		for(Item item:items){
			out.print("<tr>");
			out.print("<td>");
			out.print("<img src="+item.imageUrl+" height='100' width='100'>");
			out.print("</td>");
			out.print("<td>");
			out.print(item.description);
			out.print("</td>");
			out.print("<td>");
			if(item.isDiscounted){
			out.print("<strike>"+item.price+"</strike>");
			out.print("<br>");
			out.print(item.price-item.discount_perc*item.price/100);
			}
			else{
				out.print(item.price);
			}
			out.print("</td>");
			out.print("</tr>");
			
		}}
	%>
	</table>
	<form action="Logout">
	<input type= "submit" value="logout">
	</form>
</body>
</html>