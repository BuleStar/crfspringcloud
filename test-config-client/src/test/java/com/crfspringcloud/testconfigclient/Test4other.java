package com.crfspringcloud.testconfigclient;

import java.util.concurrent.Semaphore;

public class Test4other {
    /**
     * 以A开始的信号量,初始信号量数量为1
     */
    private static Semaphore A = new Semaphore(1);
    /**
     * B、C信号量,A完成后开始,初始信号数量为0
     */
    private static Semaphore B = new Semaphore(0);
    private static Semaphore C = new Semaphore(0);

    private int curNum = 1;

    private void printNum() {
        if (curNum > 75) {
            Thread.currentThread().interrupt();
            return;
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "  " + (curNum++));
        }
    }


    public void process1() {
        try {
            // A获取信号执行,A信号量减1,当A为0时将无法继续获得该信号量
            A.acquire();
            printNum();
            // B释放信号，B信号量加1（初始为0），此时可以获取B信号量
            B.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void process2() {
        try {
            B.acquire();
            printNum();
            C.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void process3() {
        try {
            C.acquire();
            printNum();
            A.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Test4other p = new Test4other();
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                p.process1();
            }
        }, "A").start();

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                p.process2();
            }
        }, "B").start();

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                p.process3();
            }
        }, "C").start();

    }
}
