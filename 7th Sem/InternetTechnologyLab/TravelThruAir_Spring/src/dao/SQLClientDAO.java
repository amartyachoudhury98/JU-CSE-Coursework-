package dao;

import java.util.List;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

import domain.Flight;
import domain.SearchFlights;
import domain.FlightLeg;
import domain.User;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
@Component
public class SQLClientDAO implements ClientDAO {
	private static final String JDBC_CONNECTION_URL = "jdbc:sqlserver://localhost\\MSSQLSERVER:60768;databaseName=travelThruAir;user=achoudhury98;password=1234";;
	private static final String GET_FLIGHTS = "SELECT * FROM Flight WHERE departure = ? AND arrival = ? AND CAST(departureTime AS Date) = ?";
	private static final String GET_FLIGHT_LEGS = "SELECT departFrom,departOn,arriveAt,arriveOn FROM  FlightLeg WHERE flightID = ? ";
	private static final String CREATE_USER = "INSERT INTO Users Values(?,?,?,?,?)";
	private static final String VALIDATE_USER = "SELECT * FROM Users WHERE userId = ? AND password = ?";
	private Connection connection;
	SQLClientDAO(){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(JDBC_CONNECTION_URL);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean createUser(User u) {
		boolean complete = false;
		try {
			PreparedStatement p = connection.prepareStatement(CREATE_USER);
			p.setString(1, u.getId());
			p.setString(2, u.firstname());
			p.setString(3, u.lastname());
			p.setString(4, u.mobileNo());
			p.setString(5, u.password());
			p.executeUpdate();
			complete = true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return complete;
	}
	
	@Override
	public boolean validateUser(String userId,String password) {
		boolean validated = false;
		try {
			PreparedStatement p = connection.prepareStatement(VALIDATE_USER);
			p.setString(1,userId);
			p.setString(2, password);
			ResultSet res = p.executeQuery();
			if(res.next()) validated = true;
		}
		catch(SQLException e) {
			
		}
		return validated;
	}
	@Override
	public List<Flight> getFlights(SearchFlights s){
		System.out.println(s.getDepartureCity() + s.getArrivalCity() +s.getDate().toString());
		List<Flight> flights = null;
		try {
			PreparedStatement p = connection.prepareStatement(GET_FLIGHTS);
			p.setString(1, s.getDepartureCity());
			p.setString(2, s.getArrivalCity());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			p.setDate(3,new java.sql.Date(format.parse(s.getDate()).getTime()));
			ResultSet res  = p.executeQuery();
			while(res.next()) {
				if(flights == null) flights = new ArrayList<Flight>();
				flights.add(new Flight(res.getString(1),res.getString(2),res.getString(3),new java.util.Date(res.getTimestamp(4).getTime()),new java.util.Date(res.getTimestamp(5).getTime()),res.getInt(6),res.getString(7)));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flights;
	}
	@Override
	public List<FlightLeg> getFlightLegs(String flightID){
		List<FlightLeg> legs = null;
		try {
			PreparedStatement p = connection.prepareStatement(GET_FLIGHT_LEGS);
			p.setString(1, flightID);
			ResultSet res = p.executeQuery();
			while(res.next()) {
				if(legs == null) legs = new ArrayList<>();
				legs.add(new FlightLeg(res.getString(1),new java.util.Date(res.getTimestamp(2).getTime()),res.getString(3),new java.util.Date(res.getTimestamp(4).getTime())));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return legs;
	}
}
