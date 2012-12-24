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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date enrolDate;

	@Column(nullable=false, length=255)
	private String paymentTransactionId;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="courseId", nullable=false, insertable=false, updatable=false)
	private Course course;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userId", nullable=false, insertable=false, updatable=false)
	private User user;

	public Enrollment() {
	}

	public EnrollmentPK getId() {
		return this.id;
	}

	public void setId(EnrollmentPK id) {
		this.id = id;
	}

	public Date getEnrolDate() {
		return this.enrolDate;
	}

	public void setEnrolDate(Date enrolDate) {
		this.enrolDate = enrolDate;
	}

	public String getPaymentTransactionId() {
		return this.paymentTransactionId;
	}

	public void setPaymentTransactionId(String paymentTransactionNo) {
		this.paymentTransactionId = paymentTransactionNo;
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