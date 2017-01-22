package br.com.onmyway.dom.repository;

import java.util.List;

import br.com.onmyway.dom.dao.GenericDAO;
import br.com.onmyway.dom.entity.Contact;
import br.com.onmyway.dom.entity.User;

public interface ContactRepository extends GenericDAO<Contact, Integer>{

    List<Contact> saveContacts(List<Contact> contatos);

    List<Contact> findAllByUser(User user);

    Contact saveContact(Contact contact);

    Contact findById(Integer id);

    void deleteContact(Contact contact);
}
