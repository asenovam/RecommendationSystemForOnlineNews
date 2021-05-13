/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import south.bank.tst.maven.reccomendationsystem.cronjobs.FindAndSaveNewRssNewsCronJob;
import south.bank.tst.maven.reccomendationsystem.dao.RssFeedDao;

/**
 *
 * @author mar_9
 */
@SpringBootApplication
public class ApplicationStartUp {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStartUp.class, args);

        try {
            JobDetail job = JobBuilder.newJob(FindAndSaveNewRssNewsCronJob.class)
                    .withIdentity("findAndSaveNewRssNewsCronJob", "group1").build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger", "group")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/10 * * * ?"))
                    .build();

            Scheduler scheduler = new StdSchedulerFactory().getScheduler();

            scheduler.start();

            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
