package br.com.fellipesombra.naocheguei.dom.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import br.com.fellipesombra.naocheguei.dom.entity.Position;
import br.com.fellipesombra.naocheguei.dom.repository.PositionRepository;
import br.com.fellipesombra.naocheguei.util.HibernateUtil;

public class PositionDao extends GenericDAOImpl<Position, Integer> implements PositionRepository {

    @Override
    public List<Position> findByTripId(Integer id, boolean finished) {
	List<Position> person = null;
	String sql = "SELECT p FROM Position p WHERE p.trip.id= :id";
	if(finished){
	    sql+=(" and p.trip.finished=1");
	}
	HibernateUtil.beginTransaction();
	Query query = HibernateUtil.getSession().createQuery(sql)
		.setParameter("id", id);
	person = (List<Position>) findMany(query);
	HibernateUtil.commitTransaction();
	return person;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Position> findAllPositions() {
	List<Position> positions = new ArrayList<Position>();
	try {
	    HibernateUtil.beginTransaction();
	    positions = findAll(Position.class);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	}
	return positions;
    }

    @Override
    public Position findById(Integer id) {
	Position position = null;
	try {
	    HibernateUtil.beginTransaction();
	    position = (Position) findByID(Position.class, id);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	}
	return position;
    }

    @Override
    public Position savePosition(Position position) {
	Position savedObject = null;
	try {
	    HibernateUtil.beginTransaction();
	    savedObject = save(position);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	    HibernateUtil.rollbackTransaction();
	}
	return savedObject;
    }

    @Override
    public void deletePosition(Position position) {
	try {
	    HibernateUtil.beginTransaction();
	    delete(position);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	    HibernateUtil.rollbackTransaction();
	}
    }

    @Override
    public List<Position> findThreeDaysOldPositions() {
	List<Position> positions = null;
	String sql = "SELECT p FROM Position p WHERE DATEDIFF(NOW(),p.dateTime) > 3 ";
	HibernateUtil.beginTransaction();
	Query query = HibernateUtil.getSession().createQuery(sql);
	positions = (List<Position>) findMany(query);
	HibernateUtil.commitTransaction();
	return positions;

    }

    @Override
    public void deletePositions(List<Position> positions) {
	try {
	    HibernateUtil.beginTransaction();
	    for (Position position : positions) {
		delete(position);
	    }
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    ex.printStackTrace();
	}
    }

}