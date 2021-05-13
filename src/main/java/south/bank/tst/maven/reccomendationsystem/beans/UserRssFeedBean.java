/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.beans;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedCategory;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedRate;

/**
 *
 * @author mar_9
 */
public class UserRssFeedBean {

    private String rssFeedUrl;

    private Set<RssFeedCategory> rssFeedCategories;
    private int rssFeedRate;
    private String nickname;
    private String errorMessage = "";

    private Set<RssFeedCategory> allRssFeedCategories
            = new HashSet<>(Arrays.asList(RssFeedCategory.values()));

    public String getRssFeedUrl() {
        return rssFeedUrl;
    }

    public void setRssFeedUrl(String rssFeedUrl) {
        this.rssFeedUrl = rssFeedUrl;
    }

    public Set<RssFeedCategory> getRssFeedCategories() {
        return rssFeedCategories;
    }

    public void setRssFeedCategories(Set<RssFeedCategory> rssFeedCategories) {
        this.rssFeedCategories = rssFeedCategories;
    }

    public int getRssFeedRate() {
        return rssFeedRate;
    }

    public void setRssFeedRate(int rssFeedRate) {
        this.rssFeedRate = rssFeedRate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Set<RssFeedCategory> getAllRssFeedCategories() {
        return allRssFeedCategories;
    }

    public void setAllRssFeedCategories(Set<RssFeedCategory> allRssFeedCategories) {
        this.allRssFeedCategories = allRssFeedCategories;
    }

}
