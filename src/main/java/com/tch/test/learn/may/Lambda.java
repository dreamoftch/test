package com.tch.test.learn.may;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.tch.test.learn.may.bean.Department;
import com.tch.test.learn.may.bean.Employ;

public class Lambda {

	public static void main(String[] args) {
		//Collectors文档的例子
		Employ e = new Employ("张三", 6000, new Department("技术部"));
		Employ e2 = new Employ("李四", 6500, new Department("产品部"));
		Employ e3 = new Employ("王五", 7500, new Department("高管"));
		Employ e4 = new Employ("赵六", 7500, new Department("高管"));
		List<Employ> people = Arrays.asList(e, e2, e3, e4);
		
		// Accumulate names into a List
		List<String> l = new ArrayList<>();
		people.stream().forEach(employ->l.add(employ.getName()));
		System.out.println(l);
		System.out.println(people.stream().map(employ->employ.getDepartment().getName()).collect(Collectors.toList()));
		System.out.println(people.stream().map(employ->employ.getName()).collect(Collectors.toList()));
		System.out.println(people.stream().map(Employ::getName).collect(Collectors.toList()));

	    // Accumulate names into a TreeSet
		System.out.println(people.stream().map(Employ::getName).collect(Collectors.toCollection(HashSet::new)));
	    System.out.println(people.stream().map(Employ::getName).collect(Collectors.toCollection(TreeSet::new)));

	    // Convert elements to strings and concatenate them, separated by commas
	    System.out.println(people.stream().map(Employ::toString).collect(Collectors.joining(", ")));

	    // Compute sum of salaries of employee
	    System.out.println(people.stream().collect(Collectors.summingInt(Employ::getSalary)));

	    // Group employees by department
	    Map<Department, List<Employ>> byDept = people.stream().collect(Collectors.groupingBy(Employ::getDepartment));
	    System.out.println(byDept);

	    // Compute sum of salaries by department
	    Map<Department, Integer> totalByDept = people.stream().collect(Collectors.groupingBy(Employ::getDepartment, Collectors.summingInt(Employ::getSalary)));
	    System.out.println(totalByDept);

	    // Partition students into passing and failing
	    Map<Boolean, List<Employ>> passingFailing = people.stream().collect(Collectors.partitioningBy(ele->ele.getSalary()>6000));
	    System.out.println(passingFailing);
	}
	
}
