package com.vttp2.ssfassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vttp2.ssfassessment.models.News;
import com.vttp2.ssfassessment.service.NewsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path= "/")
public class NewsRESTController {
    private static final Logger logger = LoggerFactory.getLogger(NewsRESTController.class);

    @Autowired
    NewsService service;

    @GetMapping("/news/{id}")
    public ResponseEntity<News> getArticleById(@PathVariable String id) {
        logger.info("get id > " + id);
        News n = service.findById(id);
        return ResponseEntity.ok(n);
    }
}
