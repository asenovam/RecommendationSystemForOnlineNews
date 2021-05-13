/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.stereotype.Component;
import south.bank.tst.maven.reccomendationsystem.converters.RssFeedNewsToItemConverter;
import south.bank.tst.maven.reccomendationsystem.cronjobs.FindAndSaveNewRssNewsCronJob;
import south.bank.tst.maven.reccomendationsystem.dao.RssFeedDao;
import south.bank.tst.maven.reccomendationsystem.services.RssFeedService;

/**
 *
 * @author mar_9
 */
@Component
public class StaticContextInitializer {
    
    @Autowired
    private RssFeedDao rssFeedDao;

    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private RssFeedNewsToItemConverter rssFeedNewsToItemConverter;
    
    @Autowired
    private RssFeedService rssFeedService;
    
   
    @Autowired
    private MappingMongoConverter mappingMongoConverter;

    @PostConstruct
    public void init() {
        FindAndSaveNewRssNewsCronJob.setRssFeedDao(rssFeedDao);
        FindAndSaveNewRssNewsCronJob.setRssFeedService(rssFeedService);
        mappingMongoConverter.setMapKeyDotReplacement("_");
    }
}
