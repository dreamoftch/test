package com.tch.test.learn.may.bean;

public class Employ {

	private String name;
	private int salary;

	public Employ(String name, int salary, Department department) {
		super();
		this.name = name;
		this.salary = salary;
		this.department = department;
	}

	private Department department;

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employ [name=" + name + ", salary=" + salary + ", department=" + department + "]";
	}
	
	
}
