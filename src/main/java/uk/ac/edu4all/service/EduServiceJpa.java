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
import uk.ac.edu4all.domain.*;

@Service("EduService")
public class EduServiceJpa implements IEduService {

	@Inject private UserDaoJpa userDao;
	@Inject private CourseDaoJpa courseDao;
	@Inject private TreebranchDaoJpa treebranchDao;
	@Inject private CommentDaoJpa commentDao;

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

	private List<Course> getBranchCourses(List<Course> courses, Treebranch tb) {
		courses.addAll(tb.getCourses());	
		for(Treebranch subtb : tb.getTreebranches()) getBranchCourses(courses, subtb);
		return courses;
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
}
