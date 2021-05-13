/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.beans;

import south.bank.tst.maven.reccomendationsystem.entities.RssFeedCategory;

/**
 *
 * @author mar_9
 */
public class UserRssFeedCategoryBean {
    
    private RssFeedCategory rssFeedCategory;
    private long numberOfNewNews;

    public RssFeedCategory getRssFeedCategory() {
        return rssFeedCategory;
    }

    public void setRssFeedCategory(RssFeedCategory rssFeedCategory) {
        this.rssFeedCategory = rssFeedCategory;
    }

    public long getNumberOfNewNews() {
        return numberOfNewNews;
    }

    public void setNumberOfNewNews(long numberOfNewNews) {
        this.numberOfNewNews = numberOfNewNews;
    }
    
    
}
