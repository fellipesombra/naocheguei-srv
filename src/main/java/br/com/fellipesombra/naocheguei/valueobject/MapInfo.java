package br.com.fellipesombra.naocheguei.valueobject;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Informações do mapa para usar no google maps api
 */
public class MapInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private LatLng center;
    private List<LatLng> coordinates;
    private List<String> dateTime;

    public MapInfo() {
	dateTime = Lists.newArrayList();
	coordinates = Lists.newArrayList();
    }

    public MapInfo(List<LatLng> coordinates, List<String> dateTime) {
	super();
	this.coordinates = coordinates;
	this.dateTime = dateTime;
	this.center = calculateCenter();
    }

    private LatLng calculateCenter() {
	// TODO pegar a lista de coordenadas e descobrir qual é a "central"
	// delas
	return coordinates.get(0);
    }

    public LatLng getCenter() {
	return center;
    }

    public void setCenter(LatLng center) {
	this.center = center;
    }

    public List<LatLng> getCoordinates() {
	return coordinates;
    }

    public List<String> getDateTime() {
	return dateTime;
    }

    public void setDateTime(List<String> dateTime) {
	this.dateTime = dateTime;
    }

}
