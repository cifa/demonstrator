package uk.ac.edu4all.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the enrollment database table.
 * 
 */
@Embeddable
public class EnrollmentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int userId;

	private int courseId;

	public EnrollmentPK() {
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCourseId() {
		return this.courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnrollmentPK)) {
			return false;
		}
		EnrollmentPK castOther = (EnrollmentPK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.courseId == castOther.courseId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.courseId;
		
		return hash;
	}
}