package com.tch.test.learn.march.zookeeper;
 
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ZKTest2 {
 
    private ZkClient zk;
 
    private String nodeName = "/dubbo/com.tch.test.dubbo.service.MyService";
 
    @Before
    public void initTest() {
        zk = new ZkClient("localhost:2181");
    }
 
    @After
    public void dispose() {
        zk.close();
        System.err.println("zkclient closed!");
    }
 
    @Test
    public void testListener() throws InterruptedException, IOException {
        //监听指定节点的数据变化
 
        zk.subscribeDataChanges(nodeName, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.err.println("node data changed!");
                System.err.println("node=>" + s);
                System.err.println("data=>" + o);
                System.err.println("--------------");
            }
 
            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.err.println("node data deleted!");
                System.err.println("s=>" + s);
                System.err.println("--------------");
 
            }
        });
 
        System.err.println("ready!");
 
        System.in.read();
        
    }
 
}