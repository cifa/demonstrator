package uk.ac.edu4all.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the course database table.
 * 
 */
@Entity
@Table(name="course")
public class Course implements Serializable, DomainObject {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Size(min=20, message="The course description must be at least 20 characters long")
	@Lob
	@Column(nullable=false)
	private String description;

	@Lob
	@Column(nullable=false)
	private byte[] image;

	@Min(value=2, message="The course must be at least 2 weeks long")
	@Column(nullable=false)
	private int length;

	@Min(value=0, message="The course price cannot be negative")
	@Column(nullable=false)
	private int price;

	@NotNull(message="The course start date is required")
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date startDate;

	@Size(min=5, max=50, message="Title must be between 5 and 50 characters long")
	@Column(nullable=false, length=50)
	private String title;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="course")
	private Set<Comment> comments;

	//bi-directional many-to-one association to Treebranch
	@ManyToOne
	@JoinColumn(name="parentBranch", nullable=false)
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

	@Transient
	private MultipartFile imageFile;

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

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

}