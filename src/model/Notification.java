package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the notifications database table.
 * 
 */
@Entity
@Table(name="notifications")
@NamedQuery(name="Notification.findAll", query="SELECT n FROM Notification n")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idnotifications;

	private String datetime;

	private String email;

	private int postid;

	private String type;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="users_email")
	private User user;

	public Notification() {
	}

	public int getIdnotifications() {
		return this.idnotifications;
	}

	public void setIdnotifications(int idnotifications) {
		this.idnotifications = idnotifications;
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

	public int getPostid() {
		return this.postid;
	}

	public void setPostid(int postid) {
		this.postid = postid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}