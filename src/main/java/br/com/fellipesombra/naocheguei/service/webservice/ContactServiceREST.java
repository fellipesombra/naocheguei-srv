package br.com.fellipesombra.naocheguei.service.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;

import com.google.common.collect.Lists;

import br.com.fellipesombra.naocheguei.dom.dao.ContactDao;
import br.com.fellipesombra.naocheguei.dom.dao.UserDao;
import br.com.fellipesombra.naocheguei.dom.entity.Contact;
import br.com.fellipesombra.naocheguei.dom.entity.Trip;
import br.com.fellipesombra.naocheguei.dom.entity.User;
import br.com.fellipesombra.naocheguei.dom.repository.ContactRepository;
import br.com.fellipesombra.naocheguei.dom.repository.UserRepository;
import br.com.fellipesombra.naocheguei.enums.RestResponseStatus;
import br.com.fellipesombra.naocheguei.enums.TripStatus;
import br.com.fellipesombra.naocheguei.valueobject.ContactResponse;
import br.com.fellipesombra.naocheguei.valueobject.ContatoDTO;

@Path("/contact")
public class ContactServiceREST {

    private ContactRepository contactDao = new ContactDao();

    private UserRepository userDao = new UserDao();

    @POST
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerList(List<ContatoDTO> contatosDTO) {

	Response response = null;
	try {
	    if (contatosDTO == null || contatosDTO.isEmpty()) {
		response = Response.status(Status.INTERNAL_SERVER_ERROR)
			.entity("Lista de contatos vazia").build();
	    } else {
		User user = userDao.findById(Integer.valueOf(contatosDTO.get(0)
			.getUserId()));
		List<Contact> contatos = Lists.newArrayList();
		for (ContatoDTO contatoDTO : contatosDTO) {
		    Contact contact = new Contact();
		    contact.setCellphoneNumber(contatoDTO.getCellphone());
		    contact.setEmail(contatoDTO.getEmail());
		    contact.setUser(user);
		    contatos.add(contact);
		}
		contactDao.saveContacts(contatos);
		response = Response.status(Status.OK)
			.entity("Contatos salvos com sucesso").build();
	    }
	} catch (Exception e) {
	    response = Response.status(Status.INTERNAL_SERVER_ERROR)
		    .entity("Erro ao inserir contatos: " + e.getMessage())
		    .build();
	    e.printStackTrace();
	}
	return response;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(@FormParam("id") String userId, @FormParam("email") String email){
	Response response = null;
	try {
	    User user = userDao.findById(Integer.valueOf(userId));
	    Contact contact = new Contact();
	    contact.setEmail(email);
	    contact.setUser(user);
	    contactDao.saveContact(contact);
	    response = Response.status(Status.OK)
		    .entity(new ContactResponse(RestResponseStatus.CONTACT_ADD.getStatusCode(),contact)).build();
	} catch (Exception e) {
	    if(e instanceof ConstraintViolationException){
		response = Response.status(Status.OK)
			    .entity(new ContactResponse(RestResponseStatus.CONTACT_ALREADY_EXISTS.getStatusCode()))
			    .build();
	    }else{
		response = Response.status(Status.INTERNAL_SERVER_ERROR)
			    .entity("Erro ao inserir contatos: " + e.getMessage())
			    .build();
	    }
	} 
	return response;
    }
    
    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUserContacts(@PathParam ("userId") String userId){
	Response response = null;
	try {
	    User user = userDao.findById(Integer.valueOf(userId));
	    List<Contact> contacts = contactDao.findAllByUser(user);
	    response = Response.status(Status.OK)
		    .entity(contacts).build();
	} catch (Exception e) {
	    response = Response.status(Status.INTERNAL_SERVER_ERROR)
		    .entity("Erro ao inserir contatos: " + e.getMessage())
		    .build();
	    e.printStackTrace();
	}
	return response;
    }
    
    @GET
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String contactId){
	Response response = null;
	try {
	    Contact contact = contactDao.findById(Integer.valueOf(contactId));
	    contactDao.deleteContact(contact);
	    response = Response.status(Status.OK).entity(new ContactResponse(RestResponseStatus.CONTACT_DELETED.getStatusCode())).build();
	} catch (Exception e) {
	    response = Response.status(Status.INTERNAL_SERVER_ERROR)
		    .entity("Erro ao recuperar viagem").build();
	    e.printStackTrace();
	}
	return response;
    }

}
