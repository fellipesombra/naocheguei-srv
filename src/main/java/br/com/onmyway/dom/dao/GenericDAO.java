package br.com.onmyway.dom.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;

@SuppressWarnings("rawtypes")
public interface GenericDAO<T, ID extends Serializable> {

    public T save(T entity);

    public T merge(T entity);

    public void delete(T entity);

    public List findAll(Class clazz);

    public T findByID(Class clazz, Integer id);

    public List<T> findMany(Query query);

    public T findOne(Query query);
}