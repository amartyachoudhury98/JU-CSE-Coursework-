<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List" %>
<%@page import ="com.achoudhury.flightmanagement.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TraveThruAir</title>
</head>
<body>
	<h1>Welcome to Travel Thru Air !</h1>
	<h3>Search for Flights :</h3>
	<form action="FlightController" method="get">
		
		<!--<input name="departure" "type="text" placeholder="City"> 
		
		<input name="arrival" "type="text" placeholder="City">-->
		<label>Flying From :</label>
		<select name="departure">
			<option value="Delhi">Delhi</option>
			<option value="Kolkata">Kolkata</option>
			<option value="Bangalore">Bangalore</option>
		</select>
		<label>Flying To:</label>
		<select name="arrival">
			<option value="Delhi">Delhi</option>
			<option value="Kolkata">Kolkata</option>
			<option value="Bangalore">Bangalore</option>
		</select>
		<label>Airlines:</label>
		<select name="airlines">
			<option value="Any">Any</option>
			<option value="Indigo">Indigo</option>
			<option value="AirAsia">AirAsia</option>
			<option value="Air India">Air India</option>
			<option value="GoAir">GoAir</option>
			<option value="Vistara">Vistara</option>
		</select>
		<label>Departing:</label>
		<input name="date" type="date"> 
		<button>Search</button>
	</form>
	<h2>Discounts</h2>
	<table border="1">
		<tr>
		<th>Flight Number</th>
		<th>Airline</th>
		<th>Departing</th>
		<th>Arrival</th>
		<th>TicketPrice(Rs.)</th>
		<th>Passengers</th>
		</tr>
		<% 
		if(request.getAttribute("discounts") != null){
			List<DiscountFlight> discountedFlights= (List<DiscountFlight>)request.getAttribute("discounts");
			for(DiscountFlight df: discountedFlights){
				out.print("<tr>");
				out.print("<td>");
				out.print(df.flightNo);
				out.print("</td>");
				out.print("<td>");
				out.print(df.airLine);
				out.print("</td>");
				out.print("<td>");
				out.print(df.departure.toString());
				out.print("</td>");
				out.print("<td>");
				out.print(df.arrival.toString());
				out.print("</td>");
				out.print("<td>");
				out.print("<strike>"+Integer.toString(df.ticketPrice)+"</strike>");
				out.print(df.ticketPrice*(100-df.discountPercentage)/100);
				out.print("</td>");
				out.print("<td>");
				out.print(df.passengers);
				out.print("</td>");
				out.print("</tr>");
			}
		}
		%>
	</table>
	
	<h2>Normal Rate</h2>
	<table border="1">
		<tr>
		<tr>
		<th>Flight Number</th>
		<th>Airline</th>
		<th>Departing</th>
		<th>Arrival</th>
		<th>TicketPrice(Rs.)</th>
		<th>Passengers</th>
		</tr>
		<% 
		if(request.getAttribute("normals") != null){
			List<Flight> flights= (List<Flight>)request.getAttribute("normals");
			for(Flight f: flights){
				out.print("<tr>");
				out.print("<td>");
				out.print(f.flightNo);
				out.print("</td>");
				out.print("<td>");
				out.print(f.airLine);
				out.print("</td>");
				out.print("<td>");
				out.print(f.departure.toString());
				out.print("</td>");
				out.print("<td>");
				out.print(f.arrival.toString());
				out.print("</td>");
				out.print("<td>");
				out.print(f.ticketPrice);
				out.print("</td>");
				out.print("<td>");
				out.print(f.passengers);
				out.print("</td>");
				out.print("</tr>");
			}
		}
		%>	</table>
	</body>
	</html>
