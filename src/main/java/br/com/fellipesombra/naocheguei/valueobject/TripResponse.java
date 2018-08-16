package br.com.fellipesombra.naocheguei.valueobject;

import br.com.fellipesombra.naocheguei.dom.entity.Trip;
import br.com.fellipesombra.naocheguei.enums.RestResponseStatus;

public class TripResponse {

    private int status;
    private Trip trip;

    public TripResponse(RestResponseStatus status, Trip trip) {
	super();
	this.status = status.getStatusCode();
	this.trip = trip;
    }

    public int getStatus() {
	return status;
    }

    public void setStatus(int status) {
	this.status = status;
    }

    public Trip getTrip() {
	return trip;
    }

    public void setTrip(Trip trip) {
	this.trip = trip;
    }

}
