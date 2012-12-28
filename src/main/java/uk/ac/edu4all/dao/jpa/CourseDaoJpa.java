package uk.ac.edu4all.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import uk.ac.edu4all.dao.CourseDao;
import uk.ac.edu4all.domain.Course;
import uk.ac.edu4all.domain.User;

@Repository("CourseDao")
public class CourseDaoJpa extends GenericDaoJpa<Course> implements CourseDao {

	/* (non-Javadoc)
	 * @see uk.ac.edu4all.dao.jpa.CourseDao#getPopularCourses(int)
	 */
	@Override
	public List<Course> getPopularCourses(int amount) {
		TypedQuery<Course> query = entityManager.createQuery("FROM Course c " +
				"ORDER BY SIZE(c.enrollments) DESC", Course.class);
		query.setMaxResults(amount);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see uk.ac.edu4all.dao.jpa.CourseDao#getUserCourses(uk.ac.edu4all.domain.User)
	 */
	@Override
	public List<Course> getUserCourses(User user) {
		TypedQuery<Course> query = entityManager.createQuery("SELECT e.course from Enrollment e " +
				"WHERE e.user = :user", Course.class);
		query.setParameter("user", user);
		return query.getResultList();
	}

}
