package uk.ac.edu4all.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the similarity database table.
 * 
 */
@Entity
public class Similarity implements Serializable, DomainObject {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private float similarityIndex;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="course1")
	private Course course1Bean;

	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="course2")
	private Course course2Bean;

	public Similarity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getSimilarityIndex() {
		return this.similarityIndex;
	}

	public void setSimilarityIndex(float similarityIndex) {
		this.similarityIndex = similarityIndex;
	}

	public Course getCourse1Bean() {
		return this.course1Bean;
	}

	public void setCourse1Bean(Course course1Bean) {
		this.course1Bean = course1Bean;
	}

	public Course getCourse2Bean() {
		return this.course2Bean;
	}

	public void setCourse2Bean(Course course2Bean) {
		this.course2Bean = course2Bean;
	}

}