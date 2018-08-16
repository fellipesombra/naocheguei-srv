package br.com.fellipesombra.naocheguei.dom.entity;

import java.io.Serializable;
import java.util.Date;

import br.com.fellipesombra.naocheguei.valueobject.LatLng;

public class Position implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int id;
    private double latitude;
    private double longitude;
    private Date dateTime;
    private Trip trip;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public double getLatitude() {
	return latitude;
    }

    public void setLatitude(double latitude) {
	this.latitude = latitude;
    }

    public double getLongitude() {
	return longitude;
    }

    public void setLongitude(double longitude) {
	this.longitude = longitude;
    }

    public Date getDateTime() {
	return dateTime;
    }

    public void setDateTime(Date dateTime) {
	this.dateTime = dateTime;
    }

    public Trip getTrip() {
	return trip;
    }

    public void setTrip(Trip trip) {
	this.trip = trip;
    }

    public LatLng getLatLng() {
	return new LatLng(latitude, longitude);
    }
}
