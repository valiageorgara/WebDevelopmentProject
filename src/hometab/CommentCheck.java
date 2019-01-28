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

import Bean.CommentDAO;
import Bean.NotificationDAO;
import Bean.PostDAO;
import model.Comment;
import model.Notification;
import model.Post;
import model.User;

/**
 * Servlet implementation class CommentCheck
 */
@WebServlet("/CommentCheck")
public class CommentCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session = request.getSession(true);
		User user = (User) (session.getAttribute("currentSessionUser"));
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			Comment comment = new Comment();
			comment.setDatetime(dateFormat.format(date));
			comment.setCommentstext(request.getParameter("comment"));
			comment.setCommenterName(user.getFirstname() + " " + user.getLastname());
			comment.setCommenterImage(user.getImageURL());
			comment.setEmail(user.getEmail());
			String postid = request.getParameter("postid");
			System.out.println("Comment check: postid "+Integer.parseInt(postid));
			

			comment.setPost(PostDAO.searchPost(postid));
			

			boolean allgood = CommentDAO.insertComment(comment);
			
			
			if (allgood) {
				try {
					Notification notification = new Notification();
					notification.setType("Comment");
					notification.setDatetime(dateFormat.format(date));
					notification.setPostid(Integer.parseInt(postid));
					notification.setEmail(user.getEmail());
					
					Post post = PostDAO.searchPost(postid);
					notification.setUser(post.getUser());
					
					
					boolean valid = NotificationDAO.sendNotification(notification);
					if(valid) {
						System.out.println("Notification inserted successfully");
					}
					else
						System.out.println("notification DAO problem");
				}catch (Throwable theException) {
					System.out.println(theException);
				}
				
				session.setAttribute("currentSessionUser", user);
				response.sendRedirect("home.jsp"); // logged-in page
			}
			else {
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Comment not valid');");
				out.println("location='home.jsp';");
				out.println("</script>");
			}
			
			
		}catch (Throwable theException) {
			System.out.println(theException);
		}
	}

}