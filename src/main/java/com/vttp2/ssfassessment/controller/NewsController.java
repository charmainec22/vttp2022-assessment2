package com.vttp2.ssfassessment.controller;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vttp2.ssfassessment.models.AllArticles;
import com.vttp2.ssfassessment.models.News;
import com.vttp2.ssfassessment.service.NewsRedis;
import com.vttp2.ssfassessment.service.NewsService;

@Controller
public class NewsController {
    @Autowired
    private NewsService newsSvc;

    String payload;
    public List<News> everyNews;

    @Autowired
    NewsRedis newsRedis; 

    private Logger logger = LoggerFactory.getLogger(NewsController.class);
    

    // @GetMapping("/")
    // public String showIndexPage (Model model) {
    //     News n = new News();
    //     //model.addAttribute("News", n);
    //     Optional<News> optNews = newsSvc.getArticles(n);
    //     logger.info("news is" + optNews);
    //     if (optNews.isEmpty()) {
    //         model.addAttribute("News", new News());
    //         return "article";
    //     }
    //     return "article";
    // }

    @GetMapping
    public String getArticle(Model model) {
        List<News> news = newsSvc.getArticles();
        logger.info("get article");
        model.addAttribute("news", news);
        return "article";
    }

    // @PostMapping(path = "/articles")
    // public String postArticles2(@ModelAttribute News news, Model model) throws IOException {
    //     logger.info("add article");
    //     News n = new News();
    //     newsSvc.save(n);
    //     model.addAttribute("news", news);
    //     return "article";

      
    // }

    @PostMapping(path = "/articles")
    public String saveArticle(@ModelAttribute("News") AllArticles allArticles, Model model) throws IOException {
        AllArticles everyArticle = new AllArticles();
        everyArticle.allArticles = everyNews;

        newsSvc.saveArticles(everyArticle);
        logger.info("saved article is >> " + everyArticle);
        allArticles.setAllArticles(newsSvc.getArticles());
        model.addAttribute("News", allArticles);
        return "article";
        
    }


}
