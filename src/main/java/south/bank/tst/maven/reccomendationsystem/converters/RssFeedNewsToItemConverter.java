/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedItem;
import south.bank.tst.maven.reccomendationsystem.rssfeedreader.FeedMessage;

/**
 *
 * @author mar_9
 */
@Component
public class RssFeedNewsToItemConverter {
    
    final static DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
    
    public void convert(FeedMessage newsMessage, RssFeedItem rssItem) {
        rssItem.setTitle(newsMessage.getTitle());
        rssItem.setDescription(newsMessage.getDescription());
        rssItem.setLink(newsMessage.getLink());
        try {
            Date date = (Date)formatter.parse(newsMessage.getPubDate());
            rssItem.setPubDate(date);
        } catch (Exception e) {
            System.out.println("Parse exception " + e.getMessage());
        }
        rssItem.setGuid(newsMessage.getGuid());
    }
}
