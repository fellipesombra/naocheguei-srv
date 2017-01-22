package br.com.onmyway.quartz;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.common.collect.Lists;

import br.com.onmyway.dom.dao.ContactDao;
import br.com.onmyway.dom.dao.TripDao;
import br.com.onmyway.dom.entity.Contact;
import br.com.onmyway.dom.entity.Trip;
import br.com.onmyway.dom.repository.ContactRepository;
import br.com.onmyway.dom.repository.TripRepository;
import br.com.onmyway.enums.TripStatus;
import br.com.onmyway.service.email.EmailService;
import br.com.onmyway.service.email.impl.EmailServiceImpl;
import br.com.onmyway.valueobject.Email;

public class TripVerifierJob implements Job {
    
    private static final String TRACK_ME_PAGE = "http://localhost:8080/onmyway/map.html?trip=";
    private static final String SUBJECT = "TrackME - Go check friend %s !";
    private static final String MESSAGE = "%s didn't arrive yet at his destination, you shoud check if he is ok!<br/><br/> Here it's a map of his last locations: "+TRACK_ME_PAGE+"%s";
    
    private TripRepository tripDao = new TripDao();
    
    private ContactRepository contactDao = new ContactDao();
    
    private EmailService emailService = new EmailServiceImpl();
    
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
	
	System.out.println("executando tripVerifier");
	
	//TODO criar metodo que deleta as viagens que já se passaram 2 dias desde o início
	
	List<Trip> allTrips = tripDao.findAllTripsNotFinishedsoOnTime();
	alertContacts(allTrips);
	
	for (Trip trip : allTrips) {
	    trip.setFinished(TripStatus.FINISHED.getStatusCode());
	}
	tripDao.updateTrips(allTrips);
	
	
	System.out.println("tripVerifier executado");
    }

    private void alertContacts(List<Trip> allTrips) {
	for (Trip trip : allTrips) {
	   List<Contact> contacts = contactDao.findAllByUser(trip.getUser());
	   if(contacts == null || contacts.isEmpty()){
	       continue;
	   }
	   
	   List<String> receivers = Lists.newArrayList();
	   for (Contact contact : contacts) {
	       receivers.add(contact.getEmail());
	   }
	   Email emailInfo = new Email(receivers, String.format(SUBJECT, trip.getUser().getName()), String.format(MESSAGE, trip.getUser().getName(), trip.getId()));
	   emailService.sendEmail(emailInfo);
	}
    }

}
