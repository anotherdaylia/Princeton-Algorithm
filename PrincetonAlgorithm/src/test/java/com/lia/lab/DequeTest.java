package com.lia.lab;

import org.junit.Test;
import static org.junit.Assert.*;

public class DequeTest {

    @Test
    public void testBasicCases() {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);

        deque.addLast(4);
        deque.addLast(5);

        assertEquals(deque.size(), 5);
    }
}
