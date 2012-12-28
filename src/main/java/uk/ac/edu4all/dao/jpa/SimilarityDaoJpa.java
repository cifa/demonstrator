package uk.ac.edu4all.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import uk.ac.edu4all.dao.SimilarityDao;
import uk.ac.edu4all.domain.Course;
import uk.ac.edu4all.domain.Similarity;
import uk.ac.edu4all.domain.User;

@Repository("SimilarityDao")
public class SimilarityDaoJpa extends GenericDaoJpa<Similarity> implements SimilarityDao {

	/* (non-Javadoc)
	 * @see uk.ac.edu4all.dao.jpa.SimilarityDao#getMostSimilar(uk.ac.edu4all.domain.Course, int)
	 */
	@Override
	public List<Similarity> getMostSimilar(Course course, int amount) {
		TypedQuery<Similarity> query = entityManager.createQuery("from Similarity s " +
				"WHERE s.course1Bean = :course OR s.course2Bean = :course ORDER BY s.similarityIndex DESC", Similarity.class);
		query.setParameter("course", course);
		query.setMaxResults(amount);
		return query.getResultList();
	}

}
