package com.tch.test.learn.april;

import java.util.Arrays;

/**
 * 排序
 * @author tianchaohui
 */
public class Sort {
	
	public static void main(String[] args) {
		int[] array = {1,3,2,6,5,8,4,9,7};
		quickSort(array, 0, array.length-1);
		System.out.println(Arrays.toString(array));
		/*maopao(array, 1);
		System.out.println(Arrays.toString(array));
		
		maopao(array, -1);
		System.out.println(Arrays.toString(array));*/
	}

	/**
	 * 冒泡排序
	 * @param array
	 * @param sortType 正数表示从小到大，负数表示从大到小
	 */
	public static void maopao(int[] array, int sortType){
		if(array == null || array.length == 0){
			return;
		}
		int length = array.length;
		for(int i=0;i<length-1;i++){
			for(int j=0;j<length-i-1;j++){
				if(sortType > 0){
					if(array[j]>array[j+1]){
						int temp = array[j];
						array[j] = array[j+1];
						array[j+1] = temp;
					}
				}else{
					if(array[j]<array[j+1]){
						int temp = array[j];
						array[j] = array[j+1];
						array[j+1] = temp;
					}
				}
				
			}
		}
	}
	
	/**
	 * 快速排序
	 * @param array
	 * @param sortType
	 * @param from
	 * @param to
	 */
	public static void quickSort(int[] array, int from, int to){
		if(array == null || array.length == 0){
			return;
		}
		int i = from;
		int j = to;
		int key = array[from];

		for(; i<j && j > from; j--){
			if(array[j] < key){
				swap(array, i, j);
				break;
			}
		}
		
		for(; i<j && i < to; i++){
			if(array[i] > key){
				swap(array, i, j);
				break;
			}
		}
		
		if(i>from){
			quickSort(array, from, i-1);
		}
		if(i<to){
			quickSort(array, i+1, to);
		}
	
	}
	
	/**
	 * 交换数组两个元素
	 * @param array
	 * @param i
	 * @param j
	 */
	private static void swap(int[] array, int i, int j){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
}
