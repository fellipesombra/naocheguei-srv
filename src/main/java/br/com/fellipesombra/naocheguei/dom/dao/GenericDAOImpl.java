package br.com.fellipesombra.naocheguei.dom.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.fellipesombra.naocheguei.util.HibernateUtil;

@SuppressWarnings({"unchecked","rawtypes"})
public abstract class GenericDAOImpl<T, ID extends Serializable> implements
	GenericDAO<T, ID> {

    protected Session getSession() {
	return HibernateUtil.getSession();
    }

    public T save(T entity) {
	Session hibernateSession = this.getSession();
	T object = (T) hibernateSession.merge(entity);
	return object;
    }

    public T merge(T entity) {
	Session hibernateSession = this.getSession();
	T object = (T) hibernateSession.merge(entity);
	return object;
    }

    public void delete(T entity) {
	Session hibernateSession = this.getSession();
	hibernateSession.delete(entity);
    }

    public T findByID(Class clazz, Integer id) {
	Session hibernateSession = this.getSession();
	T t = null;
	t = (T) hibernateSession.get(clazz, id);
	return t;
    }

    public List findAll(Class clazz) {
	Session hibernateSession = this.getSession();
	List T = null;
	Query query = hibernateSession.createQuery("from " + clazz.getName());
	T = query.list();
	return T;
    }
    
    public List<T> findMany(Query query) {
        List<T> t;
        t = (List<T>) query.list();
        return t;
    }
 
    public T findOne(Query query) {
        T t;
        t = (T) query.uniqueResult();
        return t;
    }
}