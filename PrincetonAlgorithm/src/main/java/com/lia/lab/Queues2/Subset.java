package com.lia.lab.Queues2;


import edu.princeton.cs.algs4.*;
/**
 * Created by liqu on 9/29/15.
 */
public class Subset {

    public static void main(String[] args) {
        Integer k = Integer.parseInt(args[0]);
        String[] strArr = StdIn.readAllStrings();
//        int k = 3;
//        String[] strArr = {"AA", "BB", "CC"};

        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < strArr.length; i++) {
            rq.enqueue(strArr[i]);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }

    }
}
