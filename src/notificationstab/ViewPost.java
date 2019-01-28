package notificationstab;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.NotificationDAO;
import Bean.PostDAO;
import model.Post;
import model.User;

/**
 * Servlet implementation class ViewPost
 */
@WebServlet("/ViewPost")
public class ViewPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewPost() {
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
		int id=Integer.parseInt(request.getParameter("id"));
		int postid=Integer.parseInt(request.getParameter("PostId"));
		System.out.println("postid is "+postid + " and notification id is "+ id);
		
		NotificationDAO.deleteNotification(id,user);
			
		Post post = PostDAO.searchPost(request.getParameter("PostId"));
			
			session.setAttribute("post", post);
			response.sendRedirect("viewpost.jsp"); // logged-in page
		
		
	}

}
