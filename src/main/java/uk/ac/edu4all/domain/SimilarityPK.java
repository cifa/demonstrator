package uk.ac.edu4all.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the similarity database table.
 * 
 */
@Embeddable
public class SimilarityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false)
	private int course1;

	@Column(unique=true, nullable=false)
	private int course2;

	public SimilarityPK() {
	}
	public int getCourse1() {
		return this.course1;
	}
	public void setCourse1(int course1) {
		this.course1 = course1;
	}
	public int getCourse2() {
		return this.course2;
	}
	public void setCourse2(int course2) {
		this.course2 = course2;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SimilarityPK)) {
			return false;
		}
		SimilarityPK castOther = (SimilarityPK)other;
		return 
			(this.course1 == castOther.course1)
			&& (this.course2 == castOther.course2);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.course1;
		hash = hash * prime + this.course2;
		
		return hash;
	}
}