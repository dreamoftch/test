package com.tch.test.spring.boot.test.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tch.test.spring.boot.test.service.RedisService;
import com.tch.test.spring.boot.test.vo.User;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate template;

    @Override
    public List<User> getUsers() {
        log.info("getUsers");
        return template.execute(new RedisCallback<List<User>>() {
            @SuppressWarnings("unchecked")
            public List<User> doInRedis(RedisConnection connection) throws DataAccessException {
                String userJson = ((StringRedisConnection) connection).get("user");
                System.out.println(userJson);
                return JSON.parseObject(userJson, List.class);
            }
        });
    }

    @Override
    public void addUser(final User user) {
        log.info("addUser");
        template.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                List<User> users = getUsers();
                if(users == null){
                    users = new ArrayList<User>();
                }
                users.add(user);
                ((StringRedisConnection) connection).set("user", JSON.toJSONString(users));
                return null;
            }
        });
    }
    
    @Override
    public Long delAllUsers() {
        log.info("delAllUsers");
        return template.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return ((StringRedisConnection) connection).del("user");
            }
        });
    }

}
