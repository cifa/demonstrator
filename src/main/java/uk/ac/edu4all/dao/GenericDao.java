package uk.ac.edu4all.dao;

import java.util.List;

import uk.ac.edu4all.domain.DomainObject;

public interface GenericDao<T extends DomainObject> {

	public abstract T get(Integer id);

	public abstract List<T> getAll();

	public abstract void save(T object);

	public abstract T update(T object);

	public abstract void delete(T object);

}