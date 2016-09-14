package com.tch.test.august;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;

public class LeanCloud {

	public static void main(String[] args) throws AVException {
		AVOSCloud.initialize("UdMRw0pCFzpOIKGPR4j1S0R6-gzGzoHsz", "6OX9dozlgyO4AeNDDjCju5AI", "gAnNaKTaCDbecv88IdhPjuV1");
		
	    AVQuery<AVObject> avQuery = new AVQuery<>("_Conversation");
	    avQuery.whereContainsAll("m", Arrays.asList("204", "133"));
	    avQuery.whereEqualTo("sys", true);
	    avQuery.whereSizeEqual("m", 2);
	    avQuery.setLimit(1);
	    AVObject object = avQuery.getFirst();
	    System.out.println(object.get("m"));
	    System.out.println(object.getObjectId());
		
	    /*AVObject testObject = new AVObject("TestObject");
		testObject.put("foo", "bar");
		testObject.save();*/
		
		
	}
	
	public static void sort() {

        Map<String, Integer> unsortMap = new HashMap<>();
        unsortMap.put("z", 10);
        unsortMap.put("b", 5);
        unsortMap.put("a", 6);
        unsortMap.put("c", 20);
        unsortMap.put("d", 1);
        unsortMap.put("e", 7);
        unsortMap.put("y", 8);
        unsortMap.put("n", 99);
        unsortMap.put("j", 50);
        unsortMap.put("m", 2);
        unsortMap.put("f", 9);

        System.out.println("Original...");
        System.out.println(unsortMap);

        Map<String, Integer> result = new LinkedHashMap<>();

        //sort by key, a,b,c..., and put it into the "result" map
        unsortMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByKey())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        System.out.println("Sorted...");
        System.out.println(result);

    }

}
