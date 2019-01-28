package hometab;

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

import Bean.LikeDAO;
import Bean.NotificationDAO;
import Bean.PostDAO;
import model.Like;
import model.Notification;
import model.Post;
import model.User;

/**
 * Servlet implementation class LikeCheck
 */
@WebServlet("/LikeCheck")
public class LikeCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LikeCheck() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		User user = (User) (session.getAttribute("currentSessionUser"));

		try {
			Like like = new Like();
			like.setEmail(request.getParameter("email"));

			String postid = request.getParameter("postid");

			like.setPost(PostDAO.searchPost(postid));
			if (!LikeDAO.likedAlready(like)) {
				boolean allgood = LikeDAO.insertLike(like);

				if (allgood) {

					try {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Date date = new Date();
						Notification notification = new Notification();
						notification.setType("Like");
						notification.setDatetime(dateFormat.format(date));
						notification.setPostid(Integer.parseInt(postid));
						notification.setEmail(user.getEmail());

						Post post = PostDAO.searchPost(postid);
						notification.setUser(post.getUser());

						boolean valid = NotificationDAO.sendNotification(notification);
						if (valid) {
							System.out.println("Like Notification inserted successfully");
						} else
							System.out.println("Like Notification not inserted successfully");
					} catch (Throwable theException) {
						System.out.println(theException);
					}
					session.setAttribute("currentSessionUser", user);
					response.sendRedirect("home.jsp"); // logged-in page
				} else {
					PrintWriter out = response.getWriter();
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Like not valid');");
					out.println("location='home.jsp';");
					out.println("</script>");
				}
			}
			session.setAttribute("currentSessionUser", user);
			response.sendRedirect("home.jsp"); // logged-in page
		} catch (Throwable theException) {
			System.out.println(theException);
		}
	}
	
}