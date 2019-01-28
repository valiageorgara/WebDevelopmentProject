package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the messages database table.
 * 
 */
@Entity
@Table(name="messages")
@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idmessages;

	private String contactemail;

	private String dateOfMsg;

	private String state;

	private String text;

	private int unread;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="users_email")
	private User user;

	public Message() {
	}

	public int getIdmessages() {
		return this.idmessages;
	}

	public void setIdmessages(int idmessages) {
		this.idmessages = idmessages;
	}

	public String getContactemail() {
		return this.contactemail;
	}

	public void setContactemail(String contactemail) {
		this.contactemail = contactemail;
	}

	public String getDateOfMsg() {
		return this.dateOfMsg;
	}

	public void setDateOfMsg(String dateOfMsg) {
		this.dateOfMsg = dateOfMsg;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getUnread() {
		return this.unread;
	}

	public void setUnread(int unread) {
		this.unread = unread;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}