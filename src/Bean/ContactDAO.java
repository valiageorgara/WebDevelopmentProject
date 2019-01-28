package Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionManager;
import model.Contact;

public class ContactDAO {
	public static boolean insertContact(Contact contact) {
		String firstname = contact.getFirstname();
		String lastname = contact.getLastname();
		String imageURL = contact.getImageURL();
		String jobtitle = contact.getJobtitle();
		String department = contact.getDepartment();
		String email = contact.getEmail();
		String users_email = contact.getUser().getEmail();

		String query = "insert into contacts (firstname,lastname,imageURL,jobtitle,department,email,users_email) values (?,?,?,?,?,?,?);";
		try {
			Connection con = ConnectionManager.getConnection();

			con.setAutoCommit(true);
			PreparedStatement ps = con.prepareStatement(query); // generates sql query

			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.setString(3, imageURL);
			ps.setString(4, jobtitle);
			ps.setString(5, department);
			ps.setString(6, email);
			ps.setString(7, users_email);

			ps.executeUpdate(); // execute it on linkedin database

			System.out.println("Contact successfuly inserted");
			ps.close();
			con.close();
			contact.setFirstname(firstname);
			contact.setLastname(lastname);
			contact.setImageURL(imageURL);
			contact.setJobtitle(jobtitle);
			contact.setDepartment(department);
			contact.setEmail(email);

			return true;
		} catch (SQLException e) { // TODO Auto-generated catch block
			System.out.println("Contact not inserted to database");
			e.printStackTrace();
			return false;
		}
	}

	public static List<Contact> searchContactList(String email) {
		List<Contact> contactlist = new ArrayList<Contact>();

		/// initialization of contacts list
		try {
			Connection currentCon = ConnectionManager.getConnection();
			String query = "select * from contacts where users_email='" + email + "'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				Contact contact = new Contact();
				contact.setIdcontacts(rs.getInt("idcontacts"));
				contact.setFirstname(rs.getString("firstname"));
				contact.setLastname(rs.getString("lastname"));
				contact.setJobtitle(rs.getString("jobtitle"));
				contact.setDepartment(rs.getString("department"));
				contact.setImageURL(rs.getString("imageURL"));
				contact.setEmail(rs.getString("email"));
				contact.setUser(UserDAO.searchUser(email));
				contactlist.add(contact);
			}
		} catch (Exception ex) {
			System.out.println("searchContactList failed: An Exception has occurred! " + ex);
		}
		return contactlist;
	}
}
