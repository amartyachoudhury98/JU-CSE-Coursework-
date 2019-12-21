package com.achoudhury.flightmanagement;

import java.sql.SQLException;
import java.util.Iterator;
import java.lang.Math;
public class Discounter implements Runnable{
	CurrentFlights currentflights;
	Discounter(CurrentFlights currentflights){
		this.currentflights = currentflights;
	}
	@Override
	public void run() {
		while(true) {
				try {
				SQLClient sqlClient = SQLClient.getInstance();
				synchronized(currentflights) {
					for(Iterator<Flight> itr = currentflights.flights.iterator();itr.hasNext();) {
						int random = (int)(Math.random()*100);
						Flight flight = itr.next();  
						if(random < 50) {
							itr.remove();
							DiscountFlight df = new DiscountFlight(flight.flightNo,flight.airLine,flight.departureCity,flight.arrivalCity,flight.departure,flight.arrival,flight.passengers,flight.ticketPrice,(int)(Math.random()*100)+1);
							currentflights.discountflights.add(df);
							sqlClient.discount(df.flightNo,df.discountPercentage);
							System.out.println("discounted flight " + df.flightNo);
						}
					}
				}
				Thread.sleep(5*60*1000);
				}
				catch (SQLException e) {
					e.printStackTrace();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}		
		} 
		
	}
}
