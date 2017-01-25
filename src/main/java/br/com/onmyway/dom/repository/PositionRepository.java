package br.com.onmyway.dom.repository;

import java.util.List;

import br.com.onmyway.dom.dao.GenericDAO;
import br.com.onmyway.dom.entity.Position;

public interface PositionRepository extends GenericDAO<Position, Integer>{

    public List<Position> findByTripId(Integer id, boolean finished);
    
    public List<Position> findAllPositions();
    
    public Position savePosition(Position person);
 
    public Position findById(Integer id);
 
    public void deletePosition(Position person);

    List<Position> findThreeDaysOldPositions();

    void deletePositions(List<Position> positions);

}
