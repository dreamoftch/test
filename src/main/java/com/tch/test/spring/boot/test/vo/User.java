package com.tch.test.spring.boot.test.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int age;
    private String name;
    private String address;
    private Date birthday;
    
}
