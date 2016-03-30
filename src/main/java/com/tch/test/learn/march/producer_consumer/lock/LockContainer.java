package com.tch.test.learn.march.producer_consumer.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.tch.test.learn.march.producer_consumer.common.Container;
import com.tch.test.learn.march.producer_consumer.common.Food;

/**
 * 使用reentrantLock实现的container
 * @author tianchaohui
 *
 */
public class LockContainer implements Container {

	private ReentrantLock lock = new ReentrantLock();
	
	/**not empty*/
	private Condition notEmpty = lock.newCondition();
	
	/**not full*/
	private Condition notFull = lock.newCondition();
	
	private Food[] foods = new Food[3];
	
	private int index = 0;
	
	@Override
	public void put(Food food) throws InterruptedException {

		lock.lock();
		try{
			while(index >= foods.length){
				//container is full
				notFull.await();
			}
			//container is not full
			foods[index++] = food;
			notEmpty.signalAll();
		}finally{
			lock.unlock();
		}
		
	}

	@Override
	public Food get() throws InterruptedException {
		Food food = null;
		
		lock.lock();
		try{
			while(index <= 0){
				//container is empty
				notEmpty.await();
			}
			//container is not empty
			food = foods[--index];
			notFull.signalAll();
		}finally{
			lock.unlock();
		}
		
		return food;
	}

}
