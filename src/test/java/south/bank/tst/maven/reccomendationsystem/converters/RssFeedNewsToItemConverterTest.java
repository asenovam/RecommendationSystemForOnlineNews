/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.converters;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedItem;
import south.bank.tst.maven.reccomendationsystem.rssfeedreader.FeedMessage;

/**
 *
 * @author mar_9
 */
public class RssFeedNewsToItemConverterTest {
 
    private RssFeedNewsToItemConverter converter = new RssFeedNewsToItemConverter();
    
    @Test
    public void isRssFeedConvertedSucesfully() {
        FeedMessage newsMessage = new FeedMessage();
        RssFeedItem rssItem = new RssFeedItem();
        
        newsMessage.setAuthor("Test Author");
        newsMessage.setDescription("Description about the RSS feed");
        newsMessage.setGuid("uiwnhds787sdf");
        newsMessage.setPubDate("Sat, 27 Apr 2019 10:09:00 +0100");
        newsMessage.setTitle("rss feed title");
        
        converter.convert(newsMessage, rssItem);
        
        assertEquals("uiwnhds787sdf", rssItem.getGuid());
        assertEquals("rss feed title", rssItem.getTitle());
    }
}
