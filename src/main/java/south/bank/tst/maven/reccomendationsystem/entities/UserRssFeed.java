/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.entities;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mar_9
 */
public class UserRssFeed {
    
    private String rssFeedUrl;
    private long rssFeedId;
    private RssFeedRate feedRate;
    private Map<String, Boolean> rssItemGuidToIsRead = new HashMap<>();

    public String getRssFeedUrl() {
        return rssFeedUrl;
    }

    public void setRssFeedUrl(String rssFeedUrl) {
        this.rssFeedUrl = rssFeedUrl;
    }
    
    public RssFeedRate getFeedRate() {
        return feedRate;
    }

    public void setFeedRate(RssFeedRate feedRate) {
        this.feedRate = feedRate;
    }

    public long getRssFeedId() {
        return rssFeedId;
    }

    public void setRssFeedId(long rssFeedId) {
        this.rssFeedId = rssFeedId;
    }

    public Map<String, Boolean> getRssItemGuidToIsRead() {
        return rssItemGuidToIsRead;
    }

    public void setRssItemGuidToIsRead(Map<String, Boolean> rssItemGuidToIsRead) {
        this.rssItemGuidToIsRead = rssItemGuidToIsRead;
    }
}
