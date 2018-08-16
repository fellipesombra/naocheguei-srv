package br.com.fellipesombra.naocheguei.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzListener implements ServletContextListener {
    Scheduler scheduler = null;
    


    @Override
    public void contextInitialized(ServletContextEvent servletContext) {
	System.out.println("Quartz Context Initialized");

	try {
	    // Setup the Job class and the Job group
	    JobDetail job = newJob(TripVerifierJob.class).withIdentity(
		    "TripJob", "Group").build();
	    
	    JobDetail jobCleaner = newJob(TripDBCleanerJob.class).withIdentity(
		    "TripJobCleaner", "Group").build();
	    // Create a Trigger that fires every 2 minutes. 
	    Trigger trigger = newTrigger()
		    .withIdentity("TriggerTrip", "Group")
		    .withSchedule(
			    CronScheduleBuilder
				    .cronSchedule("0 0/2 * 1/1 * ? *")).build();
	 // Create a Trigger that fires every 6 hours. 
	    Trigger triggerCleaner = newTrigger()
		    .withIdentity("TriggerTripCleaner", "Group")
		    .withSchedule(
			    CronScheduleBuilder
				    .cronSchedule("0 0 0/6 1/1 * ? *")).build();
	    // Setup the Job and Trigger with Scheduler & schedule jobs. 
	    scheduler = new StdSchedulerFactory().getScheduler();
	    scheduler.start();
	    scheduler.scheduleJob(job, trigger);
	    scheduler.scheduleJob(jobCleaner, triggerCleaner);
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContext) {
	System.out.println("Quartz Context Destroyed");
	try {
	    scheduler.shutdown();
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}
    }
}