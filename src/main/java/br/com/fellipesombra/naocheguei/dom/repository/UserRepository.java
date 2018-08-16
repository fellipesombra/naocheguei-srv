package br.com.fellipesombra.naocheguei.dom.repository;

import org.hibernate.HibernateException;

import br.com.fellipesombra.naocheguei.dom.dao.GenericDAO;
import br.com.fellipesombra.naocheguei.dom.entity.User;

public interface UserRepository extends GenericDAO<User, Integer>{

    User saveUser(User user) throws HibernateException;

    User findById(Integer id) throws HibernateException;

    User findUserByEmailAndPassword(String email, String password);
    
    User findUserByEmail(String email);
    
}
