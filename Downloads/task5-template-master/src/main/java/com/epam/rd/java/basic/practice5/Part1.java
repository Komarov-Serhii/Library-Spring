package com.epam.rd.java.basic.practice5;

public class Part1 {

    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread1.join();
        thread2.run();

    }

    static class Thread1 extends Thread {
        public void run() {
            for (int i = 0; i < 4; i++) {
                System.out.println(new Thread1().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Thread2 implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                System.out.println(new Thread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

