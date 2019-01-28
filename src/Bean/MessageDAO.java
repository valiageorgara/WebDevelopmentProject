package Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionManager;
import model.Message;

public class MessageDAO {

	public static boolean sendMessage(Message message) {

		String datetime = message.getDateOfMsg();
		String text = message.getText();
		String users_email = message.getUser().getEmail();
		String contact_email = message.getContactemail();
		int unread = message.getUnread();
		String state = message.getState();

		String query = "insert into messages (dateOfMsg,text,users_email,contactemail,unread,state) values (?,?,?,?,?,?);";
		try {
			Connection con = ConnectionManager.getConnection();

			con.setAutoCommit(true);
			PreparedStatement ps = con.prepareStatement(query); // generates sql query

			ps.setString(1, datetime);
			ps.setString(2, text);
			ps.setString(3, users_email);
			ps.setString(4, contact_email);
			ps.setInt(5, unread);
			ps.setString(6, state);

			ps.executeUpdate(); // execute it on linkedin database
			ps.close();
			con.close();
			message.setText(text);
			message.setDateOfMsg(datetime);
			message.setContactemail(contact_email);
			message.setUnread(unread);
			message.setState(state);
			// message.setUser(user);

			return true;
		} catch (SQLException e) { // TODO Auto-generated catch block
			System.out.println("message not inserted to database");
			e.printStackTrace();
			return false;
		}
	}

	public static List<Message> searchMessageList(String email) {
		List<Message> messagelist = new ArrayList<Message>();
		try {
			Connection currentCon = ConnectionManager.getConnection();
			/// initialization of message list
			String query = "select * from messages where users_email='" + email + "' order by dateOfMsg desc"; // sxedon
																												// swsto
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// boolean flag=true;
			while (rs.next()) {
				boolean flag = true;
				Message message = new Message();
				message.setContactemail(rs.getString("contactemail"));
				message.setDateOfMsg(rs.getString("dateOfMsg"));
				message.setText(rs.getString("text"));
				message.setState(rs.getString("state"));
				message.setUser(UserDAO.searchUser(email));
				message.setUnread(rs.getInt("unread"));
				message.setIdmessages(rs.getInt("idmessages"));

				int i;
				for (i = 0; i < messagelist.size(); i++) {
					if (messagelist.get(i).getContactemail().equals(message.getContactemail())) {
						flag = false;
					} else if (!flag)
						break;
				}
				messagelist.add(i, message);

			}

		} catch (Exception ex) {
			System.out.println("searchMessageList1 failed: An Exception has occurred! " + ex);
		}

		return messagelist;
	}

	public static void modifyUnread(int id) {
		try {
			String query = "update messages set unread='0' where idmessages='" + id + "'";
			Connection currentCon = ConnectionManager.getConnection();
			PreparedStatement ps = currentCon.prepareStatement(query);
			ps.executeUpdate(query);
			ps.close();
			currentCon.close();

		} catch (Exception ex) {
			System.out.println("modifyUnread failed: An Exception has occurred! " + ex);
		}

		// return message;
	}
}
