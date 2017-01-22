package br.com.onmyway.dom.repository;

import java.util.List;

import br.com.onmyway.dom.dao.GenericDAO;
import br.com.onmyway.dom.entity.Trip;

public interface TripRepository extends GenericDAO<Trip, Integer>{

    List<Trip> findAllTrips();

    List<Trip> findAllActiveTrips();

    List<Trip> findAllTripsNotFinishedsoOnTime();

    Trip saveTrip(Trip trip);

    Trip findById(Integer id);

    Trip updateTrip(Trip trip);
    
    List<Trip> updateTrips(List<Trip> trips);

    Trip findActiveTripByUserId(Integer integer);

    List<Trip> findThreeDaysOldTrips();

    void deleteTrips(List<Trip> trips);

}
