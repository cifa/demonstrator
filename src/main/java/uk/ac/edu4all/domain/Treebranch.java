package uk.ac.edu4all.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the treebranch database table.
 * 
 */
@Entity
public class Treebranch implements Serializable, DomainObject {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-one association to Course
	@OneToMany(mappedBy="treebranch")
	private Set<Course> courses;

	//bi-directional many-to-one association to Treebranch
	@ManyToOne
	@JoinColumn(name="parentNode")
	private Treebranch treebranch;

	//bi-directional many-to-one association to Treebranch
	@OneToMany(mappedBy="treebranch")
	private Set<Treebranch> treebranches;

	public Treebranch() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Treebranch getTreebranch() {
		return this.treebranch;
	}

	public void setTreebranch(Treebranch treebranch) {
		this.treebranch = treebranch;
	}

	public Set<Treebranch> getTreebranches() {
		return this.treebranches;
	}

	public void setTreebranches(Set<Treebranch> treebranches) {
		this.treebranches = treebranches;
	}

}