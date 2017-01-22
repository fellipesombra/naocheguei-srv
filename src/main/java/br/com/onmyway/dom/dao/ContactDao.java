package br.com.onmyway.dom.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import br.com.onmyway.dom.entity.Contact;
import br.com.onmyway.dom.entity.Trip;
import br.com.onmyway.dom.entity.User;
import br.com.onmyway.dom.repository.ContactRepository;
import br.com.onmyway.util.HibernateUtil;

import com.google.common.collect.Lists;

public class ContactDao extends GenericDAOImpl<Contact, Integer> implements
	ContactRepository {

    @Override
    public List<Contact> saveContacts(List<Contact> contatos) {
	List<Contact> savedObject = Lists.newArrayList();
	try {
	    HibernateUtil.beginTransaction();
	    for (Contact contact : contatos) {
		savedObject.add(save(contact));
	    }
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	    HibernateUtil.rollbackTransaction();
	}
	return savedObject;

    }

    @Override
    public List<Contact> findAllByUser(User user) {
	List<Contact> contacts = null;
	String sql = "SELECT c FROM Contact c WHERE c.user.id= :id";
	HibernateUtil.beginTransaction();
	Query query = HibernateUtil.getSession().createQuery(sql)
		.setParameter("id", user.getId());
	contacts = (List<Contact>) findMany(query);
	HibernateUtil.commitTransaction();
	return contacts;
    }

    @Override
    public Contact saveContact(Contact contact) {
	Contact savedObject = null;
	try {
	    HibernateUtil.beginTransaction();
	    savedObject = save(contact);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	    HibernateUtil.rollbackTransaction();
	}
	return savedObject;
    }

    @Override
    public Contact findById(Integer id) {
	Contact savedObject = null;
	try {
	    HibernateUtil.beginTransaction();
	    savedObject = findByID(Contact.class, id);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	}
	return savedObject;
    }

    @Override
    public void deleteContact(Contact contact) {
	HibernateUtil.beginTransaction();
	delete(contact);
	HibernateUtil.commitTransaction();
    }

}
