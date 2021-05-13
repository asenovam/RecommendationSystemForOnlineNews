/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedCategory;

/**
 *
 * @author mar_9
 */
public class UserDashboardBean {
    
    private String userName;
    private List<UserRssFeedCategoryBean> userRssFeedsCategories = new ArrayList<>();
    private List<String> recRssFeedUrls = new ArrayList<>();
   
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<UserRssFeedCategoryBean> getUserRssFeedsCategories() {
        return userRssFeedsCategories;
    }

    public void setUserRssFeedsCategories(List<UserRssFeedCategoryBean> userRssFeedsCategories) {
        this.userRssFeedsCategories = userRssFeedsCategories;
    }

    public List<String> getRecRssFeedUrls() {
        return recRssFeedUrls;
    }

    public void setRecRssFeedUrls(List<String> recRssFeedUrls) {
        this.recRssFeedUrls = recRssFeedUrls;
    }
    
    
}
