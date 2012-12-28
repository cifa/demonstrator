package uk.ac.edu4all.dao;

import uk.ac.edu4all.domain.User;

public interface UserDao extends GenericDao<User> {

	public abstract User getUserByUsername(String username);

}