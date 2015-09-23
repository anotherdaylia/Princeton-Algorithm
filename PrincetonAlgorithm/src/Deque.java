/**
 * Dequeue. A double-ended queue or deque (pronounced "deck") is a generalization of 
 * a stack and a queue that supports adding and removing items from either the front 
 * or the back of the data structure. 
 * 
 * Corner cases. 
 * Throw a java.lang.NullPointerException if the client attempts to add a null item; 
 * throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque; 
 * throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator; 
 * throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and 
 * there are no more items to return.
 * 
 * Performance requirements.
 * Your deque implementation must support each deque operation in constant worst-case time. 
 * A deque containing N items must use at most 48N + 192 bytes of memory. 
 * and use space proportional to the number of items currently in the deque. 
 * Additionally, your iterator implementation must support each operation (including construction) 
 * in constant worst-case time.
 * 
 * author: Lia Qu
 * date: Sept 23rd, 2015
 */

import java.util.Iterator;


public class Deque<Item> implements Iterable<Item>{
	private int capacity;
	private int size;
	private Item[] dequeArray;
	public final static int DEFAULT_CAPACITY = 10;
	
	// construct an empty deque
	public Deque() {
		this.capacity = DEFAULT_CAPACITY;
	}
	
	// is the deque empty?
	public boolean isEmpty() {
		return true;
	}
	
	// return the number of items on the deque
	public int size() {
		return size;
	}
	
	// add the item to the front
	public void addFirst(Item item) {
		
	}
	
	// add the item to the end
	public void addLast(Item item) {
		
	}
	
	// remove and return the item from the front
	public Item removeFirst() {
		int first = 0;
		return dequeArray[first];
	}
	
	// remove and return the item from the end
	public Item removeLast() {
		int last = size - 1;
		return dequeArray[last];
	}
	
	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return null;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
