package uk.ac.edu4all.dao;

import java.util.List;

import uk.ac.edu4all.domain.*;

public interface CourseDao extends GenericDao<Course> {

	public abstract List<Course> getPopularCourses(int amount);

	public abstract List<Course> getUserCourses(User user);

}