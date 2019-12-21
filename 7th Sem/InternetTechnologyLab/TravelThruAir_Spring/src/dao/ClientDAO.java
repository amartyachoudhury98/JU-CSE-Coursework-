package dao;
import domain.*;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public interface ClientDAO {
	List<Flight> getFlights(SearchFlights s);
	List<FlightLeg> getFlightLegs(String flightId);
	boolean createUser(User u);
	boolean validateUser(String userId,String password);
}
