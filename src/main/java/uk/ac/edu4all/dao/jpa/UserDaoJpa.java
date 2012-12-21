package uk.ac.edu4all.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import uk.ac.edu4all.domain.User;

@Repository("UserDao")
public class UserDaoJpa extends GenericDaoJpa<User> {
	public User getUserByUsername(String username) {
		User user = null;
        TypedQuery<User> query = entityManager.createQuery("from User u where u.username = :username", User.class);
        query.setParameter("username", username);       
        List<User> results = query.getResultList();
        if(results != null && ! results.isEmpty()) user = results.get(0);
        return user;
	}
}
