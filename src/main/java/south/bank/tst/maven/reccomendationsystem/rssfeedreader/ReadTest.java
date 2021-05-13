/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.rssfeedreader;

/**
 *
 * @author mar_9
 */
public class ReadTest {
    
     public static void main(String[] args) {
        RSSFeedParser parser = new RSSFeedParser(
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643123");
        Feed feed = parser.readFeed();
        System.out.println(feed);
        for (FeedMessage message : feed.getMessages()) {
            System.out.println(message);

        }
        
        
         RSSFeedParser parser2 = new RSSFeedParser(
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643123");
        Feed feed2 = parser2.readFeed();
        System.out.println(feed2);
        for (FeedMessage message : feed2.getMessages()) {
            System.out.println(message);

        }
    }
}
