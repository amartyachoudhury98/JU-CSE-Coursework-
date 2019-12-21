package com.achoudhury;
import com.achoudhury.SQLClientDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;
public class SignupServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//SQLClientDao sqlClient = new SQLClientDao();
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String userId =request.getParameter("username");
		String phNo =request.getParameter("phNo");
		String password = request.getParameter("passWd");
		String sex= request.getParameter("sex");
		DAOClient daoClient = (DAOClient)(request.getServletContext().getAttribute("daoclient"));
		if(daoClient.createUser(firstname, lastname, phNo, userId, password,sex)) {
			PrintWriter out = response.getWriter();
			HttpSession session = request.getSession();
			session.setAttribute("username",userId);
			request.getRequestDispatcher("/Welcome").forward(request, response);
		}
		else {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('username exists');");  
			out.println("</script>");
			//response.sendRedirect("./signup.html");
		}
	}
}
