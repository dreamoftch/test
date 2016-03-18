package com.tch.test.fastjson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tch.test.spring.boot.test.vo.User;

public class FastJsonTest {

    @Test
    public void testList() {
        //https://github.com/alibaba/fastjson/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98  #fastjson如何处理日期
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        List<User> users = new ArrayList<User>();
        users.add(new User(25, "测试", "上海", new Date()));
        users.add(new User(26, "测试2", "上海2", new Date()));
        String listJson = JSON.toJSONString(users, SerializerFeature.WriteDateUseDateFormat);
        System.out.println(listJson);
        List<User> newList = JSON.parseObject(listJson, new TypeReference<List<User>>() {});
        System.out.println(newList);
    }

    @Test
    public void testMap() {
        Map<Integer, User> map = new HashMap<Integer, User>();
        map.put(1, new User(25, "测试", "上海", new Date()));
        map.put(2, new User(26, "测试2", "上海2", new Date()));
        String mapJson = JSON.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
        System.out.println(mapJson);
        Map<Integer, User> newMap = JSON.parseObject(mapJson, new TypeReference<Map<Integer, User>>() {});
        System.out.println(newMap);
    }

}
