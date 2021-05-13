/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.services;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import south.bank.tst.maven.reccomendationsystem.converters.RssFeedNewsToItemConverter;
import south.bank.tst.maven.reccomendationsystem.dao.RssFeedDao;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeed;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedItem;
import south.bank.tst.maven.reccomendationsystem.rssfeedreader.Feed;
import south.bank.tst.maven.reccomendationsystem.rssfeedreader.FeedMessage;
import south.bank.tst.maven.reccomendationsystem.rssfeedreader.RSSFeedParser;

/**
 *
 * @author mar_9
 */
@Component
public class RssFeedService {
    
    @Autowired
    private RssFeedDao rssFeedDao;
    
    @Autowired
    private RssFeedNewsToItemConverter rssFeedNewsToItemConverter;
    
    public boolean loadAndSaveRssNews(final RssFeed rssFeed) {
        boolean areNewsLoaded = false;
        
        Set<String> existingUidNewsItems = new HashSet<>();
        for (RssFeedItem item : rssFeed.getItems()) {
            existingUidNewsItems.add(item.getGuid());
        };
        RSSFeedParser parser = new RSSFeedParser(rssFeed.getUrl());
        Feed feed = parser.readFeed();
        for (FeedMessage message : feed.getMessages()) {
            RssFeedItem feedItem = new RssFeedItem();
            
            if (!existingUidNewsItems.contains(message.getGuid())) {
                rssFeedNewsToItemConverter.convert(message, feedItem);
                rssFeed.getItems().add(feedItem);
            }
        }
        
        return areNewsLoaded;
    }
}
