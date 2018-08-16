package br.com.fellipesombra.naocheguei.quartz;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.common.collect.Lists;

import br.com.fellipesombra.naocheguei.dom.dao.ContactDao;
import br.com.fellipesombra.naocheguei.dom.dao.TripDao;
import br.com.fellipesombra.naocheguei.dom.entity.Contact;
import br.com.fellipesombra.naocheguei.dom.entity.Trip;
import br.com.fellipesombra.naocheguei.dom.repository.ContactRepository;
import br.com.fellipesombra.naocheguei.dom.repository.TripRepository;
import br.com.fellipesombra.naocheguei.enums.TripStatus;
import br.com.fellipesombra.naocheguei.service.email.EmailService;
import br.com.fellipesombra.naocheguei.service.email.impl.EmailServiceImpl;
import br.com.fellipesombra.naocheguei.valueobject.Email;

public class TripVerifierJob implements Job {
    
    private static final String NAOCHEGUEI_PAGE = "https://naocheguei.herokuapp.com/map.html?trip=";
    private static final String SUBJECT = "ÑCheguei! - Entre em contato com %s !";
    private static final String MESSAGE = "%s ainda não chegou no seu destino como havia programado. Tente falar com ele para ver se está tudo bem!<br/><br/> Aqui está o mapa mostrando por onde ele passou até o momento: "+NAOCHEGUEI_PAGE+"%s";
    
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
