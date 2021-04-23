package com.epam.rd.java.basic.practice6.part4;

import org.junit.Assert;
import org.junit.Test;

public class RangeTest {
    @Test
    public void ShouldRightDirect() {
        String result = "3 4 5 6 7 8 ";
        Range range = new Range(3,8);

        StringBuilder stringBuilder = new StringBuilder();
        for(Integer el: range){
            stringBuilder.append(el).append(" ");
        }

        String action = stringBuilder.toString();
        Assert.assertEquals(result,action);

    }

    @Test
    public void ShouldReverseDirect(){
        String result = "8 7 6 5 4 3 ";
        Range range  = new Range(3,8);

        StringBuilder stringBuilder = new StringBuilder();
        for (Integer el: range){
            stringBuilder.append(el).append(" ");
        }

        String action = stringBuilder.toString();
        Assert.assertEquals(result,action);

    }
}
