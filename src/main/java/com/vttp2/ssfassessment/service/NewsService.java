package com.vttp2.ssfassessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vttp2.ssfassessment.models.AllArticles;
import com.vttp2.ssfassessment.models.News;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NewsService {

    // @Value("${crypto.apikey}")
    // String apiKey;

    String payload;

    @Autowired
    @Qualifier("newsRedisConfig")
    RedisTemplate<String, News> redisTemplate;

    
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    private static String URL = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";

    public List<News> getArticles () {
        String apiKey = System.getenv("CRYPTO_API_KEY");
        try {
            String url = UriComponentsBuilder.fromUriString(URL)
                .queryParam("ApiKey", apiKey)
                .toUriString();

                //create get req, get url
                RequestEntity<Void> req = RequestEntity.get(url).build();

                //make call to cryptocompare
                RestTemplate template = new RestTemplate();
                ResponseEntity<String> resp;

                //throw exception if status code not in range
                resp = template.exchange(req, String.class);

                //get payload
                payload = resp.getBody();
                //System.out.println("payload: " + payload);

        } catch (Exception e) {
            System.err.printf("Error: %s\n", e.getMessage());
            return Collections.emptyList();
        }

        //convert payload to jsonObject
        //convert string to reader
        Reader strReader = new StringReader(payload);
        //create jsonreader from reader
        JsonReader jsonReader = Json.createReader(strReader);
        //read payload as json obj
        JsonObject newsResult = jsonReader.readObject();
        //logger.info("result is " + newsResult);
        JsonArray articles = newsResult.getJsonArray("Data");
        //logger.info("articles is " + articles);
        List<News> list = new LinkedList<>();
        for ( int i = 0; i < articles.size(); i++) {
            JsonObject jo = articles.getJsonObject(i);
            list.add(News.create(jo));
            
        }
        return list;
    }

    public void save(final News n) {
       logger.info("Save articles > " + n.getId());
       redisTemplate.opsForValue().set(n.getId(), n);
    }

    public void saveArticles (AllArticles al) {
        List<News> saveNews = new ArrayList<>();
        for (News article : News.getAllNews()){
            saveNews.add(article);
            redisTemplate.opsForValue().set(article.getId(), article);
        }
    }

    public News[] getAllArticles() {
        Set<String> allArticleKeys = redisTemplate.keys("*");
        List<News> allArticle = new LinkedList<>();
        for (String articleKey: allArticleKeys) {
            News result = (News) redisTemplate.opsForValue().get(articleKey);
            allArticle.add(result);
        }
        return allArticle.toArray(new News[allArticle.size()]);
        
    }

    public News findById (final String id) {
        logger.info("find article by id > " + id);
        News result = (News) redisTemplate.opsForValue().get(id);
        return result;
    }



   
}
