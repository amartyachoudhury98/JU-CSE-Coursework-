package domain;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
public class SearchFlights {
	@NotEmpty(message = "is required")
	String departureCity;
	@NotEmpty(message ="is required")
	String arrivalCity;
	@NotEmpty(message ="is required")
	String date;
	SearchFlights(String departure,String arrival,String dt){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		this.departureCity = departure;
		this.arrivalCity = arrival;
		try {
		Date dat = formatter.parse(dt);
		this.date=dt;
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
	}
	public SearchFlights() {
		
	}
	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}
	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}
	public void setDate(String date) {
		System.out.println(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.date = date;
	}
	/*public void setDate(Date date) {
		this.date = date;
	}*/
	public String getDepartureCity() {	
		return departureCity;
	}
	public String getArrivalCity() {
		return arrivalCity;
	}
	public String getDate() {
		return date;
	}
}
