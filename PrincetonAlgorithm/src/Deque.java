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

import java.util.Arrays;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>{
	private int capacity;
	private int size;
	private Item[] dequeArray;
	private int front, back;
	public final static int DEFAULT_CAPACITY = 4;
	
	// construct an empty deque
	public Deque() {
		this.capacity = DEFAULT_CAPACITY;
		this.size = 0;
		this.front = 0;
		this.back = 0;
		this.dequeArray = (Item[]) new Object[DEFAULT_CAPACITY];
	}
	
	// is the deque empty?
	public boolean isEmpty() {
		if( size() == 0 ) {
			return true;
		}
		return false;
	}
	
	// return the capacity of the deque
	public int getCapacity() {
		return this.capacity;
	}
	
	// return the number of items on the deque
	public int size() {
		return this.size;
	}
	
	// add the item to the front
	public void addFirst(Item item) throws java.util.NoSuchElementException {
		if (item == null) {
			throw new java.util.NoSuchElementException();
		}
		
		if(size() == 0) {
			dequeArray[front] = item;
			size++;
		}else {
			if(front == 0 ) {
				front = dequeArray.length - 1;
			}else {
				front--;
			}
			dequeArray[front] = item;
			size++;
		}
	}
	
	// add the item to the end
	public void addLast(Item item) {
		if(size() == 0) {
			dequeArray[back] = item;
			size++;
		} else{
			if(back == dequeArray.length - 1) {
				back = 0;
			}else {
				back++;
			}
			dequeArray[back] = item;
			size++;
		}
	}
	
	// remove and return the item from the front
	public Item removeFirst() throws java.util.NoSuchElementException {
		if(size() == 0) {
			throw new java.util.NoSuchElementException();
		}
		
		int oldfront = front;
		dequeArray[front] = null;
		size--;
		
		if(front == dequeArray.length - 1) {
			front = 0;
		}else{
			front++;
		}
		
		return dequeArray[oldfront];
	}
	
	// remove and return the item from the end
	public Item removeLast() throws java.util.NoSuchElementException {
		if(size() == 0) {
			throw new java.util.NoSuchElementException();
		}
		
		int oldback = back;
		dequeArray[back] = null;
		size--;
		
		if(back == 0){
			back = dequeArray.length - 1;
		}else {
			back--;
		}
		
		return dequeArray[oldback];
	}
	
	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new MyIterator();
	}
	
	public class MyIterator implements Iterator<Item> {
		private Item[] list;
		private int size;
		private int i;
		
		public MyIterator() {
			list = (Item[]) new Object[size()];
			size = size();
			i = 0;
			
			int j = 0;
			while(front != back) {
				if (front > dequeArray.length - 1) {
					front = 0;
				}
				list[j] = dequeArray[front];
				front++;
				j++;
			}
			list[j] = dequeArray[front];
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return size !=  0;
		}

		@Override
		public Item next() {
			// TODO Auto-generated method stub
			if(hasNext()==false){
				throw new java.util.NoSuchElementException();
			}
			
			Item nextItem = list[i];
			i++;
			size--;
			return nextItem;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub	
			throw new java.lang.UnsupportedOperationException();
		}	
	}
	
	// double the capacity of the deque when it is full
	// copy the original data to the new array
	public void doubleCapacity() {
		int newCapacity = capacity * 2;
		
		Item[] newArray = (Item[]) new Object[newCapacity];
		
		int j = 0;
		while(front != back) {
			if (front > dequeArray.length - 1) {
				front = 0;
			}
			newArray[j] = dequeArray[front];
			front++;
			j++;
		}
		newArray[j] = dequeArray[front];
		
		this.dequeArray = newArray;
		this.capacity = newCapacity;
		this.front = 0;
		this.back = size() - 1;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deque<Integer> deque = new Deque<Integer>();
		System.out.println("size:" +  deque.size() + ", front: " + deque.front + ", back: " + deque.back);
		deque.addFirst(1);
		System.out.println("size:" +  deque.size() + ", front: " + deque.front + ", back: " + deque.back);
		deque.addLast(2);
		System.out.println("size:" +  deque.size() + ", front: " + deque.front + ", back: " + deque.back);
		deque.addLast(3);
		System.out.println("size:" +  deque.size() + ", front: " + deque.front + ", back: " + deque.back);
		deque.addLast(4);
		System.out.println("size:" +  deque.size() + ", front: " + deque.front + ", back: " + deque.back);
		deque.addLast(5);
		System.out.println("size:" +  deque.size() + ", front: " + deque.front + ", back: " + deque.back);
		
		Iterator<Integer> it = deque.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}

	}

}
