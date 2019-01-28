import java.io.IOException;

import Bean.ContactDAO;
import Bean.JobDAO;
import Bean.MessageDAO;
import Bean.NotificationDAO;
import Bean.PostDAO;
import Bean.UserDAO;
import model.User;

import model.Job;
import model.Message;
import model.Post;

import java.io.PrintWriter;
import java.util.*;

import db.HelpingFunctions;
import knn.knnAlgorithm;
import knn.skillsAlgorithm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginCheck
 */
@WebServlet("/LoginCheck")
public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginCheck() {
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

			User user = new User();
			user.setEmail(request.getParameter("email"));
			user.setPassword(request.getParameter("psw"));

			boolean valid = UserDAO.login(user);

			if (valid) {
				user.setPosts(PostDAO.searchPostList(user.getEmail()));
				user.setContacts(ContactDAO.searchContactList(user.getEmail()));
				user.setNotifications(NotificationDAO.searchNotificationList(user.getEmail()));
				user.setJobs(JobDAO.searchJobList(user.getEmail()));
				user.setMessages(MessageDAO.searchMessageList(user.getEmail()));

				HttpSession session = request.getSession(true);

				if ((user.getEmail()).equals("admin@gmail.com")) {
					session.setAttribute("userlist", UserDAO.selectAllUsers());
					response.sendRedirect("admin.jsp"); // admin page
				} else {
					//gia to home
					List<Post> postlist = knnAlgorithm.knnImplementationPost(user);
					
					//gia ta jobs
					List<Job> skillsjoblist=skillsAlgorithm.skillsImplementation(user);
					List<Job> knnjoblist=knnAlgorithm.knnImplementationJob(user, skillsjoblist);
					List<Job> otherjoblist=HelpingFunctions.OtherJobs(user,skillsjoblist,knnjoblist);
					
					HelpingFunctions.ChangeLogin(user);
					
					// gia thn teleutaia sunomilia pou tha emfanizetai arxika
					List<Message> messagelist = HelpingFunctions.getLastConversation(user);
					User contact = null;
					if (!(user.getMessages().isEmpty())) {
						contact = UserDAO.searchUser(user.getMessages().get(0).getContactemail());
					}
					
					session.setAttribute("currentSessionUser", user);
					session.setAttribute("contactSession", contact);
					session.setAttribute("MessageList", messagelist);
					session.setAttribute("PostList", postlist);
					session.setAttribute("SkillsJobList", skillsjoblist);
					session.setAttribute("KnnJobList", knnjoblist);
					session.setAttribute("OtherJobList", otherjoblist);
					response.sendRedirect("home.jsp"); // logged-in page
				}
			}

			else {
				// response.sendRedirect("index.jsp"); // error page
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Invalid email or password. Try again to sign in or sign up.');");
				out.println("location='index.jsp';");
				out.println("</script>");
			}
		}

		catch (Throwable theException) {
			System.out.println(theException);
		}
	}

}