package com.epam.rd.java.basic.practice6.part2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Part2 {

    static int quantity;

    public Part2(int quantity){
        this.quantity = quantity;
    }
     static final int N = 1000;
     static final int K = 3;

    public static void main(String[] args) {



        List<Integer> linkedList = new LinkedList<>();
        List<Integer> arrayList = new ArrayList<>();

        fill(linkedList, N);
        fill(arrayList, N);

        System.out.print("ArrayList#Index:" + " ");
        System.out.println(removeByIndex(arrayList, K) + " ms");
        System.out.print("LinkedList#Index:" + " ");
        System.out.println(removeByIndex(linkedList, K) + " ms");

        System.out.print("ArrayList#Iterator:" + " ");
        System.out.println(removeByIterator(arrayList,K) + " ms");
        System.out.print("LinkedList#Iterator:" + " ");
        System.out.println(removeByIterator(linkedList, K) + " ms");


    }


    public static long removeByIndex(final List<Integer> list, final int k) {
        long time = System.currentTimeMillis();
        int local = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.size() == 1) {
                break;
            }

            local += (k - 1);

            while (local >= list.size()) {
                local = local - list.size();
            }
            list.remove(local);

        }

        return System.currentTimeMillis() - time;
    }

    public static long removeByIterator(final List<Integer> list, int k) {
      long time  = System.currentTimeMillis();
      int count = 0;
      Iterator<Integer> iterator = list.iterator();
      while (list.size() > 1){
          if(iterator.hasNext()){
              iterator.next();
              count++;
             if(count == k){
                 iterator.remove();
                 count=0;
             }
          }else
              iterator = list.iterator();
      }

        return System.currentTimeMillis() - time;
    }

    public static List<Integer> fill(List<Integer> list,int n) {
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        return list;
    }
}

