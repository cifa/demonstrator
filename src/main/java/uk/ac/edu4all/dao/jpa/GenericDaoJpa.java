package uk.ac.edu4all.dao.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.*;

import uk.ac.edu4all.dao.GenericDao;
import uk.ac.edu4all.domain.DomainObject;

public class GenericDaoJpa<T extends DomainObject> implements GenericDao<T> {
	
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

	/* (non-Javadoc)
	 * @see uk.ac.edu4all.dao.jpa.GenericDao#get(java.lang.Integer)
	 */
	@Override
	public T get(Integer id) {
		if(id != null) return entityManager.find(entityType, id); else return null;
	}

	/* (non-Javadoc)
	 * @see uk.ac.edu4all.dao.jpa.GenericDao#getAll()
	 */
	@Override
	public List<T> getAll() {
		return entityManager.createQuery("SELECT o FROM " + entityType.getName() + " o", entityType).getResultList();
	}

	/* (non-Javadoc)
	 * @see uk.ac.edu4all.dao.jpa.GenericDao#save(T)
	 */
	@Override
	public void save(T object) {
		entityManager.persist(object);
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.edu4all.dao.jpa.GenericDao#update(T)
	 */
	@Override
	public T update(T object) {
		return entityManager.merge(object);
	}

	/* (non-Javadoc)
	 * @see uk.ac.edu4all.dao.jpa.GenericDao#delete(T)
	 */
	@Override
	public void delete(T object) {
		if(! entityManager.contains(object)) { object = entityManager.merge(object);}
		entityManager.remove(object);
	}
}