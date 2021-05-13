/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author mar_9
 */
public class RssFeed {
    
    public static Long idGenerator = Long.valueOf(0);
    
    @Indexed(unique = true)
    @Id
    private String url;
    private Set<String> tags = new HashSet<>();
    private Set<RssFeedCategory> categories = new HashSet<>();
    private List<RssFeedItem> items = new ArrayList<>();
    private long uid;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<RssFeedCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<RssFeedCategory> categories) {
        this.categories = categories;
    }

    public List<RssFeedItem> getItems() {
        return items;
    }

    public void setItems(List<RssFeedItem> items) {
        this.items = items;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
       
}
