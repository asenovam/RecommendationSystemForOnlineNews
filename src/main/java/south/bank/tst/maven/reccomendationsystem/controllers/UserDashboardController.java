/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import south.bank.tst.maven.reccomendationsystem.beans.EditRssFeedsBean;
import south.bank.tst.maven.reccomendationsystem.beans.LoginBean;
import south.bank.tst.maven.reccomendationsystem.beans.RegisterBean;
import south.bank.tst.maven.reccomendationsystem.beans.UserDashboardBean;
import south.bank.tst.maven.reccomendationsystem.beans.UserRssFeedBean;
import south.bank.tst.maven.reccomendationsystem.beans.UserRssFeedCategoryBean;
import south.bank.tst.maven.reccomendationsystem.dao.RssFeedDao;
import south.bank.tst.maven.reccomendationsystem.dao.UserDao;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeed;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedCategory;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedItem;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedRate;
import south.bank.tst.maven.reccomendationsystem.entities.User;
import south.bank.tst.maven.reccomendationsystem.entities.UserRssFeed;
import south.bank.tst.maven.reccomendationsystem.services.RssFeedService;
import south.bank.tst.maven.reccomendationsystem.services.lenskitalgorithm.CBFMain;
import south.bank.tst.maven.reccomendationsystem.services.lenskitalgorithm.MetadataBuilderService;

/**
 *
 * @author mar_9
 */
@Controller
public class UserDashboardController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RssFeedDao rssFeedDao;

    @Autowired
    private RssFeedService rssFeedService;

    @Autowired
    private MetadataBuilderService metadataBuilderService;

    @GetMapping("/userdashboard")
    public String loadUserDashoboard(Model model, String nickname) {
        populateUserDashboard(model, nickname, null, null);

        return "userdashboard";
    }

    @PostMapping("/addRssFeed")
    public String addRssFeedSubmit(@ModelAttribute UserRssFeedBean registerUserRssFeed, Model model) {

        System.out.println(" RSS feed form state: ");
        System.out.println(" registerUserRssFeed: " + registerUserRssFeed.getNickname()
                + " " + registerUserRssFeed.getRssFeedCategories() + " " + registerUserRssFeed.getRssFeedUrl()
                + " " + registerUserRssFeed.getRssFeedRate());

        User user = userDao.findByNickname(registerUserRssFeed.getNickname());

        List<UserRssFeed> userFeeds = user.getRssFeeds();
        for (UserRssFeed userRssFeed : userFeeds) {
            if (userRssFeed.getRssFeedUrl().equals(registerUserRssFeed.getRssFeedUrl())) {
                populateUserDashboard(model, user.getNickname(),
                        "You have already entered the RSS feed: " + registerUserRssFeed.getRssFeedUrl(),
                        null);

                return "userdashboard";
            }
        }

        List<RssFeed> feeds = rssFeedDao.findByUrl(registerUserRssFeed.getRssFeedUrl());
        RssFeed rssFeed = feeds != null && !feeds.isEmpty() ? feeds.get(0) : null;

        if (rssFeed == null) {
            rssFeed = new RssFeed();
            rssFeed.setUrl(registerUserRssFeed.getRssFeedUrl());
            Set<RssFeedCategory> rssFeedCategorys = registerUserRssFeed.getRssFeedCategories();
            rssFeed.setCategories(rssFeedCategorys);
            Set<String> tags = new HashSet<>();
            for (RssFeedCategory rssFeedCategory : rssFeedCategorys) {
                tags.add(rssFeedCategory.toString());
            }
            rssFeed.setTags(tags);
            long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
            rssFeed.setUid(id);
        } else {
            Set<RssFeedCategory> rssFeedCategorys = rssFeed.getCategories();
            Set<RssFeedCategory> registeredRssFeedCategories = registerUserRssFeed.getRssFeedCategories();
            registeredRssFeedCategories.removeAll(rssFeedCategorys);

            if (!registeredRssFeedCategories.isEmpty()) {
                rssFeedCategorys.addAll(registeredRssFeedCategories);
                Set<String> tags = rssFeed.getTags();
                for (RssFeedCategory rssFeedCategory : rssFeedCategorys) {
                    tags.add(rssFeedCategory.toString());
                }
                rssFeed.setTags(tags);
                rssFeed.setCategories(rssFeedCategorys);
            }
        }

        rssFeedService.loadAndSaveRssNews(rssFeed);
        rssFeedDao.save(rssFeed);

        List<UserRssFeed> userRssFeeds = user.getRssFeeds();
        UserRssFeed newUserRssFeed = new UserRssFeed();
        newUserRssFeed.setRssFeedUrl(rssFeed.getUrl());

        newUserRssFeed.setFeedRate(
                RssFeedRate.getRateByValue(registerUserRssFeed.getRssFeedRate()));
        newUserRssFeed.setRssFeedId(rssFeed.getUid());
        Map<String, Boolean> itemToIsRead = newUserRssFeed.getRssItemGuidToIsRead();
        for (RssFeedItem item : rssFeed.getItems()) {
            itemToIsRead.put(item.getGuid(), Boolean.FALSE);
        }
        userRssFeeds.add(newUserRssFeed);
        user.setRssFeeds(userRssFeeds);

        userDao.save(user);

        populateUserDashboard(model, user.getNickname(), null, null);

        return "userdashboard";
    }

    @PostMapping("/reccommendations")
    public String loadUserReccomendations(Model model, String nickname) {
        System.out.println("load user reccomendations " + nickname);
        this.metadataBuilderService.buildMetadata();

        User user = userDao.findByNickname(nickname);
        final Set<String> rssFeedUrls = new HashSet<String>();
        try {
            List<Long> recommendedFessFeeds
                    = CBFMain.generateRecommendationsForUser(user.getUid());
            for (final long rssFedUid : recommendedFessFeeds) {
                List<RssFeed> feeds = rssFeedDao.findByUid(rssFedUid);
                if (feeds != null && feeds.size() > 0) {
                    System.out.println("recommended rss feed: " + feeds.get(0).getUrl());
                    rssFeedUrls.add(feeds.get(0).getUrl().trim());
                }
            }
        } catch (Exception e) {

        }

        populateUserDashboard(model, nickname, null, rssFeedUrls);

        return "userdashboard";
    }

    @GetMapping("/removeRssFeed")
    public String removeRssFeedSubmit(String rssFeedUrl, String userNickname,
            Model model) {
        System.out.println(" RSS feed to remove: " + rssFeedUrl);

        User user = userDao.findByNickname(userNickname);

        List<UserRssFeed> userFeeds = user.getRssFeeds();
        UserRssFeed rssFeedToBeRemoved = null;
        for (UserRssFeed userRssFeed : userFeeds) {
            if (userRssFeed.getRssFeedUrl().equals(rssFeedUrl)) {
                rssFeedToBeRemoved = userRssFeed;
                break;
            }
        }
        userFeeds.remove(rssFeedToBeRemoved);
        userDao.save(user);

        loadRssFeeds(userNickname, model, "The rss feed " + rssFeedUrl
                + " has been sucesfully removed");

        return "editRssFeeds";
    }

    @GetMapping("/editRssFeeds")
    public String loadRssFeeds(String userNickname,
            Model model) {

        loadRssFeeds(userNickname, model, "");
        return "editRssFeeds";
    }

    public void loadRssFeeds(String userNickname,
            Model model, String message) {
        model.addAttribute("message", message);

        User user = userDao.findByNickname(userNickname);

        List<UserRssFeed> userFeeds = user.getRssFeeds();
        model.addAttribute("userFeeds", userFeeds);
    }

    private void populateUserDashboard(
            Model model, String nickName, String errorMessage,
            Set<String> recRssFeedUrls) {

        List<RssFeed> allFeeds = rssFeedDao.findAll();
        System.out.println("All rss feeds: ");
        for (RssFeed f : allFeeds) {
            System.out.println(f.getUrl());
        }

        User user = userDao.findByNickname(nickName);

        UserDashboardBean userDashboardBean = new UserDashboardBean();
        userDashboardBean.setUserName(user.getFirstName() + " " + user.getLastName());
        List<UserRssFeed> userFeeds = user.getRssFeeds();
        List<UserRssFeedCategoryBean> userRssFeedsCategories = new ArrayList<>();
        userDashboardBean.setUserRssFeedsCategories(userRssFeedsCategories);
        Map<RssFeedCategory, Long> newNewsItemsCounter = new HashMap<>();

        for (UserRssFeed userRssFeed : userFeeds) {
            String rssfeedUrl = userRssFeed.getRssFeedUrl();
            Map<String, Boolean> rssFeedItemToIsRead = userRssFeed.getRssItemGuidToIsRead();
            final long numberOfNotReadNews
                    = rssFeedItemToIsRead.values().stream().filter(isRead -> Boolean.FALSE.equals(isRead)).count();
            List<RssFeed> feeds = rssFeedDao.findByUrl(rssfeedUrl);
            RssFeed rssFeed = feeds.get(0);
            Set<RssFeedCategory> rssFeedCategories = rssFeed.getCategories();

            for (RssFeedCategory rssFeedCategory : rssFeedCategories) {
                if (!newNewsItemsCounter.containsKey(rssFeedCategory)) {
                    newNewsItemsCounter.put(rssFeedCategory, numberOfNotReadNews);
                } else {
                    Long currentCount = newNewsItemsCounter.get(rssFeedCategory);
                    newNewsItemsCounter.put(rssFeedCategory, currentCount + numberOfNotReadNews);
                }

                boolean isCategoryRegistered = false;
                for (UserRssFeedCategoryBean userRssFeedCategory : userRssFeedsCategories) {
                    if (rssFeedCategory.equals(userRssFeedCategory.getRssFeedCategory())) {
                        isCategoryRegistered = true;
                        userRssFeedCategory.setNumberOfNewNews(newNewsItemsCounter.get(rssFeedCategory));
                        break;
                    }
                }

                if (!isCategoryRegistered) {
                    UserRssFeedCategoryBean userRssFeedCategoryBean = new UserRssFeedCategoryBean();
                    userRssFeedCategoryBean.setRssFeedCategory(rssFeedCategory);
                    userRssFeedCategoryBean.setNumberOfNewNews(newNewsItemsCounter.get(rssFeedCategory));
                    userRssFeedsCategories.add(userRssFeedCategoryBean);
                }
            }
        }

        if (recRssFeedUrls != null) {
            System.out.println("recRssFeedUrls " + recRssFeedUrls);
            List<UserRssFeed> registeredRssFeeds = user.getRssFeeds();
            for (UserRssFeed f : registeredRssFeeds) {
                System.out.println("feed: " + f.getRssFeedUrl());
                if (recRssFeedUrls.contains(f.getRssFeedUrl().trim())) {
                    System.out.println("I am going to emove: " + f.getRssFeedUrl());
                    recRssFeedUrls.remove(f.getRssFeedUrl().trim());
                }
            }

            userDashboardBean.setRecRssFeedUrls(new ArrayList<>(recRssFeedUrls));
        }
        model.addAttribute("userDashboard", userDashboardBean);

        UserRssFeedBean userRegisterRssFeedBean = new UserRssFeedBean();
        userRegisterRssFeedBean.setNickname(nickName);
        if (errorMessage != null) {
            userRegisterRssFeedBean.setErrorMessage(errorMessage);
        }
        model.addAttribute("userRegisterRssFeedBean", userRegisterRssFeedBean);
    }

    @ModelAttribute("availableRssFeedCategories")
    public RssFeedCategory[] getAvailableRssFeedCategories() {
        return RssFeedCategory.values();
    }

    @ModelAttribute("availableRssFeedRates")
    public RssFeedRate[] getAvailableRssFeedRates() {
        return RssFeedRate.values();
    }

}
