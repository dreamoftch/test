package com.tch.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class T {
	
	/**
	 * 判断字符串是否是纯数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		return str.matches("^(1[3-8]\\d{9}$");
	}
	
	public static boolean isPassword(String password){
		String reg = "[0-9A-Za-z!@#$%^\\&*()_+-.]{6,16}";
		return password.matches(reg);
	}
	private static String generateUserName() {
		//格式：USER_XXXX
		StringBuilder builder = new StringBuilder("USER_");
		Random random = new Random();
		for(int i = 0; i < 4; i++){
			builder.append(random.nextInt(10));
		}
		return builder.toString();
	}
	private static String calculateUserGrade(List<String> userGrades) {
		
		
//		List<String> userGrades = new ArrayList<>();
//		userGrades.add("4.1");
//		userGrades.add("4.0");
//		userGrades.add("4.5");
//		userGrades.add("3.5");
//		userGrades.add("3.0");
//		userGrades.add("2.5");
		if(userGrades == null || userGrades.isEmpty()){
			return "0";
		}
		BigDecimal count = new BigDecimal("" + userGrades.size());
		BigDecimal totalGrade = new BigDecimal("0");
		for(String userGrade : userGrades){
			totalGrade = totalGrade.add(new BigDecimal(userGrade));
		}
		Double result = totalGrade.divide(count, 1, RoundingMode.HALF_UP).doubleValue();
		System.out.println(count);
		System.out.println(totalGrade);
		System.out.println(result);
		int intValue = result.intValue();
		double decimal = result%1;
		if(decimal == 0){
			return "" + intValue;
		}else if(decimal > 0 && decimal < 0.5){
			return "" + (intValue + 0.5);
		}else{
			return "" + (intValue + 1);
		}
	}
	
	public static void main(String[] args) {
		
		String num = "111111.11";
		System.out.println(num.matches("\\d+(\\.\\d{0,2})?"));
		
		/*StringBuilder stringBuilder = new StringBuilder("1");
		StringBuilder[] arr = {stringBuilder, new StringBuilder("2"), new StringBuilder("3")};
		List<StringBuilder> integers = Arrays.asList(arr);
		
		integers.stream().forEach((ele) -> {
			System.out.println(ele);
		});
		stringBuilder.append("-----");
		
		integers.stream().forEach((ele) -> {
			System.out.println(ele);
		});*/
		
//		System.out.println("566.1".matches("(\\d)+"));
		
//		List<String> userGrades = new ArrayList<>();
//		userGrades.add("3.0");
//		userGrades.add("3.0");
//		userGrades.add("3.0");
//		userGrades.add("1.0");
//		userGrades.add("4.0");
//		userGrades.add("2.0");
//		System.out.println("calculateUserGrade:" + calculateUserGrade(userGrades));
		
		/*userGrades = new ArrayList<>();
		userGrades.add("4.1");
		System.out.println("calculateUserGrade:" + calculateUserGrade(userGrades));
		
		userGrades = new ArrayList<>();
		userGrades.add("4.5");
		System.out.println("calculateUserGrade:" + calculateUserGrade(userGrades));
		
		userGrades = new ArrayList<>();
		userGrades.add("4.6");
		System.out.println("calculateUserGrade:" + calculateUserGrade(userGrades));
		
		userGrades = new ArrayList<>();
		userGrades.add("4.9");
		System.out.println("calculateUserGrade:" + calculateUserGrade(userGrades));
		
		userGrades = new ArrayList<>();
		userGrades.add("5.0");
		System.out.println("calculateUserGrade:" + calculateUserGrade(userGrades));*/
		
		/*String string = "id\r\n999";
		Pattern pattern = Pattern.compile("id\r\n(\\d+)");
		Matcher matcher = pattern.matcher(string);
		if(matcher.find()){
			System.out.println(matcher.group(1));
		}*/
		/*String reg =  "[0-9A-Za-z!@#$%^\\&*()_+-.]{6,16}";
		System.out.println("%#&+-1".matches(reg));
		System.out.println("!@#$%^\\&*()_+-.");*/
		/*String stanzeTo = "111@139.196.154.104";
		System.out.println(stanzeTo.substring(0, stanzeTo.indexOf("@")).matches("[0-9]+"));
		System.out.println(Long.valueOf(stanzeTo.substring(0, stanzeTo.indexOf("@"))));*/
		
		
		/*System.out.println(isPassword("111111"));
		System.out.println(isPassword("aaaaaa"));
		System.out.println(isPassword("AAAAAA"));
		System.out.println(isPassword("!!!!!!"));
		System.out.println(isPassword("@@@@@@"));
		System.out.println(isPassword("$$$$$$"));
		System.out.println(isPassword("%%%%%%"));
		System.out.println(isPassword("^^^^^^"));
		System.out.println(isPassword("&&&&&&"));
		
		System.out.println(isPassword("******"));
		System.out.println(isPassword("(((((("));
		System.out.println(isPassword("))))))"));
		System.out.println(isPassword("------"));
		System.out.println(isPassword("______"));
		System.out.println(isPassword("+++++++"));
		System.out.println(isPassword("1Aa@!##$$%"));*/
	}
	
	
	public static boolean isMobile(String str){
		str += "11111111";
		System.out.println(str);
		//第一位1，第二位3-8，总共11位
		String reg ="^1[3-8]\\d{9}$";
		return str.matches(reg);
	}
}
