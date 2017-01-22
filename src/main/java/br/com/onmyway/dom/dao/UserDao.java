package br.com.onmyway.dom.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import br.com.onmyway.dom.entity.User;
import br.com.onmyway.dom.repository.UserRepository;
import br.com.onmyway.util.HibernateUtil;

public class UserDao extends GenericDAOImpl<User, Integer> implements
	UserRepository {

    @Override
    public User saveUser(User user) {
	User savedObject = null;
	try {
	    HibernateUtil.beginTransaction();
	    savedObject = save(user);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    HibernateUtil.rollbackTransaction();
	    throw ex;
	}
	return savedObject;
    }

    @Override
    public User findById(Integer id) {
	User user = null;
	try {
	    HibernateUtil.beginTransaction();
	    user = (User) findByID(User.class, id);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	    throw ex;
	}
	return user;
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
	User user = null;
	try {
	    String sql = "SELECT u FROM User u WHERE u.email = :email and u.password = :password";
	    HibernateUtil.beginTransaction();
	    Query query = HibernateUtil.getSession().createQuery(sql)
		    .setParameter("email", email)
		    .setParameter("password", password);
	    user = (User) findOne(query);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	    throw ex;
	}
	return user;
    }
    
    @Override
    public User findUserByEmail(String email) {
	User user = null;
	try {
	    String sql = "SELECT u FROM User u WHERE u.email = :email";
	    HibernateUtil.beginTransaction();
	    Query query = HibernateUtil.getSession().createQuery(sql)
		    .setParameter("email", email);
	    user = (User) findOne(query);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	    throw ex;
	}
	return user;
    }

}