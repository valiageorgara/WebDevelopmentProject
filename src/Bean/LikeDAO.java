package Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionManager;
import model.Like;
import model.Post;

public class LikeDAO {
	public static boolean insertLike(Like like) {
		// preparing some objects for connection

		String email = like.getEmail();
		int posts_postsId = like.getPost().getPostsId();
		//System.out.println(email);
		
		String query = "insert into likes(email,posts_postsId) values (?,?);";
		
		try {
			Connection con = ConnectionManager.getConnection();

			con.setAutoCommit(true);
			PreparedStatement ps = con.prepareStatement(query); // generates sql query
			
			
			ps.setString(1, email);
			ps.setInt(2, posts_postsId);
			
			ps.executeUpdate(); // execute it on linkedin database
			
			System.out.println("Like successfully inserted");
			ps.close();
			con.close();
			
			return true;
		} catch (SQLException e) { // TODO Auto-generated catch block
			System.out.println("Like not inserted to database");
			e.printStackTrace();
			return false;
		}
	}
	public static List<Like> searchLike(int postid, Post post) {
		List<Like> likelist = new ArrayList<Like>();
		try {
			// connect to DB
			Connection currentCon = ConnectionManager.getConnection();
			String query = "select * from likes where posts_postsId='" + postid +"'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Like like = new Like();
				like.setLikeId(rs.getInt("likeId"));
				like.setEmail(rs.getString("email"));
				like.setPost(post);
				
				likelist.add(like);
			}
			stmt.close();
			currentCon.close();
			
		}catch (Exception ex) {
			System.out.println("searchLike failed: An Exception has occurred! " + ex);
		}
		
		return likelist;
	}
	
	public static boolean likedAlready(Like like) {
		try {
			// connect to DB
			Connection currentCon = ConnectionManager.getConnection();
			String query = "select * from likes where posts_postsId='" + like.getPost().getPostsId() +"' and email= '"+ like.getEmail() + "'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return true;
			}
				
			
			stmt.close();
			currentCon.close();
			
		}catch (Exception ex) {
			System.out.println("likedAlready failed: An Exception has occurred! " + ex);
		}
		return false;
	}
	
	public static List<Like> searchLikesBySpecificUser(String email){
		List<Like> likelist = new ArrayList<Like>();
		try {
			// connect to DB
			Connection currentCon = ConnectionManager.getConnection();
			String query = "select * from likes where email='" + email +"'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Like like = new Like();
				like.setLikeId(rs.getInt("likeId"));
				like.setEmail(rs.getString("email"));
				String postid = String.valueOf(rs.getInt("posts_postsId"));
				like.setPost(PostDAO.searchPost(postid));
				
				likelist.add(like);
			}
			stmt.close();
			currentCon.close();
			
		}catch (Exception ex) {
			System.out.println("searchLikesBySpecificUser failed: An Exception has occurred! " + ex);
		}
		return likelist;
	}

}