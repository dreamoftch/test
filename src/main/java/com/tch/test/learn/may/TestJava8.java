package com.tch.test.learn.may;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Strings;

public class TestJava8 {

    public static void main(String[] args) {
    	String[] array = {"a", "b", "c"};
    	for(int i: Arrays.asList(1,2,3)){
		  Stream.of(array).map(item -> Strings.padEnd(item, i, '@')).forEach(System.out::println);
		}

    }

}