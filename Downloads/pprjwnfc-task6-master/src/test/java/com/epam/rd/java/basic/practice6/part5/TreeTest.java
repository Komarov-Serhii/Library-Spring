package com.epam.rd.java.basic.practice6.part5;


import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TreeTest {
        @Test
        public void shouldCreateTree() {
            Tree<Integer> tree = new Tree<>();

            System.out.println(tree.add(3));
            System.out.println(tree.add(3));


            Assert.assertEquals("true\n" +
                    "false\n" +
                    "3", "true\n" +
            "false\n" +
            "3");
        }
}
