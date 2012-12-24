package uk.ac.edu4all.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the enrollment database table.
 * 
 */
@Entity
@Table(name="enrollment")
public class Enrollment implements Serializable, DomainObject {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentPK id;

	private int courseRating;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date enrolDate;

	@Column(nullable=false, length=32)
	private String paymentTransactionNo;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userId", nullable=false, insertable=false, updatable=false)
	private User user;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courseId", nullable=false, insertable=false, updatable=false)
	private Course course;

	public Enrollment() {
	}

	public EnrollmentPK getId() {
		return this.id;
	}

	public void setId(EnrollmentPK id) {
		this.id = id;
	}

	public int getCourseRating() {
		return this.courseRating;
	}

	public void setCourseRating(int courseRating) {
		this.courseRating = courseRating;
	}

	public Date getEnrolDate() {
		return this.enrolDate;
	}

	public void setEnrolDate(Date enrolDate) {
		this.enrolDate = enrolDate;
	}

	public String getPaymentTransactionNo() {
		return this.paymentTransactionNo;
	}

	public void setPaymentTransactionNo(String paymentTransactionNo) {
		this.paymentTransactionNo = paymentTransactionNo;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}