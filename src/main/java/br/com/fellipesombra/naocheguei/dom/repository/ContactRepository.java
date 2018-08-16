package br.com.fellipesombra.naocheguei.dom.repository;

import java.util.List;

import br.com.fellipesombra.naocheguei.dom.dao.GenericDAO;
import br.com.fellipesombra.naocheguei.dom.entity.Contact;
import br.com.fellipesombra.naocheguei.dom.entity.User;

public interface ContactRepository extends GenericDAO<Contact, Integer>{

    List<Contact> saveContacts(List<Contact> contatos);

    List<Contact> findAllByUser(User user);

    Contact saveContact(Contact contact);

    Contact findById(Integer id);

    void deleteContact(Contact contact);
}
