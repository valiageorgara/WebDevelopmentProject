package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the likes database table.
 * 
 */
@Entity
@Table(name="likes")
@NamedQuery(name="Like.findAll", query="SELECT l FROM Like l")
public class Like implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int likeId;

	private String email;

	//bi-directional many-to-one association to Post
	@ManyToOne
	@JoinColumn(name="posts_postsId")
	private Post post;

	public Like() {
	}

	public int getLikeId() {
		return this.likeId;
	}

	public void setLikeId(int likeId) {
		this.likeId = likeId;
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