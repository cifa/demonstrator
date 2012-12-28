package uk.ac.edu4all.dao;

import java.util.List;

import uk.ac.edu4all.domain.*;

public interface SimilarityDao extends GenericDao<Similarity> {

	public abstract List<Similarity> getMostSimilar(Course course, int amount);

}