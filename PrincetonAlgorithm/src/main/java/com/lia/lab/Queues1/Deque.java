
package com.lia.lab.Queues1;

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

public class Deque<Item> implements Iterable<Item> {
    private final static int DEFAULT_CAPACITY = 4;
    private int capacity;
    private int size;
    private Item[] dequeArray;
    private int front, back;

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
		return size() == 0;
	}
	
	// return the capacity of the deque
	private int getCapacity() {
		return this.capacity;
	}
	
	// return the number of items on the deque
	public int size() {
		return this.size;
	}
	
	// add the item to the front
	public void addFirst(Item item) {
		if (item == null) {
			throw new java.lang.NullPointerException();
		}
		
		if (size() >= dequeArray.length/2){
			doubleCapacity();
		}
		
		if (size() == 0) {
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
	public void addLast(Item item) throws java.lang.NullPointerException {
		if (item == null) {
			throw new java.lang.NullPointerException();
		}
		
		if (size() >= dequeArray.length/2){
			doubleCapacity();
		}
		
		if (size() == 0) {
			dequeArray[back] = item;
			size++;
		} else {
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
	public Item removeFirst() {
		if (size() == 0) {
			throw new java.util.NoSuchElementException();
		}

        Item itemToRt = dequeArray[front];
		dequeArray[front] = null;
		size--;
		
		if (front == dequeArray.length - 1) {
			front = 0;
		} else {
			front++;
		}
		
		return itemToRt;
	}
	
	// remove and return the item from the end
	public Item removeLast() {
		if (size() == 0) {
			throw new java.util.NoSuchElementException();
		}

        Item itemToRt = dequeArray[back];
		dequeArray[back] = null;
		size--;
		
		if (back == 0){
			back = dequeArray.length - 1;
		} else {
			back--;
		}
		
		return itemToRt;
	}
	
	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new MyIterator();
	}
	
	private class MyIterator implements Iterator<Item> {
        private Item[] list;
        private int size;
        private int i;
		private int ifront, iback;

		public MyIterator() {
			list = (Item[]) new Object[size()];
			size = size();
			i = 0;
            ifront = front;
            iback = back;
			
			if (size == 0) {
				list = null;
			} else {
				int j = 0;
				while(size != 0 && ifront != iback) {
					if (ifront > dequeArray.length - 1) {
						ifront = 0;
					}
					list[j] = dequeArray[ifront];
					ifront++;
					j++;
				}
				list[j] = dequeArray[ifront];
			}
		}

		@Override
		public boolean hasNext() {
			return size !=  0;
		}

		@Override
		public Item next() {
			if (!hasNext()){
				throw new java.util.NoSuchElementException();
			}
			
			Item nextItem = list[i];
			i++;
			size--;
			return nextItem;
		}

		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}	
	}
	
	// double the capacity of the deque when it is full
	// copy the original data to the new array
	private void doubleCapacity() {
		int newCapacity = getCapacity() * 2;
		
		Item[] newArray = (Item[]) new Object[newCapacity];
		
		int j = 0;
		while (front != back) {
			newArray[j] = dequeArray[front];
			front++;
			j++;
			
			if (front > dequeArray.length - 1) {
				front = 0;
			}
		}
		newArray[j] = dequeArray[front];
		
		this.dequeArray = newArray;
		this.capacity = newCapacity;
		this.front = 0;
		this.back = size() - 1;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub


	}

}
