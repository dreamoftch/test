package com.tch.test;

import java.util.Arrays;

public class Sort2 {
	
	private static int[] array = {6,3,2,1,5,8,4,9,7};

	public static void main(String[] args) {
		/*quickSort(array, 0, array.length-1);
		System.out.println(Arrays.toString(array));*/
		/*Long long1 = 0l;
		System.out.println(long1.longValue() == 0);*/
		System.out.println(Math.max(Integer.MAX_VALUE, 18521736087l));
	}
	
	public static void quickSort(int[] array, int from, int to){
		int i = from;
		int j = to;
		int key = array[from];
		
		while(i<j){
			
			while(i<j){
				if(array[j] < key){
					swap(array, i, j);
					System.out.println(Arrays.toString(array));
					i++;
					break;
				}
				j--;
			}
			while(i<j){
				if(array[i] > key){
					swap(array, i, j);
					System.out.println(Arrays.toString(array));
					j--;
					break;
				}
				i++;
			}
		}
		
		if(i>from){
			quickSort(array, from, i-1);
		}
		if(i<to){
			quickSort(array, i+1, to);
		}
	}
	
	private static void swap(int[] array, int i, int j){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
}
