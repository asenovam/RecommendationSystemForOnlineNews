/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.beans;

import java.util.ArrayList;
import java.util.List;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedItem;

/**
 *
 * @author mar_9
 */
public class UserCategoryNewsBean {
    
    private List<RssFeedUrlToRssFeedItem> news = new ArrayList<>();
    private String errorMessage;
    private int numberOfAllNews;
    private int nextPage;
    private boolean showNextBtn;

    public List<RssFeedUrlToRssFeedItem> getNews() {
        return news;
    }

    public void setNews(List<RssFeedUrlToRssFeedItem> news) {
        this.news = news;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getNumberOfAllNews() {
        return numberOfAllNews;
    }

    public void setNumberOfAllNews(int numberOfAllNews) {
        this.numberOfAllNews = numberOfAllNews;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isShowNextBtn() {
        return showNextBtn;
    }

    public void setShowNextBtn(boolean showNextBtn) {
        this.showNextBtn = showNextBtn;
    }
    
    public static class RssFeedUrlToRssFeedItem {
        private String rssFeedUrl;
        private RssFeedItem rssFeedItem;

        public RssFeedUrlToRssFeedItem(String rssFeedUrl, RssFeedItem rssFeedItem) {
            this.rssFeedUrl = rssFeedUrl;
            this.rssFeedItem = rssFeedItem;
        }

        
        public String getRssFeedUrl() {
            return rssFeedUrl;
        }

        public void setRssFeedUrl(String rssFeedUrl) {
            this.rssFeedUrl = rssFeedUrl;
        }

        public RssFeedItem getRssFeedItem() {
            return rssFeedItem;
        }

        public void setRssFeedItem(RssFeedItem rssfeedItem) {
            this.rssFeedItem = rssfeedItem;
        }
    }
}
