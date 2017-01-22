package br.com.onmyway.valueobject;

import java.util.Date;

public class PositionDTO {

    private double Latitude;
    private double Longitude;
    private Date dateTime;

    public double getLatitude() {
	return Latitude;
    }

    public void setLatitude(double latitude) {
	Latitude = latitude;
    }

    public double getLongitude() {
	return Longitude;
    }

    public void setLongitude(double longitude) {
	Longitude = longitude;
    }

    public Date getDateTime() {
	return dateTime;
    }

    public void setDateTime(Date dateTime) {
	this.dateTime = dateTime;
    }

}
