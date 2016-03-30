package com.tch.test.learn.march.producer_consumer.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tch.test.learn.march.producer_consumer.common.Consumer;
import com.tch.test.learn.march.producer_consumer.common.Container;
import com.tch.test.learn.march.producer_consumer.common.Producer;

/**
 * 测试使用reentrantLock关键字实现的生产者-消费者
 * @author tianchaohui
 */
public class ProducerConsumerTest {

	public static void main(String[] args) {
		Container container = new LockContainer();
		
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
		
		Producer producer = new Producer();
		producer.setContainer(container);
		
		Consumer consumer = new Consumer();
		consumer.setContainer(container);
		
		//3个生产者
		threadPool.execute(producer);
		threadPool.execute(producer);
		threadPool.execute(producer);
		//2个消费者
		threadPool.execute(consumer);
		threadPool.execute(consumer);
	}
	
}
