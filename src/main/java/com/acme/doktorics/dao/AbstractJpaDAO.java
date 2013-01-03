package com.acme.doktorics.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.acme.doktorics.annotations.Trace;

public class AbstractJpaDAO<T> implements IAbstractJpaDAO<T> {
    private Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    @Override
    @Trace
    public T findOne(final Long id) {
        return entityManager.find(clazz, id);
    }

    @Override
    @Trace
    public List<T> findAll() {
        List<T> result = entityManager.createQuery("from " + clazz.getName())
                .getResultList();
        return result;

    }

    @Override
    @Trace
    public void save(final T entity) {
        entityManager.persist(entity);
    }

    @Override
    @Trace
    public void update(final T entity) {
        entityManager.merge(entity);
    }

    @Override
    @Trace
    public void delete(final T entity) {
        entityManager.remove(entity);
    }

}
