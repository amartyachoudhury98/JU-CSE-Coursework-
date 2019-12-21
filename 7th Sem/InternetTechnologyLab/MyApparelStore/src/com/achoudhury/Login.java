package com.achoudhury;
import com.achoudhury.SQLClientDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//SQLClientDao sqlClient = new SQLClientDao();
		String userId = request.getParameter("username");
		String password = request.getParameter("passWd");
		DAOClient daoClient = (DAOClient)(request.getServletContext().getAttribute("daoclient"));
		if(daoClient.validateUser(userId, password)){
			HttpSession session = request.getSession();
			session.setAttribute("username",userId);
			request.getRequestDispatcher("/Welcome").forward(request, response);
		}
		else {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('invalid username or password')");
			out.println("</script>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
