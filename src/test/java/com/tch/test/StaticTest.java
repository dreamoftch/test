package com.tch.test;

public class StaticTest {

	static StaticTest staticTest = new StaticTest();
	
	int a = 110;
	static int b = 112;
	
	static{
		System.out.println(1);
	}
	
	{
		System.out.println(2);
	}
	
	StaticTest(){
		System.out.println(3);
		System.out.println("a=" + a + " , b=" + b);
	}
	
	public static void main(String[] args) {
		staticFunction();
	}

	private static void staticFunction() {
		//1
		//3
		//2
		System.out.println(4);
	}
	
}
