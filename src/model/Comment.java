package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the comments database table.
 * 
 */
@Entity
@Table(name="comments")
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idcomments;

	private String commenterImage;

	private String commenterName;

	private String commentstext;

	private String datetime;

	private String email;

	//bi-directional many-to-one association to Post
	@ManyToOne
	@JoinColumn(name="posts_postsId")
	private Post post;

	public Comment() {
	}

	public int getIdcomments() {
		return this.idcomments;
	}

	public void setIdcomments(int idcomments) {
		this.idcomments = idcomments;
	}

	public String getCommenterImage() {
		return this.commenterImage;
	}

	public void setCommenterImage(String commenterImage) {
		this.commenterImage = commenterImage;
	}

	public String getCommenterName() {
		return this.commenterName;
	}

	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}

	public String getCommentstext() {
		return this.commentstext;
	}

	public void setCommentstext(String commentstext) {
		this.commentstext = commentstext;
	}

	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Post getPost() {
		return this.post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}