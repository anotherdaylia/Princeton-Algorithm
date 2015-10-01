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
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int capacity;
	private int size;
    private int index; //index of the most recent added element
	private Item[] queueArray;
    private final static int DEFAULT_CAPACITY = 4;
	
	// construct an empty randomized queue
	public RandomizedQueue() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.index = 0;
        this.queueArray = (Item[]) new Object[DEFAULT_CAPACITY];
	}
	
	// is the queue empty?
	public boolean isEmpty() {
		return size()==0;
	}
	
	// return the number of items on the queue
	public int size() {
		return size;
	}
	
	// add the item
	public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        if(size() >= queueArray.length/2){
            doubleCapacity();
        }

        if(size() == 0) {
            queueArray[index] = item;
            size++;
        }else {
            index++;
            queueArray[index] = item;
            size++;
        }
	}
	
	// remove and return a random item
	public Item dequeue() {
        if (size() == 0) {
            throw new java.util.NoSuchElementException();
        }

        int indexToRm = StdRandom.uniform(index+1);

        Item itemToRm = queueArray[indexToRm];

        queueArray[indexToRm] = queueArray[index];
        queueArray[index] = null;
        size--;
        index--;

        return itemToRm;
	}
	
	// return (but do not remove) a random item
	public Item sample() {
        if (size() == 0) {
            throw new java.util.NoSuchElementException();
        }

        int indexToRt = StdRandom.uniform(index+1);

        return queueArray[indexToRt];
	}
	
	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
        return new MyIterator();
	}

    private class MyIterator implements Iterator<Item> {
        private Item[] list;
        private int size;
        private int i;

        public MyIterator() {
            list = (Item[]) new Object[size()];
            size = size();
            i = index;

            for(int j = 0; j < size(); j++){
                list[j] = queueArray[j];
            }
        }

        @Override
        public boolean hasNext() {
            return size != 0;
        }

        @Override
        public Item next() {
            if(!hasNext()){
                throw new java.util.NoSuchElementException();
            }

            int nextIndex = StdRandom.uniform(index+1);
            Item nextItem = queueArray[nextIndex];

            queueArray[nextIndex] = queueArray[i];
            queueArray[i] = null;
            i--;
            size--;

            return nextItem;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    private void doubleCapacity() {
        int newCapacity = capacity * 2;
        Item[] newArray = (Item[]) new Object[newCapacity];

        int j = 0;
        while(j<= index) {
            newArray[j] = queueArray[j];
            j++;
        }
        //newArray[j] = queueArray[index];

        this.queueArray = newArray;
        this.capacity = newCapacity;
        this.index = size() - 1;
    }

	public static void main(String[] args) {

	}

}
