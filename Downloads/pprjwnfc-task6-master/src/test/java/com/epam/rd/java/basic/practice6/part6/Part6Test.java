package com.epam.rd.java.basic.practice6.part6;

import org.junit.Assert;
import org.junit.Test;

public class Part6Test {

    public static final String FILE = "part6.txt";
    public static final String TASK = "--task";
    public static final String FREQUENCY = "frequency";


    @Test
    public void ShouldReturnFalse() {
        Assert.assertFalse(new Part6().console("put", FILE, TASK, FREQUENCY));
    }

    @Test
    public void ShouldReturnFalse2() {
        Assert.assertFalse(new Part6().console("--input", FILE, TASK, FREQUENCY));
    }
}
