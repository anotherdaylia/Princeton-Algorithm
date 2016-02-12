package com.lia.lab.Queues2;

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
    private final static int DEFAULT_CAPACITY = 4;
    private int N;
    private int first; // index of first element of queue
    private int last;  // index of next available slot
	private Item[] queue;
	
	// construct an empty randomized queue
	public RandomizedQueue() {
        this.N = 0;
        this.first = 0;
        this.last = 0;
        this.queue = (Item[]) new Object[DEFAULT_CAPACITY];
	}
	
	// is the queue empty?
	public boolean isEmpty() {
		return N == 0;
	}
	
	// return the number of items on the queue
	public int size() {
		return N;
	}
	
	// add the item
	public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();

        if (N == queue.length) resize(queue.length * 2);
        queue[last] = item;
        last++;

        if(last == queue.length) last = 0;
        N++;
	}

	// remove and return a random item
	public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int ix = (StdRandom.uniform(N) + first) % queue.length;
        Item itemToRm = queue[ix];

        queue[ix] = queue[first];
        queue[first] = null;
        N--;
        first++;

        if (first == queue.length) first = 0;
        if (N > 0 && N == queue.length/4) resize(queue.length/2);

        return itemToRm;
	}

	// return (but do not remove) a random item
	public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int ix = (StdRandom.uniform(N) + first) % queue.length;

        return queue[ix];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
        return new ArrayIterator();
	}

    private class ArrayIterator implements Iterator<Item> {
        private Item[] arr;
        private int i;

        public ArrayIterator() {
            // 1. copy queue to array
            arr = (Item[]) new Object[N];
            for (int k = 0; k < N; k++) {
                arr[k] = queue[(k + first) % queue.length];
            }
            // Why do we a second loop here?
            for (int k = arr.length - 1; k >= 0; k--) {
                int ix = StdRandom.uniform(0, N);
                Item tmp = arr[ix];
                arr[ix] = arr[k];
                arr[k] = tmp;
            }
        }

        @Override
        public boolean hasNext() { return i < N; }

        @Override
        public Item next() {
            if (!hasNext()) { throw new java.util.NoSuchElementException(); }

            Item nextItem = arr[i];
            i++;
            return nextItem;
        }

        @Override
        public void remove() { throw new UnsupportedOperationException(); }
    }

    private void resize(int capacity) {
        Item[] arr = (Item[]) new Object[capacity];

        // this for loop apprears twice, one here, the other one at line 104.
        // Maybe make it a function?
        for (int k = 0; k < N; k++) {
            arr[k] = queue[(k + first) % queue.length];
        }

        this.queue = arr;
        this.first = 0;
        this.last = N;
    }

	public static void main(String[] args) {

	}

}
