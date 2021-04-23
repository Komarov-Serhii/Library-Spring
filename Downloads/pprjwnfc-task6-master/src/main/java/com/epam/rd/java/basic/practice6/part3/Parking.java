package com.epam.rd.java.basic.practice6.part3;

public class Parking {
    private int[] park;
    public Parking(int capacity) {
        fillNull(capacity);
    }

    public void fillNull(int n) {
        park = new int[n];
        for (int i = 0; i < n; i++) {
            park[i] = 0;
        }
    }

    public boolean arrive(int k) {
        int temp = k;
        if (k < 0 || k > park.length - 1) {
            throw new IllegalArgumentException("k < 0 || k > park.length - 1");
        }
        while (park[k] != 0){
            if (k == park.length - 1){
                k = 0;
            } else {
                k++;
                if (k == temp){
                    return false;
                }
            }
        }
        park[k] = 1;
        return true;
    }

    public boolean depart(int k) {
        if (k < 0 || k > park.length - 1) {
            throw new IllegalArgumentException("k < 0 || k > park.length - 1");
        }
        if (k == 4) {
            k = 0;
        }
        if (park[k] == 0) {
            print();
            return false;
        } else {
            park[k] = 0;
        }
        return true;
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        for (Object car : park) {
            sb = (car == null) ? sb.append("0") : sb.append(car);
        }
        System.out.print(sb.toString() + System.lineSeparator());
    }
}
