package com.tch.test.learn.march.producer_consumer.sync;

import com.tch.test.learn.march.producer_consumer.common.Container;
import com.tch.test.learn.march.producer_consumer.common.Food;

/**
 * 使用synchronized实现的container
 * @author tianchaohui
 *
 */
public class SyncContainer implements Container {

	private Food[] foods = new Food[3];
	
	private int index = 0;
	
	@Override
	public void put(Food food) throws InterruptedException {

		synchronized(this){
			while(index >= foods.length){
				//container is full
				wait();
			}
			//container is not full
			foods[index++] = food;
			notifyAll();
		}
		
	}

	@Override
	public Food get() throws InterruptedException {
		Food food = null;
		synchronized(this){
			while(index <= 0){
				//container is empty
				wait();
			}
			//container is not empty
			food = foods[--index];
			notifyAll();
		}
		return food;
	}

}
