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
import model.Contact;
import model.Like;
import model.Post;
import model.User;

public class PostDAO {
	public static boolean insertPost(Post post) {
		// preparing some objects for connection

		String datetime = post.getDatetime();
		String text = post.getText();
		String image = post.getImageUrl();
		String video = post.getVideoUrl();
		String audio = post.getAudioUrl();
		String users_email = post.getUser().getEmail();

		if (image.isEmpty()) {
			image = null;
		}
		if (video.isEmpty()) {
			video = null;
		}
		if (text.isEmpty()) {
			text = null;
		}
		if (audio.isEmpty()) {
			audio = null;
		}

		String query = "insert into posts(text,datetime,imageUrl,videoUrl,audioUrl,users_email) values (?,?,?,?,?,?);";

		try {
			Connection con = ConnectionManager.getConnection();

			con.setAutoCommit(true);
			PreparedStatement ps = con.prepareStatement(query); // generates sql query

			ps.setString(1, text);
			ps.setString(2, datetime);
			ps.setString(3, image);
			ps.setString(4, video);
			ps.setString(5, audio);
			ps.setString(6, users_email);

			ps.executeUpdate(); // execute it on linkedin database

			System.out.println("Post successfuly inserted");
			ps.close();
			con.close();
			post.setText(text);
			post.setDatetime(datetime);
			post.setImageUrl(image);
			post.setVideoUrl(video);
			post.setAudioUrl(audio);

			return true;
		} catch (SQLException e) { // TODO Auto-generated catch block
			System.out.println("Post not inserted to database");
			e.printStackTrace();
			return false;
		}
	}

	public static Post searchPost(String postid) {
		Post post = new Post();
		try {
			Connection currentCon = ConnectionManager.getConnection();

			String query = "select * from posts where postsId='" + postid + "'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				post.setText(rs.getString("text"));
				post.setAudioUrl(rs.getString("audioUrl"));
				post.setImageUrl(rs.getString("imageUrl"));
				post.setVideoUrl(rs.getString("videoUrl"));
				post.setDatetime(rs.getString("datetime"));
				String users_email = rs.getString("users_email");
				post.setUser(UserDAO.searchUser(users_email));
				post.setPostsId(rs.getInt("postsId"));
				String query1 = "select * from comments where posts_postsId='" + postid + "'";
				Statement stmt1 = currentCon.createStatement();
				ResultSet rs1 = stmt1.executeQuery(query1);
				List<Comment> commentlist = new ArrayList<Comment>();
				while (rs1.next()) {
					Comment comment1 = new Comment();
					comment1.setIdcomments(rs1.getInt("idcomments"));
					comment1.setCommentstext(rs1.getString("commentstext"));
					comment1.setDatetime(rs1.getString("datetime"));
					comment1.setPost(post);
					comment1.setCommenterName(rs1.getString("commenterName"));
					comment1.setCommenterImage(rs1.getString("commenterImage"));
					comment1.setEmail(rs1.getString("email"));

					commentlist.add(comment1);
				}
				post.setComments(commentlist);
				String query2 = "select * from likes where posts_postsId='" + postid + "'";
				Statement stmt2 = currentCon.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query2);
				List<Like> likelist = new ArrayList<Like>();
				while (rs2.next()) {
					Like like1 = new Like();
					like1.setLikeId(rs2.getInt("likeId"));
					like1.setEmail(rs2.getString("email"));
					like1.setPost(post);
					likelist.add(like1);
				}
				post.setLikes(likelist);
			}
			stmt.close();
			currentCon.close();
		} catch (Exception ex) {
			System.out.println("searchPost failed: An Exception has occurred! " + ex);
		}
		return post;
	}
	
	public static List<Post> searchPostList(String email) {
		List <Post> postlist=new ArrayList<Post>();
		try {
			Connection currentCon = ConnectionManager.getConnection();
			///initialization of posts list
			String query = "select * from posts where users_email='" + email +"'";
			Statement stmt = currentCon.createStatement();
			ResultSet rs = stmt.executeQuery(query);			
			while(rs.next())
			{
				Post post = new Post();
				post.setText(rs.getString("text"));
				post.setAudioUrl(rs.getString("audioUrl"));
				post.setImageUrl(rs.getString("imageUrl"));
				post.setVideoUrl(rs.getString("videoUrl"));
				post.setDatetime(rs.getString("datetime"));
				post.setUser(UserDAO.searchUser(email));
				post.setPostsId(rs.getInt("postsId"));
				
				String query1 = "select * from comments where posts_postsId='" + post.getPostsId() +"'";
				Statement stmt1 = currentCon.createStatement();
				ResultSet rs1 = stmt1.executeQuery(query1);
				List<Comment> commentlist =new ArrayList<Comment>();
				while(rs1.next()) {
					Comment comment = new Comment();
					comment.setIdcomments(rs1.getInt("idcomments"));
					comment.setCommentstext(rs1.getString("commentstext"));
					comment.setDatetime(rs1.getString("datetime"));
					comment.setPost(post);
					comment.setCommenterName(rs1.getString("commenterName"));
					comment.setCommenterImage(rs1.getString("commenterImage"));
					comment.setEmail(rs1.getString("email"));
					
					commentlist.add(comment);
				}
				post.setComments(commentlist);
				/////////like list
				String query2 = "select * from likes where posts_postsId='" + post.getPostsId() +"'";
				Statement stmt2 = currentCon.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query2);
				List<Like> likelist =new ArrayList<Like>();
				while(rs2.next()) {
					Like like = new Like();
					like.setLikeId(rs2.getInt("likeId"));
					like.setEmail(rs2.getString("email"));
					
					like.setPost(post);
					likelist.add(like);
				}
				post.setLikes(likelist);
				postlist.add(post);
				
				
			}
		} catch (Exception ex) {
			System.out.println("searchPostList failed: An Exception has occurred! " + ex);
		}
		
		
		return postlist;
	}
	
	public static List<Post> selectPostsBefore(User currentUser,String date) {

		List<Post> contactpostlist = new ArrayList<Post>(); // ta post tou kathe contact tou current user
		// connections
		try {
			Connection currentCon = ConnectionManager.getConnection();
			List<Contact> contactlist = currentUser.getContacts(); // oi contacts tou current user

			for (int i = 0; i < contactlist.size(); i++) { // gia kathe contact
				Contact temp = contactlist.get(i);
				String contactEmail = temp.getEmail();
				String query = "select * from posts where users_email='" + contactEmail + "' and datetime <= '"+date+"'";
				Statement stmt = currentCon.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) { // gia kathe post tou contact
					Post post = new Post();
					post.setText(rs.getString("text"));
					post.setPostsId(rs.getInt("postsId"));
					post.setAudioUrl(rs.getString("audioUrl"));
					post.setDatetime(rs.getString("datetime"));
					post.setImageUrl(rs.getString("imageUrl"));
					post.setVideoUrl(rs.getString("videoUrl"));
					post.setUser(UserDAO.searchUser(contactEmail));
					////////////////////////////////////////////////
					List<Comment> commentlist = new ArrayList<Comment>();
					commentlist = CommentDAO.searchComment(post.getPostsId(), post);
					List<Like> likelist = new ArrayList<Like>();
					likelist = LikeDAO.searchLike(post.getPostsId(), post);
					post.setComments(commentlist);
					post.setLikes(likelist);
					contactpostlist.add(post);

				}

			}

			currentCon.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactpostlist;
	}
	
	public static List<Post> selectPostsAfter(User currentUser,String date) {

		List<Post> contactpostlist = new ArrayList<Post>(); // ta post tou kathe contact tou current user
		// connections
		try {
			Connection currentCon = ConnectionManager.getConnection();
			List<Contact> contactlist = currentUser.getContacts(); // oi contacts tou current user

			for (int i = 0; i < contactlist.size(); i++) { // gia kathe contact
				Contact temp = contactlist.get(i);
				String contactEmail = temp.getEmail();
				String query = "select * from posts where users_email='" + contactEmail + "' and datetime > '"+date+"'";
				Statement stmt = currentCon.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) { // gia kathe post tou contact
					Post post = new Post();
					post.setText(rs.getString("text"));
					post.setPostsId(rs.getInt("postsId"));
					post.setAudioUrl(rs.getString("audioUrl"));
					post.setDatetime(rs.getString("datetime"));
					post.setImageUrl(rs.getString("imageUrl"));
					post.setVideoUrl(rs.getString("videoUrl"));
					post.setUser(UserDAO.searchUser(contactEmail));
					////////////////////////////////////////////////
					List<Comment> commentlist = new ArrayList<Comment>();
					commentlist = CommentDAO.searchComment(post.getPostsId(), post);
					List<Like> likelist = new ArrayList<Like>();
					likelist = LikeDAO.searchLike(post.getPostsId(), post);
					post.setComments(commentlist);
					post.setLikes(likelist);
					contactpostlist.add(post);

				}

			}

			currentCon.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactpostlist;
	}

}