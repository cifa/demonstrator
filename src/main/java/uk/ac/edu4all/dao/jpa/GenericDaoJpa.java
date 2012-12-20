package uk.ac.edu4all.dao.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.*;
import uk.ac.edu4all.domain.DomainObject;

public class GenericDaoJpa<T extends DomainObject> {
	
	private Class<T> entityType;
	protected EntityManager entityManager;
	
    @SuppressWarnings("unchecked")
	public GenericDaoJpa() {
    	// use reflection to get our entity type (domain object)
        this.entityType = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
    	entityManager = em;
    }

	public T get(Integer id) {
		if(id != null) return entityManager.find(entityType, id); else return null;
	}

	public List<T> getAll() {
		return entityManager.createQuery("SELECT o FROM " + entityType.getName() + " o", entityType).getResultList();
	}

	public void save(T object) {
		entityManager.persist(object);
	}
	
	public T update(T object) {
		return entityManager.merge(object);
	}

	public void delete(T object) {
		if(! entityManager.contains(object)) { object = entityManager.merge(object);}
		entityManager.remove(object);
	}
}