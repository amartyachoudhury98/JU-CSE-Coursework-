<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="java.util.List" %>
<%@page import="domain.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<style>
	.errors{color:red}
</style>
</head>
<body>
	<h1>Welcome ${user.userId} to Travel Thru Air</h1>
	<h3>Search for flights</h3>
	<form:form action="/TravelThruAir_Spring/home/processForm" modelAttribute = "search">
		<Label>Departure :</Label><br>
		<form:input path = "departureCity" type="text"/><form:errors path="departureCity" cssClass="errors"/><br>
		<Label>Arrival :</Label><br>
		<form:input path = "arrivalCity" type="text"/><form:errors path="arrivalCity" cssClass="errors"/><br>
		<Label>Date :</Label><br>
		<form:input path = "date" type="text"/><form:errors path="date" cssClass="errors"/><br>
		<input type="submit" value="submit"><br>
	</form:form>
	<h2>Flights</h2>
	<table border="1">
		<tr>
		<th>Flight Number</th>
		<th>Airline</th>
		<th>Departing</th>
		<th>Arrival</th>
		<th>TicketPrice(Rs.)</th>
		</tr>
	<%
	if(request.getAttribute("flights") != null){
		List<Flight> flights= (List<Flight>)request.getAttribute("flights");
		for(Flight f:flights){
			out.print("<tr>");
			out.print("<td>");
			out.print(f.flightID);
			out.print("</td>");
			out.print("<td>");
			out.print(f.airlines);
			out.print("</td>");
			out.print("<td>");
			out.print(f.getDeparture());
			out.print("</td>");
			out.print("<td>");
			out.print(f.getArrival());
			out.print("</td>");
			out.print("<td>");
			out.print(f.getTotalCost());
			out.print("</td>");
			out.print("</tr>");
			if(f.getLegs()!= null && f.getLegs().size() > 1){
				List<FlightLeg> legs = f.getLegs();
				out.print("<tr class=\"break\"><td colspan=\"5\">Legs</td></tr>");
				out.print("<table border=\"1\"><tr><th>Leaving from</th><th>Leaving On</th><th>Arriving At</th><th>Arriving On</th>");
				for(FlightLeg leg: legs){
					out.print("<tr>");
					out.print("<td>");
					out.print(leg.getLeavingFrom());
					out.print("</td>");
					out.print("<td>");
					out.print(leg.getLeavingOn());
					out.print("</td>");
					out.print("<td>");
					out.print(leg.getArrivingAt());
					out.print("</td>");
					out.print("<td>");
					out.print(leg.getArrivingOn());
					out.print("</td>");
					out.print("</tr>");
				}
			}
		}
	}
	%>
	</table>
	<a href="/TravelThruAir_Spring/login">Logout</a>
</body>
</html>