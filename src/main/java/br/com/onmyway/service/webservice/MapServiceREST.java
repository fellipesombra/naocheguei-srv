package br.com.onmyway.service.webservice;

import java.util.Calendar;
import java.util.Date;
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

import br.com.onmyway.dom.dao.PositionDao;
import br.com.onmyway.dom.dao.TripDao;
import br.com.onmyway.dom.dao.UserDao;
import br.com.onmyway.dom.entity.Position;
import br.com.onmyway.dom.entity.Trip;
import br.com.onmyway.dom.entity.User;
import br.com.onmyway.dom.repository.PositionRepository;
import br.com.onmyway.dom.repository.TripRepository;
import br.com.onmyway.dom.repository.UserRepository;
import br.com.onmyway.enums.RestResponseStatus;
import br.com.onmyway.enums.TripStatus;
import br.com.onmyway.valueobject.LatLng;
import br.com.onmyway.valueobject.MapInfo;
import br.com.onmyway.valueobject.TripResponse;

import com.google.common.collect.Lists;

@Path("/map")
public class MapServiceREST {

    private PositionRepository positionRepository = new PositionDao();
    
    private TripRepository tripRepository = new TripDao();
    
    private UserRepository userDao = new UserDao();

    @GET
    @Path("/trip/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPathInfomration(@PathParam("id") String id) {

	Response response = null;
	try {
	    List<Position> positions = positionRepository.findByTripId(Integer.valueOf(id));

	    if(positions == null || positions.isEmpty()){
		response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Nenhuma viagem encontrada").build();
	    }else{
        	List<LatLng> latLng = Lists.newArrayList();
        	List<String> dates = Lists.newArrayList();
        	for (Position position : positions) {
        	    latLng.add(position.getLatLng());
        	    dates.add(position.getDateTime().toString());
        	}
        	    
        	MapInfo mapInfo = new MapInfo(latLng, dates);
        	response = Response.status(Status.OK).entity(mapInfo).build();
	    }
	} catch (Exception e) {
	    response =  Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao recuperar viagem").build();
	    e.printStackTrace();
	}
	
	return response;
    }
    
    @POST
    @Path("/trip")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response startTrip(@FormParam("id") String userId, @FormParam("time") String time, @FormParam("lat") String lat, @FormParam("lng") String lng){
	Response response = null;
	try {

	    User user = userDao.findById(Integer.valueOf(userId));

	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.SECOND, Integer.valueOf(time));

	    Trip trip = new Trip();
	    trip.setEndTime(cal.getTime());
	    trip.setFinished(TripStatus.ACTIVE.getStatusCode());
	    trip.setLatitude(Double.valueOf(lat));
	    trip.setLongitude(Double.valueOf(lng));
	    trip.setUser(user);

	    Trip saveTrip = tripRepository.saveTrip(trip);

	    response = Response.status(Status.OK).entity(saveTrip).build();
	} catch (Exception e) {
	    response = Response.status(Status.INTERNAL_SERVER_ERROR)
		    .entity("Erro ao recuperar viagem").build();
	    e.printStackTrace();
	}
	return response;
    }
    
    @GET
    @Path("/trip/end/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endTrip(@PathParam("id") String tripId){
	Response response = null;
	try {
	    
	    Trip trip = tripRepository.findById(Integer.valueOf(tripId));
	    trip.setFinished(TripStatus.FINISHED.getStatusCode());
	    Trip tripSaved = tripRepository.updateTrip(trip);

	    response = Response.status(Status.OK).entity(tripSaved).build();
	} catch (Exception e) {
	    response = Response.status(Status.INTERNAL_SERVER_ERROR)
		    .entity("Erro ao recuperar viagem").build();
	    e.printStackTrace();
	}
	return response;
    }
    
    @GET
    @Path("/trip/delay/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delayTrip(@PathParam("id") String tripId){
	Response response = null;
	try {
	    
	    Trip trip = tripRepository.findById(Integer.valueOf(tripId));
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(trip.getEndTime());
	    cal.add(Calendar.MINUTE, 30);
	    trip.setEndTime(cal.getTime());
	    Trip tripSaved = tripRepository.updateTrip(trip);

	    response = Response.status(Status.OK).entity(tripSaved).build();
	} catch (Exception e) {
	    response = Response.status(Status.INTERNAL_SERVER_ERROR)
		    .entity("Erro ao recuperar viagem").build();
	    e.printStackTrace();
	}
	return response;
    }
    
    @GET
    @Path("/trip/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrip(@PathParam("id") String userId){
	Response response = null;
	try {
	    Trip trip = tripRepository.findActiveTripByUserId(Integer.valueOf(userId));
	    TripResponse tripResponse = new TripResponse(RestResponseStatus.TRIP_FOUND, trip);
	    response = Response.status(Status.OK).entity(tripResponse).build();
	} catch (Exception e) {
	    response = Response.status(Status.INTERNAL_SERVER_ERROR)
		    .entity("Erro ao recuperar viagem").build();
	    e.printStackTrace();
	}
	return response;
    }
    
    @POST
    @Path("/position")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response savePosition(@FormParam("id") String tripId, @FormParam("lat") String lat, @FormParam("lng") String lng){
	Response response = null;
	try {

	    Trip trip = tripRepository.findById(Integer.valueOf(tripId));

	    Position position = new Position();
	    position.setDateTime(Calendar.getInstance().getTime());
	    position.setLatitude(Double.valueOf(lat));
	    position.setLongitude(Double.valueOf(lng));
	    position.setTrip(trip);

	    Position savePosition = positionRepository.savePosition(position);

	    response = Response.status(Status.OK).entity(savePosition).build();
	} catch (Exception e) {
	    response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("")
		    .build();
	    e.printStackTrace();
	}
	return response;
    }
    
}