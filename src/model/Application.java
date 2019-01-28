package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the applications database table.
 * 
 */
@Entity
@Table(name="applications")
@NamedQuery(name="Application.findAll", query="SELECT a FROM Application a")
public class Application implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int applyId;

	private String email;

	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn(name="jobs_jobsId")
	private Job job;

	public Application() {
	}

	public int getApplyId() {
		return this.applyId;
	}

	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

}