package com.achoudhury.flightmanagement;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
public class RecordDeleter implements Runnable{
	CurrentFlights currentFlights;
	RecordDeleter(CurrentFlights currentflights){
		this.currentFlights = currentflights;
	}
	@Override
	public void run() {
		try{
			SQLClient sqlclient= SQLClient.getInstance();
			sqlclient.removeCompletedFlights(new Date());
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		while(true) {
			try {
			SQLClient sqlClient = SQLClient.getInstance();
			
				Date timeNow = new Date();
				synchronized(currentFlights){
					for(Iterator<Flight> itr = currentFlights.flights.iterator();itr.hasNext();) {
						Flight flight  = itr.next();
						if(timeNow.after(flight.departure)) {
							sqlClient.removeflight(flight.flightNo);
							itr.remove();
							System.out.println("Deleted record for flight" + flight.flightNo);
						}
					}
					for(Iterator<DiscountFlight> itr = currentFlights.discountflights.iterator();itr.hasNext();) {
						DiscountFlight df = itr.next();
						if(timeNow.after(df.departure)){
							sqlClient.removeflight(df.flightNo);
							itr.remove();
							System.out.println("Deleted record for flight" + df.flightNo);
						}
					}
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		
	}
}
