package br.com.onmyway.service.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.onmyway.dom.dao.UserDao;
import br.com.onmyway.dom.entity.User;
import br.com.onmyway.dom.repository.UserRepository;
import br.com.onmyway.util.CryptoUtil;

@Path("/user")
public class UserServiceREST {
    
    private static final Object EMPTY_STR = "";
    private UserRepository userDao = new UserDao();
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(@FormParam("email") String email, @FormParam("password") String password,  @FormParam("name") String name){
        
	Response response = null;
	try {
	    //checa se email j� est� em uso
	    User userBD = userDao.findUserByEmail(email);
	    if(userBD == null){
		User user = new User();
        	user.setEmail(email);
        	user.setSalt(CryptoUtil.generateSalt());
        	user.setPassword(CryptoUtil.getSecurePassword(password, user.getSaltBytes()));
        	user.setName(name);
        	User saveUser = userDao.saveUser(user);
        	response = Response.status(Status.OK).entity(saveUser).build();
	    }else{
		//user id=0 representa email email j� existente
		userBD.setId(0);
		response = Response.status(Status.OK).entity(userBD).build();
	    }
	} catch (Exception e) {
	    response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(EMPTY_STR).build();
	    e.printStackTrace();
	}
	return response;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password){
        
	Response response = null;
	try {
	    User user = userDao.findUserByEmail(email);
	    if(user == null){
		user = new User();
		user.setId(-1);
		response = Response.status(Status.OK).entity(user).build();
	    }else{
		String encryptedPassword = CryptoUtil.getSecurePassword(password, user.getSaltBytes());
		if(user.getPassword().equals(encryptedPassword)){
		    response = Response.status(Status.OK).entity(user).build();
		}else{
		    user.setId(0);
		    response = Response.status(Status.OK).entity(user).build();
		}
	    }
	} catch (Exception e) {
	    response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(EMPTY_STR).build();
	    e.printStackTrace();
	}
	return response;
    }
    
}
