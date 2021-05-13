/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import south.bank.tst.maven.reccomendationsystem.beans.UserCategoryNewsBean;
import south.bank.tst.maven.reccomendationsystem.beans.UserCategoryNewsBean.RssFeedUrlToRssFeedItem;
import south.bank.tst.maven.reccomendationsystem.dao.RssFeedDao;
import south.bank.tst.maven.reccomendationsystem.dao.UserDao;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeed;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedCategory;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeedItem;
import south.bank.tst.maven.reccomendationsystem.entities.User;
import south.bank.tst.maven.reccomendationsystem.entities.UserRssFeed;
import south.bank.tst.maven.reccomendationsystem.services.RssFeedService;

/**
 *
 * @author mar_9
 */
@Controller
public class CategoryNewsController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RssFeedDao rssFeedDao;

    @Autowired
    private RssFeedService rssFeedService;

    @GetMapping("/category/readNews")
    public RedirectView loadCategoryItems(
            Model model, String nickname, String newsUrl, String newsGuid,
            String rssFeedUrl) {
        User user = userDao.findByNickname(nickname);

        if (user != null) {
            List<UserRssFeed> rssFeeds = user.getRssFeeds();
            UserRssFeed userRssFeed
                    = rssFeeds.stream().filter(f -> f.getRssFeedUrl().equals(rssFeedUrl)).findAny().get();
            userRssFeed.getRssItemGuidToIsRead().put(newsGuid.replace(".", "_"), Boolean.TRUE);
            userDao.save(user);
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(newsUrl);
        return redirectView;
    }

    @GetMapping("/category/news")
    public String loadCategoryItems(
            Model model, String nickname, String category,
            Integer page, Integer size) {
        final UserCategoryNewsBean userCategoryNewsBean
                = new UserCategoryNewsBean();

        User user = userDao.findByNickname(nickname);

        if (user == null) {
            userCategoryNewsBean.setErrorMessage(
                    "No such a user with nickname: " + nickname);

            return "categorynews";
        }

        if (category == null) {
            userCategoryNewsBean.setErrorMessage(
                    "No category passed: " + category);

            return "categorynews";
        }

        RssFeedCategory rssFeedCategory
                = RssFeedCategory.getCategoryByString(category);

        if (rssFeedCategory == null) {
            userCategoryNewsBean.setErrorMessage(
                    "No valid category : " + category);

            return "categorynews";
        }

        List<RssFeedUrlToRssFeedItem> allSortedNews
                = new ArrayList<>();
        List<UserRssFeed> userFeeds = user.getRssFeeds();
        if (userFeeds != null) {
            for (UserRssFeed userRssFeed : userFeeds) {
                String rssfeedUrl = userRssFeed.getRssFeedUrl();
                List<RssFeed> feeds = rssFeedDao.findByUrl(rssfeedUrl);
                RssFeed rssFeed = feeds != null && !feeds.isEmpty() ? feeds.get(0) : null;
                Set<RssFeedCategory> rssFeedCategories
                        = rssFeed.getCategories();
                if (rssFeedCategories.contains(rssFeedCategory)) {
                    String rssFeedUrl = rssFeed.getUrl();
                    for (RssFeedItem it : rssFeed.getItems()) {
                        allSortedNews.add(new RssFeedUrlToRssFeedItem(rssFeedUrl, it));
                    }
                }
            }
        }

        userCategoryNewsBean.setNumberOfAllNews(allSortedNews.size());
        Collections.sort(allSortedNews, new Comparator<RssFeedUrlToRssFeedItem>() {
            public int compare(RssFeedUrlToRssFeedItem o1, RssFeedUrlToRssFeedItem o2) {
                if (o1.getRssFeedItem().getPubDate() == null
                        && o2.getRssFeedItem().getPubDate() != null) {
                    return 1;
                }
                if (o1.getRssFeedItem().getPubDate() == null
                        || o2.getRssFeedItem().getPubDate() == null) {
                    return -1;
                }
                return o2.getRssFeedItem().getPubDate()
                        .compareTo(o1.getRssFeedItem().getPubDate());
            }
        });

        List<RssFeedUrlToRssFeedItem> news = new ArrayList<>();
        int startIndex = page != null ? page.intValue() * size : 0;
        int endIndex = startIndex + size;
        if (allSortedNews.size() > startIndex) {
            if (allSortedNews.size() > endIndex) {
                news.addAll(allSortedNews.subList(
                        0, endIndex));
                userCategoryNewsBean.setShowNextBtn(true);
            } else {
                news.addAll(
                        allSortedNews.subList(
                                0, allSortedNews.size() - 1));
                userCategoryNewsBean.setShowNextBtn(false);
            }
        } else {
            news.addAll(allSortedNews);
        }

        userCategoryNewsBean.setNews(news);
        userCategoryNewsBean.setNextPage(page + 1);

        model.addAttribute("userCategoryNewsBean", userCategoryNewsBean);

        return "categorynews";
    }

}
