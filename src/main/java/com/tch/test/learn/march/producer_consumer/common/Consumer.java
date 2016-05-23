package com.tch.test.learn.march.producer_consumer.common;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Consumer implements Runnable{
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Consumer.class);

	private Container container;
	
	private Random random = new Random();
	
	@Override
	public void run() {
		while(true){
			try {
				System.out.println("消费了" + container.get());
				//100ms以内随机等待时间
				TimeUnit.MILLISECONDS.sleep(Math.round(random.nextDouble() * 10));
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}
	
}
