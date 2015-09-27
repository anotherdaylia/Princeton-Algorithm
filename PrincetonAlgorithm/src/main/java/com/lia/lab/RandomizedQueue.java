package com.lia.lab;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int capacity;
	private int size;
	private Item[] dequeArray;
	public final static int DEFAULT_CAPACITY = 10;
	
	// construct an empty randomized queue
	public RandomizedQueue() {
		
	}
	
	// is the queue empty?
	public boolean isEmpty() {
		return false;
	}
	
	// return the number of items on the queue
	public int size() {
		return size;
	}
	
	// add the item
	public void enqueue(Item item) {
		
	}
	
	// remove and return a random item
	public Item dequeue() {
		return null;
	}
	
	// return (but do not remove) a random item
	public Item sample() {
		return null;
	}
	
	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
