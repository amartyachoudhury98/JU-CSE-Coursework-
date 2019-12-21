package com.achoudhury.flightmanagement;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat ;
@WebServlet("/FlightController")
public class FlightController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public FlightController() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String departureCity = request.getParameter("departure");
		String arrivalCity = request.getParameter("arrival");
		String date = request.getParameter("date");
		String airlines = request.getParameter("airlines");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			SQLClient sqlClient = SQLClient.getInstance();
			Date dt = formatter.parse(date);
			List<Flight> normalFlights = sqlClient.getnormalFlights(departureCity,arrivalCity,dt,airlines);
			List<DiscountFlight> discountFlights = sqlClient.getdiscountFlights(departureCity,arrivalCity,dt,airlines);
			request.setAttribute("normals",normalFlights);
			request.setAttribute("discounts",discountFlights);
			request.getRequestDispatcher("/flightSchedule.jsp").forward(request, response);
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
