package uk.ac.edu4all.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.Date;


/**
 * The persistent class for the comment database table.
 * 
 */
@Entity
@Table(name="comment")
public class Comment implements Serializable, DomainObject {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Size(min=10, max=250, message="Comment must be between 10 and 250 characters long")
	@Column(nullable=false, length=250)
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date posted;

	@Column(nullable=false)
	private int rating;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courseId", nullable=false)
	private Course course;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userId", nullable=false)
	private User user;

	public Comment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPosted() {
		return this.posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}