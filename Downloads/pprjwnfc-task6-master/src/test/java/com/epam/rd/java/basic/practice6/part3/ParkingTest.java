package com.epam.rd.java.basic.practice6.part3;

import org.junit.Assert;
import org.junit.Test;

public class ParkingTest {

    @Test
    public void arrivedCar() {
       Parking parking = new Parking(3);
       boolean arr = parking.arrive(1);
        Assert.assertEquals(true,arr);

    }
    @Test
    public void notArrivedCar(){
        Parking parking = new Parking(3);
        parking.arrive(2);
        parking.arrive(2);
        parking.arrive(1);
        Assert.assertFalse(parking.arrive(2));
    }

}
