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

import Bean.PostDAO;
import model.Post;
import model.User;

/**
 * Servlet implementation class PostCheck
 */
@WebServlet("/PostCheck")
public class PostCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PostCheck() {
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
			//System.out.println(dateFormat.format(date)); //2013/10/15 16:16:39
			Post post = new Post();
			post.setDatetime(dateFormat.format(date));
			post.setAudioUrl(request.getParameter("audio"));
			post.setImageUrl(request.getParameter("image"));
			post.setVideoUrl(request.getParameter("video"));
			post.setText(request.getParameter("status"));
			post.setUser(user);


			boolean allgood = PostDAO.insertPost(post);
			user.setPosts(PostDAO.searchPostList(user.getEmail()));
			
			if (allgood) {

				session.setAttribute("currentSessionUser", user);
				response.sendRedirect("home.jsp"); // logged-in page
			}
			else {
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Post not valid');");
				out.println("location='home.jsp';");
				out.println("</script>");
			}
			
			
		}catch (Throwable theException) {
			System.out.println(theException);
		}

	}

}
