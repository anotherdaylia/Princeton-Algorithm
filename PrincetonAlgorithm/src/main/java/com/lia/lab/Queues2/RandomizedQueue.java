package com.lia.lab.Queues2;

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by liqu on 2/9/16.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> first; // the beginning of the queue
    private Node<Item> last;  // the end of the queue
    private int N;            // the number of elements on queue


    // construct an empty randomized queue
    public RandomizedQueue() {
        this.first = null;
        this.last = null;
        this.N = 0;
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if(isEmpty()) {
            first.next = last;
        } else {
            oldlast.next = last;
        }
        N++;
    }

    // remove and return a random item
    public Item dequeue() {
        int i = StdRandom.uniform(1, N+1);
        return null;
    }

    // return (but do not remove) a random item
    public Item sample() {
        return null;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator<>();
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        private int size;

        public ListIterator() {
        }

        @Override
        public boolean hasNext() {
            return size !=  0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("No element in the Deque.");

            Item item = current.item;
            current = current.next;
            size--;
            return item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args){

    }
}
