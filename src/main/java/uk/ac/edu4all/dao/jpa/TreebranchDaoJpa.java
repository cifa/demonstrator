package uk.ac.edu4all.dao.jpa;

import org.springframework.stereotype.Repository;

import uk.ac.edu4all.dao.TreebranchDao;
import uk.ac.edu4all.domain.Treebranch;

@Repository("TreebranchDao")
public class TreebranchDaoJpa extends GenericDaoJpa<Treebranch> implements TreebranchDao {

}
