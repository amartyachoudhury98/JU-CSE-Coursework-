package com.achoudhury.flightmanagement;

import java.util.Date;

public class DiscountFlight extends Flight {
	public int discountPercentage;
	DiscountFlight(String flightNo, String airLine, String departureCity, String arrivalCity, Date departure,
			Date arrival, int passengers,int ticketPrice,int discountPercentage) {
		super(flightNo, airLine, departureCity, arrivalCity, departure, arrival,passengers, ticketPrice);
		this.discountPercentage = discountPercentage;
	}
}
