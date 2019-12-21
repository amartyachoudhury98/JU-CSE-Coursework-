package domain;
import java.util.Date;
public class FlightLeg {
	String leavingFrom;
	Date leavingOn;
	String arrivingAt;
	Date arrivingOn;
	public FlightLeg(String leavingFrom,Date leavingOn,String arrivingAt,Date arrivingOn) {
		this.leavingFrom = leavingFrom;
		this.leavingOn = leavingOn;
		this.arrivingAt = arrivingAt;
		this.arrivingOn = arrivingOn;
	}
	public String getLeavingFrom() {
		return leavingFrom;
	}
	public void setLeavingFrom(String leavingFrom) {
		this.leavingFrom = leavingFrom;
	}
	public Date getLeavingOn() {
		return leavingOn;
	}
	public void setLeavingOn(Date leavingOn) {
		this.leavingOn = leavingOn;
	}
	public String getArrivingAt() {
		return arrivingAt;
	}
	public void setArrivingAt(String arrivingAt) {
		this.arrivingAt = arrivingAt;
	}
	public Date getArrivingOn() {
		return arrivingOn;
	}
	public void setArrivingOn(Date arrivingOn) {
		this.arrivingOn = arrivingOn;
	}
}
