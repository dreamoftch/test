package com.tch.test.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisShardInfo;

@SpringBootApplication
public class Application {
    
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
    
    @Bean
    public JedisConnectionFactory createJedisConnectionFactory() {
        JedisShardInfo poolConfig = new JedisShardInfo("10.7.253.99", "6379");
        JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
        return factory;
    }
    
    @Bean
    public StringRedisTemplate createStringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
        return template;
    }
}