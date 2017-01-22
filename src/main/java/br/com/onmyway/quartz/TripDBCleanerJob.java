package br.com.onmyway.quartz;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.onmyway.dom.dao.PositionDao;
import br.com.onmyway.dom.dao.TripDao;
import br.com.onmyway.dom.entity.Position;
import br.com.onmyway.dom.entity.Trip;
import br.com.onmyway.dom.repository.PositionRepository;
import br.com.onmyway.dom.repository.TripRepository;

public class TripDBCleanerJob implements Job {
    
    private TripRepository tripDao = new TripDao();
    private PositionRepository positionDao = new PositionDao();
    
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
	
	System.out.println("executando tripCleaner");
	
	List<Trip> trips = tripDao.findThreeDaysOldTrips();
	tripDao.deleteTrips(trips);
	
	List<Position> positions = positionDao.findThreeDaysOldPositions();
	positionDao.deletePositions(positions);
	
	System.out.println("tripVerifier executado");
    }

}
