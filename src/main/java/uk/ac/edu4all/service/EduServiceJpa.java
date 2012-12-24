package uk.ac.edu4all.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import uk.ac.edu4all.dao.jpa.*;
import uk.ac.edu4all.data.CourseRegistration;
import uk.ac.edu4all.domain.*;

@Service("EduService")
public class EduServiceJpa implements IEduService {

	@Inject private UserDaoJpa userDao;
	@Inject private CourseDaoJpa courseDao;
	@Inject private TreebranchDaoJpa treebranchDao;
	@Inject private CommentDaoJpa commentDao;
	@Inject private SimilarityDaoJpa similarityDao;
	@Inject private EnrollmentDaoJpa enrollmentDaoJpa;

	@Override
	public List<User> getUsers() {
		return userDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Course getCourse(Integer id) {
		return courseDao.get(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Treebranch getBranch(Integer id) {
		return treebranchDao.get(id);
	}

	@Override
	@Transactional
	public void saveCourse(Course course, MultipartFile image)
			throws IOException {
		// scale and convert into a JPEG
		if (image != null) {
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(image.getBytes()));
			BufferedImage scaledImg = new BufferedImage(350,(int) (img.getHeight() * (350.0 / img.getWidth())), img.getType());
			Graphics2D g = scaledImg.createGraphics();
			g.drawImage(img, 0, 0, 350, (int) (img.getHeight() * (350.0 / img.getWidth())), null);
			g.dispose();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(scaledImg, "jpeg", baos);
			course.setImage(baos.toByteArray());
			baos.close();
		}
		if (course.getId() == 0) {
			courseDao.save(course);
			// add new course to the similarity table
			for(Course c : courseDao.getAll()) {
				if(c.getId() != course.getId()) {
					saveNewSimilarityIndex(course, c, calculateSimilarity(course, c));
				}
			}
		} else {
			courseDao.update(course);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Course> getCoursesByCategory(int categoryId) {
		if(categoryId == 0) {
			return courseDao.getAll();
		} else {
			Treebranch tb = treebranchDao.get(categoryId);
			return getBranchCourses(new ArrayList<Course>(), tb);
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Course> getMostPopularCourses(int amount) {
		return courseDao.getPopularCourses(amount);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Map<String, Integer> getCategoryTree() {
		Map<String, Integer> cats = new TreeMap<String, Integer>();
		cats.put("All Courses", 0);
		for(Treebranch tb : treebranchDao.getAll()) {
			int id = tb.getId(); String name = "";
			do {
				name = tb.getName() + name;
				tb = tb.getTreebranch();
				if(tb != null) name = ": " + name;
			} while (tb != null);
			cats.put(name, id);
		}
		return cats;
	}

	@Override
	@Transactional(readOnly=true)
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	@Transactional
	public void saveComment(Comment comment) {
		comment.setPosted(new Date());
		commentDao.save(comment);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Course> getCourseRecommendations(Course course, int amount) {
		List<Similarity> recommendations = similarityDao.getMostSimilar(course, amount);
		List<Course> similarCourses = new ArrayList<Course>();
		for(Similarity s : recommendations) {
			if(s.getSimilarityIndex() == 0) break; 	// at least some similarity required
			if(s.getCourse1Bean().equals(course)) {
				similarCourses.add(s.getCourse2Bean());
			} else {
				similarCourses.add(s.getCourse1Bean());
			}
		}
		return similarCourses;
	}	

	@Override
	@Transactional(readOnly=true)
	public boolean isUserRegisted(User user, Course course) {
		return courseDao.getUserCourses(user).contains(course);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Course> getCoursesByUser(User user) {
		return courseDao.getUserCourses(user);
	}
	
	@Override
	@Transactional
	public boolean makeRegistrationPayment(CourseRegistration reg) {
		// save registration first
		Enrollment e = new Enrollment();
		e.setCourse(reg.getCourse());
		e.setEnrolDate(new Date());
		e.setUser(reg.getUser());
		e.setPaymentTransactionId(reg.getUuid());
		EnrollmentPK pk = new EnrollmentPK();
		pk.setCourseId(reg.getCourse().getId());
		pk.setUserId(reg.getUser().getId());
		e.setId(pk);
		enrollmentDaoJpa.save(e);
		// payment simulation - make it fail sometimes
		if(Math.random() > 0.9) {
			// oops payment transaction failed -> remove registration and notify user
			enrollmentDaoJpa.delete(e);
			return false;
		}
		return true;
	}
		
	@Override
	@Transactional
	public void updateSimilarityIndexes() {
		for(Similarity s : similarityDao.getAll()) {
			s.setSimilarityIndex(calculateSimilarity(s.getCourse1Bean(), s.getCourse2Bean()));
		}	
	}
	
	private List<Course> getBranchCourses(List<Course> courses, Treebranch tb) {
		courses.addAll(tb.getCourses());	
		for(Treebranch subtb : tb.getTreebranches()) getBranchCourses(courses, subtb);
		return courses;
	}
	
	private float calculateSimilarity(Course c1, Course c2) {
		float similarity = 0f;
		// a common branch somewhere along the tree make course similar
		int steps = getStepsToCommonTreebranch(c1, c2);
		if(steps != -1) {
			// there is a common branch - how far apart are the courses?
			steps += (getStepsToCommonTreebranch(c2, c1) - 1);
			// branch similarity weight 40% (remaining 60% determined by shared students)
			similarity = 40f / steps;
		}
		// get student ids of students on course 1
		List<Integer> studentIds = new ArrayList<Integer>();
		for(Enrollment e : c1.getEnrollments()) {
			studentIds.add(e.getUser().getId());
		}
		// how many of them are on the second course as well
		float sharedStudents = 0;
		for(Enrollment e : c2.getEnrollments()) {
			if(studentIds.contains(e.getUser().getId())) sharedStudents++;
			if(studentIds.size() == sharedStudents) break;
		}
		// increase similarity if students are studying both
		if(sharedStudents > 0) {
			// just simple percentages -> a better algorithm needed if for real
			similarity += 60 * (sharedStudents/c1.getEnrollments().size() + 
					sharedStudents/c2.getEnrollments().size()) / 2;
		}
		return similarity;
	}
	
	private int getStepsToCommonTreebranch(Course searcher, Course searched) {
		int steps = 0;
		Treebranch tb = searcher.getTreebranch();
		do {
			steps++;
			if(getBranchCourses(new ArrayList<Course>(), tb).contains(searched)) return steps;
			tb = tb.getTreebranch();
		} while (tb != null);
		return -1;
	}
	
	private void saveNewSimilarityIndex(Course c1, Course c2, float similarity) {
		Similarity s = new Similarity();
		s.setCourse1Bean(c1);
		s.setCourse2Bean(c2);
		s.setSimilarityIndex(similarity);
		SimilarityPK pk = new SimilarityPK();
		pk.setCourse1(c1.getId());
		pk.setCourse2(c2.getId());
		s.setId(pk);
		similarityDao.save(s);
	}
}
