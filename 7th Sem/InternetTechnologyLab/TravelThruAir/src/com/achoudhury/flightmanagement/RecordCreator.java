package com.achoudhury.flightmanagement;
import java.sql.SQLException;

public class RecordCreator implements Runnable,Observer{
	CurrentFlights currentflights;
	RecordCreator(CurrentFlights currentflights){
	this.currentflights = currentflights;
	}
	public void update(Object o) {
		Flight f = (Flight)o;
		synchronized(currentflights) {
			currentflights.flights.add(f);
			try {
				SQLClient sqlClient = SQLClient.getInstance();
				sqlClient.addnew(f);
				System.out.println("added flight " + f.flightNo);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void run() {
		
	}
}
