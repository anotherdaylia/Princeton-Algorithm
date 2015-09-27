package com.lia.lab;

/**
 *  randomized queue is similar to a stack or queue, except that 
 *  the item removed is chosen uniformly at random from items in the data structure.
 *  
 *  Corner cases. 
 *  The order of two or more iterators to the same randomized queue must be mutually independent; 
 *  each iterator must maintain its own random order. 
 *  Throw a java.lang.NullPointerException if the client attempts to add a null item; 
 *  throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue; 
 *  throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator; 
 *  throw a java.util.NoSuchElementException if the client calls the next() method in the iterator 
 *  and there are no more items to return.
 *  
 *  Performance requirements. 
 *  Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) 
 *  in constant amortized time. That is, any sequence of M randomized queue operations (starting from an empty queue) 
 *  should take at most cM steps in the worst case, for some constant c. 
 *  A randomized queue containing N items must use at most 48N + 192 bytes of memory. 
 *  Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time; 
 *  and construction in linear time; you may (and will need to) use a linear amount of extra memory per iterator.
 */
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
