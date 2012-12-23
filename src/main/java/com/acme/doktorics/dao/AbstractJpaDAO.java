package com.acme.doktorics.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

public class AbstractJpaDAO<T> implements IAbstractJpaDAO<T> {
	private Class<T> clazz;

	@PersistenceContext(name="persistenceUnit")
	protected EntityManager entityManager;
	
	public void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	
	public T findOne(final Long id) {
		return entityManager.find(clazz, id);
	}

	
	public List<T> findAll() {
		return entityManager.createQuery("from " + clazz.getName())
				.getResultList();
	}
	
	public void save(final T entity) {
		try{
			entityManager.persist(entity);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	public void update(final T entity) {
		entityManager.merge(entity);
	}

	public void delete(final T entity) {
		entityManager.remove(entity);
	}



}
