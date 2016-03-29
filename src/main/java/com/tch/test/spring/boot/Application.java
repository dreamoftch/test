package com.tch.test.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisShardInfo;

@SpringBootApplication
public class Application {
    
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
        JedisConnectionFactory factory = createJedisConnectionFactory();
        StringRedisTemplate template = new StringRedisTemplate(factory);
        return template;
    }
}