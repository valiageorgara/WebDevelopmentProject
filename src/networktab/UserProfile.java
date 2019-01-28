package networktab;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.UserDAO;
import db.HelpingFunctions;
import model.User;

/**
 * Servlet implementation class UserProfile
 */
@WebServlet("/UserProfile")
public class UserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		HttpSession session = request.getSession(true);
		User currentuser = (User) (session.getAttribute("currentSessionUser"));

		String em = request.getParameter("email");
		User user = UserDAO.searchUser(em);
		String connect=null;
		if (!(UserDAO.isContact(em, currentuser.getContacts()))) 
			connect = HelpingFunctions.requestSent(currentuser,em);
		else if ((UserDAO.isContact(em, currentuser.getContacts()))) 
			connect="friend";
		session.setAttribute("UserInfoData", user);
		session.setAttribute("connect", connect);     //to connect einai "yes" ama exoume steilei hdh connect, opote den emfanizw tipota, 
		response.sendRedirect("userprofile.jsp");     //"no" ama den exoume steilei, kai "friend" an eimaste hdh filoi

	}

}
