package com.achoudhury.flightmanagement;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
public class SQLClient {
	private static SQLClient instance;
	private Connection connection;
	private static final String JDBC_CONNECTION_URL ="jdbc:sqlserver://localhost\\MSSQLSERVER:60768;databaseName=travelThruAir;user=achoudhury98;password=1234";
    private static final String INSERT_NEW_RECORD = " INSERT INTO flights VALUES(?,?,?,?,?,?,?,?,'False',NULL)";
    private static final String GET_NORMAL_FLIGHT_RECORDS = "SELECT * FROM flights WHERE departure_city=? AND arrival_city=? AND CAST(departure AS Date) = ? AND discount='False'";
    private static final String GET_DISCOUNT_FLIGHT_RECORDS = "SELECT * FROM flights WHERE departure_city=? AND arrival_city=? AND CAST(departure AS Date) = ? AND discount='True'";
    private static final String GET_NORMAL_FLIGHT_RECORDS_FILTER_AIRLINES = "SELECT * FROM flights WHERE departure_city=? AND arrival_city=? AND CAST(departure AS Date) = ? AND airline = ? AND discount='False'";
    private static final String GET_DISCOUNT_FLIGHT_RECORDS_FILTER_AIRLINES = "SELECT * FROM flights WHERE departure_city=? AND arrival_city=? AND CAST(departure AS Date) = ? AND airline = ? AND discount='True'";
    private static final String REMOVE_FLIGHT_RECORD = "DELETE FROM flights WHERE flight_no = ?";
    private static final String DISCOUNT_FLIGHT_RECORD ="UPDATE flights SET discount_percentage = ? , discount='True' WHERE flight_no=?";
	private static final String REMOVE_COMPLETED_FLIGHTS = "DELETE FROM flights WHERE departure < ?";
    private SQLClient() throws SQLException{
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				this.connection = DriverManager.getConnection(JDBC_CONNECTION_URL);
			 } 
			 catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
	}
	public Connection getConnection() {
		return this.connection;
	}
	public static SQLClient getInstance() throws SQLException{
		if(instance == null || instance.connection.isClosed()) {
			instance = new SQLClient();
		}
		return instance;
	}
	
	public void addnew(Flight f) throws SQLException{
		Connection conn= this.getConnection();
		PreparedStatement p = conn.prepareStatement(INSERT_NEW_RECORD);
		p.setString(1, f.flightNo);
		p.setString(2, f.airLine);
		p.setString(3, f.departureCity);
		p.setString(4, f.arrivalCity);
		p.setTimestamp(5, new java.sql.Timestamp(f.departure.getTime()));
		p.setTimestamp(6, new java.sql.Timestamp(f.arrival.getTime()));
		p.setInt(7,f.passengers);
		p.setInt(8,f.ticketPrice);
		p.executeUpdate();
		p.close();
	}
	
	public List<DiscountFlight> getdiscountFlights(String departure,String arrival,Date dt,String airlines) throws SQLException{
		List<DiscountFlight> discountFlights = null;
		Connection conn = this.getConnection();
		PreparedStatement p;
		if(airlines.equals("Any")) {
			p = conn.prepareStatement(GET_DISCOUNT_FLIGHT_RECORDS);
			p.setString(1,departure);
			p.setString(2,arrival);
			p.setDate(3,new java.sql.Date(dt.getTime()));
		}
		else {
		p = conn.prepareStatement(GET_DISCOUNT_FLIGHT_RECORDS_FILTER_AIRLINES);
		p.setString(1,departure);
		p.setString(2,arrival);
		p.setDate(3,new java.sql.Date(dt.getTime()));
		p.setString(4, airlines);
		}
		ResultSet rs = p.executeQuery();
		while(rs.next()) {
			if(discountFlights == null) discountFlights = new ArrayList<>();
			discountFlights.add(new DiscountFlight(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),new Date(rs.getTimestamp(5).getTime()),
					new Date(rs.getTimestamp(6).getTime()),rs.getInt(7),rs.getInt(8),rs.getInt(10)));
		}
		return discountFlights;
	}
	
	public List<Flight> getnormalFlights(String departure,String arrival,Date dt,String airlines) throws SQLException{
		List<Flight> flights = null;
		Connection conn = this.getConnection();
		PreparedStatement p;
		if(airlines.equals("Any")) {
			p = conn.prepareStatement(GET_NORMAL_FLIGHT_RECORDS);
			p.setString(1,departure);
			p.setString(2,arrival);
			p.setDate(3,new java.sql.Date(dt.getTime()));
		}
		else {
		p = conn.prepareStatement(GET_NORMAL_FLIGHT_RECORDS_FILTER_AIRLINES);
		p.setString(1,departure);
		p.setString(2,arrival);
		p.setDate(3,new java.sql.Date(dt.getTime()));
		p.setString(4, airlines);
		}
		ResultSet rs = p.executeQuery();
		while(rs.next()) {
			if(flights == null) flights = new ArrayList<>();
			flights.add(new Flight(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),new Date(rs.getTimestamp(5).getTime()),
					new Date(rs.getTimestamp(6).getTime()),rs.getInt(7),rs.getInt(8)));
		}
		p.close();
		return flights;
	}
	public void removeflight(String flight_no) throws SQLException{
		Connection conn = this.getConnection();
		PreparedStatement p = conn.prepareStatement(REMOVE_FLIGHT_RECORD);
		p.setString(1, flight_no);
		p.executeUpdate();
		p.close();
		return;
	}
	public void removeCompletedFlights(Date dt) throws SQLException{
		Connection conn = this.getConnection();
		PreparedStatement p = conn.prepareStatement(REMOVE_COMPLETED_FLIGHTS);
		p.setTimestamp(1, new java.sql.Timestamp(dt.getTime()));
		System.out.println("removed "+ p.executeUpdate() + " outstanding records");
		
		p.close();
		return;
	}
	public void discount(String flight_no,int discountPercentage) throws SQLException{
		Connection conn = this.getConnection();
		PreparedStatement p = conn.prepareStatement(DISCOUNT_FLIGHT_RECORD);
		p.setString(2, flight_no);
		p.setInt(1, discountPercentage);
		p.executeUpdate();
		p.close();
		return;
	}
}	
