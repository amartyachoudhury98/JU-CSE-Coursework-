package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import domain.*;
import dao.*;
@Component
public class FlightServiceImpl implements FlightService {
	ClientDAO daoclient;
	
	@Autowired
	void setClient(ClientDAO client) {
		this.daoclient = client;
	}
	@Override
	public List<Flight> getFlights(SearchFlights s) {
		List<Flight> flights = daoclient.getFlights(s);
		try {
 		for(Flight flight:flights) {
 			flight.legs = daoclient.getFlightLegs(flight.flightID);
 		}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
 		return flights;
	}
	@Override
	public boolean createUser(User u) {
		return daoclient.createUser(u);
	}
	@Override
	public boolean validateUser(String userId,String password) {
		return daoclient.validateUser(userId, password);
	}
}
