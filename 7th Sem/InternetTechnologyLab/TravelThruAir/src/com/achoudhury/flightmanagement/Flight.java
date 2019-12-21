package com.achoudhury.flightmanagement;
import java.util.Date;
public class Flight {
	public String flightNo;
	public String airLine;
	public String departureCity;
	public String arrivalCity;
	public Date departure;
	public Date arrival;
	public int passengers;
	public int ticketPrice;
	Flight(String flightNo,String airLine,String departureCity,String arrivalCity,Date departure,Date arrival,int passengers,int ticketPrice){
		this.flightNo = flightNo;
		this.airLine =airLine;
		this.departureCity = departureCity;
		this.arrivalCity = arrivalCity;
		this.departure = departure;
		this.arrival = arrival;
		this.passengers = passengers;
		this.ticketPrice = ticketPrice;
	}
}
