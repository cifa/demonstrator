package uk.ac.edu4all.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import uk.ac.edu4all.data.CourseRegistration;
import uk.ac.edu4all.domain.Comment;
import uk.ac.edu4all.domain.Course;
import uk.ac.edu4all.domain.Treebranch;
import uk.ac.edu4all.domain.User;

public interface IEduService {

	public abstract List<User> getUsers();

	public abstract Course getCourse(Integer id);

	public abstract Treebranch getBranch(Integer id);

	public abstract void saveCourse(Course course, MultipartFile image) throws IOException;

	public abstract List<Course> getCoursesByCategory(int categoryId);

	Map<String, Integer> getCategoryTree();

	public abstract User getUserByUsername(String username);

	public abstract void saveComment(Comment comment);

	public abstract List<Course> getCourseRecommendations(Course course, int amount);

	public abstract void updateSimilarityIndexes();

	public abstract List<Course> getMostPopularCourses(int i);

	public abstract boolean isUserRegisted(User user, Course course);

	List<Course> getCoursesByUser(User user);

	public abstract boolean makeRegistrationPayment(CourseRegistration reg);

}
