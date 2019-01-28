import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.ContactDAO;
import Bean.JobDAO;
import Bean.MessageDAO;
import Bean.NotificationDAO;
import Bean.PostDAO;
import Bean.UserDAO;
import model.User;

//import HelpingFunctions.emailExists;
/**
 * Servlet implementation class SignUpCheck
 */
@WebServlet("/SignUpCheck")
public class SignUpCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpCheck() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			User user = new User();
			user.setFirstname(request.getParameter("name"));
			user.setLastname(request.getParameter("last"));
			user.setPassword(request.getParameter("psw"));
			user.setEmail(request.getParameter("email"));
			user.setNumber(request.getParameter("number"));
			user.setImageURL(request.getParameter("image"));
			user.setCarrier("not yet filled");
			user.setCarrierRadio("public");
			user.setCompany("not yet filled");
			user.setCompanyRadio("public");
			user.setJobexperience("not yet filled");
			user.setJobexperienceRadio("public");
			user.setEducation("not yet filled");
			user.setEducationRadio("public");
			user.setSkills("not yet filled");
			user.setSkillsRadio("public");
			user.setLastLogin(dateFormat.format(date));
			user.setPosts(PostDAO.searchPostList(user.getEmail()));
			user.setContacts(ContactDAO.searchContactList(user.getEmail()));
			user.setNotifications(NotificationDAO.searchNotificationList(user.getEmail()));
			user.setJobs(JobDAO.searchJobList(user.getEmail()));
			user.setMessages(MessageDAO.searchMessageList(user.getEmail()));

			boolean valid = UserDAO.signup(user);

			if (valid) {

				HttpSession session = request.getSession(true);
				session.setAttribute("currentSessionUser", user);
				session.setAttribute("MessageList", null);
				session.setAttribute("OtherJobList", JobDAO.selectAllJobs());
				response.sendRedirect("home.jsp"); // logged-in page
			}

			else {
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Email already in use');");
				out.println("location='signup.jsp';");
				out.println("</script>");
			}
		}

		catch (Throwable theException) {
			System.out.println(theException);
		}

	}

}