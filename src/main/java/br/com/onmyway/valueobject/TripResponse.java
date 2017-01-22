package br.com.onmyway.valueobject;

import br.com.onmyway.dom.entity.Trip;
import br.com.onmyway.enums.RestResponseStatus;

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
