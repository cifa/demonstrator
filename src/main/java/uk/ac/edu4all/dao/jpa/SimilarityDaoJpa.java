package uk.ac.edu4all.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import uk.ac.edu4all.domain.Course;
import uk.ac.edu4all.domain.Similarity;
import uk.ac.edu4all.domain.User;

@Repository("SimilarityDao")
public class SimilarityDaoJpa extends GenericDaoJpa<Similarity> {

	public List<Similarity> getMostSimilar(Course course, int amount) {
		TypedQuery<Similarity> query = entityManager.createQuery("from Similarity s " +
				"WHERE s.course1Bean = :course OR s.course2Bean = :course ORDER BY s.similarityIndex DESC", Similarity.class);
		query.setParameter("course", course);
		query.setMaxResults(amount);
		return query.getResultList();
	}

}
