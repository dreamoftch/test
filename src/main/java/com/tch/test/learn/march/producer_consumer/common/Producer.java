package com.tch.test.learn.march.producer_consumer.common;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Producer implements Runnable{

	private Container container;
	
	private Random random = new Random();
	
	private AtomicInteger idGenerator = new AtomicInteger(1);

	@Override
	public void run() {
		while(true){
			try {
				Food food = new Food(idGenerator.getAndIncrement());
				container.put(food);
				System.out.println("生产了" + food);
				//100ms以内随机等待时间
				TimeUnit.MILLISECONDS.sleep(Math.round(random.nextDouble() * 10));
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
}
