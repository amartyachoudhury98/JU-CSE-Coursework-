package service;
import java.util.List;

import org.springframework.stereotype.Component;

import domain.*;
@Component
public interface FlightService {
	List<Flight> getFlights(SearchFlights s);
	boolean createUser(User u);
	boolean validateUser(String userId,String password);
}
