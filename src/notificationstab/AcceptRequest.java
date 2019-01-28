package notificationstab;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Contact;
import model.User;
import Bean.ContactDAO;
import Bean.NotificationDAO;
import Bean.UserDAO;

/**
 * Servlet implementation class AcceptRequest
 */
@WebServlet("/AcceptRequest")
public class AcceptRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptRequest() {
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
		
		String idstring=request.getParameter("id");
		int id=Integer.parseInt(idstring);
		String accept=request.getParameter("accept");
		System.out.println("id is "+id+" and accept is "+ accept);
		
		String em=NotificationDAO.deleteNotification(id,user);
			
			if(accept.equals("Yes"))
			{
				//em=temp.getEmail();
				User contactuser = new User();
				contactuser = UserDAO.searchUser(em);
				System.out.println(contactuser.getEmail());

				Contact contact = new Contact();
				contact.setDepartment(contactuser.getCompany());
				contact.setEmail(contactuser.getEmail());
				contact.setFirstname(contactuser.getFirstname());
				contact.setImageURL(contactuser.getImageURL());
				contact.setJobtitle(contactuser.getCarrier());
				contact.setLastname(contactuser.getLastname());
				contact.setUser(user);
				user.addContact(contact);
				
				boolean valid = ContactDAO.insertContact(contact);
				if(valid)
					System.out.println("OK");
				else
					System.out.println("contact DAO problem");
				
				Contact contact1 = new Contact();
				contact1.setDepartment(user.getCompany());
				contact1.setEmail(user.getEmail());
				contact1.setFirstname(user.getFirstname());
				contact1.setImageURL(user.getImageURL());
				contact1.setJobtitle(user.getCarrier());
				contact1.setLastname(user.getLastname());
				contact1.setUser(contactuser);
				
				valid = ContactDAO.insertContact(contact1);
				if(valid)
					System.out.println("OK");
				else
					System.out.println("contact DAO problem");
			}
			
			session.setAttribute("currentSessionUser", user);
			response.sendRedirect("notifications.jsp"); // logged-in page
		
		
	}

}
