package com.achoudhury.flightmanagement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class FlightListener
 *
 */
@WebListener
public class FlightListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public FlightListener() {
        // TODO Auto-generated constructor stub
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	CurrentFlights currentflights = new CurrentFlights();
    	FlightFactory factory = new FlightFactory();
    	RecordCreator rc = new RecordCreator(currentflights);
    	factory.add(rc);
    	Thread flightProduction = new Thread(factory);
    	flightProduction.start();
    	Thread recordDeleter = new Thread(new RecordDeleter(currentflights));
    	recordDeleter.start();
    	
    	
    	Thread recordCreator = new Thread(rc);
    	recordCreator.start();
    	Thread discounter = new Thread(new Discounter(currentflights));
    	discounter.start();
    	System.out.println("all threads started");
    	
    }
	
}
