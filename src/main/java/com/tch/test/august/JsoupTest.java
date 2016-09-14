package com.tch.test.august;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {

	public static void main(String[] args) throws Exception {
		Map<String, String> data = new HashMap<>();
		data.put("nname", "");
		data.put("agentcode", "");
		data.put("sex", "0");
		data.put("age1", "18");
		data.put("age2", "65");
		data.put("education", "0");
		data.put("atplace", "上海市");
		data.put("type", "0");
		//data.put("corp", "");
		//data.put("corpzj", "");
		//data.put("Submit", "下一页");
		Document doc = Jsoup
				.connect("http://www.life-sky.net/sou/index.asp?page=1")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.postDataCharset("gb2312")
				.data(data)
				.timeout(10000)
				.post();
		
		Elements elements = doc.select("td.f");
		Element element = elements.get(0);
		Elements fonts = element.select("font[size=\"-1\"]");
		for(Element font : fonts){
			String content = font.text();
			String[] contenArr = content.split("　");
			Pattern pattern = Pattern.compile("MP:\\d{11,12}");
			Matcher matcher = pattern.matcher(contenArr[5]);
			System.out.print("姓名：" + contenArr[0]);
			if(matcher.find()){
				System.out.println(", 手机号：" + matcher.group());
			}
		}
	}

}
