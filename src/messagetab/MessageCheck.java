package messagetab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Lists;

import Bean.MessageDAO;
import Bean.UserDAO;
import model.Message;
import model.User;

/**
 * Servlet implementation class MessageCheck
 */
@WebServlet("/MessageCheck")
public class MessageCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		HttpSession session = request.getSession(true);
		User user = (User) (session.getAttribute("currentSessionUser"));
		
		String contactemail = request.getParameter("contactemail");

		User contact = UserDAO.searchUser(contactemail);

		List<Message> messagelist = user.getMessages();
		List<Message> messagelisttoreturn = new ArrayList<Message>();

		for(int i=0;i<messagelist.size();i++) {
			Message message = messagelist.get(i);
			
			if(message.getContactemail().equals(contactemail)) {
				MessageDAO.modifyUnread(message.getIdmessages());
				messagelisttoreturn.add(message);
			}
		}
		
		user.setMessages(MessageDAO.searchMessageList(user.getEmail()));
		messagelisttoreturn=Lists.reverse(messagelisttoreturn);
		
		session.setAttribute("currentSessionUser", user);
		session.setAttribute("MessageList", messagelisttoreturn);
		session.setAttribute("contactSession", contact);
		response.sendRedirect("messaging.jsp"); // results page
	}

}
