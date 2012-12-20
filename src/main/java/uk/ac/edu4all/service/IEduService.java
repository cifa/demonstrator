package uk.ac.edu4all.service;

import java.util.List;

import uk.ac.edu4all.domain.Course;
import uk.ac.edu4all.domain.Treebranch;
import uk.ac.edu4all.domain.User;

public interface IEduService {

	public abstract List<User> getUsers();

	public abstract Course getCourse(Integer id);

	public abstract Treebranch getBranch(Integer id);

	public abstract void saveCourse(Course course);

}
