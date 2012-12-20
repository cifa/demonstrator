package uk.ac.edu4all.service;

import java.util.*;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.edu4all.dao.jpa.*;
import uk.ac.edu4all.domain.*;

@Service("EduService")
public class EduServiceJpa implements IEduService {
	
	@Inject private UserDaoJpa userDao;
	@Inject private CourseDaoJpa courseDao;
	@Inject private TreebranchDao treebranchDao;
	
	@Override
	public List<User> getUsers() {
		return userDao.getAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public Course getCourse(Integer id) {
		return courseDao.get(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Treebranch getBranch(Integer id) {
		return treebranchDao.get(id);
	}
	
	@Override
	@Transactional
	public void saveCourse(Course course) {
		if(course.getId() == 0) {
			courseDao.save(course);
		} else {
			courseDao.update(course);
		}
	}
}
