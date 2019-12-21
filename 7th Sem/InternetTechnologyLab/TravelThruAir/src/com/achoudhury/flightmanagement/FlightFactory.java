package com.achoudhury.flightmanagement;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
public class FlightFactory implements Runnable,Observable{
	String[] cities = {
			"Kolkata","Delhi","Bangalore"} ;
	String[] airlinesCompanies = {
			"Indigo","Air India","Spicejet","AirAsia","Vistara","GoAir"
	};
	List<Observer> observers;
	
	FlightFactory(){
	}
	private Flight buildFlight(){
		Flight flight = null;
		try {
			int index1 = (int)( Math.random()*100 ) % cities.length;
			
			String temp = cities[index1];
			cities[index1] = cities[cities.length-1];
			cities[cities.length -1] = temp;
			int index2 = (int)( Math.random()*100 ) % (cities.length -1);
			String departureCity = cities[cities.length -1 ];
			String arrivalCity = cities[index2];
			String airline = airlinesCompanies[(int)(Math.random()*100)%airlinesCompanies.length];
			Date timeNow = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(timeNow);
			c.add(Calendar.DATE,1 +(int)(Math.random()*100)%7);
			Date departure = c.getTime();
			System.out.println(departure);
			c.add(Calendar.HOUR,(int)( Math.random()*100 ) % 24);
			c.add(Calendar.MINUTE,(int)( Math.random()*100 ) % 24);
			Date arrival = c.getTime();
			int passengers = 1 + (int)(Math.random()*1000) % 256;
			int cost = 2000 + (int)(Math.random()*10000) % 8000;
			String flightNo ="";
			flightNo += (char)((int)(Math.random()*26)+65);
			flightNo += (char)((int)(Math.random()*26)+65);
			flightNo += " ";
			flightNo += Integer.toString((int)(1 + Math.random()*1000));
			flight = new Flight(flightNo,airline,departureCity,arrivalCity,departure,arrival,passengers,cost);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return flight;
	}
	@Override
	public void add(Observer o) {
		if(observers == null) observers = new ArrayList<Observer>();
		observers.add(o);
	}
	@Override
	public void remove(Observer o) {
		observers.remove(o);
	}
	@Override
	public void inform(Object obj) {
		for(Observer obs : observers) {
			obs.update(obj);
		}
	}
	@Override
	public void run() {
		while(true) {
			Flight flight = buildFlight();
			inform(flight);
			try {
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
