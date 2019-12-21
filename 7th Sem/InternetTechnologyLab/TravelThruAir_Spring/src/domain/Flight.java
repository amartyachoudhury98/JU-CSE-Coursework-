package domain;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import dao.*;
public class Flight {
	public String flightID;
	public List<FlightLeg> legs;
	
	private int totalCost;
	private Date departure;
	private Date arrival;
	private String leavingFrom;
	private String arrivingAt;
	public String airlines;
	
	public Flight(String flightID,String leavingFrom,String arrivingAt,Date departure,Date arrival,int totalCost,String airlines){
		this.flightID = flightID;
		this.leavingFrom = leavingFrom;
		this.arrivingAt = arrivingAt;
		this.departure = departure;
		this.arrival = arrival;
		this.totalCost = totalCost;
		this.airlines = airlines;
		legs = null;
	}
	public String getFlightID() {
		return flightID;
	}
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}
	public List<FlightLeg> getLegs() {
		return legs;
	}
	public void setLegs(List<FlightLeg> legs) {
		this.legs = legs;
	}
	public int getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	public Date getDeparture() {
		return departure;
	}
	public void setDeparture(Date departure) {
		this.departure = departure;
	}
	public Date getArrival() {
		return arrival;
	}
	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}
	public String getLeavingFrom() {
		return leavingFrom;
	}
	public void setLeavingFrom(String leavingFrom) {
		this.leavingFrom = leavingFrom;
	}
	public String getArrivingAt() {
		return arrivingAt;
	}
	public void setArrivingAt(String arrivingAt) {
		this.arrivingAt = arrivingAt;
	}
	public String getAirlines() {
		return airlines;
	}
	public void setAirlines(String airlines) {
		this.airlines = airlines;
	}
	public String getFlightId() {
		return flightID;
	}
}
