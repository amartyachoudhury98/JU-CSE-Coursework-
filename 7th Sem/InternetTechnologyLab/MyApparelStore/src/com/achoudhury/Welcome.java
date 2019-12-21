package com.achoudhury;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
public class Welcome extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchstr = request.getParameter("searchstring");
		String preference =request.getParameter("preference");
		
		String username = request.getSession().getAttribute("username").toString();
		DAOClient daoClient = (DAOClient)(request.getServletContext().getAttribute("daoclient"));
		if(preference == null && searchstr==null) {
			preference = daoClient.getPreference(username);
			searchstr="";
		}
		ArrayList<Item> items = daoClient.getItemsForUser(username,searchstr,preference);
		request.setAttribute("items",items);
		request.setAttribute("searchstring", searchstr);
		request.setAttribute("preference", preference);
		request.getRequestDispatcher("welcome.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
