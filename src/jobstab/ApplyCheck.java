package jobstab;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.ApplicationDAO;
import Bean.JobDAO;

import model.Application;

import model.User;

/**
 * Servlet implementation class ApplyCheck
 */
@WebServlet("/ApplyCheck")
public class ApplyCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyCheck() {
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
			Application appl = new Application();
			appl.setEmail(request.getParameter("email"));

			String jobid = request.getParameter("jobid");

			appl.setJob(JobDAO.searchJob(jobid));
			if (!ApplicationDAO.appliedAlready(appl)) {
				boolean allgood = ApplicationDAO.insertApplication(appl);

				if (allgood) {
					session.setAttribute("currentSessionUser", user);
					response.sendRedirect("jobs.jsp"); // jobs page
				} else {
					PrintWriter out = response.getWriter();
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Application not valid');");
					out.println("location='jobs.jsp';");
					out.println("</script>");
				}
			}
			session.setAttribute("currentSessionUser", user);
		} catch (Throwable theException) {
			System.out.println(theException);
		}
	}

}
