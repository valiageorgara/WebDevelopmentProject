package settingstab;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.ConnectionManager;
import model.User;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePassword() {
		super();
		// TODO Auto-generated constructor stub
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
		User bean = (User) (session.getAttribute("currentSessionUser"));
		////////////////////////////////////////////////////////////////
		String currentemail = bean.getEmail();
		String currentpassword = bean.getPassword();
		//System.out.println("current email and password " + currentemail + " " + currentpassword);
		//////////////////////////////////////////////////////
		PrintWriter out = response.getWriter();

		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		//System.out.println("old is " + oldpassword + " new is " + newpassword);
		///////////////////////////////////////////////
		if (!(currentpassword.equals(oldpassword))) { // if user did not enter correct password
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Invalid Current Password');");
			out.println("location='settings.jsp';");
			out.println("</script>");
		}
		///////////////////////////////////////////////
		String em = null, pass = null;
		try {
			Connection con = ConnectionManager.getConnection();
			con.setAutoCommit(true);
			String query = "select * from users where email='" + currentemail + "'";
			PreparedStatement ps = con.prepareStatement(query); // generates sql query
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				em = rs.getString(4);
				pass = rs.getString(3);
			}
			//System.out.println("em is " + em + " pass is " + pass);
			if (pass.equals(oldpassword) && em.equals(currentemail)) {
				query = "update users set password='" + newpassword + "' where email='" + em + "'";
				ps = con.prepareStatement(query);
				ps.executeUpdate();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Password changed successfully');");
				out.println("location='settings.jsp';");
				out.println("</script>");
				ps.close();
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
			e.printStackTrace();
		}

	}

}
