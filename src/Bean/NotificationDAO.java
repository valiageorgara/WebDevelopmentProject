package Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import db.ConnectionManager;
import model.Notification;
import model.User;

public class NotificationDAO {
	public static boolean sendNotification(Notification notification) {

		String datetime = notification.getDatetime();
		String type = notification.getType();
		String senders_email = notification.getEmail();
		String users_email = notification.getUser().getEmail();
		int postid = notification.getPostid();

		String query = "insert into notifications(type,datetime,email,postid,users_email) values (?,?,?,?,?);";
		try {
			Connection con = ConnectionManager.getConnection();

			con.setAutoCommit(true);
			PreparedStatement ps = con.prepareStatement(query); // generates sql query

			ps.setString(1, type);
			ps.setString(2, datetime);
			ps.setString(3, senders_email);
			ps.setInt(4, postid);
			ps.setString(5, users_email);

			ps.executeUpdate(); // execute it on linkedin database
			ps.close();
			con.close();
			notification.setType(type);
			notification.setDatetime(datetime);
			notification.setEmail(senders_email);
			notification.setPostid(postid);

			return true;
		} catch (SQLException e) { // TODO Auto-generated catch block
			System.out.println("Post not inserted to database");
			e.printStackTrace();
			return false;
		}
	}

	public static String deleteNotification(int id, User user) {

		String query = "select * from notifications where idnotifications=" + id;
		Notification temp = new Notification();
		try {
			// connect to DB
			Connection currentCon = ConnectionManager.getConnection();
			PreparedStatement ps = currentCon.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				temp.setIdnotifications(id);
				temp.setDatetime(rs.getString("datetime"));
				temp.setType(rs.getString("type"));
				temp.setEmail(rs.getString("email"));
				temp.setPostid(rs.getInt("postid"));
				temp.setUser(user);

			}

			ListIterator<Notification> it = user.getNotifications().listIterator();
			while (it.hasNext()) {

				if (it.next().getIdnotifications() == temp.getIdnotifications()) {
					it.remove();
				}
			}

			query = "delete from notifications where idnotifications=" + id + " and type='" + temp.getType()
					+ "' and datetime='" + temp.getDatetime() + "' and email='" + temp.getEmail()
					+ "' and users_email = '" + user.getEmail() + "' and postid ='" + temp.getPostid() + "'";
			currentCon = ConnectionManager.getConnection();
			ps = currentCon.prepareStatement(query);
			ps.executeUpdate(query);
			ps.close();
			currentCon.close();
		}

		catch (Exception ex) {
			System.out.println("Notification DAO failed: An Exception has occurred! " + ex);
		}

		return temp.getEmail();

	}

	public static List<Notification> searchNotificationList(String email) {
		List<Notification> notificationlist = new ArrayList<Notification>();

		/// initialization of notification list
		try {
			Connection currentCon = ConnectionManager.getConnection();
			String query = "select * from notifications where users_email='" + email + "' order by datetime DESC";
			Statement stmt = currentCon.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				Notification notification = new Notification();
				notification.setType(rs.getString("type"));
				notification.setDatetime(rs.getString("datetime"));
				notification.setEmail(rs.getString("email"));
				notification.setUser(UserDAO.searchUser(email));
				notification.setPostid(rs.getInt("postid"));
				notification.setIdnotifications(rs.getInt("idnotifications"));
				notificationlist.add(notification);
			}
		} catch (Exception ex) {
			System.out.println("searchNotificationList failed: An Exception has occurred! " + ex);
		}
		return notificationlist;
	}
	
	public static List<Notification> selectAllFromNotifications() {
		List<Notification> notificationlist = new ArrayList<Notification>();

		/// initialization of notification list
		try {
			Connection currentCon = ConnectionManager.getConnection();
			String query = "select * from notifications";
			Statement stmt = currentCon.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				Notification notification = new Notification();
				notification.setType(rs.getString("type"));
				notification.setDatetime(rs.getString("datetime"));
				notification.setEmail(rs.getString("email"));
				notification.setUser(UserDAO.searchUser(rs.getString("users_email")));
				notification.setPostid(rs.getInt("postid"));
				notification.setIdnotifications(rs.getInt("idnotifications"));
				notificationlist.add(notification);
			}
		} catch (Exception ex) {
			System.out.println("selectAllFromNotifications failed: An Exception has occurred! " + ex);
		}
		return notificationlist;
	}
}
