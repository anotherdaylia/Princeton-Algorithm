package com.lia.lab;
import org.junit.Test;

import java.util.Iterator;
import java.util.*;
/**
 * Created by liqu on 9/28/15.
 */


public class RandomizedQueueTest {

    //Test
    public void testBasicCases() {
        RandomizedQueue<Integer> rdQueue = new RandomizedQueue();

        rdQueue.enqueue(1);
        rdQueue.enqueue(2);
        rdQueue.enqueue(3);
        rdQueue.enqueue(4);
        rdQueue.enqueue(5);
        rdQueue.enqueue(6);
        rdQueue.enqueue(7);
        //rdQueue.enqueue(8);


        System.out.println(rdQueue.dequeue());
        System.out.println(rdQueue.dequeue());
        System.out.println(rdQueue.dequeue());
        System.out.println(rdQueue.dequeue());
        System.out.println(rdQueue.dequeue());
        System.out.println(rdQueue.dequeue());
        System.out.println(rdQueue.dequeue());

    }

    //Test
    public void testIterator() {
        RandomizedQueue<Integer> rdQueue = new RandomizedQueue();

        rdQueue.enqueue(1);
        rdQueue.enqueue(2);
        rdQueue.enqueue(3);
        rdQueue.enqueue(4);
        rdQueue.enqueue(5);
        rdQueue.enqueue(6);
        rdQueue.enqueue(7);
        //rdQueue.enqueue(8);

        Iterator<Integer> it = rdQueue.iterator();
        while(it.hasNext()) {

            System.out.println(it.next());
        }

    }

    @Test
    public void testSample() {
        RandomizedQueue<Integer> rdQueue = new RandomizedQueue();
        System.out.println("test sample");
        rdQueue.enqueue(1);
        rdQueue.enqueue(2);
        rdQueue.enqueue(3);
        rdQueue.enqueue(4);
        rdQueue.enqueue(5);
        rdQueue.enqueue(6);
        rdQueue.enqueue(7);
        System.out.println(rdQueue.sample());
        System.out.println(rdQueue.sample());
        System.out.println(rdQueue.sample());
        System.out.println(rdQueue.sample());
        System.out.println(rdQueue.sample());
        System.out.println(rdQueue.sample());
        System.out.println(rdQueue.sample());

    }
}
