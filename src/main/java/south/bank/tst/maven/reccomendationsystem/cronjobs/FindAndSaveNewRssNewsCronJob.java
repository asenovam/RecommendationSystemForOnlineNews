/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.cronjobs;

import java.util.Date;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import south.bank.tst.maven.reccomendationsystem.converters.RssFeedNewsToItemConverter;
import south.bank.tst.maven.reccomendationsystem.dao.RssFeedDao;
import  south.bank.tst.maven.reccomendationsystem.entities.RssFeed;
import south.bank.tst.maven.reccomendationsystem.services.RssFeedService;

/**
 *
 * @author mar_9
 */
public class FindAndSaveNewRssNewsCronJob implements Job {
    
    private static RssFeedService rssFeedService;
    
    private static RssFeedDao rssFeedDao;
    
    public void execute(JobExecutionContext context) throws JobExecutionException {

        if (rssFeedService != null && rssFeedDao != null) {
            List<RssFeed> rssFeeds = rssFeedDao.findAll();
            
            for (RssFeed rssFeed : rssFeeds) {
                rssFeedService.loadAndSaveRssNews(rssFeed);
                rssFeedDao.save(rssFeed);
            }
            
            System.out.println("Rss Feed news update just finished");
        }
    }

    public static void setRssFeedService(RssFeedService rssFeedService) {
        FindAndSaveNewRssNewsCronJob.rssFeedService = rssFeedService;
    }

    public static void setRssFeedDao(RssFeedDao rssFeedDao) {
        FindAndSaveNewRssNewsCronJob.rssFeedDao = rssFeedDao;
    }
    
    
    
}
