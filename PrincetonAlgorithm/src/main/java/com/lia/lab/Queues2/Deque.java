package com.lia.lab.Queues2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by liqu on 2/9/16.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first; //imaginary first node
    private Node<Item> last;  //imaginary last node
    private int N;

    private static class Node<Item> { //why static?
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        this.first = new Node<Item>();
        this.last = new Node<Item>();
        first.next = last;
        last.prev = first;
        this.N = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (N == 0);
    }

    // return the number of items on the deque
    public int size() {
        return this.N;
    }

    // add the item to the first
    public void addFirst(Item item) {
        Node<Item> newItem = new Node<Item>();
        newItem.item = item;
        newItem.next = first.next;
        newItem.prev = first;
        first.next = newItem;
        N++;
    }

    // add the item to the end
    public void addLast(Item item) {
        Node<Item> newItem = new Node<Item>();
        newItem.item = item;
        newItem.prev = last.prev;
        newItem.next = last;

        last.prev.next = newItem;
        last.prev = newItem;

        N++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty.");

        Node<Item> toBeDeleted = first.next;

        first.next = first.next.next;
        first.next.prev = first;

        toBeDeleted.next = null;
        toBeDeleted.prev = null;
        N--;

        return toBeDeleted.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty.");

        Node<Item> toBeDeleted = last.prev;

        last.prev = last.prev.prev;
        last.prev.next = last;

        toBeDeleted.next = null;
        toBeDeleted.prev = null;
        N--;

        return toBeDeleted.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        private int size;

        public ListIterator(Node<Item> first) {
            current = first.next;
            size = size();
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

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }


    // unit testing
    public static void main(String[] args) {

    }
}
