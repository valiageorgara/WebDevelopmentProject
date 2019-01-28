package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the jobs database table.
 * 
 */
@Entity
@Table(name="jobs")
@NamedQuery(name="Job.findAll", query="SELECT j FROM Job j")
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int jobsId;

	private String company;

	private String datetime;

	private String jobtitle;

	private String location;

	//bi-directional many-to-one association to Application
	@OneToMany(mappedBy="job")
	private List<Application> applications;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="users_email")
	private User user;

	public Job() {
	}

	public int getJobsId() {
		return this.jobsId;
	}

	public void setJobsId(int jobsId) {
		this.jobsId = jobsId;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getJobtitle() {
		return this.jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public Application addApplication(Application application) {
		getApplications().add(application);
		application.setJob(this);

		return application;
	}

	public Application removeApplication(Application application) {
		getApplications().remove(application);
		application.setJob(null);

		return application;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}