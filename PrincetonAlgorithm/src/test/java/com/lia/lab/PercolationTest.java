package com.lia.lab;

import com.lia.lab.Percolation.Percolation;
import org.junit.Test;

/**
 * Created by liqu on 2/3/16.
 */
public class PercolationTest {

    //Test
    public void isOpenTest() throws InterruptedException {
        Percolation perc = new Percolation(4);
        System.out.println("Is open?: (1,1) - " + perc.isOpen(1,1) );
        System.out.println("Is open?: (2,2) - " + perc.isOpen(2,2) );
        System.out.println("Is open?: (3,3) - " + perc.isOpen(3,3) );
        System.out.println("Is open?: (4,4) - " + perc.isOpen(4,4) );
        perc.open(1,1);
        System.out.println("(1,1) opened.");
        System.out.println("Is open?: (1,1) - " + perc.isOpen(1,1) );
        System.out.println("Is open?: (2,2) - " + perc.isOpen(2,2) );
        System.out.println("Is open?: (3,3) - " + perc.isOpen(3,3) );
        System.out.println("Is open?: (4,4) - " + perc.isOpen(4,4) );
        perc.open(3,3);
        System.out.println("(3,3) opened.");
        System.out.println("Is open?: (1,1) - " + perc.isOpen(1,1) );
        System.out.println("Is open?: (2,2) - " + perc.isOpen(2,2) );
        System.out.println("Is open?: (3,3) - " + perc.isOpen(3,3) );
        System.out.println("Is open?: (4,4) - " + perc.isOpen(4,4) );

    }

    //Test
    public void isFullTest() throws InterruptedException {
        Percolation perc = new Percolation(4);
        System.out.println("Is full?: (2,1) - " + perc.isFull(1, 2) );
        System.out.println("Is full?: (2,2) - " + perc.isFull(2, 2) );
        System.out.println("Is full?: (3,2) - " + perc.isFull(3, 2) );
        System.out.println("Is full?: (3,3) - " + perc.isFull(3, 3) );
        System.out.println("Is full?: (4,3) - " + perc.isFull(4, 3) );
        perc.open(1,2);
        System.out.println("(2,1) opened.");
        System.out.println("Is full?: (2,1) - " + perc.isFull(1, 2) );
        System.out.println("Is full?: (2,2) - " + perc.isFull(2, 2) );
        System.out.println("Is full?: (3,2) - " + perc.isFull(3, 2) );
        System.out.println("Is full?: (3,3) - " + perc.isFull(3, 3) );
        System.out.println("Is full?: (4,3) - " + perc.isFull(4, 3) );
        perc.open(2,2);
        System.out.println("(2,2) opened. ");
        System.out.println("Is full?: (2,1) - " + perc.isFull(1, 2) );
        System.out.println("Is full?: (2,2) - " + perc.isFull(2, 2) );
        System.out.println("Is full?: (3,2) - " + perc.isFull(3, 2) );
        System.out.println("Is full?: (3,3) - " + perc.isFull(3, 3) );
        System.out.println("Is full?: (4,3) - " + perc.isFull(4, 3) );
        perc.open(3,3);
        System.out.println("(3,3) opened.");
        System.out.println("Is full?: (2,1) - " + perc.isFull(1, 2) );
        System.out.println("Is full?: (2,2) - " + perc.isFull(2, 2) );
        System.out.println("Is full?: (3,2) - " + perc.isFull(3, 2) );
        System.out.println("Is full?: (3,3) - " + perc.isFull(3, 3) + " is Open?: " + perc.isOpen(3,3));
        System.out.println("Is full?: (4,3) - " + perc.isFull(4, 3) + " is Open?: " + perc.isOpen(4,3));
        perc.open(4,3);
        System.out.println("(4,3) opened.");
        System.out.println("Is full?: (2,1) - " + perc.isFull(1, 2) );
        System.out.println("Is full?: (2,2) - " + perc.isFull(2, 2) );
        System.out.println("Is full?: (3,2) - " + perc.isFull(3, 2) );
        System.out.println("Is full?: (3,3) - " + perc.isFull(3, 3) + " is Open?: " + perc.isOpen(3,3));
        System.out.println("Is full?: (4,3) - " + perc.isFull(4, 3) + " is Open?: " + perc.isOpen(4,3));
        perc.open(3,2);
        System.out.println("(3,2) opened.");
        System.out.println("Is full?: (2,1) - " + perc.isFull(1, 2) );
        System.out.println("Is full?: (2,2) - " + perc.isFull(2, 2) );
        System.out.println("Is full?: (3,2) - " + perc.isFull(3, 2) );
        System.out.println("Is full?: (3,3) - " + perc.isFull(3, 3) + " is Open?: " + perc.isOpen(3,3));
        System.out.println("Is full?: (4,3) - " + perc.isFull(4, 3) + " is Open?: " + perc.isOpen(4,3));
    }

    @Test
    public void percTest() throws InterruptedException {
        Percolation perc = new Percolation(4);

        perc.open(1,2);
        System.out.println("(2,1) opened.");
        System.out.println("percolate? " + perc.percolates());
        perc.open(2,2);
        System.out.println("(2,2) opened. ");
        System.out.println("percolate? " + perc.percolates());
        perc.open(3,3);
        System.out.println("(3,3) opened.");
        System.out.println("percolate? " + perc.percolates());
        perc.open(4,3);
        System.out.println("(4,3) opened.");
        System.out.println("percolate? " + perc.percolates());
        perc.open(3,2);
        System.out.println("(3,2) opened.");
        System.out.println("percolate? " + perc.percolates());
    }

}
