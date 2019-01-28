package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.common.collect.Lists;

import Bean.JobDAO;
import Bean.NotificationDAO;
import Bean.UserDAO;
import model.*;

public class HelpingFunctions {

	public static boolean emailExists(String email) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// loads mysql driver

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/linkedinschema", "linkedin",
					"root");
			con.setAutoCommit(true);

			String query;
			query = "SELECT COUNT(*) FROM users where email='" + email + "'";
			PreparedStatement ps = con.prepareStatement(query); // generates sql query
			ResultSet rs = ps.executeQuery(query);
			rs.next();
			String Countrow = rs.getString(1);
			System.out.println("Emails found: " + Countrow);
			if (Countrow.equals("0")) {
				return false;
			} else {
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error coming from emailExists");
			e.printStackTrace();
		}
		return false;

	}

	public static void send(String email, String name, String last) {
		// String to = email;//change accordingly
		// String from = "LinkedIn667@gmail.com";//change accordingly

		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// String host = "localhost";//or IP address

		// Get the session object
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug", "true");
		properties.put("mail.store.protocol", "pop3");
		properties.put("mail.transport.protocol", "smtp");
		// Session session = Session.getDefaultInstance(properties);

		final String username = "LinkedIn667@gmail.com";//
		final String password = "Y?Ugjxgar";
		// compose the message

		try {
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			// -- Create a new message --
			Message msg = new MimeMessage(session);

			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress("LinkedIn667@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
			msg.setSubject("LinkedIn Verification");
			msg.setText("\tYou have created a LinkedIn Account\n\n\tYour full name is " + name + " " + last);
			Transport.send(msg);
			System.out.println("Message sent.");
		} catch (MessagingException e) {
			System.out.println("Erreur d'envoi, cause: " + e);
		}
	}

	
	
	public static String requestSent(User currentuser,String em) {
		List<Notification> notificationlist = NotificationDAO.selectAllFromNotifications();
		String myemail = currentuser.getEmail();
		String connect="no";
		for(int i=0;i<notificationlist.size();i++)
		{
			Notification notification=notificationlist.get(i);
			if((notification.getEmail().equals(myemail)&&notification.getUser().getEmail().equals(em)&&notification.getType().equals("Request"))
					||(notification.getEmail().equals(em)&&notification.getUser().getEmail().equals(myemail)&&notification.getType().equals("Request")))
			{
				connect="yes";
				break;
			}
		}
		return connect;
	}
	
	public static int getUnread(List<model.Message> messagelist) {
		int count=0;
		for(int i=0; i<messagelist.size(); i++) {
			count += messagelist.get(i).getUnread();
		}
		return count;
	}
	
	private static int minimum(int a, int b, int c) {                            
        return Math.min(Math.min(a, b), c);                                      
    }                                                                            
                                                                                 
    public static int LevenshteinDistance(String lhs, String rhs) {      
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];        
                                                                                 
        for (int i = 0; i <= lhs.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= rhs.length(); j++)                                 
            distance[0][j] = j;                                                  
                                                                                 
        for (int i = 1; i <= lhs.length(); i++)                                 
            for (int j = 1; j <= rhs.length(); j++)                             
                distance[i][j] = minimum(                                        
                        distance[i - 1][j] + 1,                                  
                        distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));
                                                                                 
        return distance[lhs.length()][rhs.length()];                           
    } 
    
    public static List<model.Message> getLastConversation(User user) {
    	List<model.Message> messagelisttoreturn = new ArrayList<model.Message>();
		if (!(user.getMessages().isEmpty())) {

			List<model.Message> messagelist = user.getMessages();
			messagelisttoreturn = new ArrayList<model.Message>();

			for (int i = 0; i < messagelist.size(); i++) {
				model.Message message = messagelist.get(i);

				if (message.getContactemail().equals(user.getMessages().get(0).getContactemail())) {
					messagelisttoreturn.add(message);
				}
			}
			messagelisttoreturn = Lists.reverse(messagelisttoreturn);
		}
		
		return messagelisttoreturn;
    }
    
    public static void ChangeLogin(User user){
				
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		user.setLastLogin(dateFormat.format(date));
		UserDAO.modifyDate(dateFormat.format(date),user.getEmail());
		
    }
    
    public static List<Job> OtherJobs(User user, List<Job> skillsjoblist,List<Job> knnjoblist){
    	List<Job> trainingset = JobDAO.selectJobsAfter(user.getLastLogin());
		List<Job> joblisttoreturn = new ArrayList<Job>();
		
		for(int i=0;i<trainingset.size();i++) {
			boolean flag=true;
			for(int j=0;j<skillsjoblist.size()&&flag==true;j++)
				if(trainingset.get(i).getJobsId()==skillsjoblist.get(j).getJobsId()) 
					flag=false;
			for(int j=0;j<knnjoblist.size()&&flag==true;j++)
				if(trainingset.get(i).getJobsId()==knnjoblist.get(j).getJobsId()) 
					flag=false;
			if(flag)
				joblisttoreturn.add(trainingset.get(i));
		}
		
		return joblisttoreturn;
    }
}