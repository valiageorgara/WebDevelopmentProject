package Bean;
//package model;
import db.ConnectionManager;
import db.HelpingFunctions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.User;
import model.Contact;

public class UserDAO {
	static Connection currentCon = null;
	static ResultSet rs = null;

	public static boolean login(User bean) {

		// preparing some objects for connection
		Statement stmt = null;

		String loggedemail = bean.getEmail();
		String loggedpassword = bean.getPassword();

		String searchQuery = "select * from users where email='" + loggedemail + "' AND password='" + loggedpassword + "'";

		// "System.out.println" prints in the console; Normally used to trace the
		// process
		System.out.println("Your email is " + loggedemail);
		System.out.println("Your password is " + loggedpassword);

		try {
			// connect to DB
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(searchQuery);
			boolean more = rs.next();

			// if user does not exist set the isValid variable to false
			if (!more) {
				System.out.println("Sorry, you are not a registered user! Please sign up first");
				return false;
			}

			// if user exists set the isValid variable to true
			else if (more) {
				
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String password = rs.getString("password");
				String email = rs.getString("email");
				String image = rs.getString("imageURL");
				String number = rs.getString("number");
				String carrier = rs.getString("carrier");
				String carrierRadio = rs.getString("carrierRadio");
				String company = rs.getString("company");
				String companyRadio = rs.getString("companyRadio");
				String jobexperience = rs.getString("jobexperience");
				String jobexperienceRadio = rs.getString("jobexperienceRadio");
				String education = rs.getString("education");
				String educationRadio = rs.getString("educationRadio");				
				String skills = rs.getString("skills");
				String skillsRadio = rs.getString("skillsRadio");
				String lastLogin = rs.getString("lastLogin");
				
				
				System.out.println("Welcome " + firstname);
				bean.setFirstname(firstname);
				bean.setLastname(lastname);
				bean.setPassword(password);
				bean.setEmail(email);
				bean.setNumber(number);
				bean.setImageURL(image);
				bean.setCarrier(carrier);
				bean.setCarrierRadio(carrierRadio);
				bean.setCompany(company);
				bean.setCompanyRadio(companyRadio);
				bean.setJobexperience(jobexperience);
				bean.setJobexperienceRadio(jobexperienceRadio);
				bean.setEducation(education);
				bean.setEducationRadio(educationRadio);
				bean.setSkills(skills);
				bean.setSkillsRadio(skillsRadio);
				bean.setLastLogin(lastLogin);
				
				return true;
			}
		}

		catch (Exception ex) {
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		}

		// some exception handling
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
				stmt = null;
			}

			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}

				currentCon = null;
			}
		}
		System.out.println("Did not go into if.");
		return false;

	}

	public static boolean signup(User bean) {
		
		
		// preparing some objects for connection

		String name = bean.getFirstname();
		String last = bean.getLastname();
		String email = bean.getEmail();
		String password = bean.getPassword();
		String number = bean.getNumber();
		String image = bean.getImageURL();
		String carrier = bean.getCarrier();
		String carrierRadio = bean.getCarrierRadio();
		String company = bean.getCompany();
		String companyRadio = bean.getCompanyRadio();
		String jobexperience = bean.getJobexperience();
		String jobexperienceRadio = bean.getJobexperienceRadio();
		String education = bean.getEducation();
		String educationRadio = bean.getEducationRadio();
		String skills = bean.getSkills();
		String skillsRadio = bean.getSkillsRadio();
		String lastLogin = bean.getLastLogin();

		if (image.isEmpty()) {
			image = "JPAWebExample/WebContent/css/images/profile.jpg";
		}
		if (number.isEmpty()) {
			number = null;
		}
	       

		boolean emailExists = HelpingFunctions.emailExists(email);

		System.out.println("Your email is " + email);
		System.out.println("Your password is " + password);
		String query = "insert into users(firstname,lastname,password,email,number,imageURL,carrier,carrierRadio,company,companyRadio,jobexperience,jobexperienceRadio,education,educationRadio,skills,skillsRadio,lastLogin) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

		if (!emailExists) {
			try {
				Connection con = ConnectionManager.getConnection();

				con.setAutoCommit(true);
				PreparedStatement ps = con.prepareStatement(query); // generates sql query
				
				
				ps.setString(1, name);
				ps.setString(2, last);
				ps.setString(3, password);
				ps.setString(4, email);
				ps.setString(5, number);
				ps.setString(6, image);
				ps.setString(7, carrier);
				ps.setString(8, carrierRadio);

				ps.setString(9, company);
				ps.setString(10, companyRadio);

				ps.setString(11, jobexperience);
				ps.setString(12, jobexperienceRadio);

				ps.setString(13, education);
				ps.setString(14, educationRadio);

				ps.setString(15, skills);
				ps.setString(16, skillsRadio);
				
				ps.setString(17, lastLogin);

				ps.executeUpdate(); // execute it on linkedin database
				System.out.println("successfuly inserted");
				ps.close();
				con.close();
				bean.setFirstname(name);
				bean.setLastname(last);
				bean.setPassword(password);
				bean.setEmail(email);
				bean.setNumber(number);
				bean.setImageURL(image);
				bean.setCarrier(carrier);
				bean.setCarrierRadio(carrierRadio);
				bean.setCompany(company);
				bean.setCompanyRadio(companyRadio);
				bean.setJobexperience(jobexperience);
				bean.setJobexperienceRadio(jobexperienceRadio);
				bean.setEducation(education);
				bean.setEducationRadio(educationRadio);
				bean.setSkills(skills);
				bean.setSkillsRadio(skillsRadio);
				bean.setLastLogin(lastLogin);
				
				HelpingFunctions.send(email, name, last);
				return true;

			} catch (SQLException e) { // TODO Auto-generated catch block
				System.out.println("not inserted to database");
				e.printStackTrace();
			}
		} else if (emailExists) {
			System.out.println("Sorry, email already in use. Try with a different one or log in.");
			return false;
		}
		System.out.println("Did not even go into if.");
		return false;
	}
	
	public static User updateUser(User user, String query) {
		try {
			// connect to DB
			Connection currentCon = ConnectionManager.getConnection();
			PreparedStatement ps = currentCon.prepareStatement(query);

			
			ps.setString(1, user.getCarrier());
			ps.setString(2, user.getCarrierRadio());
			ps.setString(3, user.getCompany());
			ps.setString(4, user.getCompanyRadio());
			ps.setString(5, user.getJobexperience());
			ps.setString(6, user.getJobexperienceRadio());
			ps.setString(7, user.getEducation());
			ps.setString(8, user.getEducationRadio());
			ps.setString(9, user.getSkills());
			ps.setString(10, user.getSkillsRadio());
			ps.setString(11, user.getEmail());
			ps.executeUpdate();
			ps.close();
			currentCon.close();
		
		}

		catch (Exception ex) {
			System.out.println("Update data failed: An Exception has occurred! " + ex);
		}
		
		return user;
	}
	
	public static User searchUser(String email) {
		User bean = new User();
		try {
			// connect to DB
			Connection currentCon = ConnectionManager.getConnection();
			String query = "select * from users where email='" + email +"'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {		
				bean.setFirstname(rs.getString("firstname"));
				bean.setLastname(rs.getString("lastname"));
				bean.setPassword(rs.getString("password"));
				bean.setEmail(rs.getString("email"));
				bean.setNumber(rs.getString("number"));
				bean.setImageURL(rs.getString("imageURL"));
				bean.setCarrier(rs.getString("carrier"));
				bean.setCarrierRadio(rs.getString("carrierRadio"));
				bean.setCompany(rs.getString("company"));
				bean.setCompanyRadio(rs.getString("companyRadio"));
				bean.setJobexperience(rs.getString("jobexperience"));
				bean.setJobexperienceRadio(rs.getString("jobexperienceRadio"));
				bean.setEducation(rs.getString("education"));
				bean.setEducationRadio(rs.getString("educationRadio"));
				bean.setSkills(rs.getString("skills"));
				bean.setSkillsRadio(rs.getString("skillsRadio"));
				bean.setLastLogin(rs.getString("lastLogin"));
			}
			stmt.close();
			currentCon.close();
		}catch (Exception ex) {
			System.out.println("Update data failed: An Exception has occurred! " + ex);
		}
		return bean;
	}
	
	public static boolean isContact(String em, List<Contact> list)
	{
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).getEmail().equals(em)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<User> selectAllUsers(){
		List<User> userlist = new ArrayList<User>();
		try {
			Connection currentCon = ConnectionManager.getConnection();

			String query = "select * from users";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				User user = new User();
				user.setFirstname(rs.getString("firstname"));
				user.setLastname(rs.getString("lastname"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setNumber(rs.getString("number"));
				user.setImageURL(rs.getString("imageURL"));
				user.setCarrier(rs.getString("carrier"));
				user.setCarrierRadio(rs.getString("carrierRadio"));
				user.setCompany(rs.getString("company"));
				user.setCompanyRadio(rs.getString("companyRadio"));
				user.setJobexperience(rs.getString("jobexperience"));
				user.setJobexperienceRadio(rs.getString("jobexperienceRadio"));
				user.setEducation(rs.getString("education"));
				user.setEducationRadio(rs.getString("educationRadio"));
				user.setSkills(rs.getString("skills"));
				user.setSkillsRadio(rs.getString("skillsRadio"));
				user.setLastLogin(rs.getString("lastLogin"));
				
				userlist.add(user);
			}
			stmt.close();
			currentCon.close();
		} catch (Exception ex) {
			System.out.println("selectAllUsers failed: An Exception has occurred! " + ex);
		}
		
		return userlist;
	}
	
	public static void modifyDate(String date,String email) {
		try {
			String query = "update users set lastLogin='"+ date + "' where email='" +email+"'";
			Connection currentCon = ConnectionManager.getConnection();
			PreparedStatement ps = currentCon.prepareStatement(query);
			ps.executeUpdate(query);
			ps.close();
			currentCon.close();

		} catch (Exception ex) {
			System.out.println("modifyDate failed: An Exception has occurred! " + ex);
		}
	}
}