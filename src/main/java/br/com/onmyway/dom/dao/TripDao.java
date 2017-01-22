package br.com.onmyway.dom.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import br.com.onmyway.dom.entity.Trip;
import br.com.onmyway.dom.repository.TripRepository;
import br.com.onmyway.util.HibernateUtil;

public class TripDao extends GenericDAOImpl<Trip, Integer> implements
	TripRepository {

    @SuppressWarnings("unchecked")
    @Override
    public List<Trip> findAllTrips() {
	List<Trip> trips = new ArrayList<Trip>();
	try {
	    HibernateUtil.beginTransaction();
	    trips = findAll(Trip.class);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	}
	return trips;
    }

    @Override
    public List<Trip> findAllActiveTrips() {
	List<Trip> trips = null;
	String sql = "SELECT t FROM Trip t WHERE t.finished=0";
	HibernateUtil.beginTransaction();
	Query query = HibernateUtil.getSession().createQuery(sql);
	trips = (List<Trip>) findMany(query);
	HibernateUtil.commitTransaction();
	return trips;
    }

    @Override
    public List<Trip> findAllTripsNotFinishedsoOnTime() {
	List<Trip> trips = null;
	Date date = new Date();
	String sql = "SELECT t FROM Trip t WHERE t.finished=0 AND t.endTime < :date";
	HibernateUtil.beginTransaction();
	Query query = HibernateUtil.getSession().createQuery(sql)
		.setParameter("date", date);
	trips = (List<Trip>) findMany(query);
	HibernateUtil.commitTransaction();
	return trips;
    }

    @Override
    public Trip saveTrip(Trip trip) {
	Trip savedObject = null;
	try {
	    HibernateUtil.beginTransaction();
	    savedObject = save(trip);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	}
	return savedObject;
    }

    @Override
    public Trip findById(Integer id) {
	Trip savedObject = null;
	try {
	    HibernateUtil.beginTransaction();
	    savedObject = findByID(Trip.class, id);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	}
	return savedObject;
    }

    @Override
    public Trip updateTrip(Trip trip) {
	Trip savedObject = null;
	try {
	    HibernateUtil.beginTransaction();
	    savedObject = merge(trip);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	}
	return savedObject;
    }
    
    @Override
    public List<Trip> updateTrips(List<Trip> trips) {
	List<Trip> savedObject = new ArrayList<Trip>();
	try {
	    HibernateUtil.beginTransaction();
	    for (Trip trip : trips) {
		savedObject.add(merge(trip));
	    }
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	}
	return savedObject;
    }

    @Override
    public Trip findActiveTripByUserId(Integer userId) {
	Trip savedObject = null;
	String sql = "SELECT t FROM Trip t WHERE t.user.id=:id and t.finished=0";
	HibernateUtil.beginTransaction();
	Query query = HibernateUtil.getSession().createQuery(sql).setParameter("id", userId);
	savedObject = (Trip) findOne(query);
	HibernateUtil.commitTransaction();
	return savedObject;
    }

    @Override
    public List<Trip> findThreeDaysOldTrips() {
	List<Trip> trips = null;
	String sql = "SELECT t FROM Trip t WHERE DATEDIFF(NOW(),t.endTime) > 3 ";
	HibernateUtil.beginTransaction();
	Query query = HibernateUtil.getSession().createQuery(sql);
	trips = (List<Trip>) findMany(query);
	HibernateUtil.commitTransaction();
	return trips;
	
    }
    
    @Override
    public void deleteTrips(List<Trip> trips) {
	try {
	    HibernateUtil.beginTransaction();
	    for (Trip trip : trips) {
		delete(trip);
	    }
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	}
    }
}