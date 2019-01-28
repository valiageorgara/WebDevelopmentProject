
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import Bean.UserDAO;

/**
 * Servlet implementation class EditProfile
 */
@WebServlet("/EditProfile")
public class EditProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		HttpSession session = request.getSession(true);
		User user = (User) (session.getAttribute("currentSessionUser"));
				
		
		///get the new data
		String carrier = request.getParameter("carrier");
		String carrierRadio = request.getParameter("carrierRadio");
		String company = request.getParameter("company");
		String companyRadio = request.getParameter("companyRadio");
		String jobexperience = request.getParameter("jobexperience");
		String jobexperienceRadio = request.getParameter("jobexperienceRadio");
		String education = request.getParameter("education");
		String educationRadio = request.getParameter("educationRadio");
		String skills = request.getParameter("skills");
		String skillsRadio = request.getParameter("skillsRadio");
		///put them in the bean
		user.setCarrier(carrier);
		user.setCarrierRadio(carrierRadio);
		user.setCompany(company);
		user.setCompanyRadio(companyRadio);
		user.setJobexperience(jobexperience);
		user.setJobexperienceRadio(jobexperienceRadio);
		user.setEducation(education);
		user.setEducationRadio(educationRadio);
		user.setSkills(skills);
		user.setSkillsRadio(skillsRadio);
		

		String query = "UPDATE users SET carrier= ?,carrierRadio=?, company=?,companyRadio=?, jobexperience=?,jobexperienceRadio=?, education=?,educationRadio=?, skills=?, skillsRadio=? WHERE email=?";

		user = UserDAO.updateUser(user, query);
		request.setAttribute("msg", "User updated successfully");
		getServletContext().getRequestDispatcher("/profile.jsp").forward(request, response);
		
	}

}