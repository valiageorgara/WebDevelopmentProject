package networktab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.ConnectionManager;
import model.User;

/**
 * Servlet implementation class SearchCheck
 */
@WebServlet("/SearchCheck")
public class SearchCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchCheck() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		
		HttpSession session = request.getSession(true);
		User user = (User) (session.getAttribute("currentSessionUser"));
		
		int flag =1;
		String[] part = null;
		String input = request.getParameter("input");
		if(input.contains(" ")) {
			part = input.split("\\s+");
			flag=0;
		}

		String firstname = null, lastname = null, password = null, email = null, number = null, image = null,
				carrier = null, carrierRadio = null, company = null, companyRadio = null, jobexperience = null,
				jobexperienceRadio = null, education = null, educationRadio = null, skills = null, skillsRadio = null;
		String query;
		try {
			Connection con = ConnectionManager.getConnection();
			con.setAutoCommit(true);
			if(flag == 1) {
				query ="select * from users where firstname like '%" + input + "%' or lastname like '%" + input + "%'";
			}else {
				query = "select * from users where (firstname like '%" + part[0] + "%' and lastname like '%" + part[1] + "%') or (firstname like '%" + part[1] +"%' and lastname like '%" + part[0] + "%')"; 

			}
			PreparedStatement ps = con.prepareStatement(query); // generates sql query
			ResultSet rs = ps.executeQuery();

			List<User> userlist = new ArrayList<User>();
			while (rs.next()) {
				User temp = new User();
				firstname = rs.getString("firstname");
				lastname = rs.getString("lastname");
				password = rs.getString("password");
				email = rs.getString("email");
				number = rs.getString("number");
				image = rs.getString("imageURL");
				carrier = rs.getString("carrier");
				carrierRadio = rs.getString("carrierRadio");
				company = rs.getString("company");
				companyRadio = rs.getString("companyRadio");
				jobexperience = rs.getString("jobexperience");
				jobexperienceRadio = rs.getString("jobexperienceRadio");
				education = rs.getString("education");
				educationRadio = rs.getString("educationRadio");
				skills = rs.getString("skills");
				skillsRadio = rs.getString("skillsRadio");

				temp.setFirstname(firstname);
				temp.setLastname(lastname);
				temp.setPassword(password);
				temp.setEmail(email);
				temp.setNumber(number);
				temp.setImageURL(image);
				temp.setCarrier(carrier);
				temp.setCarrierRadio(carrierRadio);
				temp.setCompany(company);
				temp.setCompanyRadio(companyRadio);
				temp.setJobexperience(jobexperience);
				temp.setJobexperienceRadio(jobexperienceRadio);
				temp.setEducation(education);
				temp.setEducationRadio(educationRadio);
				temp.setSkills(skills);
				temp.setSkillsRadio(skillsRadio);

				if(!(temp.getEmail().equals("admin@gmail.com"))&& !temp.getEmail().equals(user.getEmail()))
					userlist.add(temp);
			}
			

			session.setAttribute("SearchedUsers", userlist);
			response.sendRedirect("search.jsp"); // results page

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("search error" + e);
			e.printStackTrace();
		}

	}
}
