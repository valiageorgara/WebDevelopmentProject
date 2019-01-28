package messagetab;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.MessageDAO;
import Bean.UserDAO;
import model.Message;
import model.User;

/**
 * Servlet implementation class SendMessage
 */
@WebServlet("/SendMessage")
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessage() {
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
		
		String text = request.getParameter("text");
		String contactemail = request.getParameter("contactemail");		
		User contact = UserDAO.searchUser(contactemail);
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			//insert message for sender
			Message message = new Message();
			message.setText(text);
			message.setDateOfMsg(dateFormat.format(date));
			message.setContactemail(contactemail);
			message.setState("send");
			message.setUnread(0);
			message.setUser(user);
			
			boolean valid = MessageDAO.sendMessage(message);
			
			//insert message for receiver
			Message message2 = new Message();
			message2.setText(text);
			message2.setDateOfMsg(dateFormat.format(date));
			message2.setContactemail(user.getEmail());
			message2.setState("receive");
			message2.setUnread(1);
			message2.setUser(contact);
			
			boolean valid2 = MessageDAO.sendMessage(message2);
			
			if(valid&&valid2) {
				user.setMessages(MessageDAO.searchMessageList(user.getEmail()));
				@SuppressWarnings("unchecked")
				List<Message> messagelisttoreturn = (List<Message>) (session.getAttribute("MessageList"));

				messagelisttoreturn.add(message);
				
				session.setAttribute("MessageList", messagelisttoreturn); //edw ksanastelnetai arxikopoihmeno
				session.setAttribute("contactSession", contact);
				response.sendRedirect("messaging.jsp"); 
			}
			else
				System.out.println("SendMessage problem");
		}catch (Throwable theException) {
			System.out.println("SendMessage: "+theException);
		}
		
	}

}
