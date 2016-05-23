package com.tch.test.spring.boot.test.controller;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tch.test.spring.boot.test.service.RedisService;
import com.tch.test.spring.boot.test.vo.User;

@Slf4j
@RestController
public class SampleController {
    
    @Autowired
    private RedisService redisService;

    @RequestMapping(value={"/", "/home"})
    String home() {
        return "Hello World !";
    }
    
    @RequestMapping(value={"/delAllUsers"})
    String delAllUsers(){
        redisService.delAllUsers();
        return "delAllUsers success";
    }
    
    @RequestMapping(value={"/getUsers"})
    List<User> getUsers() {
        return redisService.getUsers();
    }
    
    @RequestMapping(value={"/addUser"})
    String addUser() {
        redisService.addUser(new User(25, "测试", "上海", new Date()));
        return "addUser success";
    }

}
