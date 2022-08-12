package com.vttp2.ssfassessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.vttp2.ssfassessment.models.News;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NewsRedis {
    private static final Logger logger = LoggerFactory.getLogger(NewsRedis.class);

    @Autowired
    @Qualifier("newsRedisConfig")
    RedisTemplate<String, News> redisTemplate;

    public void save(final News n) {
        logger.info("save article > " + n.getId());
        redisTemplate.opsForValue().set(n.getId(), n);
    }
}
