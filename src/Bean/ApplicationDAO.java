package Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionManager;
import model.Application;
import model.Job;

public class ApplicationDAO {
	public static boolean insertApplication(Application appl) {
		// preparing some objects for connection

		String email = appl.getEmail();
		int jobs_jobsId = appl.getJob().getJobsId();
		//System.out.println(email);
		
		String query = "insert into applications(email,jobs_jobsId) values (?,?);";
		
		try {
			Connection con = ConnectionManager.getConnection();

			con.setAutoCommit(true);
			PreparedStatement ps = con.prepareStatement(query); // generates sql query
			
			
			ps.setString(1, email);
			ps.setInt(2, jobs_jobsId);
			
			ps.executeUpdate(); // execute it on linkedin database
			
			System.out.println("Application successfully inserted");
			ps.close();
			con.close();
			
			return true;
		} catch (SQLException e) { // TODO Auto-generated catch block
			System.out.println("Application not inserted to database");
			e.printStackTrace();
			return false;
		}
	}
	public static List<Application> searchApplication(int jobid, Job job) {
		List<Application> applicationlist = new ArrayList<Application>();
		try {
			// connect to DB
			Connection currentCon = ConnectionManager.getConnection();
			String query = "select * from applications where jobs_jobsId='" + jobid +"'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Application appl = new Application();
				appl.setApplyId(rs.getInt("applyId"));
				appl.setEmail(rs.getString("email"));
				appl.setJob(job);
				
				applicationlist.add(appl);
			}
			stmt.close();
			currentCon.close();
			
		}catch (Exception ex) {
			System.out.println("searchApplication failed: An Exception has occurred! " + ex);
		}
		
		return applicationlist;
	}
	
	public static boolean appliedAlready(Application appl) {
		try {
			// connect to DB
			Connection currentCon = ConnectionManager.getConnection();
			String query = "select * from applications where jobs_jobsId='" + appl.getJob().getJobsId() +"' and email= '"+ appl.getEmail() + "'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return true;
			}
				
			
			stmt.close();
			currentCon.close();
			
		}catch (Exception ex) {
			System.out.println("appliedAlready failed: An Exception has occurred! " + ex);
		}
		return false;
	}
}
