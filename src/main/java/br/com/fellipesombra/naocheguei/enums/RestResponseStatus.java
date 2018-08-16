package br.com.fellipesombra.naocheguei.enums;

public enum RestResponseStatus {

    TRIP_FOUND(0),
    CONTACT_DELETED(1), 
    CONTACT_ALREADY_EXISTS(2),
    CONTACT_ADD(3);
    
    private int statusCode;

    private RestResponseStatus(int statusCode) {
	this.statusCode = statusCode;
    }

    public int getStatusCode() {
	return statusCode;
    }    
    
}
