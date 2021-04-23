package com.epam.rd.java.basic.practice6.part2;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Part2Test {
    public static final int N = 1000;

    Part2 part2 = new Part2(1000);
    @Test
    public void index_test(){
        List<Integer> linkedList = new LinkedList<>();
        List<Integer> arrayList = new ArrayList<>();
        part2.fill(linkedList,N);
        part2.fill(arrayList,N);

        boolean index_actual =  part2.removeByIndex(linkedList,3) <= part2.removeByIndex(arrayList,3);
        boolean expected = true;

        Assert.assertEquals(expected,index_actual);
    }
    @Test
    public void iterator_test(){
        List<Integer> linkedList = new LinkedList<>();
        List<Integer> arrayList = new ArrayList<>();
        part2.fill(linkedList,N);
        part2.fill(arrayList,N);

        boolean index_actual =  part2.removeByIterator(linkedList,3) >= part2.removeByIterator(arrayList,3);
        boolean expected = true;

        Assert.assertEquals(expected,index_actual);
    }
}