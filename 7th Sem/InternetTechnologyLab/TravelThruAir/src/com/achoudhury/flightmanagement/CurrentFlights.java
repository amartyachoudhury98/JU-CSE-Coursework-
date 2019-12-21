package com.achoudhury.flightmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
public class CurrentFlights {
	public List<Flight> flights;
	public List<DiscountFlight> discountflights;
	CurrentFlights(){
		flights = new ArrayList<>();
		discountflights = new ArrayList<>();
	}
}
