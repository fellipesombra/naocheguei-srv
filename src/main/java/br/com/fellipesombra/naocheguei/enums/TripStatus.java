package br.com.fellipesombra.naocheguei.enums;

public enum TripStatus {

    FINISHED(1),
    ACTIVE(0);
    
    private int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    private TripStatus(int status) {
	this.statusCode = status;
    }
}
