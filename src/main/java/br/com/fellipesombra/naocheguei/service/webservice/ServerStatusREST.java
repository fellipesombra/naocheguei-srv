package br.com.fellipesombra.naocheguei.service.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.fellipesombra.naocheguei.valueobject.ContatoDTO;

@Path("/server")
public class ServerStatusREST {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response check(){
	Response response = Response.status(Status.OK).entity(new ContatoDTO()).build();
	return response;
    }

}
