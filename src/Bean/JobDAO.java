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

public class JobDAO {
	public static boolean insertJob(Job job) {
		// preparing some objects for connection

		String company = job.getCompany();
		String jobtitle = job.getJobtitle();
		String location = job.getLocation();
		String datetime = job.getDatetime();
		String users_email = job.getUser().getEmail();

		if (company.isEmpty()) {
			company = null;
		}
		if (jobtitle.isEmpty()) {
			jobtitle = null;
		}
		if (location.isEmpty()) {
			location = null;
		}

		String query = "insert into jobs(company,jobtitle,location,datetime,users_email) values (?,?,?,?,?);";

		try {
			Connection con = ConnectionManager.getConnection();

			con.setAutoCommit(true);
			PreparedStatement ps = con.prepareStatement(query); // generates sql query

			ps.setString(1, company);
			ps.setString(2, jobtitle);
			ps.setString(3, location);
			ps.setString(4, datetime);
			ps.setString(5, users_email);

			ps.executeUpdate(); // execute it on linkedin database

			ps.close();
			con.close();

			return true;
		} catch (SQLException e) { // TODO Auto-generated catch block
			System.out.println("Job not inserted to database");
			e.printStackTrace();
			return false;
		}
	}

	public static List<Job> searchJobList(String email) {
		List<Job> joblist = new ArrayList<Job>();
		try {
			Connection currentCon = ConnectionManager.getConnection();
			/// initialization of posts list
			String query = "select * from jobs where users_email='" + email + "'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Job job = new Job();
				job.setCompany(rs.getString("company"));
				job.setJobtitle(rs.getString("jobtitle"));
				job.setLocation(rs.getString("location"));
				job.setDatetime(rs.getString("datetime"));

				job.setUser(UserDAO.searchUser(email));
				job.setJobsId(rs.getInt("jobsId"));
				///////// application list
				String query2 = "select * from applications where jobs_jobsId='" + job.getJobsId() + "'";
				Statement stmt2 = currentCon.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query2);
				List<Application> applicationlist = new ArrayList<Application>();
				while (rs2.next()) {
					Application appl = new Application();
					appl.setApplyId(rs2.getInt("applyId"));
					appl.setEmail(rs2.getString("email"));

					appl.setJob(job);
					applicationlist.add(appl);
				}
				job.setApplications(applicationlist);
				joblist.add(job);

			}
		} catch (Exception ex) {
			System.out.println("searchJobList failed: An Exception has occurred! " + ex);
		}

		return joblist;
	}

	public static Job searchJob(String jobid) {
		Job job = new Job();
		try {
			Connection currentCon = ConnectionManager.getConnection();

			String query = "select * from jobs where jobsId='" + jobid + "'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				job.setCompany(rs.getString("company"));
				job.setJobtitle(rs.getString("jobtitle"));
				job.setLocation(rs.getString("location"));
				job.setDatetime(rs.getString("datetime"));
				String users_email = rs.getString("users_email");
				job.setUser(UserDAO.searchUser(users_email));
				job.setJobsId(rs.getInt("jobsId"));
				String query1 = "select * from applications where jobs_jobsId='" + jobid + "'";
				Statement stmt1 = currentCon.createStatement();
				ResultSet rs1 = stmt1.executeQuery(query1);
				List<Application> applicationlist = new ArrayList<Application>();
				while (rs1.next()) {
					Application appl = new Application();
					appl.setApplyId(rs1.getInt("applyId"));
					appl.setEmail(rs1.getString("email"));
					
					appl.setJob(job);
					applicationlist.add(appl);
				}
				job.setApplications(applicationlist);
			}
			stmt.close();
			currentCon.close();
		} catch (Exception ex) {
			System.out.println("searchJob failed: An Exception has occurred! " + ex);
		}
		return job;
	}

public static List<Job> selectAllJobs() {
	List <Job> joblist=new ArrayList<Job>();
	try {
		Connection currentCon = ConnectionManager.getConnection();
		///initialization of posts list
		String query = "select * from jobs";
		Statement stmt = currentCon.createStatement();
		ResultSet rs = stmt.executeQuery(query);			
		while(rs.next())
		{
			Job job = new Job();
			job.setCompany(rs.getString("company"));
			job.setJobtitle(rs.getString("jobtitle"));
			job.setLocation(rs.getString("location"));
			job.setDatetime(rs.getString("datetime"));
			
			job.setUser(UserDAO.searchUser(rs.getString("users_email")));
			job.setJobsId(rs.getInt("jobsId"));
			/////////application list
			String query2 = "select * from applications where jobs_jobsId='" + job.getJobsId() +"'";
			Statement stmt2 = currentCon.createStatement();
			ResultSet rs2 = stmt2.executeQuery(query2);
			List<Application> applicationlist =new ArrayList<Application>();
			while(rs2.next()) {
				Application appl = new Application();
				appl.setApplyId(rs2.getInt("applyId"));
				appl.setEmail(rs2.getString("email"));
				
				appl.setJob(job);
				applicationlist.add(appl);
			}
			job.setApplications(applicationlist); 
			joblist.add(job);
			
			
		}
	} catch (Exception ex) {
		System.out.println("selectAllJobs failed: An Exception has occurred! " + ex);
	}
	
	
	return joblist;
}

public static List<Job> selectJobsBefore(String date) {
	List <Job> joblist=new ArrayList<Job>();
	try {
		Connection currentCon = ConnectionManager.getConnection();
		///initialization of posts list
		String query = "select * from jobs where datetime <= '" + date +"'";
		Statement stmt = currentCon.createStatement();
		ResultSet rs = stmt.executeQuery(query);			
		while(rs.next())
		{
			Job job = new Job();
			job.setCompany(rs.getString("company"));
			job.setJobtitle(rs.getString("jobtitle"));
			job.setLocation(rs.getString("location"));
			job.setDatetime(rs.getString("datetime"));
			
			job.setUser(UserDAO.searchUser(rs.getString("users_email")));
			job.setJobsId(rs.getInt("jobsId"));
			/////////application list
			String query2 = "select * from applications where jobs_jobsId='" + job.getJobsId() +"'";
			Statement stmt2 = currentCon.createStatement();
			ResultSet rs2 = stmt2.executeQuery(query2);
			List<Application> applicationlist =new ArrayList<Application>();
			while(rs2.next()) {
				Application appl = new Application();
				appl.setApplyId(rs2.getInt("applyId"));
				appl.setEmail(rs2.getString("email"));
				
				appl.setJob(job);
				applicationlist.add(appl);
			}
			job.setApplications(applicationlist); 
			joblist.add(job);
			
			
		}
	} catch (Exception ex) {
		System.out.println("selectAllJobs failed: An Exception has occurred! " + ex);
	}
	
	
	return joblist;
}

public static List<Job> selectJobsAfter(String date) {
	List <Job> joblist=new ArrayList<Job>();
	try {
		Connection currentCon = ConnectionManager.getConnection();
		///initialization of posts list
		String query = "select * from jobs where datetime > '" + date +"'";
		Statement stmt = currentCon.createStatement();
		ResultSet rs = stmt.executeQuery(query);			
		while(rs.next())
		{
			Job job = new Job();
			job.setCompany(rs.getString("company"));
			job.setJobtitle(rs.getString("jobtitle"));
			job.setLocation(rs.getString("location"));
			job.setDatetime(rs.getString("datetime"));
			
			job.setUser(UserDAO.searchUser(rs.getString("users_email")));
			job.setJobsId(rs.getInt("jobsId"));
			/////////application list
			String query2 = "select * from applications where jobs_jobsId='" + job.getJobsId() +"'";
			Statement stmt2 = currentCon.createStatement();
			ResultSet rs2 = stmt2.executeQuery(query2);
			List<Application> applicationlist =new ArrayList<Application>();
			while(rs2.next()) {
				Application appl = new Application();
				appl.setApplyId(rs2.getInt("applyId"));
				appl.setEmail(rs2.getString("email"));
				
				appl.setJob(job);
				applicationlist.add(appl);
			}
			job.setApplications(applicationlist); 
			joblist.add(job);
			
			
		}
	} catch (Exception ex) {
		System.out.println("selectAllJobs failed: An Exception has occurred! " + ex);
	}
	
	
	return joblist;
}

}