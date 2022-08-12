package com.vttp2.ssfassessment.models;

import java.util.List;

import jakarta.json.JsonObject;

public class News {
    private String id;
    private String published_on;
    private String title;
    private String url;
    private String imageurl;
    private String body;
    private String tags;
    private String categories;
    public static List<News> allNews;
    

    public static List<News> getAllNews() {
        return allNews;
    }
    public void setAllNews(List<News> allNews) {
        this.allNews = allNews;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPublished_on() {
        return published_on;
    }
    public void setPublished_on(String published_on) {
        this.published_on = published_on;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }

    public static News create(JsonObject jo) {
        News n = new News();
        n.setId(jo.getString("id"));
        n.setPublished_on(jo.get("published_on").toString());
        n.setTitle(jo.getString("title"));
         n.setUrl(jo.getString("url"));
         n.setImageurl(jo.getString("imageurl"));
         n.setBody(jo.getString("body"));
         n.setTags(jo.getString("tags"));
         n.setCategories(jo.getString("categories"));
        // /ystem.out.println("ID is >>>> " + n.getId());
        // System.out.println("ImageUrl is >>>> " + n.getImageurl());
        return n;
    }
}
