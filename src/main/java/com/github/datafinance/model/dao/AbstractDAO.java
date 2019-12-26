package com.github.datafinance.model.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public abstract class AbstractDAO<T extends Object> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public T insert(T entity) {
		getEntityManager().persist(entity);
		return entity;
	}

	public T getById(Object pk) {
		try {
			return (T) getEntityManager().find(getParameterizedClass(), pk);
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<T> getAll() {
		return getEntityManager()
				.createQuery("select o from " + getParameterizedClass().getSimpleName() + " o", getParameterizedClass())
				.getResultList();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getParameterizedClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

}
