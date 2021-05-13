/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.services.lenskitalgorithm;

/**
 *
 * @author mar_9
 */

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import south.bank.tst.maven.reccomendationsystem.dao.RssFeedDao;
import south.bank.tst.maven.reccomendationsystem.dao.UserDao;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeed;
import south.bank.tst.maven.reccomendationsystem.entities.User;
import south.bank.tst.maven.reccomendationsystem.entities.UserRssFeed;

@Component
public class MetadataBuilderService {
    
    @Autowired
    private RssFeedDao rssFeedDao;
    
    @Autowired
    private UserDao userDao; 
    
    public void buildMetadata() {
        writeRatings("data/ratings.csv");
        writeRssTags("data/rss-tags.csv");
        writeRssUrls("data/rss-urls.csv");
        writeUsers("data/users.csv");
    }
    
    public void writeRssTags(String filePath) 
    { 
        // first create file object for file placed at location 
        // specified by filepath 
        File file = new File(filePath); 
        try { 
            // create FileWriter object with file as parameter 
            FileWriter outputfile = new FileWriter(file); 
  
            // create CSVWriter object filewriter object as parameter 
            CSVWriter writer = new CSVWriter(outputfile, ',', 
                                         CSVWriter.NO_QUOTE_CHARACTER, 
                                         CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
                                         CSVWriter.DEFAULT_LINE_END); 
            
            List<RssFeed> rssFeeds = rssFeedDao.findAll();
            
            for (RssFeed rssFeed : rssFeeds) {
                final long id = rssFeed.getUid();
                Set<String> tags = rssFeed.getTags();
                for (String tag : tags) {
                    String[] data = { String.valueOf(id), tag };
                    System.out.println(String.valueOf(id) + " " + tag);
                    writer.writeNext(data);
                }
            }
  
            // closing writer connection 
            writer.close(); 
        } 
        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
    } 
    
    public void writeUsers(String filePath) 
    { 
        // first create file object for file placed at location 
        // specified by filepath 
        File file = new File(filePath); 
        try { 
            // create FileWriter object with file as parameter 
            FileWriter outputfile = new FileWriter(file); 
  
            // create CSVWriter object filewriter object as parameter 
            CSVWriter writer = new CSVWriter(outputfile, ',', 
                                         CSVWriter.NO_QUOTE_CHARACTER, 
                                         CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
                                         CSVWriter.DEFAULT_LINE_END); 
            
            List<User> users = userDao.findAll();
            
            for (User user : users) {
                final long id = user.getUid();
                final String name = user.getNickname();
                System.out.println(id + " " + name);
                String[] data = { String.valueOf(id), name };
                writer.writeNext(data);
            }
  
            // closing writer connection 
            writer.close(); 
        } 
        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
    } 
    
    public void writeRssUrls(String filePath) 
    { 
        // first create file object for file placed at location 
        // specified by filepath 
        File file = new File(filePath); 
        try { 
            // create FileWriter object with file as parameter 
            FileWriter outputfile = new FileWriter(file); 
  
            // create CSVWriter object filewriter object as parameter 
            CSVWriter writer = new CSVWriter(outputfile, ',', 
                                         CSVWriter.NO_QUOTE_CHARACTER, 
                                         CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
                                         CSVWriter.DEFAULT_LINE_END); 
            
            List<RssFeed> rssFeeds = rssFeedDao.findAll();
            
            for (RssFeed rssFeed : rssFeeds) {
                final long id = rssFeed.getUid();
                final String url = rssFeed.getUrl();
                String[] data = { String.valueOf(id), url };
                System.out.println(url + " " + url);
                writer.writeNext(data);
            }
  
            // closing writer connection 
            writer.close(); 
        } 
        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
    } 
    
    public void writeRatings(String filePath) 
    { 
         // first create file object for file placed at location 
        // specified by filepath 
        File file = new File(filePath); 
        try { 
            // create FileWriter object with file as parameter 
            FileWriter outputfile = new FileWriter(file); 
  
            // create CSVWriter object filewriter object as parameter 
            CSVWriter writer = new CSVWriter(outputfile, ',', 
                                         CSVWriter.NO_QUOTE_CHARACTER, 
                                         CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
                                         CSVWriter.DEFAULT_LINE_END); 
            
            List<User> users = userDao.findAll();
            
            for (User user : users) {
                final long id = user.getUid();
                final List<UserRssFeed> rssFeedsRatings = user.getRssFeeds();
                for (UserRssFeed userRssFeed : rssFeedsRatings) {
                    long  rssFeedId = userRssFeed.getRssFeedId();
                    double rssFeedRating = userRssFeed.getFeedRate().getRating();
                    System.out.println(id + " " + rssFeedId + " " + String.valueOf(rssFeedRating));
                    String[] data = { String.valueOf(id), String.valueOf(rssFeedId), String.valueOf(rssFeedRating)};
                    writer.writeNext(data);            
                }
            }
  
            // closing writer connection 
            writer.close(); 
        } 
        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
    } 
}
