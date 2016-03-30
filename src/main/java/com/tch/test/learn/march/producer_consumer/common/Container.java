package com.tch.test.learn.march.producer_consumer.common;

public interface Container {

	/**
	 * 生产者往容器存放食物
	 * @param food
	 */
	void put(Food food) throws InterruptedException ;

	/**
	 * 消费者从容器获取食物
	 * @param food
	 */
	Food get() throws InterruptedException;
	
}
