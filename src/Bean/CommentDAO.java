package Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionManager;
import model.Comment;
import model.Post;

public class CommentDAO {
	
	public static boolean insertComment(Comment comment) {
		// preparing some objects for connection

				String datetime = comment.getDatetime();
				String text = comment.getCommentstext();
				String commenterName = comment.getCommenterName();
				String commenterImage = comment.getCommenterImage();
				String email = comment.getEmail();
				int posts_postsId = comment.getPost().getPostsId();
				
				String query = "insert into comments(commentstext,commenterName,commenterImage,datetime,email,posts_postsId) values (?,?,?,?,?,?);";
				
				try {
					Connection con = ConnectionManager.getConnection();

					con.setAutoCommit(true);
					PreparedStatement ps = con.prepareStatement(query); // generates sql query
					
					
					ps.setString(1, text);
					ps.setString(2, commenterName);
					ps.setString(3, commenterImage);
					ps.setString(4, datetime);
					ps.setString(5, email);
					ps.setInt(6, posts_postsId);
					
					ps.executeUpdate(); // execute it on linkedin database
					
					System.out.println("Comment successfully inserted");
					ps.close();
					con.close();
					
					return true;
				} catch (SQLException e) { // TODO Auto-generated catch block
					System.out.println("Comment not inserted to database");
					e.printStackTrace();
					return false;
				}
	}
	public static List<Comment> searchComment(int postid, Post post) {
		List<Comment> commentlist = new ArrayList<Comment>();
		try {
			// connect to DB
			Connection currentCon = ConnectionManager.getConnection();
			String query = "select * from comments where posts_postsId='" + postid +"'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setCommenterImage(rs.getString("commenterImage"));
				comment.setCommenterName(rs.getString("commenterName"));
				comment.setCommentstext(rs.getString("commentstext"));
				comment.setDatetime(rs.getString("datetime"));
				comment.setIdcomments(rs.getInt("idcomments"));
				comment.setEmail(rs.getString("email"));
				comment.setPost(post);
				
				commentlist.add(comment);
			}
			stmt.close();
			currentCon.close();
			
		}catch (Exception ex) {
			System.out.println("searchComment failed: An Exception has occurred! " + ex);
		}
		
		return commentlist;
	}

	public static List<Comment> searchCommentsBySpecificUser(String email){
		List<Comment> commentlist = new ArrayList<Comment>();
		try {
			// connect to DB
			Connection currentCon = ConnectionManager.getConnection();
			String query = "select * from comments where email='" + email +"'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setCommenterImage(rs.getString("commenterImage"));
				comment.setCommenterName(rs.getString("commenterName"));
				comment.setCommentstext(rs.getString("commentstext"));
				comment.setDatetime(rs.getString("datetime"));
				comment.setIdcomments(rs.getInt("idcomments"));
				comment.setEmail(rs.getString("email"));
				String postid = String.valueOf(rs.getInt("posts_postsId"));
				comment.setPost(PostDAO.searchPost(postid));
				
				commentlist.add(comment);
			}
			stmt.close();
			currentCon.close();
			
		}catch (Exception ex) {
			System.out.println("searchCommentsBySpecificUser failed: An Exception has occurred! " + ex);
		}
		return commentlist;
	}
}