package com.tch.test.spring.boot.test.service;

import java.util.List;

import com.tch.test.spring.boot.test.vo.User;

public interface RedisService {

    List<User> getUsers();

    void addUser(User user);

    Long delAllUsers();
}
