package uk.ac.edu4all.dao.jpa;

import org.springframework.stereotype.Repository;
import uk.ac.edu4all.domain.User;

@Repository("UserDao")
public class UserDaoJpa extends GenericDaoJpa<User> {

}
