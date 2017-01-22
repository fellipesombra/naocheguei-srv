package br.com.onmyway.dom.repository;

import org.hibernate.HibernateException;

import br.com.onmyway.dom.dao.GenericDAO;
import br.com.onmyway.dom.entity.User;

public interface UserRepository extends GenericDAO<User, Integer>{

    User saveUser(User user) throws HibernateException;

    User findById(Integer id) throws HibernateException;

    User findUserByEmailAndPassword(String email, String password);
    
    User findUserByEmail(String email);
    
}
