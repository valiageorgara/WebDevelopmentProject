package jobstab;

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

import Bean.JobDAO;
import model.Job;
import model.User;

/**
 * Servlet implementation class JobCheck
 */
@WebServlet("/JobCheck")
public class JobCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobCheck() {
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
			
			Job job = new Job();
			job.setCompany(request.getParameter("company"));
			job.setJobtitle(request.getParameter("jobtitle"));
			job.setLocation(request.getParameter("location"));
			job.setDatetime(dateFormat.format(date));
			job.setUser(user);


			boolean allgood = JobDAO.insertJob(job);
			user.setJobs(JobDAO.searchJobList(user.getEmail()));
			
			if (allgood) {

				session.setAttribute("currentSessionUser", user);
				response.sendRedirect("jobs.jsp"); // logged-in page
			}
			else {
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Job not valid');");
				out.println("location='jobs.jsp';");
				out.println("</script>");
			}
			
			
		}catch (Throwable theException) {
			System.out.println(theException);
		}

	
	}

}
