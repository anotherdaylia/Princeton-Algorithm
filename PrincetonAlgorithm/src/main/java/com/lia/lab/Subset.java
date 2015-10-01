package com.lia.lab;
import edu.princeton.cs.algs4.*;
/**
 * Created by liqu on 9/29/15.
 */
public class Subset {
    private static RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

    private static void subset(String[] str, int k) {
        for (int i = 0; i < str.length; i++) {
            randomizedQueue.enqueue(str[i]);
        }

        for(int i = 0; i < k; i++) {
            System.out.println(randomizedQueue.dequeue());
        }
    }

    public static void main(String[] args) {
        Integer k = Integer.parseInt(args[0]);
        //subset(StdIn.readAllLines(), k);
        subset(StdIn.readAllStrings(), k);
    }
}
