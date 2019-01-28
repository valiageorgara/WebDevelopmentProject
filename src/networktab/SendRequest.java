package networktab;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.NotificationDAO;
import Bean.UserDAO;
import db.HelpingFunctions;
import model.Notification;
import model.User;

/**
 * Servlet implementation class SendRequest
 */
@WebServlet("/SendRequest")
public class SendRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendRequest() {
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
				
		String em = request.getParameter("email");
		System.out.println("SendRequest em is "+ em);
		User temp = UserDAO.searchUser(em);
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			Notification notification = new Notification();
			notification.setType("Request");
			notification.setDatetime(dateFormat.format(date));
			notification.setEmail(user.getEmail());
			notification.setPostid(0);
			notification.setUser(temp);
			
			boolean valid = NotificationDAO.sendNotification(notification);
			if(valid) {
				String connect = HelpingFunctions.requestSent(user,em);
				session.setAttribute("NonFriend", temp);
				session.setAttribute("connect", connect);
				response.sendRedirect("userprofile.jsp"); 
			}
			else
				System.out.println("notification DAO problem");
		}catch (Throwable theException) {
			System.out.println(theException);
		}
		
		
		
		
		
	}

}
