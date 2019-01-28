package settingstab;

import db.ConnectionManager;
import db.HelpingFunctions;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.PreparedStatement;
import model.User;

/**
 * Servlet implementation class ChangeEmail
 */
@WebServlet("/ChangeEmail")
public class ChangeEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeEmail() {
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
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession(true);
		User currentUser = (User) (session.getAttribute("currentSessionUser"));
		//System.out.println("current user email is " + currentUser.getEmail());

		String oldemail = request.getParameter("oldemail");
		String newemail = request.getParameter("newemail");
		//System.out.println("old email=" + oldemail + " new email= " + newemail);

		///////////////////////////////////////////////
		boolean emailExists = HelpingFunctions.emailExists(newemail);
		//System.out.println("value of emailExists is " + emailExists);
		////////////////////////////////////
		if (!((currentUser.getEmail()).equals(oldemail))) {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Invalid Current Email');");
			out.println("location='settings.jsp';");
			out.println("</script>");
		}
		if (emailExists) {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Email already in use');");
			out.println("location='settings.jsp';");
			out.println("</script>");
		}
		
		///////////////////////////////////////////////
		String em=null,pass=null;
		try {
			Connection con = ConnectionManager.getConnection();
			con.setAutoCommit(true);

			String query = "select * from users where email='" + oldemail + "'";
			PreparedStatement ps = con.prepareStatement(query); // generates sql query
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				em = rs.getString(4);
				pass = rs.getString(3);
			}

			System.out.println("em=" + em + " old= " + oldemail + " pass= " + pass);

			if ((currentUser.getEmail()).equals(oldemail)) {
				query = "update users set email='" + newemail + "' where password='" + pass + "' and email='" + oldemail
						+ "'";
				ps = con.prepareStatement(query);
				ps.executeUpdate();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Email changed successfully');");
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
