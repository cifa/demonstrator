package uk.ac.edu4all.domain;

import java.io.IOException;
import java.io.Serializable;
import javax.persistence.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the course database table.
 * 
 */
@Entity
public class Course implements Serializable, DomainObject {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String description;

	@Lob
	private byte[] image;

	private int length;

	private int price;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	private String title;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="course")
	private Set<Comment> comments;

	//bi-directional many-to-one association to Treebranch
	@ManyToOne
	@JoinColumn(name="parentBranch")
	private Treebranch treebranch;

	//bi-directional many-to-one association to Enrollment
	@OneToMany(mappedBy="course")
	private Set<Enrollment> enrollments;

	//bi-directional many-to-one association to Similarity
	@OneToMany(mappedBy="course1Bean")
	private Set<Similarity> similarities1;

	//bi-directional many-to-one association to Similarity
	@OneToMany(mappedBy="course2Bean")
	private Set<Similarity> similarities2;

	public Course() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Treebranch getTreebranch() {
		return this.treebranch;
	}

	public void setTreebranch(Treebranch treebranch) {
		this.treebranch = treebranch;
	}

	public Set<Enrollment> getEnrollments() {
		return this.enrollments;
	}

	public void setEnrollments(Set<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	public Set<Similarity> getSimilarities1() {
		return this.similarities1;
	}

	public void setSimilarities1(Set<Similarity> similarities1) {
		this.similarities1 = similarities1;
	}

	public Set<Similarity> getSimilarities2() {
		return this.similarities2;
	}

	public void setSimilarities2(Set<Similarity> similarities2) {
		this.similarities2 = similarities2;
	}
	
//	@Transient
//	public void setImageFile(MultipartFile image) {
//		try {
//			setImage(image.getBytes());
//		} catch (IOException e) {e.printStackTrace();}
//	}

}